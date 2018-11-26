package com.jadmin.controller.admin.base.userCenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.column.UnEditColunm;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.vo.entity.base.RoleModuleVO;
import com.jadmin.vo.entity.base.RoleVO;
import com.jadmin.vo.enumtype.AdminPageMenu;
import com.jadmin.vo.fundation.controller.AdminPageMenuVO;
import com.jadmin.vo.fundation.tool.Commons;

/** 
 * @Title:web框架
 * @Description:角色管理相关的控制层
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
@RequestMapping("/role") // 定义前台url访问的基础路径
@AdminPage(menu = AdminPageMenu.userCenter, name = "角色管理") // 声明后台管理页面左边的菜单属性，用来控制权限
@FileConfig(selfJsp = {"edit"}) // 只通用 admin/common/date-list.jsp，edit跳到自己默认的jsp页面
@AdminPageNoButton({"toShow"}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
public class RoleController extends CommonListController<RoleVO> {

	@UnEditColunm("0") // 当roleId为0时，不允许编辑和删除
	public String roleId;
	
	@FormColunm(value = "名称")
	@TableColumn(search = true)
	public String roleName;

	@InitDefaultColunm("2")
	public String roleType;

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
	
	@Override
	public boolean beforeDelete(List<String> ids, HttpServletRequest request) {
		for (String string : ids) {
			String sql = "SELECT COUNT(*) FROM sys_user WHERE roleId = ? AND isDelete = 0";
			if(systemDao.getJdbcTemplate().queryForObject(sql, new Object[] {string}, Integer.class) != 0){
				throw new BusinessException("角色下还有用户存在，不允许删除！");
			}
		}
		for (String string : ids) {
			String sql = "DELETE FROM sys_role WHERE roleId = ?";
			systemDao.getJdbcTemplate().update(sql, new Object[]{string});
		}
		return false;
	}

    /**
     * 跳转到添加/编辑页面前的操作
     */
	@Override
	public void beforeToAddOrUpdate(HttpServletRequest request) {
		//获取所有的菜单
		List<AdminPageMenuVO> pageMenuList =  Commons.getInstance().getAdminPageMenus();
		request.setAttribute("pageMenuList", pageMenuList);
	}
	
    /**
     * 跳转到编辑页面前的操作
     * @param vo
     */
    public void beforeToUpdate(RoleVO vo, HttpServletRequest request){
    	// 获取用户拥有的菜单、页面、按钮
    	Set<String> hasMenu = new HashSet<String>();
    	Set<String> hasPage = new HashSet<String>();
    	Set<String> hasButton = new HashSet<String>();
    	for (RoleModuleVO rmVO : vo.getRoleModule()) {
    		hasMenu.add(rmVO.getModuleMenuFullName());
    		hasPage.add(rmVO.getModulePageFullName());
    		hasButton.add(rmVO.getModuleButtonFullName());
		}
    	request.setAttribute("hasMenu", hasMenu);
    	request.setAttribute("hasPage", hasPage);
    	request.setAttribute("hasButton", hasButton);
    }
	
    /**
     * 保存单据 前的操作
     * @param vo
     * @param request 
     */
    public void beforeEditSave(RoleVO vo, HttpServletRequest request){
    	// 删除该角色老的模块
    	systemDao.getJdbcTemplate().update(" DELETE from sys_role_module WHERE roleId = ? ", new Object[]{vo.getRoleId()});
    	// 将新的模块放入vo
    	String[] buttonValues = request.getParameterValues("buttonValues");
    	if(buttonValues != null && buttonValues.length != 0){
    		List<RoleModuleVO> roleModule = new ArrayList<RoleModuleVO>();
    		for (String string : buttonValues) {
    			String[] names = string.split(";");
    			RoleModuleVO mVo = new RoleModuleVO();
    			mVo.setRoleId(vo);
    			mVo.setBillStatus("1");
    			mVo.setModuleMenu1(names[0]);
    			mVo.setModuleMenu2(names[1]);
    			mVo.setModulePage(names[2]);
    			mVo.setModuleButton(names[3]);
    			roleModule.add(mVo);
    		}
    		vo.setRoleModule(roleModule);
    	}
    }

}
