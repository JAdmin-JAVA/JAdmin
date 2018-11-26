package com.jadmin.modules.util.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/** 
 * @Title:web框架
 * @Description:JobFactory工厂类，用来定义创建job的时候，支持spring 注入
 * @Copyright:JAdmin (c) 2018年10月30日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法  
        Object jobInstance = super.createJobInstance(bundle);
        //进行注入  
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}  
