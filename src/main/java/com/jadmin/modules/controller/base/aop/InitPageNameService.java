package com.jadmin.modules.controller.base.aop;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.list.AdminPageButton;
import com.jadmin.modules.annotation.list.ButtonTablePage;
import com.jadmin.modules.annotation.list.SinglePage;
import com.jadmin.modules.util.ClassUtil;
import com.jadmin.modules.util.ConfigUtil;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:初始化页面的标题
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
@Aspect
@Slf4j
public class InitPageNameService {

    /** 給据pageName，记录url，用来返回 */
	private static Map<String, String> lastUrls = new HashMap<String, String>();

	/**
	 * @param point
	 * @throws Throwable
	 */
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void methodCachePointcut() {

	}
	
	/**
	 * 递归获取ButtonTablePage注解的名字
	 * @param pList
	 * @param class1
	 */
	private void getPageNameByButtonTablePage(List<String> pList, Class<?> class1){
		ButtonTablePage btAn = class1.getAnnotation(ButtonTablePage.class);
		if(btAn != null){
			pList.add(btAn.name());
			AdminPage apAn = btAn.page().getAnnotation(AdminPage.class);
			if(apAn != null){
				pList.add(apAn.name());
				pList.add(apAn.menu().getLabel());
				return;
			}
			ButtonTablePage bAn = class1.getAnnotation(ButtonTablePage.class);
			if(bAn != null){
				getPageNameByButtonTablePage(pList, bAn.page());
			}
		}
	}
	
	@Around("methodCachePointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	request.setCharacterEncoding("UTF-8");

		Object object;
		try {
			object = point.proceed();
		} catch (Exception e) {
			// 异常处理记录日志..log.error(e);
			throw e;
		}
		
		// 給据AdminPage或者ButtonTablePage注解，获得一级菜单、页面的名字
		List<String> pageNameList = new ArrayList<String>();
		Method method = ClassUtil.getNowMethod(point);
		Class<?> class1 = point.getTarget().getClass();
		AdminPage adminPageAn = class1.getAnnotation(AdminPage.class);
		if(adminPageAn == null){
			adminPageAn = method.getAnnotation(AdminPage.class);
		}
		if(adminPageAn != null){
			pageNameList.add(adminPageAn.menu().getLabel());
			pageNameList.add(adminPageAn.name());
		}else{
			// 递归获取ButtonTablePage注解的名字
			List<String> pList = new ArrayList<String>();
			getPageNameByButtonTablePage(pList, class1);
			for (int i = pList.size() - 1; i >= 0; i--) {
				pageNameList.add(pList.get(i));
			}
		}
        //获取到这个类上面的方法上面的注释
		AdminPageButton adminPageButtonAn = method.getAnnotation(AdminPageButton.class);
		if(adminPageButtonAn != null){
			SinglePage singlePageAn = class1.getAnnotation(SinglePage.class);
			// singlePageId如果是单页面，不显示adminPageButtonAn.name()
			if(adminPageAn == null || singlePageAn == null || StringUtils.isBlank(singlePageAn.value())){
				pageNameList.add(adminPageButtonAn.name());
			}
		}
		Object rePageName = request.getAttribute("pageName");
		if(rePageName != null && rePageName instanceof String){
			pageNameList.add(rePageName.toString());
		}
		
        String pageNameString = getPageNameString(pageNameList);
        
		// 获取@ButtonTablePage上一个页面vo的主题名字
		Object mainName = request.getAttribute("mainName");
		if(mainName != null && mainName instanceof String){
			pageNameList.add(mainName.toString());
		}
		
        String url = request.getRequestURI();
        log.info("请求：" + pageNameList + "， url ：" + url);
        
        // 給据AdminPage、AdminPageButton注解获得当前页面的一级菜单和页面的名字
    	request.setAttribute("pageName", pageNameList.toArray(new String[]{}));
    	
    	// 获取上一次请求的url
    	if(!pageNameList.isEmpty()){
            if(!lastUrls.containsKey(pageNameString)){
            	lastUrls.put(pageNameString, url);
            }
            if(pageNameString.contains(".")){
                String lastUrl = lastUrls.get(pageNameString.substring(0, pageNameString.lastIndexOf(".")));
                String baseWhere = request.getParameter("baseWhere");
                if(StringUtils.isNotBlank(baseWhere)){
                	lastUrl = lastUrl + "?id=" + baseWhere.substring(baseWhere.indexOf("=") + 1);
                }
                request.setAttribute("lastUrl", lastUrl);
            }
    	}
    	
    	// 支持手机版
    	if(object instanceof String){
    		if( ("m".equals(request.getParameter("show")) || isMobileDevice(request.getHeader("user-agent"))) && checkHasWapJsp(object.toString())){
    			object = "wmobile/" + object;
    		}
    	}
        
		return object;
	}
	
	public static boolean isMobileDevice(String requestHeader){
		/**
		 * android : 所有android设备
		 * mac os : iphone ipad
		 * windows phone:Nokia等windows系统的手机
		 */
		String[] deviceArray = new String[]{"android","mac os","windows phone"};
		if(requestHeader == null){
			return false;
		}
		requestHeader = requestHeader.toLowerCase();
		for(int i=0;i<deviceArray.length;i++){
			if(requestHeader.indexOf(deviceArray[i])>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查看是否有jsp文件
	 * @param jspPath
	 * @return
	 */
	public static boolean checkHasWapJsp(String jspPath){
		File file = new File(ConfigUtil.getJAdminProperty().getFileBasePath() + "/src/main/webapp/WEB-INF/jsp/wmobile/" + jspPath + ".jsp");
		return file.isFile();
	}
	
	/**
	 * 用.拼接下pageNameList
	 * @param pageNameList
	 * @return
	 */
	private String getPageNameString(List<String> pageNameList){
		StringBuffer sb = new StringBuffer();
		for (String string : pageNameList) {
			sb.append(string + ".");
		}
		if(sb.length() != 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	// 方法运行出现异常时调用	
	public void afterThrowing(Exception ex) {
		log.error(ex.getMessage(), ex);
	}
}

