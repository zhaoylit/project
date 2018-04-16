<%@ page language="java" import="com.zj.web.common.utils.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%
	String returnPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
%>

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
        url:'${ctx}/user/selectList.do',
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
    		{title:'',field:'imgPath',width:'5%',formatter:function(value,row,index){
				return '<img style="width:50px;height:50px;" src="<%=returnPath%>'+row.photo+'"/>';
			}},
    		{title:'用户名称',field:'userName',width:'10%'},
    		{title:'手机号码',field:'mobile',width:'10%'},
    		{title:'邮箱',field:'email',width:'12%'},
    		{title:'用户类型',field:'userTypeShow',width:'10%'},
    		{title:'所属角色',field:'roleName',width:'10%'},
    		{title:'登录时间',field:'loginTimeShow',width:'15%'},
    		{title:'上次登录时间',field:'lastLoginTimeShow',width:'15%'},
    		{title:'账号状态',field:'statusShow',width:'6%',align:'left',formatter:function(value,row,index){
    			return '<span style="color:'+row.styleShow+'">'+row.statusShow+'</span>';
    		}},
        ]],
	    onBeforeLoad:function(data){
	    	//添加搜索条件
	        if($(".searchBox").length == 0){
		        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:30px;line-height:30px;">';
		        searchTool+='用户类型：<input id="userType" style="width:150px;height:30px;">';
		        searchTool+='&nbsp;&nbsp;关键字：<input id="keyWords" style="width:150px;height:30px;">';
		        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
		        searchTool+='</div>';
		        $(".datagrid-toolbar").append(searchTool);
	        }
	    },
	    onLoadSuccess:function(data){
			 $("#userType").combobox({
				 data:[{
					 "name":"普通用户",
					 "value":"1"
				 },{
					 "name":"商家",
					 "value":"2"
				 }],
				 valueField:"value",
				 textField:"name"
			 });
			 $("#keyWords").textbox({
				 prompt:'姓名/手机/邮箱',
			 });
	    	 $('#search').linkbutton({
	        	iconCls:'icon-search', 
	         });
	    }
    });
    
})
//编辑条目
function editItemInit(type){
	var checkedItems = $('#tableList').datagrid('getSelected');
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
	$('#tableList').datagrid('load',{
		userType:$("#userType").combobox("getValue"),
		keyWords:$("#keyWords").val(),
	});
}
</script>
</head>
<body class="easyui-layout">
<table id="tableList" style="width:100%;height:100%;"></table>
</body>
</html>