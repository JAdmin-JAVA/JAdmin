package com.jadmin.modules.util.quartz;

import java.sql.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.jadmin.modules.exception.BusinessException;
import com.jadmin.modules.util.DictinfoUtils;
import com.jadmin.modules.util.SpringContext;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:MyScheduler业务类
 * @Copyright:JAdmin (c) 2018年08月27日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
@Slf4j
public class SchedulerUtil {
	
	/** 单例类 */
	private static SchedulerUtil schedulerUtil;

	private Scheduler scheduler;
	
    /**
     * 获得工具类实例
     * 
     * @return
     */
    public static final SchedulerUtil getInstance() {
        if (schedulerUtil == null) {
        	schedulerUtil = new SchedulerUtil();
        	try {
    			SchedulerFactoryBean factory = SpringContext.getBean(SchedulerFactoryBean.class);
    			factory.getScheduler().getListenerManager().addTriggerListener(new MyTriggerListener());
    			schedulerUtil.setScheduler(factory.getScheduler());
    		} catch (SchedulerException e) {
    			log.error("致命错误>>调度无法启动：" + e.getMessage(), e);
    		}
        }
        return schedulerUtil;
    }

	public void init() {
		
	}

	/**
	 * 添加任务
	 * @param time
	 * @param jobClass
	 * @param delayedSecond 延迟多少秒执行
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addJob(Class jobClass, String time, int delayedSecond) {
		try {
			// 指明job的名称，所在组的名称，以及绑定job类
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobClass.getName()).build();// 设置Job的名字和组
			
			// corn表达式
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);

			// 设置定时任务的时间触发规则
			Date date = new Date(System.currentTimeMillis() + delayedSecond * 1000);
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).startAt(date).withIdentity(jobClass.getName()).build();
			
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, cronTrigger);
		} catch (Exception e) {
			log.error("添加调度任务失败：" + e.getMessage(), e);
		}
	}
	
	/**
	 * 添加任务
	 * @param time
	 * @param job
	 */
	public void addJob(String job, String time) {
		addJob(job, time, 0);
	}
	
	/**
	 * 添加任务
	 * @param time
	 * @param job
	 * @param delayedSecond 延迟多少秒执行
	 */
	@SuppressWarnings("rawtypes")
	public void addJob(String job, String time, int delayedSecond) {
		try {
			// 创建jobDetail实例，绑定Job实现类
			Class jobClass = Class.forName(job);
			addJob(jobClass, time, delayedSecond);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加或者修改任务
	 * @param time
	 * @param job
	 * @param delayedSecond 延迟多少秒执行
	 */
	public void addOrUpdateJob(String job, String time, int delayedSecond) {
		int status = getJobStatus(job);
		if(status == -1 || status == 0) {
			addJob(job, time, delayedSecond);
		}else {
			modifyJobTime(job, time, delayedSecond);
		}
	}
	
	/**
	 * 修改定时任务时间
	 * @param job
	 * @param time
	 */
	public void modifyJobTime(String job, String time) {
		modifyJobTime(job, time, 0);
	}
	

	/**
	 * 修改定时任务时间
	 * @param job
	 * @param time
	 */
	public void modifyJobTime(String job, String time, int delayedSecond) {
		try {
			TriggerKey triggerKey = new TriggerKey(job);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) { // Trigger已存在，那么更新相应的定时设置
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);// 设置一个新的定时时间
				
				// 设置定时任务的时间触发规则
				Date date = new Date(System.currentTimeMillis() + delayedSecond * 1000);

				// 按新的cronExpression表达式重新构建trigger
				CronTrigger cronTrigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).startAt(date).build();
				
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, cronTrigger);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("修改定时任务时间失败：" + e.getMessage());
		}
	}
	
	/**
	 * 根据包名+类名获取调度状态
	 * @param triggerName
	 * @return
	 */
	public int getJobStatus(String triggerName) {
		try {
			JobKey jobKey = new JobKey(triggerName);
			TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
			return scheduler.getTriggerState(triggerKey).ordinal();
		} catch (Exception e) {
			log.error("获取调度状态失败" + e.getMessage(), e);
			return -1;
		}
	}
	
	/**
	 * 根据包名+类名获取调度状态
	 * @param triggerName
	 * @return
	 */
	public String getJobStatusString(String triggerName) {
		int status = getJobStatus(triggerName);
		if(status == -1) {
			return "获取状态失败";
		}
		return DictinfoUtils.getDictkindNameByKey("SchedulerState", "" + status, null);
	}

	/*public String getJobNextTime(String triggerName) {
		try {
			JobKey jobKey = new JobKey(triggerName);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			jobDetail.
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return triggerName;
		
	}*/

	/**
	 * 
	 * 暂停一个任务
	 * @param triggerName
	 */
	public void pauseJob(String triggerName) {
		try {
			JobKey jobKey = new JobKey(triggerName);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if (jobDetail == null) {
				return;
			}
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			log.error("暂停任务失败：" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除一个任务
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public void deleteJob(String triggerName) {
		try {
			JobKey jobKey = new JobKey(triggerName);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if (jobDetail == null) {
				return;
			}
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			log.error("删除任务失败：" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 恢复一个任务
	 * @param triggerName
	 */
	public void resumeJob(String triggerName) {
		try {
			JobKey jobKey = new JobKey(triggerName);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if (jobDetail == null) {
				return;
			}
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			log.error("恢复任务失败：" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/***
	 * 开始所有定时任务
	 */
	public void startAllJob() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			log.error("开始任务失败：" + e.getMessage(), e);
		}
	}

	/***
	 * 立即执行定时任务
	 */
	public void doJob(String triggerName) {
		try {
			JobKey jobKey = JobKey.jobKey(triggerName);
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			log.error("执行任务失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 停止所有调度
	 */
	public void shutdown() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			log.error("停止所有任务失败：" + e.getMessage(), e);
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	
}
