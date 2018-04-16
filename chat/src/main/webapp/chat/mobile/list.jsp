<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ page import="com.zkkj.chat.util.*,java.util.*,java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String return_path_z =  PropertiesConfig.getProperties("return_path_z");
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>MyChat</title>
<style type="text/css">
.*{
	margin:0px;
	padding:0px;
}
html{
	background: url(<%=path%>/chat/image/background-list.jpg) no-repeat;
	background-size: 100%;
	background-attachment:fixed;
}
img{
	position:absolute;
	margin:100% 0 0 10px;
	cursor:pointer;
}
.u_1{
	width:90%;
	margin:10% 5% 0 5%;
	min-height:300px;
	border:0px solid blue;
}
.u_1_1{
	width: 100%;
	height:40px;
	border:0px solid #ccc;
	line-height:40px;
	margin-top:10px;
	cursor:pointer;
	border-radius:5px;
	color:black;
	font-weight:bold;
	background:url('<%=path%>/chat/image/down.png') no-repeat top 0px right -15px;
	background-color:gray;
}
.u_1_2{
	width: 99%;
	min-height:100px;
	border:0px solid #ccc;
	margin-top:10px 0 10px 0;
	display:none;
	max-height:300px;
	overflow:scroll;
}
</style>
<script type="text/javascript" src="<%=path %>/chat/plugin/easy-ui/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".u_1_1").click(function(){
		if($(this).attr("flag") == "up"){
			$(this).css({"background":"url('<%=path%>/chat/image/up.png') no-repeat top 0px right","background-color":"#ccc"});
			$(this).attr("flag","down");
			$(this).next().slideDown(300);
		}else{
			$(this).css({"background":"url('<%=path%>/chat/image/down.png') no-repeat top 0px right -15px","background-color":"gray"});
			$(this).attr("flag","up");
			$(this).next().slideUp(300);
		}
	});
})
</script>
</head>
<body>
	<!-- 	退出登录 -->
	<img src="<%=path%>/chat/image/logout.png" onclick="javascript:window.location.href='<%=path %>/logout';"/>
	<div class="u_1">
		<div style="width:99%;height:80px;border:0px solid #ccc;text-align:center;line-height:100px;background:url(<%=path %>/chat/image/chat.png) no-repeat center center;">
		</div>
		<div class="u_1_1"  flag="up">
			<span style="margin:10px;">
				用户列表(${userList.size() })
			</span>
		</div>
		<div class="u_1_2">
			<c:forEach items="${userList }" var = "user">
				<div style="cursor :pointer;width:100%;height:40px;border:0px solid blue; margin:15px 0 0 0 ;line-height:30px; background:url(<%=return_path_z %>${user.headUrl }) no-repeat top left;background-size:40px 40px;" onclick ="javascript: window.location.href='<%=path %>/mobile?method=chatInit&type=1&fromUserId=${userSession.id}&toUserId=${user.id}';">
					<div style="width:80%;height:20px; border:0px solid blue;margin:-5px 0 0 45px;line-height:20px;">
						<c:if test="${user.onlineStatus == '1' }">
						 <font style="color:gray;">在线</font>
						</c:if>
						<c:if test="${user.onlineStatus != '1' }">
						 <font style="color:#ccc;">离线</font>
						</c:if>
					</div>
					<div style="margin-left:45px;height:20px;line-height:20px;" >
						${user.nickName }
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="u_1_1" flag="up">
			<span style="margin:10px;">
				群组列表(${groupList.size() })
			</span>
		</div>
		<div class="u_1_2">
			<c:forEach items="${groupList}" var = "group">
				<div style="cursor :pointer;width:100%;height:40px;border:0px solid blue; margin:15px 0 0 0 ;line-height:30px; background:url(<%=path %>/chat/image/group.png) no-repeat top left;background-size:40px 40x;" onclick ="javascript:window.location.href='<%=path %>/mobile?method=chatInit&type=2&fromUserId=${userSession.id}&groupId=${group.id }';">
					<div style="margin-left:50px;height:40px;line-height:50px;" >
						${group.groupName }(${group.userCount })
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>