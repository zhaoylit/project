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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>众享互动IM系统</title>
<!-- 引入JQuery -->
  <script type="text/javascript" src="<%=path %>/chat/plugin/easy-ui/jquery.min.js"></script>
  <!-- 引入EasyUI -->
  <script type="text/javascript" src="<%=path %>/chat/plugin/easy-ui/jquery.easyui.min.js"></script>
  <!-- 引入EasyUI的中文国际化js，让EasyUI支持中文 -->
  <script type="text/javascript" src="<%=path %>/chat/plugin/easy-ui/locale/easyui-lang-zh_CN.js"></script>
  <!-- 引入EasyUI的样式文件-->
  <link rel="stylesheet" href="<%=path %>/chat/plugin/easy-ui/themes/bootstrap/easyui.css" type="text/css"/>
  <!-- 引入EasyUI的图标样式文件-->
  <link rel="stylesheet" href="<%=path %>/chat/plugin/easy-ui/themes/icon.css" type="text/css"/>
  
  <style type="text/css">
		body{
			height:100%;
			width:99%;
			background: url(<%=basePath%>/chat/image/5.jpg) no-repeat;
			background-size: cover;
		}
		#pageHead{
			border-radius: 6px; //圆角
			width:150px;
			height:28px; 
			color:white;
			margin-left:85%; 
			margin-top:10px; 
			position:relative;
		}
		#pageHeadFont{
		
		}
		#pageHeadButton{
			font-size:15px;
			height:24px; 
   			border: 1px solid #dedede;
   			-moz-border-radius: 15px;      /* Gecko browsers */
   			-webkit-border-radius: 15px;   /* Webkit browsers */
   			border-radius:15px;            /* W3C syntax */
		}
  </style>
  
  <script type="text/javascript">
  	$(function(){
  		$('#tt').tree({    
  		    url:'<%=path%>/TreeServlet',
  		    onClick: function(node){
// 	  			alert(node.id);  // 在用户点击的时候提示
// 	  			alert("type-------------"+node.attributes.type);
// 	  			alert("level-------------"+node.attributes.level);
	  			if(node.attributes.level=="2"){
	  				if(node.attributes.type=="1"){
	  					var url="<%=path%>/group?method=chatInit&type=1&fromUserId=${userSession.id}&toUserId="+node.id+"";
			  			addTab('聊天',url);
	  				}else if(node.attributes.type=="2"){
	  					var url="<%=path %>/group?method=chatInit&type=2&fromUserId=${userSession.id}&groupId="+node.id+""; 
			  			addTab('聊天',url);
	  				}
	  			}
	  		}
  		});
  	});
  	
  	function exitUser(){
  		$.ajax({
			url : "<%=path%>/exitUserSession",
			success : function(data) {
				var json = eval('(' + data + ')');
				if(json.msg=="1"){
					window.location.href="<%=path%>/chat/login.jsp";
				}else {
					alert("网络错误！")
				}
			}
  		});
  	}
  	
	function addTab(title,url){
		var content = '<iframe scrolling="false" id="roleIframe" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	 	if ($('#tabs_index').tabs('exists', title)){
			$('#tabs_index').tabs('select', title);
			/* 刷新iframe内容 */
				var tab = $('#tabs_index').tabs('getSelected');  // get selected panel
			$('#tabs_index').tabs('update', {	
				tab: tab,
				options: {
					content:content
				}
			}); 
		} else { 
			$('#tabs_index').tabs('add',{
				title:title,
				content:content,
				closable:true
			});
		 } 
	}    
	
	$(document).ready(function()
            {
                $("#pageHeadButton").hover(function()
                {
                    $("#pageHeadButton").css("background-color","gray");
                });
                 
                $("#pageHeadButton").mouseout(function()
                {
                    $("#pageHeadButton").css("background-color","white");
                });
            });  
	
  </script>
  
</head>
	<body>
		<div id="pageHead" >&nbsp&nbsp<font id="pageHeadFont">欢迎:${userSession.nickName }</font>&nbsp&nbsp&nbsp<input id="pageHeadButton"  type="button" style="cursor: pointer" name="button" value="注销用户"    onclick="javascript:exitUser();"/></div>
		<div id="cc" class="easyui-layout"  style="width:1300px;height:700px;margin:0px auto;margin-top:6%"> 
			<!--     <div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div>    -->
			<!--     <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>    -->
			<!--     <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>    -->
		    <div data-options="region:'west',title:'聊天列表',split:true" style="width:30%;">
		    	<c:if test="${userSession.account == 'admin' }">
			    	<div class="easyui-layout"  data-options="fit:true">
			    		<div data-options="region:'north'" style="width:30%;height:94%;">
					    	<ul id="tt"></ul>
					    </div>   
					    <div data-options="region:'center'" style="padding:5px;height:100px;">
							<!-- 系统设置按钮 -->
					    	<img src="<%=path %>/chat/image/setting.png" style="cursor:pointer;" title="点击进行系统设置" onclick="addTab('系统设置','<%=path %>/system?method=settingInit');"/>
					    </div>   
			    	</div>
		    	</c:if>
		    	<c:if test="${userSession.account != 'admin' }">
			    	<ul id="tt"></ul>
		    	</c:if>
		    </div>   
		    <div data-options="region:'center'" style="padding:5px;background:	;">
		    	<div id="tabs_index" name="test" class="easyui-tabs" fit="true" border="false" style=""></div>
		    </div>   
		</div>  
	</body>
</html>