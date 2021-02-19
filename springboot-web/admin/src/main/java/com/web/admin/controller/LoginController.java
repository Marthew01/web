package com.web.admin.controller;

import com.web.common.exception.CustomUnauthorizedException;
import com.web.component.redis.util.JedisUtil;
import com.web.component.shiro.util.JwtUtil;
import com.web.dao.model.UserDto;
import com.web.dao.model.common.Constant;
import com.web.dao.model.common.ResultVo;
import com.web.dao.model.entity.User;
import com.web.dao.result.ResultVoUtil;
import com.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.controller
 * @date 2021/1/6/006 16:15
 */
@Slf4j
@Controller
public class LoginController {

    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    IUserService userService;

    @GetMapping(value = {"/login","/"})
    public String toLogin() {
        return "/login";
    }
    /**
     * 登录
     * @return main.html
     */
    @PostMapping(value = "/login")
    public ResultVo login(User user, HttpServletResponse httpServletResponse) {
        // 根据用户名和密码创建 Token
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        // 获取 subject 认证主体
        Subject subject = SecurityUtils.getSubject();
        //开始认证，这一步会跳到我们自定义的 Realm 中
        subject.login(token);
        //Shiro认证通过后会将user信息放到subject内，生成token并返回
        Object principal = subject.getPrincipal();

        User userTemp = userService.findUserByAccount(user.getUsername());

        if (userTemp == null) {
            throw new CustomUnauthorizedException("该帐号不存在(The account does not exist.)");
        }

        if (userTemp == null) {
            throw new CustomUnauthorizedException("该帐号不存在(The account does not exist.)");
        }

        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + userTemp.getAccount())) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + userTemp.getAccount());
        }
        // redis设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userTemp.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String tokens = JwtUtil.sign(userTemp.getAccount(), currentTimeMillis);
        log.info("返回加密的Token[{}]",tokens);
        httpServletResponse.setHeader("Authorization", tokens);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

        /**
         * 进行AccessToken登录认证授权
         */
        /*@Override
        protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
            // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
            JwtToken token = new JwtToken(this.getAuthzHeader(request));
            // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
            this.getSubject(request, response).login(token);
            // 如果没有抛出异常则代表登入成功，返回true
            return true;
        }*/

//#################################################
        // 查询数据库中的帐号信息
        /*User userTemp = userService.findUserByAccount(user.getUsername());

        if (userTemp == null) {
            throw new CustomUnauthorizedException("该帐号不存在(The account does not exist.)");
        }

        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + userTemp.getAccount())) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + userTemp.getAccount());
        }
        // redis设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userTemp.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String tokens = JwtUtil.sign(userTemp.getAccount(), currentTimeMillis);
        log.info("返回加密的Token[{}]",tokens);
        httpServletResponse.setHeader("Authorization", tokens);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
*/
        return ResultVoUtil.success("登录成功");
    }
}
