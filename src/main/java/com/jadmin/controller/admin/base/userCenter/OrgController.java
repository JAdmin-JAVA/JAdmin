package com.jadmin.controller.admin.base.userCenter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.vo.entity.base.OrgVO;
import com.jadmin.vo.enumtype.AdminPageMenu;

/** 
 * @Title:web框架
 * @Description:部门管理相关的控制层
 * @Copyright:JAdmin (c) 2018年11月26日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
@RequestMapping("/org") // 定义前台url访问的基础路径
@AdminPage(menu = AdminPageMenu.userCenter, name = "部门管理") // 声明后台管理页面左边的菜单属性，用来控制权限
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@TableHql(value = "isDelete = 0", orderBy = "operateTime desc")
@DeleteMode(DeleteMode.UPDATE) // 定义删除策略为修改状态
public class OrgController extends CommonListController<OrgVO> {

    @TableColumn(value = "部门编号", search = true)
    @InitDefaultColunm(methodGet = true)
    public String code;

    @FormColunm(value = "部门名称")
    @TableColumn(search = true)
    public String name;

    @FormColunm(value = "部门全称", required = false)
    public String fullname;

    @FormColunm(value = "所属父部门", selectCode = "orgFSeq")
    @TableColumn
    public String orgFSeq;

    @TableColumn(value = "操作时间")
    @InitDefaultColunm
    public String operateTime;

    @TableColumn(value = "操作人")
    @InitDefaultColunm
    public String operatorId;

    @FormColunm(value = "状态", selectCode = "billStatus")
    @TableColumn(search = true)
    public String billStatus;

    @FormColunm(value = "描述", type = "textarea", length = 512, required = false)
    public String memo;

    @InitDefaultColunm("0")
    public String isDelete;
    
	public String getCode(){
		SystemDao systemDao = SpringContext.getBean(SystemDao.class);
		return systemDao.genCode("sys_org", "code", 1000);
	}

    @Override
    public void beforeEditSave(OrgVO vo, HttpServletRequest request) {
        // 部门序列是orgFSeq+seq
        vo.setSeq(vo.getOrgFSeq() + vo.getCode() + ".");
    }

}
