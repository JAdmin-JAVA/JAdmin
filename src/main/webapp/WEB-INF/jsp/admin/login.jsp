<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<title>登陆 | <html:configValue code="DEF_PROJECT_NAME" /></title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<link rel="icon" href="/admin/public/images/favicon.png" type="image/x-icon"/>
	<link href="/admin/public/css/H-ui.login.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="/admin/public/js/cookie.js"></script>
</head>
<body style="background-color: #1c77ac; background-repeat: no-repeat; background-position: center top; overflow: hidden;">

	<div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>

	<div class="loginbody">

		<div class="systemlogoDiv">
			<span class="systemlogo"> <html:configValue
					code="DEF_PROJECT_NAME" /></span>
		</div>
		
		<div class="loginbox">
			<form id="loginForm" action="logon.do" class="login_form"
				method="post">
				<ul>
					<li><input type="text" class="loginuser"
						onclick="JavaScript:this.value=''" id="account" name="account"
						value="" /></li>
					<li><input type="password" class="loginpwd" id="password"
						name="password" value="" onclick="JavaScript:this.value=''" /></li>
					<li style="margin-bottom: 15px;"><input
						style="float: left; margin-top: 10px;" name="rmbPassword"
						type="checkbox" id="rmbPassword" value="记住密码" /> <span
						style="float: left; margin-top: 9px;">&nbsp;&nbsp;记住密码&nbsp;&nbsp;</span>
						<input style="" name="" type="button" class="loginbtn" value="登录"
						onclick="login()" /></li>
					<li style="margin-bottom: 0px;">
						<div>
							系统初始密码为
							<html:configValue code="DEF_PASSWORD" />
							，第一次登陆后，请修改密码！
						</div>
					</li>
				</ul>

			</form>

			<div class="login_info"
				style="display: none; color: #0b3a58; position: relative; top: -10px;"></div>
		</div>

	</div>


	<div class="loginbm">请使用IE浏览器，IE9以上的浏览器请调至兼容模式</div>

	<script language="javascript">
		var browser = {
			versions : function() {
				var u = navigator.userAgent, app = navigator.appVersion;
				return {//移动终端浏览器版本信息   
					trident : u.indexOf('Trident') > -1, //IE内核  
					presto : u.indexOf('Presto') > -1, //opera内核  
					webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核  
					gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
					mobile : !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端  
					ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
					android : u.indexOf('Android') > -1
							|| u.indexOf('Linux') > -1, //android终端或者uc浏览器  
					iPhone : u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器  
					iPad : u.indexOf('iPad') > -1, //是否iPad    
					webApp : u.indexOf('Safari') == -1
				//是否web应该程序，没有头部与底部  
				};
			}(),
			language : (navigator.browserLanguage || navigator.language)
					.toLowerCase()
		};
		
		$(function() {
			//居中
			document.getElementById("account").focus();
			$("#account").keydown(function(event) {
				if (event.keyCode == 13) {
					login();
				}
			});
			$("#password").keydown(function(event) {
				if (event.keyCode == 13) {
					login();
				}
			});

			$('.loginbox').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 692) / 2
			});
			$(window).resize(function() {
				$('.loginbox').css({
					'position' : 'absolute',
					'left' : ($(window).width() - 692) / 2
				});
			});
		});

		//登录
		function login() {
			var errorMsg = "";
			var loginName = document.getElementById("account");
			var password = document.getElementById("password");
			if (!loginName.value) {
				errorMsg += "&nbsp;&nbsp;用户名不能为空!";
			}
			if (!password.value) {
				errorMsg += "&nbsp;&nbsp;密码不能为空!";
			}
			if (errorMsg != "") {
				$(".login_info").html(errorMsg);
				$(".login_info").show();
			} else {
				$(".login_info").show();
				$(".login_info").html("&nbsp;&nbsp;正在登录中...");
				$.ajax('/toLogin',{
					data:{
						account:loginName.value,
						password: password.value
					},
					charset:'utf-8',
					dataType:'json',/*服务器返回json格式数据*/
					type:'post',/*HTTP请求类型*/
					timeout:1000000,/*超时时间设置为10秒*/
					success:function(data){
						if(data.status){
							if (document.all.rmbPassword.checked) {
								setCookie("account", loginName.value,
										365 * 24, "/");
								setCookie("password", password.value,
										365 * 24, "/");
								setCookie("isSave", "1", 365 * 24, "/");
							} else {
								deleteCookie("account", "/");
								deleteCookie("password", "/");
								deleteCookie("isSave", "0");
							}
							$(".login_info").html(
									"&nbsp;&nbsp;登录成功，正在转到主页...");
							window.location.href = "admin";
						}else{
							$(".login_info").html("&nbsp;&nbsp;" + data.errorMsg);
						}
					},
					error:function(xhr,type,errorThrown){
						alert(errorThrown);
					}
				});
			}
		}
		

		// 获取cookie信息
		getRememberInfo();
		
		//获取cookie信息
		function getRememberInfo() {
			try {
				var account = getCookieValue("account");
				var password = getCookieValue("password");
				var isSave = getCookieValue("isSave");
				document.getElementById("account").value = account;
				document.getElementById("password").value = password;
				if ("1" == isSave) {
					document.getElementById("rmbPassword").checked = "checked";
				}
			} catch (err) {
				//alert("NO RMB PASSWORD!");
			}
		}
	</script>
</body>
</html>