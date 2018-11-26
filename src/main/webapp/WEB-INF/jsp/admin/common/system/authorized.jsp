<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../../part/head.jsp"/>
</head>
<body>
<jsp:include page="../../part/data-title.jsp"/>
<div class="pd-20">
	<form class="form form-horizontal validform">
		<div class="row cl" style="margin-top: 0px; display: block;">
			<label class="form-label col-5"><span class="c-red">${errorMsg }</span></label>
		</div>

	    <html:div name="设备ID" required="false">
			<input type="text" class="input-text" disabled="disabled" value="${vo.serId }">
		</html:div>

	    <html:div name="授权类型" required="false">
			<input type="text" class="input-text" disabled="disabled" value="${vo.proType }">
		</html:div>

	    <html:div name="公司" required="false">
			<input type="text" class="input-text" disabled="disabled" value="${vo.customer }">
		</html:div>

	    <html:div name="授权开始时间" required="false">
			<input type="text" class="input-text" disabled="disabled" value="${vo.beginTime }">
		</html:div>

	    <html:div name="授权期限" required="false">
			<input type="text" class="input-text" disabled="disabled" value="${vo.validPeriod }天">
		</html:div>

		<div class="row cl" style="display: block;">
			<div class="col-9 col-offset-4 col-sm-offset-3" style="padding-left: 0px;">
				<a class="btn btn-primary radius" href="/authorize/up">重新授权</a>
			</div>
		</div>
	</form>
  </div>
</body>