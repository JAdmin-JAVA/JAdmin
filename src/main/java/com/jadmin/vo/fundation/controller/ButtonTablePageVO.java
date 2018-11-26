package com.jadmin.vo.fundation.controller;

import com.jadmin.modules.annotation.list.ButtonTablePage;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:ButtonTablePage注解的VO
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class ButtonTablePageVO {

	/** 注解 */
	private ButtonTablePage btpAn;
	
	/** 该类的class */
	private Class<?> class1;
	
	/** 级别，2级、3级 */
	private int level;
	
}
