package com.web.dao.model.common;

import javafx.beans.DefaultProperty;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.dao.model.common
 * @date 2021/2/5/005 10:44
 */
public class ValidatorErrorType {

    private ValidatorErrorType() {}
    /**
     * INVALID
     */
    public static final String INVALID = "无效字段";
    public static final String TYPE_MISMATCH = "参数类型不匹配";
    public static final String MISSING_FIELD = "缺少必填字段";
    public static final String METHOD_PARAMETER_FIELD = "方法参数校验不通过";
    public static final String MODEL_PARAMETER_FIELD = "实体类属性校验不通过";

}
