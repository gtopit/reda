package com.gtop.reda.core.support;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Javy Hong
 * @Date 2023-05-17 9:52
 */
public interface RedaDefinition {

    boolean isMultiRequest();

    RedaUri[] getRedaUris();

    RedaResultDecorator getDecorator();

    Map<String, Object> getRedaParameters();

    default void refresh() {};

    RedaMethod getRedaMethod();

    Method getControllerMethod();

    Class<?> getFinalResultClass();

    RedaResultTemplate getResultTemplate();

}
