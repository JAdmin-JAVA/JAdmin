package com.jadmin.modules.itf;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.jadmin.modules.exception.BusinessException;

import net.sf.json.JSONException;

/** 
 * @Title:web框架
 * @Description:一些基础方法的接口
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class GeneralOperatUtils {

    /**
     * 获取17位凭证号
     * @return
     */
    public String getVoucherNo(){
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
    }
    
    /**
     * getDateTime
     * 取系统时间(当前服务器时间)
     *
     * @return String
     */
    public static String getCurDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }
    
    /**
    * getDate
    * 取系统日期(当前服务器时间)
    *
    * @return String
    */
    public static String getCurDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
    
    /**
     * getDate
     * 取系统日期(当前服务器时间)
     *
     * @return String
     */
     public static String getChinDate() {
         return new SimpleDateFormat("MM月dd日").format(new java.util.Date());
     }
     
     /**
      * getDate
      * 取系统日期(当前服务器时间)
      *
      * @return String
      */
      public static String getToChinDate(String  data) {
          return new SimpleDateFormat("MM月dd日").format(new java.util.Date());
      }
      
    /**
     * getTs
     * 生成时间戳
     *
     * @return String
     */
    public static String getTs() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }

    /**
     * getTimestamp
     * 生成时间戳
     *
     * @return String "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date());
    }
    
    /**
     * getDate
     * 生成时间戳
     *
     * @return String "yyyy-MM-dd"
     */
    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
    
    /**
	 * 获取正确的返回结果
	 * @return
	 */
	protected Map<String, Object> getRuturnJsonMap(){
		return getRuturnJsonMap(true, "");
	}
	
	/**
	 * 获取错误的返回结果
	 * @param arg1
	 * @return
	 */
	protected Map<String, Object> getRuturnJsonMap(Throwable arg1){
		String errorMsg = null;
		if(arg1 instanceof JSONException){
			errorMsg = "data 格式异常";
		}else if(arg1 instanceof BusinessException){
			errorMsg = arg1.getMessage();
		}else {
			errorMsg = "访问异常：" + arg1.getMessage();
		}
		return getRuturnJsonMap(false, errorMsg);
	}
	
	/**
	 * 获取错误的返回结果
	 * @param errorMsg
	 * @return
	 */
	protected Map<String, Object> getRuturnJsonMap(String errorMsg){
		return getRuturnJsonMap(false, errorMsg);
	}
	
	/**
	 * 获取统一格式的返回结果
	 * @param status
	 * @param errorMsg
	 * @return
	 */
	protected Map<String, Object> getRuturnJsonMap(boolean status, String errorMsg){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("errorMsg", errorMsg);
		return map;
	}
}
