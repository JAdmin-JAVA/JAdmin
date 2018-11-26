package com.jadmin.modules.annotation.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jadmin.vo.enumtype.JavaType;

/** 
 * @Title:web框架
 * @Description:声明该注解之后，只有初始化vo的时候会设置值
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) 
public @interface InitDefaultColunm{
	
    /** 初始化的值 属性名为operateTime、operatorId时，可以设置值 */
	String value() default "INITDEFAULTCOLUNM";

    /** 字段 */
	String column() default "";

    /** Java类型，默认是String类型，声明后，会转换为其他类型 */
	JavaType javaType() default JavaType.String;
	
    /** 如果是系统配置里面的值，设置改字段 */
	String sysConfigCode() default "";
	
    /** 是否通过get方法初始化值 */
	boolean methodGet() default false;
}
