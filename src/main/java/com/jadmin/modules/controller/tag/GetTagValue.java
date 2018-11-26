package com.jadmin.modules.controller.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.jadmin.modules.util.encode.Encode;
import com.jadmin.vo.fundation.base.AbstractValueObject;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:获取select标签的值
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SuppressWarnings("serial")
@Getter @Setter @Slf4j
public class GetTagValue extends TagSupport {

	private AbstractValueObject vo;
	private String attribute;
	private String videoAttribute;
	private String selectCode;
	private String encode;
	private boolean getLength;
	private boolean isImg;
	private Object initValue;
	
	// 最长显示多少个字符
	private int maxLength;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(getValue(vo, attribute, selectCode, encode, maxLength, getLength, initValue, isImg, videoAttribute));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}
	
	/**
	 * 封装一下，其他地方会用
	 * @param vo
	 * @param attribute
	 * @param selectCode
	 * @param encode
	 * @param maxLength
	 * @param getLength
	 * @param initValue
	 * @param isImg
	 * @param videoAttribute
	 * @return
	 */
	public static Object getValue(AbstractValueObject vo, String attribute, String selectCode, 
			String encode, int maxLength, boolean getLength, Object initValue, Boolean isImg, String videoAttribute){
		Object obj = null;
		if (vo != null && attribute != null && !"".equals(attribute)
				&& !"".equals(vo)) {
			obj = vo.get(attribute);
			if (selectCode != null && !"".equals(selectCode)) {
				obj = vo.getSelectName(selectCode, obj);
			}
		}
		try {
			if(obj==null){
				obj="";
			}
			if(StringUtils.isNotBlank(encode) && StringUtils.isNotBlank(obj.toString())){
				obj = Encode.decode(obj.toString());
			}
			if(getLength){
				obj = obj.toString().length();
			}
			if(obj instanceof String){
				String str = ((String) obj).trim();
				if(maxLength > 0 && str.length() > maxLength){
					str = str.substring(0, maxLength) + "...";
				}
				obj = str;
			}
			if(obj == null || StringUtils.isBlank(obj.toString())){
				if(initValue != null){
					obj = initValue;
				}
			}
			if(isImg){
				String video = StringUtils.isBlank(videoAttribute) ? "" : (String) vo.get(videoAttribute);
				obj = "<img class='imgA' videoPath='" + video + "' width='30px' height = '30px' src='" + obj + "'/><span style='position: absolute;'> <img class='hImg' width='200px' src='" + obj + "'></span>  ";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		obj = obj.toString().replaceAll("\"", "&quot;");
		return obj;
	}

	public boolean getIsImg() {
		return isImg;
	}

	public void setIsImg(boolean isImg) {
		this.isImg = isImg;
	}
}
