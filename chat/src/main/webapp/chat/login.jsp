<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath() ;
	String patha = request.getContextPath() + "/javascript";
    String ctx = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<title>欢迎登陆</title>
<link rel="stylesheet" href="<%=path %>/chat/plugin/bootstrap/bootstrap.min.css">
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/chat/js/ajaxfileupload.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=path %>/chat/plugin/bootstrap/bootstrap.min.js"></script>



<script type="text/javascript">
	var NikeNameFlag=false;
	var EmailFlag=false;
	var passWordFlag=false;
	var fileFlag=false;
	
	document.onkeydown = function(e) {
		var event = e || window.event;
		var code = event.keyCode || event.which || event.charCode;
		if (code == 13) {
			login();
		}
	}
	$(function() {
		$("#account").val($.cookie('account'));
		$("#password").val($.cookie('password'));
		$("input[name='login']").focus();
	});
	function cleardata() {
		$('#loginForm').form('clear');
	}
	
	function enrooll(){
		debugger;
		if(NikeNameFlag && EmailFlag && passWordFlag && fileFlag){
			var email=$("#email").val();
			var userName=$("#userName").val();
			var passWord=$("#passWord").val();
			var filehidden=$("#filehidden").val();
			var emailCode =$("#emailCode").val();
					$.ajax({
						data : {
								email:email,
								userName:userName,
								passWord:passWord,
								headUrl:filehidden,
								emailCode:emailCode
							},
							type : "POST",
							dataType : 'json',
							url : "<%=path%>/EnrollPC",
							success : function(data) {
								if(data=="1"){
									window.location.href="<%=path%>/chat/loading.jsp"
								}else if(data=="3"){
									alert("验证码错误，请重新输入！");
								}else{
									alert("注册失败，请检查网络！");
								}
							}
					});
		}else{
			alert("请确认表单信息是否完整！")
		}
	}
	
	
	var wait=100;
	function time(o){
		debugger;
		 if (wait==0) {
	            o.removeAttribute("disabled");    
	            o.innerHTML="重新获取验证码";
	            o.style.backgroundColor="#fe9900";
	            wait=100;
	        }else{
	            o.setAttribute("disabled", true);
	            o.innerHTML=wait+"秒后重新获取";
	            o.style.backgroundColor="#8f8b8b";
	            wait--;
	            setTimeout(function(){
	                time(o)
	            },1000)
	        }
	}
	
    function code(o){
    	if(NikeNameFlag && EmailFlag && passWordFlag && fileFlag){
			var email=$("#email").val();
			$.ajax({
				data:{
					email:email
				},
				type : "POST",
				dataType : 'json',
				url:"<%=path%>/EmailEnrollSend?method=getEmailCodePC",
				beforeSend: function () {
					time(o);
                },
				success:function(data){
					if(data=="1"){
					}else{
						alert("邮件发送失败，请确认网络状况！");
					}
				},
				error : function(data) {
					alert("网络状况差！请确认网络状况！");
				},
			})
		}else{
			alert("请正确填写表单信息，在进行邮件验证确认!");
		}
    }
	
	   //图片文件上传
	   function file1Upload(){
	   	$.ajaxFileUpload({
	           url: '<%=path%>/resource?method=upload&fileType=image', 
	           type: 'get',
	           async: 'false',
	           data : {
	           },
	           secureuri: false, //一般设置为false
	           fileElementId: 'file1', // 上传文件的id、name属性名
	           dataType: 'JSON', //返回值类;型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
	           success: function(data, status){  
	              	var obj = $(data).removeAttr("style").prop("outerHTML");
	               	data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
	               	var json = JSON.parse(data);
	        	   var savePath=json.savePath;
	        	   //放置到input隐藏域
	        	    $("#filehidden").val(savePath);
	        	    $("#fileSpan").css("color","green");
					$("#fileSpan").text("头像上传成功")
	            	fileFlag=true;
	           },	
	           error: function(data, status, e){ 
	        	    $("#fileSpan").css("color","red");
					$("#fileSpan").text("头像上传失败，请检查网络！")
	           }
	       }); 
	   }
	
	function login() {
		var password = $("input[name='password']").val();
		if ($("input[name='login']").val() == "" || password == "") {
			$("#showMsg").html("用户名或密码不能为空，并且密码长度为6-12位");
			$("input[name='login']").focus();
		} else {
			//ajax异步提交  
			$.ajax({
				data : $("#loginForm").serializeArray(),
				type : "GET",
				dataType : 'json',
				url : "<%=path%>/userlogin",
				beforeSend: function () {
					//加载图标
					$('#YWaitDialog').show();
					                    },
				error : function(data) {
					$('#YWaitDialog').hide();
// 					$.messager.alert('警告',"网络出错了！！" ,'warning');
				},
				success : function(data) {
					$('#YWaitDialog').hide();
					$("#result").html(data.msg);
					if($("#checkCode").val()==""){
						 Common.confirm({
						      title: "提示",
						      message: "请输入验证码！",
						  })
							}else{
								if(data.msg=="success"){
									if($("#check").is(":checked")){
										var account = $("#account").val();
										var password = $("#password").val();
										$.cookie("account", account, { expires: 7 });
										$.cookie("password", password, { expires: 7 });
									}
										window.location.href="<%=basePath%>loginSuccess";
									}else if (data.msg == "NoAccount") {
										
										 Common.confirm({
										      title: "提示",
										      message: "无此账户，请重新输入！",
										  })
									}else if (data.msg == "wrongPassword") {
										 Common.confirm({
										      title: "提示",
										      message: "账户密码错误，请重新输入！",
										  })
									}else if (data.msg == "networkAnomaly") {
										 Common.confirm({
										      title: "警告",
										      message: "网络链接异常，请确认！",
										  })
									}else if (data.msg == "checkCode") {
										 Common.confirm({
										      title: "警告",
										      message: "验证码错误，请重新输入！",
										  })
										  
									}
							}
					}
				});
		}
	}

	//提示框
	var Common = {
		    confirm:function(params){
		        var model = $("#common_confirm_model");
		        model.find(".title").html(params.title)
		        model.find(".message").html(params.message)
		        $("#common_confirm_btn").click()
		        //每次都将监听先关闭，防止多次监听发生，确保只有一次监听
		        model.find(".cancel").die("click")
		        model.find(".ok").die("click")
		        model.find(".ok").live("click",function(){
		            params.operate(true)
		        })
		        model.find(".cancel").live("click",function(){
		            params.operate(false)
		        })
		    }
		}
	
	function userNameCheck(){
		$.ajax({
			data:{
				userName:$("input[name='userName']").val(),
			},
			type : "POST",
			dataType : 'json',
			url : "<%=path%>/UserNameCheck",
			success : function(data) {
				var aa=$("input[name='userName']").val();
				if(aa==""){
					$("#userNameSpan").css("color","red");
					$("#userNameSpan").text("昵称不能为空！")
				}else{
					if(data.msg=="1"){
						$("#userNameSpan").css("color","red");
						$("#userNameSpan").text("昵称已经存在！")
 					}else if(data.msg=="2"){
 						$("#userNameSpan").css("color","green");
						$("#userNameSpan").text("该昵称可以注册！")
						NikeNameFlag = true;
 					}else{
 						alert("网络错误！")
 					}
				}
			}
		});
	}
	
	function emailCheck(){
		$.ajax({
			data:{
				email:$("input[name='email']").val(),
			},
			type : "POST",
			dataType : 'json',
			url : "<%=path%>/EmailCheck",
			success : function(data) {
// 				var json = eval('(' + data + ')');
					var email=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/; 
					var aa=$("input[name='email']").val()
					if(email.test(aa)){
						if(data.msg=="1"){
							$("#emailSpan").css("color","red");
							$("#emailSpan").text("邮箱已经存在！")
	 					}else if(data.msg=="2"){
	 						$("#emailSpan").css("color","green");
							$("#emailSpan").text("该邮箱可以注册！")
							EmailFlag = true;
							
	 					}else{
	 						alert("网络错误！")
	 					}
					}else{
						$("#emailSpan").css("color","red");
						$("#emailSpan").text("邮箱格式错误！请正确输入邮箱！")
					}
					
			}
		});
	}

	function passWordTwoCheck(){
		var passWord=$("input[name='passWord']").val()
		var passWordTwo=$("input[name='passWordTwo']").val();
		if(passWordTwo==""){
			$("#passWordTwoSpan").css("color","red");
			$("#passWordTwoSpan").text("密码不能为空");
		}else{
			if(passWord==passWordTwo){
				$("#passWordTwoSpan").css("color","green");
				$("#passWordTwoSpan").text("密码格式正确");
				passWordFlag = true;
			}else{
				$("#passWordTwoSpan").css("color","red");
				$("#passWordTwoSpan").text("俩次输入的密码不同！")
			}
		}
	}
	
	function codeUpdate(data){
		data.src='<%=path %>/PictureCheckCode?d=' + Math.random();
	}
	
</script>

<style type="text/css">
	body {
		height:100%;
		width:100%;
		background: url(<%=path%>/chat/image/background_4.jpg) no-repeat ;
		background-size: cover;
	    border:solid 1px #fff;
	}
	.mycenter{
	    margin-top:15%;
	    margin-left: auto;
	    margin-right: auto;
	    height: 600px;
	    width:600px;
	    padding: 5%;
	    padding-left: 5%;
	    padding-right: 5%;
	}
	.mycenter mysign{
	    width: 300px;
	}
	.mycenter input,checkbox,button{
	    margin-top:2%;
	    margin-left: 16%;
	    margin-right: 10%;
	}
	.mycheckbox{
	    margin-top:10px;
	    margin-left: 16%;
	    margin-bottom: 10px;
	    height: 10px;
	}
	
	#title{
		position:absolute;
		margin-top:-5%;
		margin-left:30%;
		height: 50px;
	    width:600px;
	}
	#tatlefont{
		font-size:50px;
		font-color:
	} 
	#headimage{
		margin-top:15%;
	    margin-left: 55%;
	    height:80px;
	}
	#titelImage{
		position:absolute;
	}
	#CreateCheckCodeDiv{
		margin-top:-10%;
		margin-left: 67%;
	}
	#register{
		margin-top:5%;
	}
	
</style>
</head>
<body>
<%-- 	 <div id=titelImage><img id="headimage" src="<%=path%>/chat/images/titel1.png" /></div> --%>
	 <form id="loginForm"  action="login.php" method="post">
	 		<!-- <div class="col-lg-11 text-center text-info" id="title"><font id="tatlefont" color="#497BC8" style="letter-spacing: 6px">贵宾厅智能候机系统</font></div> -->
            <div class="mycenter">
            <div class="mysign">
                <div class="col-lg-11 text-center text-info">
                    <h2><font color="#3CA9C4">请登录</font></h2>
                </div>
                <div class="col-lg-10">
                    <input type="text" class="form-control"  style="width:85%"  id="account"  name="account" placeholder="请输入账户名" required autofocus/>
                </div>
                <div class="col-lg-10"></div>
                <div class="col-lg-10">
                    <input type="password" class="form-control"   style="width:85%"  id="password" name="password"  placeholder="请输入密码" required autofocus/>
                </div>
                <div class="col-lg-10">
                	<input type="text" class="form-control"  style="width:50%"   id="checkCode" name="checkCode"  placeholder="输入验证码" required autofocus/>
                    <div id="CreateCheckCodeDiv"><img src="<%=path %>/PictureCheckCode"  style="width:102%" id="CreateCheckCode" align="middle"  onclick="codeUpdate(this)"></div>
                </div>
                <div class="col-lg-10 mycheckbox checkbox">
                    <input type="checkbox" class="col-lg-1"  id="check"  ><font color="#3CA9C4">记住密码</font></input>
                    <div
						style="margin-top:75px; margin-left:-16%;text-align: center; color: #3CA9C4;"
						id="showMsg" ></div>
                </div>
                <div class="col-lg-10">
                    <button type="button"  style="width:85%" class="btn btn-success col-lg-12" onclick="login()">登录</button>
                </div>
                 <div class="col-lg-10">
                    <a data-toggle="modal" data-target="#register"  style="width:85%;margin:20px 20px 20px 16%" href="" > 没有账号？点击注册</a>
<!--                     <button type="button"  style="width:85%" class="btn btn-success col-lg-12" ></button> -->
                </div>
            </div>
        </div>
        
        <input type="hidden" id="common_confirm_btn" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#common_confirm_model">
		<div style="margin-top:18%"  id="common_confirm_model" class="modal">
		    <div class="modal-dialog modal-sm">
		        <div class="modal-content" style="border-radius: 8px 8px 8px 8px;">
		            <div class="modal-header" style="background-color: #4995C8;border-radius: 8px 8px 0px 0px;">
		                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		                <h5 class="modal-title"><i class="fa fa-exclamation-circle"></i> <span class="title"></span></h5>
		            </div>
		            <div class="modal-body small">
		                <p ><span class="message" style="font-size: 15px"></span></p>
		            </div>
		            <div class="modal-footer"  style="height:60px" >
		                <button type="button" class="btn btn-primary ok" data-dismiss="modal">确认</button>
		            </div>
		        </div>
		    </div>
		</div>
        
        <div id="YWaitDialog" style="background-color: #FFFFFF; z-index: 99998;position: absolute; left: 50%; margin-left: -138px; display: none;   top: 51%;">
				<p style=""><img style="width:130px;height:21px" src="<%=path%>/chat/image/abcd.gif" /><a>正在登陆，请稍候...</a></p>
		</div>
		
		<div id="emailLoding" style="border:1px solid #183150;width:500;height:100px;background-color:white;margin-top:-27%;display: none;margin-left:37%;z-index: 99998;position: absolute;">
			<div style="margin-top:8%;margin-left:10%"><p style=""><a style="color:#183150;margin:10px 10px 10px 10px ">发送确认邮件中，请稍等...</a><img style="width:200px;height:18px" src="<%=path%>/chat/image/abcd.gif" /></p></div>
		</div>
		
        </form>
             <!-- 注册窗口 -->
    <div id="register" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-title">
                    <h1 class="text-center">注册</h1>
                </div>
                <div class="modal-body" >
                    <div class="form-group" action="">
                    		<div class="form-group">
                                <label for="">账户（邮箱）</label>
                                <input id="email"  name="email" class="form-control" type="email" placeholder="例如:123@123.com" onblur="emailCheck()">
                                <span id="emailSpan"></span>
                            </div>
                            <div class="form-group">
                                <label for="">昵称</label>
                                <input id="userName" name="userName" class="form-control" type="text" placeholder="6-15位字母或数字" onblur="userNameCheck()">
                                 <span id="userNameSpan"></span>
                            </div>
                            <div class="form-group">
                                <label for="">密码</label>
                                <input id="passWord"  name="passWord" class="form-control" type="password" placeholder="至少6位字母或数字">
                            </div>
                            <div class="form-group">
                                <label for="">再次输入密码</label>
                                <input id="passWordTwo"  name="passWordTwo" class="form-control" type="password" placeholder="至少6位字母或数字" onblur="passWordTwoCheck()">
                                <span id="passWordTwoSpan"></span>
                            </div>
                            <div class="form-group">
                                <label for="">上传头像</label>
                                <input id="file1"  name="file1" class="form-control" type="file" placeholder="至少6位字母或数字" onchange="file1Upload()">
                                <span id="fileSpan"></span>
                            </div>
                            
                            <div class="form-group">
							    <label for="exampleInputEmail2">邮箱验证</label>
							    <input type="email" class="form-control" id="emailCode" placeholder="请输入邮箱验证码">
						    </div>
 							<button style="width:20%;color:white;background-color:#1E7BA5" type="button" class="btn btn-default"  onclick="code(this)">发送验证码</button>
						    <button style="width:20%;color:white;background-color:#1E7BA5" type="button" class="btn btn-default" onclick="enrooll()">注册账户</button>
                                <span id="fileSpan"></span>
                            </div>
                            <input id="filehidden"  name="filehidden"  type="hidden"  >
                            <span id="nextCheck"></span>
                            
                            
                            
                            
                            
                            <a href="" data-toggle="modal" data-dismiss="modal" data-target="#login">已有账号？点我登录</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    

</body>


</html>