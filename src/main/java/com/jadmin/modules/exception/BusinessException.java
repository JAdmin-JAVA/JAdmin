package com.jadmin.modules.exception;

/** 
 * @Title:web框架
 * @Description:业务异常
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class BusinessException extends RuntimeException {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 构造器
     * @param msg
     */
    public BusinessException(String msg) {
        super(msg);
    }
}