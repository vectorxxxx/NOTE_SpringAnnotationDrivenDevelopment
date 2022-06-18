package com.vectorx.springannotation.import_;

import com.vectorx.springannotation.config.import_.SpringConfig4;
import com.vectorx.springannotation.entity.*;
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
public class SpringAnnotationTest4
{
    private ApplicationContext context;

    @Before
    public void initContext() {
        context = new AnnotationConfigApplicationContext(SpringConfig4.class);
    }

    @Test
    public void testImport() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("===============");
        Red red = context.getBean(Red.class.getName(), Red.class);
        Yellow yellow = context.getBean(Yellow.class.getName(), Yellow.class);
        Green green = context.getBean(Green.class.getName(), Green.class);
        Blue blue = context.getBean(Blue.class.getName(), Blue.class);
        Rainbow rainbow = context.getBean("rainbow", Rainbow.class);
        System.out.println(red);
        System.out.println(yellow);
        System.out.println(green);
        System.out.println(blue);
        System.out.println(rainbow);
    }
}
