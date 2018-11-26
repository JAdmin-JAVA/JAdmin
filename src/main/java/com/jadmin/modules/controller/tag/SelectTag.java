package com.jadmin.modules.controller.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.jadmin.modules.util.DictinfoUtils;
import com.jadmin.vo.entity.base.DictinfoVO;
import com.jadmin.vo.entity.base.DictkindVO;
import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:自定义select标签
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SuppressWarnings("serial")
@Getter @Setter @Slf4j
public class SelectTag extends TagSupport {
    
	private String id;
	private String code;
	private String name;
	private String style;
	private String selectStyle;
	private String dataSource;
	private String value;
	private String datatype;
	private String nullmsg;
	private AbstractValueObject vo;
	private String attribute;
	private String lable;
	
	@Override
	public int doStartTag() throws JspException {
		// 如果value 为空，并且vo和attribute不为空，则通过这两个属性获得value
		initlValue();
		// 先判断数据词典的类型，是optionSelect（单选下拉）类型，还是弹出框多选
		DictkindVO dictkind = DictinfoUtils.getDictkind(code, (HttpServletRequest) pageContext.getRequest());
		if("1".equals(dictkind.getType())){
			optionSelectHiddle(dictkind.getDictinfos());
		}else if("2".equals(dictkind.getType())){
			checkBoxSelectHiddle(dictkind);
		}
		return EVAL_PAGE;
	}
	
	/**
	 * 处理弹出框多选
	 * @param dictkind
	 */
	private void checkBoxSelectHiddle(DictkindVO dictkind) {
		JspWriter out = pageContext.getOut();
		try {
			if("icheck".equals(selectStyle)) {
				out.print(getIcheckMultiSelect(dictkind));
			}else {
				out.print(getDefMultiSelect(dictkind));
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @return 获取icheck的多选选样式
	 */
	public String getIcheckMultiSelect(DictkindVO dictkind) {
		// 获取所有value
		List<String> valueList = new ArrayList<>();
		for (String string : value.split(",")) {
			valueList.add(string);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='skin-minimal' style='padding-top: 3px;'>");
		// TODO:目前只支持到1级
		List<DictinfoVO> list = dictkind.getDictinfos();
		for (int i = 0; i < list.size(); i++) {
			DictinfoVO dictinfoVO = list.get(i);
			sb.append("<div class='check-box'>");
			sb.append("<input " + ((i == list.size() - 1 && StringUtils.isNotBlank(datatype)) ? "datatype='*'" : "")
						+ " type='checkbox' id='checkbox-" + dictinfoVO.getPrimaryKey() + "' name='"
						+ name + "' value='" + dictinfoVO.getCode() + "' " + (valueList.contains(dictinfoVO.getCode()) ? "checked" : "") + ">");
			sb.append("<label for='checkbox-" + dictinfoVO.getPrimaryKey() + "'>" + dictinfoVO.getName() + "</label>");
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * @return 获取默认的多选选样式
	 */
	public String getDefMultiSelect(DictkindVO dictkind) {
		StringBuffer s = new StringBuffer();
		s.append("<input type=\"text\" class=\"input-text\" ");
		s.append(" onclick=\"dictinfoCheckbox('" + dictkind.getName() + "', '" + name + "','" + dictkind.getCode() + "','','')\" ");
		String[] prop = {id,name,"", "点击选择" + dictkind.getName(), "", getValue()};
		String[] propString = {"id", "name", "datatype", "placeholder", "nullmsg", "value"};
		for (int i = 0; i < prop.length; i++) {
			if (prop[i] != null && !"".equals(prop[i])) {
					s.append(" " + propString[i] + "=\"" + prop[i] + "\"");
			}
		}
		s.append("/>");
		return s.toString();
	}
	
	private void initlValue(){
		// 如果value 为空，并且vo和attribute不为空，则通过这两个属性获得value
		if(StringUtils.isBlank(value) && StringUtils.isNotBlank(attribute) && vo != null){
			if(vo.get(attribute)==null){
				value = "";
			}else{
				value = vo.get(attribute).toString();
			}
		}
	}

	/**
	 * 处理单选下拉
	 * @param list
	 */
	private void optionSelectHiddle(List<DictinfoVO> list){
		JspWriter out = pageContext.getOut();
		try {
			if("icheck".equals(selectStyle)) {
				out.print(getIcheckSingleSelect(list));
			}else {
				out.print(getDefSingleSelect(list));
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @return 获取icheck的单选样式
	 */
	public String getIcheckSingleSelect(List<DictinfoVO> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='skin-minimal' style='padding-top: 3px;'>");
		// TODO:目前只支持到1级
		for (int i = 0; i < list.size(); i++) {
			DictinfoVO dictinfoVO = list.get(i);
			sb.append("<div class='radio-box'>");
			sb.append("<input " + ((i == list.size() - 1 && StringUtils.isNotBlank(datatype)) ? "datatype='*'" : "")
						+ " type='radio' id='radio-" + dictinfoVO.getPrimaryKey() + "' name='"
						+ name + "' value='" + dictinfoVO.getCode() + "' " + (dictinfoVO.getCode().equals(value) ? "checked" : "") + ">");
			sb.append("<label for='radio-" + dictinfoVO.getPrimaryKey() + "'>" + dictinfoVO.getName() + "</label>");
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * @return 获取默认的单选样式
	 */
	public String getDefSingleSelect(List<DictinfoVO> list) {
		StringBuffer s = new StringBuffer();
		StringBuffer b= new StringBuffer();
		String[] prop = {id,name,style,dataSource,datatype,nullmsg};
		String[] propString = {"id","name","class","dataSource","datatype","nullmsg"};
//		s.append("<span class=\"select-box inline\">");
		s.append("<select");
		for (int i = 0; i < prop.length; i++) {
			if (prop[i] != null && !"".equals(prop[i])) {
					s.append(" " + propString[i] + "=\"" + prop[i] + "\"");
			}
			if (propString[i] != null && propString[i].equals("dataSource")) {
				b.append("<option value=\"\"");
				if("".equals(value)||value==null){
					b.append(" selected=\"selected\"");
				}
				String tit = "请选择";
				if(StringUtils.isBlank(name)){
					tit = "快速输入";
				}else if(StringUtils.isNotBlank(lable)){
					tit = lable;
				}
				b.append(">" + tit + "</option>");
				for (DictinfoVO dictinfoVO : list) {
					b.append("<option value=\""+dictinfoVO.getCode()+"\"");
					if(dictinfoVO.getCode().equals(value)){
						b.append(" selected=\"selected\"");
					}
					String name = dictinfoVO.getName();
					if("2".equals(dictinfoVO.getLevel())){
						name = "&nbsp;&nbsp;├&nbsp;" + name;
					}else if("3".equals(dictinfoVO.getLevel())){
						name = "&nbsp;&nbsp;&nbsp;&nbsp;├├&nbsp;" + name;
					}
					b.append(">" + name + "</option>");
				}
			}
		}
		s.append(">");
		s.append(b);
		s.append("</select>");
		s.append("<span></span>");
//		s.append("</span>");
		return s.toString();
	}
}
