<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp" />
</head>
<body>
	<jsp:include page="../part/data-title.jsp"/>
	<div class="pd-20">
		<form action="${baseUrl }/edit" method="post" class="form form-horizontal validform"  enctype="multipart/form-data"  id="form-add">
			<div class="row cl">
				<label class="form-label col-5"><span class="c-red">${errorMsg}</span></label>
			</div>
			<jsp:include page="../part/data-edit-colunm.jsp" />
			<input type="hidden" name ="common_check_status" id= "common_check_status">
			<div class="row cl">
				<div class="col-9 col-offset-3">
					<input class="btn btn-primary radius" type="button"
						onclick="formSub('pass');"
						value="&nbsp;&nbsp;通过&nbsp;&nbsp;">
					<input class="btn btn-primary radius" type="button"
						onclick="formSub('refuse');"
						value="&nbsp;&nbsp;拒绝&nbsp;&nbsp;">				
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	function formSub(substr){
   		 $('#common_check_status').val(substr);
		 $('#form-add').submit();  
	}
</script>
</html>