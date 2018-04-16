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
		 		$('#departmentList').datagrid('load', {
		 			name: $('#departmentName').val(),
					status: $('#departmentState').val()
		 		});
			}); 

		 	/* 新增部门 */
		 	$("#add").bind("click",function(){
		 		$('#title').html('新增部门');
		 		$('#actionURL').val("dept/addDepartment.do");
		 		$('#mWindow').window('open');
			}); 
		 	/* 编辑部门  */
		 	$("#edit").bind("click",function(){		
		 		$('#title').html('编辑部门');
		 		$('#actionURL').val("dept/editDepartment.do");
		 		var row = $('#departmentList').datagrid('getSelected');
		 		if (row){
		 			$('#department').form('load',{'departmentId':row.departmentId,'name':row.name,'parentId':row.parentId,'leader':row.leader,'departmentPhone':row.departmentPhone});
		 			$("input[name='name']").attr('readonly','readonly');
		 			$('#mWindow').window('open');
		 		}else{
		 			$.messager.alert('提示', '请选择部门！', '');	
		 		}	 		
			}); 

		 	/*  删除部门 */
		 	$("#del").bind("click",function(){		
		 		$('#title').html('删除部门');		 	
		 		$.messager.confirm('删除部门', '你确定要删除该部门吗?', function(r){
		 			if (r){
		 				var row = $('#departmentList').datagrid('getSelected');
		 				$.get("dept/removeDepartment.do", 
		 						{ departmentId: row.departmentId},
		 						function(data){
		 							$.messager.alert('消息', data.message, '');	
			 						if (data.success=="true"){									
			 							$('#departmentList').datagrid('reload'); 
			 						}	
		 						},
	 						"json"
		 				);

		 			}
		 		});
			}); 

		 	/*  加载页面时初始化数据表格 */
			$('#departmentList').datagrid({
		    url:'dept/getDepartmentList.do',
		    toolbar:'#tb',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"departmentId",
		    singleSelect:true,
		    checkOnSelect:true,
		    queryParams: {
		    	name: $('#departmentName').val(),
				status: $('#departmentState').val()
			},
			columns:[[
				{field:'ck',width:100,checkbox:"true"},
				{field:'departmentId',title:'部门ID',width:100,align:'center'},
				{field:'name',title:'部门名称',width:100,align:'center'},
				{field:'leader',title:'部门领导',width:100,align:'center'},
				{field:'parentId',title:'上级部门',width:100,align:'center'},			
				{field:'departmentPhone',title:'部门电话',width:100,align:'center'},
				{field:'status',title:'部门状态',width:100,align:'center',formatter:formatStatus},
				{field:'addedTime',title:'添加时间',width:100,align:'center',formatter:formatDate}
				
		    ]]
		}); 
			
	});
		
		function doSubmit(){
			
			$.messager.progress();
			$('#department').form('submit',{
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
						$('#departmentList').datagrid('reload'); //刷新数据
						$('#mWindow').window('close'); 
						$('#department').form('reset');  //清除表单
						$('#departmentId').val(null);
					}						
				}
			});
			
		}
		
		function doReset(){
			
			$('#department').form('reset');
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
					return "有效";
					break;
				case "2":
					return "禁用";
					break;
				default:
					return "有效";
				}
			}
		}		
		
	</script>
	<style type="text/css">
		td { text-align: left;}
	</style>
</head>
<body>
    <!-- 工具栏栏 -->
	<div id="tb"  class="mytoolbar">
		<div class="mymenubar">
			<div id="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增部门</div>
			<div id="edit" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑部门</div>
			<a id="del" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除部门</a>
		</div>
		<div class="mysearchbar">
		部门名称:&nbsp;<input class="easyui-textbox" id="departmentName" style="width:100px;">
		部门状态:&nbsp;<select class="easyui-combobox" id="departmentState" style="width:100px;">
					 <option value="1">有效</option>
					 <option value="2">禁用</option>
				</select>
		<div  id="search" class="easyui-linkbutton" iconCls="icon-search">查找</div>
		</div>
	</div>
	<!-- 数据表格 -->
	<table id="departmentList" class="easyui-datagrid" title="部门列表" pagination="true" style="width:98%;height:500px"></table>
	<!-- 弹出模态窗口  -->
	<div id="mWindow" class="easyui-window" title="部门窗口" data-options="modal:true,closed:true" style="width:60%;height:50%;padding:10px;">
		<form id="department" method="post" >
		<input type='hidden' id="departmentId" name='departmentId'>
		<input type='hidden' id='actionURL'>
		<center>
            <table cellspacing="20" style="width:80%;height:100%;">
            	<tr><td colspans=4></td><div id="title" style="margin-top:25px;font-size:18px;font-weight:bold;"></div></tr>
            	<tr style="width:90%">
            		<td>			
						<label>部门名称:</label>
					</td>
					<td>
						<input class="easyui-textbox" id="name" name="name" style="width:120px;" data-options="required:true">&nbsp;&nbsp;
					 </td>
					 <td>	 
						<label>部门领导:</label>
					</td>				
					<td>
						<input class="easyui-textbox" id="leader" name="leader" style="width:120px;" />		   		
				    </td>
			    </tr>
			    <tr style="width:90%">
				    <td>				    
				   		<label>上级部门:</label>
				   	</td>
				   	<td>
				    	<input class="easyui-textbox" id="parentId" name="parentId" style="width:120px;">&nbsp;&nbsp;					
					</td>				
					 <td>				    
				   		<label>部门电话:</label>
				   	</td>
				   	<td>
						<input class="easyui-textbox" id="departmentPhone"　name="departmentPhone" style="width:120px;">
					</td>
				</tr>
			</table>
			<div style="text-align:center;padding:5px">
		    	<input id="submitBtn" type="button" class="easyui-linkbutton mybtn"  value="提交" onclick="doSubmit()"></input>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input id="canelBtn" type="button" class="easyui-linkbutton mybtn"  value="重置" onClick="doReset()"></input>
	   		 </div>
			</center>
		</form>
	</div>

</body>

</html>