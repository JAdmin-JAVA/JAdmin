<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../../part/head.jsp"/>
<style>
	.Huifold .item h4{ background-color: #ddd;}
	.info{ border: 1px solid #ddd; padding: 0px !important; margin-bottom: 10px; }
	.permission-list > dt { padding: 5px 25px;}
	.permission-list > dd > dl{ padding: 5px 15px;}
	.permission-list{ border: none;}
	.permission-list > dd > dl > dt{ min-width: 100px; width: auto; margin-right: 10px;}
</style>
</head>
<body>
	<c:if test="${param.hideAll != 'true' && param.hideTitle != 'true'}">
		<jsp:include page="../../part/data-title.jsp"/>
	</c:if>
	<div class="pd-20" style="padding-top: 0px;">
		<form action="${baseUrl }/edit" method="post" class="form form-horizontal" enctype="multipart/form-data" id="form-add">
			<div class="row cl">
				<label class="form-label col-5"><span class="c-red">${errorMsg}</span></label>
			</div>
			<jsp:include page="../../part/data-edit-colunm.jsp"/>
			<div class="row cl editDiv ed_100 ed_100_left">
				<label class="form-label col-xs-4 col-sm-3-msun"><span class="c-red">*</span>网站权限菜单：</label>
				<!-- checkboxStart -->
				<div class = "formControls col-xs-5 col-sm-7" style="padding-left: 0px;">
					<div class="">
					
					<ul id="Huifold1" class="Huifold">
					  <c:forEach items="${pageMenuList}" var="upMeun">
					  	<li class="item">
					  	  <c:if test="${!empty upMeun.name}">
						    <h4>${upMeun.name}<b>+</b></h4>
				      	  </c:if>
						  <div class="info" style="display: block;">
						  	<c:forEach items="${upMeun.menus}" var="meun">
								<dl class="permission-list">
									<dt>
										<label>
											<input type="checkbox" 
												<c:if test="${hasButton != null && hasMenu.contains(meun.fullName)}">
													checked="checked" 
												</c:if>
											>
											${meun.name}
										</label>
									</dt>
									<dd>
									  <c:forEach items="${meun.pages}" var="pages">
										<dl class="cl permission-list2">
											<dt>
												<label class="">
													<input type="checkbox" 
														<c:if test="${hasPage != null && hasPage.contains(pages.fullName) }">
															checked="checked" 
														</c:if>
													>
													${pages.name}
												</label>
											</dt>
											<dd style="margin-left: 110px;">
											<!-- 每个页面都有一个刷新的按钮 -->
											<label class="">
													<input type="checkbox" value="${upMeun.name};${meun.name};${pages.name};刷新" name="buttonValues" 
														<c:if test="${hasPage != null && hasPage.contains(pages.fullName) }">
															checked="checked" 
														</c:if>
													>
													刷新
											</label>
											<c:forEach items="${pages.buttons}" var="button">
												<label class="">
													<input type="checkbox" value="${upMeun.name};${meun.name};${pages.name};${button.name}" name="buttonValues" 
														<c:if test="${hasButton != null && hasButton.contains(button.fullName) }">
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
						</li>
					</c:forEach>
				  </ul>
				</div>
		   	 </div>
		</div>
		<!-- end -->
			
			<div class="row cl editDiv ed_100">
				<div style="margin-left: 12.5%; margin-bottom: 20px;">
					<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</div>
	
<script type="text/javascript">
$(function(){
	$.Huifold("#Huifold1 .item h4","#Huifold1 .item .info","fast",3,"click"); /*5个参数顺序不可打乱，分别是：相应区,隐藏显示的内容,速度,类型,事件*/
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
</script> 
</body>
</html>