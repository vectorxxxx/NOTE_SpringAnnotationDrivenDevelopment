package com.vectorx.springannotation;

import com.vectorx.springannotation.config.SpringConfig2;
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
public class SpringAnnotationTest2
{
    private ApplicationContext context;

    @Before
    public void initContext(){
        context = new AnnotationConfigApplicationContext(SpringConfig2.class);
        System.out.println("IOC 容器创建完成...");
    }

    @Test
    public void testBean() {
        Person person1 = context.getBean("person", Person.class);
        Person person2 = context.getBean("person", Person.class);
        System.out.println(person1 == person2); // true
    }
}
