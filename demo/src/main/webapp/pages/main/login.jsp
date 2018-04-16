<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath() + "/pages";
	String patha = request.getContextPath() + "/javascript";
    String ctx = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<title>欢迎登陆</title>
<link rel="stylesheet" href="<%=patha %>/js/bootstrap.min.css">
<script type="text/javascript" src="<%=patha%>/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=patha%>/jquery.cookie.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=patha %>/js/bootstrap.min.js"></script>



<script type="text/javascript">
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
				url : "<%=basePath%>user/login.do",
				beforeSend: function () {
					//加载图标
					$('#YWaitDialog').show();
					                    },
				error : function(data) {
					$('#YWaitDialog').hide();
					$.messager.alert('警告',"网络出错了！！" ,'warning');
				},
				success : function(data) {
					$('#YWaitDialog').hide();
					$("#result").html(data.msg);
					
					if(data.msg=="success"){
							console.log("aa");	
							
							if($("#check").is(":checked")){
								var account = $("#account").val();
								var password = $("#password").val();
								$.cookie("account", account, { expires: 7 });
								$.cookie("password", password, { expires: 7 });
							}
								window.location.href="<%=basePath%>menu/index.do";
							}
							 else if (data.msg == "freeze") {
								 Common.confirm({
								      title: "警告",
								      message: "登陆失败！账号被冻结，请联系管理员。",
								  })
							}
							 else if (data.count == "1") {
								 Common.confirm({
								      title: "警告",
								      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数1次！",
								  })
							}
							 else if (data.count == "2") {
								 Common.confirm({
								      title: "警告",
								      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数2次！",
								  })
							}
							 else if (data.count == "3") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数3次！",
									  })
								}
							 else if (data.count == "4") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数4次！",
									  })
								}
							 else if (data.count == "5") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数5次！",
									  })
								}
							 else if (data.count == "6") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数6次！",
									  })
								}
							 else if (data.count == "7") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数7次！",
									  })
								}
							 else if (data.count == "8") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数8次！",
									  })
								}
							 else if (data.count == "9") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数9次！",
									  })
								}
							 else if (data.count == "10") {
									 Common.confirm({
									      title: "警告",
									      message: "&nbsp&nbsp密码错误，错误10次该账户将被冻结！<br/><br/>&nbsp&nbsp当前错误次数10次！",
									  })
								}
							 else if (data.msg == "cuowu") {
								 Common.confirm({
								      title: "提示",
								      message: "无此账户，请重新输入！",
								  })
							}
					
						}
					});
		}
	}

	
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
	
</script>

<style type="text/css">
	body {
		height:100%;
		width:100%;
		background: url(<%=basePath%>/images/index/background.jpg) no-repeat ;
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
		margin-top:10%;
	    margin-left: 40%;
	}
	#titelImage{
		position:absolute;
	}
	
</style>
</head>
<body>
	 <div id=titelImage><img id="headimage" src="<%=basePath%>/images/index/titel.png" /></div>
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
                
						
                <div class="col-lg-10"></div>
                <div class="col-lg-10 mycheckbox checkbox">
                    <input type="checkbox" class="col-lg-1"  id="check"  ><font color="#3CA9C4">记住密码</font></input>
                    <div
						style="margin-top:68px; text-align: center; color: #3CA9C4;"
						id="showMsg"></div>
                </div>
                
                <div class="col-lg-10"></div>
                <div class="col-lg-10">
                    <button type="button"  style="width:85%" class="btn btn-success col-lg-12" onclick="login()">登录</button>
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
				src="<%=basePath%>/images/abcd.gif" /><a>正在登陆，请稍候...</a>
		</p>
		<a></a>
	</div>
        </form>

</body>


</html>