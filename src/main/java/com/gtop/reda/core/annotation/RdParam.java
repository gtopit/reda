package com.gtop.reda.core.annotation;

import java.lang.annotation.*;

/**
 * 有时候，接口需要先出，再和数据治理平台对接，请求参数可能我们内部的和他们的不一致，可以通过本注解解耦
 * eg.,  我们前端对接的参数outType和数据治理平台的type不一致，就可以使用@RdParam("type")
 * public class OutDTO {
 *     private String name;
 *     @RdParam("type")
 *     private Integer outType;
 * }
 * @author Javy Hong
 * @Date 2023-05-16 16:01
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RdParam {

    String value() default "";

}
