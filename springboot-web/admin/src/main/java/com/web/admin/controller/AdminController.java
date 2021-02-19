package com.web.admin.controller;

import com.web.component.shiro.util.PasswordHelper;
import com.web.dao.model.UserDto;
import com.web.dao.model.common.ResultEnum;
import com.web.dao.model.common.ResultVo;
import com.web.dao.model.entity.User;
import com.web.dao.result.ResultVoUtil;
import com.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 管理员
 *
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.admin.controller
 * @date 2021/1/7/007 16:32
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IUserService userService;

    @Autowired
    PasswordHelper passwordHelper;

    /**
     * 注册用户
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/register")
    @ResponseBody
    public ResultVo register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setAccount(username);
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateDate(new Date());
        user.setCity("超强管理员");
        user.setClassify("超强管理员");
        user.setExperience(999999999);
        user.setSex("女");
        user.setWealth(9999999);
        passwordHelper.encryptPassword(user);
        userService.save(user);
        log.info("用户: {} 注册成功", username);
        return ResultVoUtil.success("注册成功");
    }


    /**
     * 新增用户
     * @param userDto
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    /*@PostMapping
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public ResponseEntity add(@RequestBody UserDto userDto) {
        // 判断当前帐号是否存在
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setAccount(userDto.getAccount());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp != null && StringUtil.isNotBlank(userDtoTemp.getPassword())) {
            throw new CustomUnauthorizedException("该帐号已存在(Account exist.)");
        }
        userDto.setRegTime(new Date());
        // 密码以帐号+密码的形式进行AES加密
        if (userDto.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
            throw new CustomException("密码最多8位(Password up to 8 bits.)");
        }
        String key = AesCipherUtil.enCrypto(userDto.getAccount() + userDto.getPassword());
        userDto.setPassword(key);
        int count = userService.insert(userDto);
        if (count <= 0) {
            throw new CustomException("新增失败(Insert Failure)");
        }
        return new ResponseBean(HttpStatus.OK.value(), "新增成功(Insert Success)", userDto);
    }*/


    /**
     * 返回员工列表
     *
     * @return ResultVo
     */
    @GetMapping("/userList")
//    @Cacheable(cacheNames = "pageList",key = "#user.username==null?'allpage':#user.username")
    @RequiresRoles(value = {"admin"})
//    @RequiresAuthentication
    @ResponseBody
    public ResultVo userList(User user) {

        return ResultVoUtil.success(userService.getPageList(user));
    }


    @GetMapping("/getUser/{id}")
//    @Cacheable(value = "user",key = "111")
    @ResponseBody
    public User getUser(@PathVariable(required=true) Integer id) {
        User user = userService.findUserById(id);
        return user;
    }


    @GetMapping("/getaa")
    @ResponseBody
    public ResponseEntity getUser1(Integer id,String name) {

        return new ResponseEntity<>(id+"哈哈"+name, HttpStatus.valueOf(400));
    }

    /**
     * 测试是否登录
     * @return
     */
    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResultVo requireAuth() {
        return ResultVoUtil.success("You are authenticated", null);
    }

}
