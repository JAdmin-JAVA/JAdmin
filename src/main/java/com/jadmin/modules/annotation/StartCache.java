package com.jadmin.modules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:初始化的缓存
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartCache {

    /** sql或者hql */
	String value() default "";

    /** 默认智能识别，可人工填写，sql或hql */
	String type() default "";
	
    /** 是否通过方法初始化值 */
	boolean methodInit() default false;
}
