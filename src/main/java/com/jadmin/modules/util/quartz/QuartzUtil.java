package com.jadmin.modules.util.quartz;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;

import com.jadmin.WebApplication;
import com.jadmin.dao.SystemDao;
import com.jadmin.modules.annotation.quartz.DJob;
import com.jadmin.modules.annotation.quartz.EnableQuartz;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.modules.util.ClassUtil;
import com.jadmin.modules.util.ConfigUtil;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.vo.entity.base.SysTaskVO;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:quartz工具类
 * @Copyright:JAdmin (c) 2018年09月25日
 * 
 * @author:-zhangjiao
 * @version:1.0 
 */
@Slf4j
public class QuartzUtil {
	
	public static String PACKAGE_NAME = ConfigUtil.BASE_PACKAGE + "quartz";
	
	public static String SERVER_IP = "";
	
	static {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			SERVER_IP = addr.getHostAddress().toString(); //获取本机ip  
		} catch (UnknownHostException e) {
			log.error("注意：获取服务器ip失败，可能会影响您调度的正常使用，请留意！");
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 开启调度任务
	 * @return
	 */
	public static void startAllJob() {
		// 查看调度器是否开启
		EnableQuartz taskStart = WebApplication.class.getAnnotation(EnableQuartz.class);
		if(taskStart == null) {
			log.info("调度器未开启");
			return;
		}
		// 获取所有有dJob 并且未被EnableQuartz注解过滤的任务类
		List<Class<?>> jobClass = getAnJobClass(taskStart);
		SystemDao systemDao = SpringContext.getBean(SystemDao.class);
		// 查询数据库里面的调度
		Map<String, SysTaskVO> dbMap = getDbJob(systemDao);
		// 合并注解里面的调度
		List<SysTaskVO> finalTasks = mergeAnJob(dbMap, jobClass);
		// 删除不需要的调度
		deleteNoNeedJob(finalTasks, systemDao);
    	// 动态添加任务和保存任务到数据库
    	addAndSaveJob(finalTasks, systemDao);
	}
	
	/**
	 * 合并注解里面的调度
	 * @param dbMap
	 */
	private static List<SysTaskVO> mergeAnJob(Map<String, SysTaskVO> dbMap, List<Class<?>> jobClass) {
    	List<SysTaskVO> list = new ArrayList<>();
    	for (Class<?> class1 : jobClass) {
    		DJob dJob = class1.getAnnotation(DJob.class);
    		if(!dJob.isDynamic() && StringUtils.isBlank(dJob.cron())) {
    			throw new BusinessException(class1.getName() + "调度配置不合理：声明已注解为主，但是注解并未配置时间表达式？");
    		}
			SysTaskVO vo = dbMap.get(class1.getName());
			if(vo == null) {
				vo = new SysTaskVO();
				vo.setClassName(class1.getName());
				vo.setIsEdited("0");
				vo.setServerIp(SERVER_IP);
			}
			// 是否已注解的值 为主   （如果从未修改过，也已注解的值为主）
			boolean isAn = !dJob.isDynamic() || "0".equals(vo.getIsEdited());
			vo.setIsDynamic(dJob.isDynamic() ? "1" : "0");
			if(isAn || StringUtils.isBlank(vo.getCron())) {
				vo.setCron(dJob.cron());
			}
			if(isAn || StringUtils.isBlank(vo.getDescription())) {
				vo.setDescription(dJob.description());
			}
			if(isAn || vo.getInitialDelay() == null) {
				vo.setInitialDelay(dJob.initialDelay());
			}
			list.add(vo);
		}
    	return list;
	}
	
	/**
	 * 获取所有有dJob 并且未被EnableQuartz注解过滤的任务类
	 * @param taskStart
	 * @return
	 */
	private static List<Class<?>> getAnJobClass(EnableQuartz taskStart) {
		List<Class<?>> list = new ArrayList<>();
		if(!taskStart.ymlConfig() && taskStart.value().length == 0) {
			List<Class<?>> classes = ClassUtil.getClasses(PACKAGE_NAME);
			for (Class<?> class1 : classes) {
				// 如果该方法里有DJob注解，说明其有调度进行
				DJob dJob = class1.getAnnotation(DJob.class);
				if (dJob != null) {
					list.add(class1);
				}
			}
			return list;
		}
		Class<?>[] tempList = taskStart.value();
		if(taskStart.ymlConfig()) {
			tempList = getTaskListClasses(ConfigUtil.getJAdminProperty().getTaskList());
			log.info("application.yml配置了" + tempList.length + "个需要启动的调度……");
		}
		for (Class<?> class1 : tempList) {
			DJob dJob = class1.getAnnotation(DJob.class);
    		if (dJob != null) {
				list.add(class1);
    		}else {
    			log.error("配置异常：" + class1.getName() + "在EnableQuartz中声明开启，但是并未配置@DJob，请核实……");
    		}
		}
		return list;
	}
    
    /**
     * 获取配置文件中 要启动的调度
     * @param taskList
     * @return
     */
    private static Class<?>[] getTaskListClasses(List<String> taskList) {
		if(taskList == null || taskList.isEmpty()) return new Class<?>[] {};
		List<Class<?>> list = new ArrayList<>();
		for (String task : taskList) {
			String errorMsg = "配置异常：application.yml中的taskList配置的" + task + "错误，请检查！";
			if(!task.startsWith("com.")) {
				task = QuartzUtil.PACKAGE_NAME + "." + task;
			}
			try {
				Class<?> class1 = Class.forName(task);
				if(!(class1.newInstance() instanceof Job)) {
					throw new BusinessException(errorMsg + "该调度类是否继承了org.quartz.Job？");
				}
				list.add(class1);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				throw new BusinessException(errorMsg);
			}
		}
		return list.toArray(new Class<?>[] {});
	}

	/**
	 * @return 数据库里面的调度
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, SysTaskVO> getDbJob(SystemDao systemDao){
		List<SysTaskVO> dbList = (List<SysTaskVO>) systemDao.queryList("from SysTaskVO where serverIp = '" + SERVER_IP + "'");
		Map<String, SysTaskVO> dbMap = new HashMap<>();
		for (SysTaskVO sysTaskVO : dbList) {
			dbMap.put(sysTaskVO.getClassName(), sysTaskVO);
		}
		return dbMap;
	}
	
	/**
	 * 删除不需要的调度
	 */
	private static void deleteNoNeedJob(Collection<SysTaskVO> collection, SystemDao systemDao){
		if(collection.isEmpty()) return;
		StringBuffer sb = new StringBuffer();
		for (SysTaskVO sysTaskVO : collection) {
			sb.append("'").append(sysTaskVO.getPrimaryKey()).append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "DELETE FROM SYS_TASK WHERE CLASSNAME NOT IN (" + sb + ") AND SERVERIP = ? ";
		systemDao.getJdbcTemplate().update(sql, new Object[] {SERVER_IP});
	}

	/**
	 * 动态添加任务和保存任务到数据库
	 */
	private static void addAndSaveJob(Collection<SysTaskVO> collection, SystemDao systemDao){
		SchedulerUtil myScheduler = SchedulerUtil.getInstance();
		List<String> success = new ArrayList<>();
		List<String> error = new ArrayList<>();
		for (SysTaskVO sysTaskVO : collection) {
			if(StringUtils.isBlank(sysTaskVO.getCron())) {
				sysTaskVO.setBillStatus("0");
				error.add(sysTaskVO.getClassName());
			}else {
				sysTaskVO.setBillStatus("1");
				myScheduler.deleteJob(sysTaskVO.getClassName());
				myScheduler.addJob(sysTaskVO.getClassName(), sysTaskVO.getCron(), sysTaskVO.getInitialDelay());
				success.add(sysTaskVO.getClassName());
			}
			systemDao.saveOrUpdate(sysTaskVO);
		}
		myScheduler.startAllJob();
		log.info("初始化" + collection.size() + "个调度，开启" + success.size() +
				 "个调度：" + success + "，未开启" + error.size() + "个调度（需在后台设置后开启）：" + error);
	}
	
}
