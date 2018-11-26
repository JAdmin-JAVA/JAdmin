package com.jadmin.modules.annotation.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:声明表格中的排序属性
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderByColumn {

    /** 排序的方式常量 分别是：正序/倒叙，倒叙，正序 */
	public static int DEFAULT = 0;
	public static int NORMAL = 1;
	public static int DESC = 2;
	
    /** 排序方式 */
	int value() default DEFAULT;

}
