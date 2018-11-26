<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/admin/part/head.jsp"/>
<title>${pageName[1] }</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/admin/part/data-title.jsp"/>
	<div class="pd-10">
		<jsp:include page="../part/data-list-search.jsp"/>
		<div class="cl pd-5 bg-1 bk-gray mt-10" style="padding-bottom: 0px;">
			<span class="l">
				<c:forEach items="${pageButtons}" var="pageButton">
					<a href="${pageButton.href}" class="btn ${pageButton.color} radius" style="padding: 4px 4px; margin-bottom: 5px; height: 32px;">
						<i class="Hui-iconfont">${pageButton.imgChar}</i> ${pageButton.name}
					</a>
				</c:forEach>
		    </span>
			
		    <%-- <span class="r" style="padding-top: 3px; margin-right: 10px">共有数据：<strong>${page.totalCount}</strong> 条 </span> --%>
		</div>
		<div class="mt-10" style="overflow-x: scroll;">
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
							<tr class="text-c">
								<td><input type="checkbox" value="${data.primaryKey }" name="id"></td>
								<c:forEach items="${tableColumns}" var="tabCol">
									<td style="min-width: 60px;">
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
			<jsp:include page="/WEB-INF/jsp/admin/part/page.jsp"/>
		</div>
	</div>
</body>
<script type="text/javascript">
function orderBySub(or, orName){
	$("#orderBy").val(or);
	$("#orderByName").val(orName);
	$("#searchFrom").submit();
}

$(document).ready(function(){
	$("#pageSizeSe option[value=${page.pageSize}]").attr("selected", true);
	$('#pageSizeSe').change(function(){
		$("#pageSize").val($(this).children('option:selected').val());
		$("#searchFrom").submit();
	});
	$('#downLoad').click(function(){
		$("#isDownLoad").val("1");
		$("#pageSize").val("20000");
		$("#searchFrom").submit();
		$("#isDownLoad").val("");
		$("#pageSize").val("");
	});
});
</script>
</html>