package com.jadmin.modules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:声明后台url需要记录日志
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {

	/**
	 * 日志的类别
	 * 如果request属性中，有BaseAbstractController.SYS_LOG_TYPE属性，将无视该属性的值
	 * @return
	 */
	String type() default "";
	
    /** 日志内容属性 */
	String[] content() default {};
}
