package com.jadmin.vo.entity.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:数据词典vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity @Getter @Setter
@Table(name = "sys_dictinfo")
public class DictinfoVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = -5452735218569309710L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String dictinfoId;

	@Column(length = 32)
	private String dictkindId;
	
	@Column(length = 32)
	private String dictinfoFid;

	@Column(length = 128)
	private String code;

	@Column(length = 128)
	private String name;
	
	@Column(length = 32)
	private Integer sort;
	
	@Column(length = 1)
	private String billStatus;
	
	@Column(length = 1 , name="\"level\"")
	private String level;
	
	@Column(length = 512)
	private String memo;
	
	// 是否可以选中（ztree的选择时有效，目前只支持动态sql的数据词典）
	@Transient
	private boolean nocheck;
	
	// 人工給据级别放入dictinfos中
	@Transient
	private List<DictinfoVO> dictinfos = new ArrayList<DictinfoVO>();

	@Override
	public String getPrimaryKey() {
		return dictinfoId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.dictinfoId =  key;
	}
	
	public String getFullCode(){
		if(StringUtils.isNotBlank(code)){
			return "," + code + ",";
		}
		return null;
	}
	
	public String getValue(){
		return "";
		/*List<DictinfoVO> ds = null;
		DictkindVO dv = Commons.getDictkindById(dictkindId);
		for (DictinfoVO dictinfoVO : dv.getDictinfoLevels()) {
			if(dictinfoId.equals(dictinfoVO.getDictinfoId())){
				
			}
		}*/
	}
	
}