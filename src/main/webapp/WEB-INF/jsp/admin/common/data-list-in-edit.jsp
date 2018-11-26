<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<c:if test="${!empty refreshTime}">
	<meta http-equiv="refresh" content="${refreshTime }">
</c:if>
<jsp:include page="../part/head.jsp"/>
<title>${pageName[1] }</title>
</head>
<body>
	<div class="">
		<input type="hidden" id="fid" value="${param.fid}">
		<input type="hidden" id="baseWhere" value="${baseWhere}">
		<div class="cl pd-5 bg-1 bk-gray">
			<span class="l">
				<c:forEach items="${pageButtons}" var="pageButton">
					<a href="javascript:${pageButton.hrefInEdit}" class="btn ${pageButton.color} radius">
						<i class="Hui-iconfont">${pageButton.imgChar}</i> ${pageButton.name}
					</a>
				</c:forEach>
		    </span>
			
		    <span class="r" style="padding-top: 3px; margin-right: 10px">共有数据：<strong>${page.totalCount}</strong> 条 </span>
		</div>
		<div class="mt-5">
			<table
				class="table table-border table-bordered table-hover table-bg table-sort">
				<thead>
				<tr class="text-c">
					<th width="30"><input type="checkbox"></th>
					<c:forEach items="${tableColumns}" var="tabCol">
						<th>${tabCol.name }</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty page.result }">
						<tr class="text-c">
							<td colspan="100">${msg }</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${page.result}" var="data">
							<tr class="text-c text-data">
								<td><input type="checkbox" value="${data.primaryKey }" name="id"></td>
								<c:forEach items="${tableColumns}" var="tabCol">
									<td>
										<html:getValue vo="${data }" attribute="${tabCol.column }"
											 selectCode="${tabCol.selectCode }" maxLength = "${tabCol.maxLength }"></html:getValue>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
			</table>
			<%-- <jsp:include page="../part/page.jsp"/> --%>
		</div>
	</div>
	
<script type="text/javascript">
$(document).ready(function(){
	var hideIndex = '${param.tableHiddleColumn}';
	if(hideIndex ==''){
		return;
	}
	var hides = hideIndex.split(',');
	for (var i=0;i<hides.length;i++){
		$('.text-c th:eq(' + hides[i] + ')').hide();
	}
	$(".text-data").each(function(){
		for (var i=0;i<hides.length;i++){
			$(this).find('td').get(hides[i]).style.display = 'none';
		}
	});
});
</script>
</body>
</html>