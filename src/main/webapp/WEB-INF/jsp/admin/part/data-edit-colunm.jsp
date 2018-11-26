<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<input type="hidden" name="primaryKey" value="${data.primaryKey}" />
<input id="baseWhere" name="baseWhere" type="hidden" value="${param.baseWhere }" />
<input id="hideReturn" name="hideReturn" type="hidden" value="${param.hideReturn }" />
<input id="hideTitle" name="hideTitle" type="hidden" value="${param.hideTitle }" />

<c:forEach items="${hiddenInputs}" var="hiddInput">
	<input type="hidden" name="${hiddInput}" value="<html:getValue vo="${data }" attribute="${hiddInput}" />" />
</c:forEach>

<c:forEach items="${formColumns}" var="formCol">
	<html:div name="${formCol.name}" required="${formCol.requiredValue}" widthScale="${formCol.widthScale }">
		<c:if test="${formCol.type=='input'}">
			<c:choose>
				<c:when test="${formCol.ajaxurl==''}">
					<input type="text" class="input-text" id="${formCol.column}"
						value="<html:getValue vo="${data }" attribute="${formCol.column }" encode="${formCol.encode }" selectCode="${formCol.selectCode }"/>"
						name="${formCol.column}" ${formCol.disabled}
						<c:if test="${!empty formCol.datatype}">datatype="${formCol.datatype}"</c:if>
						placeholder="${formCol.lable }" nullmsg="${formCol.nullmsg}">
					<c:if test="${formCol.edit!='true'}">
					</c:if>
				</c:when>
				<c:otherwise>
					<input type="text" class="input-text" id="${formCol.column}"
						value="<html:getValue vo="${data }" attribute="${formCol.column }" encode="${formCol.encode }" selectCode="${formCol.selectCode }"/>"
						name="${formCol.column}" ${formCol.disabled}
						<c:if test="${!empty formCol.datatype}">datatype="${formCol.datatype}"</c:if>
						ajaxurl="${formCol.ajaxurl } " placeholder="${formCol.lable }"
						nullmsg="${formCol.nullmsg}">
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${formCol.type=='xy'}">
			<!--引用百度地图API-->
			<script type="text/javascript" src="http://map.qq.com/api/js?v=2.exp"></script>
			<!--百度地图容器-->
			<div>
				<input id="keyword" style="width: 200px; float: left;" class="input-text" type="text" placeholder="请输入关键词"> 
				<input id="region" style="width: 150px; float: left; margin: 0px 10px;" class="input-text" type="text" placeholder="请输入城市"> 
				<input type="button" class="btn btn-primary radius" value="搜索" onclick="searchKeyword()">
			</div>
			<div style="width: 120%; height: 400px; border: #ccc solid 1px; margin-top: 10px;" id="map_canvas"></div>
			<!-- 引入地图jsp -->
			<input id="map_x_col" type="hidden" value="${formCol.x }" />
			<input id="map_y_col" type="hidden" value="${formCol.y }" />
			<input id="map_x" type="hidden" value="<html:getValue vo="${data }" attribute="${formCol.x }"/>" />
			<input id="map_y" type="hidden" value="<html:getValue vo="${data }" attribute="${formCol.y }"/>" />
			<script type="text/javascript" src="/admin/public/js/qq-map.js"></script>
			<input type="hidden" id="${formCol.x }" name="${formCol.x }" value="<html:getValue vo="${data }" attribute="${formCol.x }"/>" />
			<input type="hidden" id="${formCol.y }" name="${formCol.y }" value="<html:getValue vo="${data }" attribute="${formCol.y }"/>" />
		</c:if>
		<c:if test="${formCol.type=='select'}">
			<c:if test="${formCol.selectStyle=='tree'}">
				<div id="${formCol.column}" class="select-box inline treeSelect" _value='<html:getTreeJson code="${formCol.selectCode}"></html:getTreeJson>'>
						<span>请选择</span>
					</div>
					<input name="${formCol.column}" class="treeSelect-input" value="<html:getValue vo="${data }" attribute="${formCol.column }"/>"
						   datatype="${formCol.datatype}" nullmsg="${formCol.nullmsg}"/>
			</c:if>
			<c:if test="${formCol.selectStyle!='tree'}">
				<html:select code="${formCol.selectCode}" datatype="${formCol.datatype}"
					id="${formCol.column}" nullmsg="${formCol.nullmsg}" style="select"
					name="${formCol.column}" value="" vo="${data }" selectStyle = "${formCol.selectStyle}"
					attribute="${formCol.column }"/>
			</c:if>
		</c:if>
		
		<c:if test="${formCol.type=='speedInput'}">
			<input type="text" class="input-text" id="${formCol.column}" style="width: calc(100% - 105px);"
				value="<html:getValue vo="${data }" attribute="${formCol.column }" encode="${formCol.encode }" selectCode="${formCol.selectCode }"/>"
				name="${formCol.column}" ${formCol.disabled}
				<c:if test="${!empty formCol.datatype}">datatype="${formCol.datatype}"</c:if>
			    placeholder="${formCol.lable }" nullmsg="${formCol.nullmsg}">
			    <c:if test="${formCol.edit!='true'}">
					<input type="hidden" name="${formCol.column }"
						value="<html:getValue vo="${data }" attribute="${formCol.column }" encode="${formCol.encode }" selectCode="${formCol.selectCode }"/>" />
				</c:if>
				<span class="select_min">
					<html:select code="${formCol.speedSelectCode}" id="${formCol.column}_speed"
						style="select" 
						value="" vo="${data }" attribute="${formCol.column }" />
				</span>
				
			<script type="text/javascript">
				$("#${formCol.column}_speed").change(function(){
					var id = $(this).attr("id").replace('_speed', '');
					$("#" + id).val($(this).find("option:selected").text());
				});
			</script>
		</c:if>
		
		<c:if test="${formCol.type=='textarea'}">
			<textarea name="${formCol.column}" cols="" rows="" class="textarea"
				${formCol.disabled} id="${formCol.column}"
				onKeyUp="textarealength(this,${formCol.length })"
				maxlength="${formCol.length }"><html:getValue
					vo="${data }" attribute="${formCol.column }" /></textarea>
			<p class="textarea-numberbar">
				<em class="textarea-length"> <html:getValue vo="${data }"
						attribute="${formCol.column }" getLength="true" />
				</em>/${formCol.length }
			</p>
		</c:if>
		<c:if test="${formCol.type=='date'}">
			<html:date name="${formCol.column}" value="" maxDate="${formCol.maxDate}" datatype="${formCol.datatype}"
				dateFmt="${formCol.dateFmt}" vo="${data }" id="${formCol.column}"
				attribute="${formCol.column }"></html:date>
		</c:if>

		<c:if test="${formCol.type=='upFile'}">
			<!-- 选择图片 -->
			<span class="btn-upload form-group"> <input
				class="input-text upload-url" type="text" name="${formCol.column}"
				id="${formCol.column}" readonly="" nullmsg="请选择图片！"
				value="<html:getValue vo="${data }" attribute="${formCol.column }" encode="${formCol.encode }" selectCode="${formCol.selectCode }"/>"
				style="width: 200px"> <a href="javascript:void();"
				class="btn btn-primary upload-btn"><i class="Hui-iconfont"></i>
					浏览文件</a> <input type="file" name="${formCol.column}File"
				id="${formCol.column}File" class="input-file"> <span>${formCol.lable }</span>
			</span>
			<!-- 预览与隐藏图片 -->
			<c:if test="${data.primaryKey!='' && data.primaryKey!=null}">
				<script type="text/javascript">
							function yulan(){
								if($("#imgDiv").css("display")=="none"){
									$("#imgDiv").css("display","block");
									$("#yulan").html("隐藏");
								}else{
									$("#imgDiv").css("display","none");
									$("#yulan").html("预览");
								}
							}
						</script>
				<a href="javascript:yulan();" id="yulan">预览</a>
				<br />
				<div id="imgDiv" style="display: none; margin-top: 10px;">
					<img src="<html:getValue vo="${data }" attribute="${formCol.column }" 
						encode="${formCol.encode }" selectCode="${formCol.selectCode }"/>" height="150" >
				</div>
			</c:if>
		</c:if>
		<c:if test="${formCol.type=='editor'}">
			<script charset="utf-8" src="/lib/kindeditor/kindeditor.js"></script>
			<script charset="utf-8">
				KE.show({
					id : "${formCol.column}",
					imageUploadJson : '/kindeditorUpLoadPic',  
		    		allowFileManager : true,
		    		afterBlur: function(id){//同步数据到textarea
			    			KE.sync(id);
			    		}
					});
			</script>
			<textarea name="${formCol.column}" cols="" rows="" class="textarea" ${formCol.disabled}  id="${formCol.column}" style="height:300px;">
				<html:getValue vo="${data }" attribute="${formCol.column }" />
			</textarea>
		</c:if>
		<c:if test="${formCol.type=='table'}">
			<input name="${formCol.idColunm }TsId" type="hidden" value="${ts }" />
			<iframe style="max-height: 300px; min-height: 200px;" scrolling="yes"
				frameborder="0" id="${formCol.column }Frame" width="100%"
				src="${formCol.tableUrl }/getAll?id=<html:getValue vo="${data }" initValue="${ts }" attribute="primaryKey"/>&inEdit=true&fid=${formCol.column }Frame&tableHiddleColumn=${formCol.tableHiddleColumn }"></iframe>
		</c:if>
	</html:div>
</c:forEach>

