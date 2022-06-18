package com.vectorx.springannotation.config.bean;

import com.vectorx.springannotation.entity.Person;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * SpringConfig2
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-06-06 21:11:04
 */
@Configuration
public class SpringConfig2
{
    @Lazy
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean("person")
    public Person person() {
        System.out.println("给 IOC 容器中添加 Person ...");
        return new Person("lisi", 25);
    }
}
