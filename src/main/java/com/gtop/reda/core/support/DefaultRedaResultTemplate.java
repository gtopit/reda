package com.gtop.reda.core.support;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 16:48
 */
public class DefaultRedaResultTemplate implements RedaResultTemplate {
    @Override
    public Object getData(Object result) {
        JSONObject jsonObject = JSONUtil.parseObj(result);
        Object data = jsonObject.get("data");
        if (data.getClass().getClassLoader() == null) {
            // jdk的类直接返回
            return data;
        }
        // 自定义的类转成json返回
        return JSONUtil.toJsonStr(data);
    }
}
