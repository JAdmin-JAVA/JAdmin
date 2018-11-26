package com.jadmin.vo.fundation.base;

import com.jadmin.modules.annotation.column.SearchColumn;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:hql条件对象
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class QueryArgVO {

	// 字段名称
	private String key;

	// 字段值
	private Object value;

	// 类型 默认 完全相等
	private int type = SearchColumn.INIT;
	
	public QueryArgVO(){
		
	}
	
	public QueryArgVO(String key, Object value){
		this.key = key;
		this.value = value;
	}
	
	public QueryArgVO(String key, Object value, int type){
		this.key = key;
		this.value = value;
		this.type = type;
	}

}