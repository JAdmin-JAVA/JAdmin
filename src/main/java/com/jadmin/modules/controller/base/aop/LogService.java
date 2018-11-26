package com.jadmin.modules.controller.base.aop;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jadmin.modules.controller.base.BaseAbstractController;
import com.jadmin.modules.dao.base.BaseBusinessDao;
import com.jadmin.modules.util.AdminPageUtils;
import com.jadmin.vo.entity.base.LogVO;
import com.jadmin.vo.entity.base.UserVO;
import com.jadmin.vo.fundation.controller.AdminPageLogVO;
import com.jadmin.vo.fundation.tool.ClientENV;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:日志AOP切片记录 纯注解模式
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 * 
 * @Aspect 实现spring aop 切面（Aspect）：
 *         一个关注点的模块化，这个关注点可能会横切多个对象。事务管理是J2EE应用中一个关于横切关注点的很好的例子。 在Spring
 *         AOP中，切面可以使用通用类（基于模式的风格） 或者在普通类中以 @Aspect 注解（@AspectJ风格）来实现。
 * 
 *         AOP代理（AOP Proxy）： AOP框架创建的对象，用来实现切面契约（aspect contract）（包括通知方法执行等功能）。
 *         在Spring中，AOP代理可以是JDK动态代理或者CGLIB代理。 注意：Spring
 *         2.0最新引入的基于模式（schema-based
 *         ）风格和@AspectJ注解风格的切面声明，对于使用这些风格的用户来说，代理的创建是透明的。
 */
@Component
@Aspect
@Slf4j
public class LogService {
	
	@Autowired
	private BaseBusinessDao baseDao;

	/**
	 * 在Spring
	 * 2.0中，Pointcut的定义包括两个部分：Pointcut表示式(expression)和Pointcut签名(signature
	 * )。让我们先看看execution表示式的格式：
	 * 括号中各个pattern分别表示修饰符匹配（modifier-pattern?）、返回值匹配（ret
	 * -type-pattern）、类路径匹配（declaring
	 * -type-pattern?）、方法名匹配（name-pattern）、参数匹配（(param
	 * -pattern)）、异常类型匹配（throws-pattern?），其中后面跟着“?”的是可选项。
	 * 
	 * @param point
	 * @throws Throwable
	 */
	@Pointcut("@annotation(com.jadmin.modules.annotation.MethodLog)")
	public void methodCachePointcut() {

	}
	
	@Around("methodCachePointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String url = request.getRequestURI();
        
		String monthName = point.getSignature().getName();
		String packages = point.getThis().getClass().getName();
		if (packages.indexOf("$$EnhancerByCGLIB$$") > -1) { // 如果是CGLIB动态生成的类
			try {
				packages = packages.substring(0, packages.indexOf("$$"));
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}

		Object object;
		try {
			object = point.proceed();
		} catch (Exception e) {
			// 异常处理记录日志..log.error(e);
			throw e;
		}
		
		// 給据url获得日志的一些基础对象，是給据注解获得的，可以独立该模块
    	AdminPageLogVO aplVO = AdminPageUtils.findLogMenthodByUrl(url);
    	if(aplVO != null){
    		LogVO vo = new LogVO();
    		vo.setUrl(url);
    		vo.setClientIp(request.getRemoteAddr());
    		vo.setLevel("1");
    		if(request.getAttribute(BaseAbstractController.SYS_LOG_TYPE) != null){
    			vo.setType(request.getAttribute(BaseAbstractController.SYS_LOG_TYPE).toString());
    		}else if(StringUtils.isNotBlank(aplVO.getType())){
    			vo.setType(aplVO.getType());
    		}
    		if(StringUtils.isBlank(vo.getType())){
    			log.error("【请修改】未设置日志注解MethodLog的value的值" + packages + "." + monthName);
    		}
    		String content = "";
    		if(StringUtils.isNotBlank(aplVO.getMenuName())){
    			content += aplVO.getMenuName() + vo.getType();
    		}
    		if(StringUtils.isNotBlank(aplVO.getType())){
    			content += aplVO.getType();
    		}
    		if(StringUtils.isNotBlank(content)){
    			content += "：";
    		}
    		if(!object.toString().startsWith("forward") && !object.toString().startsWith("redirect")){
    			content += "返回结果：" + object + "，";
    		}
    		for (String str : aplVO.getContent()) {
    			if(request.getAttribute(str) != null){
    				content += str + "：" + request.getAttribute(str) + "，";
    			}
    		}
    		if(content.endsWith("，")){
    			content = content.substring(0, content.length() - 1);
    		}
    		vo.setContent(content);
    		vo.setOperateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
    		UserVO userVO = new ClientENV(request.getSession()).getCurUser();
    		if(userVO != null){
    			vo.setUserId(userVO.getAccount());
    		}
    		// 保存到数据库
        	baseDao.save(vo);
    	}else{
    		log.error("未找到日记记录对象的初始化注解：" + packages + "." + monthName);
    	}
		return object;
	}

	// 方法运行出现异常时调用	
	public void afterThrowing(Exception ex) {
		log.error(ex.getMessage(), ex);
	}
}

