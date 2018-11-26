package com.jadmin.vo.fundation.controller;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:报表vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class ReportPage{
	
	private List<ReportCol> columns;
	
	private String name;
	
	public ReportPage(String name, List<ReportCol> columns){
		this.name = name;
		this.columns = columns;
	}
}
