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
    $('#iconList').datagrid({
        url:'${ctx}/resource/iconList.do',
        idField:'id',
        treeField:'funName',
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
    		{title:'',field:'imgPath',width:'10%',formatter:function(value,row,index){
				return '<img src="'+row.url+'"/>';
			}},
    		{title:'图标名称',field:'iconName',width:'25%'},
    		{title:'className',field:'className',width:'25%',align:'left'},
    		{title:'图标文件名称',field:'fileName',width:'25%',align:'left'},
        ]],
	    onBeforeLoad:function(data){
	    	//添加搜索条件
	        if($(".searchBox").length == 0){
		        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:30px;line-height:30px;">';
		        searchTool+='<input id="keyWords" style="width:150px;height:30px;">';
		        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
		        searchTool+='</div>';
		        $(".datagrid-toolbar").append(searchTool);
	        }
	    },
	    onLoadSuccess:function(data){
// 	    	 $('#expandAll').linkbutton({text:'展开',plain:false,iconCls:'icon-edit'});
// 	    	 $('#collapseAll').linkbutton({text:'折叠',plain:false,iconCls:'icon-edit'});
			 $("#keyWords").textbox({
				 prompt:'请输入搜索关键字',
			 });
	    	 $('#search').linkbutton({
	        	iconCls:'icon-search', 
	         });
	    }
    });
    
    $("#expandAll").bind("click",function(){
    	var checkedItems = $('#iconList').datagrid('getSelected');
    	if(checkedItems != null){
    		var id  = checkedItems.id;
    		$('#iconList').datagrid('expandAll',id);
    	}else{
    		$('#iconList').datagrid('expandAll');
    	}
	});
    $("#collapseAll").bind("click",function(){
    	var checkedItems = $('#iconList').datagrid('getSelected');
    	if(checkedItems != null){
    		var id  = checkedItems.id;
    		$('#iconList').datagrid('collapseAll',id);
    	}else{
    		$('#iconList').datagrid('collapseAll');
    	}
	});
})
//编辑条目
function editItemInit(type){
	var checkedItems = $('#iconList').datagrid('getSelected');
	var selectId = "";
	if(type == "edit"){
		if(checkedItems != null){
			selectId = checkedItems.id;
		}else{
			$.messager.alert('提示',"请选择要编辑的功能",'warning');
			return;
		}
	}
	var dialog = $('<div></div>').dialog({
		title:type != "add" ? "编辑图标" : "添加图标",
		height:'350',
		width:'480',
		top:'10%',
		left:'30%',
		minimizable:false,  
	    maximizable:false,  
	    collapsible:false,  
	    shadow: true,  
	    modal: true,  
		href:'${ctx}/resource/addOrEditIconInit.do?id='+selectId,
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
                		url:'${ctx}/resource/addOrEditIcon.do',
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
	var checkedItems = $('#iconList').datagrid('getChecked');
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
        		url:"${ctx}/resource/deleteIcon.do",
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
	$('#iconList').datagrid('load',{});
}
</script>
</head>
<body class="easyui-layout">
<table id="iconList" style="width:100%;height:100%;"></table>
</body>
</html>