package com.gtop.reda.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.json.JSONUtil;
import com.gtop.reda.core.annotation.RdParam;
import com.gtop.reda.core.annotation.RemoteData;
import com.gtop.reda.core.config.RedaDefaultConfig;
import com.gtop.reda.core.exception.RedaParameterException;
import com.gtop.reda.core.support.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务核心类
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-16 9:05
 */
public class RedaInterceptor implements HandlerInterceptor, RedaResolver {

    private ThreadLocal<RedaDefinition> dbCache = new ThreadLocal<>();

    private RedaContext context;

    public RedaInterceptor(RedaContext context) {
        this.context = context;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 解析handler方法上的RemoteData注解，生成RemoteDataDefinition
        parseRemoteData(handler);
        // 判断是否需要执行远程调用
        if (isRemoteData()) {
            // 远程调用前的参数设置
            setRemoteCallParameter(request);
            // 远程调用
            Object result = remoteCall();
            // 远程调用后，对返回结果的后续处理
            Object finalResult = postRedaResult(result);
            // 结果返回
            responseRemoteData(finalResult, response);
            return false;
        }
        return true;
    }

    private void responseRemoteData(Object finalResult, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(finalResult));
        writer.flush();
        writer.close();
    }

    private Object postRedaResult(Object result) {
        RedaDefinition info = dbCache.get();
        DefaultRedaDefinition definition = (DefaultRedaDefinition) info;
        Object finalResult = result;
        if (definition.getDecorator() != null) {
            RedaResultDecorator decorator = definition.getDecorator();
            finalResult = decorator.decorate(result);
        }
        return finalResult;
    }

    private Object remoteCall() {
        RedaCallHandler redaCallHandler = context.getRemoteCallHandler();
        Object result;
        RedaDefinition definition = dbCache.get();
        RedaMethod redaMethod = definition.getRedaMethod();
        if (redaMethod.equals(RedaMethod.GET)) {
            result = redaCallHandler.invokeGet(definition);
        } else {
            result = redaCallHandler.invokePost(definition);
        }
        return result;
    }

    private void setRemoteCallParameter(HttpServletRequest request) {
        RedaDefinition info = dbCache.get();
        Method method = info.getControllerMethod();
        if (method.getParameterCount() > 0) {
            try {
                Class<?> aClass = Class.forName(TypeUtil.getParamTypes(method)[0].getTypeName());
                getFieldsAndSetParameter(request, aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void getFieldsAndSetParameter(HttpServletRequest request, Class<?> aClass) {
        Map<String, Object> parameters = dbCache.get().getRedaParameters();
        Field[] fields = aClass.getDeclaredFields();

        for (Field field : fields) {
            RdParam param = field.getAnnotation(RdParam.class);
            String[] value = request.getParameterMap().get(field.getName());
            if (value != null && !StrUtil.isBlankIfStr(value[0])) {
                if (param == null || param.value().isEmpty()) {
                    parameters.put(field.getName(),  value[0]);
                } else {
                    parameters.put(param.value(), value[0]);
                }
            }
        }

        if (aClass.getSuperclass() != null && !aClass.getSuperclass().equals(Object.class)) {
            getFieldsAndSetParameter(request, aClass.getSuperclass());
        }
    }

    private boolean isRemoteData() {
        RedaDefaultConfig defaultConfig = context.getDefaultConfig();
        RedaDefinition info = dbCache.get();
        if (defaultConfig.getCurEnv().equals(defaultConfig.getFormalEnv()) && info != null && info.getRedaUris().length > 0) {
            return true;
        }
        return false;
    }

    private void parseRemoteData(Object handler) {
        DefaultRedaDefinition definition = new DefaultRedaDefinition();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RemoteData remoteData = method.getAnnotation(RemoteData.class);
        if (remoteData != null) {
            definition = new DefaultRedaDefinition();
            definition.setDecorator(remoteData.resultDecorator());
            if (!remoteData.customUris().isInterface()) {
                definition.setRedaUris(resolveUris(remoteData.customUris()));
            } else if (remoteData.uris().length > 0) {
                definition.setRedaUris(resolveUris(remoteData.uris()));
            }
            definition.setControllerMethod(method);
            definition.setRedaMethod(remoteData.method());
            definition.setParameters(new HashMap<>());
            definition.setFinalResultClass(resolveFinalResultClass(method));
            definition.setResultTemplate(remoteData.resultTemplate());
            dbCache.set(definition);
        }
    }

    @Override
    public RedaUri[] resolveUris(String[] uris) {
        RedaUri[] redaUris = new RedaUri[uris.length];
        doResolveUris(redaUris, uris);
        return redaUris;
    }

    private void doResolveUris(RedaUri[] redaUris, String[] uris) {
        for (int i = 0; i < uris.length; i++) {
            String[] uri = uris[i].split("@");
            if (uri.length == 3) {
                redaUris[i] = RedaUri.builder().meId(uri[1]).uri(uri[2]).build();
            } else if (uri.length == 1) {
                redaUris[i] = RedaUri.builder().uri(uri[0]).build();
            } else {
                throw new RedaParameterException("@RemoteData注解的customUris实现类返回值或uris不符合规则，应该为http://reda/rd/get/fooCount或meId@1@http://reda/rd/get/fooCount");
            }
        }
    }

    @Override
    public RedaUri[] resolveUris(Class<? extends CustomUris> customUrisClass) {
        RedaUri[] redaUris = null;
        try {
            String[] uris = customUrisClass.newInstance().getUris();
            redaUris = new RedaUri[uris.length];
            doResolveUris(redaUris, uris);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return redaUris;
    }

    @Override
    public Class<?> resolveFinalResultClass(Method method) {
        return getActualResultClass(method.getGenericReturnType());
    }

    private Class<?> getActualResultClass(Type genericReturnType) {
        Class<?> retClass;
        if (genericReturnType instanceof ParameterizedType) {
            retClass = getActualResultClass(((ParameterizedType)genericReturnType).getActualTypeArguments()[0]);
        } else {
            retClass = (Class<?>) genericReturnType;
        }
        return retClass;
    }
}
