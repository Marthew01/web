package com.web.component.shiro;

import com.web.common.util.common.StringUtil;
import com.web.component.redis.util.JedisUtil;
import com.web.component.shiro.jwt.JwtToken;
import com.web.component.shiro.util.JWTCredentialsMatcher;
import com.web.component.shiro.util.JwtUtil;
import com.web.dao.model.common.Constant;
import com.web.dao.model.entity.SysPermission;
import com.web.dao.model.entity.SysRole;
import com.web.dao.model.entity.User;
import com.web.service.ISysPermissionService;
import com.web.service.ISysRoleService;
import com.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 自定义Realm
 * @author zhang-rongyao
 * @version V1.0
 * @Package com.web.component.shiro
 * @date 2020/12/22/022
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm{

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPermissionService permissionService;


    public JwtRealm(){
        //这里使用我们自定义的Matcher
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken != null && authenticationToken instanceof JwtToken;
    }



    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        String account = (String) principals.getPrimaryPrincipal();


        /*String username = (String) principals.getPrimaryPrincipal();
        User user = userService.findUserByName(username);
        for (SysRole role : user.getRoles()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission permission : role.getPermissions()) {
                authorizationInfo.addStringPermission(permission.getName());
            }
        }
        */

          //使用jwt
        String account = JwtUtil.getClaim(principals.toString(), Constant.ACCOUNT);
        // 查询用户角色:1对多
        List<SysRole> sysRoles = roleService.findRoleByUserAccount(account);
        for (SysRole sysRoleDto : sysRoles) {
            authorizationInfo.addRole(sysRoleDto.getRole());
            for (SysPermission permission : permissionService.findPermissionByRoles(sysRoleDto)) {
                authorizationInfo.addStringPermission(permission.getName());
            }
        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String token = (String) authenticationToken.getCredentials();
//        String token = (String) authenticationToken.getPrincipal();

        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);

        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        User user = userService.findUserByName(account);
        if (user == null) {
            throw new UnknownAccountException("该帐号不存在(The account does not exist.)");
        }

        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                log.info("user getName()==========[{}]",getName());
                /*SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(token, token,
                        ByteSource.Util.bytes(user.getCredentialsSalt()), getName());*/
                SimpleAuthenticationInfo authenticationInfo3 = new SimpleAuthenticationInfo(account, user.getPassword(),getName());
                return authenticationInfo3;
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");




    }

}
