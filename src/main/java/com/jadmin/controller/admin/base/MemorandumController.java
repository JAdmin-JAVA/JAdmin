package com.jadmin.controller.admin.base;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.vo.entity.base.MemorandumVO;

/**
 * @Title:web框架
 * @Description:备忘笺相关的控制层
 * @Copyright:JAdmin (c) 2018-09-17
 * 
 * @author:-jiujiya
 * @version:1.0
 */
@Controller
@RequestMapping("/memorandum") // 定义前台url访问的基础路径
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({"toAdd"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@DeleteMode(DeleteMode.UPDATE) // 定义删除策略为修改isDelete的状态
@TableHql(value = "isDelete = 0")
public class MemorandumController extends CommonListController<MemorandumVO> {

    @FormColunm(value = "内容", type = "textarea", length = 2048)
	@TableColumn(search = true)
	public String content;

	@TableColumn(value = "操作时间")
	@InitDefaultColunm
	public String operateTime;

	@InitDefaultColunm
	public String operatorId;

	@InitDefaultColunm("0")
	public String isDelete;
	
	@Override
	public String getHqlWhere(HttpServletRequest request) {
		String userId = getClientENV(request.getSession()).getCurUser().getUserId();
		return " operatorId = '" + userId + "'";
	}

}
