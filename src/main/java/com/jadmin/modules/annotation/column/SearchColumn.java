package com.jadmin.modules.annotation.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jadmin.vo.enumtype.JavaType;

/** 
 * @Title:web框架
 * @Description:声明表格中的搜索属性
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchColumn {

    /** 搜索的方式常量 分别是：完全相等，左匹配，右匹配，模糊搜索 */
	public static int EQUAL = 0;
	public static int LEFT = 1;
	public static int RIGHT = 2;
	public static int LIKE = 3;
	public static int DA = 4;
	public static int DA_DENG = 5;
	public static int XIAO = 6;
	public static int XIAO_DENG = 7;
	public static int NOT_NULL = 8;
	
	// 默认搜索
	public static int INIT = LIKE;
	
    /** 搜索的标签 */
	String value() default "";

    /** 搜索的字段 */
	String column() default "";
	
    /** select类型的数据词典 */
	String selectCode() default "";
	
    /** 搜索字段，查询时的Java类型 */
	JavaType javaType() default JavaType.String;

    /** 搜索的初始值，默认列表界面查询时，会加上改值 */
	String initDefault() default "";

    /** 搜索的初始值，如果为true，说明需要初始化值，值为通过get方法获取 */
	boolean initDefaultMethodGet() default false;
}
