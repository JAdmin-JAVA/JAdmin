package com.jadmin.modules.annotation.quartz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:任务注释  任务类必须继承com.jadmin.modules.quartz.base.BaseJob
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DJob {
	
	/** 时间表达式，当isDynamic为true是可以为空，为空时需要在后台设置cron时间，然后才能开启 */
	String cron() default "";
	
	/** 调度任务是否允许在后台编辑cron时间、延迟时间等，默认允许编辑 */
	boolean isDynamic() default true;
	
	/** 任务描述 */
	String description() default "";
	
	/** 延迟，单位为秒 */
	int initialDelay() default 0;

}
