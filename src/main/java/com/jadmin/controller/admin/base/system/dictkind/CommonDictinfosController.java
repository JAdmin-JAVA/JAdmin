package com.jadmin.controller.admin.base.system.dictkind;

import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.util.StartCacheUtil;
import com.jadmin.vo.fundation.base.AbstractValueObject;

/**
 * @Title:web框架
 * @Description:字典属性的基类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0
 */
public class CommonDictinfosController<T extends AbstractValueObject> extends CommonListController<T> {

	@FormColunm(value = "名称")
	@TableColumn
	public String name;

	@FormColunm(value = "编号")
	@TableColumn(search = true)
	public String code;

	@FormColunm(value = "排序")
	public String sort;

	@FormColunm(value = "状态", selectCode = "billStatus")
	@TableColumn(search = true)
	public String billStatus;

	@FormColunm(value = "描述", type = "textarea", length = 512, required = false)
	public String memo;

	/**
	 * 当数据发生改变后的操作，包括增删改
	 * 
	 * @param vo
	 * @param request
	 */
	public void afterDataChange() {
		// 刷新数据词典缓存
		StartCacheUtil.refurbish("dictkinds");
	}
}
