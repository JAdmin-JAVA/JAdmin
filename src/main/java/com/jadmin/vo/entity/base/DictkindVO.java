package com.jadmin.vo.entity.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:数据词典类别vo
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Entity @Getter @Setter
@Table(name = "sys_dictkind")
public class DictkindVO extends AbstractValueObject {

	/** 序列号 */
	private static final long serialVersionUID = -5452735218569309710L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String dictkindId;

	@Column(length = 128)
	private String name;

	@Column(length = 128)
	private String code;

	@Column(length = 1)
	private String isDynamic;
	
	@Column(length = 1024)
	private String dynSql;
	
	@Column(length = 1)
	private String type;
	
	@Column(length = 1)
	private String isOpen;
	
	@Column(length = 19)
	private String operateTime;
	
	@Column(length = 1)
	private String billStatus;
	
	@Column(length = 512)
	private String memo;
	
	@OneToMany(targetEntity=DictinfoVO.class,cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	//updatable=false很关键，如果没有它，在级联删除的时候就会报错(反转的问题)
	@JoinColumn(name="dictkindId",updatable=false)
	@OrderBy("sort")
	@Where(clause="billStatus=1")
	@Embedded
	private List<DictinfoVO> dictinfos = new ArrayList<DictinfoVO>();
	
	// 人工給据级别放入dictinfos中，有分层次的list
	@Transient
	private List<DictinfoVO> dictinfoLevels = new ArrayList<DictinfoVO>();

	@Override
	public String getPrimaryKey() {
		return dictkindId;
	}

	@Override
	public void setPrimaryKey(String key) {
		this.dictkindId = key;
	}
	
	public String getValue() {
		if(dictinfos.isEmpty()) return "";
		String value = "";
		for (DictinfoVO dictinfo : dictinfos) {
			if("1".equals(dictinfo.getLevel())){
				value += dictinfo.getName() + "、";
			}
		}
		return value.substring(0, value.length() - 1);
	}
	
	/**
	 * @return 获取tree的json的list
	 */
	public List<Map<String, Object>> getTreeJsonList() {
		List<Map<String, Object>> rlist = new ArrayList<>();
		List<DictinfoVO> list = getDictinfos();
    	for (DictinfoVO dictinfoVO : list) {
    		Map<String, Object> rMap = new HashMap<>();
    		rMap.put("id", dictinfoVO.getPrimaryKey());
    		rMap.put("rId", dictinfoVO.getCode());
    		rMap.put("name", dictinfoVO.getName());
    		rMap.put("pId", dictinfoVO.getDictinfoFid());
    		rMap.put("nocheck", dictinfoVO.isNocheck());
    		rlist.add(rMap);
    	}
    	return rlist;
	}

}