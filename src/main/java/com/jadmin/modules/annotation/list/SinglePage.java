package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:声明后为单个页面，直接跳转到编辑页面，而不是列表页面
 * @Copyright:JAdmin (c) 2018年11月14日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SinglePage {

    /** 单个页面配置属性，值为该vo的主键，可使用关键词：userId, orgId，表示的是当前登陆用户的id和机构id */
	String value() default "";
}
