package com.vectorx.springannotation.config.import_;

import com.vectorx.springannotation.entity.Red;
import com.vectorx.springannotation.entity.Yellow;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * SpringConfig4
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-06-18 18:07:42
 */
@Configuration
@Import({Red.class, Yellow.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class SpringConfig4
{
}
