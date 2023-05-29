package com.gtop.reda.core;

import com.gtop.reda.core.config.RedaDefaultConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javy Hong
 * @Date 2023-05-16 14:55
 */
public class RedaContext {

    private final ApplicationContext context;

    private RedaDefaultConfig defaultConfig;

    private RedaCallHandler redaCallHandler;

    public RedaContext(ApplicationContext context, RedaCallHandler redaCallHandler) {
        this.context = context;
        defaultConfig = new RedaDefaultConfig();
        this.redaCallHandler = redaCallHandler;
    }

    public RedaCallHandler getRemoteCallHandler() {
        return redaCallHandler;
    }

    public RedaDefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }

}
