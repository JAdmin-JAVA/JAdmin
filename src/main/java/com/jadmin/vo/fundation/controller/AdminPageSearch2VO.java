package com.jadmin.vo.fundation.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:后台管理列表页面搜索的总vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageSearch2VO {
	
	/** 所有的搜索列表 */
	private List<AdminPageTableColumnVO> searchColumns = new ArrayList<AdminPageTableColumnVO>();
	
	public AdminPageSearch2VO(List<AdminPageTableColumnVO> searchColumns){
		this.searchColumns = searchColumns;
	}
	
	/**
	 * 获取搜索类型的列表
	 * @return
	 */
	public List<AdminPageTableColumnVO> getTypeSearchColumns(){
		List<AdminPageTableColumnVO> typeSearchColumns = new ArrayList<AdminPageTableColumnVO>();
		for (AdminPageTableColumnVO vo : searchColumns) {
			if("select".equals(vo.getType())){
				typeSearchColumns.add(vo);
			}
		}
		return typeSearchColumns;
	}
	
	/**
	 * 获取input类型的名字
	 * @return
	 */
	public String getInputSearchColumnNames(){
		String str = "";
		for (AdminPageTableColumnVO vo : searchColumns) {
			if("input".equals(vo.getType())){
				str += vo.getName() + "、";
			}
		}
		if(StringUtils.isNotBlank(str)){
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	/**
	 * 获取input类型的列名
	 * @return
	 */
	public String getInputSearchColumns(){
		String str = "";
		for (AdminPageTableColumnVO vo : searchColumns) {
			if("input".equals(vo.getType())){
				str += vo.getColumn().replaceAll("search.", "o.") + " ";
			}
		}
		if(StringUtils.isNotBlank(str)){
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
}
