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
    $('#tableList').datagrid({
        url:'${ctx}/iconFun/selectList.do',
        idField:'id',
        pagination:true,
	    pageSize:10,
	    pageList:[5,10,15,20,100],//每页的个数
	    singleSelect:false,
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
    		{title:'',field:'imgPath',width:'15%',formatter:function(value,row,index){
				return '<img style="width:120px;height:50px;" src="'+row.imageUrl+'"/>';
				
			}},
    		{title:'名称',field:'title',width:'20%'},
    		{title:'跳转地址',field:'clickUrl',width:'20%',formatter:function(value,row,index){
    			return '<a href="javascript:void();" onclick="toHrefUrl(\''+row.clickUrl+'\')">'+row.clickUrl+'</a>';
    		}},
    		{title:'顺序',field:'order',width:'20%'},
    		{title:'状态',field:'statusShow',width:'20%',align:'left',formatter:function(value,row,index){
    			return '<span style="color:'+(row.status == '1' ? "green" : "red")+'">'+row.statusShow+'</span>';
    		}},
        ]],
	    onBeforeLoad:function(data){
	    	//添加搜索条件
	        if($(".searchBox").length == 0){
		        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:30px;line-height:30px;">';
// 		        searchTool+='平台：<input id="platform" style="width:150px;height:30px;">';
		        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
		        searchTool+='&nbsp;&nbsp;<a id="sort" onclick="sortTable()" style="width:80px;height:30px;">排序</a> ';
		        searchTool+='</div>';
		        $(".datagrid-toolbar").append(searchTool);
	        }
	    },
	    onLoadSuccess:function(data){
	    	 $('#search').linkbutton({
	        	iconCls:'icon-search', 
	         });
	    	 $('#sort').linkbutton({
	        	iconCls:'icon-sort', 
	         });
	    }
    });
    $("#expandAll").bind("click",function(){
    	var checkedItems = $('#tableList').datagrid('getSelected');
    	if(checkedItems != null){
    		var id  = checkedItems.id;
    		$('#tableList').datagrid('expandAll',id);
    	}else{
    		$('#tableList').datagrid('expandAll');
    	}
	});
    $("#collapseAll").bind("click",function(){
    	var checkedItems = $('#tableList').datagrid('getSelected');
    	if(checkedItems != null){
    		var id  = checkedItems.id;
    		$('#tableList').datagrid('collapseAll',id);
    	}else{
    		$('#tableList').datagrid('collapseAll');
    	}
	});
})

    
function toHrefUrl(clickUrl){
	window.open(clickUrl);
}
//编辑条目
function editItemInit(type){
	var checkedItems = $('#tableList').datagrid('getSelected');
	var selectId = "";
	if(type == "edit"){
		if(checkedItems != null){
			//判断是否选中了 多行
			var selections = $('#tableList').datagrid("getSelections");
		    if(selections.length == 0){
		        return;
		    }
		    if(selections.length > 1){
		    	$.messager.alert('提示',"只能选中一行进行编辑",'warning');
	            return;
		    }
			selectId = checkedItems.id;
		}else{
			$.messager.alert('提示',"请选择要编辑的功能",'warning');
			return;
		}
	}
	var dialog = $('<div></div>').dialog({
		title:type != "add" ? "编辑" : "添加",
		height:'400',
		width:'480',
		top:'5%',
		left:'30%',
		minimizable:false,  
	    maximizable:false,  
	    collapsible:false,  
	    shadow: true,  
	    modal: true,  
		href:'${ctx}/iconFun/addOrEditInit.do?id='+selectId,
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
                		url:'${ctx}/iconFun/addOrEdit.do',
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
	var ids = [];
	var checkedItems = $('#tableList').datagrid('getChecked');
	$.each(checkedItems,function(index,item){
		ids.push(item.id);
	});
	if(ids.length == 0){
		$.messager.alert('提示',"请选择要删除的图标",'warning');
		return;
	}
	$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
        if (r){  
        	$.ajax({
        		type:'post',
        		url:"${ctx}/iconFun/deleteByPrimaryKey.do",
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
function reloadTable(){
	$('#tableList').datagrid('load',{
	});
}
function sortTable(){
	$.messager.confirm("确认信息","对当前搜索结果进行排序？", function (r) {  
        if (r){  
        	var sortDialog = $('<div></div>').dialog({
        		title:"拖拽排序",
        		height:'400',
        		width:'500',
        		top:'5%',
        		left:'20%',
        		minimizable:false,  
        	    maximizable:false,  
        	    collapsible:false,  
        	    shadow: true,  
        	    modal: true,  
        		href:'${ctx}/iconFun/sortInit.do',
        		onClose:function(){	
        			$(this).dialog('destroy');
        		},
                buttons : [
				{
				    text : '提交',
				    iconCls : 'icon-save',
				    handler : function() {
				    	//提交表单
				    	var ids = $("#orderIds").val();
				    	if(ids == null || ids == ""){
				    		$.messager.alert('提示',"排序无更改，不需要保存",'warning');
				    		return;
				    	}else{
				    		$.messager.progress({
					 	   		 title:'Please waiting',
					 	   		 msg:'操作中，请稍后...'
					    	});
				    		$.ajax({
				        		type:'post',
				        		url:'${ctx}/iconFun/updateSort.do',
				        		data:{
				        			ids: ids
				        		},
				        		success:function(data){
									$.messager.progress('close');
				        			if(data.result == "1"){
				        				$.messager.alert('提示',"操作成功",'info', function(r){
				        					sortDialog.dialog('destroy');
				        					reloadTable();
				        			    });
				        			}else{
				        				$.messager.alert('操作失败',data.message,'error');
				        			}
				        		},
				        		error:function(){
									$.messager.progress('close');
				        		},
				        		dataType:'json'
				        	});
				    	}
				    }
				  },        
                  {
                    text : '关闭',
                    iconCls : 'icon-cancel',
                    handler : function() {
                    	sortDialog.dialog('destroy');
                    }
                  }
                ]
        	});
        }  
    });  
}
</script>
</head>
<body class="easyui-layout">
<table id="tableList" style="width:100%;height:100%;"></table>
</body>
</html>