package com.gtop.reda.core.annotation;

import com.gtop.reda.core.support.*;

import java.lang.annotation.*;

/**
 * @author Javy Hong
 * @Date 2023-05-11 23:06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteData {

    Class<? extends CustomUris> customUris() default CustomUris.class;

    /**
     * 如果存在多个远程调用的情况，uri需要遵循约定
     * 形式为meId@xxx@http://xxx/xxx...
     * 如：@RemoteData(uris = {"meId@1@http://reda/rd/get/fooCount", "meId@2@http://reda/rd/get/barCount"})
     * 其中每个uri两个@中间的值等于@IsMe注解中的id值
     * @return
     */
    String[] uris() default {};

    Class<? extends RedaResultDecorator> resultDecorator() default RedaResultDecorator.class;

    RedaMethod method() default RedaMethod.GET;

    Class<? extends RedaResultTemplate> resultTemplate() default RedaResultTemplate.class;

}
