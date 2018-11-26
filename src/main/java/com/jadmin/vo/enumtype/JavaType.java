package com.jadmin.vo.enumtype;

import java.math.BigDecimal;

/** 
 * @Title:web框架
 * @Description:Java常见类型枚举
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public enum JavaType {
	
	Integer(0, "数字型"),
	
	String(1, "字符串"),
	
	BigDecimal(2, "BigDecimal");
	
    /** 显示值 */
    private int code;
    
    /** 显示标签 */
    private String label;

    /**
     * 构造器，必须私有
     * @param code
     */
    JavaType(Integer code, String label){
        this.code = code;
        this.label = label;
    }
    
    /**
     * 給据javaType和value获得真正的value
     * @param javaType
     * @param value
     * @return
     */
    public static Object getValueByString(JavaType javaType, String value){
    	Object val = value;
		if(javaType.getCode().equals(JavaType.Integer.getCode())){
			val = java.lang.Integer.parseInt(value);
		}else if(javaType.getCode().equals(JavaType.BigDecimal.getCode())){
			val = new BigDecimal(value);
		}
		return val;
    }

	public String getLabel() {
        return label;
    }

	public Integer getCode() {
		return code;
	}  
}
