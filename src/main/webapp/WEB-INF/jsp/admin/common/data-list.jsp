<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	<c:if test="${param.hideAll != 'true' && param.hideTitle != 'true'}">
		<jsp:include page="../part/data-title.jsp"/>
	</c:if>
	<div class="pd-20">
		<div class="">
			<c:if test="${param.hideAll != 'true'}">
				<jsp:include page="../part/data-list-search.jsp"/>
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l" id="a_warp">
						<c:forEach items="${pageButtons}" var="pageButton">
							<c:if test="${pageButton.name != '详情'}">
								<a href="${pageButton.href}" class="btn ${pageButton.color} radius">
									<i class="Hui-iconfont">${pageButton.imgChar}</i> ${pageButton.name}
								</a>
							</c:if>
						</c:forEach>
				    </span>
				    
				    <span class="r" style="padding-top: 3px; margin-right: 8px; margin-left: 9px;" id="downLoad">
				    	<i class="Hui-iconfont">&#xe640;</i>
				    </span>
				    
					<c:if test="${!empty orderByColumns}">
						<li class="dropDown dropDown_hover r" style="padding-top: 5px;">
					    	<a href="#" class="dropDown_A">
									<c:if test="${empty param.orderByName}">
										排序
									</c:if>
									<c:if test="${!empty param.orderByName}">
										${param.orderByName }
									</c:if>
								<i class="Hui-iconfont"></i></a>
							<ul class="dropDown-menu radius box-shadow dropDown-desc" style="left: auto; right: -5px;">
								<li><a href="javascript:orderBySub('', '排序');">默认顺序</a></li>
								<c:forEach items="${orderByColumns}" var="col">
									<c:if test="${col.orderBy==0 || col.orderBy==1}">
										<li><a href="javascript:orderBySub('${col.column }', '${col.name }正序');">${col.name }正序</a></li>
									</c:if>
									<c:if test="${col.orderBy==0 || col.orderBy==2}">
										<li><a href="javascript:orderBySub('${col.column } desc', '${col.name }倒序');">${col.name }倒序</a></li>
									</c:if>
								</c:forEach>
							</ul>
					    </li>
					</c:if>
				    
				    <span class="r" style="margin-top: 5px; margin-right: 10px;">
				   		<select name="DataTables_Table_0_length" id="pageSizeSe" aria-controls="DataTables_Table_0" class="select defalut">
				   			<option value="${DEF_PAGESIZE}">${DEF_PAGESIZE}条</option>
				   			<option value="50">50条</option>
				   			<option value="100">100条</option>
				   			<option value="500">500条</option>
				   			<option value="1000">1000条</option>
				   		</select>
				    </span>
					
				    <span class="r" style="padding-top: 5px; margin-right: 10px">共有数据：<strong>${page.totalCount}</strong> 条 </span>
				</div>
			</c:if>
			
			<div class="<c:if test="${param.hideAll != 'true'}">mt-20</c:if>">
				<table
					class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
					<tr class="text-c">
						<c:if test="${fn:length(pageButtons) > 1 || (fn:length(pageButtons) == 1 && pageButtons[0].name != '详情')}">
							<th width="30"><input type="checkbox"></th>
						</c:if>
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
									<c:if test="${fn:length(pageButtons) > 1 || (fn:length(pageButtons) == 1 && pageButtons[0].name != '详情')}">
										<td><input type="checkbox" value="${data.primaryKey }" name="id", id="checkbox_${data.primaryKey }"></td>
									</c:if>
									<c:forEach items="${tableColumns}" var="tabCol" varStatus="status">
										<td>
											<c:if test="${status.first && oneUrl != ''}">
												<a style="text-decoration: underline;" href="javascript:msunCommonToEdit('${baseUrl }${oneUrl }', '${data.primaryKey }', '2');" class="">
											</c:if>
											<html:getValue vo="${data }" attribute="${tabCol.column }" videoAttribute = "${tabCol.videoPath }"
												 selectCode="${tabCol.selectCode }" maxLength = "${tabCol.maxLength }" isImg = "${tabCol.img }"></html:getValue>
											<c:if test="${status.first && oneUrl != ''}">
												</a>
											</c:if> 
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
				</table>
				<c:if test="${param.hideAll != 'true'}">
					<jsp:include page="../part/page.jsp"/>
				</c:if>
			</div>
		</div>
	</div>
	
	<div class="carrousel" style="display: none;"> <span class="entypo-cancel">×</span> 
		<div class="wrapper"> <img src="" alt="" class="img_video"/> <video  autoplay="autoplay" style="padding-top: 20px; max-height: calc(100% - 40px);" class="img_video" src="" controls="controls"> 您的浏览器不支持 video 标签。</video> </div>
	</div>
</body>

<script type="text/javascript" src="/admin/public/js/data-list.js"></script>
<c:if test="${jsListFile != null && jsListFile != '' }">
	<script type="text/javascript" src="${jsListFile }"></script>
</c:if>

</html>