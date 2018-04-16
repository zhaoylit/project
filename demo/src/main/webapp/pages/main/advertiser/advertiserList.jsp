<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style>
.datagrid-toolbar, .datagrid-pager {
    background:none;
}
.datagrid-toolbar {
    border-width: 0 0 0 0;
}
.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber
  {
      text-overflow: ellipsis;
  }
</style>
<script type="text/javascript">
	$(function() { 		 	
		 	/*  加载页面时初始化数据表格 */
			$('#tableList').datagrid({
// 			title:'广告商列表',
		    url:'<%=path%>/advertiser/advertiserList.do',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"id",
		    pagination:true,
		    pageSize:10,
		    pageList:[10,20,100],//每页的个数  
		    singleSelect:false,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    sortName:'id',
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
				{field:'advertiserName',title:'广告商名称',width:'20%',align:'center'},
				{field:'advertiserType',title:'广告商类型',width:'20%',align:'center'},
				//{field:'"resourceCount',title:'广告资源数量',width:'15%',align:'center'},
				{field:'addedTimeShow',title:'添加时间',width:'20%',align:'center'},
				{field:'operator',title:'操作',width:'20%',align:'center',formatter:function(value,row,index){
					return '<a class="editCls" href="javascript:editItemInit(\''+row.id+'\');"></a><a class="editCls2" href="javascript:editResourceItemInit(\''+row.id+'\');"></a>';
				}},		
		    ]],
		    onBeforeLoad:function(data){
		    	//添加搜索条件
		        if($(".searchBox").length == 0){
			        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:40px;line-height:30px;">';
			        searchTool+='广告商名称:&nbsp;<input id="advertiserName" style="width:150px;height:30px;"/>';
			        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
			        searchTool+='</div>';
			        $(".datagrid-toolbar").append(searchTool);
			        $("#advertiserName").textbox({});
					//初始活动类型选择combobox
			        $('#search').linkbutton({
			        	iconCls:'icon-search', 
			        });
		        }
		    },
		    onLoadSuccess:function(data){ 
		    	if(data.result=='0'){
    	    		$.messager.alert('提示',data.message,'error');
    	    		return;
    	    	}
		    	//初始化编辑按钮
		        $('.editCls').linkbutton({text:'编辑广告商',plain:true,iconCls:'icon-edit'});
		        $('.editCls2').linkbutton({text:'编辑广告商资源',plain:true,iconCls:'icon-edit'});
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
	});
	//刷新表格
	function reloadTable(){
		$('#tableList').datagrid('load', {
			advertiserName:$("#advertiserName").val(),
 		}); 
	}
	//编辑广告商信息条目
	function editItemInit(id){
		var dialog = $('<div></div>').dialog({
			title:id != "" ? "编辑广告商" : "添加广告商",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/advertiser/addOrEditAdvertiserInit.do?id='+id,
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
                	if(validateForm()){
                		$.ajax({
                    		type:'post',
                    		url:'<%=path%>/advertiser/addOrEditAdvertiser.do',
                    		data:{
                    			id:id,
                    			advertiserName:$("input[name='advertiserName']").val(),
                    			advertiserType:$("input[name='advertiserType']").val()
                    		},
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

	//编辑广告商的图片
    function editResourceItemInit(id){
		 window.open("resource/resourceManageInit.do?resourceServiceType=1&resourceServiceId="+id);
	 }
	//批量删除条目
	function deleteItem(){
		var ids = [];
		var checkedItems = $('#tableList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要删除的广告商",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/advertiser/deleteAdvertiser.do",
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
<table id="tableList" style="width:100%;margin:0px;"></table>