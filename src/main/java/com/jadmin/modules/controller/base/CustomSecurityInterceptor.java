package com.jadmin.modules.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.exception.AlertBusinessException;
import com.jadmin.modules.util.AdminPageUtils;
import com.jadmin.modules.util.ConfigUtil;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.modules.util.StaticPageUtils;
import com.jadmin.vo.entity.base.UserVO;
import com.jadmin.vo.fundation.tool.ClientENV;
import com.jadmin.vo.fundation.tool.Commons;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title:web框架
 * @Description:自定义拦截器
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
@Component
public class CustomSecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	// 静态文件，直接返回
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }
        
        String url = request.getRequestURI();
        
        // 页面静态化处理
        if(Commons.getInstance().getStaticPages().contains(url) && !"true".equals(request.getParameter(StaticPageUtils.KEYS))){
        	String rUrl = StaticPageUtils.BASEURL + url + StaticPageUtils.POSTFIX;
        	log.info("正在访问静态页面：" + rUrl);
        	request.getRequestDispatcher(rUrl).forward(request,response);
            return false;
        }
        
        // 调试模式，不过滤
        if(ConfigUtil.getJAdminProperty().getDebug()){
            UserVO user = new ClientENV(request.getSession()).getCurUser();
            if(user == null){
                SystemDao systemDao = SpringContext.getBean(SystemDao.class);
                UserVO vo = systemDao.login("admin", "111111");
                BaseAbstractController.getClientENV(request.getSession()).setCurUser(vo);
            }
        	Commons.getInstance().setAdminPageMenus(AdminPageUtils.getAdminPageMenus());
        	return true;
        }
        
        for (String u : Commons.getInstance().getExcludedUrls()) {
            if (url.equals(u) || (u.endsWith("*") && url.startsWith(u.replace("*", "")))) {
                return true;
            }
        }
        
        UserVO user = new ClientENV(request.getSession()).getCurUser();
        if (user == null) {
        	String title = "&#x8BF7;&#x767B;&#x5F55;&#x540E;&#x7EE7;&#x7EED;&#x64CD;&#x4F5C;....";
        	String location = "parent.window.location = '/login';";
        	AlertBusinessException.hiddleAlertException(request, response, title, "登陆超时，请重新登录！", location);
            return false;
        } else {
            return true;
        }
    }

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception arg3)
			throws Exception {
		// 整个请求处理完毕回调方法，即在视图渲染完毕时回调
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// 后处理回调方法，实现处理器的后处理（但在渲染视图之前）
	}
}