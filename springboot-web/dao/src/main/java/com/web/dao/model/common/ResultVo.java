package com.web.dao.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<T>{

    /**状态码
     *
     */

    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 总条数
     */

    private Long count;

    /**
     * 响应数据
     */

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ResultVo() {
    }

    public ResultVo(Integer code, String msg, Long count, T data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
