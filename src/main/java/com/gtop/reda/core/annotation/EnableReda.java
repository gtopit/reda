package com.gtop.reda.core.annotation;

import com.gtop.reda.core.config.RedaImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动开关
 * @author Javy Hong
 * @Date 2023-05-29 12:11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedaImportSelector.class)
public @interface EnableReda {
}
