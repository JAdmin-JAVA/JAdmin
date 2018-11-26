package com.jadmin.vo.entity.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:机构管理vo，小的vo，去掉不需要的属性
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity @Getter @Setter
@Table(name = "sys_org")
public class OrgRefVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = 1l;

	@Id
	@Column(length = 32)
	private String orgId;

	@Column(length = 128)
	private String code;

	@Column(length = 128)
	private String name;

	@Column(length = 1024)
	private String seq;

	@Override
	public String getPrimaryKey() {
		return orgId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.orgId = key;
	}

}