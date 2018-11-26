package com.jadmin.controller.admin.base.system.dictkind;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.column.UniqueColunm;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.util.StartCacheUtil;
import com.jadmin.vo.entity.base.DictkindVO;
import com.jadmin.vo.enumtype.AdminPageMenu;

/** 
 * @Title:web框架
 * @Description:字典类别相关的控制层
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
@RequestMapping("/dictkind") // 定义前台url访问的基础路径
@AdminPage(menu = AdminPageMenu.baseCenter, name = "数据字典") // 声明后台管理页面左边的菜单属性，用来控制权限
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({"toShow"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@TableHql(value = "isOpen = 1")
public class DictkindController extends CommonListController<DictkindVO> {

	@FormColunm(value = "名称")
	@TableColumn
	public String name;

	@FormColunm(value = "编号")
	@TableColumn(search = true)
	@UniqueColunm // 该字段唯一，不能重复
	public String code;
	
	@FormColunm(value = "字典属性类别", selectCode = "isDynamic")
	@TableColumn(search = true)
	public String isDynamic;
	
	@FormColunm(value = "sql", required = false)
	public String dynSql;
	
	@TableColumn("字典属性")
	public String value;
	
	@TableColumn(value = "操作时间")
	@InitDefaultColunm
	public String operateTime;
	
	@FormColunm(value = "类型", selectCode = "dictkindType", selectStyle = "icheck")
	@TableColumn
	public String type;

	@InitDefaultColunm("1") // 系统添加的全部默认开放
	public String isOpen;
	
	@FormColunm(value = "状态", selectCode = "billStatus")
	@TableColumn
	public String billStatus;
	
	@FormColunm(value = "描述", type = "textarea", length = 512, required = false)
	@TableColumn
	private String memo;

    /**
     * 当数据发生改变后的操作，包括增删改
     * @param vo
     * @param request 
     */
    public void afterDataChange(){
    	// 刷新数据词典缓存
    	StartCacheUtil.refurbish("dictkinds");
    }
	
	@Override
	public boolean beforeDelete(List<String> ids, HttpServletRequest request) {
		for (String string : ids) {
			String sql1 = "DELETE FROM sys_dictinfo WHERE dictkindId = ?";
			systemDao.getJdbcTemplate().update(sql1, new Object[]{string});
			String sql = "DELETE FROM sys_dictkind WHERE dictkindId = ?";
			systemDao.getJdbcTemplate().update(sql, new Object[]{string});
		}
		return false;
	}
}
