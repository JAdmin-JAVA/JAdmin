<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<c:if test="${!empty search2.searchColumns || searchMode.showDate}">
	<div class="text-c">
		<form name="form" id="searchFrom" action="${url }?id=${param.id }&baseWhere=${param.baseWhere}" method="get">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo }"> 
			<input id="exportIds" name="exportIds" type="hidden" value=""> 
			<input name="pageSize" id="pageSize" type="hidden" value="${page.pageSize }"> 
			<input id="baseWhere" name="baseWhere" type="hidden" value="${baseWhere }" /> 
			<input id="orderBy" name="orderBy" type="hidden" value="${param.orderBy }" />
			<input id="orderByName" name="orderByName" type="hidden" value="${param.orderByName }" />
			<input id="isDownLoad" name="isDownLoad" type="hidden" value="" /> 
			<input id="searchType" name="searchType" type="hidden" value="${searchMode.defType}" /> 
			<input id="hideTitle" name="hideTitle" type="hidden" value="${param.hideTitle }" />
			<input id="msun_tree_id" name="msun_tree_id" type="hidden" value="${param.msun_tree_id }" />
			<input id="msun_tree_level" name="msun_tree_level" type="hidden" value="${param.msun_tree_level }" />
			<input id="msun_tree_pId" name="msun_tree_pId" type="hidden" value="${param.msun_tree_pId }" />
			<span id="search1"> 
			<c:if test="${searchMode.showDate}">
				<input name="dateColumn" type="hidden" value="${searchMode.dateColumn}" />
				日期范围：
				<input type="text" onfocus="WdatePicker({ dateFmt:'${searchMode.dateFmt}'})"
					id="searchModeStartTime" name="searchModeStartTime" value="${param.searchModeStartTime}" 
					class="input-text Wdate" style="width: 105px;">
				-
				<input type="text" onfocus="WdatePicker({ dateFmt:'${searchMode.dateFmt}'})"
					id="searchModeEndTime" name="searchModeEndTime" value="${param.searchModeEndTime}" 
					class="input-text Wdate" style="width: 105px;">
				</c:if> 
				<c:if test="${!empty search2.searchColumns}">
					<c:forEach items="${search2.typeSearchColumns}" var="seaCol">
						<html:select lable="${seaCol.name}" code="${seaCol.selectCode}"
							name="${seaCol.column} 1" style="select" datatype=""
							value="${seaCol.value }"></html:select>
					</c:forEach>
					<input class="input-text" style="width: 0px; max-width: 600px"
						value="${param.inputSearchColumnNames }"
						title="输入${search2.inputSearchColumnNames }"
						id="inputSearchColumnNames" name="inputSearchColumnNames"
						placeholder="输入${search2.inputSearchColumnNames }" />
					<input name="inputSearchColumns" type="hidden" value="${search2.inputSearchColumns }" />
				</c:if>
			</span> 
			<span id="search2" style="display: none;"> 
				<c:forEach items="${search2.searchColumns}" var="seaCol">
					<c:if test="${seaCol.type=='input'}">
						${seaCol.name}
					   <span class="inline"> <select
							name="type.${seaCol.column}" id="type.${seaCol.column}"
							class="select">
								<option value="3" selected="selected">包含</option>
								<option value="0">等于</option>
								<!-- <option value="1">左=</option>
					    		<option value="2">右=</option>
					    		<option value="4">></option>
					    		<option value="5">>=</option>
					    		<option value="6"><</option>
					    		<option value="7"><=</option> -->
								<option value="8">不为空</option>
						</select>
						</span>
						<script type="text/javascript">
							$(document).ready(function() {
								$("[id='type.${seaCol.column}'] option[value='${seaCol.searchLike}']").attr("selected", true);
							});
						</script>
						<input class="input-text" style="width: 100px" name="${seaCol.column}" value="${seaCol.value }" />
					</c:if>
					<c:if test="${seaCol.type=='select'}">
						${seaCol.name}：
						<html:select code="${seaCol.selectCode}" name="${seaCol.column}"
							style="select" datatype="" value="${seaCol.value }"></html:select>
					</c:if>
				</c:forEach>
			</span>
			<c:if test="${searchColumns.size()!=0}">
				<button type="submit" class="btn btn-success">
					<i class="Hui-iconfont">&#xe665;</i> 搜索
				</button>
				<i id="search_change" style="cursor: pointer;" class="icon Hui-iconfont" title="展开详细搜索模式">&#xe6d7;</i>
			</c:if>
		</form>
		<script type="text/javascript">
			$(document).ready(function() {
				<c:if test="${param.searchType=='2' || searchMode.defType==2}">
					search_change($("#search_change"));
				</c:if>

				<c:if test="${param.searchType=='1' || searchMode.defType==1 || searchMode.showDate}">
					searchAn();
				</c:if>
				
				$("#searchFrom").submit(function(){
					form.pageNo.value=1;
					form.submit();
					return true;
				});

				$("#search_change").click(function() {
					search_change($(this));
				});

				function searchAn() {
					var width = $("#inputSearchColumnNames")
							.attr("placeholder").length * 14;
					if (width < 250) {
						width = 250;
					}
					$("#inputSearchColumnNames").animate({
						width : width + 'px'
					}, "hide");
				}

				function search_change(ele) {
					if (ele.attr("title") == '展开详细搜索模式') {
						ele.html("&#xe6d4;");
						ele.attr("title", "展开简单搜索模式");
						$("#searchType").val("2");
						$("#search1").hide();
						$("#search2").show(500);
					} else {
						ele.html("&#xe6d7;");
						ele.attr("title", "展开详细搜索模式");
						$("#searchType").val("1");
						$("#search2").hide();
						$("#search1").show(500);
					}
				}
			});
		</script>
	</div>
</c:if>
