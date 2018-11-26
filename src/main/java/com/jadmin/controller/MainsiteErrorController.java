package com.jadmin.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.annotation.NoNeedLogin;

/** 
 * @Title:web框架
 * @Description: 
 * 	更换404和500页面
 *  spring boot并不带404的配置功能，实现方法有多种，暂未具体研究
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
public class MainsiteErrorController implements ErrorController {
	
    private static final String ERROR_PATH = "/error";
    
    @RequestMapping(value = ERROR_PATH)
    @NoNeedLogin //无需登录就能访问
    public String handleError(HttpServletResponse response){
    	if(response.getStatus() == 404) {
        	return "public/404";
    	}
        return "public/500";
    }
    
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
