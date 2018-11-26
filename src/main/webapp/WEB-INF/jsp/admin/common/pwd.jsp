<%@page import="com.jadmin.vo.entity.base.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp" />
<style type="text/css">
.col-sm-3-msun{
	width: 33.33333333%;
}
</style>
</head>
<body>
	<div class="pd-20">
		<form class="form form-horizontal validform">

			<html:div name="用户名" required="true">
				<input type="text" class="input-text" disabled="disabled"
					value="<%= ((UserVO) session.getAttribute("CUR_USER")).getName() %>">
			</html:div>

			<html:div name="当前密码" required="true">
				<input type="password" class="input-text" name="password"
					id="password" datatype="*6-18">
			</html:div>

			<html:div name="新密码" required="true">
				<input type="password" class="input-text" name="newPassword"
					id="newPassword"
					datatype="*6-18">
			</html:div>

			<html:div name="再次输入新密码" required="true">
				<input type="password" class="input-text" name="SecondPassword"
					datatype="*6-18"
					recheck="newPassword" errormsg="您两次输入的密码不一致！">
			</html:div>
			
			<div class="row cl editDiv ed_100">
				<div style="margin-left: 33.3%; margin-bottom: 20px;">
					<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"> 
					<c:if test="${!param.psInit }">
						<input class="btn btn-default radius ml-30" type="button" onclick="closeNowFrame()" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
					</c:if>
				</div>
			</div>

		</form>
	</div>
	<script type="text/javascript">
		// from表单校验成功后，会调用该方法
		function validOk(from) {
			var password = document.getElementById("password");
			var newPassword = document.getElementById("newPassword");
			$.post("toModifyPwd?password=" + password.value + "&newPassword="
					+ newPassword.value, function(result) {
				if (result == "success") {
					parent.layerOkShow("密码修改成功");
					closeNowFrame();
				} else {
					layerFailShow(result);
				}
			});
		}
	</script>
</body>