package com.jadmin.vo.fundation.controller;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:日志的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageLogVO {

    /** 日志的类型 */
	private String type;
	
    /** 二级菜单的名字 */
	private String menuName;

    /** 日志的url */
	private String url;

    /** 日志的内容属性 */
	private String[] content;
}
