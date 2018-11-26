package com.jadmin.vo.fundation.controller;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:搜索策略的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageSearchModeVO {

	// 搜索的模式 
	private int defType = 1;

	// 是否显示日期范围
	private boolean showDate = false;

	// 日期范围字段
	private String dateColumn = "operateTime";

	// 日期的类型
	private String dateFmt = "yyyy-MM-dd";

}
