package com.jadmin.vo.fundation.tool;

import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpSession;

import com.jadmin.vo.entity.base.UserVO;
import com.jadmin.vo.fundation.controller.AdminPageMenuVO;

/** 
 * @Title:web框架
 * @Description:客户端Session控制器
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class ClientENV implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = 3272990249325617429l;
    /** 所在session句柄，这种设计主要是为了实现session与web容器脱离 */
    private HttpSession session; // 应该只有get方法，没有set方法，所有存储统一由ENV控制
    /** 当前后台用户 */
    private static final String CUR_USER = "CUR_USER";
    /** 用户ip */
    private static final String IP = "cur_ip";
    /** 用户登录号，验证登录时使用 */
    private static final String CLIENT_NO = "client_no";
    /** 验证码 */
    private static final String VALIDATE_CODE = "validateCode";
    /** 用户拥有的权限 */
    private static final String ADMIN_PAGEM_ENUS = "adminPageMenus";
    
    /**
     * 构造器
     */
    public ClientENV(){

    }

    public ClientENV(HttpSession session) {
    	this.session = session;
	}

	public UserVO getCurUser() {
        return (UserVO) session.getAttribute(CUR_USER);
    }
    
    public void setCurUser(UserVO curUser) {
    	session.setAttribute(CUR_USER, curUser);
    }

    public void setAdminPageMenus(List<AdminPageMenuVO> adminPageMenus) {
        session.setAttribute(ADMIN_PAGEM_ENUS, adminPageMenus);
    }
    
    @SuppressWarnings("unchecked")
	public List<AdminPageMenuVO> getAdminPageMenus() {
    	return (List<AdminPageMenuVO>) session.getAttribute(ADMIN_PAGEM_ENUS);
    }

    public String getIp() {
        return (String) session.getAttribute(IP);
    }

    public void setIp(String ip) {
        session.setAttribute(IP, ip);
    }

    public String getClientNo() {
        return (String)session.getAttribute(CLIENT_NO);
    }

    public void setClientNo(String clientNo) {
        session.setAttribute(CLIENT_NO, clientNo);
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public void setValidateCode(String code){
        this.session.setAttribute(VALIDATE_CODE, code);
    }
    
    public String getValidateCode(){
        return (String)this.session.getAttribute(VALIDATE_CODE);
    }
}
