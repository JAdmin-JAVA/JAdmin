package com.jadmin.vo.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title:web框架
 * @Description:SysTaskLogVO
 * @Copyright:JAdmin (c) 2018年09月25日
 * 
 * @author:-jiujiya
 * @version:1.0
 */
@Entity
@Table(name = "sys_task_log")
@Getter @Setter
public class SysTaskLogVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String logId;

	@Column(length = 256)
	private String className;
	
	@Column(length = 32)
	private String serverIp;

	@Column(length = 2048)
	private String content;

	@Column(length = 19)
	private String startTime;

	@Column(length = 19)
	private String endTime;
	
	@Column(length = 16)
	private Integer executeTime;

	@Override
	public String getPrimaryKey() {
		return logId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.logId = key;
	}

}