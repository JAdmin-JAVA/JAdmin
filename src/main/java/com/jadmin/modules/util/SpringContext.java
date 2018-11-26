package com.jadmin.modules.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/** 
 * @Title:web框架
 * @Description:控制spring的bean
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
public class SpringContext implements ApplicationContextAware {

    /**
     * context
     */
    @Autowired
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;;
    }

    /**
     * 获取bean
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }
    
    /**
     * 通过class获取Bean.
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz){
        return context.getBean(name, clazz);
    }

    /**
     * 是否包含bean
     * @param name
     * @return
     */
    public static Boolean containsBean(String name){
        return context.containsBean(name);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        SpringContext.context = context;
    }
}
