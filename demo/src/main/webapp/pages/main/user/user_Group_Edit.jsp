 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<style>
.datagrid-toolbar, .datagrid-pager {
	background: none;
}

.datagrid-toolbar {
	border-width: 0 0 0 0;
}
</style>
<script type="text/javascript"> 
	$(function() { 		 	
		 	/*  加载页面时初始化数据表格 */
			$('#tableListone').datagrid({
// 			title:'用户组列表',
		    url:'<%=path%>/user/userGroupsList.do',
		    toolbar:'#tb',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中.....',
		    fitColumns:true,
		    idField:"rowKey",
		    //pagination:true,
		    pageSize:10,
		    pageList:[5,10,15,20,100],//每页的个数  
		    singleSelect:false,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    sortName:'rowKey',
		    sortOrder:'desc',
		    queryParams: {
		    },
			columns:[[
				{field:'ck',width:'3%',checkbox:"true"},
				//{field:'rowKey',title:'ID',width:'3%',align:'left'},	
				{field:'usergroupsname',width:'90%',align:'left',sortable:true},	
		    ]],
		    toolbar: [
			      	    {
			      	    	text:'勾选加入',
			      	    	height:'40',
			      			iconCls: 'icon-custom_userGroup',
			      			width:'80',
			      			handler: function(){
			      				editUserInit('');
			      		    }
			      		},{
			      	    	text:'清空当前',
			      	    	height:'40',
			      			iconCls: 'icon-custom_deleteuserGroup',
			      			width:'80',
			      			handler: function(){
			      				deleteUserGroup('');
			      		    }
			      		}
			      	],
		    
			}); 
	});
	function reloadTable(){
		$('#tableList').datagrid('load', {
    		/* keyWords:$("#search").val(),
    		areaId:$("#areaId").val(),
    		activityType:$("#activityTypeSearch").val(), */
 		});
	}
	//刷新表格
	function deleteUserGroup(){
		$.messager.confirm("确认信息","确定清空该用户所拥有的用户组吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/user/updataUserGroup.do",
	        		data:{
	        			userid:${userid},
	        		},
	        		success:function(data){
	        			
	        			if(data.result == "2"){
	        				$.messager.alert('提示',"操作成功",'info', function(r){
	        					reloadTable();
	        			    });
	        			}else{
	        				$.messager.alert('操作失败',data.message,'error');
	        			}
	        		},
	        		dataType:'json'
	        	});
	        }  
	    });  
	    return false;  
	}
	
	//批量添加用户组
	function editUserInit(){
		var ids = [];
		var checkedItems = $('#tableListone').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.rowKey);
		});
		if(ids.length < 1){
			$.messager.alert('提示',"请勾选在添加",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定给该用户添加勾选中的"+ids.length+"条用户组吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/user/userAddUserGroups.do",
	        		data:{
	        			userid:${userid},
	        			ids:ids.join(",")	
	        		},
	        		success:function(data){
	        			if(data.result == "1"){
	        				$.messager.alert('提示',"操作成功",'info', function(r){
	        					reloadTable();
	        			    });
	        			}else{
	        				$.messager.alert('操作失败',data.message,'error');
	        			}
	        		},
	        		dataType:'json'
	        	});
	        }  
	    });  
	    return false;  
	}
	</script>
<table id="tableListone" style="width: 100%; margin: 0px;"></table> 