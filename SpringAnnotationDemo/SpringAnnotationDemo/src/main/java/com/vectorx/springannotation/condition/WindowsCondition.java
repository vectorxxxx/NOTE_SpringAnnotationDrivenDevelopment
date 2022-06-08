package com.vectorx.springannotation.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * WindowsCondition
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-06-08 20:05:53
 */
public class WindowsCondition implements Condition
{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        // 判断是否是 Windows 系统
        String osName = environment.getProperty("os.name");
        return osName.toLowerCase().contains("windows");
    }
}
