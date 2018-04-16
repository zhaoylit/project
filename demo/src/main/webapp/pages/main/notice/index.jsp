<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath()+"/pages";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<base href="<%=basePath%>" />
<meta charset="UTF-8">
<title>后台管理系统</title>
<style type="text/css">
    html,body  
    {  
        height:100%;  
        margin:0 auto;  
    }

</style>
<!-- 引入easyui公共库文件 -->
<link id="easyuiTheme" rel="stylesheet"	href="<%=path%>/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/index/index.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/demo.css">
<script type="text/javascript" src="<%=path%>/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/javascript/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path%>/main/xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.portal.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.cookie.js"></script>
<%-- <script type="text/javascript" src="<%=path%>/main/jeasyui.common.js"></script> --%>
<script type="text/javascript" src="<%=path%>/jquery.datagrid.extend.js"></script>
<script type="text/javascript" src="<%=path%>/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/javascript/page.js" charset="utf-8"></script>
</head>
<body class="easyui-layout">
	 <noscript>
		<div style="position: absolute; z-index: 100000; height: 246px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div data-options="region:'north',border:false" style="z-index:100000;" id="index-head">
		<div class="head">
		   <ul>
			   <li>
				   <img src="images/index/titel.png">
			   </li>
			   <li>
				   <!-- <img src="images/index/logo.png" id="logo"> -->
				   
			   </li>
		   </ul>
		   <div id="cancellation">
				欢迎${sessionScope.username }&nbsp&nbsp
				<a id="zhu" href="javascript:toPage('修改密码','pages/main/gaimi.jsp')">修改密码</a>&nbsp
				<a id="zhu" href="user/cancellation.do">注销</a></span>
			</div>
	   </div>
	   <div class="banner" style="">
	  	   <!--  如果是地服人员则显示 -->
		   <c:if test="${isDF ==true }">
			<div class="easyui-panel banner_head" style="padding:10px;">
				<a href="#" class="easyui-menubutton" data-options="menu:'#mm1'" id="index_notice">公告发布</a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#mm2'" id="index_check">历史查询</a>
				<a href="${ctx}/pages/main/guide/guide.jsp" target="_blank" class=""  id="index_guide" style="font-size:18px;">使用说明书</a>
			
			</div>
			<div id="mm1" class="menu-content" style="text-align:center;">
				<div>
				     <a  href="javascript:toPage('寻找旅客','pages/main/notice/noticePer.jsp')" style="font-size:18px;color:#fff">寻找旅客</a>
				</div>
				<div>
				     <a class="nav-link" href="javascript:toPage('航班提醒','pages/main/notice/noticeFlight.jsp')" style="font-size:18px;color:#fff">航班提醒</a>
				</div>
			</div>
			<div id="mm2" style="text-align:center;" class="menu-content">
				<div>
				     <a href="javascript:toPage('寻找旅客查询','pages/main/history/query_per.jsp')" style="font-size:18px;color:#fff">寻找旅客发布记录查询</a>
				</div>
				<div>
				     <a href="javascript:toPage('航班提醒查询','pages/main/history/query_flight.jsp')" style="font-size:18px;color:#fff">航班提醒发布记录查询</a>
				</div>
			 <!-- <div>
				      <a href="javascript:toPage('日志查询','pages/main/log/log.jsp')" class="nav-link" style="font-size:18px;color:#fff">广告发布记录查询</a>
				</div>  -->
			</div>
			</c:if>
		</div>
	</div>
	<div data-options="region:'west',collapsed:${isDF},split:true,title:'导航菜单'" id="navMenu" style="width: 200px;height:1000000px;z-index:-1000;position:relative">
		<div id="aa" class="easyui-accordion sider"
			data-options="fit:true,border:false" >
			<!--左侧菜单-->		
			 <c:forEach var="item" items="${mainMenu}" varStatus="status"> 
			  <c:if test="${item.name != '授权管理'}"> 
				<div title="${item.name}" id="${item.menuId}" data-options="iconCls:'icon-mini-add'" style="padding: 10px;">
			 		<!--树形菜单-->
			 		<ul class="easyui-tree" id="tree_${item.menuId}"  data-options="animate:true,lines:true,url:'menu/getTreeMenu.do?parentId=${item.menuId}'">
				</div>	
				  <script type="text/javascript">      
	     	  	     $("#tree_${item.menuId}").tree({
	     	     	    onSelect:function(node){    
	     	     	    	/* 菜单跳转 */
 	     	     			//addTab(node.text,node.attributes.menuUrl);
	     	     	    	toPage(node.text,node.attributes.menuUrl);
	     	     	   }
	     	        }); 
	   	  		 </script>
	   	  		 </c:if>  
			</c:forEach>
			
		    <div title="测试菜单" data-options="iconCls:'icon-application-form-edit'" style="padding:5px;">
				<ul class="easyui-tree wu-side-tree">
					<li iconCls="icon-chart-organisation"><a href="javascript:toPage('测试菜单','program/programListInit.do')" data-icon="icon-chart-organisation" data-link="layout-3.html" iframe="0">节目单管理</a></li>
				</ul>
			</div>
		
  			<!-- <div title="快捷菜单" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">
			<div title="快捷菜单" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">
				<ul class="easyui-tree wu-side-tree">
					<li iconCls="icon-usergroups"><a href="javascript:toPage('软件更新','system/deviceRemoteUpdateInit.do')" data-icon="icon-users" data-link="temp/layout-3.html" iframe="0">软件更新</a></li>		
				</ul>
			</div>
<!-- 		
  			<div title="快捷菜单" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">
				<ul class="easyui-tree wu-side-tree">
					<li iconCls="icon-users"><a href="javascript:toPage('节目单管理','pages/main/advert/program.jsp')" data-icon="icon-users" data-link="temp/layout-3.html" iframe="0">节目单管理</a></li>			
				</ul>
			</div>
			<div title="设备管理" data-options="iconCls:'icon-application-form-edit'" style="padding:5px;">
				<ul class="easyui-tree wu-side-tree">
					<li iconCls="icon-chart-organisation"><a href="javascript:toPage('设备远程重启','system/deviceRebootInit.do')" data-icon="icon-chart-organisation" data-link="layout-3.html" iframe="0">设备远程重启1</a></li>
					<li iconCls="icon-chart-organisation"><a href="javascript:toPage('设备信息管理','device/deviceListInit.do')" data-icon="icon-chart-organisation" data-link="layout-3.html" iframe="0">设备信息管理</a></li>
					<li iconCls="icon-users"><a href="javascript:toPage('设备现场维护记录','device/deviceMtnListInit.do')" data-icon="icon-users" data-link="temp/layout-3.html" iframe="0">设备现场维护记录</a></li>
					<li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="temp/layout-3.html" iframe="0">设置远程关机</a></li>
					<li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="temp/layout-3.html" iframe="0">监测设备的实时状态</a></li>
				</ul>
			</div>	
			<div title="信息导入" data-options="iconCls:'icon-application-form-edit'" style="padding:5px;">
				<ul class="easyui-tree wu-side-tree">
					<li iconCls="icon-chart-organisation"><a href="javascript:toPage('导入航班计划表','pages/main/info/flight.jsp')" data-icon="icon-chart-organisation" data-link="layout-3.html" iframe="0">导入航班计划表</a></li>
					<li iconCls="icon-users"><a href="javascript:toPage('导入登机口信息','pages/main/log/gate.jsp')" data-icon="icon-users" data-link="temp/layout-3.html" iframe="0">导入登机口信息</a></li>
					<li iconCls="icon-user-group"><a href="javascript:toPage('导入机场信息','pages/main/info/airport.jsp')" data-icon="icon-user-group" data-link="temp/layout-3.html" iframe="0">导入机场信息</a></li>
					<li iconCls="icon-book"><a href="javascript:toPage('导入vip旅客的信息','pages/main/info/vip.jsp')" data-icon="icon-book" data-link="temp/layout-3.html" iframe="0">导入vip旅客的信息</a></li>
				</ul>
			</div>    -->
		</div>	
	</div>
	<!--west-->
	     <div data-options="region:'south',border:false"
			style="height: 30px; background: #fff; padding: 5px;">
			<div id="footer">
				Copyright &copy; 2016 by . All Rights Reserved<br>
			</div>
		</div>	
	<!--//主体内容部分-->
	<div data-options="region:'center'" border="false" class="indexcenter" style="z-index:-10000;">
		<div id="tabs_index" name="test" class="easyui-tabs" fit="true" border="false" style="">
			<div id="index_content" title="首页" style="overflow:auto;z-index:100000000" data-options="href:'${ctx }/pages/main/main.jsp'">
				
			</div>
		</div>
	</div> 
	<!--center-->
	<!--//主体内容部分-->
	<div id="dialog_cms" data-options="iconCls:'icon-save'"></div>
     <script type="text/javascript">
     $(window).resize(function(){
// 		$(".panel-body-noheader").css({"background-color":"#0864aa !important"});
     });
     $("[iconCls='icon-add']").click(function(){
    	 $("#gai").window({
    	 title:'增加角色信息', 
    	 href:'test/add', 
    	 width:800, 
    	 height:400, 
    	 });
    	 $("#popWindow").window("open");
    	 });
          $(document).ready(function(){
        	  $("#top_menu_u_main1").hide();
        	  $("#top_menu_u_main2").hide();
        	  
        	  $("#tabs_index").click(function(){
        		  $("#top_menu_u_main1").hide();
            	  $("#top_menu_u_main2").hide();
        	  });
        	  
        	  $("#aa").click(function(){
        		  $("#top_menu_u_main1").hide();
            	  $("#top_menu_u_main2").hide();
        	  });
          })
          
           $(".dropdown-menu").find("li").click(function(){
        	   $(this).toggleClass("ulShow");
        	   
        	   $(".dropdown-menu li").removeClass("ulShow");
        	   $(this).addClass("ulShow");
        	   
        	   
           });
           // 头部banner的js控制
            $(".dropdown-toggle").click(function(){
            	 $(".dropdown-toggle").removeClass("addStyle");
            	  $(this).toggleClass("addStyle");  
		       if($(this).siblings("ul").is(':hidden')){
		    	   $(".dropdown-menu").hide();
		           $(this).siblings("ul").show();
		       }else{
		           $(this).siblings("ul").hide();
		           $(this).removeClass("addStyle");
		           
		       }    
		    })
		    
		function addTab(title, url){
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
		function logout(){
				$.get("logout.do", 
 						{},
 						function(data){
 							if (data.success=="true"){	
 								window.location.href = "menu/index.do";
 							}
 						},
						"json"
 				);
		}
		
		//点击菜单跳转的方法,局部刷新
		function toPage(text,url){
			$.ajax({
				type:"post",
				async:false,//jax同步
				url:"${ctx}/"+url,
				data:{},
				global:true,//使用全局的ajax
				success:function(data, textStatus){
					$("#index_content").find(".tabs-title").html(text);
					$("#index_content").html(data);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(errorThrown);
				},
				dataType:"html"
			});
			
		}
        </script>		
</body>
</html>