<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp" />
</head>
<body>
	<c:if test="${param.hideTitle != 'true'}">
		<jsp:include page="../part/data-title.jsp"/>
	</c:if>
	<div class="pd-20" style="padding-top: 0px;">
		<div class="form form-horizontal validform" id="form-add">
			<div class="row cl">
				<label class="form-label col-5"><span class="c-red">${errorMsg}</span></label>
			</div>
			<jsp:include page="../part/data-edit-colunm.jsp" />
		</div>
	</div>
</body>

<script type="text/javascript">
$(function(){
	$(".col-4").hide();
	$("input, select, textarea").removeAttr('datatype');
	$("input, textarea").attr('readonly','readonly');
	$("select").attr("disabled","disabled");
});
<c:if test="${'true' == param.closeNowFrame}">
var fE = $("#${fn:replace(baseUrl, "/", "")}Frame", window.parent.document);
fE.attr("src", fE.attr("src"));
closeNowFrame();
</c:if>
</script>
<c:if test="${jsEditFile != null && jsEditFile != '' }">
	<script type="text/javascript" src="${jsEditFile }"></script> 
</c:if>
</html>