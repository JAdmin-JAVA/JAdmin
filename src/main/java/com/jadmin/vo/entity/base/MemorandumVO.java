package com.jadmin.vo.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:备忘签vo
 * @Copyright:JAdmin (c) 2018年11月09日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity
@Table(name = "sys_memorandum")
@Getter @Setter
public class MemorandumVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
	@Column(length = 32)
	private String memorandumId;

	private String content;

	@Column(length = 19)
	private String operateTime;

	@Column(length = 32)
	private String operatorId;
	
	@Column(length = 1)
	private String isDelete;

	@Override
	public String getPrimaryKey() {
		return memorandumId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.memorandumId = key;
	}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getMemorandumId(){
	    return memorandumId;
	}

}