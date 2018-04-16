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
		    //$("span.textbox").css("width","50px");
			/* 条件查询 */
/* 		 	$("#search").bind("click",function(){			 		
		 		$('#userList').datagrid('load', {
		 			name: $('#userName').val(),
					status: $('#userState').val()
		 		});
			}); 

	
		 	$("#add").bind("click",function(){
		 		$('#title').html('新增用户');
		 		$('#actionURL').val("user/addBackendUser.do");
		 		$('#mWindow').window('open');
			}); 

		 	$("#edit").bind("click",function(){		
		 		$('#title').html('编辑用户');
		 		$('#actionURL').val("user/editBackendUser.do");
		 		var row = $('#userList').datagrid('getSelected');
		 		if (row){
		 			$('#user').form('load',{'operatorId':row.operatorId,'name':row.name,'referenceId':row.referenceId,'referenceType':row.referenceType,'status':row.status});
		 			$("input[name='name']").attr('readonly','readonly');
		 			$('#mWindow').window('open');
		 		}else{
		 			$.messager.alert('提示', '请选择用户！', '');	
		 		}	 		
			}); 

	
		 	$("#del").bind("click",function(){		
		 		$('#title').html('删除用户');		 	
		 		$.messager.confirm('删除用户', '你确定要删除该用户吗?', function(r){
		 			if (r){
		 				var row = $('#userList').datagrid('getSelected');
		 				$.get("user/removeBackendUser.do", 
		 						{ operatorId: row.operatorId},
		 						function(data){
		 							$.messager.alert('消息', data.message, '');	
			 						if (data.success=="true"){									
			 							$('#userList').datagrid('reload'); 
			 						}	
		 						},
	 						"json"
		 				);

		 			}
		 		});
			});  */
		 	
		 	/* 授予角色 */
		 	$("#assign").bind("click",function(){
		 		var row = $('#userList').datagrid('getSelected');

		 		if(row){
		 			window.location.href="user/getUserRolePage.do?operatorId="+row.rowKey;
		 		}else
		 			$.messager.alert('提示', '请先选择用户！', '');	
			}); 
		 	
		 	/* 查看拥有的角色 */
		 	$("#view").bind("click",function(){
		 		var row = $('#userList').datagrid('getSelected');

		 		if(row){
		 			window.location.href="user/getUserOwnedRolePage.do?operatorId="+row.rowKey;
		 		}else
		 			$.messager.alert('提示', '请先选择用户！', '');	
			}); 
		 	
		 	/*  加载页面时初始化数据表格 */
			$('#userList').datagrid({
		    url:'user/userList.do',
		    toolbar:'#tb',
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"operatorId",
		    singleSelect:true,
		    checkOnSelect:true,
		    fit:true,
		    queryParams: {
		    	name: $('#userName').val(),
				status: $('#userState').val()
			},
			columns:[[
				{field:'ck',width:'5%',checkbox:"true"},
				{field:'rowKey',hidden:true,title:'ID',width:'5%',align:'left'},		
				{field:'account',title:'账户名',width:'10%',align:'left',sortable:true},
				{field:'nickName',title:'昵称',width:'8%',align:'left',sortable:true},
				{field:'realName',title:'真实姓名',width:'10%',align:'left',sortable:true},
				{field:'phone',title:'电话',width:'12%',align:'left',sortable:true},
				{field:'email',title:'邮箱',width:'12%',align:'left',sortable:true},
				{field:'weixin',title:'微信',width:'10%',align:'left',sortable:true}
		    ]]
		}); 
			
	});
		
		function doSubmit(){
			
			$.messager.progress();
			$('#user').form('submit',{
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
						$('#userList').datagrid('reload'); //刷新数据
						$('#mWindow').window('close'); 
						$('#user').form('reset');  //清除表单
						$('#operatorId').val(null);
					}						
				}
			});
			
		}
		
		function doReset(){
			
			$('#user').form('reset');
		}	
		
		/*格式化时间 */
		function formatDate(val, row) {
			if (val != null) {
			var date = new Date(val);
			return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate()+' '+date.getHours()+":"+date.getMinutes();
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
		<div class="mymenubar">
			<div id="view" class="easyui-linkbutton" iconCls="icon-large-smartart" plain="true">查看拥有的角色</div>
			<div id="assign" class="easyui-linkbutton" iconCls="icon-man" plain="true">赋予角色</div>
		</div>
		<!-- <div class="mysearchbar">
			<span>
			   用户名称：<input class="easyui-textbox" style="width:100px" id="userName" >
			</span>
			<div  id="search" class="easyui-linkbutton" iconCls="icon-search">查找</div>
		</div> -->
	
	</div>
	<!-- 数据表格 -->
	<table id="userList" class="easyui-datagrid" title="用户列表" pagination="true" ></table>
	<!-- 弹出模态窗口  -->
	<div id="mWindow" class="easyui-window " title="用户窗口" style="width:70%;height:400px;padding:10px;" data-options="modal:true,closed:true" style="">
		<form id="user" method="post" >
		<input type='hidden' id="operatorId" name='operatorId'>
		<input type='hidden' id='actionURL'>
		<center>
            <table cellspacing="20" style="width:80%;height:100%;">
            	<tr><td colspans=4></td><div id="title" style="margin-top:25px;font-size:18px;font-weight:bold;"></div></tr>
            	<tr style="width:90%">
            		<td>			
						<label>用户名：</label>
					</td>
					<td>
						<input class="easyui-textbox mytextbox" id="name" name="name"  data-options="required:true">&nbsp;
						<a href="javascript:void(0);" title="用户名只能是数字、字母和下划线的组合,且大于等于5位小于32位字符。" class="easyui-tooltip">(说明)</a>					
					 </td>
					 <td>	 
						<label>密码：</label>
					</td>				
					<td>
						<input class="easyui-textbox" id="password" name="password"/>		   		
				   		<a href="javascript:void(0);" title="密码默认为000000，请注意设置为新的密码。" class="easyui-tooltip">(说明)</a>
				    </td>
			    </tr>
			    <tr style="width:90%">
				    <td>				    
				   		<label>外部用户ID：</label>
				   	</td>
				   	<td>
				    	<input class="easyui-textbox" id="referenceId" name="referenceId" >&nbsp;
				    	<a href="javascript:void(0);" title="此用户位于其他数据库或其他系统的Id，内部用户不用填。" class="easyui-tooltip">(说明)</a>					
					</td>
					<td>
						<label>用户来源：</label><br>
					</td>
					<td>
						<input class="easyui-textbox" id="referenceType"　name="referenceType" >&nbsp;
						<a href="javascript:void(0);" title="外部用户所属系统的名称，内部用户不用填。" class="easyui-tooltip">(说明)</a>										
					</td>
				</tr>
			    <tr style="width:90%">
			    	<td>				    
				   		<label>类型：</label>
				   	</td>
				   	<td>
				    	<select class="easyui-combobox" name="type" >
							 <option value="1">后台</option>
							 <option value="2">外部</option>
						</select>
					</td>
				    <td>				    
				   		<label>状态：</label>
				   	</td>
				   	<td>
				    	<select class="easyui-combobox" name="status" >
							 <option value="1">有效</option>
							 <option value="2">禁用</option>
						</select>
					</td>
				</tr>
			</table>
			<div style="text-align:center;padding:5px">
		    	<input id="submitBtn" type="button" class="easyui-linkbutton mybtn" value="提交" onclick="doSubmit()"></input>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input id="canelBtn" type="button" class="easyui-linkbutton mybtn"  value="重置" onClick="doReset()"></input>
	   		 </div>
			</center>
		</form>
	</div>

</body>

</html>