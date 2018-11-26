package com.jadmin.controller.admin.base.system.dictkind;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.ButtonTablePage;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.vo.entity.base.DictinfoVO;

/** 
 * @Title:web框架
 * @Description:一个数据字典中的数据属性的相关的控制层
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller 
@RequestMapping("/dictkindDD1") // 定义前台url访问的基础路径
//声明该页面是“字典种类”页面，“字典属性”按钮的结果页面
@ButtonTablePage(page = DictkindController.class, name = "一级字典属性", imgChar="&#xe6bf;", idColunm = "dictkindId") 
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({"toShow"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@DeleteMode(DeleteMode.DELETE) // 定义删除策略为真删
@TableHql(value = "level=1", orderBy = "sort")
public class DictKindDictinfosController extends CommonDictinfosController<DictinfoVO> {

	@TableColumn("字典属性")
	public String value;

	// 一级字典属性，级别是1
	@InitDefaultColunm(value = "1")
	public String level;
}
