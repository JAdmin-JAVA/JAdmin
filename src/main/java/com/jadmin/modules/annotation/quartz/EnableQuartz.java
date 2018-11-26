package com.jadmin.modules.annotation.quartz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.quartz.Job;

/** 
 * @Title:web框架
 * @Description:调度启动器，声明该注解后，将启动调度
 * @Copyright:JAdmin (c) 2018年09月26日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableQuartz {

    /** 如果为空，默认启动所有调度  */
	Class<? extends Job>[] value() default {};
	
    /** 是否以配置文件的设置为主 yml配置文件的关键词为 taskList，此值为true时，value无效  */
	boolean ymlConfig() default false;
}
