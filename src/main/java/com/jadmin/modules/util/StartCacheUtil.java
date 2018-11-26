package com.jadmin.modules.util;

import java.lang.reflect.Field;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.annotation.StartCache;
import com.jadmin.vo.entity.CacheVO;
import com.jadmin.vo.fundation.tool.Commons;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:初始化的缓存工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
public class StartCacheUtil {
    
	/**
	 * 缓存所有数据
	 */
	public static void refurbish() {
    	CacheVO vo = new CacheVO();
    	Commons.getInstance().setCacheVO(vo);
    	Field[] fields = CacheVO.class.getDeclaredFields();
    	for (Field field : fields) {
    		refurbish(field);
		}
	}

	/**
	 * 缓存某个类型的数据
	 * @param code
	 */
	public static void refurbish(String code) {
		Field[] fields = CacheVO.class.getDeclaredFields();
    	for (Field field : fields) {
    		if(field.getName().toLowerCase().equals(code.toLowerCase())){
    			refurbish(field);
    		}
		}
	}

	/**
	 * 缓存某个类型的数据
	 * @param field
	 */
	private static void refurbish(Field field) {
		try {
			StartCache cacheAn = field.getAnnotation(StartCache.class);
			if(cacheAn == null) return;
			CacheVO vo = Commons.getInstance().getCacheVO();
        	SystemDao systemDao = SpringContext.getBean(SystemDao.class);
	    	Object obj = null;
			if(cacheAn.methodInit()){
				obj = ClassUtil.methodInitInvoke(field, CacheVO.class);
			}else{
				obj = systemDao.getCacheDataByHSql(cacheAn.value(), cacheAn.type());
			}
			vo.set(field.getName(), obj);
			log.info(field.getName() + "缓存成功：" + obj);
		} catch (Exception e) {
			log.error("初始化缓存失败……" + e.getMessage(), e);
		}
	}
}
