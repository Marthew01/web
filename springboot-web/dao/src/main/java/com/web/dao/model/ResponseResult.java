package com.web.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 服务器响应类
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.model
 * @date 2021/1/8/008 9:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult implements Serializable {

    private int code;
    private String message;
    private Object data;

}