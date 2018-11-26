package com.jadmin.modules.annotation.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:当声明该注解后，默认不显示该字段，只有定义的selectColumn下拉框选中为value值后，才显示该字段
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectShow {

	/** 选择下拉框的列名 */
	String selectColumn();
	
	/** 选择下拉框的值 */
	String[] value();

}
