package com.gtop.reda.test;

import com.gtop.reda.core.support.RedaResultDecorator;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 18:49
 */
public class MyDecorator implements RedaResultDecorator<CountRes> {
    @Override
    public CountRes decorate(CountRes result) {
        result.setTotal(result.getCount1() + result.getCount2());
        return result;
    }
}
