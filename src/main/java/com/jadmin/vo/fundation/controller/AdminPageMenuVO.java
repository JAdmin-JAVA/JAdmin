package com.jadmin.vo.fundation.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:后台管理页面一级菜单的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageMenuVO {

    /** 菜单的英文名字 */
	private String enName;

    /** 菜单的名字 */
	private String name;
	
    /** 菜单的样式 */
	private String css;
	
    /** 菜单下面的菜单 */
	private List<AdminPageMenuVO> menus = new ArrayList<AdminPageMenuVO>();

    /** 菜单下面的页面 */
	private List<AdminPageVO> pages = new ArrayList<AdminPageVO>();

    /** 父级菜单 */
	private String upMemu;
	
	/**
	 * 获取所有的页面，包括列表的按钮列表页面
	 * @return
	 */
	public List<AdminPageVO> getAllPages(){
		List<AdminPageVO> allPages = new ArrayList<AdminPageVO>();
		for (AdminPageVO page : pages) {
			List<AdminPageButtonVO> buttons = page.getButtons();
			// 如果buttons下面还有button，说明是按钮列表页面
			List<AdminPageButtonVO> pageButtons = new ArrayList<AdminPageButtonVO>();
			AdminPageVO page1 = new AdminPageVO(upMemu, name);
			page1.setName(page.getName());
			page1.setTargetBlank(page.isTargetBlank());
			allPages.add(page1);
			for (AdminPageButtonVO button : buttons) {
				if(!button.getButtons().isEmpty()){
					AdminPageVO tempVO = new AdminPageVO(upMemu, name);
					tempVO.setName(button.getName());
					tempVO.setMenuName1(null);
					tempVO.setMarginLeft("10px");
					List<AdminPageButtonVO> tempPageButtons = new ArrayList<AdminPageButtonVO>();
					for (AdminPageButtonVO button1 : button.getButtons()) {
						button1.setMenuName(page.getMenuName1());
						button1.setPageName(tempVO.getName());
						if(!button1.getButtons().isEmpty()){
							AdminPageVO tempVO1 = new AdminPageVO(upMemu, name);
							tempVO1.setName(button1.getName());
							for (AdminPageButtonVO button22 : button1.getButtons()) {
								button22.setMenuName(page.getMenuName1());
								button22.setPageName(tempVO1.getName());
							}
							tempVO1.setButtons(button1.getButtons());
							tempVO1.setMenuName1(page1.getMenuName1());
							tempVO1.setMarginLeft("20px");
							allPages.add(tempVO1);
							tempPageButtons.add(button1);
						}else{
							tempPageButtons.add(button1);
						}
					}
					tempVO.setButtons(tempPageButtons);
					allPages.add(tempVO);
					pageButtons.add(button);
				}else{
					pageButtons.add(button);
				}
			}
			page1.setButtons(pageButtons);
		}
		return allPages;
	}
	
	/**
	 * @return 如果是该菜单只有一个唯一页面，返回唯一页面的url
	 */
	public String getOnlyUrl() {
		if(pages.size() == 1 && pages.get(0).getName().equals(name)) {
			return pages.get(0).getUrl();
		}
		return null;
	}
	
	/**
	 * @return 获取全称
	 */
	public String getFullName() {
		return upMemu + ";" + name;
	}
	
}
