<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath() ;
	String patha = request.getContextPath() + "/javascript";
    String ctx = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<META http-equiv="refresh" CONTENT="3;URL=<%=path %>/chat/login.jsp" > 
<link rel="stylesheet" href="<%=path %>/chat/plugin/bootstrap/bootstrap.min.css">
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery.cookie.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=path %>/chat/plugin/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript">
	function enrooll(){
		window.location.href="<%=path%>/chat/mobile/login.jsp"
	}
</script>
<title>众享互动IM系统</title>
</head>
<body style="background-color:#808080">
  <!-- 注册窗口 -->
        <div style="margin-top:30%" id="topDiv" class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>	
                <div class="modal-title">
                    <h1 class="text-center">注册成功</h1>
                </div>
                <div class="modal-body" >
                    <div class="form-group" action="">
                    		<div class="form-group">
                                <label for=""><h3>3秒后自动跳转回登陆页面...</h3></label>
                            </div>
                            <div class="text-right">
                                <button class="btn btn-primary"  onclick="enrooll()">点击立即跳转</button>
                            </div>
                    </div>
                </div>
            </div>
    </div>
</body>
</html>