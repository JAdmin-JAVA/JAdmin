package com.jadmin.modules.annotation.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title:web框架
 * @Description:声明是属性结构的列表界面
 * @Copyright:JAdmin (c) 2018年10月10日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tree {

	/**
	 * 如果不是通过方法自定义数据，将使用sql自定义树的json数据
	 * 里面的必填字段有：
     *  id 节点id
     *  name 节点名称
     * 选填字段有：
     *  pId 所属父节点 如果顶级节点，设置为0（可选，为空表示只是一级结构）
     *  rId 如果rId存在，id只负责和pId对应，选择后保存的值是rId
     *  nocheck 如果为false或者0，该节点不能被选中（nocheck属性同样支持selectCode，但是只支持动态的数据词典）
	 */
	String sql() default "SELECT seq id, orgFSeq pId, name FROM sys_org where isDelete = 0 and billStatus = 1";
	
	/** select类型的数据词典，如果该字段不为空，将无视sql */
	String selectCode() default "";
	
	/** 当前vo所对应的关键词，默认是 like 匹配，如果需要特殊处理，可重新getTreeHqlWhere方法 */
	String fieldKey() default "org.seq";
	
	/** 点击tree的节点之后，传递到后台的值，默认传递id */
	String jsonKey() default "id";
	
	/** 是否需要通过方法 自定义树的json数据，通过重新getTreeData方法来实现自定义 */
	boolean methodCustom() default false;
}
