<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>系统设置</title>
<script type="text/javascript" src="<%=path %>/chat/js/jquery-1.9.1.min.js"></script>
</head>
<body>
	<div style="width:100%;">
		 <div style="width:100%;height:30px;font-weight:bold;">
		 	 添加群组
		 </div>
		 <div style="width:100%;height:30px;font-weight:bold;">
		 	 <input type="text"  id="groupName"/>
		 	 <input type="button" value="保存" onclick="saveGroup();"/>
		 </div>
		 <div style="width:100%;height:30px;font-weight:bold;">
		 	 设置群成员
		 </div>
		 <div style="width:100%;min-height:25px;">
		 	<select style="width:200px;height:25px;" id="groupSelect">
		 		<option value="0">---请选择---</option>
				<c:forEach items="${groupList }" var="group">
					 <option value="${group.id }">${group.groupName }</option>
				</c:forEach>
			</select>
			<div id="userList" style="width:100%;min-height:30px;">
			</div>
		 </div>
		 
		 <div style="width:100%;height:30px;font-weight:bold;">
		 	 给用户生成随机密码
		 </div>
		 <div style="width:100%;min-height:25px;">
		 	<select style="width:200px;height:25px;" id="userSelect">
		 		<option value="0">---请选择---</option>
				<c:forEach items="${userList }" var="user">
					 <option value="${user.id }">${user.nickName }(${user.account })</option>
				</c:forEach>
			</select>
			<div id="userList" style="width:100%;min-height:30px;">
			<input id="userUpdateButton" type="button" value="确认生成密码" onclick="updateUserPWD()"/>
			</div>
		 </div>
	</div>
	
	<div id="YWaitDialog"
		style="background-color: #FFFFFF; 
		z-index: 99999;
		position: absolute; 
		left: 50%; 
		margin-left: -138px; 
    	display: none;    
		top: 51%;">
		<p style="">
			<img style="width:130px;height:21px"
				src="<%=basePath%>/chat/image/abcd.gif" /><a>正在修改密码并发送至该账户邮箱中，请稍后.</a>
		</p>
		<a></a>
	</div>
	<script type="text/javascript">
	function updateUserPWD(){
		var userId=$("#userSelect").val();
// 		alert(userId);
		if(userId=="0"){
			alert("请正确选择用户！")
			return;
		}
		$.ajax({
			url:"<%=path%>/UserUpdatePWD",
			method:"post",
			data:{
				userId:userId
			},
			success:function(data){
				$("#userUpdateButton").attr("disabled", false); 
				$('#YWaitDialog').hide();
				alert("该用户生成的新密码为："+data[0].PWD);
			},
			error:function(data){
				$("#userUpdateButton").attr("disabled", false); 
				$('#YWaitDialog').hide();
				alert("网络错误，请重试！");
			},
			beforeSend: function () {
				$("#userUpdateButton").attr("disabled", true); 
				//加载图标
				$('#YWaitDialog').show();
				                    },
			dataType:"JSON"
		})
	}
	
	$("#groupSelect").change(function(){
		var groupId = $(this).val();
		$.ajax({
			method:"post",
			url:"<%=path%>/system?method=getAllUserByGroupId&groupId=" + groupId,
			data:{
				
			},
			success:function(data){
				$("#userList").html("");
				$.each(data,function(index,item){
					var str = "";
					if(item.isJoin == 1){
						str += 'checked=checked';
					}
					$("#userList").append('<input style="margin:10px 0 10px 10px;" class="group_select_user" type="checkbox" '+str+' value="'+item.id+'"/>' + item.nickName);
				});
				//追加操作按钮
				$("#userList").append('<br/><input type="button" value="保存" onclick="saveGroupUser()"/>');
			},
			error:function(data){
				alert("网络错误，请重试！");
			},
			dataType:"JSON"
		});
	});
	function saveGroupUser(){
		var groupId = $("#groupSelect").val();
		var chk_value =[];
		$('input[class="group_select_user"]:checked').each(function(){
			chk_value.push($(this).val());
		}); 
		var userIds = chk_value.join(",");
		//保存结果
		$.ajax({
			method:"post",
			url:"<%=path%>/system?method=saveGroupUser&groupId="+groupId+"&userIds="+userIds,
			data:{
			},
			success:function(data){
				if(data.result == 1){
					alert("操作成功，请刷新页面后查看");
				}else{
					alert(data.message);
				}
			},
			error:function(data){
				alert("网络错误，请重试！");
			},
			dataType:"JSON"
		});
	}
	function saveGroup(){
		var groupName = $("#groupName").val();
		if(groupName == ""){
			alert("请填写群组名称！");
			return;
		}
		$.ajax({
			method:"post",
			url:"<%=path%>/system?method=saveGroup&groupName="+groupName,
			data:{
			},
			success:function(data){
				if(data.result == 1){
					alert("操作成功，请刷新页面后查看");
				}else{
					alert(data.message);
				}
			},
			error:function(data){
				alert("网络错误，请重试！");
			},
			dataType:"JSON"
		});
	}
	</script>
</body>
</html>