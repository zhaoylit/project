<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath() ;
	String patha = request.getContextPath() + "/javascript";
    String ctx = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String email=(String)request.getAttribute("email");
	String userName=(String)request.getAttribute("userName");
	String passWord=(String)request.getAttribute("passWord");
	String headUrl=(String)request.getAttribute("headUrl");
	String randomNumberString=(String)request.getAttribute("randomNumberString");
	
%>
<title>邮箱确认</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="<%=path %>/chat/plugin/bootstrap/bootstrap.min.css">
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery.cookie.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=path %>/chat/plugin/bootstrap/bootstrap.min.js"></script>
<style type="text/css">
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
	
<script type="text/javascript">
$(function(){
	$(".t_1").focus(function(){
		if($(this).val() == "" || $(this).val() == $(this).attr("title")){
			$(this).val("");
		}
		$(this).css({"color":"black"});
		if($(this).attr("name") == "password"){
			$(this).attr("type","password");
		}
	});
	$(".t_1").blur(function(){
		//如果文本框的输入值等于默认值 ，清空输入框的值
		if($(this).val() == "" || $(this).val() == $(this).attr("title")){
			$(this).val($(this).attr("title"));
		}else{
			if($(this).attr("name") == "password"){
				$(this).attr("type","password");
			}
		}
		$(this).css({"color":"#ccc"});
	});
})

	function enrooll(){
		var secretKey=$("#secretKey").val();
// 		alert(secretKey);
		if(secretKey== '<%=randomNumberString%>'){
				$.ajax({
					data : {
							email:'<%=email%>',
							userName:'<%=userName%>',
							passWord:'<%=passWord%>',
							headUrl:'<%=headUrl%>'
						},
						type : "POST",
						dataType : 'json',
						url : "<%=path%>/enrooll",
						success : function(data) {
							if(data.msg=="1"){
								window.location.href="<%=path%>/chat/mobile/loading.jsp"
							}else{
								alert("注册失败，请检查网络！")
							}
						}
				});
			}else{
				alert("密令错误，请重新输入！")
			}
	}
</script>	
<body>
		<form style="margin-top:33%" id="loginForm" action="" method="post">
		<div class="u_1">
			 <div class="u_1_0">
			 </div>
			 <div class="u_1_1">
		 	 	<input class="t_1 t_1_p"  type="text"  id="secretKey"  name="secretKey"  title="请在此处填写对应的注册密令" value="请在此处填写对应的注册密令"/>
			 </div>
			 <div class="u_1_1">
				<div class="b_1" style=" line-height:22px" id="loginButton" onclick="enrooll()">确认注册</div>
			 </div>
		</div> 
	</form>
    
    </body>