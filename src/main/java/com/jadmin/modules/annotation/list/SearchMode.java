package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:搜索策略
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchMode {

	/** 搜索的模式 1-精简搜索，2-复杂搜索 */
	public final int EASY_TYPE = 1;
	public final int COMPLEX_TYPE = 2;

	/** 搜索的模式  */
	int defType() default EASY_TYPE;
	
	/** 日期范围字段 */
	String dateColumn() default "";
	
	/** 日期的类型 yyyy-MM-dd HH:mm:ss */
	String dateFmt() default "yyyy-MM-dd";

}
