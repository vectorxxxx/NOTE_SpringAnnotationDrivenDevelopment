package com.vectorx.springannotation.config.import_;

import com.vectorx.springannotation.entity.Blue;
import com.vectorx.springannotation.entity.Green;
import com.vectorx.springannotation.entity.Rainbow;
import com.vectorx.springannotation.entity.Red;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 导入 Bean 定义注册器
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-06-18 18:52:55
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar
{
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean beanDefinition1 = registry.containsBeanDefinition(Red.class.getName());
        boolean beanDefinition2 = registry.containsBeanDefinition(Green.class.getName());
        boolean beanDefinition3 = registry.containsBeanDefinition(Blue.class.getName());
        if (beanDefinition1 && beanDefinition2 && beanDefinition3) {
            BeanDefinition beanDefinition = new RootBeanDefinition(Rainbow.class);
            registry.registerBeanDefinition("rainbow", beanDefinition);
        }
    }
}
