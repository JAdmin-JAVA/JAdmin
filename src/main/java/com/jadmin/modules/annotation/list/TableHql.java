package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:列表界面的where条件和 排序
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableHql {
	
	/** 列表界面的where条件 */
	String value() default "";
	
	/** 列表界面排序 */
	String orderBy() default "";

	/** 定义分页的大小 */
	int pageSize() default -1;

}
