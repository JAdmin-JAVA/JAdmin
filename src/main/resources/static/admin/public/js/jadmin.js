var validform= null;
$(function(){
	$(".Wdate").parent('.formControls').css('width', '180px');
	$('.navbar-nav .dropDown .dropDown_A').click(function() {
		$('.Hui-aside .menu_dropdown dl').hide();
		$('.Hui-aside .menu_dropdown .msun_menu_' + $(this).attr('_id')).show();
		$(this).parent().parent().find('li').removeClass('current');
		$(this).parent().addClass('current');
	});
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	validform = $(".validform").Validform({
		// 可用的值有：1、2、3、4和function函数， 2=> 侧边提示，默认tiptype为1
		tiptype:function(msg, o, cssctl){
		    //msg：提示信息;
		    //o:{obj:*,type:*,curform:*},
		    //obj指向的是当前验证的表单元素（或表单对象，验证全部验证通过，提交表单时o.obj为该表单对象），
		    //type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, 
		    //curform为当前form对象;
		    //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			var ele = o.obj.parent().next();
			cssctl(ele, o.type);
			ele.html(msg);
			if(o.type == 3){
				o.obj.parent('.sod_select').addClass('Validform_error');
			}else{
				o.obj.parent('.sod_select').removeClass('Validform_error');
			}
		}, 
		label:".form-label",
		callback:function(form){
			if(typeof validOk === 'function' ){
				validOk();
			// 表单效验成功后的操作，默认为提交该表单
			}else{
				var index = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				form[0].submit();
			}
			return false;
		}
	});
});

//通过父页面 关闭当前的iframe
function closeNowFrame(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

//失败的提示框
function layerFailShow(content){
	layer.msg(content,{icon: 5, time : 5000});
}

function layerFailShow(content, time){
	layer.msg(content,{icon: 5, time : time});
}

// 成功的提示框
function layerOkShow(content){
	layer.msg(content,{icon: 6, time : 2000});
}
// 出来一个确定按钮，点击后，刷新页面
function layerOkFlush(content){
	layer.alert(content, {icon: 6}, '消息', function(){
		parent.location.reload();
	});
}

// 弹出多选的窗体
function dictinfoCheckbox(lable, name, code, width, height){
	var values = $("input[name='" + name + "']").val();
	layer_show(lable,'/dictinfoCheckbox?code=' + code + "&values=" + values + "&name=" + name, width, height);
}

// 统一处理url id 可以不传
function getUrl(url, id){
	url = addUrlPram(url, 'id', id);
	if($("#baseWhere").length != 0){
		url = addUrlPram(url, 'baseWhere', $("#baseWhere").val());
	}
	/*if($("#pageNo").length != 0){
		url = addUrlPram(url, 'pageNo', $("#pageNo").val());
	}*/
	return url;
}

// 在url后面拼接参数
function addUrlPram(url, key, value){
	if(typeof(value) == "undefined" || value == null || value == ""){
		return url;
	}
	if(url.indexOf("?") == -1){
		url = url + "?";
	}else{
		url = url + "&";
	}
	return url + key + "=" + value;
}

// 异步提交通用方法 此处处理是否需要提示
function ajaxSubmit(url, name, isConfirm){
	if(isConfirm){
		layer.confirm('确认要' + name + '么？', function() {
			comPost(url, name);
		});
	}
}

// post方法
function comPost(url, name){
	msunLayIndex = layer.load(1, {
	  shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	$.post(url, function(data) {
		layer.close(msunLayIndex);
		if (data == "success") {
			layerOkShow(name + '成功!');
			// 异步操作完成之后，这里统一刷新
			location.replace(location.href);
		} else {
			layerFailShow(data);
		}
	});
}

// 效验至少选择一个，如果合格返回选择的ids
function getMoreSlectIds(){
	var ids = '';
	$("input[name='id']:checked").each(function(i){
		ids += this.value + ",";
	});
	if(ids == ''){
		layerFailShow('至少选择一行!');
		return nul;
	}
	ids = ids.substring(0, ids.length - 1);
	return ids;
}

// 效验只选择一个，如果合格返回id
function getOneSelectId(){
	var ids = "";
	$("input[name='id']:checked").each(function(i){
		ids = this.value;
		if(i>0){
			layerFailShow('请选择一行!');
			ids = "false";
			return null;
		}
	});
	if(ids=="false"){
		return null;
	}
	if(ids == ''){
		layerFailShow('请选择一行!');
		return null;
	}
	return ids;
}
	
/**
 * list页面通用的button按钮
 * url：要跳转/弹出/ajax请求的url
 * name：
 * showType：1、将当前页面跳转到 指定url 2、将当期页面通过 layer弹出来 （默认） 3、ajax异步请求服务器，然后弹出处理结果
 * dataType：1、不选择任何数据 即可触发 （默认） 2、必须选择一个数据 才能触发 3、必须选择一个或多个数据 才能触发
 * ajaxConfirm：showType为3时 才有效，是否弹出是否提示框后，再进行ajax操作
 * layerHeight：showType为2时 才有效，layer的高度，为空时默认为当前页面高度-50
 */
function msunCommonButton(url, name, showType, dataType, ajaxConfirm, layerHeight){
	var id = null;
	if(dataType == '2'){
		id = getOneSelectId();
		if(id == null) return;
	}else if(dataType == '3'){
		id = getMoreSlectIds();
		if(id == null) return;
	}
	// 统一处理url
	url = getUrl(url, id);
	if(showType == '1'){
		window.location.href = url;
	}else if(showType == '2'){
		url = addUrlPram(url, 'hideTitle', 'true');
		if($('#msun-data-tree', parent.document).length == 0){
			layer_show(name, url, '', layerHeight);
		}else{
			parent.layer_show(name, url, '', layerHeight);
		}
	}else if(showType == '3'){
		ajaxSubmit(url, name, ajaxConfirm);
	}
}

// 列表页面，第一个元素的点击事件
function msunCommonToEdit(url, id, showType){
	// 统一处理url
	url = getUrl(url, id);
	if(showType == '1'){
		window.location.href = url;
	}else if(showType == '2'){
		url = addUrlPram(url, 'hideTitle', 'true');
		if($('#msun-data-tree', parent.document).length == 0){
			layer_show('编辑', url, '', '');
		}else{
			parent.layer_show('编辑', url, '', '');
		}
	}
}

/** 此处不知道怎么改
function inEdit_toButton(url, name){
	var id = getOneSelectId();
	if(id != null){
		url = url + "?id=" + id;
		var baseWhere = $("#baseWhere").val();
		if(baseWhere != null && baseWhere != ""){
			url = url + "&baseWhere=" + baseWhere;
		}
		var fid = $("#fid").val();
		parent.layer_show_etab(name, fid, url);
	}
}

function inEdit_toUrl(url, name){
	var baseWhere = $("#baseWhere").val();
	if(baseWhere != null && baseWhere != ""){
		url = url + "?baseWhere=" + baseWhere;
	}
	var fid = $("#fid").val();
	parent.layer_show_etab(name, fid, url);
}


function inEdit_toDel(url){
	toDel(url);
}
*/

//判断是否登录
function checkIsLogin(sessio){
	if(sessio==null){
		$("#noLogin").css('display','block');
		return false;
	}else{	
		return true;
	}
}

//JS前端中文字符转换
function toUtf8(str) {   
    var out, i, len, c;   
    out = "";   
    len = str.length;   
    for(i = 0; i < len; i++) { 
    	c = str.charCodeAt(i); 
    	if ((c >= 0x0001) && (c <= 0x007F)) { 
        	out += str.charAt(i);
    	} else if (c > 0x07FF) { 
        	out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));   
        	out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	} else {
        	out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	}
    }
    return out;   
}

//导出数据
function exportExcel(url){
	window.location.href = url;
}

// 处理编辑页面用到的tree
var msunNowTreeSelectEle;

function beforeClick(treeId, treeNode) {
	/* var zTree = $.fn.zTree.getZTreeObj("msunSelectTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true); */
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("msunSelectTree"),
	nodes = zTree.getCheckedNodes(true),
	v = "", ids = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		ids += nodes[i].rId + ",";
	}
	if (v.length > 0 ){
		v = v.substring(0, v.length-1);
	}else{
		v = "请选择";
	}
	if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
	msunNowTreeSelectEle.find('span').html(v);
	reTreeCss();
	var inputEle = msunNowTreeSelectEle.parent().find('.treeSelect-input');
	inputEle.attr('value', ids);
	inputEle.blur();
}

function showMenu(obj) {
	msunNowTreeSelectEle = obj;
	msunNowTreeSelectEle.parent().addClass('now-tree-select');
	reTreeCss();
	var _value = obj.attr('_value');
	var checkArgs = {enable: true, chkboxType: {"Y":"", "N":""}};
	var radioArgs = {enable: true, chkStyle: "radio", radioType: "all"};
	var setting = {
		check: '1' == _value.substring(0, 1) ? radioArgs : checkArgs,
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onCheck: onCheck
		}
	};
	var zNodes = eval('(' + _value.substring(1, _value.length) + ')');
	$.fn.zTree.init($("#msunSelectTree"), setting, zNodes);
	$("body").bind("mousedown", onBodyDown);
	
	var zTree = $.fn.zTree.getZTreeObj("msunSelectTree");
    zTree.expandAll(true);
    var values = obj.parent().find('.treeSelect-input').val().split(',');
    for (var i = 0; i < values.length; i++){
        var node = zTree.getNodeByParam('rId', values[i] + '');
        if(node != null){
	        zTree.checkNode(node, true, true);
        }
    }
}

function reTreeCss(){
	$('#msunSelectTree-wrap').css('margin-top', '-' + (msunNowTreeSelectEle.height() + 7) + 'px');
	$('#msunSelectTree-wrap').css('width', (msunNowTreeSelectEle.width() + 42) + 'px');
	$('.msun-tree-search').css('margin-top', (msunNowTreeSelectEle.height() + 7) + 'px');
	var offset = msunNowTreeSelectEle.offset();
	$("#msunSelectTree-wrap").css({left:offset.left + "px", top:offset.top + msunNowTreeSelectEle.outerHeight() + "px"}).slideDown("fast");
}

function hideMenu() {
	$("#msunSelectTree-wrap").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
	msunNowTreeSelectEle.parent().removeClass('now-tree-select');
}

function onBodyDown(event) {
	if (!(event.target.id == "msunSelectTree-wrap" || 
			$(event.target).parents("#msunSelectTree-wrap").length>0)
		) {
		hideMenu();
	}
}

$(document).ready(function(){
	$(".treeSelect").click(function(){
		showMenu($(this));
	});
	//输入框的值改变时触发
    $(".msun-tree-search").on("input", function(e){
        //获取input输入的值
    	var search = e.delegateTarget.value;
    	var zTree = $.fn.zTree.getZTreeObj("msunSelectTree");
	    //显示隐藏的节点
	    nodes = zTree.getNodesByParam("isHidden", true);
	    zTree.showNodes(nodes);
	    
	    var root = zTree.getNodeByParam("level", "0");
	    
	    var hiddenNodes = new Array();
	    
	    filterNodes(root, search, hiddenNodes);
	    zTree.hideNodes(hiddenNodes);
    });
    
	$(".treeSelect").each(function(){
		$(this).parent().css('width', 'auto');
		$(this).parent().css('max-width', '58.333333333333336%');
		var values = $(this).parent().find('.treeSelect-input').val().split(',');
		var _value = $(this).attr('_value');
		var json = eval('(' + _value.substring(1, _value.length) + ')');
		var lables = '';
		for(var i = 0; i < json.length; i++){
			var tv = json[i]['rId'];
			if(values.indexOf(tv) != -1){
				lables += json[i]['name'] + ',';
			}
		}
		if (lables.length > 0 ){
			lables = lables.substring(0, lables.length-1);
		}else{
			lables = "请选择";
		}
		$(this).find('span').html(lables);
	});
});

/**
 * 遍历树节点，将 
 * 1.自身不满足搜索条件 
 *     且 
 * 2.其子节点不包含有满足条件的节点
 * 的节点加入到filterResult中
 * 
 * @param node 查询的节点
 * @param inputStr 搜索条件
 * @param filterResult 过滤的结果集
 * @return 该节点是否满足条件
 */
function filterNodes(node, inputStr, filterResult){
    if(node != null){
        //自身是否符合搜索条件
        var selfMatch = node.name.indexOf(inputStr) > -1;
        //子节点是否有满足的条件的节点
        var childMatch = false;
        
        var children = node.children;
        if(children != undefined){
            for(index in children){
                childMatch = filterNodes(children[index], inputStr, filterResult) || childMatch;
            }
        }
        
        //自身不满足搜索条件 且其子节点不包含有满足条件的节点
        if(!selfMatch && !childMatch){
            filterResult.push(node);
        }
        
        return selfMatch || childMatch;
    }else{
        return true;
    }
}

$(document).ready(function(){
	//select美化
	$("select:not('.defalut')").selectOrDie();
});
