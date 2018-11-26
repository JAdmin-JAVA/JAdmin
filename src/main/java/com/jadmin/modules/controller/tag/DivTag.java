package com.jadmin.modules.controller.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:自定义div标签
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SuppressWarnings("serial")
@Getter @Setter @Slf4j
public class DivTag extends TagSupport {
    
	private String required;
	private String name;
	private String value;
	private Integer widthScale;
	
	/** 用来记录是否是占比50%元素的右边 */
	private final static String DIVTAG_TEMP_ED_TYPE = "DIVTAG_TEMP_ED_TYPE";
	
	@Override
	public int doStartTag() throws JspException {
		if(widthScale == null) {
			widthScale = 100;
		}
		// 记录一下是否是右边
		String edType = (widthScale == 50 && "50_left".equals(pageContext.getAttribute(DIVTAG_TEMP_ED_TYPE)) ? "50_rigth" : widthScale + "_left");
		pageContext.setAttribute(DIVTAG_TEMP_ED_TYPE, edType);
		StringBuffer s = new StringBuffer();
		JspWriter out = pageContext.getOut();
		s.append(" <div class=\"row cl editDiv ed_" + widthScale + " ed_" + edType + "\"><label class=\"form-label col-xs-4 col-sm-3-msun\">");
		if("required".equals(required)){
			s.append("<span class=\"c-red\">*</span>");
		}
		s.append(name+"：</label><div class=\"formControls col-xs-5 col-sm-7\" style=\"padding-left:0px;\"> ");
		try {
			out.print(s.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		StringBuffer s = new StringBuffer();
		JspWriter out = pageContext.getOut();
//		s.append("<label id=\"" + value + "-error\" class=\"error valid\" for=\"" + value + "\"></label></div></div>");col-4
		s.append("</div><div></div></div>");
		try {
			out.print(s.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}
}
