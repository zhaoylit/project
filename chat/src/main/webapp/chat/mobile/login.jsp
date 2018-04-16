<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>众享互动IM系统</title>
<!-- 引入toast样式 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/chat/mobile/css/toast.css">
<!-- 	引入toast插件 -->
<script type="text/javascript" src="<%=path %>/chat/mobile/js/toast.js"></script>
<script type="text/javascript" src="<%=path %>/chat/plugin/easy-ui/jquery.min.js"></script>
<style type="text/css">
*{
	margin:0;padding:0;
 }
 input{
 	font-size:15px;
 }
.u_1{
	width:90%;
	margin:10% 5% 0 5%;
	min-height:300px;
	border:0px solid blue;
}
.u_1_0{
	padding:10% 0 0 0;
	width: 100%;
	height:30px;
	text-align:center;
	font-weight:bold;
}
.u_1_1{
	width: 100%;
	height:50px;
}
.t_1{
	width:95%;
	height:30px;
	border:1px solid #ccc;
	line-height:30px;
	color:#ccc;
	border-radius:5px;
}
.t_1_p{
	padding:5px 0 5px 5px;
}
.t_2{
	height:30px;
	border:1px solid #ccc;
	line-height:30px;
	color:#ccc;
	border-radius:5px;
}
.b_1{
	width:95%;
	height:30px;
	padding:6px;
	line-height:30px;
	color:white;
	border:0px;
	background-color:blue;
	border-radius:5px;
	font-weight:bold;
	text-align:center;
	cursor:pointer;
}
.b_2{
	width:150px;
	height:40px;
	line-height:40px;
	border:1px solid blue;
	text-align:center;
	cursor:pointer;
	margin:20px auto;
	color:blue;
	
}
.d_l_1{
	width:48%;
	float:left;
	color:#ccc;
	font-size:12px;
}
.d_r_1{
	width:48%;
	float:right;
	color:#ccc;
	text-align:right;
	font-size:12px;
}
</style>
</head>
<body>
	<form id="loginForm" action="" method="post">
		<div class="u_1">
			 <div class="u_1_0">
			 	用户登录
			 </div>
			 <div class="u_1_1">
		 	 	<input  class="t_1 t_1_p" type="text" name="account" title="请输入用户名" value="请输入用户名"/>
			 </div>
			 <div class="u_1_1">
		 	 	<input class="t_1 t_1_p" type="text" name="password" title="请输入密码" value="请输入密码"/>
			 </div>
			 <div class="u_1_1">
			 	<div class="t_1 t_1_p" style="border:0px;padding:0px;">
			 		<input class="t_2 t_1_p" style="width:65%;" type="text" name="code" title="请输入验证码" value="请输入验证码"/>
			 	</div>
			 	<div class="t_1 t_1_p" style="border:0px;margin-top:-35px;border:0px;">
			 		<img id="codeShow" src="<%=path %>/code" style="cursor:pointer;width:30%;float:right;height:40px;border:0px;"/>
			 	</div>
			 	 
			 </div>
			 <div class="u_1_1">
				<div class="b_1" id="loginButton">登录</div>
			 </div>
			 <!-- <div class="u_1_1">
			 	<div class="t_1" style="border:0px;">
			 		<a href="javascript:void();" style="color:#ccc;font-size:12px;">登录遇到问题</a>
			 	</div>
			 	<div class="t_1" style="border:0px;margin-top:-30px;text-align:right;">
			 		<a href="javascript:void();" style="color:#ccc;font-size:12px;">其他方式登录</a>
			 	</div>
			 </div> -->
			 <div class="u_1_1" style="text-align:center;">
				<div class="b_2" id="registerButton">注册</div>
			 </div>
		</div> 
	</form>
	<script type="text/javascript">
	$(function(){
	})
	$(".t_1,.t_2").focus(function(){
		if($(this).val() == "" || $(this).val() == $(this).attr("title")){
			$(this).val("");
		}
		$(this).css({"color":"black"});
		if($(this).attr("name") == "password"){
			$(this).attr("type","password");
		}
	});
	$(".t_1,.t_2").blur(function(){
		//如果文本框的输入值等于默认值 ，清空输入框的值
		if($(this).val() == "" || $(this).val() == $(this).attr("title")){
			$(this).val($(this).attr("title"));
			$(this).attr("type","text");
		}else{
			if($(this).attr("name") == "password"){
				$(this).attr("type","password");
			}
		}
		$(this).css({"color":"#ccc"});
	});
	//点击登录触发
	$("#loginButton").click(function(){
		var account = $("input[name='account']").val() == $("input[name='account']").attr("title") ? "" : $("input[name='account']").val();
		var password = $("input[name='password']").val() == $("input[name='password']").attr("title") ? "" : $("input[name='password']").val();
		var code = $("input[name='code']").val() == $("input[name='code']").attr("title") ? "" : $("input[name='code']").val();
		if(account == "" || password == "" || code == ""){
			drawToast("表单不完整！");
			return ;
		}
		//ajax异步提交  
		$.ajax({
			data : {
				account : account,
				password : password,
				code : code
			},
			type : "post",
			dataType : 'JSON',
			url : "<%=path%>/login",
			beforeSend: function () {
				//加载图标
			},
			error : function(data) {
				drawToast("网络错误，请重试！");
			},
			success : function(data) {
				if(data.result =="0"){
					drawToast(data.message);
					changeCode($("#codeShow"));
				}else{
					//登录成功，跳转到首页
					window.location.href="<%=path%>/group?method=getList";
					
				}
			}
		});
	});
	//点击注册按钮触发
	$("#registerButton").click(function(){
		//跳转到注册页面
		window.location.href="<%=path%>/chat/mobile/register.jsp";
	});
	//点击验证码触发
	$("#codeShow").click(function(){
		changeCode($(this));
	});
	function changeCode(obj){
		//后台请求新的验证码
		var timestamp = new Date().getTime();
		obj.attr('src',obj.attr('src') + '?' +timestamp);
	}
	</script>
</body>
</html>