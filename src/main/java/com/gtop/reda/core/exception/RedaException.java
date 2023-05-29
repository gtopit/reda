package com.gtop.reda.core.exception;

import javax.management.RuntimeMBeanException;

/**
 * @author Javy Hong
 * @Date 2023-05-17 14:06
 */
public class RedaException  extends RuntimeMBeanException {


    public RedaException(RuntimeException e, String message) {
        super(e, message);
    }

    public RedaException(RuntimeException e) {
        super(e);
    }

}
