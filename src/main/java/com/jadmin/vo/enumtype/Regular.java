package com.jadmin.vo.enumtype;

/** 
 * @Title:web框架
 * @Description:正则
 * @Copyright:JAdmin (c) 2018年09月12日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public @interface Regular {
	
	/** 账号的正则 */
	public static String account = "/^[a-zA-z]\\w{3,15}$/";
	
	/** 数字的正则  */
	public static String numer = "/^[0-9]*$/";
	
	/** 正整数的正则  */
	public static String posInteger = "/^([1-9]\\d*|[0]{1,1})$/";
	
	/** 邮箱的正则 */
	public static String email = "/^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$/";
	
	/** 电话的正则 */
	public static String phone = "/^\\s*[.0-9]{8,11}\\s*$/";
	
	/** 手机号的正则 */
	public static String mobile = "/^1[34578]\\d{9}$/";
	
	/** 身份证号的正则 */
	public static String idcard = "/^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$/";
	
	/** QQ的正则 */
	public static String QQ = "/^\\s*[.0-9]{5,10}\\s*$/";
	
	/**车牌号正则表达式 */
	public static String carNumber = "/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/";
	
	/** 姓名的正则 */
	public static String uname = "/[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*/";
	
	/** 密码的正则 */
	public static String passWord = "/^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,22}$/";
	
	/** 图片的正则 */
	public static String pictureUrl = "/([ ]?(.jpg|.JPG|.JPEG|.jpeg|.BMP|.bmp|.gif|.GIF|.PNG|.png)$)/";
}
