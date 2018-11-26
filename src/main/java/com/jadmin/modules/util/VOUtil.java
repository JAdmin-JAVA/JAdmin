package com.jadmin.modules.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.vo.fundation.base.AbstractValueObject;

/** 
 * @Title:web框架
 * @Description:和vo有关的工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class VOUtil {

	private static String PACKAGE_NAME = ConfigUtil.BASE_PACKAGE + "vo";
	
	/**
	 * 获得一个vo所有的属性和值
	 * @param vo
	 */
	public static String getVOValues(AbstractValueObject vo){
		Field[] fields = vo.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for (Field field : fields) {
			if(!field.getName().equals("serialVersionUID")){
				sb.append(field.getName()).append(" = ").append(vo.get(field.getName())).append(",");
			}
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	/**
	 * 給据vo获得表名
	 * @param tableName
	 */
	public static String getTableNameByVOClass(Class<?> class1) {
		Table tAn = class1.getAnnotation(Table.class);
		if(tAn == null || StringUtils.isBlank(tAn.name())){
			throw new BusinessException("不能找到vo对应的表名，vo：" + class1);
		}
		return tAn.name().toLowerCase();
	}

	/**
	 * 給据表名获得vo的名字
	 * @param tableName
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends AbstractValueObject> getVOClassNameByTableName(String tableName) {
		List<Class<?>> classes = ClassUtil.getClasses(PACKAGE_NAME);
    	for (Class<?> class1 : classes) {
    		Table tAn = class1.getAnnotation(Table.class);
    		if(tAn != null && tAn.name().toUpperCase().equals(tableName.toUpperCase())){
    			return (Class<? extends AbstractValueObject>) class1;
    		}
    	}
    	throw new BusinessException("不能找到表对应的vo，表名：" + tableName);
	}
	
	/**
	 * 給据表名获得控制层的名字，只获得第一个，未检查多个的情况
	 * @param tableName
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends CommonListController<?>> getControllerClassNameByTableName(String tableName) {
		Class<? extends AbstractValueObject> voClass = getVOClassNameByTableName(tableName);
		List<Class<?>> classes = ClassUtil.getClasses(AdminPageUtils.PACKAGE_NAME);
    	for (Class<?> class1 : classes) {
        	ParameterizedType type = (ParameterizedType)class1.getGenericSuperclass();
        	if(type.getActualTypeArguments()[0].equals(voClass)){
        		return (Class<? extends CommonListController<?>>) class1;
        	}
    	}
    	throw new BusinessException("不能找到表对应的vo，表名：" + tableName);
	}
	
	/**
	 * 給据vo的class，获得主键的类型
	 * @param voClass
	 * @return
	 */
	public static Class<?> getIdType(Class<? extends AbstractValueObject> voClass){
		Field[] fields = voClass.getDeclaredFields();
		for (Field field : fields) {
			Id idAn = field.getAnnotation(Id.class);
			if(idAn != null){
				return field.getType();
			}
		}
    	throw new BusinessException("不能找到vo中的主键，请检查vo中是否有ID注解，vo：" + voClass);
	}
}
