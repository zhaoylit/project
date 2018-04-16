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
		$(function() {

			/* 条件查询 */
		 	$("#search").bind("click",function(){			 		
		 		$('#roleList').datagrid('load', {
		 			name: $('#roleName').val(),
					status: $('#roleState').val()
		 		});
			}); 

		 	/* 新增角色 */
		 	$("#add").bind("click",function(){
		 		$('#title').html('新增角色');
		 		$('#actionURL').val("role/addAuthorityRole.do");
		 		$('#mWindow').window('open');
			}); 
		 	/* 编辑角色  */
		 	$("#edit").bind("click",function(){		
		 		$('#title').html('编辑角色');
		 		$('#actionURL').val('role/editAuthorityRole.do');
		 		var row = $('#roleList').datagrid('getSelected');
		 		if (row){
		 			$('#role').form('load',{'roleId':row.roleId,'name':row.name,'status':row.status,'addedTime':formatDate(row.addedTime)});
		 			$("input[name='name']").attr('readonly','readonly');
		 			$('#mWindow').window('open');
		 		}else{
		 			$.messager.alert('提示', '请选择角色！', '');	
		 		}	 		
			}); 

		 	/*  删除角色 */
		 	$("#del").bind("click",function(){		
		 		$('#title').html('删除角色');		 	
		 		$.messager.confirm('删除角色', '你确定要删除该角色吗?', function(r){
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
			}); 
		 	
		 	/* 授予权限 */
		 	$("#assign").bind("click",function(){
		 		var row = $('#roleList').datagrid('getSelected');

		 		if(row){
		 			window.location.href="role/getRolePrivilegePage.do?roleId="+row.roleId;
		 		}else
		 			$.messager.alert('提示', '请先选择角色！', '');	
			}); 
		 	
		 	/* 查看拥有的权限 */
		 	$("#view").bind("click",function(){
		 		var row = $('#roleList').datagrid('getSelected');

		 		if(row){
		 			window.location.href="role/getRoleOwnedPrivilegePage.do?roleId="+row.roleId;
		 		}else
		 			$.messager.alert('提示', '请先选择角色！', '');	
			}); 
		 	

		 	/* 授予菜单 */
		 	$("#assignMenu").bind("click",function(){
		 		var row = $('#roleList').datagrid('getSelected');

		 		if(row){
		 			window.location.href="role/getRoleOwnedMenuPage.do?roleId="+row.roleId;
		 		}else
		 			$.messager.alert('提示', '请先选择角色！', '');	
			}); 
		 	
		 	/* 查看拥有的菜单 */
		 	$("#viewMenu").bind("click",function(){
		 		var row = $('#roleList').datagrid('getSelected');

		 		if(row){
		 			window.location.href="user/getUserOwnedRolePage.do?operatorId="+row.operatorId;
		 		}else
		 			$.messager.alert('提示', '请先选择角色！', '');	
			}); 
		 	
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
		    	name: $('#roleName').val(),
				status: $('#roleState').val()
			},
			columns:[[
				{field:'ck',width:100,checkbox:"true"},
				{field:'roleId',title:'角色ID',width:100,align:'center'},
				{field:'name',title:'角色名',width:100,align:'center'},
				{field:'status',title:'角色状态',width:100,align:'center',formatter:formatStatus},	
				{field:'addedTime',title:'添加时间',width:100,align:'center',formatter:formatDate},
		/* 		{field:'privilege',title:'拥有的权限',width:100,align:'center',formatter:formatOperator} */
		    ]]
		}); 
	});
		
		function doSubmit(){
			
			$.messager.progress();
			$('#role').form('submit',{
				url:$('#actionURL').val(),
				//提交前校验，返回false终止提交
				onSubmit: function(){
					var isValid = $(this).form('validate');
					if (!isValid){
						$.messager.progress('close');	
					}
					return isValid;	
				},
				success:function(data){
					$.messager.progress('close');
					var data = eval('(' + data + ')'); 
					$.messager.alert('消息', data.message, '');	
					if (data.success=="true"){									
						$('#roleList').datagrid('reload'); //刷新数据
						$('#mWindow').window('close'); 
						$('#role').form('reset');  //清除表单
						$('#roleId').val(null);
					}						
				}
			});
			
		}
		
		function doReset(){
			
			$('#role').form('reset');
		}	
		
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
    <!-- 工具栏 -->
	<div id="tb"  class="mytoolbar">
		<div class="mymenubar">
			<div id="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增角色</div>
			<div id="edit" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑角色</div>
			<a id="del" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除角色</a>
			<div id="view" class="easyui-linkbutton" iconCls="icon-large-smartart" plain="true">查看拥有的权限</div>
			<div id="assign" class="easyui-linkbutton" iconCls="icon-man" plain="true">赋予权限</div>
			<div id="assignMenu" class="easyui-linkbutton" iconCls="icon-man" plain="true">赋予菜单</div>
		</div>
		<div  class="mysearchbar">
		角色名称:&nbsp;<input class="easyui-textbox" id="roleName" style="width:100px;">
		角色状态:&nbsp;<select class="easyui-combobox" id="roleState" style="width:100px;">
					 <option value="1">可用</option>
					 <option value="2">不可用</option>
				</select>
		<div  id="search" class="easyui-linkbutton" iconCls="icon-search">查找</div>
		</div>
	</div>
	
	<!-- 数据表格 -->
	<table id="roleList" class="easyui-datagrid" title="角色列表" pagination="true" style="width:98%;height:500px"></table>
	<!-- 弹出模态窗口  -->
	<div id="mWindow" class="easyui-window" title="角色窗口" data-options="modal:true,closed:true" style="width:60%;height:50%;padding:10px;">
		<form id="role" method="post" >
		<input type='hidden' id="roleId" name='roleId'>
		<input type='hidden' id='actionURL'>
		<center>
            <table cellspacing="20" style="width:80%;height:100%;">
            	<tr><td colspans=4></td><div id="title" style="margin-top:25px;font-size:18px;font-weight:bold;"></div></tr>
            	<tr style="width:90%">
            		<td>			
						<label>角色名:</label>
					</td>
					<td>
						<input class="easyui-textbox" name="name" style="width:120px;height:24px" data-options="required:true">&nbsp;&nbsp;					  
					 </td>
					 <td>	 
						<label>别名:</label>
					</td>
					<td>
						<input class="easyui-textbox" name="alias" style="width:120px;height:26px;" />			   		
				    </td>
			    </tr>
			    <tr style="width:90%">
				    <td>				    
				   		<label>状态:</label>
				   	</td>
				   	<td>
				    	<select class="easyui-combobox" name="status" style="width:120px;height:26px">
							 <option value="1">可用</option>
							 <option value="2">不可用</option>
						</select>
					</td>
					<td>
						<label>创建时间:</label><br>
					</td>
					<td>
						<input class="easyui-textbox" name="addedTime" style="width:120px;height:26px;" readonly="readonly">
					</td>
				</tr>
			</table>
			<div style="text-align:center;padding:5px">
		    	<input id="submitBtn" type="button" class="easyui-linkbutton" style="width:60px;height:26px;" value="提交" onclick="doSubmit()"></input>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input id="canelBtn" type="button" class="easyui-linkbutton" style="width:60px;height:26px;" value="重置" onClick="doReset()"></input>
	   		 </div>
			</center>
		</form>
	</div>

</body>

</html>