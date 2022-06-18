package com.vectorx.springannotation.config.bean;

import com.vectorx.springannotation.condition.LinuxCondition;
import com.vectorx.springannotation.condition.WindowsCondition;
import com.vectorx.springannotation.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * SpringConfig2
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-06-06 21:11:04
 */
@Configuration
public class SpringConfig3
{
    @Bean("person")
    public Person person() {
        return new Person("wangwu", 26);
    }

    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01() {
        return new Person("Bill Gates", 67);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02() {
        return new Person("Linus", 53);
    }
}
