package com.jadmin.vo.fundation.controller;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:报表列属性vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class ReportCol{
	
	private String column;
	
	private String name;
	
	private String selectCode;
	
	private boolean isSelect;
	
	public ReportCol(String column, String name, String selectCode, boolean isSelect){
		this.column = column;
		this.name = name;
		this.isSelect = isSelect;
		this.selectCode = selectCode;
	}
	
	public ReportCol(String column, String name, boolean isSelect){
		this.column = column;
		this.name = name;
		this.isSelect = isSelect;
	}

	public String getColumn1() {
		return column.replace(".", "");
	}
	
}
