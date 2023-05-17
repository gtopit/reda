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
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-16 14:55
 */
public class RedaContext implements ApplicationContext {

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

    @Override
    public String getId() {
        return context.getId();
    }

    @Override
    public String getApplicationName() {
        return context.getApplicationName();
    }

    @Override
    public String getDisplayName() {
        return context.getDisplayName();
    }

    @Override
    public long getStartupDate() {
        return context.getStartupDate();
    }

    @Override
    public ApplicationContext getParent() {
        return context.getParent();
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return context.getAutowireCapableBeanFactory();
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return context.getParentBeanFactory();
    }

    @Override
    public boolean containsLocalBean(String name) {
        return context.containsBean(name);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return context.containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return context.getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(ResolvableType type) {
        return context.getBeanNamesForType(type);
    }

    @Override
    public String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit) {
        return context.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return context.getBeanNamesForType(type);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return context.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return context.getBeansOfType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return context.getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return context.getBeanNamesForAnnotation(annotationType);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return context.getBeansWithAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
        return context.findAnnotationOnBean(beanName, annotationType);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return context.getBean(name, requiredType);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return context.getBean(name, args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return context.getBean(requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return context.getBean(requiredType, args);
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
        return context.getBeanProvider(requiredType);
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
        return context.getBeanProvider(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return context.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return context.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return context.isPrototype(name);
    }

    @Override
    public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return context.isTypeMatch(name, typeToMatch);
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return context.isTypeMatch(name, typeToMatch);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return context.getType(name);
    }

    @Override
    public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
        return context.getType(name, allowFactoryBeanInit);
    }

    @Override
    public String[] getAliases(String name) {
        return context.getAliases(name);
    }

    @Override
    public void publishEvent(Object event) {
        context.publishEvent(event);
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return context.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return context.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return context.getMessage(resolvable, locale);
    }

    @Override
    public Environment getEnvironment() {
        return context.getEnvironment();
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return context.getResources(locationPattern);
    }

    @Override
    public Resource getResource(String location) {
        return context.getResource(location);
    }

    @Override
    public ClassLoader getClassLoader() {
        return context.getClassLoader();
    }

}
