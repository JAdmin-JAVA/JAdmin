<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>${title}</title>
        <script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
        <script type="text/javascript" src="/lib/layer/3.1.0/layer.js"></script>
        <script type="text/javascript" src="/admin/public/js/H-ui.js"></script>
        <script type="text/javascript" src="/admin/public/js/H-ui.admin.js"></script>
    </head>
    <body>
        <div>
        </div>
    </body>
    <script type="text/javascript">
        window.onload=function(){
        	parent.layer.alert('${messge}',function(){
        		${url}
        	});
        }
    </script>
</html>