package com.web.component.shiro.exception;

import com.web.common.exception.CustomException;
import com.web.common.exception.CustomUnauthorizedException;
import com.web.common.model.validator.FieldValidatorError;
import com.web.dao.model.common.ResponseBean;
import com.web.dao.model.common.ResultEnum;
import com.web.dao.model.common.ResultVo;
import com.web.dao.result.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 异常控制处理器
 *
 * @author dolyw.com
 * @date 2018/8/30 14:02
 */
@Slf4j
@ControllerAdvice
public class ShiroExceptionsAdvice {

    private static final String LOG_EXCEPTION_FORMAT = "[EXIGENCE] Some thing wrong with the system: %s";

    /**
     * 捕捉所有Shiro异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ModelAndView handle401(ShiroException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/403");
        log.info("异常:[{}]", e.getMessage());
        modelAndView.addObject("error", ResultEnum.NO_PERMISSIONS);
        return modelAndView;
    }

    /**
     * 认证登录（父类）
     * 单独捕捉Shiro(AuthenticationException)异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handle401(AuthenticationException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        log.info("登录失败:[{}]", e.getMessage());
        modelAndView.addObject("error", ResultEnum.USER_ADMIN);
        return modelAndView;
    }


    /**
     * 授权（父类）
     * 单独捕捉Shiro(AuthorizationException)异常
     * 当前Subject对请求的资源的访问是不允许的
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView handle401(AuthorizationException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/403");
        log.info("权限不足:[{}]", e.getMessage());
        modelAndView.addObject("error", ResultEnum.NO_PERMISSIONS);
        return modelAndView;
    }


    /**
     * 认证登录
     * UnknownAccountException 未知的账号
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnknownAccountException.class)
    public ModelAndView handle401(UnknownAccountException e, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        log.info("账号不存在:[{}]", e.getMessage());
        modelAndView.addObject("error", ResultEnum.USER_NOT_EXIST);
        return modelAndView;
    }

    /**
     * 认证登录（父类）
     * 单独捕捉Shiro(AuthenticationException)异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ModelAndView handle401(IncorrectCredentialsException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        log.info("密码错误:[{}]", e.getMessage());
        modelAndView.addObject("error", ResultEnum.USER_PWD_ERROR);
        return modelAndView;
    }

/**
 * ExcessiveAttemptsException 用户被禁用
 * ExcessiveAttemptsException 用户被锁定
 *
 */
    /**
     * 授权
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 当前Subject对请求的资源的访问是不允许的
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResultVo handle401(UnauthorizedException e) {
        log.info("权限不足:[{}]", e.getMessage());
        return ResultVoUtil.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    /**
     * 授权
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 当尚未完成成功认证时，尝试执行授权操作时引发异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public ModelAndView handle401(UnauthenticatedException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        log.info("游客未登录:[{}]", e.getMessage());
        modelAndView.addObject("error", ResultEnum.USER_ANONYMOUS);
        return modelAndView;
    }

    /**
     * 捕捉UnauthorizedException自定义异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResultVo handle401(CustomUnauthorizedException e) {
        return ResultVoUtil.error(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage());
    }

    /**
     * 数据绑定异常(BindException)，效果与MethodArgumentNotValidException类似，为MethodArgumentNotValidException的父类
     *
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResultVo validException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResultVoUtil.error(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
    }


    /**
     * MethodArgumentNotValidException: 实体类属性校验不通过
     * 如: listUsersValid(@RequestBody @Valid UserFilterOption option)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo validException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResultVoUtil.error(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
    }

    /**
     * ConstraintViolationException: 直接对方法参数进行校验，校验不通过。
     * 如: pageUsers(@RequestParam @Min(1)int pageIndex, @RequestParam @Max(100)int pageSize)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo handleConstraintViolationException(HttpServletRequest request,
                                                       ConstraintViolationException ex) {
        log.debug("[{}]", ex.getMessage());
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .collect(Collectors.toList()));
        return ResultVoUtil.error(this.getStatus(request).value(), "方法参数校验不通过。", messages);
    }

    /**
     * 捕捉其他所有自定义异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public ResultVo handle(CustomException e) {
        return ResultVoUtil.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


    /**
     * 捕捉404异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(NoHandlerFoundException e) {
        ModelAndView modelAndView = new ModelAndView();
        ResponseBean responseBean = new ResponseBean(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        modelAndView.setViewName("error/4xx");
        log.info("页面飞了:[{}]", e.getMessage());
        modelAndView.addObject("error", responseBean);
        return modelAndView;
    }

    /**
     * 返回值类型转化错误
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResultVo exceptionHandle(HttpServletRequest request,
                                    HttpMessageConversionException ex) {
        return internalServiceError(ex);
    }

    /**
     * 对应 Http 请求头的 accept
     * 客户器端希望接受的类型和服务器端返回类型不一致。
     * 这里虽然设置了拦截，但是并没有起到作用。需要通过http请求的流程来进一步确定原因。
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public ResultVo handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
                                                              HttpMediaTypeNotAcceptableException ex) {
        StringBuilder messageBuilder = new StringBuilder().append("The media type is not acceptable.")
                .append(" Acceptable media types are ");
        ex.getSupportedMediaTypes().forEach(t -> messageBuilder.append(t + ", "));
        String message = messageBuilder.substring(0, messageBuilder.length() - 2);
        log.info("accept异常[{}]", message);
        return ResultVoUtil.error(HttpStatus.NOT_ACCEPTABLE.value(), message);
    }

    /**
     * 对应请求头的 content-type
     * 客户端发送的数据类型和服务器端希望接收到的数据不一致
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResultVo handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                             HttpMediaTypeNotSupportedException ex) {
        StringBuilder messageBuilder = new StringBuilder().append(ex.getContentType())
                .append(" media type is not supported.").append(" Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> messageBuilder.append(t + ", "));
        String message = messageBuilder.substring(0, messageBuilder.length() - 2);
        log.info("content-type异常[{}]", message);
        return ResultVoUtil.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), message);
    }

    /**
     * 前端发送过来的数据无法被正常处理
     * 比如后天希望收到的是一个json的数据，但是前端发送过来的却是xml格式的数据或者是一个错误的json格式数据
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultVo handlerHttpMessageNotReadableException(HttpServletRequest request,
                                                           HttpMessageNotReadableException ex) {
        String message = "Problems parsing JSON";
        log.info("前端发送过来的数据无法被正常处理异常[{}]", message);
        return ResultVoUtil.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 将返回的结果转化到响应的数据时候导致的问题。
     * 当使用json作为结果格式时，可能导致的原因为序列化错误。
     * 目前知道，如果返回一个没有属性的对象作为结果时，会导致该异常。
     */
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResultVo handlerHttpMessageNotWritableException(HttpServletRequest request,
                                                           HttpMessageNotWritableException ex) {
        return internalServiceError(ex);
    }

    /**
     * 请求方法不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultVo exceptionHandle(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {

        StringBuilder messageBuilder = new StringBuilder().append(ex.getMethod())
                .append(" method is not supported to request.").append(" Supported methods:");

        ex.getSupportedHttpMethods().forEach(m -> messageBuilder.append(m + ","));
        String message = messageBuilder.substring(0, messageBuilder.length() - 2);
        return ResultVoUtil.error(HttpStatus.METHOD_NOT_ALLOWED.value(), message);
    }

    /**
     * 参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResultVo methodArgumentTypeMismatchExceptionHandler(HttpServletRequest request,
                                                               MethodArgumentTypeMismatchException ex) {

        String message = "The parameter '" + ex.getName() + "' should of type '"
                + ex.getRequiredType().getSimpleName().toLowerCase() + "'";

        return ResultVoUtil.error(this.getStatus(request).value(),message);
    }

    /**
     * 缺少必填字段
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResultVo exceptionHandle(HttpServletRequest request,
                                    MissingServletRequestParameterException ex) {

        String message = "Required parameter '" + ex.getParameterName() + "' is not present";
        return ResultVoUtil.error(this.getStatus(request).value(),message);
    }

    /**
     * 文件上传时，缺少　file 字段
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    public ResultVo exceptionHandle(HttpServletRequest request, MissingServletRequestPartException ex) {
        return ResultVoUtil.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }


    /**
     * 缺少路径参数
     * Controller方法中定义了　@PathVariable(required=true)　的参数，但是却没有在url中提供
     */
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public ResultVo exceptionHandle(HttpServletRequest request, MissingPathVariableException ex) {
        return internalServiceError(ex);
    }

    /**
     * 捕捉其他所有异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView globalException(HttpServletRequest request, Throwable ex) {
        ModelAndView modelAndView = new ModelAndView();

        ResultVo error = ResultVoUtil.error(this.getStatus(request).value(), ex.getMessage());
        modelAndView.setViewName("error/5xx");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    /**
     * 获取状态码
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 获取校验错误信息
     *
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        List<String> errorList = new ArrayList<String>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }

    private ResultVo internalServiceError(Exception ex) {
        logException(ex);
        return ResultVoUtil.error(ex.getMessage());
    }

    private <T extends Throwable> void logException(T e) {
        log.error(String.format(LOG_EXCEPTION_FORMAT, e.getMessage()), e);
    }

}
