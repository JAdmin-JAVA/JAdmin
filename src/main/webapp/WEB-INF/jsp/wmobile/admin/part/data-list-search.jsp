<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<div class="text-c">
	<form name="form" id="searchFrom" action="${url }?id=${param.id }&baseWhere=${param.baseWhere}" method="post" style="text-align: left;">
		<input name="pageNo" type="hidden" value=""> 
		<input name="pageSize" id="pageSize" type="hidden" value="${page.pageSize }">
		<input id="baseWhere" name="baseWhere" type="hidden" value="${baseWhere }"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${param.orderBy }"/>
		<input id="orderByName" name="orderByName" type="hidden" value="${param.orderByName }"/>
		<input id="isDownLoad" name="isDownLoad" type="hidden" value=""/>
		
		<select class="select" id="mobileColSel" style="width: auto; max-width: 33.3%; height: 31px; padding-bottom: 3px;">
			<c:forEach items="${searchColumns}" var="seaCol" varStatus="status">
				<option value="${status.index}">${seaCol.name}</option>
			</c:forEach>
    	</select>
		
		<c:forEach items="${searchColumns}" var="seaCol">
			<c:if test="${seaCol.type=='input'}">
				<input class="mobileInputHidden input-text" id="${seaCol.column}" name="${seaCol.column}" style="width:40%; display: none;" value="${seaCol.value}"/>
				<input name="type.${seaCol.column}" type="hidden" value="3"/>
			</c:if>
			<c:if test="${seaCol.type=='select'}">
				<span id="${seaCol.column}" class="mobileInputHidden" style="display: none;">
					<html:select code="${seaCol.selectCode}" name="${seaCol.column}" style="select" datatype="" value="${seaCol.value }"></html:select>
				</span>
			</c:if>
		</c:forEach>
		
		<c:if test="${searchColumns.size()!=0}">
			<button type="submit" class="btn btn-success" style="height: 31px;">
				<i class="Hui-iconfont">&#xe665;</i > 搜索
			</button>
		</c:if>
	</form>
	
	<script language="javascript">
	$(document).ready(function(){
		$(".mobileInputHidden:eq(0)").show();
		<c:forEach items="${searchColumns}" var="seaCol" varStatus="status">
			v = '${seaCol.value}';
			if(v != ''){
				$('#mobileColSel').val('${status.index}');
				hid(${status.index});
			}
		</c:forEach>
		$('#mobileColSel').change(function(){
			hid($(this).val());
		});
		function hid(index){
			$(".mobileInputHidden").hide();
			$(".mobileInputHidden:eq(" + index + ")").show();
		}
	}); 
	</script>
	
</div>