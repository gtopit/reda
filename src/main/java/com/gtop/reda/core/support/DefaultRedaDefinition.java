package com.gtop.reda.core.support;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Javy Hong
 * @Date 2023-05-17 11:10
 */
public class DefaultRedaDefinition implements RedaDefinition {

    private RedaUri[] redaUris;

    private RedaResultDecorator decorator;

    private Map<String, Object> parameters;

    private RedaMethod redaMethod;

    private Class<?> finalResultClass;

    private Method method;

    private RedaResultTemplate resultTemplate;

    public void setResultTemplate(Class<? extends RedaResultTemplate> resultTemplate) {
        if (resultTemplate.isInterface()) {
            this.resultTemplate = new DefaultRedaResultTemplate();
        } else {
            try {
                this.resultTemplate = resultTemplate.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void setControllerMethod(Method method) {
        this.method = method;
    }

    public void setFinalResultClass(Class<?> finalResultClass) {
        this.finalResultClass = finalResultClass;
    }

    public void setRedaMethod(RedaMethod redaMethod) {
        this.redaMethod = redaMethod;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void setDecorator(Class<? extends RedaResultDecorator> decorator) {
        try {
            if (!decorator.isInterface()) {
                this.decorator = decorator.newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setRedaUris(RedaUri[] redaUris) {
        this.redaUris = redaUris;
    }

    @Override
    public boolean isMultiRequest() {
        return redaUris != null && redaUris.length == 1;
    }

    @Override
    public RedaUri[] getRedaUris() {
        return redaUris;
    }

    @Override
    public RedaResultDecorator getDecorator() {
        return decorator;
    }

    @Override
    public Map<String, Object> getRedaParameters() {
        return parameters;
    }

    @Override
    public RedaMethod getRedaMethod() {
        return redaMethod;
    }

    @Override
    public Method getControllerMethod() {
        return method;
    }

    @Override
    public Class<?> getFinalResultClass() {
        return finalResultClass;
    }

    @Override
    public RedaResultTemplate getResultTemplate() {
        return resultTemplate;
    }

}
