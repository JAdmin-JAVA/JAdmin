package com.jadmin.modules.annotation.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:声明该注解之后，当改字段的值为value中的值时，不允许修改和删除，TODO:目前值只支持String类型
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnEditColunm{
	
	/** 当改字段的值为value中的值时，不允许修改和删除 */
	String[] value();

    /** 字段 */
	String column() default "";

    /** 是否允许删除 */
	boolean allowDel() default false;

    /** 是否允许修改 */
	boolean allowUpdate() default false;
}
