<%@page import="com.jadmin.vo.entity.base.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="admin/public/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="admin/public/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css">
<style type="text/css">
.wel_left {
	width: calc(100% - 400px);
	float: left;
}

.wel_left_module {
	width: 48.5%;
	float: left;
	margin-bottom: 20px;
}

.wel_right_module {
	width: 48.5%;
	float: right;
	margin-bottom: 20px;
}

.wel_right {
	float: right;
	width: 360px;
	height: 630px;
	border: none;
	overflow: hidden;
	margin-bottom: 20px;
}

.wel_tool_ul {
	width: 100%;
	display: block;
	height: 162px;
}

.wel_tool_li {
	text-align: center;
	padding: 5px;
	width: 25%;
	float: left;
	position: relative;
	display: block;
	box-sizing: border-box;
}

.wel-iconfont {
	background-color: #F8F8F8;
	display: inline-block;
	width: 100%;
	height: 60px;
	line-height: 60px;
	text-align: center;
	border-radius: 2px;
	font-size: 30px;
	color: #333;
	transition: all .3s;
	-webkit-transition: all .3s;
}

.wel-iconfont:hover {
	background-color: #f2f2f2;
}

.wel_tool_li a:hover {
	text-decoration: none;
}

.wel_tool_ul li cite {
	position: relative;
	top: 2px;
	display: block;
	color: #666;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
	font-size: 14px;
}

.wel_left_module .panel-body, .wel_right_module .panel-body {
	min-height: 177px;
}

.Huifold .item h4{
	background-color: white;
	border-bottom: 1px solid #eee;
	font-size: 14px;
    font-weight: normal;
    padding: 9px 10px;
}
.memo-more{
	float: right;
    font-size: 13px;
    font-weight: normal;
}
</style>
<title>我的桌面</title>
</head>
<body>
	<%
   UserVO userVO = (UserVO)session.getAttribute("CUR_USER"); 
  %>

	<div class="pd-20" style="padding-top: 20px;">
		<div class="wel_left">
			<div class="panel panel-default wel_left_module">
				<div class="panel-header">
					感谢您使用
					<html:configValue code="DEF_PROJECT_NAME" />
				</div>
				<div class="panel-body">
					<p>
						用户名：<%= userVO.getName()  %></p>
					<p>
						当前角色：<%= userVO.getRole().getRoleName() %></p>
					<p>
						登录次数：<%= userVO.getLoginCount() %></p>
					<p>
						上次登录IP：<%= userVO.getLastLoginIp() %></p>
					<p>
						上次登录时间：<%= userVO.getLastLoginTime() %></p>
				</div>
			</div>
			<div class="panel panel-default wel_right_module">
				<div class="panel-header">实用小工具</div>
				<div class="panel-body">
					<ul class="wel_tool_ul">
						<li class="wel_tool_li"><a
							href="/admin/public/tools/clock/index.html" target="_black">
								<i class="Hui-iconfont wel-iconfont">&#xe690;</i> <cite>闹钟</cite>
						</a></li>
						<li class="wel_tool_li"><a
							href="/admin/public/tools/counter/index.html" target="_black">
								<i class="Hui-iconfont wel-iconfont">&#xe68c;</i> <cite>计算器</cite>
						</a></li>
						<li class="wel_tool_li"><a
							href="/admin/public/tools/draw/index.html" target="_black"> <i
								class="Hui-iconfont wel-iconfont">&#xe646;</i> <cite>绘画板</cite>
						</a></li>
						<li class="wel_tool_li"><a
							href="/admin/public/tools/pinyin/index.html" target="_black">
								<i class="Hui-iconfont wel-iconfont">&#xe647;</i> <cite>中文转拼音</cite>
						</a></li>
						<li class="wel_tool_li"><a
							href="/admin/public/tools/chron/index.html" target="_black">
								<i class="Hui-iconfont wel-iconfont">&#xe69c;</i> <cite>秒表</cite>
						</a></li>
						<li class="wel_tool_li"><a
							href="/admin/public/tools/font/index.html" target="_black"> <i
								class="Hui-iconfont wel-iconfont">&#xe70c;</i> <cite>繁体字转换</cite>
						</a></li>
						<li class="wel_tool_li"><a
							href="/admin/public/tools/ewm/index.html" target="_black"> <i
								class="Hui-iconfont wel-iconfont">&#xe682;</i> <cite>二维码生成器</cite>
						</a></li>
						<li class="wel_tool_li"><a
							href="/admin/public/tools/calendar/index.html" target="_black">
								<i class="Hui-iconfont wel-iconfont">&#xe655;</i> <cite>万年历</cite>
						</a></li>
					</ul>

				</div>
			</div>

			<div class="panel panel-default wel_left_module">
				<div class="panel-header">常见问题</div>
				<div class="panel-body" style="padding: 0px;">
					<ul id="Huifold1" class="Huifold">
						<li class="item">
							<h4>
								系统推荐使用哪些浏览器？<b>+</b>
							</h4>
							<div class="info">系统推荐您使用IE9及以上浏览量、360浏览器极速模式、谷歌浏览器、火狐浏览器。</div>
						</li>
						<li class="item">
							<h4>
								忘记密码如何找回？<b>+</b>
							</h4>
							<div class="info">系统不支持“自助找回密码”，需要向管理员申请，重置密码。</div>
						</li>
						<li class="item">
							<h4>
								页面出现500错误怎么办？<b>+</b>
							</h4>
							<div class="info">建议您刷新页面，如果仍然无法解决，请联系管理员。</div>
						</li>
						<li class="item">
							<h4>
								如何下载列表界面的数据？<b>+</b>
							</h4>
							<div class="info">
								在列表页面的右上角，点击 <i class="Hui-iconfont"></i> 按钮，即可下载数据。
							</div>
						</li>
						<li class="item">
							<h4 style="border-bottom: 0px;">
								如何编辑或删除历史备忘笺？<b>+</b>
							</h4>
							<div class="info">点击“备忘笺”右上角的“更多”按钮，进入复杂的编辑模式，可以对备忘笺进行编辑、删除操作。</div>
						</li>
					</ul>
				</div>
			</div>

			<div class="panel panel-default wel_right_module">
				<div class="panel-header">
					备忘笺<a class="memo-more" _href="/memorandum/getAll?hideTitle=true" data-title="我的便签" href="javascript:;"
					         onclick="parent.Hui_admin_tab(this);">更多></a>
				</div>
				<div class="panel-body" style="padding: 0px; min-height: 207px;">
					<ul id="Huifold2" class="Huifold">
						<c:forEach items="${memorandums}" var="memo">
							<li class="item">
								<h4 class="text-overflow" style="padding-right: 30px;">${memo.comOperateDate }：${memo.content }<b>+</b></h4>
								<div class="info">${memo.content }</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>

		<iframe class="wel_right" src="/admin/public/tools/rili/index.html"></iframe>

	</div>
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="/lib/layer/3.1.0/layer.js"></script>
	<script type="text/javascript" src="/admin/public/js/H-ui.js"></script> 
	<script type="text/javascript" src="/admin/public/js/H-ui.admin.js"></script>
	<script type="text/javascript">
		$(function(){
			$.Huifold("#Huifold1 .item h4","#Huifold1 .item .info","fast",1,"click"); /*5个参数顺序不可打乱，分别是：相应区,隐藏显示的内容,速度,类型,事件*/
			$.Huifold("#Huifold2 .item h4","#Huifold2 .item .info","fast",1,"click");
		});
		function addMemo(date, value){
			var html = '<li class="item">';
			html += '<h4 class="text-overflow" style="padding-right: 30px;">' + date + '：' + value + '<b>+</b></h4>';
			html += '<div class="info">' + value + '</div>';
			html += '</li>';
			$('#Huifold2').prepend(html);
			$.Huifold("#Huifold2 .item h4","#Huifold2 .item .info","fast",1,"click");
		}
	</script>
</body>
</html>