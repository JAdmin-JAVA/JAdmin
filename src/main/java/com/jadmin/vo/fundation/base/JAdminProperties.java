package com.jadmin.vo.fundation.base;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/** 
 * @Title:web框架
 * @Description:配置文件属性
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Component
@ConfigurationProperties(prefix = "jadmin")
@Getter @Setter
public class JAdminProperties {
	
	/** 项目的本地基础路径 */
    private String fileBasePath;
	
	/** 是否是调试模式 */
    private Boolean debug;

	/** 该属性为后台页面，二级菜单的显示顺序，写在前面，就显示在前面 */
    private List<String> adminPage;

	/** 该属性为后台页面，页面按钮的显示顺序，写在前面，就显示在前面 */
    private List<String> adminButton;

	/** 如果需要通过配置文件指定 启动哪些调度，可在此处配置，注意需开启WebApplication类的@EnableQuartz注解，并将ymlConfig设置为true */
    private List<String> taskList;
}
