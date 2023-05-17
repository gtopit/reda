package com.gtop.reda.core.config;

import com.gtop.reda.core.RedaContext;
import com.gtop.reda.core.RedaCallHandler;
import com.gtop.reda.core.RedaInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-16 9:09
 */
@Configuration
public class RedaConfiguration implements WebMvcConfigurer {

    @Resource
    private ApplicationContext applicationContext;

    @Value("${spring.profiles.active:local}")
    private String env;

    @Value("${reda.prefix.path:/rd/**}")
    private String rdPath;

    @Value("${reda.formal.env:prod}")
    private String formalEnv;

    @Value("${reda.remoteCall.handler:com.gtop.reda.core.support.DefaultRedaCallHandler}")
    private String remoteCallHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createRemoteDataInterceptor()).addPathPatterns(rdPath);
    }

    private HandlerInterceptor createRemoteDataInterceptor() {
        RedaInterceptor interceptor = null;
        try {
            RedaContext context = new RedaContext(applicationContext, (RedaCallHandler) Class.forName(remoteCallHandler).newInstance());
            setRedaContextDefaultConfig(context);
            interceptor = new RedaInterceptor(context);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return interceptor;
    }

    private void setRedaContextDefaultConfig(RedaContext context) {
        RedaDefaultConfig config = context.getDefaultConfig();
        config.setCurEnv(env);
        config.setFormalEnv(formalEnv);
    }

}
