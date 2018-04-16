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
			$('#tableGroup').datagrid({
// 			title:'用户列表',
		    url:'<%=path%>/user/userGroupsList.do',
		    toolbar:'#tb',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"rowKey",
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
	      	    	text:'添加用户组',
	      	    	height:'40',
	      			iconCls: 'icon-custom_addusergroup',
	      			width:'100',
	      			handler: function(){
	      				editItemInit('');
	      		    }
	      		},'-',{
	      			text:'删除用户组',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-custom_deleteuserGroup',
	      			handler: function(){
	      				deleteItem('');
	      		    }
	      		},'-',{
	      			text:'刷新列表',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-custom_refresh',
	      			handler: function(){
	      				reloadTable('');
	      		    }
	      		}
	      		
	      	],
			columns:[[
				{field:'ck',width:'5%',checkbox:"true"},
				{field:'rowKey',title:'ID',width:'5%',align:'left',hidden:'true'},		
				{field:'usergroupsname',title:'用户组名称',width:'10%',align:'left',sortable:true},
				{field:'operator',title:'操作',width:'7%',align:'left',formatter:function(value,row,index){
					return '<a class="editCls" href="javascript:editItemInit(\''+row.rowKey+'\');"></a>'
					;
				}},
		    ]],
		  /*   onBeforeLoad:function(data){
		    },  */
		     onLoadSuccess:function(data){ 
		    	 if(data.result=='0'){
	    	    		$.messager.alert('提示',data.message,'error');
	    	    		return;
	    	    	}
		    	 if(data.preRowKey != ""){
				    	preRowKey = data.preRowKey;
				    	nextRowKey = data.nextRowKey;
			    	}else{
			    		preRowKey = nextRowKey;
			    	}
		    	//初始化编辑按钮
		        $('.editCls').linkbutton({text:'编辑',plain:true,iconCls:'icon-custom_edit'});
		    	//初始化表格提示插件
		        $('#tableGroup').datagrid('doCellTip', {
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
		        var rows = $('#tableGroup').datagrid('getRows');
	        	var lastRow = $('#tableGroup').datagrid('getData').rows[rows.length - 1];
	        	if($("#lastRowKey").val() != ""){
	        		var new_obj = $("<input type='hidden' id='lastRowKey' value='"+lastRow.city+"'>");
	        		$("body").append(new_obj);
	        	}else{
	        		$("#lastRowKey").val(lastRow.city);
	        	}
		    }
		}); 
			initPage($('#tableGroup'));
	}); 
	//刷新表格
	function reloadTable(){
		$('#tableGroup').datagrid('load', {
    		/* keyWords:$("#search").val(),
    		areaId:$("#areaId").val(),
    		activityType:$("#activityTypeSearch").val(), */
 		});
	}
	//编辑条目
	function editItemInit(id){
		var dialog = $('<div></div>').dialog({
			title:id != "" ? "编辑用户组" : "添加用户组",
			height:'25%',
			width:'30%',
			top:'35%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/user/addOrEditGroupsInit.do?id='+id,
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
                	if($form.form('validate')){
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
                		$.messager.alert('提示',"表单数据不完整",'error');
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
		var checkedItems = $('#tableGroup').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.rowKey);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要删除的用户组",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/user/deleteUserGroups.do",
	        		data:{
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
<table id="tableGroup" style="width: 100%; margin: 0px;"></table>