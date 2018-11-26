package com.jadmin.vo.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:角色管理vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity @Getter @Setter
@Table(name = "sys_role_module")
public class RoleModuleVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String roleModuleId;

	@ManyToOne 
	@JoinColumn(name = "roleId") 
	private RoleVO roleId;

	@Column(length = 128)
	private String moduleMenu1;

	@Column(length = 128)
	private String moduleMenu2;
	
	@Column(length = 128)
	private String modulePage;

	@Column(length = 128)
	private String moduleButton;

	@Column(length = 1)
	private String billStatus;

	@Override
	public String getPrimaryKey() {
		return roleModuleId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.roleModuleId = key;
	}
	
	/**
	 * 获得menu的全称
	 * @return
	 */
	public String getModuleMenuFullName() {
		return moduleMenu1 + ";" + moduleMenu2;
	}
	
	/**
	 * 获得page的全称
	 * @return
	 */
	public String getModulePageFullName() {
		return getModuleMenuFullName() + ";" +this.modulePage;
	}
	
	/**
	 * 获得button的全称
	 * @return
	 */
	public String getModuleButtonFullName() {
		return getModulePageFullName() + ";" + this.moduleButton;
	}

}