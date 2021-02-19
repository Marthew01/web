package com.web.component.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 重写token
 * JwtToken
 *
 * @author com.web.component.shiro.jwt
 * @date 2018/8/30 14:06
 */
public class JwtToken implements AuthenticationToken {
    /**
     * Token
     */
    private String token;


    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * 账号
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 密码
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
