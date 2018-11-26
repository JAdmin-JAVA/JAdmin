package com.jadmin.modules.controller.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.jadmin.util.DateTimeUtil;
import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:自定义日期标签
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SuppressWarnings("serial")
@Getter @Setter @Slf4j
public class DateTag extends TagSupport {
    
	private String name;
	private String value;
	private String dateFmt;
	private AbstractValueObject vo;
	private String attribute;
	private String datatype;
	private String maxDate;

	@Override
	public int doStartTag() throws JspException {
		StringBuffer s = new StringBuffer();
		JspWriter out = pageContext.getOut();
		if(StringUtils.isBlank(value) && StringUtils.isNotBlank(attribute) && vo != null){
			if(vo.get(attribute)==null){
				value = "";
			}else{
				value =  vo.get(attribute).toString();
			}
		}
		// 如果为空，查看是否有默认值
		if(StringUtils.isBlank(value)){
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			Object iV = request.getAttribute(attribute);
			if(iV != null && StringUtils.isNotBlank(iV.toString())){
				value = iV.toString();
			}
		}
		s.append("<input autocomplete=\"off\" style=\"width:160px\" type=\"text\" onFocus=\"WdatePicker({skin:'whyGreen',dateFmt:'");
		if(StringUtils.isBlank(dateFmt)) {
			dateFmt = "yyyy-MM-dd";
		}
		s.append(dateFmt).append("'");
		if("today".equals(maxDate)) {
			maxDate = DateTimeUtil.getDate();
		}
		
		if(StringUtils.isNotBlank(maxDate)){
			s.append(",maxDate:'%y-%M-%d'");
		}
		
		s.append("})\" class=\"input-text Wdate\"");
		
		if(StringUtils.isNotBlank(datatype)){
			s.append(" datatype=\"" + datatype + "\" ");
		}
		s.append(" name=\""+name+"\" id=\""+id+"\" value=\""+value+"\"/>");
		try {
			out.print(s.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}
	
}
