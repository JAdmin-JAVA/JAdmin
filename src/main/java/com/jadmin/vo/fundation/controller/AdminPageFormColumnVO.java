package com.jadmin.vo.fundation.controller;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:后台管理列表页面form表单的vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class AdminPageFormColumnVO {

    /** 字段 */
	private String column;
	
    /** 字段的类型 */
	private String type;

    /** 标签  */
	private String name;

    /** 标签提示  */
	private String lable;
	
    /** 值 */
	private String value;
	
	/** 数据词典的code */
	private String selectCode;

	/** select的样式 目录2中风格icheck风格、默认风格 */
	private String selectStyle;
	
	/** 快速输入 数据词典的code */
	private String speedSelectCode;
	
	/** 允许的最大长度，目前只有textarea 有效 */
	private int length;
	
	/** 为空的提示 */
	private String nullmsg;
	
	/** 效验 */
	private String datatype;

	/** 效验 */
	private String dateFmt;
	
	/** 效验 日期*/
	private boolean maxTDate;
	
	/** 效验 日期*/
	private String maxDate;

	/** 是否必须输入，默认必须输入 */
	private boolean required;
	
	/** 字段加密方式 目前只支持md5和des，默认不加密 */
	private String encode;
	
	/** 表单界面的列，是否可以编辑，true表示可编辑，false表示不可编辑，hidden表示隐藏该列 */
	private String edit;
	
	//如果需要Ajax 验证,给出验证
	private String ajaxurl;

	/** 标注地图时，用到的经度 对应的字段 */
	private String x;

	/** 标注地图时，用到的维度 对应的字段 */
	private String y;
	
	private String picPath;
	
    /** 编辑页面的表格，对应的vo的class */
	private Class<?> table;

    /** 编辑页面的表格，对应的vo的class的url */
	private String tableUrl;
	
	/** 编辑页面的表格，对应的vo的外键 */
	private String idColunm;

	/** 编辑页面的表格，需要隐藏的列，从1开始，多个列用逗号分开 */
	private String tableHiddleColumn;
	
	/** 内容为空时的默认值 */
	private String initValue;

    /** 属性所在宽度比例，目前只有100，50 两个值 */
	private int widthScale;
	
	public String getDisabled(){
		return edit.equals("true") ? "" : "readonly=\"true\"";
	}
	
	public String getRequiredValue() {
		return required ? "required" : "";
	}
	
	public String getSelectRequire(){
		return required ? "*" : "";
	}
	
	public String getMaxTDateValue() {
		return maxTDate ? "maxTDate" : "";
	}

}
