package com.jadmin.modules.controller.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.annotation.Configuration;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.vo.entity.base.ConfigVO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:获取系统设置的值
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SuppressWarnings("serial")
@Configuration
@Getter @Setter @Slf4j
public class ConfigTagValue extends TagSupport {

	private String code;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		SystemDao systemDao = (SystemDao) SpringContext.getBean("systemDao");
		ConfigVO configVo = systemDao.getConfigVO(code);
		try {
			out.print(configVo.getCoValue());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}

}
