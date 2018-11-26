package com.jadmin.modules.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jadmin.vo.fundation.base.JAdminProperties;

/** 
 * @Title:web框架
 * @Description:配置文件工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
public class ConfigUtil {
	
	public final static String BASE_PACKAGE = "com.jadmin.";
	
	private static JAdminProperties properties;
	
	/** spring boot的config工具类 springboot 本身已将配置数据缓存，这里只是方便使用 */
    private static Environment env;
	
	@Autowired
    public ConfigUtil(JAdminProperties properties, Environment env) {
		ConfigUtil.properties = properties;
		ConfigUtil.env = env;
    }
	
	/**
	 * 只能识别String类型的
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return env.getProperty(key);
	}

	/**
	 * @return 获取jadmin的配置属性
	 */
	public static JAdminProperties getJAdminProperty() {
		return properties;
	}
	
	/**
	 * @return 获取原生的配置文件读取类
	 */
	public static Environment getEvn() {
		return env;
	}
    
    /**
     * @return 获取本地访问地址
     */
    public static String getStaticUrlPath() {
    	return "http://127.0.0.1:" + env.getProperty("server.port", Integer.class);
    }
}
