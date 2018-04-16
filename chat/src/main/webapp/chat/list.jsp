<%@ page language="java" pageEncoding="UTF-8"%>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天列表</title>
</head>
<body>
	<div>
		<span>全部用户列表：</span>
		<c:forEach items="${userList }" var = "user">
			<div style="width:200px;padding-left:10px;height:30px;border:0px solid blue; margin:2px 0 0 0 ;line-height:30px;">
				<img src="${user.headUrl }" style="width:30px;height:30px;border-raidius:30px" title="${user.nickName }"/>
				<span style="font-size:12px;">${user.nickName }</span>
				<!-- 	type = 1 为单聊 -->
				<a href="<%=path %>/group?method=chatInit&type=1&fromUserId=${userSession.id}&toUserId=${user.id}" target="_blank">对话</a>
			</div>
		</c:forEach>
	</div>
	<!-- 	群组列表 -->
	<div>
		<span>群组列表：</span>
		<c:forEach items="${groupList}" var = "group">
			<div style="width:200px;height:30px; border : 0px solid blue; margin : 2px 0 0 0 ; line-height:30px;">
				${group.groupName }(${group.userCount })
				<c:if test="${group.isJoin == 1 }">
					<a href="<%=path %>/group?method=chatInit&type=2&fromUserId=${userSession.id}&groupId=${group.id }" target="_blank">聊天</a>
					<!-- 	type = 2 为群聊 -->
					<a href="<%=path %>/group?method=quitGroup&fromUserId=${userSession.id}&groupId=${group.id }">退出</a>
				</c:if>
				<c:if test="${group.isJoin == 0 }">
					<a href="<%=path %>/group?method=joinGroup&fromUserId=${userSession.id}&groupId=${group.id }">加入</a>
				</c:if>
			</div>
		</c:forEach>
	</div>
	<script type="text/javascript">
// 	退出群聊
	function quit(groupId,userId){
		alert(groupId + "---" + userId);
		//退出群聊
	}
	</script>
</body>
</html>