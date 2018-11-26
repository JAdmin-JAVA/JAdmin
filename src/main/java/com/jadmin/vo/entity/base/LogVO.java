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
 * @Description:系统日志vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity @Getter @Setter
@Table(name = "sys_log")
public class LogVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String logId;

	@Column(length = 32)
	private String orgId;

	@Column(length = 128)
	private String userId;

	@Column(length = 1024)
	private String url;

	@Column(length = 128)
	private String type;

	@Column(length = 1024)
	private String content;

	@Column(length = 64)
	private String clientIp;

	@Column(length = 1, name = "\"level\"")
	private String level;

	@Column(length = 19)
	private String operateTime;

	@Column(length = 512)
	private String memo;

	@Override
	public String getPrimaryKey() {
		return logId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.logId = key;
	}

}