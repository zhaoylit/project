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
<meta charset="UTF-8">
<title>众享互动后台管理系统</title>
<link id="easyuiTheme" rel="stylesheet"	href="<%=path%>/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/main/portal.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/main/common.css">
<script type="text/javascript" src="<%=path%>/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/main/xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.portal.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path%>/main/jeasyui.common.js"></script>
<script type="text/javascript" src="<%=path%>/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

</head>
<body class="easyui-layout">
	 <noscript>
		<div
			style="position: absolute; z-index: 100000; height: 246px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div data-options="region:'north',border:false"
		style="height: 45px; background: rgb(225, 237, 255); padding: 0px">
		<div class="site_title">众享互动后台管理系统</div>
		<div id="sessionInfoDiv"
			style="position: absolute; right: 20px; top: 8px;">
			<strong>${account}</strong> &nbsp;欢迎你！
			<%-- 您使用[<strong>${clientIP}</strong>]IP登录！ --%>
		</div>
		<div style="position: absolute; right: 20px; bottom: 0px;">
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
			<!-- <a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a> -->
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a>
		</div>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('metro');">metro</div>
			<div onclick="changeTheme('black');">black</div>
<!-- 			<div onclick="changeTheme('cupertino');">cupertino</div>
			<div onclick="changeTheme('dark-hive');">dark-hive</div>
			<div onclick="changeTheme('pepper-grinder');">pepper-grinder</div>
			<div onclick="changeTheme('sunny');">sunny</div> -->
		</div>
		<div id="layout_north_zxMenu" style="width: 100px; display: none;">
			<div onclick="logout();">退出登录</div>
		</div>
            <input type="hidden" name ="todd" id="todd" value="test"/>
	</div>
	<div data-options="region:'west',split:true,title:'导航菜单'"
		style="width: 200px;">
    	
		<div id="aa" class="easyui-accordion sider"
			data-options="fit:true,border:false" >
			<!--左侧菜单-->		
			<c:forEach var="item" items="${mainMenu}" varStatus="status">
				<%-- <c:if test="${item.visible == '1' }"> --%>
					<div title="${item.name}" id="${item.menuId}" data-options="iconCls:'icon-mini-add'" style="padding: 10px;">
			 			<!--树形菜单-->
			 			<ul class="easyui-tree" id="tree_${item.menuId}"  data-options="animate:true,lines:true,url:'menu/getTreeMenu.do?parentId=${item.menuId}'">
					</div>	
					  <script type="text/javascript">      
	     	  		     $("#tree_${item.menuId}").tree({
	     	         	    onSelect:function(node){    
	     	         	    	/* 菜单跳转 */
	     	         			addTab(node.text,node.attributes.menuURL);
	     	         	   }
	     	            }); 
	   	  			 </script>
   	  			<%--  </c:if> --%>
			</c:forEach>							
		</div>	
		<!--accordion-->
	</div>
	<!--west-->
	<div data-options="region:'south',border:false"
		style="height: 30px; background: #fff; padding: 5px;">
		<div id="footer">
			Copyright &copy; 2017 by 众享互动. All Rights Reserved<br>
		</div>
	</div>
	<!--//主体内容部分-->
	<div data-options="region:'center'" class="indexcenter"
		title="欢迎使用众享互动后台管理系统">
		<div id="tabs_index" name="test" class="easyui-tabs" fit="true" border="false">
			<div title="首页" style="overflow: hidden;"
				data-options="href:'pages/main/portal.html'"></div>
		</div>
	</div> 
	<!--center-->
	<!--//主体内容部分-->
	<div id="dialog_cms" data-options="iconCls:'icon-save'"></div>
	
     <script type="text/javascript">
    
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
        </script>		

</body>
</html>