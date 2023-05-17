package com.gtop.reda.core.support;

import lombok.Builder;
import lombok.Getter;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 9:44
 */
@Builder
public class RedaResult {
    @Getter
    private String meId;
    @Getter
    private Object data;
}
