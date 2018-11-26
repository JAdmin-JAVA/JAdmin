package com.jadmin.modules.util.quartz;

import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/** 
 * @Title:web框架
 * @Description:Quartz用到的Bean的声明
 * @Copyright:JAdmin (c) 2018年11月26日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Configuration
public class QuartzBean {
    
    @Autowired
    JobFactory jobFactory;

    /**
     * 注册调度器
     * @return
     */
    @Bean
    public SchedulerFactoryBean createSchedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public JobDetailImpl createJobDetailImpl() {
        return new JobDetailImpl();
    }
}
