package com.jadmin.modules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jadmin.vo.enumtype.AdminPageMenu;

/** 
 * @Title:web框架
 * @Description:声明后台管理页面
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminPage {

    /** 页面的名字 */
	String name();
	
    /** 页面所属的菜单，目前只支持二级菜单 */
	AdminPageMenu menu();
	
    /** 该页面是否是菜单唯一的页面，如果是，将不显示页面列表，点击菜单后，直接显示该页面 */
	boolean only() default false;

    /** 弹出到新的标签，如果为true，浏览器将打开新的标签，显示该url */
	boolean targetBlank() default false;

    /** 是否隐藏 */
	boolean hide() default false;
	
    /** 自动刷新页面，单位为秒，目前只有当前页面为通用list页面时才生效 */
	String refreshTime() default "";
}
