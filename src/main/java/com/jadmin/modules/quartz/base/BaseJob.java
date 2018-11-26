package com.jadmin.modules.quartz.base;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import com.jadmin.WebApplication;
import com.jadmin.modules.dao.base.BaseBusinessDao;
import com.jadmin.modules.itf.GeneralOperatUtils;

/** 
 * @Title:web框架
 * @Description:Job基础类
 * @Copyright:JAdmin (c) 2018年10月29日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
public abstract class BaseJob extends GeneralOperatUtils implements Job {

	/** 基础的dao */ 
	@Autowired
	public BaseBusinessDao baseDao;
	
	/** 是否是测试启动 */ 
	public static boolean isDebugStart = false;
	
	/**
	 * 测试启动，临时main方法运行时 使用
	 */
	public void start() {
		BaseJob.isDebugStart = true;
		SpringApplication.run(WebApplication.class, new String[] {});
		try {
			execute(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
