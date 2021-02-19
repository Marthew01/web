package com.web.component.shiro;

import com.web.common.util.common.StringUtil;
import com.web.component.redis.util.JedisUtil;
import com.web.component.shiro.jwt.JwtToken;
import com.web.component.shiro.util.JwtUtil;
import com.web.dao.mapper.SysPermissionMapper;
import com.web.dao.mapper.SysRolesMapper;
import com.web.dao.mapper.UserMapper;
import com.web.dao.model.SysRoleDto;
import com.web.dao.model.UserDto;
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
 * 登录的Realm
 * @author zhang-rongyao
 * @version V1.0
 * @Package com.web.component.shiro
 * @date 2020/12/22/022
 */
@Slf4j
public class UserShiroRealm extends AuthorizingRealm{

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPermissionService permissionService;


    /**
     * controller只负责封装下参数，然后扔给Shiro
     * Shiro收到后，会到所有的realm中找能处理UsernamePasswordToken的Realm（我们这里是UserShiroRealm），然后交给Realm处理
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken != null && authenticationToken instanceof UsernamePasswordToken;
    }

    /**
     * 授权  @RequiresPermissions类似 需要权限鉴定的时候才会执行该方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String account = (String) principals.getPrimaryPrincipal();
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = (String) token.getPrincipal();
        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        User user = userService.findUserByAccount(account);
        if (user == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }

        //交给matcher--> HashedCredentialsMatcher 做判断
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        return authenticationInfo;
    }

}
