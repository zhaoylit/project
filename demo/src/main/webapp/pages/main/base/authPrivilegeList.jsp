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
		 		$('#privilegeList').datagrid('load', {
		 			name: $('#privilegeName').val()
		 		});
			}); 

		 	/* 新增权限 */
		 	$("#add").bind("click",function(){
		 		$('#title').html('新增权限');
		 		$('#privilege').form('reset');  //清除表单
		 		$('#actionURL').val("priv/addAuthorityPrivilege.do");
		 		$('#mWindow').window('open');
			}); 
		 	/* 编辑权限  */
		 	$("#edit").bind("click",function(){		
		 		$('#title').html('编辑权限');
		 		$('#actionURL').val("priv/editAuthorityPrivilege.do");
		 		var row = $('#privilegeList').datagrid('getSelected');
		 		if (row){
		 			$('#privilege').form('load',{'rowKey':row.rowKey,'name':row.name,'urlPattern':row.urlPattern,'method':row.method,'groupId':row.groupId,'intro':row.intro,'status':row.status,'createTime':formatDate(row.createTime)});
		 			$("#name").attr('readonly','readonly');
		 			$('#mWindow').window('open');
		 		}else{
		 			$.messager.alert('提示', '请选择权限！', '');	
		 		}	 		
			}); 

		 	/*  删除权限 */
		 	$("#del").bind("click",function(){		
		 		$('#title').html('删除权限');		 	
		 		$.messager.confirm('删除权限', '你确定要删除该权限吗?', function(r){
		 			if (r){
		 				var row = $('#privilegeList').datagrid('getSelected');
		 				$.get("priv/removeAuthorityPrivilege.do", 
		 						{ rowKey: row.rowKey},
		 						function(data){
		 							$.messager.alert('消息', data.message, '');	
			 						if (data.success=="true"){									
			 							$('#privilegeList').datagrid('reload'); 
			 						}	
		 						},
	 						"json"
		 				);

		 			}
		 		});
			}); 
		 	
		 	/*  加载页面时初始化数据表格 */
			$('#privilegeList').datagrid({
		    url:'priv/getPrivilegeList.do',
		    toolbar:'#tb',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"rowKey",
		    singleSelect:true,
		    checkOnSelect:true,
		    queryParams: {
		    	name: $('#privilegeName').val(),
		    	groupId: $('#privilegeGroupId').val(),
				status: $('#privilegeState').val()
			},
			columns:[[
				{field:'ck',width:100,checkbox:"true"},
				{field:'rowKey',hidden:true,title:'权限ID',width:100,align:'center'},
				{field:'name',title:'权限名',width:100,align:'center'},
				{field:'urlPattern',hidden:true,title:'匹配模式',width:100,align:'center'},
				{field:'createTime',title:'添加时间',width:100,align:'center',formatter:formatDate},
				{field:'updateTime',title:'修改时间',width:100,align:'center',formatter:formatDate} 
		    ]]
		}); 
	});
		
		function doSubmit(){
			
			$.messager.progress();
			$('#privilege').form('submit',{
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
						$('#privilegeList').datagrid('reload'); //刷新数据
						$('#mWindow').window('close'); 
						$('#privilege').form('reset');  //清除表单
						$('#rowKey').val(null);
					}						
				}
			});
			
		}
		
		function doReset(){
			
			$('#privilege').form('reset');
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
	</script>
	<style type="text/css">
		td { text-align: left;}
	</style>
</head>
<body>
    <!-- 工具栏 -->
	<div id="tb"  class="mytoolbar">
		<div class="mymenubar">
			<div id="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增权限</div>
			<div id="edit" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑权限</div>
			<a id="del" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除权限</a>
		</div>
		<div  class="mysearchbar">
		 权限名称:&nbsp;<input class="easyui-textbox" id="privilegeName" style="width:100px;">
		<div  id="search" class="easyui-linkbutton" iconCls="icon-search">查找</div>
		</div>
	</div>
	<!-- 数据表格 -->
	<table id="privilegeList" class="easyui-datagrid" title="权限管理" pagination="false" style="width:98%;height:500px"></table>
	<!-- 弹出模态窗口  -->
	<div id="mWindow" class="easyui-window" title="权限窗口" data-options="modal:true,closed:true" style="width:60%;height:60%;padding:10px;">
		<form id="privilege" method="post" >
		<input type='hidden' id="rowKey" name='rowKey'>
		<input type='hidden' id='actionURL'>
		<center>
            <table cellspacing="20" style="width:80%;height:100%;">
            	<tr><td colspans=4></td><div id="title" style="margin-top:25px;font-size:18px;font-weight:bold;"></div></tr>
            	<tr style="width:90%">
            		<td>			
						<label>权限名:</label>
					</td>
					<td>
						<input class="easyui-textbox" id="name" name="name" style="width:120px;" data-options="required:true">&nbsp;&nbsp;					  
					 </td>
					<td>			
						<label>匹配模式:</label>
					</td>
					<td>
						<input class="easyui-textbox" id="urlPattern" name="urlPattern" style="width:120px;" data-options="required:true">&nbsp;&nbsp;					  
					 </td>
			    </tr>
			</table>
			<div style="text-align:center;padding:5px">
		    	<input id="submitBtn" type="button" class="easyui-linkbutton mybtn" value="提交" onclick="doSubmit()"></input>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input id="canelBtn" type="button" class="easyui-linkbutton mybtn"  value="重置" onclick="doReset()"></input>
	   		 </div>
			</center>
		</form>
	</div>

</body>

</html>