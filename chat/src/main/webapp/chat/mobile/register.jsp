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
<title>用户注册</title>
<!-- 引入toast样式 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/chat/mobile/css/toast.css">
<!-- 	引入toast插件 -->
<script type="text/javascript" src="<%=path %>/chat/mobile/js/toast.js"></script>
<script type="text/javascript" src="<%=path %>/chat/plugin/easy-ui/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/chat/js/ajaxfileupload.js"></script>
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
	height:300px;
	border:0px solid blue;
}
.u_1_0{
	padding:5% 0 0 0;
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
	height:35px;
	border:1px solid #ccc;
	line-height:35px;
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
	<form id="registerForm" action="" method="post">
		<div class="u_1">
			 <div  style="width:100%;height:30px;">
			 	<img id="backButton" src="<%=path %>/chat/image/back.png" style="cursor:pointer;" title="返回到登录页面"/>
			 </div>
			 <div class="u_1_0">
			 	用户注册
			 </div>
			 <div class="u_1_1">
		 	 	<input  class="t_1 t_1_p" type="text" name="account" title="请输入用户名" value="请输入用户名"/>
			 </div>
			 <div class="u_1_1">
		 	 	<input class="t_1 t_1_p" type="text" name="password" title="请输入密码" value="请输入密码"/>
			 </div>
			 <div class="u_1_1">
		 	 	<input  class="t_1 t_1_p" type="text" name="nickName" title="请输入昵称" value="请输入昵称"/>
			 </div>
			 <div class="u_1_1" style="cursor:pointer;background:url(<%=path %>/chat/image/upload_photo.png) no-repeat center left;" onclick="javascript:$('#file').click();">
			 	 <img id="photoShow" src="" style="width:45px;height:45px;margin-left:50px;display:none;"/>
			 </div>
			 <div class="u_1_1" style="display:none;">
		 	 	<input  class="t_1 t_1_p" type="file" name="file"  id="file" title="请选择头像" value="请选择头像" onchange="file1Upload()" />
		 	 	<input  class="t_1 t_1_p" type="hidden" name="file"  id="filehidden" />
			 </div>
			  <div class="u_1_1">
			 	<div class="t_1 t_1_p" style="border:0px;padding:0px;">
			 		<input class="t_2 t_1_p" style="width:65%;" type="text" name="emailCode" title="请输入验证码" value="请输入验证码"/>
			 	</div>
			 	<div class="t_1 t_1_p" style="border:0px;margin-top:-35px;border:0px;">
			 		<div id="getCode" style="cursor:pointer;width:30%;color:white;float:right;height:45px;text-align:center;line-height:45px;background-color:blue;border-radius:5px;">
			 			获取
			 		</div>
			 		<div  id="getCode1" style="display:none;cursor:pointer;width:30%;color:white;float:right;height:45px;text-align:center;line-height:45px;background-color:#ccc;border-radius:5px;">
			 			获取
			 		</div>
			 	</div>
			 	 
			 </div>
			 <div class="u_1_1">
				 <div class="b_1" id="registerButton">确定</div>
			 	<div class="b_1" id="registerButtonGrey" style="z-index:99999;background-color:#ccc;margin-top:-42px;display: none;"></div>
			 </div>
		</div> 
	</form>
	<script type="text/javascript" src="<%=path %>/chat/mobile/js/toast.js"></script>
	<script type="text/javascript">
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
	//点击注册触发
	$("#registerButton").click(function(){
		
		var email = $("input[name='account']").val();
		var passWord = $("input[name='password']").val();
		var userName = $("input[name='nickName']").val();
		var savePath=$("#filehidden").val();
		var secretKey=$("input[name='emailCode']").val();
		var emailregular=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/; 
		if(!emailregular.test(email)){
			drawToast("用户名格式应为邮箱格式，请确认！");
			return;
		}
		if(email == "" || passWord == "" ||  userName == "" || savePath == "" || secretKey == ""){
			drawToast("表单不完整！");
			return ;
		}
		<%-- alert("${randomNumberString}");
		if(secretKey != "<%= request.getSession().getAttribute("randomNumberString")%>"){
			alert("验证码不正确！");
			return ;
		} --%>
		$("#registerButtonGrey").show();
		$.ajax({
			data : {
					email:email,
					userName:userName,
					passWord:passWord,
					headUrl:savePath,
					secretKey : secretKey
				},
				type : "POST",
				dataType : 'json',
				url : "<%=path%>/enrooll",
				success : function(data) {
					if(data.result=="1"){
						window.location.href="<%=path%>"
					}else{
						drawToast(data.msg)
						$("#registerButtonGrey").hide();
					}
				},error:function (){
					drawToast("网络错误，请重试！");
					$("#registerButtonGrey").hide();
				}
		});
		//window.location.href="<%=path%>/MobileEmailEnrollSend?email="+email+"&userName="+userName+"&passWord="+passWord+"&savePath="+savePath;
	});
	   function file1Upload(){
		 console.log("sadas");
	   	$.ajaxFileUpload({
	           url: '<%=path%>/resource?method=upload&fileType=image', 
	           type: 'get',
	           async: 'false',
	           data : {
	           },
	           secureuri: false, //一般设置为false
	           fileElementId: 'file', // 上传文件的id、name属性名
	           dataType: 'JSON', //返回值类;型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
	           success: function(data, status){  
//	        	   debugger;
	              	var obj = $(data).removeAttr("style").prop("outerHTML");
	               	data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
	               	var json = JSON.parse(data);
	        	   var savePath=json.savePath;
	        	   //放置到input隐藏域
	        	    $("#filehidden").val(savePath);
	        	   $("#photoShow").attr("src",json.url).show();
	           },	
	           error: function(data, status, e){ 
	        	   drawToast("头像上传失败");
	           }
	       }); 
	   }
	//点击注册按钮触发
	$("#backButton").click(function(){
		//跳转到登录页面
		window.location.href="<%=path%>/chat/mobile/login.jsp";
	});
	
	var timer = null;
	//获取验证码
	$("#getCode").click(function(){
		var email = $("input[name='account']").val().replace($("input[name='account']").attr("title"),"");
		if(email == ""){
			drawToast("请输入邮箱！");
			return;
		}
		$("#getCode").hide();
		$("#getCode1").html("获取中..");
		$("#getCode1").show();
		$.ajax({
				data : {
					email:email,
				},
				type : "POST",
				dataType : 'text',
				url : "<%=path%>/EmailEnrollSend?method=getEmailCode",
				success : function(data) {
					if(data == "1"){
						drawToast("验证码发送成功，请查看邮件！");
						setTimeout(function(){
							$("#getCode").show();
							$("#getCode1").html("获取");
							$("#getCode1").hide();
							clearInterval(timer);
						},60000);
						$("#getCode1").html("60秒后");
						var time = 59;
						timer = setInterval(function(){
							$("#getCode1").html(time--+"秒后");
						},1000);
					}else{
						drawToast("验证码发送失败！");
						$("#getCode").show();
						$("#getCode1").html("获取");
						$("#getCode1").hide();
					}
				},error:function(){
					drawToast("网络错误，请重试！");
					$("#getCode").show();
					$("#getCode1").html("获取");
					$("#getCode1").hide();
				}
		});
	});
	</script>
</body>
</html>