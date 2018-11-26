<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp"/>
<title>${pageName[1] }</title>
<script type="text/javascript">
	var zNodes = ${msunTreeData};
	var setting = {
		data: {
			key: {
				title:"t"
			},
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode, clickFlag){
				$("#msun-tree-iframe").attr('src', '${url}?msun_tree_id=' + treeNode.${msunTreeJsonKey} +
						'&msun_tree_level=' + treeNode.level + '&msun_tree_pId=' + treeNode.pId + '&hideTitle=true');
			}
		}
	};
	
	$(document).ready(function(){
		var zTree = $.fn.zTree.init($("#msun-data-tree"), setting, zNodes);
        zTree.expandAll(true);
        var node = zTree.getNodeByParam('${msunTreeJsonKey}', '${msunTreeData_firstId}');// 默认选择第一个节点
        zTree.selectNode(node);//选择点  
        zTree.setting.callback.onClick(null, zTree.setting.treeId, node);//调用事件 
	});

	// 自适应ifram的高度
	function setIframeHeight(iframe) {
		if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
				var height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
				var minHeight = document.documentElement.clientHeight - 40 - 15;
				if(minHeight > height){
					height = minHeight;
				}
				iframe.height = height;
				$('#msun-data-tree').css('height', height);
				$('.treeArrow').css('height', height + 12);
			}
		}
	};
	
</script>
<script type="text/javascript" src="/admin/public/js/data-tree-drag.js"></script>
</head>
<body>
	<c:if test="${param.hideAll != 'true' && param.hideTitle != 'true'}">
		<jsp:include page="../part/data-title.jsp"/>
	</c:if>
	<div class="pd-5">
		<ul id="msun-data-tree" class="msun-ztree ztree"></ul>
		<div class="msun-page-right-wrap">
			<iframe id="msun-tree-iframe" src="" onload="setIframeHeight(this)"></iframe>
		</div>
	</div>
	<div class="treeArrow" id="treeArrow">
		<span class="pngfix" onClick="treeHide(this)">
			<i class="icon Hui-iconfont Hui-iconfont-arrow2-right"></i>
		</span>
	</div>
	<div class="layui-layer-shade" id="tree-drag-wrap" style="display: none;"></div>
</body>
</html>