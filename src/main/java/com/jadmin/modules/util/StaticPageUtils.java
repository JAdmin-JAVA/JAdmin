package com.jadmin.modules.util;

import com.jadmin.util.conn.ConnectionUtils;
import com.jadmin.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:页面静态化
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
public class StaticPageUtils {
    
	public static final String KEYS = "NOSTATCIPAGE";
	
	public static final String BASEURL = "/static/html";
	
	public static final String POSTFIX = ".html";
	
	/**
	 * 刷新页面
	 * @param url
	 */
	public static void flushPage(String url) {
		log.info("尝试静态化：" + url);
		String filePath = ConfigUtil.getJAdminProperty().getFileBasePath() + BASEURL + url + POSTFIX;
		url = url + (url.contains("?") ? "&" : "?") + KEYS + "=true";
		FileUtil.addDirs(filePath);
		ConnectionUtils.download(ConfigUtil.getStaticUrlPath() + url, filePath);
		log.info("静态化：" + url + "，到：" + filePath);
	}
	
}  
