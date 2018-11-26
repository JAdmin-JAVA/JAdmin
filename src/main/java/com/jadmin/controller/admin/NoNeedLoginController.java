package com.jadmin.controller.admin;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jadmin.modules.annotation.MethodLog;
import com.jadmin.modules.annotation.NoNeedLogin;
import com.jadmin.modules.controller.base.BaseAbstractController;
import com.jadmin.modules.util.StartCacheUtil;
import com.jadmin.modules.util.StaticPageUtils;
import com.jadmin.util.UserAgentUtil;
import com.jadmin.vo.entity.base.UserVO;

/** 
 * @Title:web框架
 * @Description:无需登录就能访问的控制层
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
@NoNeedLogin //无需登录就能访问
public class NoNeedLoginController extends BaseAbstractController {

	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping({ "/", "/login" })
	public String login(HttpServletRequest request) {
		return "admin/login";
	}

	/**
	 * 登陆的具体操作
	 * @return
	 */
	@RequestMapping({ "/toLogin" })
	@MethodLog(type = "登陆")
	public @ResponseBody Object toLogin(String account, String password, HttpServletRequest request) {
		UserVO vo = systemDao.login(account, password);
		getClientENV(request.getSession()).setCurUser(vo);
		// 登陆次数+1
	    systemDao.upLastLogin(UserAgentUtil.getRemoteAddr(request), vo.getUserId());
		return getRuturnJsonMap();
	}
	
	/**
	 * 刷新静态化页面
	 */
	@RequestMapping(value="/flushPage")
	public @ResponseBody Object flushPage(String url, HttpServletRequest request){
		StaticPageUtils.flushPage(url);
		return getRuturnJsonMap();
	}
	
	/**
	 * 刷新缓存
	 */
	@RequestMapping(value="/flushStaticData")
	public @ResponseBody Object flushStaticData(String keys, HttpServletRequest request){
		StartCacheUtil.refurbish(keys);
		return getRuturnJsonMap();
	}
	
}