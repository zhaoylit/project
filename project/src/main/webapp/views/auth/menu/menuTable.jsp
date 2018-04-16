<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/link.jsp"%>
<%@include file="/script.jsp"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
    $('#menuTreeTable').treegrid({
        url:'${ctx}/menu/getMenuTable.do',
        idField:'id',
        treeField:'menuName',
        pagination:false,
	    pageSize:10,
	    pageList:[5,10,15,20,100],//每页的个数
	    singleSelect:true,
	    checkOnSelect:true,
	    selectOnCheck: true,
	    lines:true,
		iconCls: 'icon-ok',
		rownumbers: true,
		animate: true,
		collapsible: true,
		fitColumns: true,
	    toolbar: [
		      	    {
		      	    	text:'添加',
		      			iconCls: 'icon-add',
		      	    	height:'40',
		      			width:'100',
		      			handler: function(){
		      				editItemInit('add');
		      		    }
		      		},'-',{
		      	    	text:'编辑',
		      			iconCls: 'icon-edit',
		      	    	height:'40',
		      			width:'100',
		      			handler: function(){
		      				editItemInit('edit');
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
    		{title:'菜单名称',field:'menuName',width:'30%'},
    		{title:'功能名称',field:'funName',width:'20%'},
    		{title:'功能地址',field:'funUrl',width:'20%',align:'left'},
    		{title:'位置',field:'preNodeName',width:'20%',align:'left',formatter:function(value,row,index){
    			return row.preNodeName+"&nbsp;之后";
    		}},
    		{title:'排序',field:'menuOrder',width:'5%',align:'left'},
        ]],
	    onBeforeLoad:function(data){
	    	//添加搜索条件
	        if($(".searchBox").length == 0){
		        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:30px;line-height:30px;">';
		        searchTool+='<a id="expandAll" class="easyui-linkbutton"></a>';
		        searchTool+='&nbsp;&nbsp;&nbsp;<a id="collapseAll" class="easyui-linkbutton"></a>';
// 		        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
		        searchTool+='</div>';
		        $(".datagrid-toolbar").append(searchTool);
	        }
	    },
	    onLoadSuccess:function(data){
	    	 $('#expandAll').linkbutton({text:'展开',plain:false,iconCls:'icon-expand'});
	    	 $('#collapseAll').linkbutton({text:'折叠',plain:false,iconCls:'icon-collapse'});
	    }
    });
    
    $("#expandAll").bind("click",function(){
    	var checkedItems = $('#menuTreeTable').treegrid('getSelected');
    	if(checkedItems != null){
    		var id  = checkedItems.id;
    		$('#menuTreeTable').treegrid('expandAll',id);
    	}else{
    		$('#menuTreeTable').treegrid('expandAll');
    	}
	});
    $("#collapseAll").bind("click",function(){
    	var checkedItems = $('#menuTreeTable').treegrid('getSelected');
    	if(checkedItems != null){
    		var id  = checkedItems.id;
    		$('#menuTreeTable').treegrid('collapseAll',id);
    	}else{
    		$('#menuTreeTable').treegrid('collapseAll');
    	}
	});
})
//编辑条目
function editItemInit(type){
	var checkedItems = $('#menuTreeTable').treegrid('getSelected');
	var selectId = "",selectPid = "";
	if(type == "edit"){
		if(checkedItems != null){
			selectId = checkedItems.id;
			selectPid = checkedItems.pid;
		}else{
			$.messager.alert('提示',"请选择要编辑的功能",'warning');
			return;
		}
	}
	var dialog = $('<div></div>').dialog({
		title:type != "add" ? "编辑功能" : "添加功能",
		height:'400',
		width:'480',
		top:'10%',
		left:'30%',
		minimizable:false,  
	    maximizable:false,  
	    collapsible:false,  
	    shadow: true,  
	    modal: true,  
		href:'${ctx}/menu/addOrEditMenuInit.do?id='+selectId+'&pid='+selectPid,
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
                		url:'${ctx}/menu/addOrEditMenu.do',
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
//批量删除条目
function deleteItem(){
	var checkedItems = $('#menuTreeTable').treegrid('getSelected');
	
	if(checkedItems == null || checkedItems.length == 0){
		$.messager.alert('提示',"请选择要删除的功能",'warning');
		return;
	}
	$.messager.confirm("确认信息","确定删除当前节点和下面的所有子节点吗？", function (r) {  
        if (r){  
        	$.ajax({
        		type:'post',
        		url:"${ctx}/menu/deleteAllChildrenNodebyId.do",
        		data:{
        			ids:checkedItems.id
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
function reloadTable(){
	$('#menuTreeTable').treegrid('load',{});
}
</script>
</head>
<body class="easyui-layout">
<table id="menuTreeTable" style="width:100%;height:100%;"></table>
</body>
</html>