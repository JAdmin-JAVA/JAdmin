<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp"/>
</head>
<body>
	<div style="padding: 0px 0 10px 10px;">
		<form action="${baseUrl }/edit" method="post" class="form form-horizontal" id="form-add">
			<div class="row cl">
				<!-- checkboxStart -->
				<div class = "formControls col-12">
					<div class="">
						<c:forEach items="${dictkind.dictinfoLevels}" var="dictinfo">
							<dl class="permission-list">
								<dt>
									<label>
										<input type="checkbox" value="${dictinfo.code}"  
											<c:if test="${dictinfo.fullCode != null && hasDictkind.contains(dictinfo.fullCode)}">
												checked="checked" 
											</c:if>
										>
										${dictinfo.name} 
									</label>
								</dt>
								<dd>
								  <c:forEach items="${dictinfo.dictinfos}" var="dictinfo2">
									<dl class="cl permission-list2">
									<dt>
										<label class="">
											<input type="checkbox" value="${dictinfo2.code}"  
												<c:if test="${dictinfo2.fullCode != null && hasDictkind.contains(dictinfo2.fullCode) }">
													checked="checked" 
												</c:if>
											>
											${dictinfo2.name}
										</label>
									 </dt>
										<dd style="margin-left: 110px;">
										<dd style="margin-left:110px">
											<c:forEach items="${dictinfo2.dictinfos}" var="dictinfo3">
												<label class="">
													<input type="checkbox" value="${dictinfo3.code}"  
														<c:if test="${dictinfo3.fullCode != null && hasDictkind.contains(dictinfo3.fullCode) }">
															checked="checked" 
														</c:if>
													>
													${button.name}
												</label>
											</c:forEach>
										</dd>
									</dl>
								  </c:forEach>
								</dd>
							</dl>
						</c:forEach>
					</div>
		   	 </div>
		</div>
		<!-- end -->
			
			<div class="row cl">
				<div class="col-9 col-offset-3">
					<input class="btn btn-primary radius" type="button" onclick="setInputVla()" value="&nbsp;&nbsp;确定&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</div>
	
<script type="text/javascript">
$(function(){
	$(".permission-list dt input:checkbox").click(function(){
		$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
	});
	$(".permission-list2 dd input:checkbox").click(function(){
		var l =$(this).parent().parent().find("input:checked").length;
		var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
		if($(this).prop("checked")){
			$(this).closest("dl").find("dt input:checkbox").prop("checked",true);
			$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
		}
		else{
			if(l==0){
				$(this).closest("dl").find("dt input:checkbox").prop("checked",false);
			}
			if(l2==0){
				$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
			}
		}
	});
});

function setInputVla(){
	var values = "";
	$(".permission-list dt input:checkbox:checked").each(function(){
		var val = $(this).val();
		if(val != ''){
			values += val + ",";
		}
	});
	if(values != ''){
		values = values.substring(0, values.length - 1);
	}
	$("input[name='${param.name}']", parent.document).val(values);
	closeNowFrame();
}

</script> 
</body>
</html>