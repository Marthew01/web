package com.web.common.model.validator;

import lombok.Data;

import java.util.Map;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.common.model.validator
 * @date 2021/2/5/005 10:07
 */
@Data
public class FieldValidatorError {

    private String type;
    private String field;
    private String message;

}
