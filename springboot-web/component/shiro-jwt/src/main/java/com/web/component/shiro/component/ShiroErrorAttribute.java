package com.web.component.shiro.component;

import com.web.dao.model.common.ResultEnum;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 *
 *
 * 尚未使用
 * 尚未使用
 * 尚未使用
 * 尚未使用
 * 尚未使用
 * 尚未使用
 * 尚未使用
 * 尚未使用
 * 尚未使用
 *
 *
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.component.shiro.component
 * @date 2021/2/4/004 15:07
 */
//@Component
public class ShiroErrorAttribute extends DefaultErrorAttributes {

    /**
     * 自定义ErrorAttributes,携带我们的自定义的异常返回的数据
     * @param webRequest
     * @param includeStackTrace
     * @return
     */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        /**
         * 0表示从request域中获取
         * 1表示从session域中获取
         */
        Map<String, Object> map = (Map<String, Object>)webRequest.getAttribute("extMap", 0);
        errorAttributes.put("extMap",map);
        return errorAttributes;
    }
}
