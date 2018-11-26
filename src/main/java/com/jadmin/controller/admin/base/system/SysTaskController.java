package com.jadmin.controller.admin.base.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.list.AdminPageButton;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.exception.AlertBusinessException;
import com.jadmin.modules.util.quartz.QuartzUtil;
import com.jadmin.modules.util.quartz.SchedulerUtil;
import com.jadmin.vo.entity.base.SysTaskVO;
import com.jadmin.vo.enumtype.AdminPageMenu;
import com.jadmin.vo.enumtype.Regular;
import com.jadmin.vo.fundation.tool.Page;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:systask的控制层
 * @Copyright:JAdmin (c) 2018年09月26日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
@Controller
@RequestMapping("/sysTask") // 定义前台url访问的基础路径
@AdminPage(menu = AdminPageMenu.baseCenter, name = "调度管理") // 声明后台管理页面左边的菜单属性，用来控制权限
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({"toAdd","del"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@DeleteMode(DeleteMode.DELETE) // 定义删除策略为修改isDelete的状态
@TableHql
public class SysTaskController extends CommonListController<SysTaskVO> {

	@FormColunm(value = "方法名", edit="false")
	@TableColumn(search = true)
	public String className;

	@FormColunm(value = "cron表达式", required = false)
	@TableColumn
	public String cron;

	@FormColunm(value = "延迟时间", required = false, datatype = Regular.posInteger)
	@TableColumn
	public Integer initialDelay;

	@FormColunm(value = "调度开关", selectCode = "quartzSwitch")
	@TableColumn
	public String billStatus;

    @FormColunm(value = "描述", type = "textarea", length = 512, required = false)
	@TableColumn(search = true)
	public String description;

	@TableColumn(value = "是否可修改", selectCode = "yesNo")
	@InitDefaultColunm
	public String isDynamic;
	
	@TableColumn(value = "调度状态")
	public String status;
	
	@TableColumn(value = "执行记录")
	public String logs;

	@InitDefaultColunm
	public String isEdited;

	@InitDefaultColunm
	public String serverIp;
	
	@Autowired
	SystemDao systemDao;
	
	@Override
	public String getHqlWhere(HttpServletRequest request) {
		return " serverIp = '" + QuartzUtil.SERVER_IP + "' ";
	}
	
	@Override
	public void afterQueryPage(Page<SysTaskVO> page, HttpServletRequest request) {
		for (SysTaskVO vo : page.getResult()) {
			SchedulerUtil myScheduler = SchedulerUtil.getInstance();
			vo.setStatus(myScheduler.getJobStatusString(vo.getClassName()));
		}
	}
	
	@Override
	public void beforeToUpdate(SysTaskVO vo, HttpServletRequest request) {
		SchedulerUtil myScheduler = SchedulerUtil.getInstance();
		if ("暂停".equals(myScheduler.getJobStatusString(vo.getClassName()))) {
			throw new AlertBusinessException("调度已暂停，该数据不允许修改！");
		}
		if("0".equals(vo.getIsDynamic())) {
			throw new AlertBusinessException("该数据不允许修改！");
		}
	}
	
	@Override
	public void beforeEditSave(SysTaskVO vo, HttpServletRequest request) {
		SchedulerUtil myScheduler = SchedulerUtil.getInstance();
		if("1".equals(vo.getBillStatus()) && StringUtils.isNotBlank(vo.getCron())) {
			// 如果存在，就修改下时间
			myScheduler.addOrUpdateJob(vo.getClassName(), vo.getCron(), vo.getInitialDelay());
		}else {
			vo.setBillStatus("0");
			myScheduler.deleteJob(vo.getClassName());
		}
		// 标记为已修改过
		vo.setIsEdited("1");
	}
	
	@AdminPageButton(name = "启动", imgChar="&#xe6e1;", color = "secondary", showType = AdminPageButton.AJAX, dataType = AdminPageButton.CHECKBOX)
	@RequestMapping({"/startSchedule"})
	public @ResponseBody String startSchedule(HttpServletRequest request, String id) {
		return changeStatus(id, "start");
	}
	
	@AdminPageButton(name = "暂停", imgChar="&#xe6e1;", color = "danger", showType = AdminPageButton.AJAX, dataType = AdminPageButton.CHECKBOX)
	@RequestMapping({"/stopSchedule"})
	public @ResponseBody String pauseSchedule(HttpServletRequest request, String id) {
		return changeStatus(id, "stop");
	}
	
	@AdminPageButton(name = "立即执行一次", imgChar="&#xe6e1;", showType = AdminPageButton.AJAX, dataType = AdminPageButton.CHECKBOX)
	@RequestMapping({"/doSchedule"})
	public @ResponseBody String doSchedule(HttpServletRequest request, String id) {
		return changeStatus(id, "do");
	}
	
	/**
	 * 修改调度状态
	 * @param ids
	 * @param type
	 * @return
	 */
	private String changeStatus(String ids, String type) {
		SchedulerUtil myScheduler = SchedulerUtil.getInstance();
		try {
			String sql = "select className from sys_task where id in (?)";
			List<String> list = systemDao.getJdbcTemplate().queryForList(sql, String.class, new Object[] {ids});
			for (String string : list) {
				if ("start".equals(type)) {
					myScheduler.resumeJob(string);
				}else if("stop".equals(type)){
					myScheduler.pauseJob(string);
				}else {
					myScheduler.doJob(string);
				}
			}
			return success;
		} catch (Exception e) {
			log.error(e.getMessage().toString());
			return failed;
		}
	}

}
