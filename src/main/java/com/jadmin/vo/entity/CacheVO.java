package com.jadmin.vo.entity;

import java.util.List;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.annotation.StartCache;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.vo.entity.base.ConfigVO;
import com.jadmin.vo.entity.base.DictkindVO;
import com.jadmin.vo.fundation.base.AbstractSimple;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:web缓存vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter
public class CacheVO extends AbstractSimple{

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -4637817654992729041L;
	
	/** 配置数据 */
    @StartCache(" from ConfigVO where billStatus = 1 ")
    private List<ConfigVO> configs;
	
	/** 数据词典 */
    @StartCache(methodInit = true)
    private List<DictkindVO> dictkinds;
    
    /*public static void main(String[] args) {
		// 获取缓存的方法
    	Commons.getInstance().getCacheVO().getConfigs();
    	// 刷新该缓存
    	StartCacheUtil.refurbish("configs");
	}*/
    
    /**
     * 初始化数据词典
     */
    public List<DictkindVO> initDictkinds(){
    	SystemDao systemDao = SpringContext.getBean(SystemDao.class);
		return systemDao.getDictkinds();
    }
}
