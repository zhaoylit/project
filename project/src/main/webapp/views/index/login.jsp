<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/link.jsp"%>
<%@include file="/script.jsp"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/gray/easyui.css"/> --%>
</head>
<body>
<div style="width:100%;height:100%;">
	<div id="loginWin" class="easyui-window" title="登录"  data-options="iconCls:'icon-key'" style="width:300px;height:200px;padding:5px;"
	 minimizable="false" maximizable="false" resizable="false" collapsible="false" draggable="false" closable="false">
	    <div class="easyui-layout" fit="true">
	        <div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">
		        <form id="loginForm" method="post">
		            <div style="padding:5px 0;">
		                <label for="login">帐号:</label>
		                <input class="easyui-textbox" prompt="请输入用户名" data-options="iconCls:'icon-man'" name="userName" style="width:200px;"></input>
		            </div>
		            <div style="padding:5px 0;">
		                <label for="password">密码:</label>
		                <input class="easyui-passwordbox" prompt="请输入密码" data-options="iconCls:'icon-lock'" name="passWord" style="width:200px;"></input>
		            </div>
		            <div style="padding:5px 0;text-align: left;color: red;" id="showMsg"></div>
		        </form>
	        </div>
	        <div region="south" border="false" style="text-align:right;padding:5px 0;">
	            <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="login()">登录</a>
	            <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cleardata()">重置</a>
	        </div>
	    </div>
	</div>
</div>
</body>
<script type="text/javascript">
document.onkeydown = function(e){
    var event = e || window.event;  
    var code = event.keyCode || event.which || event.charCode;
    if (code == 13) {
        login();
    }
}
$(function(){
    $("input[name='login']").focus();
});
function cleardata(){
    $('#loginForm').form('clear');
}
function login(){
     if($("input[name='userName']").val()=="" || $("input[name='passWord']").val()==""){
         $("#showMsg").html("用户名或密码为空，请输入");
         $("input[name='login']").focus();
    }else{
            //ajax异步提交  
           $.ajax({            
                  type:"POST",   //post提交方式默认是get
                  url:"checkLogin.do", 
                  data:$("#loginForm").serialize(),   //序列化               
                  error:function(request) {      // 设置表单提交出错                 
                      $("#showMsg").html(request);  //登录错误提示信息
                  },
                  success:function(data) {
                	  if(data.resultCode != "1"){
                		  $("#showMsg").html(data.resultMsg);
                	  }else{
                		  document.location = "main.do";
                	  }
                      
                  }
                  ,dataType:"json"        
            });       
        } 
}
</script>
</html>