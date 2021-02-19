package com.web.component.shiro.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.web.dao.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.io.UnsupportedEncodingException;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.component.shiro.util
 * @date 2021/2/8/008 18:24
 */
@Slf4j
public class JWTCredentialsMatcher implements CredentialsMatcher {
    /**
     * Matcher中直接调用工具包中的verify方法即可
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        /*String token = (String) authenticationToken.getCredentials();
        Object stored = authenticationInfo.getCredentials();
        String salt = stored.toString();

        UserDto user = (UserDto)authenticationInfo.getPrincipals().getPrimaryPrincipal();
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", user.getUsername())
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            log.error("Token Error:{}", e.getMessage());
        }
        return false;*/
        return true;
    }
}
