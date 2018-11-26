package com.jadmin.controller.admin.base.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.vo.entity.base.SysTaskLogVO;
import com.jadmin.vo.enumtype.AdminPageMenu;

/**
 * @Title:web框架
 * @Description:系统任务执行记录相关的控制层
 * @Copyright:JAdmin (c) 2018-09-25
 * 
 * @author:-jiujiya
 * @version:1.0
 */
@Controller
@RequestMapping("/sysTaskLog") // 定义前台url访问的基础路径
@AdminPage(menu = AdminPageMenu.baseCenter, name = "系统任务执行记录", hide = true) // 声明后台管理页面左边的菜单属性，用来控制权限
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({"toUpdate", "toAdd", "del", "toShow"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@DeleteMode(DeleteMode.DELETE) // 定义删除策略为修改isDelete的状态
@TableHql(orderBy = "startTime desc")
public class SysTaskLogController extends CommonListController<SysTaskLogVO> {

	@FormColunm(value = "任务方法名")
	@TableColumn
	public String className;

	@FormColunm(value = "任务运行服务器ip")
	@TableColumn
	private String serverIp;

	@FormColunm(value = "日志内容")
	@TableColumn
	public String content;

	@FormColunm(value = "任务开始时间")
	@TableColumn(search = true)
	public String startTime;

	@FormColunm(value = "任务结束时间")
	@TableColumn
	public String endTime;

	@FormColunm(value = "耗时（秒）")
	@TableColumn
	public String executeTime;
	
	@Override
	public String getHqlWhere(HttpServletRequest request) {
		String baseWhere = request.getParameter("baseWhere");
		String[] baseWheres = baseWhere.split(";");
		request.setAttribute("baseWhere", baseWhere);
		return "className = '" + baseWheres[0] + "' and serverIp = '" + baseWheres[1] + "'";
	}

}
