package com.jadmin.modules.util.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.util.DateTimeUtil;

import lombok.extern.slf4j.Slf4j;

import org.quartz.TriggerListener;

/** 
 * @Title:web框架
 * @Description:Job任务监听
 * @Copyright:JAdmin (c) 2018年09月25日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
public class MyTriggerListener implements TriggerListener {
	
    @Override
    public String getName() {
        return "MyTriggerListener";
    }

    /**
     *  Trigger 被触发了，此时Job 上的 execute() 方法将要被执行
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
    	String jobName = context.getJobDetail().getJobClass().getName();
		log.info("开始执行调度：" + jobName);
    }

    /**
     * 发现此次Job的相关资源准备存在问题，不便展开任务，返回true表示否决此次任务执行
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    /**
     * 当前Trigger触发错过了
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
    }

    /**
     * Trigger 被触发并且完成了 Job 的执行,此方法被调用
     */
	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		String jobName = context.getJobDetail().getJobClass().getName();
		long second = context.getJobRunTime() / 1000;
		log.info("结束执行调度：" + jobName + "，耗时（S）：" + second);
		// 添加调度记录
		SystemDao systemDao = SpringContext.getBean(SystemDao.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO SYS_TASK_LOG( ");
		sql.append("  LOGID,     CLASSNAME, SERVERIP,    CONTENT, ");
		sql.append("  STARTTIME, ENDTIME,   EXECUTETIME           ");
		sql.append(" )VALUES( ");
		sql.append("  ?,         ?,         ?,           ?, ");
		sql.append("  ?,         ?,         ?               ");
		sql.append(" ) ");
		Object[] args = new Object[]{ 
					systemDao.getOID(),    jobName,      QuartzUtil.SERVER_IP,     "执行调度", 
					DateTimeUtil.getDateTime(context.getFireTime()), DateTimeUtil.getCurrentDateTime(),   second 
				};
		systemDao.getJdbcTemplate().update(sql.toString(), args);
	}
}
