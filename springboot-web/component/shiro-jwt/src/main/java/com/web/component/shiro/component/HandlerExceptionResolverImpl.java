package com.web.component.shiro.component;

import com.web.common.exception.CustomException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常的视图控制跳转
 * 未使用
 * 未使用未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 * 未使用
 *
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.component.shiro.component
 * @date 2021/2/5/005 15:33
 */
@Configuration
public class HandlerExceptionResolverImpl implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", ex.getMessage());

        if (ex instanceof ShiroException) {
            //授权、认证异常
            modelAndView.setViewName("error/401");

        } else if (ex instanceof CustomException) {
            //自定义异常
            modelAndView.setViewName("error/5xx");
        } else {
            modelAndView.setViewName("error/5xx");
        }

        return modelAndView;
    }

}
