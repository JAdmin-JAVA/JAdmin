package com.jadmin.vo.fundation.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jadmin.vo.enumtype.AdminPageMenu;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:后台管理页面的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageVO {

    /** 页面的名字 */
	private String name;

    /** 一级菜单的名字 */
	private String menuName1;

    /** 二级菜单的名字 */
	private String menuName2;

    /** 页面的url */
	private String url;

    /** 该页面是否二级菜单唯一的页面 */
	private boolean only;

    /** 自动刷新页面，单位为秒 */
	private String refreshTime;

    /** css的样式，按钮表格页面时10 */
	private String marginLeft = "0";
	
	/** 弹出到新的标签 */
	private boolean targetBlank;
	
	/** 是否隐藏 */
	private boolean hide;

    /** 类名 */
	private String className;

    /** 页面的按钮 */
	private List<AdminPageButtonVO> buttons = new ArrayList<AdminPageButtonVO>();
	
	public AdminPageVO(String menuName1, String menuName2){
		this.menuName1 = menuName1;
		this.menuName2 = menuName2;
	}
	
	public AdminPageVO(AdminPageMenu adminPageMenu){
		this.menuName1 = adminPageMenu.getMenu().getLabel();
		this.menuName2 = adminPageMenu.getLabel();
	}
	
	public AdminPageVO(AdminPageMenuVO apm) {
		this.menuName1 = apm.getUpMemu();
		this.menuName2 = apm.getName();
	}
	
	public String getName() {
		if(isOnly() && StringUtils.isBlank(name)) {
			return menuName2;
		}
		return name;
	}

	/**
	 * 获得page的全称
	 * @return
	 */
	public String getFullName() {
		return menuName1 + ";" + menuName2 + ";" + getName();
	}

	/**
	 * 获得menu的全称
	 * @return
	 */
	public String getFullMenuName() {
		return menuName1 + ";" + menuName2;
	}
}
