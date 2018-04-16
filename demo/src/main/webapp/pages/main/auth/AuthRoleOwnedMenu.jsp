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
			
		 	$("#save").bind("click",function(){	 		
		 		var nodes = $('#menuTree').tree('getChecked');
	                var s = '';
	                for (var i = 0; i < nodes.length; i++) {
	                    if (s != '')
	                        s += ',';
	                    s += nodes[i].attributes.menuId;
	                }
		 	   if(nodes.length>0){		 		   
	 				$.get("menu/saveRoleMenu.do", 
	 						{ menuIds:s,roleId:$("#roleId").val()},
	 						function(data){
	 							$.messager.alert('消息', data.message, '');	
	 						},
 						"json"
	 				);	 				
		 		} 	
			}); 
		 	 
	});
		
		function doSubmit(){
			
			$.messager.progress();
			$('#menu').form('submit',{
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
				//	var res = eval('(' + data + ')'); 
					var res = $.parseJSON(data);
					$.messager.alert('消息', res.message, '');	
					$('#mWindow').window('close'); 
					var node = $('#menuTree').tree('getSelected');
					if (res.success=="true"){
						if('addChild'== $('#actionType').val()){//添加子菜单				
							if (node){
								$('#menuTree').tree('append', {
									parent: node.target,
									data: res.menu
								});
							}	
						}else if('addSibling'== $('#actionType').val()){ //添加兄弟菜单
							if (node){
								$('#menuTree').tree('insert', {
									after: node.target,
									data: res.menu
								});
								$('#menuTree').tree('expand',node.target);
							}						
						}else if('editMenu'== $('#actionType').val()){
							if (node){
								$('#menuTree').tree('update', {
									target: node.target,
									text: res.menu.text
								});
							}						
						}				
						$('#menu').form('reset');  //清除表单
						$('#menuId').val(null);
						if(res.menu.attributes.parentId==0){				
							parent.location.reload(); 			
						}
					}						
				}
			});
			
		}
		
		function doReset(){
			
			$('#menu').form('reset');
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
    		 <input type="hidden" id="roleId" name="roleId" value="${roleId}">			
			 <div  id="goback" class="easyui-linkbutton" onclick="javascript:history.back(-1);" iconCls="icon-back"  plain="true">返回上一页</div>
			 <div id="save" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存选中</div>
		</div>
	</div>
	<!-- 菜单编辑 -->
        <div class="easyui-layout" style="width:100%;height:500px;">
			<div id="p" data-options="region:'west',split:true,checkbox:true" style="width:25%;padding:10px">
				 <ul class="easyui-tree" id="menuTree"  data-options="lines:true,animate:true,checkbox:true,onlyLeafCheck:false,url:'menu/getRoleTreeMenu.do?parentId=0&roleId=${roleId}'"></ul>
			</div>
			<div data-options="region:'center'" title="">
			</div>
		</div>

		  <script type="text/javascript"> 

 	  		     $("#menuTree").tree({
 	  		    	onDblClick:function(node){  
 	  		    		$('#menuTree').tree('toggle',node.target);
 	         	   }
 	            }); 
		
		 </script>
<%-- 	</c:forEach>  --%>
	
<!-- 弹出模态窗口  -->
	<div id="mWindow" class="easyui-window" title="菜单窗口" data-options="modal:true,closed:true" style="width:60%;height:50%;padding:10px;">
		<form id="menu" method="post" >
		<input type='hidden' id="menuId" name='menuId'>
		<input type='hidden' id="roleId" name='"roleId"'>
		<input type='hidden' id="parentId" name='parentId'>
		<input type='hidden' id='actionURL'>
		<input type='hidden' id='actionType'>
		<center>
            <table cellspacing="20" style="width:80%;height:100%;">
            	<tr><td colspans=4></td><div id="title" style="margin-top:25px;font-size:18px;font-weight:bold;"></div></tr>
            	<tr style="width:90%">
            		<td>			
						<label>菜单名:</label>
					</td>
					<td>
						<input class="easyui-textbox" name="name" style="width:120px;" data-options="required:true">&nbsp;&nbsp;					  
					 </td>
					 <td>	 
						<label>菜单URL:</label>
					</td>
					<td>
						<input class="easyui-textbox" name="menuURL" style="width:120px;" />			   		
				    </td>
			    </tr>
			    <tr style="width:90%">
				    <td>				    
				   		<label>状态:</label>
				   	</td>
				   	<td>
				    	<select class="easyui-combobox" name="visible" style="width:120px;">
							 <option value="1">启用</option>
							 <option value="2">禁用</option>
						</select>
					</td>
					<td>
						<label>排序:</label><br>
					</td>
					<td>
						<input class="easyui-textbox" name="index" style="width:120px;">
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