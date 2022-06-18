package com.vectorx.springannotation.config.bean;

import com.vectorx.springannotation.entity.Person;
import com.vectorx.springannotation.filter.MyTypeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * 配置类
 *
 * @author vectorx
 * @version 1.0
 * @date 2022-05-30 20:42:40
 */
// 自动扫描包
//@ComponentScan(value = "com.vectorx.springannotation")
//@ComponentScan(value = "com.vectorx.springannotation", excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
//})
//=====================================================
//@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//})
//@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})
//})
//=====================================================
//@ComponentScans(value = {
//    @ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//    }),
//    @ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})
//    })
//})
//=====================================================
//@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
//    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class})
//})
//=====================================================
//@ComponentScan(value = "com.vectorx.springannotation", useDefaultFilters = false, includeFilters = {
//    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
//})
@ComponentScan(value = "com.vectorx.springannotation", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})})
// 标识为一个配置类
@Configuration
public class SpringConfig
{
    /**
     * 相当于 bean.xml 配置文件的 <bean> 标签，可以进行类和属性的注入
     * value 指定注册类的 id，不写则默认将方法名作为 bean 的 id
     *
     * @param
     * @return com.vectorx.springannotation.entity.Person
     * @throws
     * @author vectorx
     */
    @Bean(value = "person")
    public Person person01() {
        return new Person("zhangsan", 18);
    }

    //@Bean(value = "person2")
    //public Person person02(){
    //    return new Person("zhangsan", 18);
    //}
}
