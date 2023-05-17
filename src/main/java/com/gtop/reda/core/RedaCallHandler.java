package com.gtop.reda.core;

import com.gtop.reda.core.support.RedaDefinition;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-16 16:31
 */
public interface RedaCallHandler {

    Object invokeGet(RedaDefinition info);

    Object invokePost(RedaDefinition info);

}
