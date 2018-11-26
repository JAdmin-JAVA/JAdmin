package com.jadmin.vo.fundation.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:定义下拉框选中为某个值后，需要显示的字段的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageSelectShowVO {

    /** 选择下拉框的列名 */
	private String selectColumn;
	
    /** 选择下拉框的值 */
	private String value;

    /** 需要显示的column */
	private List<String> showColumn = new ArrayList<String>();

}
