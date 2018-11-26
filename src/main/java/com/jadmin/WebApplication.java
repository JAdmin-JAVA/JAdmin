package com.jadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jadmin.modules.annotation.quartz.EnableQuartz;
import com.jadmin.modules.controller.base.CustomSecurityInterceptor;

/** 
 * @Title:web框架
 * @Description:spring boot启动类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SpringBootApplication
@Configuration
@EnableQuartz //启动调度
public class WebApplication extends SpringBootServletInitializer implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}
	
	/**
	 * 拦截器固定写法
	 * 如果不实现此方法拦截器不工作
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加一个拦截器，连接以/admin为前缀的 url路径
		registry.addInterceptor(new CustomSecurityInterceptor()).addPathPatterns("/**");
	}
	
}
