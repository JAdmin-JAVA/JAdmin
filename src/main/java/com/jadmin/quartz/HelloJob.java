package com.jadmin.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jadmin.modules.annotation.quartz.DJob;
import com.jadmin.modules.quartz.base.BaseJob;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web项目演示1.0
 * @Description:job任务例子
 * @Copyright:JAdmin (c) 2018年08月27日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@DisallowConcurrentExecution // 同一时间将只有一个Job实例被执行
@DJob
@Slf4j
public class HelloJob extends BaseJob{
	
	public static void main(String[] args) {
		// 模拟测试该调度
		new HelloJob().start();
	}
	
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	log.info("调度测试，数据库时间：" + baseDao.getJdbcTemplate().queryForObject("SELECT NOW()", String.class));
    	try {
    		// sleep了40秒，当前调度时间是5秒，声明@DisallowConcurrentExecution后，下一个调度会等待40秒后执行。
			Thread.sleep(40000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
}
