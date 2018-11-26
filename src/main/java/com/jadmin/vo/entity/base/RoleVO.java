package com.jadmin.vo.entity.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:RoleVO业务类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity @Getter @Setter
@Table(name = "sys_role")
public class RoleVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = -5452735218569309710L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String roleId;

	@Column(length = 128)
	private String roleName;

	@Column(length = 1)
	private String roleType;

	@Column(length = 19)
	private String operateTime;
	
	@Column(length = 32)
	private String operatorId;
	
	@Column(length = 1)
	private String billStatus;
	
	@Column(length = 512)
	private String memo;
	
	@OneToMany(targetEntity=RoleModuleVO.class,cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	//updatable=false很关键，如果没有它，在级联删除的时候就会报错(反转的问题)
	@JoinColumn(name="roleId",updatable=false)
	List<RoleModuleVO> roleModule = new ArrayList<RoleModuleVO>();

	@Override
	public String getPrimaryKey() {
		return roleId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.roleId = key;
	}

}