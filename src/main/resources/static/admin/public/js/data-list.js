function orderBySub(or, orName) {
	$("#orderBy").val(or);
	$("#orderByName").val(orName);
	$("#searchFrom").submit();
}

$(document).ready(function() {
	var i = /*[[${page.pageSize}]]*/ "1";
	$("#pageSizeSe option[value=" + i + "]").attr("selected", true);
	$('#pageSizeSe').change(function() {
		$("#pageSize").val($(this).children('option:selected').val());
		$("#searchFrom").submit();
	});
	$('#downLoad').click(function() {
		downLoad();
	});
});

function downLoad() {
	$("#isDownLoad").val("1");
	$("#pageSize").val("20000");
	$("#searchFrom").submit();
	$("#isDownLoad").val("");
	$("#pageSize").val("");
	$("#exportIds").val("");
}

/*批量导出*/
function toExport(url) {
	var ids = '';
	$("input[name='id']:checked").each(function(i) {
		ids += this.value + ",";
	});
	if (ids == '') {
		layer.msg('至少选择一行!', {
			icon : 5,
			time : 1000
		});
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	$("#exportIds").val(ids);
	downLoad();
}
$('.table-sort').dataTable({
	paging : false,
	searching : false,
	info : false,
	stateSave : false,
	"order" : [],
	columnDefs : [ {
		"orderable" : false,
		"targets" : [ 0 ]
	} // 不参与排序的列
	]
});
//图片画廊插件
$(function() {
	String.prototype.startWith = function(str) {
		if (str == null || str == "" || this.length == 0
				|| str.length > this.length)
			return false;
		if (this.substr(0, str.length) == str)
			return true;
		else
			return false;
		return true;
	}
	$(".imgA").mouseover(function() {
		var src = $(this).attr("src");
		if (src.startWith('/images/')) {
			return;
		}
		var Y = this.getBoundingClientRect().top + $(this)[0].scrollTop;
		var ibY = document.body.clientHeight / 2;
		var imge = $(this).parent().find(".hImg");
		var heigth = imge.height();
		if (Y > ibY) {
			imge.css('top', '-' + heigth + 'px');
		} else {
			imge.css('top', '10px');
		}
		imge.show();
	});
	$(".imgA").mouseout(function() {
		$(this).parent().find(".hImg").hide();
	});

	var carrousel = $(".carrousel");

	$(".imgA").click(function(e) {
		var src = $(this).attr("src");
		var video = $(this).attr("videoPath");
		var type = (video == '' ? 'img' : 'video');
		if (type == 'video') {
			src = video;
		}
		carrousel.find('.img_video').hide();
		carrousel.find(type).show();
		carrousel.find(type).attr("src", src);
		carrousel.fadeIn(200);
	});

	carrousel.find(".close").click(function(e) {
		carrousel.attr("src", '');
		carrousel.fadeOut(200);
	});

	$(".carrousel").not(".img_video").click(function(e) {
		carrousel.attr("src", '');
		carrousel.fadeOut(200);
	});

});