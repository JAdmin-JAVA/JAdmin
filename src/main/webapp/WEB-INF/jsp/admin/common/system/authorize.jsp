<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../../part/head.jsp"/>
</head>
<body>
<jsp:include page="../../part/data-title.jsp"/>
<div class="pd-20">
	<form action="/authorize/upDo" method="post" class="form form-horizontal validform" enctype="multipart/form-data" id="form-add">
			<div class="row cl" style="margin-top: 0px; display: block;">
				<label class="form-label col-5"><span class="c-red">${errorMsg }</span></label>
			</div>
	
		    <html:div name="设备ID" required="false">
				<input type="text" class="input-text" value="${vo.serId }">
			</html:div>
			
			 <div class="row cl editDiv" style="display: block;"><label class="form-label col-xs-4 col-sm-3">请选择授权文件：</label>
			 	<div class="formControls col-xs-5 col-sm-7" style="padding-left:0px;"> 
					<span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="restorePath" id="restorePath" readonly="" nullmsg="请选择升级包！" value="" style="width:200px">
						<a href="javascript:void();" class="btn btn-primary upload-btn"><i class="Hui-iconfont"></i> 浏览文件</a>
						<input type="file" name="restorePathFile" id="restorePathFile" class="input-file">
						<span></span>
					</span>
				</div>
			</div>

			<div class="row cl" style="display: block;">
				<div class="col-9 col-offset-4 col-sm-offset-3" style="padding-left: 0px;">
					<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;点击授权&nbsp;&nbsp;">
				</div>
			</div>
	</form>
  </div>
</body>