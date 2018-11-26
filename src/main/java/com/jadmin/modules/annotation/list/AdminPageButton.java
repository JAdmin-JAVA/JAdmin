package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:声明后台管理页面中的按钮
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminPageButton {

    /** 将当前页面跳转到 指定url */
	public static final int JUMP = 1;
    /** 将当期页面通过 layer弹出来 （默认） */
	public static final int LAYER = 2;
    /** ajax异步请求服务器，然后弹出处理结果 */
	public static final int AJAX = 3;

    /** 不选择任何数据 即可触发 （默认） */
	public static final int NO = 1;
    /** 必须选择一个数据 才能触发 */
	public static final int RADIO = 2;
    /** 必须选择一个或多个数据 才能触发 */
	public static final int CHECKBOX = 3;
	
    /** 按钮的名字 */
	String name();
	
    /** 图片文字 */
	String imgChar() default "";
	
    /** 
     * 颜色，目前有五种:</br>
     * primary 提供额外的视觉感, 可在一系列的按钮中指出主要操作</br>
     * secondary 默认样式的替代样式</br>
     * success 表示成功或积极的动作</br>
     * warning 提醒应该谨慎采取这个动作</br>
     * danger 表示这个动作危险或存在危险</br>
     */
	String color() default "primary";
	
	/**
	 * 按钮点击后的处理方式：
	 * 
	 * 1、将当前页面跳转到 指定url
	 * 2、将当前页面通过 layer弹出来 （默认）
	 * 3、ajax异步请求服务器，然后弹出处理结果
	 * 
	 */
	int showType() default LAYER;
	
	/**
	 * 按钮可以设定触发的策略，按钮一定是在列表页面出现的，目前有三种：
	 * 
	 * NO 1、不选择任何数据 即可触发 （默认）
	 * RADIO 2、必须选择一个数据 才能触发 
	 * CHECKBOX 3、必须选择一个或多个数据 才能触发 
	 */
	int dataType() default NO;
	
    /** 弹出框的高度，只有showType为LAYER时生效 */
	int layerHeight() default -1;
	
    /** 是否弹出操作确认提示框，只有showType为AJAX时生效 */
	boolean ajaxConfirm() default true;
	
    /** TODO：此处尚未更新，按钮自定义的href，可以直接写链接，也可以跳转到js对应的方法：javascript:toButton('${baseUrl }/password/to'); */
	String clickCustomHref() default "";
}
