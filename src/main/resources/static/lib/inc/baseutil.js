
/**
 * 基础工具
 **/
var baseutil = {
    getParams: function(){
        var query = location.search.substring(1);
        var result = new Array();
        if (query) {
            var arQuery = query.split("&");
            for (var i = 0; i < arQuery.length; i++) {
                var pos = arQuery[i].indexOf("=");
                var qName = arQuery[i].substring(0, pos);
                //qName = qName.toLowerCase();
                var qValue = arQuery[i].substring(pos + 1);
                result[qName] = qValue;
            }
        }
        return result;
    },
    getParamsX: function(){
        var location_url = top.location;
        location_url = location_url.toString();
        var idxq = location_url.indexOf("#");
        var result = new Array();
        if (idxq >= 0) {
            var params = location_url.substring(idxq + 1);
            var arQuery = params.split("&");
            for (var i = 0; i < arQuery.length; i++) {
                var pos = arQuery[i].indexOf("=");
                var qName = arQuery[i].substring(0, pos);
                var qValue = arQuery[i].substring(pos + 1);
                result[qName] = qValue;
            }
        }
        return result;
    },
    isEmpty: function(s){
        if (s == null || s == "") {
            return true;
        }
        return false;
    },
    isNull: function(s){
        if (typeof(s)==undefined || s == null || s == undefined || s == "" || s == "undefined" || s == "null") {
            return true;
        }
        return false;
    },
	converNullToValue : function(param, value){
		return this.isNull(param) ? value : param;
	},
	containsSpecialWord :function(str){
		var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\"]"); 
		return pattern.test(str);
	}
    
};



//上传组件
function tm_uploadify(btnid, btntxt, basepath, callback){
	$("#"+btnid).uploadify({  
		'buttonText' : btntxt,  
		'height' : 20,  
		'swf' : basepath + 'inc/js/uploadify/uploadify.swf',  
		'uploader' : basepath + 'common/upload.do',  
		'cancelImg' : basepath + 'inc/js/uploadify/uploadify-cancel.png', 
		'width' : 80,  
		'auto':true,  
		'fileTypeExts' : '*.gif; *.jpg; *.png',
		'fileObjName'   : 'file',  
		'fileSizeLimit' : '500KB',
		'queueSizeLimit' : 1,
		'onUploadSuccess' : function(file, data, response) {  
			var retdata = eval("("+data+")");
			//alert( retdata.code + '  ');  
			//alert( retdata.name + ' 上传成功！ ');  
			//$("#a_photo").val(retdata.name);
			callback(retdata);
		}  
	}); 
}

function tm_removePhoto(id){
	$(".tm_img_preview").hide();
	$("#"+id).val("");
}


function tm_showPic(imgurl){
	layer.open({
	  type: 1,
      title: false,
	  skin: 'layui-layer-rim',
	  area: '400px', //宽高
	  content: '<img src="'+imgurl+'" width="400" />'
	});
}


function tm_bindPasswordLevelChecker(id){
	$('#'+id).keyup(function() { 
		var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
		var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
		var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
	
		if (false == enoughRegex.test($(this).val())) { 
			$('#tm_level').removeClass('pw-weak'); 
			$('#tm_level').removeClass('pw-medium'); 
			$('#tm_level').removeClass('pw-strong'); 
			$('#tm_level').addClass(' pw-defule'); 
			 //密码小于六位的时候，密码强度图片都为灰色 
		} 
		else if (strongRegex.test($(this).val())) { 
			$('#tm_level').removeClass('pw-weak'); 
			$('#tm_level').removeClass('pw-medium'); 
			$('#tm_level').removeClass('pw-strong'); 
			$('#tm_level').addClass(' pw-strong'); 
			 //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强 
		} 
		else if (mediumRegex.test($(this).val())) { 
			$('#tm_level').removeClass('pw-weak'); 
			$('#tm_level').removeClass('pw-medium'); 
			$('#tm_level').removeClass('pw-strong'); 
			$('#tm_level').addClass(' pw-medium'); 
			 //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等 
		} 
		else { 
			$('#tm_level').removeClass('pw-weak'); 
			$('#tm_level').removeClass('pw-medium'); 
			$('#tm_level').removeClass('pw-strong'); 
			$('#tm_level').addClass('pw-weak'); 
			 //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的 
		} 
		//return true; 
	}); 
}


function tm_fn_page_to(evt, url, pageid){
	var e = evt || window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode==13){
		if(!/^[0-9]*$/.test(pageid)){ 
			pageid = 1;
		}
		var epagesize = $("#tm_pager_pagesize").val();
		window.location.href = url + "epage=" + pageid + "&epagesize="+epagesize; 
		return false;
	}
}


function tm_fn_pagesize(url, pagesize){
	window.location.href = url + "epage=1&epagesize=" + pagesize; 
}

function tm_fn_formatSeconds(value) { 
	var theTime = parseInt(value);// 秒 
	var theTime1 = 0;// 分 
	var theTime2 = 0;// 小时 
	// alert(theTime); 
	if(theTime > 60) { 
		theTime1 = parseInt(theTime/60); 
		theTime = parseInt(theTime%60); 
		// alert(theTime1+"-"+theTime); 
		if(theTime1 > 60) { 
			theTime2 = parseInt(theTime1/60); 
			theTime1 = parseInt(theTime1%60); 
		} 
	} 
	var the_second = parseInt(theTime) < 10? "0" + theTime : theTime;
	var the_minute = parseInt(theTime1) < 10? "0" + theTime1 : theTime1;
	var result = theTime2 + ":" + the_minute + ":" + the_second;
	return result; 
} 
