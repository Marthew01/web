package com.web.dao.model.common;

import org.springframework.http.HttpStatus;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.dao.model.common
 * @date 2021/1/12/012 10:15
 */
public enum ResultEnum {

    /**
     * 通用状态
     */
    SUCCESS(200, "成功"),
    ERROR(400, "错误"),

    /**
     * 账户问题
     */
    USER_ADMIN(401,"登录认证失败"),
    USER_NOT_EXIST(HttpStatus.UNAUTHORIZED.value(), "该用户名不存在"),
    USER_EXIST(401, "该用户名已经存在"),
    USER_ANONYMOUS(401,"无权访问,请先登录"),
    USER_PWD_NULL(402, "密码不能为空"),
    USER_PWD_ERROR(402, "密码错误"),
    USER_INEQUALITY(403, "两次密码不一致"),
    USER_OLD_PWD_ERROR(404, "原来密码不正确"),
    USER_NAME_PWD_NULL(405, "用户名和密码不能为空"),
    USER_CAPTCHA_ERROR(406, "验证码错误"),


    /**
     * 角色问题
     */
    ROLE_EXIST(401, "该角色标识已经存在，不允许重复！"),

    /**
     * 部门问题
     */
    DEPT_EXIST_USER(401, "部门存在用户，无法删除"),

    /**
     * 字典问题
     */
    DICT_EXIST(401, "该字典标识已经存在，不允许重复！"),

    /**
     * 非法操作
     */
    STATUS_ERROR(401, "非法操作：状态有误"),

    /**
     * 权限问题
     */
    NO_PERMISSIONS(401, "权限不足！"),
    NO_ADMIN_AUTH(500, "不允许操作超级管理员"),
    NO_ADMIN_STATUS(501, "不能修改超级管理员状态"),
    NO_ADMINROLE_AUTH(500, "不允许操作管理员角色"),
    NO_ADMINROLE_STATUS(501, "不能修改管理员角色状态"),

            ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
}
