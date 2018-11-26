package com.jadmin.vo.enumtype;

/** 
 * @Title:web框架
 * @Description:菜单类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public enum AdminPageMenu {
	
	system("", "&#xe653;"), // 一级菜单

	userCenter(system, "用户管理", "&#xe62b;"),
	
	baseCenter(system, "系统管理", "&#xe62e;");
	
    /** 一级菜单 */
    private AdminPageMenu menu;
   
    /** 显示标签 */
    private String label;
    
    /** 显示样式（这里指的是图标） */
    private String css;

    /** 
     * 一级菜单构造器
     * @param label
     * @param css 
     */
    AdminPageMenu(String label, String css){
        this.label = label;
        this.css = css;
    }

    /** 
     * 二级菜单构造器
     * @param menu 一级菜单
     * @param label
     * @param css 
     */
    AdminPageMenu(AdminPageMenu menu, String label, String css){
    	this.menu = menu;
        this.label = label;
        this.css = css;
    }
    
    public String getLabel() {
        return label;
    }

    public String getCss() {
        return css;
    }

    public AdminPageMenu getMenu() {
        return menu;
    }
  
}
