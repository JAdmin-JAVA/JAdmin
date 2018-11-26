package com.jadmin.vo.enumtype;

/** 
 * @Title:web框架
 * @Description:是否的枚举
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public enum YesNo {
    
    YES((short)1, "是"),
    
    NO((short)0, "否");

    /** code */
    private Short code;

    /** 显示标签 */
    private String label;

    /**
     * 构造器，必须私有
     * @param code
     */
    YesNo(Short code, String label){
        this.code = code;
        this.label = label;
    }

    /**
     * 获取code的文本
     * @param code
     * @return
     */
    public static String getLabelByCode (Short code){
        for (YesNo enuma : YesNo.values()){
            if (enuma.getCode().compareTo(code) == 0){
                return enuma.getLabel();
            }
        }
        return "未知枚举项";
    }
    /**
     * 根据文本获取code
     */
    public static Short getCodeByLabel(String label){
        for(YesNo enuma : YesNo.values()){
            if(enuma.getLabel().equals(label)){
                return enuma.getCode();
            }
        }
        return null;
        
    }
    public String toString(){
        return label;
    }

    public String getLabel() {
        return label;
    }

    public Short getShort(){
        return this.code;
    }

    public Short getCode(){
        return this.code;
    }
  
       
}
