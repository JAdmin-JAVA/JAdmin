package com.jadmin.modules.controller.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.jadmin.modules.util.DictinfoUtils;
import com.jadmin.util.JsonUtil;
import com.jadmin.vo.entity.base.DictkindVO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:获取tree的json数据
 * @Copyright:JAdmin (c) 2018年10月12日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SuppressWarnings("serial")
@Getter @Setter @Slf4j
public class GetTreeJson extends TagSupport {
    
	private String code;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			DictkindVO dictkind = DictinfoUtils.getDictkind(code, (HttpServletRequest) pageContext.getRequest());
			out.print(dictkind.getType() + JsonUtil.getJsonByList(dictkind.getTreeJsonList()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}

}
