<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/link.jsp"%>
<%@include file="/script.jsp"%>	
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body{
	padding:0px;
	margin:0px;
}
</style>
<script type="text/javascript">
var roleId = 0;
$(function(){
	$('#roleList').datagrid({
	    url:'<%=path%>/roleAuth/roleList.do',
	    fit:true,
	    method:'post',
	    loadMsg:'数据加载中,请稍等...',
	    fitColumns:true,
	    idField:"id",
	    pagination:true,
		pageSize:10,
		pageList:[5,10,15,20,100],//每页的个数  
	    singleSelect:false,
	    checkOnSelect:true,
	    selectOnCheck: true,
	    sortName:'rowKey',
	    sortOrder:'desc',
	    queryParams: {
	    },
	    toolbar: [
      	    {
      	    	text:'添加',
      			iconCls: 'icon-add',
      	    	height:'40',
      			width:'100',
      			handler: function(){
      				editItemInit('');
      		    }
      		},'-',{
      			text:'删除',
      			width:'100',
      	    	height:'40',
      			iconCls: 'icon-remove',
      			handler: function(){
      				deleteItem('');
      		    }
      		}
      	],
		columns:[[
			{field:'ck',width:'5%',checkbox:"true"},
			{field:'id',title:'角色id',width:'15%',align:'left'},
		    {field:'roleName',title:'角色名称',width:'20%',align:'center'},

            {field:'roleStatus',title:'角色状态',width:'20%',align:'center'},
            
			{field:'operator1',title:'操作',width:'40%',align:'center',formatter:function(value,row,index){
				return '<a title="编辑角色" class="editCls" href="javascript:editItemInit(\''+row.id+'\');"></a><a title="查看角色" class="preview" href="javascript:viewItem(\''+row.id+'\');"></a><a title="编辑角色权限" class="roleFun" href="javascript:editRoleAuthInit(\''+row.id+'\');"></a>';
			}},		
	    ]],
	    onBeforeLoad:function(data){
	    	//添加搜索条件
	        if($(".searchBox").length == 0){
		        /* var searchTool = '<div class="searchBox" style="padding:3px 0 0 15px;height:40px;line-height:30px;">';
		        searchTool+='机场:&nbsp;<input id="airportId" style="width:200px;height:30px;"></td>&nbsp;&nbsp;&nbsp;';
		        searchTool+='广告商:&nbsp;<input id="advertiserId" style="width:200px;height:30px;"></td>';
		        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
		        searchTool+='</div>';
		        $(".datagrid-toolbar").append(searchTool);
				//初始活动类型选择combobox
		        $('#search').linkbutton({
		        	iconCls:'icon-search', 
		        }); */
	        }
	    },
	    onLoadSuccess:function(data){
	    	//初始化编辑按钮
	        $('.editCls').linkbutton({text:'',plain:true,iconCls:'icon-edit'});
	        $('.preview').linkbutton({text:'',plain:true,iconCls:'icon-view'});
	        $('.roleFun').linkbutton({text:'',plain:true,iconCls:'icon-role_auth'});
	    	//初始化表格提示插件
	        $('#roleList').datagrid('doCellTip', {
	            onlyShowInterrupt : true,
	            position : 'bottom',
	            maxWidth : '200px',
	            specialShowFields : [{
	                field : 'status',
	                showField : 'statusDesc'
	            }],
	            tipStyler : {
	                'backgroundColor' : '#fff000',
	                borderColor : '#ff0000',
	                boxShadow : '1px 1px 3px #292929'
	            }
	        });
	    }
	}); 
	
	$("#expandAll").bind("click",function(){
		var checkedItems = $('#funTree').tree('getSelected');
		if(checkedItems != null){
			$('#funTree').tree('expandAll',checkedItems.target);
		}else{
			$('#funTree').tree('expandAll');
		}
	});
	$("#collapseAll").bind("click",function(){
		var checkedItems = $('#funTree').tree('getSelected');
		if(checkedItems != null){
			$('#funTree').tree('collapseAll',checkedItems.target);
		}else{
			$('#funTree').tree('collapseAll');
		}
	});
	$("#saveChange").bind("click",function(){
		var ids = new Array();
		var checkedItems = $('#funTree').tree('getChecked');
		if(checkedItems != null){
			$.each(checkedItems,function(index,item){
				if(item.isGroup == "1"){
					ids.push(item.id);
				}
			});
			if(ids.length == 0){
				$.messager.alert('提示',"请选择要更新的权限",'warning');
				return;
			}
			var formData = new FormData();
			formData.append("ids",ids.join(","));
			formData.append("roleId",roleId);
			$.ajax({
				type:'post',
				async: false,
				url:'${ctx}/roleAuth/updateRoleAuth.do',
				data:formData,
				cache: false,
				processData : false, 
				contentType : false,
				success:function(data){
					if(data.result == "1"){
        				$.messager.alert('提示',"操作成功",'info', function(r){
        					dialog.dialog('destroy');
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
	
})
function editRoleAuthInit(roleId){
	$('#funTree').tree({    
		method:'post',
	    url:'${ctx}/roleAuth/getAllAuthByRole.do',
	    checkbox:true,
	    animate:true,
	    queryParams:{
	    	roleId:roleId
	    }
	});
	this.roleId = roleId;
}
//编辑条目
function editItemInit(id){
	var dialog = $('<div></div>').dialog({
		title:id != "" ? "编辑角色" : "添加角色",
		height:'300',
		width:'480',
		top:'10%',
		left:'30%',
		minimizable:false,  
	    maximizable:false,  
	    collapsible:false,  
	    shadow: true,  
	    modal: true,  
		href:'${ctx}/roleAuth/addOrEditRoleInit.do?roleId='+id,
		onClose:function(){	
			$(this).dialog('destroy');
		},
        buttons : [
          {
            text : '提交',
            iconCls : 'icon-save',
            handler : function() {
            	//提交表单
            	var $form = $("#addOrEditForm");
            	if($form.form("validate")){
            		$.ajax({
                		type:'post',
                		url:$form.attr("action"),
                		data:$form.serialize(),
                		success:function(data){
                			if(data.result == "1"){
                				$.messager.alert('提示',"操作成功",'info', function(r){
                					dialog.dialog('destroy');
                					reloadTable();
                			    });
                			}else{
                				$.messager.alert('操作失败',data.message,'error');
                			}
                		},
                		dataType:'json'
                	});
            	}else{
            		$.messager.alert('提示',"表单数据不完整！",'error');
            	}
            }
          },
          {
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
            	dialog.dialog('destroy');
            }
          }
        ]
	});
}
function reloadTable(){
	$('#roleList').datagrid('load',{
		
	});
}
//批量删除条目
function deleteItem(){
	var ids = [];
	var checkedItems = $('#roleList').datagrid('getChecked');
	$.each(checkedItems,function(index,item){
		ids.push(item.id);
	});
	if(ids.length == 0){
		$.messager.alert('提示',"请选择删除的角色",'warning');
		return;
	}
	$.messager.confirm("确认信息","确定删除选中的"+ids.length+"个角色和赋予的权限吗？", function (r) {  
        if (r){  
        	$.ajax({
        		type:'post',
        		url:"${ctx}/roleAuth/deleteRole.do",
        		data:{
        			ids:ids.join(",")
        		},
        		success:function(data){
        			if(data.result == "1"){	
        				$.messager.alert('提示',"操作成功",'info', function(r){
        					reloadTable();
        			    });
        			}else{
        				$.messager.alert('删除失败',data.message,'error'); 
        			}
        		},
        		dataType:'json'
        	});
        }  
    });  
    return false;  
}
</script>
</head>
<body>
<div id="roleAuth" class="easyui-layout" style="width:100%;height:99.9999%;">   
    <div data-options="region:'west',title:'角色列表',split:true,iconCls:'icon-roleList'" style="width:40%;">
    	<table id="roleList" style="width:100%;margin:0px;"></table>
    </div>   
    <div class="easyui-layout" data-options="region:'center',title:'功能列表',iconCls:'icon-menu'" style="width:60%;">
    	<div data-options="region:'north'" style="height:40px;line-height:35px;padding-left:10px;">
    		<a id="expandAll" class="easyui-linkbutton" data-options="iconCls:'icon-expand',plain:false">展开</a>
    		&nbsp;&nbsp;<a id="collapseAll" class="easyui-linkbutton" data-options="iconCls:'icon-collapse',plain:false">折叠</a>
    		&nbsp;&nbsp;<a id="saveChange" class="easyui-linkbutton" data-options="iconCls:'icon-formSave',plain:false">保存更改</a>
	    </div>   
	    <div data-options="region:'center'" style="height:100%;">
	    	<ul id="funTree"></ul> 
	    </div>   
    </div>   
</div>  
</body>
</html>