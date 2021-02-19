package com.web.admin.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.controller
 * @date 2021/1/7/007 14:03
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {

    // 当用户访问未授权的资源时，跳转到 403 视图，并携带出错信息
    @ExceptionHandler(UnknownAccountException.class)
    public ModelAndView error(UnknownAccountException e) {
        ModelAndView mv = new ModelAndView("login");
        log.info("用户名不存在");
        mv.addObject("error", "用户名不存在");
        return mv;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView error(AuthenticationException e) {
        ModelAndView mv = new ModelAndView("login");
        log.info("密码错误");
        mv.addObject("error", "密码错误");
        return mv;
    }

    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView error(AuthorizationException e) {
        ModelAndView mv = new ModelAndView("/403");
        log.info("没有权限");
        mv.addObject("error", "没有权限");
        return mv;
    }
}
