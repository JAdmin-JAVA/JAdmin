package com.jadmin.modules.exception;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:前台用弹出框的方式处理异常，并返回到当前请求的页面
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
public class AlertBusinessException extends BusinessException {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 构造器
     * @param msg
     */
    public AlertBusinessException(String msg) {
        super(msg);
    }
    
    /**
     * 前台用弹出框的方式处理异常，并返回到当前请求的页面
     * @param response
     * @param messge
     */
    public static void hiddleAlertException(HttpServletRequest request, HttpServletResponse response, String messge) {
    	hiddleAlertException(request, response, null, messge);
    }
    
    /**
     * 前台用弹出框的方式处理异常，并返回到当前请求的页面
     * @param response
     * @param hideTitle 是否是layer的方式，如果是的话另外一种js
     * @param messge
     */
    public static void hiddleAlertException(HttpServletRequest request, HttpServletResponse response, String hideTitle, String messge) {
    	String js = "true".equals(hideTitle) ? "parent.layer.closeAll();" : "history.go(-1);";
    	hiddleAlertException(request, response, "", messge, js);
    }
    
    /**
     * 前台用弹出框的方式处理异常，并返回到指定的页面
     * @param response
     * @param title
     * @param messge
     * @param url
     */
    public static void hiddleAlertException(HttpServletRequest request, HttpServletResponse response, String title, String messge, String url) {
    	try {
    		request.setAttribute("title", title);
    		request.setAttribute("messge", messge);
    		request.setAttribute("url", url);
			request.getRequestDispatcher("/WEB-INF/jsp/public/alert-exception.jsp").forward(request, response);
		} catch (Exception e) {
            log.error(e.getMessage(), e);
		}
	}

    /**
     * 前台用弹出框的方式处理异常，并返回到授权页面
     * @param response
     * @param title
     * @param messge
     * @param url
     */
    public static void hiddleAutoException(HttpServletResponse response) {
    	PrintWriter out = null;
        try {
            out = response.getWriter();
            StringBuffer exePage = new StringBuffer();
            exePage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
            exePage.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
            exePage.append("    <head>\n");
            exePage.append("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
            exePage.append("        <title></title>\n");
            exePage.append("        <script type=\"text/javascript\" src=\"/lib/jquery/1.9.1/jquery.min.js\"></script>\n");
            exePage.append("        <script type=\"text/javascript\" src=\"/lib/layer/3.1.0/layer.js\"></script>\n");
            exePage.append("        <script type=\"text/javascript\" src=\"/admin/public/js/H-ui.js\"></script>\n");
            exePage.append("        <script type=\"text/javascript\" src=\"/admin/public/js/H-ui.admin.js\"></script>\n");
            exePage.append("    </head>\n");
            exePage.append("    <body>\n");
            exePage.append("        <div>\n");
            exePage.append("        </div>\n");
            exePage.append("    </body>\n");
            exePage.append("    <script type=\"text/javascript\">\n");
            exePage.append("        parent.window.location = '/authorize/index';\n");
            exePage.append("    </script>\n");
            exePage.append("</html>");
            out.write(exePage.toString());
            out.flush();
        } catch (Exception e) {
             log.error("Exception:", e);
        } finally {
            if (out != null){
                out.close();
            }
        }
	}
}