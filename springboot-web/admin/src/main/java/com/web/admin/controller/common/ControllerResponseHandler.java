package com.web.admin.controller.common;

import com.web.dao.model.common.ResultVo;
import com.web.dao.result.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一 Controller 返回值格式
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.admin.controller.common
 * @date 2021/2/4/004 18:20
 */
@Slf4j
@ControllerAdvice
public class ControllerResponseHandler implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 支持所有的返回值类型
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if(body instanceof ResultVo) {
            return body;
        } else {
            // 所有没有返回ResultVo结构的结果均认为是成功的 仍然包装成ResultVo
            return ResultVoUtil.success(body);
        }
    }
}
