package com.gtop.reda.core.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Javy Hong
 * @Date 2023-05-29 12:17
 */
public class RedaImportSelector implements ImportSelector {

    RedaImportSelector() {
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {"com.gtop.reda.core.config.RedaConfiguration"};
    }
}
