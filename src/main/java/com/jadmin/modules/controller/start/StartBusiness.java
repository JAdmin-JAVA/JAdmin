package com.jadmin.modules.controller.start;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jadmin.modules.quartz.base.BaseJob;
import com.jadmin.modules.util.AdminPageUtils;
import com.jadmin.modules.util.ConfigUtil;
import com.jadmin.modules.util.StartCacheUtil;
import com.jadmin.modules.util.quartz.QuartzUtil;
import com.jadmin.vo.fundation.tool.Commons;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:spring boot 启动类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
@Slf4j
public class StartBusiness implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		try {
			
			// 如果是测试，不初始化一下模块
			if(BaseJob.isDebugStart) return;
			
			Commons.getInstance().setExcludedUrls(AdminPageUtils.getNoNeedLoginUrl());
			log.info("成功缓存不需要拦截的url");
			
			Commons.getInstance().setAdminPageMenus(AdminPageUtils.getAdminPageMenus());
			log.info("成功缓存权限模块");

			Commons.getInstance().setLogMenthods(AdminPageUtils.getLogMenthods());
			log.info("成功缓存需要记录日志的请求");

			// 缓存其他所有
			StartCacheUtil.refurbish();

			// 开启调度
			QuartzUtil.startAllJob();

			// 开启页面缓存
			if(!ConfigUtil.getJAdminProperty().getDebug()) {
				new Thread() {
					public void run() {
						try {
							Thread.sleep(10000);
							List<String> list = AdminPageUtils.getStaticPages();
							Commons.getInstance().setStaticPages(list);
							log.info("成功静态化页面：" + list.size() + "个");
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
					};
				}.start();
			}

		} catch (Exception e) {
			log.error("加载失败！", e);
		}
	}
}
