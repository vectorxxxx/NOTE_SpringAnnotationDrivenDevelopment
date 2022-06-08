package com.vectorx.springannotation;

import com.vectorx.springannotation.config.SpringConfig3;
import com.vectorx.springannotation.entity.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * 测试类
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-05-30 20:41:46
 */
public class SpringAnnotationTest3
{
    private ApplicationContext context;

    @Before
    public void initContext() {
        context = new AnnotationConfigApplicationContext(SpringConfig3.class);
    }

    @Test
    public void testBean() {
        Environment environment = context.getEnvironment();
        String osName = environment.getProperty("os.name");
        System.out.println("os.name=" + osName);
        System.out.println("==============");
        String[] names = context.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("==============");
        Map<String, Person> beans = context.getBeansOfType(Person.class);
        for (Map.Entry<String, Person> entry : beans.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
