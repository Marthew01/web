package com.web.component.shiro.util;

import com.web.common.exception.CustomException;
import com.web.dao.mapper.UserMapper;
import com.web.dao.model.UserDto;
import com.web.dao.model.common.Constant;
import com.web.dao.model.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取当前登录用户工具类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/3/15 11:45
 */
@Component
public class UserUtil {

    private final UserMapper userMapper;

    @Autowired
    public UserUtil(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 获取当前登录用户
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public User getUser() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得Account
        String userName = JwtUtil.getClaim(token, Constant.ACCOUNT);
        UserDto userDto = new UserDto();
        userDto.setUsername(userName);
        userDto = userMapper.findOne(userDto);
        // 用户是否存在
        if (userDto == null) {
            throw new CustomException("该帐号不存在(The account does not exist.)");
        }
        return userDto;
    }

    /**
     * 获取当前登录用户Id
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public long getUserId() {
        return getUser().getId();
    }

    /**
     * 获取当前登录用户Token
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户Account
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public String getAccount() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得Account
        return JwtUtil.getClaim(token, Constant.ACCOUNT);
    }
}
