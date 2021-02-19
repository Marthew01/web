package com.web.common.interceptor;

import com.web.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.common.interceptor
 * @date 2021/1/6/006 10:45
 */
@Slf4j
@Component
public class HandlerInterceptors implements HandlerInterceptor {

    /**
     * 在调用controller之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getRequestURI();
        String ip = getIpAddr(request);
        String url = path.substring(path.lastIndexOf("/") + 1, path.length());
        log.info("请求的url:[{}]; Method:[{}];客户端的IP:[{}]",url,request.getMethod(),ip);

        Enumeration<?> paraNames = request.getParameterNames();
        while (paraNames.hasMoreElements()) {
            String paraName = (String) paraNames.nextElement();
            String paraStrval = request.getParameter(paraName);
            if (paraStrval.length() < 5000){
                log.info("---paraName--->:[{}] paraValue--->:[{}]",paraName,paraStrval);
                if (!StringUtils.isEmpty(paraStrval)) {
                    paraStrval = paraStrval.toLowerCase();
                    for (String filter : filterStr) {
                        if (paraStrval.contains(filter)) {
                            log.info("本次请求包含特殊字符，拒绝本次请求，参数内容：{}",paraStrval);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 方法在controller被调用之后调用，可在modelAndView中加入数据
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("Controller请求处理后===");

        /**
         * 该方法生效条件：不能使用@ResponseBody注解，@ResponseBody注解会提前返回
         * 统一处理返回值可以使用：className implements ResponseBodyAdvice{
         *     @Override
         *     public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
         *
         *         System.out.println("TestResponseBodyAdvice==>beforeBodyWrite:" + o.toString() + ","
         *                 + methodParameter);
         *         return o;
         *     }
         *
         *     @Override
         *     public boolean supports(MethodParameter methodParameter, Class aClass) {
         *         return true;
         *     }
         * }
         */
        /*if (modelAndView != null) {
            ModelMap map = modelAndView.getModelMap();
            if (map != null && map.get("values") != null){
                log.info("请求返回结果代码--->" + map.get("values") + ";请求返回消息--->" + map.get("msg"));}
        }*/

        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            switch (statusCode) {
                case 404:
                    throw new NoHandlerFoundException(request.getMethod(),request.getRequestURI(), HttpHeaders.EMPTY);
                case 500:
                    throw new Exception();

            }
        }
    }

    /**
     * 在呈现视图之后调用，可用于清理资源等
      * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    //非法字符
    private static String[] filterStr =
            { "eval", "$(", "$.","<img","<",">","\"","\'","confirm","script",
             "select","insert","document","window","function",
            "delete","update","prompt","alert","create","alter",
            "drop","iframe","link","where","replace","function","onabort",
            "onactivate","onafterprint","onafterupdate","onbeforeactivate",
            "onbeforecopy","onbeforecut","onbeforedeactivateonfocus",
            "onkeydown","onkeypress","onkeyup","onload",
            "expression","applet","layer","ilayeditfocus","onbeforepaste",
            "onbeforeprint","onbeforeunload","onbeforeupdate",
            "onblur","onbounce","oncellchange","oncontextmenu",
            "oncontrolselect","oncopy","oncut","ondataavailable",
            "ondatasetchanged","ondatasetcomplete","ondeactivate",
            "ondrag","ondrop","onerror","onfilterchange","onfinish","onhelp",
            "onlayoutcomplete","onlosecapture","onmouse","ote",
            "onpropertychange","onreadystatechange","onreset","onresize",
            "onresizeend","onresizestart","onrow","onscroll",
            "onselect","onstaronsubmit","onunload","IMgsrc","infarction" };


}
