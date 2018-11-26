package com.jadmin.vo.fundation.controller;

import java.util.ArrayList;
import java.util.List;

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
public class AdminPageButtonVO {

    /** 页面的名字 */
	private String name;

    /** 页面的url */
	private String url;

    /** 图片文字 */
	private String imgChar;

    /** 颜色，目前只有red和green */
	private String color;

    /** 按钮要跳转的地址 */
	private String href;

    /** 一级菜单的名字 */
	private String menuName;

    /** 页面的名字 */
	private String pageName;

    /** 按钮是否是通用的列表页面 */
	private boolean isButtonTablePage = false;

    /** 按钮是通用的列表页面时，按钮点击后页面的class，用来给该页面继续添加通用列表页面 */
	private Class<?> buttonTablePageClass;

    /** 是否隐藏按钮 */
	private boolean hide;

    /** 如果按钮点击后是列表界面，按钮会存到改界面 */
	private List<AdminPageButtonVO> buttons = new ArrayList<AdminPageButtonVO>();
	
	public AdminPageButtonVO(String menuName, String pageName){
		this.menuName = menuName;
		this.pageName = pageName;
	}
	
	/**
	 * 获得button的全称
	 * @return
	 */
	public String getFullName() {
		return this.menuName + ";" + this.pageName + ";" + this.name;
	}
	
	public String getHrefInEdit(){
		return href.replace("javascript:", "javascript:inEdit_");
	}
	
}
