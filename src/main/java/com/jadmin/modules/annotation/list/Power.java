package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:机构权限功能
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Power {

    /** 权限的方式常量 分别是：所属机构，当前机构，当前用户 */
	public final int ORG = 1;
	public final int SELF_ORG = 2;
	public final int USER = 3;
	
	/** 默认所属机构权限，可以使用上面3个变量，也可以自定义权限 */
	int value() default ORG;
	
	/** 当前vo所对应机构的seq的属性 */
	String orgKey() default "org.seq";
	
	/** 当前vo所对应用户的属性 */
	String userKey() default "";

}
