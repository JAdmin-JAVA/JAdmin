package com.jadmin.controller.admin.base.system;

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
import com.jadmin.vo.entity.base.LogVO;
import com.jadmin.vo.enumtype.AdminPageMenu;

/** 
 * @Title:web框架
 * @Description:系统日志相关的控制层
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
@RequestMapping("/log") // 定义前台url访问的基础路径
@AdminPage(menu = AdminPageMenu.baseCenter, name = "系统日志") // 声明后台管理页面左边的菜单属性，用来控制权限
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({"toUpdate", "toAdd"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@DeleteMode(DeleteMode.DELETE) // 定义删除策略为真删
@TableHql(orderBy = "operateTime desc")
public class LogController extends CommonListController<LogVO> {

	@FormColunm(value = "类别")
	@TableColumn(search = true)
	public String type;

	@FormColunm(value = "操作人")
	@TableColumn(search = true)
	public String userId;

	@FormColunm(value = "ip")
	@TableColumn
	public String clientIp;

	@FormColunm(value = "时间")
	@TableColumn
	public String operateTime;

	@FormColunm(value = "详细内容", type = "textarea", length = 512, required = false)
	@TableColumn(maxLength = 80)
	public String content;
}
