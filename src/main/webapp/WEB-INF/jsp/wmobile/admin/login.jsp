<%@page import="com.jadmin.vo.entity.base.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html class="ui-page-login">

<head>
	<title>登陆 | <html:configValue code="DEF_PROJECT_NAME"/></title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title></title>
	<link href="/lib/mui/css/mui.min.css" rel="stylesheet" />
	<link href="/lib/mui/css/style.css" rel="stylesheet" />
	<style>
		.area {
			margin: 20px auto 0px auto;
		}
		
		.mui-input-group {
			margin-top: 10px;
		}
		
		.mui-input-group:first-child {
			margin-top: 20px;
		}
		
		.mui-input-group label {
			width: 22%;
		}
		
		.mui-input-row label~input,
		.mui-input-row label~select,
		.mui-input-row label~textarea {
			width: 78%;
		}
		
		.mui-checkbox input[type=checkbox],
		.mui-radio input[type=radio] {
			top: 6px;
		}
		
		.mui-content-padded {
			margin-top: 25px;
		}
		
		.mui-btn {
			padding: 10px;
		}
		
		.link-area {
			display: block;
			margin-top: 25px;
			text-align: center;
		}
		
		.spliter {
			color: #bbb;
			padding: 0px 8px;
		}
		
		.oauth-area {
			position: absolute;
			bottom: 20px;
			left: 0px;
			text-align: center;
			width: 100%;
			padding: 0px;
			margin: 0px;
		}
		
		.oauth-area .oauth-btn {
			display: inline-block;
			width: 50px;
			height: 50px;
			background-size: 30px 30px;
			background-position: center center;
			background-repeat: no-repeat;
			margin: 0px 20px;
			/*-webkit-filter: grayscale(100%); */
			border: solid 1px #ddd;
			border-radius: 25px;
		}
		
		.oauth-area .oauth-btn:active {
			border: solid 1px #aaa;
		}
		
		.oauth-area .oauth-btn.disabled {
			background-color: #ddd;
		}
	</style>

</head>

<body>
	<header class="mui-bar mui-bar-nav">
		<h1 class="mui-title"><html:configValue code="DEF_PROJECT_NAME"/></h1>
	</header>
	<div class="mui-content">
		<form id='login-form' class="mui-input-group">
			<div class="mui-input-row">
				<label>账号</label>
				<input id='account' type="text" class="mui-input-clear mui-input" placeholder="请输入账号">
			</div>
			<div class="mui-input-row">
				<label>密码</label>
				<input id='password' type="password" class="mui-input-clear mui-input" placeholder="请输入密码">
			</div>
		</form>
		<form class="mui-input-group">
			<ul class="mui-table-view mui-table-view-chevron">
				<li class="mui-table-view-cell">
					记住密码
					<div id="autoLogin" class="mui-switch">
						<div class="mui-switch-handle"></div>
					</div>
				</li>
			</ul>
		</form>
		<div class="mui-content-padded">
			<button id='login' onclick="login()" class="mui-btn mui-btn-block mui-btn-primary">登录</button>
			<%-- <div class="link-area">系统初始密码为<html:configValue code="DEF_PASSWORD"/>
			</div> --%>
		</div>
		<div class="mui-content-padded oauth-area">
			<!-- 技术支持&&军创盛安 -->
		</div>
	</div>
	<script src="/lib/mui/js/mui.min.js"></script>
	<script src="/lib/mui/js/mui.enterfocus.js"></script>
	<script src="/lib/mui/js/app.js"></script>
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="/admin/public/js/cookie.js"></script>
	
	<script language="javascript">

	(function($, doc) {
		$.init();
	}(mui, document));
	
	//登录
	function login() {
		var errorMsg = "";
		var loginName = document.getElementById("account");
		var password = document.getElementById("password");
		if(!loginName.value){
			errorMsg += "用户名不能为空!";
		}
		if(!password.value){
			errorMsg += "密码不能为空!";
		}
		if(errorMsg != ""){
			mui.toast(errorMsg);
		}
		else{
			mui.toast("正在登录中...");
			$.post("toLogin?account=" + loginName.value + "&password=" + password.value, function(result) {
				if(result == "success"){
					  if($("#autoLogin").hasClass('mui-active')){
						  setCookie("account",loginName.value,365 * 24,"/");
						  setCookie("password",password.value,365 * 24,"/");
						  setCookie("isSave","1",365 * 24,"/");
					  }else{
						  deleteCookie("account", "/");
						  deleteCookie("password", "/");
						  deleteCookie("isSave", "0");
					  }
					  mui.toast("登录成功，正在转到主页...");
					  window.location.href = "admin";
				  } else{
					  mui.toast(result);
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
			if("1" == isSave){
				$("#autoLogin").addClass('mui-active');
			}
		} catch (err) {
			//alert("NO RMB PASSWORD!");
		}
	}
	
	
</script>
	
</body>

</html>