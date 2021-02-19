package com.web.dao.result;

import com.web.dao.model.common.ResultEnum;
import com.web.dao.model.common.ResultVo;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.util
 * @date 2021/1/12/012 10:14
 */
public class ResultVoUtil {
    public static ResultVo SAVE_SUCCESS = success("保存成功");

    /**
     * 操作成功
     *
     * @param msg    提示信息
     * @param object 对象
     */
    public static <T> ResultVo<T> success(String msg, T object) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setMsg(msg);
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setData(object);
        return resultVo;
    }

    /**
     * 操作成功，使用默认的提示信息
     *
     * @param object 对象
     */
    public static <T> ResultVo<T> success(T object) {
        String message = ResultEnum.SUCCESS.getMsg();
        return success(message, object);
    }

    /**
     * 操作成功，返回提示信息，不返回数据
     */
    public static <T> ResultVo<T> success(String msg) {
        return success(msg, null);
    }

    /**
     * 操作成功，不返回数据
     */
    public static ResultVo success() {
        return success(null);
    }

    /**
     * 操作有误
     * @param code 错误码
     * @param msg 提示信息
     * @param obj 错误data
     * @param <T>
     * @return
     */
    public static <T> ResultVo<T> error(Integer code, String msg,T obj) {
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg);
        resultVo.setCode(code);
        resultVo.setData(obj);
        return resultVo;
    }
    /**
     * 操作有误
     *
     * @param code 错误码
     * @param msg  提示信息
     */
    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg);
        resultVo.setCode(code);
        return resultVo;
    }

    /**
     * 操作有误，使用默认400错误码
     *
     * @param msg 提示信息
     */
    public static ResultVo error(String msg) {
        Integer code = ResultEnum.ERROR.getCode();
        return error(code, msg);
    }

    /**
     * 操作有误，只返回默认错误状态码
     */
    public static ResultVo error() {
        return error(null);
    }
}
