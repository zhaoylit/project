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
        url:'${ctx}/shop/selectList.do',
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
    		{title:'用户',field:'shopName',width:'10%'},
    		{title:'商家名称',field:'shopName',width:'10%'},
    		{title:'手机号码',field:'mobile',width:'10%'},
    		{title:'电话',field:'phone',width:'10%'},
    		{title:'地址',field:'address',width:'20%'},
    		{title:'添加时间',field:'addedTimeShow',width:'15%'},
        ]],
	    onBeforeLoad:function(data){
	    	//添加搜索条件
	        if($(".searchBox").length == 0){
		        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:30px;line-height:30px;">';
		        searchTool+='商家名称：<input id="shopName"  style="width:150px;height:30px;">';
		        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
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
	    	 $("#shopName").textbox({
	    		 prompt: "输入关键字"
	    	 });
	    	//初始化表格提示插件
	        $('#tableList').datagrid('doCellTip', {
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
		left:'20%',
		minimizable:false,  
	    maximizable:false,  
	    collapsible:false,  
	    shadow: true,  
	    modal: true,  
		href:'${ctx}/shop/addOrEditInit.do?id='+selectId,
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
                		url:'${ctx}/shop/addOrEdit.do',
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
        		url:"${ctx}/shop/deleteByPrimaryKey.do",
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
</script>
</head>
<body class="easyui-layout">
<table id="tableList" style="width:100%;height:100%;"></table>
</body>
</html>