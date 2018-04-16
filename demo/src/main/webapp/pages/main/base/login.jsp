<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath()+"/pages";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
<link id="easyuiTheme" rel="stylesheet"	href="<%=path%>/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/main/portal.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/main/common.css">
<script type="text/javascript" src="<%=path%>/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/main/xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.portal.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.md5.js"></script>
<script type="text/javascript" src="<%=path%>/main/jeasyui.common.js"></script>
<script type="text/javascript" src="<%=path%>/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script>
	//该页面功能相对独立，尽量单独使用
		$(function(){
			$('#win').window({  
				collapsible:false,  
				minimizable:false,  
				maximizable:false,
				resizable:false,
				closable:false,
				draggable:false
			});  
		 	$("#changeImg").live("click", function(){
	     	   	  changeImg($("#imgObj"));
	      	});
		});

	      function changeImg(obj){
	       	 var verifySrc = $(obj).attr("src");
	       	 var time = new Date().getTime();
	       	 if(verifySrc.indexOf('?') > 0){
	       	 	$(obj).attr("src", verifySrc+"&time="+time);
	       	 }else{
	       	 	$(obj).attr("src", verifySrc+"?time="+time);
	       	 }
	       }
	      
	    document.onkeydown=onLogin;
		
	    function doSubmit(){	 
	    	var vcode=$("#verifyCode").val();
	    	
	        $.post("login.do",
	        		{"username":$("#username").val(),"password":$.md5($("#password").val()),"verifyCode":$("#verifyCode").val()},
	        		function(data,status){
	        			var success = data.result;
	        			if("SUCCESS"==data.result){
	        				window.location.href = data.url;	        				
	        			}else{	        				
	        				$("#errorMessage").html(data.message);
	        			}
	        		},
	        		"json"
	        );
	    }
	    
	    function onLogin(e) {  
	
	        // 兼容FF和IE和Opera  
	        var theEvent = e || window.event;  
	        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
	        if (code == 13) {  
	        	doSubmit();
	            return false;  
	        }  
	        return true;  
	    }
	</script>

	<style>
		#login_form em{
			float:left;
			width:56px;
			line-height:22px;
			font-style:normal;
		}
    table td{padding:5px;cellspacing:5px;}
	</style>
</head>
<body onkeydown="onLogin();" style="height:100%;width:100%;overflow:hidden;border:none;background-image: url('<%=basePath%>/images/background.jpg');background-size:cover;background-repeat:no-repeat;" >
	<!--
	<h1>Window</h1>
	<button onclick="javascript:$('#win').window('open')">open</button>
	-->
	  <div style="margin-top:50px;font-family:'黑体';font-weight:bold;">
	     	 <center><span style="font-size:26px;">风游精后台管理系统</span></center>
      </div>
	<div id="win" class="easyui-window" title="风游精登录" style="width:360px;height:300px;">
		<form id="login_form" style="padding:10px 20px 10px 20px;"  method="post">
		<table style="width:100%;margin:5px;font-size:16px;font-weight:normal;">
		  <tr align="center"><td colspan="2"><span id="errorMessage" style="font-size:14px;color:red;"></span></tr>
		  <tr><td>用户名:</td><td><input class="easyui-textbox" id="username" name="username" style="width:160px;height:25px"></td></tr>
		  <tr><td>密    码:</td><td><input class="easyui-textbox" id="password" name="password" type="password" style="width:160px;height:25px"></td></tr>
		  <tr>
			<td><label class="control-label visible-ie8 visible-ie9">验证码</label></td>
		  	<td>		
	            <input class="easyui-textbox" style="width:160px;height:25px" placeholder="输入验证码" name="verifyCode" id="verifyCode"/>	           
        	</td>
          </tr>
          <tr>
       		<td></td>
       		<td><span><img id="imgObj" alt="验证码" src="<%=basePath%>vcode/verifyCode.do"/></span>
            <span>&nbsp;<a href="javascript:;" id="changeImg" class="changeImg" >换一张</a></span>
            </td>
          </tr>
		  <tr><td colspan="2"><div style="padding:5px;text-align:center;">
				<a href="javascript:void(0);" onclick="doSubmit();" class="easyui-linkbutton" style="width:160px">登录</a>&nbsp;&nbsp;
				<!-- <a href="javascript:void(0);" onclick="$('#login_form').reset()" class="easyui-linkbutton" icon="icon-cancel">重填</a> -->
			</div></td></tr>
		</table>	
		</form>
	</div>
	<footer>
      <center>
	      <div id="footerDiv" style="height:20px;">
	     	 <span>2016 &copy; 北京风游精互联网科技有限公司 - 后台管理系统.</span>
	      </div>
      </center>
</footer>
</body>
<script type="text/javascript">
	$("#footerDiv").css("margin-top",$(window).height()-150);
</script>
</html>