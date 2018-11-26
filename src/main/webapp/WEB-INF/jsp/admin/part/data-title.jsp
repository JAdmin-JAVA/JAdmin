<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> <a data-title="我的桌面" onclick="parent.Hui_admin_tab(this)" _href="/welcome">首页</a> <span class="c-gray en">&gt;</span>
	<c:forEach items="${pageName}" var="name" varStatus="stat">
		<c:if test="${stat.index == 1}">
			<a data-title="${name }" onclick="parent.Hui_admin_tab(this)" _href="${lastUrl }">${name }</a>
		</c:if>
		<c:if test="${stat.index != 1}">
			${name } 
		</c:if>
		<c:if test="${!stat.last}">
			<span class="c-gray en">&gt;</span> 
		</c:if>
	</c:forEach>
	
	<c:if test="${'true' != param.nrTab}">
		<c:choose>
		    <c:when test="${fn:length(pageName) <= 2}">
				<a class="btn btn-success radius r" style="line-height: 1.4em; margin-top: 3px; height: 33px;"  
					href="javascript:location.replace(location.href);" title="刷新">
					<i class="Hui-iconfont">&#xe68f;</i>
				</a>
		    </c:when>
		    <c:otherwise>
		    	<c:if test="${'true' != param.hideReturn}">
					<a class="btn btn-success radius r mr-20" style="line-height: 1.4em; margin-top: 3px; height: 33px;" 
					   href="${lastUrl }" title="返回">返回</a>
		    	</c:if>
		    </c:otherwise>
		</c:choose>
	</c:if>
		
</nav>