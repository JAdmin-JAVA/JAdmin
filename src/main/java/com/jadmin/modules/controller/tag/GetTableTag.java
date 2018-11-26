package com.jadmin.modules.controller.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.util.AdminPageUtils;

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
@SuppressWarnings({"serial", "rawtypes"})
@Getter @Setter @Slf4j
public class GetTableTag extends TagSupport {

	private Class<? extends CommonListController> table;
	private String idColunm;
	private String idColunmValue;
	private String type;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			table.newInstance().getAll("0", "20", request, 
					(HttpServletResponse) pageContext.getResponse());
			if("1".equals(type)){
				System.out.println(AdminPageUtils.findAdminPageButtonsByClass(table));
				out.print(AdminPageUtils.findAdminPageButtonsByClass(table));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}

}
