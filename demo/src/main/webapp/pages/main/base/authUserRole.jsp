<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath()+"/pages";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String endTime = request.getParameter("endTime")!=null?request.getParameter("endTime").toString():"";
	String startTime = request.getParameter("startTime")!=null?request.getParameter("startTime").toString():"";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>" />
	<meta charset="UTF-8">
<link id="easyuiTheme" rel="stylesheet"	href="<%=path%>/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/main/portal.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/main/common.css">
<script type="text/javascript" src="<%=path%>/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/main/xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path%>/main/jeasyui.common.js"></script>
<script type="text/javascript" src="<%=path%>/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript"> 
		$(function() { 		 	
			
		 	/*  加载页面时初始化数据表格 */
			$('#roleList').datagrid({
		    url:'role/getRoleList.do',
		    toolbar:'#tb',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"roleId",
		    singleSelect:true,
		    checkOnSelect:true,
		    queryParams: {
			},
			columns:[[
				{field:'ck',width:100,checkbox:"true"},
				{field:'rowKey',hidden:true,title:'角色ID',width:100,align:'center'},
				{field:'name',title:'角色名',width:100,align:'center'},
				{field:'status',title:'角色状态',width:100,align:'center',formatter:formatStatus},	
				{field:'addedTime',title:'添加时间',width:100,align:'center',formatter:formatDate},
		    ]]
		}); 
		 	
		/* 授予角色 */
	 	$("#assign").bind("click",function(){
	 		var row = $('#roleList').datagrid('getSelected');
	 		if (row){
		        $.post("user/addUserRole.do",
		        		{"operatorId":$("#operatorId").val(),"roleId":row.rowKey,"roleName":row.name},
		        		function(data,status){
		        			var success = data.result;
		        			if("SUCCESS"==data.result){
		        				$.messager.alert('提示', data.message, '');        				
		        			}else{	        				
		        				$.messager.alert('提示',data.message, '');	
		        			}
		        		},
		        		"json"
		        );
	 		}else{
	 			$.messager.alert('提示', '请选择用户！', '');	
	 		}	 		

		}); 	
		 	
	});
	
	 	
	 	
	/*格式化时间 */
	function formatDate(val, row) {
		if (val != null) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
		+ date.getDate()+' '+date.getHours()+":"+date.getMinutes();
		}
	}
	
	function formatStatus(val, row) {
		if (val != null) {
			switch(val){
			case '1':
				return "可用";
				break;
			case "2":
				return "不可用";
				break;
			default:
				return "可用";
			}
		}
	}
	</script>
	<style type="text/css">
		td { text-align: left;}
	</style>
</head>
<body>
	 <!-- 工具栏 -->
	<div id="tb" class="mytoolbar">
		<div class="mysearchbar">
    		 <input type="hidden" id="operatorId" name="operatorId" value="${operatorId}">			
			 <div  id="goback" class="easyui-linkbutton" onclick="javascript:history.back(-1);" iconCls="icon-back">返回上一页</div>&nbsp;&nbsp;&nbsp;&nbsp;
			 <div id="assign" class="easyui-linkbutton" iconCls="icon-man" plain="true">赋予选中角色</div>
		</div>
	</div>
	
	<table id="roleList" class="easyui-datagrid" title="角色列表" pagination="false" style="padding:5px;width:98%;height:500px;"></table>
	
	</div>
</body>

</html>