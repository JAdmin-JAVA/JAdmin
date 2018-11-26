package com.jadmin.vo.fundation.controller;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:后台管理列表页面的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageTableColumnVO {

    /** 字段 */
	private String column;

    /** 导出时用的字段 */
	private String exportColumn;

    /** 字段是否可以导出 */
	private boolean export;
	
    /** 字段的类型 */
	private String type;

    /** 标签  */
	private String name;
	
    /** 值 */
	private String value;
	
	/** 数据词典的code */
	private String selectCode;
	
	/** 是否在搜索组件中显示 */
	private boolean search;
	
	/** 是否是图片 */
	private boolean img;
	
	/** 视频路径 */
	private String videoPath;
	
	/** 显示的最大长度，默认 显示全部 */
	private int maxLength;
	
	/** order by  */
	private int orderBy;
	
	/** 搜索类型  */
	private int searchLike;
	
}
