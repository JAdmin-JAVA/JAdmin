<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/HTML" prefix="html"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../part/head.jsp"/>

<style type="text/css">
.Huifold .item{ position:relative}
.Huifold .item h4{margin:0;font-weight:bold;position:relative;border-top: 1px solid #fff;font-size:15px;line-height:22px;padding:7px 10px;background-color:#eee;cursor:pointer;padding-right:30px}
.Huifold .item h4 b{position:absolute;display: block; cursor:pointer;right:10px;top:7px;width:16px;height:16px; text-align:center; color:#666}
.Huifold .item .info{display:none;padding:10px}
</style>

<script type="text/javascript">
jQuery.Huifolda = function(obj,obj_c,speed,obj_type,Event){
	$(obj).bind(Event,function(){
		if($(this).next().is(":visible")){		
			$(this).next().slideUp(speed).end().removeClass("selectedt");
			$(this).find("b").html("+");
		}else{//折叠状态   需要判断 变状态为已读1 站内信重新加载 -1直到0 或者重新加载
			if(obj_type == 3){
				$(this).next().slideDown(speed).end().addClass("selectedt");
				$(this).css("font-weight","normal");
				$(this).find("b").html("-");
			}else{
				$(obj_c).slideUp(speed);
				$(obj).removeClass("selectedt");
				$(obj).find("b").html("+");
				$(this).next().slideDown(speed).end().addClass("selectedt");
				$(this).css("font-weight","normal");
				$(this).find("b").html("-");
			}
			
		    var inboxIdVal=$(this).find(":input").val();
			$.post("toModifyInbox",{inboxId:inboxIdVal},function(data){
				//alert(data);
			},'json');
			
		}
	});
};
	
$(function(){
	$.Huifolda("#Huifold1 .item h4","#Huifold1 .item .info","fast",1,"click"); /*5个参数顺序不可打乱，分别是：相应区,隐藏显示的内容,速度,类型,事件*/
	});
</script>
</head>
<body>
<div class="pd-20">
 <c:choose>
     <c:when test="${messageCount==0}">
		<div style="margin-top:82px;text-align: center;">
			<B><font size="4">目前没有未读消息!</font></B>
		</div> 
     </c:when>
     <c:otherwise>
        <ul id="Huifold1" class="Huifold">
		 	<c:forEach items="${messages}" var="message">
		 	<hr>
  			<li class="item">
   				 <h4>${message.messageTitle}&nbsp;<input type="hidden" id="${message.inboxId}" value="${message.inboxId}"/><b>+</b></h4>
    			 <div class="info">${message.messageContent}</div>
  			</li>
  			</c:forEach>
		</ul>
     </c:otherwise>
  </c:choose>
		 
</div>
</body>