package com.jadmin.modules.controller.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.jadmin.modules.exception.BusinessException;
import com.jadmin.modules.itf.GeneralOperatUtils;
import com.jadmin.modules.util.ClassUtil;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description: 
 * 	统一处理ResponseBody类的请求异常
 *  TODO:@ControllerAdvice也可统一处理异常，但不知道如何区分是json类的请求，故而采用AOP切片的方式处理
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
@Aspect
@Slf4j
public class JsonExceptionHadler extends GeneralOperatUtils{

	/**
	 * @param point
	 * @throws Throwable
	 */
	@Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
	public void methodCachePointcut() {

	}
	
	@Around("methodCachePointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		try {
			return point.proceed();
		} catch(BusinessException e) {
			log.error(e.getMessage(), e);
			String type = ClassUtil.getNowMethodType(point);
			if(!"java.util.Map<java.lang.String, java.lang.Object>".equals(type) && !"java.lang.Object".equals(type)) {
				log.error("友情提示：框架只对ResponseBody方法返回类型为Object和Map<String, Object>的请求进行【JSON异常】统一处理，此处并未得到处理！");
				throw e;
			}
			return getRuturnJsonMap(e);
		}
	}

}

