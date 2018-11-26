<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<script>
	function getPage(pageNo){
		form.pageNo.value=pageNo;
		form.submit();
	}
	function goPage(pageNo){
		var page = $("#goInput").val();
		if(page == ''){
			return;
		}
		getPage(page);
	}
</script>
<title>管理</title>

<div id="pageNav" class="pageNav">
	<a href="javascript:getPage(1)">首页</a>
	<c:if test="${page.pageNo>1 }">
		<a href="javascript:getPage(${page.pageNo-1 })">上一页</a>
	</c:if>
	<c:forEach begin="${page.pageFirst }" end="${page.pageEnd}" var="i">
		<a href="javascript:getPage(${i })"
			<c:if test="${page.pageNo==i }">style="background-color: yellow;"</c:if>>${i
			}</a>
	</c:forEach>
	<c:if test="${page.pageNo<page.totalPages }">
		<a href="javascript:getPage(${page.pageNo+1 })">下一页</a>
	</c:if>
	<a href="javascript:getPage(${page.totalPages })">尾页</a>
	<input type="text" class="input-text" id="goInput" style="width:35px; height: 24px; margin-top: -8px;">
	<button type="button" onclick="goPage()" class="btn btn-success" style="height: 24px; margin: -8px 0px 0px 8px; padding: 2px 8px;">go</button>
</div>