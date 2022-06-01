package com.vectorx.springannotation.filter;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义规则类
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-06-01 21:24:20
 */
public class MyTypeFilter implements TypeFilter
{
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        // 获取当前正在扫描的类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前正在扫描的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前正在扫描的类的资源信息（如类的路径）
        Resource resource = metadataReader.getResource();

        // 主要看下类信息相关
        String className = classMetadata.getClassName();
        //System.out.println(className);
        // 随便定义一个规则
        if (className.contains("er")) {
            return true;
        }
        return false;
    }
}
