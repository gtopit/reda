package com.gtop.reda.core;

import com.gtop.reda.core.support.CustomUris;
import com.gtop.reda.core.support.RedaUri;

import java.lang.reflect.Method;

/**
 * @author Javy Hong
 * @Date 2023-05-17 12:41
 */
public interface RedaResolver {

    default RedaUri[] resolveUris(String[] uris) {
        return new RedaUri[0];
    }

    default RedaUri[] resolveUris(Class<? extends CustomUris> customUrisClass) {
        return new RedaUri[0];
    }

    default Class<?> resolveFinalResultClass(Method method) {
        return null;
    }

}
