package com.gtop.reda.test;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 18:31
 */
public class TestDemo {

    public static void main(String[] args) {
        String str = "{\"code\": 200, \"name\": \"faker\", \"car\": {\"type\": \"BWM\"}, \"money\":\"13.2\"}";
        JSONObject entries = JSONUtil.parseObj(str);
        Object code = entries.get("code");
        System.out.println(code.getClass().getClassLoader() == null);
        Object name = entries.get("name");
        System.out.println(name.getClass().getClassLoader() == null);
        Object car = entries.get("car");
        System.out.println(car.getClass().getClassLoader() == null);
    }
}
