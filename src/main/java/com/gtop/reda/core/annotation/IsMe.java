package com.gtop.reda.core.annotation;

import java.lang.annotation.*;

/**
 * 1、存在返回的数据映射到内部类的情况，此时注解用于指明使用哪个内部类接收返回的数据
 * 如：
 * public class A {
 *      @IsMe
 *      private Record record;
 *      private Integer count;
 * }
 * 返回的数据将与record映射
 *
 * 2、当多个远程调用存在时，返回的多个远程调用结果需要指明接收数据的内部类
 * 如：
 * public class MultiR {
 *     @IsMe("1")
 *     private Integer fooCount;
 *     @IsMe("2")
 *     private Integer barCount;
 * }
 * controller层的请求方法上的注解@RemoteData(uris = {"meId@1@http://reda/rd/get/fooCount", "meId@2@http://reda/rd/get/barCount"})
 * 其中每个uri两个@中间的值等于@IsMe注解中的id值
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-16 18:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsMe {
    String id() default "";
}
