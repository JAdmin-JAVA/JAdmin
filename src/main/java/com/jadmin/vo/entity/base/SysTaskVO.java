package com.jadmin.vo.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Title:web框架
 * @Description:SysTaskVO
 * @Copyright:JAdmin (c) 2018年08月22日
 * 
 * @author:zhangjiao
 * @version:1.0
 */
@Entity
@Table(name = "sys_task")
@Getter @Setter @ToString
public class SysTaskVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String taskId;

	@Column(length = 256)
	private String className;

	@Column(length = 256)
	private String cron;

	@Column(length = 32)
	private Integer initialDelay;

	@Column(length = 256)
	private String description;
	
	@Column(length = 32)
	private String serverIp;
	
	@Column(length = 1)
	private String billStatus;
	
	@Column(length = 1)
	private String isDynamic;
	
	@Column(length = 1)
	private String isEdited;
	
	@Transient
	private String status;
	
	@Override
	public String getPrimaryKey() {
		return taskId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.taskId = key;
	}
	
	public String getLogs() {
		String url = "/sysTaskLog/getAll?baseWhere=" + className + ";" + serverIp;
		return "<a href='javascript:;' onclick=layer_show('执行记录','" + url + "','','')>查看执行记录</a>";
	}
	
}