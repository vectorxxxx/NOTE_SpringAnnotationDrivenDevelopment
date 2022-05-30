package com.vectorx.springannotation;

import com.vectorx.springannotation.config.SrpingConfig;
import com.vectorx.springannotation.entity.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试类
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-05-30 20:41:46
 */
public class SpringAnnotationTest
{
    private ApplicationContext context;

    @Before
    public void initContext(){
        context = new AnnotationConfigApplicationContext(SrpingConfig.class);
    }

    @Test
    public void testBean() {
        Person person1 = (Person) context.getBean("person");
        System.out.println(person1);
        Person person2 = context.getBean(Person.class);
        System.out.println(person2);
        Person person3 = context.getBean("person", Person.class);
        System.out.println(person3);
        System.out.println("===============");
        String[] names = context.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testComponentScan(){
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
