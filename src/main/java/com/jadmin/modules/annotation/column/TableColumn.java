package com.jadmin.modules.annotation.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jadmin.vo.enumtype.JavaType;

/** 
 * @Title:web框架
 * @Description:声明表格中的属性
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn{
	
    /** 字段的名称，如果为空将使用FormColunm的value值 */
	String value() default "";

    /** 对应的JavaBean字段，默认使用的是变量的值，不需要定义。如果是多/一对一里面另一个对象的属性，可以填写{对象名}.{属性} */
	String column() default "";
	
    /** select类型的数据词典 */
	String selectCode() default "";

    /** 是否是图片 */
	boolean img() default false;

    /** 视频的路径，如果不是视频 空即可 */
	String videoPath() default "";
	
	/** 显示的最大长度，默认 显示全部 */
	int maxLength() default -1;

    /** 是否在搜索组件中显示 */
	boolean search() default false;
	
    /** 搜索字段，查询时的Java类型 */
	JavaType javaType() default JavaType.String;

    /** 导出时用的字段 */
	String exportColumn() default "";

    /** 字段是否可以导出 */
	boolean export() default true;
}
