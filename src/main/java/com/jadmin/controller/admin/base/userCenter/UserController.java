package com.jadmin.controller.admin.base.userCenter;

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
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.annotation.list.Tree;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.vo.entity.base.UserVO;
import com.jadmin.vo.enumtype.AdminPageMenu;
import com.jadmin.vo.enumtype.JavaType;

/**
 * @Title:web框架
 * @Description:用户管理相关的控制层
 * @Copyright:JAdmin (c) 2018年08月21日
 * @author:-jiujiya
 * @version:1.0
 */
@Controller
@RequestMapping("/user") // 定义前台url访问的基础路径
@AdminPage(menu = AdminPageMenu.userCenter, name = "用户管理") // 定义菜单和页面名称
@FileConfig // jsp和js的相关配置
@AdminPageNoButton({}) // 声明页面中不需要的button，默认显示删除、添加、编辑 3个按钮
@TableHql(value = "isDelete = 0 ", orderBy = "operateTime desc")
@DeleteMode(DeleteMode.UPDATE) // 定义删除策略为修改状态
@Tree // 声明为树形结构
public class UserController extends CommonListController<UserVO> {

    @FormColunm(value = "账号", edit = "false") // 不允许编辑
    @TableColumn(search = true)
    @UniqueColunm // 账号唯一性效验
    public String account;

    @FormColunm(value = "姓名")
    @TableColumn
    public String name;

    @FormColunm(value = "性别", selectCode = "sex", selectStyle = "icheck")
    @TableColumn(search = true)
    public String sex;

    @FormColunm(value = "所属角色", selectCode = "userRole", column = "role.roleId")
    @TableColumn(search = true)
    public String roleId;

    @FormColunm(value = "所属部门", selectCode = "org", column = "org.orgId", selectStyle = "tree")
    @TableColumn
    public String orgId;

    @FormColunm(value = "密码", encode = "des", edit = "false") // 设置该字段为 des加密，并且编辑时，不可见
    public String password; 

    @FormColunm(value = "状态", selectCode = "billStatus")
    @TableColumn(search = true)
    public String billStatus;

    @TableColumn(value = "操作时间")
    @InitDefaultColunm
    public String operateTime;

    @TableColumn(value = "操作人")
    @InitDefaultColunm
    public String operatorId;

    @InitDefaultColunm("0")
    public String isDelete;

    @InitDefaultColunm(" ")
    private String lastLoginIp;

    @InitDefaultColunm(value = "0", javaType = JavaType.Integer)
    private Integer loginCount;

    @InitDefaultColunm(" ")
    private String lastLoginTime;

	@InitDefaultColunm("")
	private String psChangeTime;

    @FormColunm(value = "描述", type = "textarea", length = 512, required = false)
    public String memo;
    
    @Override
    public boolean beforeDelete(List<String> ids, HttpServletRequest request) {
        for (String id : ids) {
            String nAccount = getCurUser(request.getSession()).getAccount();
            UserVO vo = (UserVO) systemDao.getEntityManager().find(UserVO.class, id);
            if ("admin".equals(vo.getAccount()) || nAccount.equals(vo.getAccount())) {
                throw new BusinessException("不能删除自己和admin用户");
            }
        }
        return true;
    }

}
