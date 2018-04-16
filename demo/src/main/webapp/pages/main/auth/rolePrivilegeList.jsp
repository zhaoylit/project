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

</script>
<script type="text/javascript"> 
		$(function() {

		 	/* 新增权限 */
 		 	$("#add").bind("click",function(){
		 		$('#title').html('新增权限');
		 		$('#actionURL').val("role/addAuthorityRole.do");
		 		$('#mWindow').window('open');
			}); 

		 	/*  删除权限 */
		 /**	$("#del").bind("click",function(){		
		 		$('#title').html('删除权限');		 	
		 		$.messager.confirm('删除权限', '你确定要删除该权限吗?', function(r){
		 			if (r){
		 				var row = $('#roleList').datagrid('getSelected');
		 				$.get("role/removeAuthorityRole.do", 
		 						{ roleId: row.roleId},
		 						function(data){
		 							$.messager.alert('消息', data.message, '');	
			 						if (data.success=="true"){									
			 							$('#roleList').datagrid('reload'); 
			 						}	
		 						},
	 						"json"
		 				);

		 			}
		 		});
			});  */

		 	/*  加载页面时初始化数据表格  */
			$('#privilegeList').datagrid({
			    url:'priv/getPrivilegeList.do',
			    toolbar:'#privtb',
			    method:'post',
			    loadMsg:'数据加载中,请稍等...',
			    fitColumns:true,
			    idField:"privilegeId",
			    singleSelect:true,
			    checkOnSelect:true,
			    queryParams: {
				},
				columns:[[
					{field:'ck',width:100,checkbox:"true"},
					{field:'privilegeId',title:'权限ID',width:100,align:'center'},
					{field:'name',title:'权限名',width:100,align:'center'}
			    ]]
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
		
		function formatOperator(val, row, index) {			
			return '<button  onclick="showPrivilege('+index+')">编辑</button>';
		}
		

	</script>
	<style type="text/css">
		td { text-align: left;}
	</style>
</head>
<body>
	<div style="margin:20px 0;"></div>
	
	<div id="privtb" style="padding:5px;height:auto">
		<div style="margin-top:5px;margin-bottom:5px;">
			<div id="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加权限</div>
			<a id="del" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除权限</a>
		</div>
	</div>
	
	<!-- 数据表格 -->
	<table id="privilegeList" class="easyui-datagrid" title="权限列表" pagination="true" style="width:98%;height:500px"></table>

</body>

</html>