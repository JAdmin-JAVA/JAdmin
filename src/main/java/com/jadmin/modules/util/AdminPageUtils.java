package com.jadmin.modules.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.MethodLog;
import com.jadmin.modules.annotation.NoNeedLogin;
import com.jadmin.modules.annotation.PageStatic;
import com.jadmin.modules.annotation.list.AdminPageButton;
import com.jadmin.modules.annotation.list.AdminPageNoButton;
import com.jadmin.modules.annotation.list.ButtonTablePage;
import com.jadmin.modules.annotation.list.SinglePage;
import com.jadmin.modules.annotation.list.Tree;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.vo.entity.base.RoleModuleVO;
import com.jadmin.vo.entity.base.RoleVO;
import com.jadmin.vo.enumtype.AdminPageMenu;
import com.jadmin.vo.fundation.controller.AdminPageButtonVO;
import com.jadmin.vo.fundation.controller.AdminPageLogVO;
import com.jadmin.vo.fundation.controller.AdminPageMenuVO;
import com.jadmin.vo.fundation.controller.AdminPageVO;
import com.jadmin.vo.fundation.controller.ButtonTablePageVO;
import com.jadmin.vo.fundation.tool.Commons;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:后台管理页面相关工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
public class AdminPageUtils {
    
	public static String PACKAGE_NAME = ConfigUtil.BASE_PACKAGE + "controller";
    
    /**
     * 获取不需要登陆拦截的url
     * @return
     */
    public static List<String> getNoNeedLoginUrl(){
    	List<String> urls = new ArrayList<String>();
    	List<Class<?>> classes = ClassUtil.getClasses(PACKAGE_NAME);
    	for (Class<?> class1 : classes) {
    		String baseUrl = ClassUtil.getRequestMappingValue(class1, false);
			boolean classNoNeed = class1.getAnnotation(NoNeedLogin.class) != null;
    		// 获取该页面所有的方法
    		Method[] methods = class1.getMethods();
    		for (Method method : methods) {
    			boolean methodNoNeed = method.getAnnotation(NoNeedLogin.class) != null;
    			String[] url = ClassUtil.getRequestMappingValues(method, methodNoNeed);
    			if(url.length != 0 && (methodNoNeed || classNoNeed)) {
    				for (String u : url) {
    					urls.add(baseUrl + u);
					}
    			}
    		}
    	}
        log.info("未设置登陆拦截的url有：" + urls);
    	return urls;
    }
    
	/**
	 * 获取静态界面
	 * @return
	 */
	public static List<String> getStaticPages() {
		List<String> pages = new ArrayList<String>();
    	List<Class<?>> classes = ClassUtil.getClasses(PACKAGE_NAME);
    	for (Class<?> class1 : classes) {
    		String baseUrl = ClassUtil.getRequestMappingValue(class1, false);
    		// 获取该页面所有的方法
    		Method[] methods = class1.getMethods();
    		for (Method method : methods) {
    			// 如果按钮，直接有AdminPage，说明他就是二级菜单
    			PageStatic pageStaticAnnotation = method.getAnnotation(PageStatic.class);
    			if(pageStaticAnnotation != null){
    				// 初始化静态文件
    				final String url = baseUrl + ClassUtil.getRequestMappingValue(method, true);
    				try {
    					StaticPageUtils.flushPage(url); 
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
    				// 如果设置了定时刷新，放线程刷新
    				final int refreshTime = pageStaticAnnotation.refreshTime();
    				if(refreshTime > 0){
    					new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(refreshTime * 60 * 1000);
			    					StaticPageUtils.flushPage(url);
								} catch (Exception e) {
									log.error(e.getMessage(), e);
								}
							}
						}).start();
    				}
    				pages.add(url);
    			}
			}
		}
    	return pages;
	}
	
	/**
	 * 給据角色，获取用户拥有的菜单、页面、按钮
	 * @return
	 */
	public static List<AdminPageMenuVO> getHasAdminPageMenus(RoleVO role) {
    	List<AdminPageMenuVO> menu = new ArrayList<AdminPageMenuVO>();
		if(role == null || role.getRoleModule() == null){
			return menu;
		}
		// 如果是0，表示的是超级管理员权限，返回所有模块
		if(role.getRoleId().equals("0")){
			return Commons.getInstance().getAdminPageMenus();
		}
		Set<String> hasMenu = new HashSet<String>();
		Set<String> hasMenu2 = new HashSet<String>();
    	Set<String> hasPage = new HashSet<String>();
    	Set<String> hasButton = new HashSet<String>();
    	for (RoleModuleVO rmVO : role.getRoleModule()) {
    		hasMenu.add(rmVO.getModuleMenu1());
    		hasMenu2.add(rmVO.getModuleMenuFullName());
    		hasPage.add(rmVO.getModulePageFullName());
    		hasButton.add(rmVO.getModuleButtonFullName());
		}
    	for (AdminPageMenuVO apm : Commons.getInstance().getAdminPageMenus()) {
			if(hasMenu.contains(apm.getName())){
				// 如果has菜单，就把hasApm加到menu中
				AdminPageMenuVO hasApm = new AdminPageMenuVO();
				hasApm.setCss(apm.getCss());
				hasApm.setName(apm.getName());
				menu.add(hasApm);
				for (AdminPageMenuVO apm2 : apm.getMenus()) {
					if(hasMenu2.contains(apm2.getFullName())){
						AdminPageMenuVO hasApm2 = new AdminPageMenuVO();
						hasApm2.setCss(apm2.getCss());
						hasApm2.setName(apm2.getName());
						hasApm.getMenus().add(hasApm2);
						for (AdminPageVO ap : apm2.getPages()) {
							// 如果has页面，就加到hasApm（还有的菜单）中
							if(hasPage.contains(ap.getFullName())){
								AdminPageVO hasAp = new AdminPageVO(apm);
								hasAp.setName(ap.getName());
								hasAp.setUrl(ap.getUrl());
								hasApm2.getPages().add(hasAp);
								for (AdminPageButtonVO apb : ap.getButtons()) {
									if(hasButton.contains(apb.getFullName())){
										hasAp.getButtons().add(apb);
									}
								}
							}
						}
					}
				}
			}
		}
    	return menu;
	}
	

	/**
	 * 此方法以作废
     * 給据url获取一级菜单名字，当前页面名字、按钮名字
     * @return 
     */
    public static String[] findPageNameByUrl(String url){
    	for (AdminPageMenuVO apm : Commons.getInstance().getAdminPageMenus()) {
			for(AdminPageVO ap : apm.getPages()){
				if(url.equals(ap.getUrl())){
					return new String[]{apm.getName() , ap.getName()};
				}
				for (AdminPageButtonVO apb : ap.getButtons()) {
					if(url.equals(apb.getUrl())){
						return new String[]{apm.getName() , ap.getName(), apb.getName()};
					}
				}
			}
		}
    	return new String[]{"", "", ""};
    }
    

    /**
     * 給据name获取按钮集合，并通过角色权限过滤（）
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByNameAndRole(String name, RoleVO role){
    	List<AdminPageButtonVO> adminPageMenuVOs = findAdminPageButtonsByName(name);
		// 如果是0，表示的是超级管理员权限，返回所有模块
		if(role.getRoleId().equals("0")){
			return adminPageMenuVOs;
		}
    	List<AdminPageButtonVO> hasAdminPageMenuVOs = new ArrayList<AdminPageButtonVO>();
    	Set<String> hasButton = new HashSet<String>();
    	for (RoleModuleVO rmVO : role.getRoleModule()) {
    		hasButton.add(rmVO.getModuleButtonFullName());
		}
    	for (AdminPageButtonVO dpBu : adminPageMenuVOs) {
    		if(hasButton.contains(dpBu.getFullName())){
    			hasAdminPageMenuVOs.add(dpBu);
    		}
		}
    	return hasAdminPageMenuVOs;
    }
    
    /**
     * 給据url获取按钮集合，并通过角色权限过滤
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByUrlAndRole(String baseUrl, RoleVO role){
    	List<AdminPageButtonVO> adminPageMenuVOs = findAdminPageButtonsByUrl(baseUrl);
		// 如果是0，表示的是超级管理员权限，返回所有模块
		if(role.getRoleId().equals("0")){
			return adminPageMenuVOs;
		}
    	List<AdminPageButtonVO> hasAdminPageMenuVOs = new ArrayList<AdminPageButtonVO>();
    	Set<String> hasButton = new HashSet<String>();
    	for (RoleModuleVO rmVO : role.getRoleModule()) {
    		hasButton.add(rmVO.getModuleButtonFullName());
		}
    	for (AdminPageButtonVO dpBu : adminPageMenuVOs) {
    		if(hasButton.contains(dpBu.getFullName())){
    			hasAdminPageMenuVOs.add(dpBu);
    		}
		}
    	return hasAdminPageMenuVOs;
    }

    /** 
     * 給据name获取按钮集合
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByName(String name){
		List<AdminPageMenuVO> adminPageMenuVOs = Commons.getInstance().getAdminPageMenus();
		for (AdminPageMenuVO apm : adminPageMenuVOs) {
			for (AdminPageMenuVO apm2 : apm.getMenus()) {
				for (AdminPageVO adminPageVO : apm2.getAllPages()) {
					if(adminPageVO.getName().equals(name)){
						return adminPageVO.getButtons();
					}
				}
			}
		}
		return new ArrayList<AdminPageButtonVO>();
    }
    
    /** 
     * 給据url获取按钮集合
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByUrl(String baseUrl){
		List<AdminPageMenuVO> adminPageMenuVOs = Commons.getInstance().getAdminPageMenus();
		for (AdminPageMenuVO adminPageMenuVO1 : adminPageMenuVOs) {
			for (AdminPageMenuVO adminPageMenuVO2 : adminPageMenuVO1.getMenus()) {
				List<AdminPageVO> adminPageVOs = adminPageMenuVO2.getPages();
				for (AdminPageVO adminPageVO : adminPageVOs) {
					if(adminPageVO.getUrl().equals(baseUrl) || baseUrl.equals(adminPageVO.getClassName())){
						return adminPageVO.getButtons();
					}
				}
			}
		}
		return new ArrayList<AdminPageButtonVO>();
    }
    
    /**
     * 給据注解获取按钮集合（获得该控制层下面所有的按钮，无法非配权限功能）
     * @param btPage 
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByClass(Class<?> class1){
    	// 过滤掉声明不需要的按钮
		AdminPageNoButton adminPageNoButtonAnnotation = class1.getAnnotation(AdminPageNoButton.class);
		// 获取该页面所有的button
		Method[] methods = class1.getMethods();
		String baseUrl = ClassUtil.getRequestMappingValue(class1, false);
		return getButtons(null, null, adminPageNoButtonAnnotation, methods, baseUrl);
    }
    
    /**
     * 給据ButtonTablePage的class，获得该class下面的所有按钮
     * @param btPage 
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByButtonTablePage(Class<?> class1){
    	for(AdminPageMenuVO apm : Commons.getInstance().getAdminPageMenus()){
			for(AdminPageVO ap : apm.getPages()){
				for (AdminPageButtonVO apb : ap.getButtons()) {
					if(class1.equals(apb.getButtonTablePageClass())){
						return apb.getButtons();
					}
					// 此处应该递归调用，考虑到性能，暂时重复写代码吧，默认最多5层
					for (AdminPageButtonVO apb2 : apb.getButtons()) {
						if(class1.equals(apb2.getButtonTablePageClass())){
							return apb2.getButtons();
						}
						for (AdminPageButtonVO apb3 : apb2.getButtons()) {
							if(class1.equals(apb3.getButtonTablePageClass())){
								return apb3.getButtons();
							}
							for (AdminPageButtonVO apb4 : apb3.getButtons()) {
								if(class1.equals(apb4.getButtonTablePageClass())){
									return apb4.getButtons();
								}
							}
						}
					}
				}
			}
    	}
    	return new ArrayList<AdminPageButtonVO>();
    }
    
    /**
     * 給据注解获取按钮集合（获得该控制层下面所有的按钮，无法非配权限功能）
     * @param btPage 
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByClass(Class<?> class1, ButtonTablePage btPage){
    	// 过滤掉声明不需要的按钮
		AdminPageNoButton adminPageNoButtonAnnotation = class1.getAnnotation(AdminPageNoButton.class);
		AdminPage adminPageAnnotation = btPage.page().getAnnotation(AdminPage.class);
		// 获取该页面所有的button
		Method[] methods = class1.getMethods();
		String baseUrl = ClassUtil.getRequestMappingValue(class1, false);
		return getButtons(adminPageAnnotation.menu() + ";" + adminPageAnnotation.name(), btPage.name(), adminPageNoButtonAnnotation, methods, baseUrl);
    }
    
    /**
     * 給据url获取按钮集合
     * @return 
     */
    public static List<AdminPageButtonVO> findAdminPageButtonsByUrl(ButtonTablePage baseUrl){
		List<AdminPageMenuVO> adminPageMenuVOs = Commons.getInstance().getAdminPageMenus();
		for (AdminPageMenuVO adminPageMenuVO : adminPageMenuVOs) {
			List<AdminPageVO> adminPageVOs = adminPageMenuVO.getPages();
			for (AdminPageVO adminPageVO : adminPageVOs) {
				if(adminPageVO.getUrl().equals(baseUrl)){
					return adminPageVO.getButtons();
				}
			}
		}
		return new ArrayList<AdminPageButtonVO>();
    }

	/**
	 * 給据url获取需要记录日志的配置属性
	 * @param url
	 * @return
	 */
	public static AdminPageLogVO findLogMenthodByUrl(String url) {
    	for (AdminPageLogVO apl : Commons.getInstance().getLogMenthods()) {
    		if(apl.getUrl().equals(url)){
    			return apl;
    		}
    	}
		return null;
	}
	
	/**
	 * AdminPageMenu 转 AdminPageMenuVO
	 * @param apm
	 * @return
	 */
	public static AdminPageMenuVO getAdminPageMenuVO(AdminPageMenu apm) {
		AdminPageMenuVO adminPageMenu = new AdminPageMenuVO();
		adminPageMenu.setName(apm.getLabel());
		adminPageMenu.setCss(apm.getCss());
		adminPageMenu.setEnName(apm.name());
		if(apm.getMenu() != null) {
			adminPageMenu.setUpMemu(apm.getMenu().getLabel());
		}
		return adminPageMenu;
	}
	
	/**
     * 获取后台管理系统一级菜单的集合
     * @return 
     */
    public static List<AdminPageMenuVO> getAdminPageMenus(){
    	// 后台管理系统一级菜单的集合 change by jjy 2018-09-18
    	List<AdminPageMenuVO> adminPageMenus = new ArrayList<AdminPageMenuVO>();
    	Map<AdminPageMenu, AdminPageMenuVO> tempAdminPageMenuMap = new HashMap<>();
    	for(AdminPageMenu apm : AdminPageMenu.values()){
    		if(apm.getMenu() == null){
    			AdminPageMenuVO adminPageMenu = getAdminPageMenuVO(apm);
        		adminPageMenus.add(adminPageMenu);
        		tempAdminPageMenuMap.put(apm, adminPageMenu);
    		}
    	}
    	// 后台管理系统二级菜单的集合
    	for(AdminPageMenu apm : AdminPageMenu.values()){
    		if(apm.getMenu() != null){
    			tempAdminPageMenuMap.get(apm.getMenu()).getMenus().add(getAdminPageMenuVO(apm));
    		}
    	}
    	// 获取一级菜单下面的页面，并放入adminPageMenus中；声明的类注解AdminPage的控制层，表示一个页面
    	List<Class<?>> classes = ClassUtil.getClasses(PACKAGE_NAME);
    	for (Class<?> class1 : classes) {
    		AdminPage adminPageAnnotation = class1.getAnnotation(AdminPage.class);
    		SinglePage singlePageAnnotation = class1.getAnnotation(SinglePage.class);
    		Tree treeAnnotation = class1.getAnnotation(Tree.class);
    		String baseUrl = ClassUtil.getRequestMappingValue(class1, false);
    		AdminPageVO adminPage = null;
    		if(adminPageAnnotation != null && !adminPageAnnotation.hide()){
    			adminPage = new AdminPageVO(adminPageAnnotation.menu());
    			adminPage.setName(adminPageAnnotation.name());
    			adminPage.setTargetBlank(adminPageAnnotation.targetBlank());
    			// 如果是单页面，默认url为toUpdate
    			if(singlePageAnnotation != null && StringUtils.isNotBlank(singlePageAnnotation.value())){
    				adminPage.setUrl(baseUrl + "/toUpdate");
    			}else if(treeAnnotation != null){
    				adminPage.setUrl(baseUrl + "/getTree");
    			}else {
    				adminPage.setUrl(baseUrl + "/getAll");
    			}
    			adminPage.setRefreshTime(adminPageAnnotation.refreshTime());
    			adminPage.setClassName(class1.getName());
    			adminPage.setOnly(adminPageAnnotation.only());
    			// 通过菜单名字，将该页面放入adminPageMenus
    			addAdminPage(adminPageAnnotation, adminPageMenus, adminPage);
    		}
    		// 过滤掉声明不需要的按钮
    		AdminPageNoButton adminPageNoButtonAnnotation = class1.getAnnotation(AdminPageNoButton.class);
    		// 获取该页面所有的button
    		Method[] methods = class1.getMethods();
    		for (Method method : methods) {
    			// 如果按钮，直接有AdminPage，说明他就是二级菜单
    			AdminPage methodAdminPageAnnotation = method.getAnnotation(AdminPage.class);
    			if(methodAdminPageAnnotation != null){
    				AdminPageVO methodAdminPage = new AdminPageVO(methodAdminPageAnnotation.menu());
    				methodAdminPage.setName(methodAdminPageAnnotation.name());
    				methodAdminPage.setTargetBlank(methodAdminPageAnnotation.targetBlank());
    				methodAdminPage.setUrl(baseUrl + ClassUtil.getRequestMappingValue(method, true));
    				methodAdminPage.setOnly(methodAdminPageAnnotation.only());
        			addAdminPage(methodAdminPageAnnotation, adminPageMenus, methodAdminPage);
        			addButton(adminPageNoButtonAnnotation, method, baseUrl, methodAdminPage);
    			// 否则就是一个button (单页面的没有按钮)
    			}else if(adminPage != null && 
    					(singlePageAnnotation == null || StringUtils.isBlank(singlePageAnnotation.value()))){
    				addButton(adminPageNoButtonAnnotation, method, baseUrl, adminPage);
    			}
			}
		}
    	
    	// 扫描全包，自动加载ButtonTablePage注解中定义的button 遍历所有的AdminPageButtonVO，并排序再将按钮注入到相应的页面中
    	intButtonTablePage(classes, adminPageMenus);
    	
    	// 处理页面显示顺序
		final List<String> adminPageSoft = ConfigUtil.getJAdminProperty().getAdminPage();
    	for(AdminPageMenuVO apm : adminPageMenus){
    		for (AdminPageMenuVO apm2 : apm.getMenus()) {
    			Collections.sort(apm2.getPages(), new Comparator<AdminPageVO>() {
        			public int compare(AdminPageVO arg0, AdminPageVO arg1) {
        				for(String str : adminPageSoft){
        					if(str.equals(arg0.getName())){
        						return -1; 
        					}
        					if(str.equals(arg1.getName())){
        						return 1;
        					}
        				}
        				return 0;
        			}
        		});
			}
    	}
    	
    	// 处理按钮显示顺序 
		final List<String> adminButtonSoft = ConfigUtil.getJAdminProperty().getAdminButton();
    	for(AdminPageMenuVO apm : adminPageMenus){
    		for(AdminPageMenuVO apm2 : apm.getMenus()){
	    		for (AdminPageVO ap : apm2.getPages()) {
	    			softAdminPageButton(adminButtonSoft, ap.getButtons());
					for (AdminPageButtonVO apb1 : ap.getButtons()) {
						softAdminPageButton(adminButtonSoft, apb1.getButtons());
						for (AdminPageButtonVO apb2 : apb1.getButtons()) {
							softAdminPageButton(adminButtonSoft, apb2.getButtons());
							for (AdminPageButtonVO apb3 : apb2.getButtons()) {
								softAdminPageButton(adminButtonSoft, apb3.getButtons());
								for (AdminPageButtonVO apb4 : apb3.getButtons()) {
									softAdminPageButton(adminButtonSoft, apb4.getButtons());
								}
							}
						}
					}
				}
    		}
    	}
    	
    	return adminPageMenus;
    }
    
    /**
     * 排序一个页面的按钮
     * @param adminButtonSoft
     * @param buttons
     */
    private static void softAdminPageButton(final List<String> adminButtonSoft, List<AdminPageButtonVO> buttons){
    	if(buttons == null || buttons.isEmpty()){
    		return;
    	}
    	Collections.sort(buttons, new Comparator<AdminPageButtonVO>() {
			public int compare(AdminPageButtonVO arg0, AdminPageButtonVO arg1) {
				for(String str : adminButtonSoft){
					if(str.equals(arg0.getName())){
						return -1; 
					}
					if(str.equals(arg1.getName())){
						return 1;
					}
				}
				return 0;
			}
		});
    }
    
    /**
     * 扫描全包，自动加载ButtonTablePage注解中定义的button 遍历所有的AdminPageButtonVO，并排序再将按钮注入到相应的页面中
     * @param classes
     * @param adminPageMenus
     */
    private static void intButtonTablePage(List<Class<?>> classes, List<AdminPageMenuVO> adminPageMenus){
    	List<ButtonTablePageVO> btpAnnotations = new ArrayList<ButtonTablePageVO>();
    	for (Class<?> class1 : classes) {
    		// 遍历所有的AdminPageButtonVO
    		ButtonTablePage btpAnnotation = class1.getAnnotation(ButtonTablePage.class);
			if(btpAnnotation != null && !btpAnnotation.inEdit()){
				ButtonTablePageVO btpVO = new ButtonTablePageVO();
				btpVO.setBtpAn(btpAnnotation);
				btpVO.setClass1(class1);
				btpVO.setLevel(getLevelByButtonTablePage(btpAnnotation));
				btpAnnotations.add(btpVO);
			}
		} 
    	// 排序，按照级别排序，2级的先加载出来，要不然先加载3级的肯定会有问题
    	Collections.sort(btpAnnotations, new Comparator<ButtonTablePageVO>() {
			public int compare(ButtonTablePageVO arg0, ButtonTablePageVO arg1) {
				return arg0.getLevel() - arg1.getLevel();
			}
		});
    	
    	// 一个个找到 定义ButtonTablePage注解的按钮，所属的页面
    	for (ButtonTablePageVO btpVO : btpAnnotations) {
    		ButtonTablePage btpAnnotation = btpVO.getBtpAn();
    		// 按钮上一个页面的控制层页面的class
    		String baseUrl = ClassUtil.getRequestMappingValue(btpVO.getClass1(), false);
    		String buttonMeno = btpAnnotation.page().toString() + "， " + btpAnnotation.name();
			// TODO:刚开始还没有 menuName 和 pageName，这两个属性是权限的时候用得
			AdminPageButtonVO adminPageButton = getAdminPageButtonVO(null, null, 
					baseUrl, btpAnnotation.name(), baseUrl + "/getAll", btpAnnotation.imgChar(),
					AdminPageButton.JUMP, AdminPageButton.RADIO, -1, false, "", btpAnnotation.color(), btpAnnotation.hide());
			adminPageButton.setButtonTablePage(true);
			// 获得该按钮页面的所有按钮 
			adminPageButton.setButtons(findAdminPageButtonsByClass(btpVO.getClass1()));
			// 找到按钮应该所属哪个页面的按钮组（通过ButtonTablePage中的page找寻，如果page中的注解是AdminPageButton，就是该页面的按钮组，如果是ButtonTablePage，就是这个按钮页面的按钮组）
			AdminPage adminPageAn = btpAnnotation.page().getAnnotation(AdminPage.class);
			adminPageButton.setButtonTablePageClass(btpVO.getClass1());
			// 如果page中的注解是AdminPageButton，就是该页面的按钮组
			if(adminPageAn != null){
	    		List<AdminPageButtonVO> list = findButtonListByAdminPage(adminPageMenus, adminPageAn, buttonMeno);
	    		//TODO:临时这样写，头都大了
	    		if(!list.isEmpty()){
	    			adminPageButton.setPageName(list.get(0).getPageName());
	    			adminPageButton.setMenuName(list.get(0).getMenuName());
	    		}
				list.add(adminPageButton);
			// 如果该按钮从属的页面不是二级页面，寻找其所在的按钮组，并添加进去
			}else if(btpAnnotation.page().getAnnotation(ButtonTablePage.class) != null){
	    		List<AdminPageButtonVO> list = findButtonListByClass(adminPageMenus, btpAnnotation.page(), buttonMeno);
				list.add(adminPageButton);
			}
		}  
    }
    
    /**
     * 通过ButtonTablePage注解中的class找到对应的按钮组
     * @param adminPageMenus
     * @param class1
     * @return
     */
    private static List<AdminPageButtonVO> findButtonListByClass(List<AdminPageMenuVO> adminPageMenus, Class<?> page, String buttonMeno){
    	for (AdminPageMenuVO apm : adminPageMenus) {
    		for(AdminPageMenuVO apm2 : apm.getMenus()){
    			for(AdminPageVO ap : apm2.getPages()){
    				for (AdminPageButtonVO apb : ap.getButtons()) {
    					if(page.equals(apb.getButtonTablePageClass())){
    						return apb.getButtons();
    					}
    					// TODO:再循环一层，最多支持4层
    					for (AdminPageButtonVO apbv : apb.getButtons()) {
    						if(page.equals(apbv.getButtonTablePageClass())){
    							return apbv.getButtons();
    						}
    					}
    				}
    			}
        	}
		}
    	throw new BusinessException("无法将按钮[" + buttonMeno + "]注入自动注入到对应的页面中，请检查ButtonTablePage注解的有效性！");
    }
    
    /**
     * 通过adminPageAn找到对应的按钮组
     * @param adminPageMenus
     * @param class1
     * @return
     */
    private static List<AdminPageButtonVO> findButtonListByAdminPage(List<AdminPageMenuVO> adminPageMenus, AdminPage adminPageAn, String buttonMeno){
		String menuName1 = adminPageAn.menu().getMenu().getLabel();
		String menuName2 = adminPageAn.menu().getLabel();
    	String name = adminPageAn.name();
    	for(AdminPageMenuVO apm : adminPageMenus){
    		if(apm.getName().equals(menuName1)){
    			for (AdminPageMenuVO apm2 : apm.getMenus()) {
    				if(apm2.getName().equals(menuName2)){
    					for(AdminPageVO ap : apm2.getPages()){
    	    				if(ap.getName().equals(name)){
    	    					return ap.getButtons();
    	    				}
    	    			}
    				}
				}
    		}
    	}
    	throw new BusinessException("无法将按钮[" + buttonMeno + "]注入自动注入到对应的页面中，请检查AdminPage注解的有效性！");
    }
    

    /**
     * 給据btpAnnotation获得该按钮的级别，2级，3级，最多支持9级
     * @param btpAnnotation
     * @return
     */
    private static int getLevelByButtonTablePage(ButtonTablePage btpAnnotation){
    	return getLevelByButtonTablePage(btpAnnotation, btpAnnotation.page(), 2);
    }
    
    /**
     * 給据btpAnnotation获得该按钮的级别，2级，3级，最多支持9级
     * @param btpAnnotation
     * @param class1 页面的class
     * @return
     */
    private static int getLevelByButtonTablePage(ButtonTablePage btpAnnotation, Class<?> class1, int level){
    	if(class1.getAnnotation(AdminPage.class) != null){
    		return level;
    	}
    	ButtonTablePage btpAn = class1.getAnnotation(ButtonTablePage.class);
    	if(btpAn == null || level == 9){
    		String buttonMeno = btpAnnotation.page().toString() + "， " + btpAnnotation.name();
    		throw new BusinessException(class1.getName() + "类未声明AdminPage注解或者ButtonTablePage，无法将按钮[" + buttonMeno + "]注入到该类对应的页面中！");
    	}
    	// 递归获取级别
    	return getLevelByButtonTablePage(btpAnnotation, btpAn.page(), level + 1);
    }
    
    /**
     * 获得AdminPageButtonVO对象
     * @param name
     * @param url
     * @param imgChar
     * @param href
     * @param color
     */
    private static AdminPageButtonVO getAdminPageButtonVO(String menuName, String pageName, String baseUrl, String name, String url, 
    		String imgChar, int showType, int dataType, int layerHeight, boolean ajaxConfirm, String clickCustomHref, String color, boolean hide){
    	AdminPageButtonVO adminPageButton = new AdminPageButtonVO(menuName, pageName);
		adminPageButton.setName(name);
		adminPageButton.setUrl(url);
		adminPageButton.setImgChar(imgChar);
		adminPageButton.setHide(hide); // 列表按钮时有效
		String href = clickCustomHref;
		/**
		 * list页面通用的button按钮
		 * url：要跳转/弹出/ajax请求的url
		 * name：
		 * showType：1、将当前页面跳转到 指定url 2、将当期页面通过 layer弹出来 （默认） 3、ajax异步请求服务器，然后弹出处理结果
		 * dataType：1、不选择任何数据 即可触发 （默认） 2、必须选择一个数据 才能触发 3、必须选择一个或多个数据 才能触发
		 * ajaxConfirm：showType为3时 才有效，是否弹出是否提示框后，再进行ajax操作
		 * layerHeight：showType为2时 才有效，layer的高度，为空时默认为当前页面高度-50
		 */
		if(StringUtils.isBlank(href)){
			href = "javascript:msunCommonButton('" + url + "', '" + name + "', " + showType + " , " + dataType + ", " + ajaxConfirm + ", " + layerHeight + ");";
		}
		adminPageButton.setHref(href.replace("${baseUrl }", baseUrl).replace("${baseUrl}", baseUrl));
		if("green".equals(color)){
			adminPageButton.setColor("btn-primary");
		}else if("red".equals(color)){
			adminPageButton.setColor("btn-danger");
		}else {
			adminPageButton.setColor("btn-" + color);
		}
		return adminPageButton;
    }
    
	/**
     * 记录有Log注解的方法 
     * @return 
     */
    public static List<AdminPageLogVO> getLogMenthods(){
		// 记录有Log注解的方法 
    	List<AdminPageLogVO> aplList = new ArrayList<AdminPageLogVO>();
    	List<Class<?>> classes = ClassUtil.getClasses(PACKAGE_NAME);
    	for (Class<?> class1 : classes) {
    		Method[] methods = class1.getMethods();
    		AdminPage apAnno = class1.getAnnotation(AdminPage.class);
    		String baseUrl = ClassUtil.getRequestMappingValue(class1, false);
    		for (Method method : methods) {
    			MethodLog logAnnotation = method.getAnnotation(MethodLog.class);
    			AdminPageButton apbAnnotation = method.getAnnotation(AdminPageButton.class);
    			if(logAnnotation != null){
    				AdminPage apAnnoTemp = apAnno;
    				if(method.getAnnotation(AdminPage.class) != null){
    					apAnnoTemp = method.getAnnotation(AdminPage.class);
    				}
					AdminPageLogVO aplVO = new AdminPageLogVO();
					aplVO.setContent(logAnnotation.content());
					if(StringUtils.isNotBlank(logAnnotation.type())){
						aplVO.setType(logAnnotation.type());
					}else if(apbAnnotation != null){
						aplVO.setType(apbAnnotation.name());
					}
					aplVO.setUrl(baseUrl + ClassUtil.getRequestMappingValue(method, false));
    				if(apAnnoTemp != null){
    					aplVO.setMenuName(apAnnoTemp.menu().getLabel() + ">>" + apAnnoTemp.name() + ">>");
    				}
    				aplList.add(aplVO);
    			}
    		}
    	}
    	return aplList;
    }
    
    /**
     * 通过菜单名字，将该页面放入adminPageMenus
     * @param adminPageAnnotation
     * @param adminPageMenus
     * @param adminPage
     */
    private static void addAdminPage(AdminPage adminPageAnnotation, List<AdminPageMenuVO> adminPageMenus, AdminPageVO adminPage){
		for (AdminPageMenuVO apm : adminPageMenus) {
			if(apm.getName().equals(adminPageAnnotation.menu().getMenu().getLabel())){
				for (AdminPageMenuVO apm2 : apm.getMenus()) {
					if(apm2.getName().equals(adminPageAnnotation.menu().getLabel())){
						apm2.getPages().add(adminPage);
					}
				}
			}
		}
    }
    
    /**
     * 为adminPage中的方法添加按钮vo
     * @param method
     * @param baseUrl
     * @param adminPage
     */
    private static void addButton(AdminPageNoButton adminPageNoButtonAnnotation, Method method, String baseUrl, AdminPageVO adminPage){
    	AdminPageButtonVO buttonVO = getButton(adminPage.getFullMenuName(), adminPage.getName(), adminPageNoButtonAnnotation, method, baseUrl);
    	if(buttonVO != null){
    		adminPage.getButtons().add(buttonVO);
    	}
    }

    /**
     * 获取方法中的button属性
     */
    private static List<AdminPageButtonVO> getButtons(String menuName, String pageName, AdminPageNoButton adminPageNoButtonAnnotation, Method[] methods, String baseUrl){
    	List<AdminPageButtonVO> buttons = new ArrayList<AdminPageButtonVO>();
    	for (Method method : methods) {
    		AdminPageButtonVO buttonVO = getButton(menuName, pageName, adminPageNoButtonAnnotation, method, baseUrl);
        	if(buttonVO != null){
        		buttons.add(buttonVO);
        	}
		}
    	return buttons;
    }
    
    /**
     * 获取方法中的button属性
     */
    private static AdminPageButtonVO getButton(String menuName, String pageName, AdminPageNoButton adminPageNoButtonAnnotation, Method method, String baseUrl){
		Annotation anno = method.getAnnotation(AdminPageButton.class);
		if(anno != null){
			AdminPageButton adminPageButtonAnnotation = (AdminPageButton) anno;
			String url = ClassUtil.getRequestMappingValue(method, true);
			if(adminPageNoButtonAnnotation != null){
				String[] noButtons = adminPageNoButtonAnnotation.value();
				for (String noButton : noButtons) {
					if(("/" + noButton).equals(url)){
						return null;
					}
				}
			}
			AdminPageButtonVO adminPageButton = getAdminPageButtonVO(menuName, pageName, 
					baseUrl, adminPageButtonAnnotation.name(), baseUrl + url, adminPageButtonAnnotation.imgChar(),
					adminPageButtonAnnotation.showType(), adminPageButtonAnnotation.dataType(), adminPageButtonAnnotation.layerHeight(),
					adminPageButtonAnnotation.ajaxConfirm(), adminPageButtonAnnotation.clickCustomHref(), adminPageButtonAnnotation.color(), false);
			return adminPageButton;
		}
		return null;
    }

}
