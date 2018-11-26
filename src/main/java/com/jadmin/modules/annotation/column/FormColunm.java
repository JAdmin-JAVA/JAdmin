package com.jadmin.modules.annotation.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.vo.fundation.base.Null;

/** 
 * @Title:web框架
 * @Description:声明添加/编辑页面中的属性
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormColunm{
	
    /** 标签 */
	String value();
	
    /** 标签描述 */
	String lable() default "";

    /** 字段 */
	String column() default "";
	
    /** 类型 目前的类型有：input、speedInput、select、textarea、 date、xy, upFile, editor, table*/
	String type() default "input";

    /** 是否运行添加多个，默认为空，不为空输入多个属性的分隔符  */
	String mutipleSplit() default "";
	
    /** 允许的最大长度，目前只有textarea 有效 */
	int length() default -1;
	
	/** select类型的数据词典 */
	String selectCode() default "";
	
	/** select的样式 目前3中：tree风格、风格icheck、默认风格 */
	String selectStyle() default "def";
	
	/** 快速输入的 select类型的数据词典 */
	String speedSelectCode() default "";

    /** 是否必须输入，默认必须输入 */
	boolean required() default true;
	
    /** 属性所在宽度比例，目前只有100，50 两个值 */
	int widthScale() default 100;
	
    /** 为空的提示 */
	String nullmsg() default "";
	
    /** 效验 */
	String datatype() default "";
	
	/** 日期格式，如果type为date，该字段默认为yyyy-MM-dd */
	String dateFmt() default "";
	
    /** 日期允许选择的最大值，如果是今天，填写关键词 today */
    String maxDate()  default "";
    
	/** 字段加密方式 目前只支持md5和des，默认不加密 */
	String encode() default "";
	
	/** 表单界面的列，是否可以编辑，true表示可编辑，false表示不可编辑，hidden表示隐藏该列, notEdit表示可以添加，不可以编辑 */
	String edit() default "true";
	
	String ajaxurl() default "";

	/** 标注地图时，用到的经度 对应的字段 */
	String x() default "";

	/** 标注地图时，用到的维度 对应的字段 */
	String y() default "";
	
	/** 图片上传路径 */
	String picPath() default "";

    /** 编辑/添加的时候是否显示 */
	boolean editShow() default true;

    /** 编辑页面的表格，对应的vo控制类的class */
	@SuppressWarnings("rawtypes")
	Class<? extends CommonListController> table() default Null.class;
	
	/** 编辑页面的表格，对应的vo的外键，为空的话表示只显示，不update */
	String idColunm() default "";
	
	/** 编辑页面的表格，需要隐藏的列，从1开始，多个列用逗号分开 */
	String tableHiddleColumn() default "";
	
	/** 内容为空时的默认值 */
	String initValue() default "";
	
}
