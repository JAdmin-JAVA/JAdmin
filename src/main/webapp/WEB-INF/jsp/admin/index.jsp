<%@page import="com.jadmin.vo.entity.base.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="part/head.jsp"></jsp:include>
<link href="/admin/public/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<title><html:configValue code="DEF_PROJECT_NAME"/></title>
</head>
<body>
<% 
UserVO userVO = (UserVO)session.getAttribute("CUR_USER"); 
%>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> 
			<a class="logo navbar-logo f-l mr-10 hidden-xs" href="/admin"><html:configValue code="DEF_PROJECT_NAME"/></a>
			<span class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0</span>
			
			<nav class="nav navbar-nav">
				<ul class="cl">
					<c:forEach items="${menus}" var="menu" varStatus="statu">
						<li class="dropDown dropDown_hover <c:if test="${statu.first}">current</c:if>">
							<a href="javascript:;" class="dropDown_A" _id="${menu.enName }">
								<i class="Hui-iconfont" style="top: -2px; position: relative;">${menu.css }</i> ${menu.name }
							</a>
						</li>
					</c:forEach>
				</ul>
			</nav>
			
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li>
					<%= userVO.getOrg().getName()  %>
					</li>
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A"><%= userVO.getName()  %> <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li>
								<a href="javascript:;" onclick="layer_show('修改密码','pwd?id=<%= userVO.getUserId()  %>','650','380')" >
								修改密码</a>
							</li>
							<li><a href="quit">退出</a></li>
						</ul>
					</li>
					<%-- <li id="Hui-msg"> <a href="javascript:;" onclick="layer_show_inbox('未读消息','inbox?id=<%=userVO.getUserId() %>','610','380')" title="消息"><span class="badge badge-danger" id="messageCount">${messageCount}</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li> --%>
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="default" title="默认（蓝色）">默认（蓝色）</a></li>
							<li><a href="javascript:;" data-val="black" title="黑色">黑色</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<c:forEach items="${menus}" var="upMenu" varStatus="statu">
			<c:forEach items="${upMenu.menus}" var="menu">
		    <dl class="msun_menu_${upMenu.enName }" <c:if test="${!statu.first}">style="display:none;"</c:if>>
		      <c:if test="${empty menu.onlyUrl}">
			    <dt><i class="Hui-iconfont">${menu.css }</i> ${menu.name }<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			    <dd>
		      	  <ul>
			        <c:forEach items="${menu.pages}" var="page">
			      	  <c:if test="${!page.targetBlank }">
			      			<li><a _href="${page.url}" data-title="${page.name}" href="javascript:;">${page.name}</a></li>
			      	  </c:if>
			      	  <c:if test="${page.targetBlank }">
			      			<li><a target="_blank" data-title="${page.name}" href="${page.url}">${page.name}</a></li>
			      	  </c:if>
					</c:forEach>
			      </ul>
		     	</dd>
		      </c:if>
		      <c:if test="${!empty menu.onlyUrl}">
		      	<dt class=""><a class="menu-only" _href="${menu.onlyUrl}" data-title="${menu.name }" href="javascript:;"><i class="Hui-iconfont">${menu.css }</i> ${menu.name }</a></dt>
	      	  </c:if>
		    </dl>
			</c:forEach>
		</c:forEach>
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="/welcome">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group">
			<a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a>
			<a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
		</div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="/welcome"></iframe>
		</div>
	</div>
</section>

<c:if test="${mustPsChange}">
	<script type="text/javascript">
		layer.open({
			type: 2,
			area: ['650px', '380px'],
			fix: true, //不固定
			maxmin: true,
			closeBtn: 0,
			shade:0.4,
			title: '请先初始化密码',
			content: 'pwd?id=${user.userId}&psInit=true'
		});
	</script>
</c:if>

</body>
</html>