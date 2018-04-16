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
// 			title:'广告列表',
		    url:'<%=path%>/advert/advertList.do',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"rowKey",
		    pagination:true,
// 		    pageSize:10,
// 		    pageList:[5,10,15,20,100],//每页的个数  
		    singleSelect:false,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    sortName:'rowKey',
		    sortOrder:'desc',
		    queryParams: {
		    },
		    toolbar: [
	      	    {
	      	    	text:'添加广告',
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
	      		},'-',{
	      			text:'审核',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-save',
	      			handler: function(){
	      				check('');
	      		    }
	      		},'-',{
	      			text:'生成节目单',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-print',
	      			handler: function(){
	      				createProgram ('');
	      		    }
	      		}
	      	],
			columns:[[
				{field:'ck',width:'5%',checkbox:"true"},
				{field:'addedTime',title:'添加时间',width:'15%',align:'center'},
// 				{field:'rowKey',title:'ID',width:'40%',align:'center'},		
				
				
				{field:'advertiserName',title:'客户名称',width:'15%',align:'center'},
				{field:'airpor	t',title:'机场/频次',width:'10%',align:'center',formatter:function(value,row,index){
					return '<a class="airportView" id="'+row.rowKey+'" href="javascript:void();">查看</>';
				}},
// 				{field:'frequency',title:'播放频次',width:'10%',align:'center'},
				{field:'playType',title:'广告播放形式',width:'10%',align:'center',formatter:function(value,row,index){
					if(row.playType=='image'){
						return '图片广告';
					}else if(row.playType=='video'){
						return '视频广告';
					}
				}},
				{field:'auditStatus',title:'审核状态',width:'10%',align:'center',formatter:function(value,row,index){
					if(row.auditStatus==1){
						return '<span class="waitCheck">待审核</span>';
					}else if(row.auditStatus==2){
						return '<span>已审核</span>';
					}else if(row.auditStatus==3){
						return '<span>审核未通过</span>';
					}else if(row.auditStatus==4){
						return '<span>已生成节目单</span>';
					}
				},styler:function(value,row,index){
					if(row.auditStatus==1){
						return 'background-color:#ffee00;color:#000000;'
					}
					if(row.auditStatus==2){
						return 'background-color:#00FF00;color:#000000;'
					}
					if(row.auditStatus==3){
						return 'background-color:#FF3333;color:#000000;'
					}
					if(row.auditStatus==4){
						return 'background-color:#ffee00;color:red;'
					}
				}},

                {field:'operator',title:'操作账户',width:'10%',align:'center'},
                
                {field:'remark',title:'备注',width:'10%',align:'center'},
				{field:'operator1',title:'操作',width:'15%',align:'center',formatter:function(value,row,index){
					return '<a class="editCls" href="javascript:editItemInit(\''+row.rowKey+'\');"></a><a class="preview" href="javascript:MediaLook(\''+row.playType+'\',\''+row.rowKey+'\',\''+row.auditStatus+'\');"></a>';
				}},		
		    ]],
		    onBeforeLoad:function(data){
		    	//添加搜索条件
		        if($(".searchBox").length == 0){
			        var searchTool = '<div class="searchBox" style="padding:3px 0 0 15px;height:40px;line-height:30px;">';
			        searchTool+='机场:&nbsp;<input id="airportId" style="width:200px;height:30px;"></td>&nbsp;&nbsp;&nbsp;';
			        searchTool+='广告商:&nbsp;<input id="advertiserId" style="width:200px;height:30px;"></td>';
			        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
			        searchTool+='</div>';
			        $(".datagrid-toolbar").append(searchTool);
			        //初始化机场选择combobox
			        $.post("<%=path%>/advert/getAirportJson.do",{},function(data){
			        	data.splice(0,0,{airportCode:"",airportName:"全部"});
			        	$('#airportId').combobox({
			        		data:data,  
					        editable:false,  
					        valueField:'airportCode',  
					        textField:'airportName',  
					        onSelect:function(record){  	
					        	$("#airportId").val(record.airportCode);
					        }  
					    }); 
			        },"json"); 
			        //初始化广告商的选择
			        $.post("<%=path%>/advert/getAdvertiserJson.do",{},function(data){
			        	data.splice(0,0,{rowKey:"",advertiserName:"全部"});
			        	$("#advertiserId").combobox({
			        		data:data,  
					        editable:false,  
					        valueField:'rowKey',  
					        textField:'advertiserName',
					        onSelect:function(record){  	
					        	$("#advertiserId").val(record.rowKey);
					        }  
			        	}) 	
			        },"json")
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
		    	if(data.preRowKey != ""){
			    	preRowKey = data.preRowKey;
			    	nextRowKey = data.nextRowKey;
		    	}else{
		    		preRowKey = nextRowKey;
		    	}
		    	//初始化编辑按钮
		        $('.editCls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
		        $('.preview').linkbutton({text:'预览',plain:true,iconCls:'icon-tip'});
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
		        $(".airportView").each(function(){
		        	var advertId = $(this).attr("id");
		        	$("#"+advertId).tooltip({
						content: "",
						onShow: function(){
							var t = $(this);
							t.tooltip('tip').unbind().bind('mouseenter', function(){
								t.tooltip('show');
							}).bind('mouseleave', function(){
								t.tooltip('hide');
							});
						}
					});
		        });
		        $(".airportView").hover(function(){
		        	var advertId = $(this).attr("id");
		        	$.ajax({
						type:"post",
						async:false,//jax同步
						url:'<%=path%>/advert/getAirportInfo.do',
						data:{advertId:advertId},
						global:true,//使用全局的ajax实际爱你
						
						success:function(html, textStatus){
							$("#"+advertId).tooltip("update",html);
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							alert("网络错误，请稍后重试!");
						},
						dataType:"html"
					});
		        },function(){
		        	$("#"+advertId).tooltip("hide");
		        });
		    	
		    }
		}); 
		initPage($('#tableList')); 
	});
	//刷新表格
	function reloadTable(){
		toHomePage();
	}
	
	    //生成节目单
	    function createProgram(){
	    	var ids = [];
			var checkedItems = $('#tableList').datagrid('getChecked');
			$.each(checkedItems,function(index,item){
				ids.push(item.rowKey);
			});
			if(ids.length == 0){
				$.messager.alert('提示',"请选择要生成节目单的广告",'warning');
				return;
			}
			$.messager.confirm("确认信息","确定将选中的"+ids.length+"条数据生成节目单吗？", function (r) {  
        		if (r){
        			
       
        			$.ajax({
		        		type:'post',
		        		async:false,
		        		url:"<%=path%>/advert/createProgram.do",
		        		data:{
		        			ids:ids.join(",")
		        			
		        		},
		        		success:function(data){
		        			if(data.result == "1"){
		        				$.messager.alert('提示',data.message,'info', function(r){
		        					reloadTable();
		        			    });
		        			}else{
		        				$.messager.alert('生成节目单失败',data.message,'error');
		        			}
		        		},
		        		dataType:'json'
		        	});
        		}
        	});
		    return false;  
	  }
	
	   //预览条目
	   function MediaLook(adtype,ids,status){
		var dialog = $('<div></div>').dialog({
			title:adtype == "image" ? "图片预览页面" : "视频预览页面",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/advert/advertViewInit.do?id='+ids,
			onClose:function(){
				$(this).dialog('destroy');
			},
            buttons : [
              {
                text : '确认审核',
                iconCls : 'icon-ok',
                handler : function() {
                	if(status==2||status==4){
                		$.messager.alert('提示','该条资源已修改,无权操作','error');
                		return false;
                	}
                	$.messager.confirm("确认信息","确认审核这条信息吗", function (r) {
                		if(r){
                			$.ajax({
                        		type:'post',
                        		url:'<%=path%>/advert/advertExaminePass.do',
                        		data:{
                        			ids:ids
                        		},
                        		success:function(data){
                        			if(data.result == "1"){
                        				$.messager.alert('提示',"审核成功",'info', function(r){
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
                	})	
                }
              },
              {
                 text : '审核不通过',
                 iconCls : 'icon-cut',
                 handler : function() {
                	 if(status==2||status==4){
                 		$.messager.alert('提示','该条资源已修改,无权操作','error');
                 		return false;
                 	}
                	 $.messager.confirm("确认信息","确认拒绝审核这条信息吗", function (r) {
                 		if(r){
                 			$.ajax({
                         		type:'post',
                         		url:'<%=path%>/advert/advertExamineUnPass.do',
                         		data:{
                         			ids:ids
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
                 	})	
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
	//编辑条目
	function editItemInit(id){
		var dialog = $('<div></div>').dialog({
			title:id != "" ? "编辑广告" : "添加广告",
			height:'80%',
			width:'70%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/advert/addOrEditAdvertInit.do?id='+id,
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
                	if(validateAdvertForm()){
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
			ids.push(item.rowKey);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要删除的广告",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/advert/deleteAdvert.do",
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
	//批量同步资源到厅服务器
	function synchro(){
		var ids = [];
		var checkedItems = $('#tableList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.rowKey);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要同步的广告",'warning');
			return;
		}
		$.messager.confirm("确认信息","确同步选中的"+ids.length+"条数据吗？", function (r) {
	        if (r){  
	        	 $.messager.progress({
	    	   		 title:'Please waiting',
	    	   		 msg:'资源同步中，这可能需要很长时间，请稍后...'
	       		 });
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/advert/synchroResource.do",
	        		data:{
	        			ids:ids.join(",")
	        		},
	        		success:function(data){
	        			if(data.result == "1"){
	        				$.messager.alert('提示',data.message,'info', function(r){
	        					reloadTable();
	        			    });
	        			}else{
	        				$.messager.alert('操作失败',data.message,'error');
	        			}
	        			$.messager.progress('close');
	        		},
	        		error:function(){
	        			$.messager.progress('close');
	        		},
	        		dataType:'json'
	        	});
	        }  
	    });  
	    return false;  
	}
	
	//批量审核条目
	 function check(){
		var ids = [];
		var checkedItems = $('#tableList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.rowKey);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要审核的广告",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定审核选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/advert/advertExaminePass.do",
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