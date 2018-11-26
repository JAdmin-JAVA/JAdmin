package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:定义删除的策略
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteMode {

    /** 删除的方式 分别是：真删、修改idDelete为1 */
	public final int DELETE = 1;
	public final int UPDATE = 2;
	
	/** 删除策略 */
	int value();
	
	/** 删除状态对应的字段 */
	String updateKey() default "isDelete";
	
	/** 删除状态对应的值 默认1，1是是，0是否 */
	String updateValue() default "1";

}
