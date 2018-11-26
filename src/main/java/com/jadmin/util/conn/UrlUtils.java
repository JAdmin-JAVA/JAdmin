package com.jadmin.util.conn;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** 
 * @Title:web框架
 * @Description:统一处理url
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class UrlUtils implements Serializable {
	
	/**
	 * 序列号 
	 */
	private static final long serialVersionUID = -4502193483183066187L;

	/**
	 * 添加后台必须传输的数据 
	 * @param type
	 * @return
	 */
	public static Map<String, Object> putEnd(String type, Map<String, Object> map) {
		if(map == null){
			map = new HashMap<String, Object>();
		}
		map.put("app_base_type", type);
		return map;
	}
	
	
	/**
	 * 对URL进行格式处理 前面加http://， 后面加用户pk和 花店pk
	 * @param path
	 * @return
	 */
	public static String formatURL(String path) {
		return formatEndURL(addURLArgus(path, null));
	}
	
	/**
	 * 对URL进行格式处理 前面加http://， 后面加用户pk和 花店pk
	 * @param path
	 * @return
	 */
	public static String formatURL(String path, Map<String, Object> argus) {
		return formatEndURL(addURLArgus(path, argus));
	}
	
	/**
	 * 对URL进行格式处理（后面加用户pk和 花店pk） 
	 * @param path
	 * @return
	 */
	private final static String formatEndURL(String path) {
		Map<String, Object> map = new HashMap<String, Object>();
		return addURLArgus(path, map);
	}

	/**
	 * 对URL进行格式处理 添加参数,前面加http://
	 * @param path
	 * @return
	 */

	public static String addURLArgus(String path, Map<String, Object> argus1) {
		return addURLArgus(path, argus1, true);
	}
	
	public static String addURLArgus(String path, Map<String, Object> argus1, boolean isEscape) {
		// 前面加http://
		Map<String, Object> argus = new HashMap<String, Object>();
		argus.putAll(argus1);
		if(argus == null || argus.size() == 0) return path;
		argus.remove("app_base_type");
		if(path.contains("=")){
			path += "&";
		}else if(!path.endsWith("?")){
			path += "?";
		}
		String argusUrl = "";
        for(Map.Entry<String, Object> entry: argus.entrySet()){
        	if(entry.getValue() != null){
    			argusUrl += "&" + entry.getKey() + "=" + (String) entry.getValue();
        	}
        }
        if(argusUrl.equals("")){
        	path = "";
        }else{
        	path = argusUrl.substring(1, argusUrl.length());
        }
		return path;
	}


}
