<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp"/>
</head>
<body>
<div class="pd-20">
	<form class="form form-horizontal validform" >
	
	    <html:div name="账号" required="true">
			<input type="text" class="input-text" disabled="disabled" value="${orgvo.account }">
		</html:div>
		
	    <html:div name="新密码" required="true">
			<input type="password" class="input-text" name="password" id="password" datatype="/((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,20}$/" errormsg="至少 8 位以上，包括三类字符（数字、字符、大小写字母等）" >
		</html:div>
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3 mt-20">
	         <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	         <input class="btn btn-default radius ml-30" type="button" onclick="closeNowFrame()" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
	      </div>
	    </div>
	    
	  </form>
  </div>
<script type="text/javascript">

// from表单校验成功后，会调用该方法
function validOk(from){
	var password = document.getElementById("password");
	$.post("/toResPwd?password=" + password.value + "&account=${orgvo.account}", function(result) {
		if(result == "success"){
			parent.layerOkShow("密码修改成功");
			closeNowFrame();
		}else{
			layerFailShow(result);
		}
	});
}
</script>
</body>