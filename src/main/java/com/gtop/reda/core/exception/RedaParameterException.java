package com.gtop.reda.core.exception;

import javax.management.RuntimeMBeanException;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-15 11:31
 */
public class RedaParameterException extends RedaException {

    public RedaParameterException(String message) {
        super(new RuntimeException(message));
    }

}
