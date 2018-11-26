package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:jsp和js的配置
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileConfig {
	
	/** 
	 * 需要自定义的jsp，可自定义的jsp有 list、edit
	 * 默认为空，将使用通用的admin/common/*.jsp
	 * 如果声明list为自定义，将使用admin/business/{url}/data-list.jsp页面
	 * 如果声明edit为自定义，将使用admin/business/{url}/data-edit.jsp页面
	 */
	String[] selfJsp() default {};

	/** 定义后将使用该路径作为jsp基础路径，默认路径是admin/business/{url} */
	String jspBaseUrl() default "";
	
	/** edit需要特殊加载的js */
	String editJs() default "";
	
	/** list需要特殊加载的js */
	String listJs() default "";
}
