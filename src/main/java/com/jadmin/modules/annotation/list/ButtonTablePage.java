package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:按钮要跳转的列表页面的注解
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ButtonTablePage {

    /** 声明是哪个页面上的按钮 */
	Class<?> page();
	
    /** 是否是编辑页面的表格 */
	boolean inEdit() default false;
	
    /** 按钮的名字 */
	String name();
	
    /** 給据url中传过来的id，通过该字段过滤 */
	String idColunm() default "";
	
	/** 按钮的图片文字 */
	String imgChar() default "";
	
    /** 颜色，目前只有red和green */
	String color() default "green";

    /** 是否隐藏 */
	boolean hide() default false;
}
