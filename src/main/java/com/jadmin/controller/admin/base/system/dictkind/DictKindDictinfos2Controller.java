package com.jadmin.controller.admin.base.system.dictkind;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/dictkindDD2") // 定义前台url访问的基础路径
//声明该页面是“字典种类”页面，“字典属性”按钮的结果页面
@ButtonTablePage(page = DictKindDictinfosController.class, name = "二级字典属性", imgChar="&#xe6bf;", idColunm = "dictinfoFid") 
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({"toShow"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@DeleteMode(DeleteMode.DELETE) // 定义删除策略为真删
@TableHql(orderBy = "sort")
public class DictKindDictinfos2Controller extends CommonDictinfosController<DictinfoVO> {

	@TableColumn("字典属性")
	public String value;
	
	// 二级字典属性，级别是2
	@InitDefaultColunm(value = "2")
	public String level;

    /**
     * 保存单据 前的操作
     * @param vo
     * @param request 
     */
    @Override
    public void beforeEditSave(DictinfoVO vo, HttpServletRequest request) {
    	DictinfoVO fVO = (DictinfoVO) systemDao.getEntityManager().find(DictinfoVO.class, vo.getDictinfoFid());
    	vo.setDictkindId(fVO.getDictkindId());
    }
}
