package com.jadmin.vo.fundation.controller;

import com.jadmin.vo.enumtype.JavaType;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:后台管理列表页面搜索的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageSearchColumnVO {
	
    /** 字段 */
	private String column;
	
    /** 字段的类型 */
	private String type;

    /** 搜索的标签  */
	private String name;

    /** 搜索字段，查询时的Java类型  */
	private JavaType JavaType;
	
    /** 搜索的初始值，默认列表界面查询时，会加上改值 */
	private Object initDefaultValue;
	
	/** 数据词典的code */
	private String selectCode;
	
	/** 搜索的方式，详见上面枚举 */
	private int like;
}
