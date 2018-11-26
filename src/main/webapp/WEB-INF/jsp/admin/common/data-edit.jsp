<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp" />
</head>
<body>
	<c:if test="${param.hideTitle != 'true'}">
		<jsp:include page="../part/data-title.jsp"/>
	</c:if>
	<div class="pd-20" style="padding-top: 0px;">
		<form action="${baseUrl }/edit?nrTab=${param.nrTab}" method="post" 
			class="form form-horizontal validform" enctype="multipart/form-data" id="form-add">
			<div class="row cl" style="margin-top: 0px;">
				<label class="form-label col-5"><span class="c-red">${errorMsg}</span></label>
			</div>
			<jsp:include page="../part/data-edit-colunm.jsp" />
			<div class="row cl editDiv ed_100">
				<div style="margin-left: 12.5%; margin-bottom: 20px;">
					<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</div>
	<div id="msunSelectTree-wrap">
		<input type="text" class="input-text msun-tree-search">
		<ul id="msunSelectTree" class="ztree"></ul>
	</div>
			
	
</body>
<script type="text/javascript">
$(document).ready(function(){
	<c:forEach items="${selectShow}" var="data">
		hideAndShowComponents('${data.selectColumn}', $('#${data.selectColumn}').val());
	</c:forEach>

	<c:forEach items="${selectShow}" var="data">
		$("#${data.selectColumn}").change(function(){
	       hideAndShowComponents('${data.selectColumn}', $(this).val());
		});
	</c:forEach>
});

function hideAndShowComponents(selectId, value){
	<c:forEach items="${selectShow}" var="data">
	if(selectId == '${data.selectColumn}'){
	  <c:forEach items="${data.showColumn}" var="co">
	     $("#${co}").parent().parent().hide();
	     validform.ignore("#${co}");
	  </c:forEach>
	}
	</c:forEach>
	<c:forEach items="${selectShow}" var="data">
	if(selectId == '${data.selectColumn}' && value == '${data.value}'){
	  <c:forEach items="${data.showColumn}" var="co">
	     $("#${co}").parent().parent().show();
	     validform.unignore("#${co}");
	  </c:forEach>
	}
	</c:forEach>
}
<c:if test="${(errorMsg == null || errorMsg == '') && param.fromEdit == 'true' }">
	layerOkShow("操作成功！");
</c:if>
init_text_edit();
function l_col_a_click(){
	$(".l_col a").click(function(){
		//询问框
		var ele = $(this);
		var content = ele.html();
		layer.confirm('确认要删除“' + content + '”？', {
			btn: ['是','否'] //按钮
		}, function(){
			var parent = ele.parent();
			ele.remove();
			layer.closeAll();
			resetTextArea(parent);
		}, function(){
			
		});
	});
}

function init_text_edit(){
	$('.text_c_t textarea').each(function(){
		resetTextEdit($(this));
	});
}

// ele 为 textarea 元素
function resetTextEdit(ele){
	var words = ele.val().split(' ');
	var htmls = '';
	for (var i=0;i<words.length;i++){
		htmls += '<a href="#" title="' + words[i] + '">' + words[i] + '</a>';
	}
	ele.parent().parent().find('.l_col').html(htmls);
	l_col_a_click();
}

// parent 为 l_col 元素
function resetTextArea(parent){
	var tempkeys = '';
	parent.find('a').each(function(){
		tempkeys += $(this).html() + ' ';
	});
	if(tempkeys != ''){
		tempkeys = tempkeys.substring(0, tempkeys.length - 1);
	}
	parent.parent().parent().find('textarea').val(tempkeys);
}
// 添加按钮
$(".c_t_add").click(function(){
	var s_add_inp = $(this).parent().find('.s_add_inp');
	var content = s_add_inp.val().replace(' ', '');
	if(content == ''){
		return;
	}
	var ele = $(this).parent().parent().find('.l_col');
	var chong = false;
	ele.find('a').each(function(){
		if($(this).html() == content){
			layer.tips('请勿重复添加', $(this), {
				  tips: [1, '#3595CC'],
				  time: 4000
			});
			chong = true;
			return;
		}
	});
	if(!chong){
		ele.append('<a href="#" title="' + content + '">' + content + '</a>');
		resetTextArea(ele);
		l_col_a_click();
		s_add_inp.val('');
	}
});
// 文本模式
$(".c_t_text").click(function(){
	var ele = $(this).parent().parent().parent();
	ele.find('.c_t').hide();
	ele.find('.text_c_t').show();
});
// 编辑模式
$(".c_t_edit_type").click(function(){
	var ele = $(this).parent().parent().parent();
	ele.find('.c_t').hide();
	ele.find('.keys_c_t').show();
	resetTextEdit(ele.find('textarea'));
});
</script>
<c:if test="${jsEditFile != null && jsEditFile != '' }">
	<script type="text/javascript" src="${jsEditFile }"></script> 
</c:if>
</html>