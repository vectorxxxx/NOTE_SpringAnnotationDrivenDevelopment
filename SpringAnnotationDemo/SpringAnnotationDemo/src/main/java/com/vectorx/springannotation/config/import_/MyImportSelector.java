package com.vectorx.springannotation.config.import_;

import com.vectorx.springannotation.entity.Blue;
import com.vectorx.springannotation.entity.Green;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 导入选择器
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-06-18 18:37:16
 */
public class MyImportSelector implements ImportSelector
{
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {Green.class.getName(), Blue.class.getName()};
    }
}
