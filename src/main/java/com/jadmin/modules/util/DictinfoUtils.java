package com.jadmin.modules.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.vo.entity.base.DictinfoVO;
import com.jadmin.vo.entity.base.DictkindVO;
import com.jadmin.vo.fundation.tool.Commons;

/** 
 * @Title:web框架
 * @Description:数据词典相关工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class DictinfoUtils {
	
	/**
	 * 給据id，查询数据词典
	 * @param id
	 * @return
	 */
	public static DictkindVO getDictkindById(String id, HttpServletRequest request) {
		return getDictkindByCodeOrId(null, id, request);
	}

	/**
	 * 給据code，查询数据词典
	 * @param code
	 * @return
	 */
	public static DictkindVO getDictkind(String code, HttpServletRequest request) {
		return getDictkindByCodeOrId(code, null, request);
	}
	
	/**
	 * 給据code和key获得name
	 * @param code
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getDictkindNameByKey(String code, String key, HttpServletRequest request) {
		List<DictinfoVO> list = getDictkind(code, request).getDictinfos();
    	for (DictinfoVO dictinfoVO : list) {
    		if(key.equals(dictinfoVO.getCode())){
    			return dictinfoVO.getName();
    		}
    	} 
    	return key;
	}
	
	/**
	 * 給据code和name获得key
	 * @param code
	 * @param name
	 * @param request
	 * @return
	 */
	public static String getDictkindKeyByName(String code, String name, HttpServletRequest request) {
		if(StringUtils.isBlank(name)){
			return "";
		}
		DictkindVO dVO = getDictkind(code, request);
		for (DictinfoVO ddvo : dVO.getDictinfos()) {
			if(ddvo.getName().equals(name)){
				return ddvo.getCode();
			}
		}
		return "";
	}

	/**
	 * 給据code或者是id，查询数据词典
	 * @param code
	 * @param id
	 * @return
	 */
	private static DictkindVO getDictkindByCodeOrId(String code, String id, HttpServletRequest request) {
		for (DictkindVO dVO : Commons.getInstance().getCacheVO().getDictkinds()) {
			if(dVO.getCode().equals(code) || dVO.getDictkindId().equals(id)){
				// 如果是动态的，就查询数据库
				if(dVO.getIsDynamic().equals("1")){
		        	SystemDao systemDao = SpringContext.getBean(SystemDao.class);
		        	dVO.setDictinfos(systemDao.getDictinfosBysql(dVO.getDynSql(), request));
		        	DictinfoUtils.initDictkindByLevel(dVO);
				}
				return dVO;
			}
		}
		throw new BusinessException("未找到该类型的数据词典，code：" + code);
	}

	/**
	 * 給据level将数据词典排序，仍在一个list里面，只是按照树形结构排序
	 * @param dictionfos
	 */
	private static void softDictkindByLevel(List<DictkindVO> dictionfos){
		// 排序，出现层级关系，給据树形结构遍历出层级关系
		for (DictkindVO dictkindVO : dictionfos) {
			List<DictinfoVO> softList = new ArrayList<DictinfoVO>();
			for (DictinfoVO dictinfoVO : dictkindVO.getDictinfoLevels()) {
				softList.add(dictinfoVO);
				for (DictinfoVO dv1 : dictinfoVO.getDictinfos()) {
					softList.add(dv1);
					for (DictinfoVO dv2 : dv1.getDictinfos()) {
						softList.add(dv2);
						softList.addAll(dv2.getDictinfos());
					}
				}
			}
			dictkindVO.setDictinfos(softList);
		}
	}
	
	/**
	 * 給据level将数据词典弄成树形结构
	 * @param dictionfos
	 */
	public static void initDictkindByLevel(List<DictkindVO> dictionfos){
		// 将dictionfo下的dictionfos初始化
		for (DictkindVO dictkindVO : dictionfos) {
			for (DictinfoVO dictinfoVO : dictkindVO.getDictinfos()) {
				dictinfoVO.setDictinfos(new ArrayList<DictinfoVO>());
			}
		}
		// 人工給据级别放入dictinfos中
		for (DictkindVO dictkindVO : dictionfos) {
			List<DictinfoVO> dictinfos = new ArrayList<DictinfoVO>();
			for (DictinfoVO dictinfoVO : dictkindVO.getDictinfos()) {
				// 如果级别是1，直接放入
				if(StringUtils.isBlank(dictinfoVO.getLevel()) || dictinfoVO.getLevel().equals("1")){
					dictinfos.add(dictinfoVO);
					continue;
				}
				// 其他级别，自动寻找，目前只支持到3级，可以增加级别
				for (DictinfoVO di : dictinfos) {
					if(di.getDictinfoId().equals(dictinfoVO.getDictinfoFid())){
						di.getDictinfos().add(dictinfoVO);
						continue;
					}
					for (DictinfoVO di2 : di.getDictinfos()) {
						if(di2.getDictinfoId().equals(dictinfoVO.getDictinfoFid())){
							di2.getDictinfos().add(dictinfoVO);
							continue;
						}
					}
				}
			}
			dictkindVO.setDictinfoLevels(dictinfos);
		}
		
		// 給据level将数据词典排序，仍在一个list里面，只是按照树形结构排序
		softDictkindByLevel(dictionfos);
	}
	
	/**
	 * 給据level将数据词典弄成树形结构
	 */
	private static void initDictkindByLevel(DictkindVO dictionfo){
		List<DictkindVO> dictionfos = new ArrayList<DictkindVO>();
		dictionfos.add(dictionfo);
		initDictkindByLevel(dictionfos);
	}
	
}
