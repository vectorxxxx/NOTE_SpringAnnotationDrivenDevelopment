> 笔记来源：:sparkles:[尚硅谷Spring注解驱动教程(雷丰阳源码级讲解)](https://www.bilibili.com/video/BV1gW411W7wy) 

[TOC]

# Import 注册组件

## 1、@Import

- 1）包扫描+组件标注注解（`@Controller`/`@Service`/`@Repository`/`@Component`）【局限于自己编写的组件】
- 2）`@Bean`【导入的第三方包中的组件】
- 3）`@Import`【快速给容器中导入一个组件】
  - 1）`@Import(要导入到容器中的组件)`：容器中会自动注册这个组件，id 默认是全类名
  - 2）`ImportSelector`：返回需要导入的组件的全类名数组
  - 3）`ImportBeanDefinitionRegistrar`：手动注册 Bean 到容器中

### Import 注解源码解析

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {
    /**
	 * {@link Configuration}, {@link ImportSelector}, {@link ImportBeanDefinitionRegistrar}
	 * or regular component classes to import.
	 */
	Class<?>[] value();
}
```

`@Import` 只能作用于类上，可以传入“一组” Class 类

- 可以是需要导入到容器中的组件 `Bean`
- 可以是 `ImportSelector` 实现类
- 可以是 `ImportBeanDefinitionRegistrar` 实现类

### ImportSelector 接口源码解析

```java
public interface ImportSelector {
	String[] selectImports(AnnotationMetadata importingClassMetadata);
}
```

其中定义一个 `selectImports` 方法，提供了一个入参

- `AnnotationMetadata`：可以获取当前类的注解信息
  - `getAnnotationTypes`：获取注解类型集合
  - `getMetaAnnotationTypes`：获取元注解类型集合
  - `hasAnnotation`：是否有某个注解
  - `hasMetaAnnotation`：是否有某个元注解
  - `hasAnnotatedMethods`：是否有注解方法
  - `getAnnotatedMethods`：获取注解方法集合

```java
public interface AnnotationMetadata extends ClassMetadata, AnnotatedTypeMetadata {
	Set<String> getAnnotationTypes();

	Set<String> getMetaAnnotationTypes(String annotationName);

	boolean hasAnnotation(String annotationName);

	boolean hasMetaAnnotation(String metaAnnotationName);

	boolean hasAnnotatedMethods(String annotationName);

	Set<MethodMetadata> getAnnotatedMethods(String annotationName);
}
```

### ImportBeanDefinitionRegistrar 接口源码解析

```java
public interface ImportBeanDefinitionRegistrar {
	public void registerBeanDefinitions(
			AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry);

}
```

其中定义一个 `registerBeanDefinitions` 方法，提供了两个入参

- `AnnotationMetadata`：可以获取当前类的注解信息
- `BeanDefinitionRegistry`：可以向容器中注册一个 Bean
  - `registerBeanDefinition`：注册组件
  - `removeBeanDefinition`：移除组件
  - `getBeanDefinition`：获取组件
  - `containsBeanDefinition`：是否包含组件
  - `getBeanDefinitionNames`：获取组件名
  - `getBeanDefinitionCount`：获取组件数
  - `isBeanNameInUse`：组件是否使用

```java
public interface BeanDefinitionRegistry extends AliasRegistry {
    void registerBeanDefinition(String var1, BeanDefinition var2) throws BeanDefinitionStoreException;

    void removeBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

    BeanDefinition getBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

    boolean containsBeanDefinition(String var1);

    String[] getBeanDefinitionNames();

    int getBeanDefinitionCount();

    boolean isBeanNameInUse(String var1);
}
```



## 2、测试

**实体类**

```java
public class Red {}
public class Yellow {}
public class Green {}
public class Blue {}
public class Rainbow {}
```

**配置类**

```java
@Configuration
@Import({Red.class, Yellow.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class SpringConfig4 {}
```

自定义导入选择器

```java

public class MyImportSelector implements ImportSelector
{
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {Green.class.getName(), Blue.class.getName()};
    }
}
```

自定义导入 Bean 定义注册器

```java
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
```

**测试类**

```java
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
```

**测试结果**

```
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfig4
com.vectorx.springannotation.entity.Red
com.vectorx.springannotation.entity.Yellow
com.vectorx.springannotation.entity.Green
com.vectorx.springannotation.entity.Blue
rainbow
===============
com.vectorx.springannotation.entity.Red@ca263c2
com.vectorx.springannotation.entity.Yellow@589b3632
com.vectorx.springannotation.entity.Green@45f45fa1
com.vectorx.springannotation.entity.Blue@4c6e276e
com.vectorx.springannotation.entity.Rainbow@534df152
```

