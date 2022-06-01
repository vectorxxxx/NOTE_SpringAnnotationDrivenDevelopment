> 笔记来源：:sparkles:[尚硅谷Spring注解驱动教程(雷丰阳源码级讲解)](https://www.bilibili.com/video/BV1gW411W7wy) 

[TOC]

# Configuration Bean 注册组件

## 1、准备工作

### pom

引入 `spring-context` 依赖

```xml
<dependencies>
    <!-- 核心依赖 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.3.12.RELEASE</version>
    </dependency>
    <!-- 测试用 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

> **Q**：为什么只需要一个 spring-context？
>
> **A**：查看 spring-context 的 pom 文件就一目了然了

查看 `spring-context-4.3.12.RELEASE.pom` 文件内容，其中引入 Spring 核心依赖 `spring-aop`、`spring-beans`、`spring-core`、`spring-expression`，所以引入 `spring-context` 一个依赖足矣

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>4.3.12.RELEASE</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-beans</artifactId>
    <version>4.3.12.RELEASE</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
    <version>4.3.12.RELEASE</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-expression</artifactId>
    <version>4.3.12.RELEASE</version>
    <scope>compile</scope>
</dependency>
```

当然，也可以通过 maven 依赖关系看出

![image-20220530205802384](https://s2.loli.net/2022/05/30/3SYoJLOr2iWUqlp.png)

### 实体类

```java
public class Person
{
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age='" + age + '\'' + '}';
    }
}
```

### 

## 2、@Bean 注解

**xml 方式**

```xml
<bean id="person" class="com.vectorx.springannotation.entity.Person">
    <property name="name" value="zhangsan"></property>
    <property name="age" value="18"></property>
</bean>
```

**注解方式**

```java
// 标识为一个配置类
@Configuration
public class SrpingConfig
{
	/**
     * 相当于 beans.xml 配置文件的 <bean> 标签，可以进行类和属性的注入
     * value 指定 bean 的 id，不写则默认将方法名作为 bean 的 id
     */
    @Bean(value = "person")
    public Person person01(){
        return new Person("zhangsan", 18);
    }
}
```

**注解作用**

- `@Configuration` 将修饰的类标识为一个配置类，相当于 Spring 配置文件
- `@Bean` 将修饰的方法标识为一个 bean，进行类型注入，纳入到 Spring 的 IOC 容器中进行管理。`value` 属性标识 bean 的 id，不指定 `value` 时默认将方法名作为 bean 的 id；方法内部可以进行类的实例化，对属性进行装配

**测试 1**

```java
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
}
```

- 使用 `AnnotationConfigApplicationContext` 可以读取配置类
- 通过 `getBean()` 方法获取到配置类中注入的类。其方法有多个重载方法，主要可以通过 bean 的类型、id 作为参数来获得对应 bean 的实例对象，也可以通过指定需要的类型和 id 搭配更精准地获取所需的 bean
- 通过 `getBeanNamesForType` 可以获取指定类型的所有 bean 的 id 名，返回类型是一个 String 类型数组

**测试结果**

```java
Person{name='zhangsan', age='18'}
Person{name='zhangsan', age='18'}
Person{name='zhangsan', age='18'}
===============
person
```

但是如果配置类中多次注入同一个类型，`getBean` 的重载方法 `getBean(Class<T> requiredType)` 就会执行报错了，测试如下

**测试 2**

```java
@Configuration
public class SrpingConfig
{
    @Bean(value = "person")
    public Person person01(){
        return new Person("zhangsan", 18);
    }

    @Bean(value = "person2")
    public Person person02(){
        return new Person("zhangsan", 18);
    }
}
```

**测试结果**

```java
Person{name='zhangsan', age='18'}

org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.vectorx.springannotation.entity.Person' available: expected single matching bean but found 2: person,person2
    at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveNamedBean(DefaultListableBeanFactory.java:1041)
    at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:345)
    at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:340)
    at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1090)
    at com.vectorx.springannotation.SpringAnnotationTest.testBean(SpringAnnotationTest.java:23) <27 internal lines>
```

很明显，`expected single matching bean but found 2` 期望匹配 1 个但却找到了 2 个同一类型的 bean ，所以使用 `getBean` 方法时需要注意使用场景，选择不同的重载方法防止出现不必要的异常问题



## 3、@ComponentScan 注解

`@ComponentScan` 注解可以指定要扫描的包，与配置文件中 `<context:component-scan>` 标签作用一致

**xml 方式**

```xml
<context:component-scan base-package="com.vectorx.springannotation"></context:component-scan>
```

**注解方式**

```java
// 自动扫描包
@ComponentScan(value = "com.vectorx.springannotation")
@Configuration
public class SrpingConfig
{
    //...
}
```

Controller、Service、Dao 类

```java
@Controller
public class BookController {}
@Service
public class BookService {}
@Repository
public class BookDao {}
```

**测试方法**

```java
@Test
public void testComponentScan(){
    String[] names = context.getBeanDefinitionNames();
    for (String name : names) {
        System.out.println(name);
    }
}
```

`getBeanDefinitionNames()` 方法可以获取 Spring 的 IOC 容器中定义的 bean 的 id 名

**测试结果**

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
bookController
bookDao
bookService
person
```

除了 Spring IOC 容器中自己要装配的组件外，还有我们的配置类 SpringConfig，以及刚刚定义的 Controller、Service、Dao 类和 Person 类

### excludeFilters

**xml 方式**

```xml
<context:component-scan base-package="com.vectorx.springannotation">
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
</context:component-scan>
```

**注解方式**

```java
@ComponentScan(value = "com.vectorx.springannotation", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
})
@Configuration
public class SrpingConfig
{
    //...
}
```

**测试结果**

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
bookDao
person
```

可以看到，bookController 和 bookService 已经不在 Spring 的 IOC 容器中了

### includeFilters

**》》》错误示范**

**xml 方式**

```xml
<context:component-scan base-package="com.vectorx.springannotation">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Service"/>
</context:component-scan>
```

**注解方式**

```java
@ComponentScan(value = "com.vectorx.springannotation", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
})
@Configuration
public class SrpingConfig
{
    //...
}
```

**测试结果**

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
bookController
bookDao
bookService
person
```

> **Q**：发现并没有生效，这是为什么呢？
>
> **A**：因为还有一个 `use-default-filters` 属性要搭配 `include-filter` 属性进行使用。因为 `use-default-filters` 默认的过滤策略为 `true`，即不做任何过滤，所以只使用 `include-filter` 是没有任何效果的

**》》》正确示范**

**xml 方式**

```xml
<context:component-scan base-package="com.vectorx.springannotation" use-default-filters="false">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Service"/>
</context:component-scan>
```

**注解方式**

```java
@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
})
@Configuration
public class SrpingConfig
{
    //...
}
```

**测试结果**

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
bookController
bookService
person
```

可以发现，bookDao 被过滤掉了。同时我们也注意到 Spring IOC 自己的组件没有被过滤，SpringConfig 配置类也没有被过滤

> **Q**：Spring IOC 自己的组件没有被过滤：毕竟是 IOC 容器中基础的、必要的组件，不被过滤能够理解；SpringConfig 配置类也没有被过滤：配置类不会被过滤掉也能理解，过滤掉还得了？person 也没有被过滤掉，这是为什么呢？
>
> **A**：因为自动扫描包只会扫描 `@Controller`、`@Service`、`@Repository`、`@Component`。这个从 *2、@Bean 注解* 一节中就能够想到（那时还没有使用包扫描照样能获取到 bean 对象）

### @ComponentScans

另外，如果阅读 `@ComponentScan` 注解源码可以发现其被 `@Repeatable` 修饰，说明可以重复写多次

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {...}
```

如下

```java
@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})
})
```

不过这个是 jdk8 之后才支持的写法，如果是之前的版本，可以使用 `@ComponentScans` 注解可以达到同样的效果

```java
@ComponentScans(value = {
    @ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
    }),
    @ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})
    })
})
```

### FilterType

上面的例子中，我们学习了对扫描包通过排除或包含的方式进行了过滤

- `excludeFilters=Filter[]`：指定扫描的时候排除哪些组件
- `includeFilters=Filter[]`：指定扫描的时候包含哪些组件

同时我们使用了 `FilterType.ANNOTATION` 即按照注解的方式对扫描的包进行了过滤。这里简单介绍下每种过滤方式

- `FilterType.ANNOTATION`：按照注解过滤
- `FilterType.ASSIGNABLE_TYPE`：按照指定类型过滤
- `FilterType.ASPECTJ`：使用 ASPECTJ 表达式过滤
- `FilterType.REGEX`：使用正则过滤
- `FilterType.CUSTOM`：使用自定义规则过滤

其中 `FilterType.ANNOTATION` 和 `FilterType.ASSIGNABLE_TYPE` 最常用到，而对于 `FilterType.ASPECTJ` 和 `FilterType.REGEX` 稍作了解即可。因为我们已经使用过 `FilterType.ANNOTATION` 了，所以这里着重学习下 `FilterType.ASSIGNABLE_TYPE`（按照类型过滤）和 `FilterType.CUSTOM`（自定义规则）这两种过滤类型

#### 按照类型过滤

```java
@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class})
})
```

测试结果

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
bookService
person
```

#### 自定义规则

自定义规则比较特殊，需要我们自定义实现类。如何自定义实现类呢？我们先看下 `FilterType` 的官方源码注释是如何说明的

```java
public enum FilterType {
    ANNOTATION,
    ASSIGNABLE_TYPE,
    ASPECTJ,
    REGEX,
    /** Filter candidates using a given custom
	 * {@link org.springframework.core.type.filter.TypeFilter} implementation.
	 */
    CUSTOM
}
```

注释告诉我们说需要对 `TypeFilter` 进行实现，话不多说直接上代码

- 定义一个 `MyTypeFilter` 实现 `TypeFilter`

```java
public class MyTypeFilter implements TypeFilter
{
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        return false;
    }
}
```

- 注解中使用我们刚刚定义好的自定义规则类

```java
@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
})
```

先测试一波，看看效果

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
person
```

可以看到，默认的自定义实现（即 `return false`）时，包下所有类全部会过滤掉。接下来我们把注意力着重放在 *自定义规则方法* 上，该如何实现呢？

不知道如何实现没关系，先看下 `TypeFilter` 接口的源码注释

```java
public interface TypeFilter {

	/**
	 * Determine whether this filter matches for the class described by
	 * the given metadata.
	 * @param metadataReader the metadata reader for the target class
	 * @param metadataReaderFactory a factory for obtaining metadata readers
	 * for other classes (such as superclasses and interfaces)
	 * @return whether this filter matches
	 * @throws IOException in case of I/O failure when reading metadata
	 */
	boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException;

}
```

主要看这两句话，说明了 `metadataReader` 和 `metadataReaderFactory` 两个参数的作用

```java
@param metadataReader the metadata reader for the target class
@param metadataReaderFactory a factory for obtaining metadata readers for other classes (such as superclasses and interfaces)
```

翻译过来就是

- `metadataReader`：目标类的元数据读取器
- `metadataReaderFactory`：为其他类（如超类和接口）获取元数据读取器的工厂

如何理解呢？

- `metadataReader`：读取器，可以获取当前正在扫描的类的信息
- `metadataReaderFactory`：读取器的工厂，可以获取其他类的信息

既然如此，我们就先来着重看下 `metadataReader` 中能获取到哪些相关信息

```java
public class MyTypeFilter implements TypeFilter
{
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        // 获取当前正在扫描的类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前正在扫描的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前正在扫描的类的资源信息（如类的路径）
        Resource resource = metadataReader.getResource();

        // 主要看下当前类的相关信息
        String className = classMetadata.getClassName();
        System.out.println(className);
        return false;
    }
}
```

测试结果

```java
com.vectorx.springannotation.SpringAnnotationTest
com.vectorx.springannotation.controller.BookController
com.vectorx.springannotation.dao.BookDao
com.vectorx.springannotation.entity.Person
com.vectorx.springannotation.filter.MyTypeFilter
com.vectorx.springannotation.service.BookService
```

到这里是不是就一目了然了，既然 `return false` 是会被过滤掉的，那只要符合自定义规则时 `return true` 不就可以了嘛

```java
public class MyTypeFilter implements TypeFilter
{
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        String className = classMetadata.getClassName();
        if (className.contains("er")) {
            return true;
        }
        return false;
    }
}
```

测试结果

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
bookController
person
myTypeFilter
bookService
```

可以看到 `bookController`、`myTypeFilter`、`bookService` 包含 `er` 所以这几个都被打印了出来，而 `bookDao` 不满足条件所以没被打印 

> **注意**：`TypeFilter` 实现类中并不是说，“返回 `false` 就会被排除，返回 `true` 就会被包含”（并非如此）
>
> 只是因为我们使用了 `includeFilters` 属性，所以返回 `true` 的类都会作为 `includeFilters` 属性的值
>
> 因此，如果我们使用 `excludeFilters` 属性，结果则会刚好相反

使用 `excludeFilters` 属性自定义规则

```java
@ComponentScan(value = "com.vectorx.springannotation", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
})
```

测试结果

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
srpingConfig
bookDao
person
```

可以看到， `bookController`、`myTypeFilter`、`bookService` 包含 `er` 所以这几个都被过滤了，而 `bookDao` 不满足条件所以被打印出来了

