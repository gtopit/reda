package com.gtop.reda.core;

import com.gtop.reda.core.support.RedaDefinition;
import com.gtop.reda.core.support.RedaResult;

import java.util.List;

/**
 * 远程调用返回的结果惊醒处理类
 * @author Javy Hong
 * @Date 2023-05-17 16:01
 */
public interface RedaResultHandler {

    Object handleResult(List<RedaResult> redaResults, RedaDefinition definition);

}
