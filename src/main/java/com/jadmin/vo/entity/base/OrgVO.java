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
 * @Description:部门管理vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity @Getter @Setter
@Table(name = "sys_org")
public class OrgVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String orgId;

	@Column(length = 128)
	private String code;

	@Column(length = 128)
	private String name;

	@Column(length = 1024)
	private String fullname;

	@Column(length = 1024)
	private String seq;

	@Column(length = 1024)
	private String orgFSeq;

	@Column(length = 19)
	private String operateTime;

	@Column(length = 32)
	private String operatorId;

	@Column(length = 1)
	private String billStatus;

	@Column(length = 512)
	private String memo;
	
	@Column(length = 1)
	private String isDelete;
	
	@Override
	public String getPrimaryKey() {
		return orgId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.orgId = key;
	}

}