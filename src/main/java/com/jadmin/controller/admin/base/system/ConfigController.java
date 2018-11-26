package com.jadmin.controller.admin.base.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.column.UniqueColunm;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.SearchMode;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.util.StartCacheUtil;
import com.jadmin.vo.entity.base.ConfigVO;
import com.jadmin.vo.enumtype.AdminPageMenu;

/** 
 * @Title:web框架
 * @Description:系统设置相关的控制层
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
@RequestMapping("/config") // 定义前台url访问的基础路径
@FileConfig // jsp和js的相关配置
@AdminPage(menu = AdminPageMenu.baseCenter, name = "系统设置") // 声明后台管理页面左边的菜单属性，用来控制权限
@AdminPageNoButton({"toShow"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@DeleteMode(DeleteMode.DELETE) // 定义删除策略为真删
@TableHql(value = "isOpen = 1")
@SearchMode(dateColumn = "operateTime")
public class ConfigController extends CommonListController<ConfigVO> {

	@FormColunm(value = "名称")
	@TableColumn(search = true)
	public String name;

	@FormColunm(value = "编号")
	@TableColumn(search = true)
	@UniqueColunm // 编号不能重复
	public String code;

	@FormColunm(value = "value", required = false)
	@TableColumn
	public String coValue;

	@InitDefaultColunm("1") // 系统添加的全部默认开放
	public String isOpen;

	@TableColumn(value = "操作时间")
	@InitDefaultColunm
	public String operateTime;

	@TableColumn(value = "操作人")
	@InitDefaultColunm
	public String operatorId;

	@InitDefaultColunm("1")
	public String billStatus;

	@FormColunm(value = "描述", type = "textarea", length = 512, required = false)
	public String memo;
	
	@Override
	public void afterDataChange() {
    	StartCacheUtil.refurbish("configs");
	}
}
