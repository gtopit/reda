package com.gtop.reda.core.support;

/**
 * 功能：对外部接口返回对象在返回前端前做修改
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 10:22
 */
public interface RedaResultDecorator<T> {

    /**
     * 对外部接口返回对象在返回前端前做最后的装饰（值修改）
     * @param result
     * @return
     */
    T decorate(T result);

}
