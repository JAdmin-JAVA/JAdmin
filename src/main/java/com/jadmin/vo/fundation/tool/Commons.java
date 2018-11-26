package com.jadmin.vo.fundation.tool;

import java.util.ArrayList;
import java.util.List;

import com.jadmin.vo.entity.CacheVO;
import com.jadmin.vo.entity.base.ConfigVO;
import com.jadmin.vo.fundation.controller.AdminPageLogVO;
import com.jadmin.vo.fundation.controller.AdminPageMenuVO;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:缓存容器类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class Commons {

    /**
     * 工具类实例
     */
    private static Commons commons;

    /** 后台管理页面所有的一级菜单 */
    private List<AdminPageMenuVO> adminPageMenus;

    /** 静态页面 */
    private List<String> staticPages = new ArrayList<String>();
    
    /** 记录有Log注解的方法  */
    private List<AdminPageLogVO> logMenthods;
    
	/** 用户未登录时，不需要过滤的url */
    private List<String> excludedUrls;

    /** 自定义的缓存  */
    private CacheVO cacheVO = new CacheVO();

    /**
     * 构造器
     */
    private Commons() {

    }

    /**
     * 获得工具类实例
     * 
     * @return
     */
    public static final Commons getInstance() {
        if (commons == null) {
            commons = new Commons();
        }
        return commons;
    }
	
	/**
	 * 获取配置数据
	 * @param code
	 * @param defValue
	 * @return
	 */
	public static String getConfigValue(String code, String defValue){
		for (ConfigVO cvo : getInstance().getCacheVO().getConfigs()) {
			if(cvo.getCode().equals(code)){
				return cvo.getCoValue();
			}
		}
		return defValue;
	}
}
