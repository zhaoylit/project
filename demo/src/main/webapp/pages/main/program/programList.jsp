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
			datagrid = $('#showList').datagrid({
 			title:'节目单列表',
		    url:'program/programList.do',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"id",
		    pagination:true,
 		    pageSize:10,
 		    pageList:[5,10,15,20,100],//每页的个数  
		    singleSelect:true,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    sortName:'id',
		    sortOrder:'desc',
		    queryParams: {
		    },
		    toolbar: [{
	      			text:'添加',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-add',
	      			handler: function(){
	      				addItem('1');
	      		    }
	      		},'-',
	      		{
	      			text:'编辑节目单信息',
	      			width:'160',
	      	    	height:'40',
	      			iconCls: 'icon-remove',
	      			handler: function(){
	      				addItem('2');
	      		    }
	      		},'-',
		              {
	      			text:'删除',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-clear',
	      			handler: function(){
	      				deleteItem('');
	      		    }
	      		},'-',
	      		{
	      			text:'审核',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-lock',
	      			handler: function(){
	      			    CheckItem('');
	      		    }
	      		},'-',
	      	    {
	      			text:'同步',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-save',
	      			handler: function(){
	      				keep('');
	      		    }
	      		},'-',
	      	    {
	      			text:'发布',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-save',
	      			handler: function(){
	      				pushProgram('');
	      		    }
	      		}
	      	],
			columns:[[
                {field:'ck',width:'5%',checkbox:"true"}, 
                {field:'id',title:'节目单id',width:'10%',align:'center',hidden:'true'},
                {field:'operator',title:'操作账户',width:'5%',align:'center'},
                {field:'programName',title:'节目单名称',width:'15%',align:'left',formatter:function(value,row,index){
					return '<div class="programNameView" id="programName'+row.rowKey+'" programId="'+row.rowKey+'" style="font-size:10px;">'+row.programName+'</div>';
				}},
				{field:'examStatus',title:'审核状态',width:'10%',align:'center',formatter:function(value,row,index){
					if(row.examStatus==1){
						return '<span class="waitCheck">待审核</span>';
					}else if(row.examStatus==2){
						return '<span>已审核</span>';
					}else if(row.examStatus==3){
						return '<span>审核未通过</span>';
					}
				},styler:function(value,row,index){
					if(row.auditStatus==1){
						return 'background-color:#ffee00;color:#000000;'
					}
					if(row.auditStatus==2){
						return 'background-color:#00FF00;color:#000000;'
					}
					if(row.auditStatus==3){
						return 'background-color:#FF3333;color:red;'
					}
					if(row.auditStatus==4){
						return 'background-color:#ffee00;color:red;'
					}
				}},
				{field:'examRemark',title:'审核备注',width:'10%',align:'center'},
                {field:'beginTime',title:'有效开始时间',width:'10%',align:'center'},
                {field:'endTime',title:'有效结束时间',width:'10%',align:'center'},
                //{field:'endDate',title:'操作时间',width:'10%',align:'center'},
                {field:'synchCount',title:'已同步的VIP厅的数量',width:'10%',align:'center'},
				/* {field:'updateTing',title:'查看已同步的设备',width:'10%',align:'center',formatter:function(value,row,index){
					return '<a onclick="programTingView(\''+row.programId+'\')" class="viproomListView" id="viproom'+row.rowKey+'" href="javascript:void();">查看列表</a>';	
				}}, */
				{field:'operator1',title:'操作',width:'10%',align:'center',formatter:function(value,row,index){
					return '<a class="editCls" href="javascript:editProgramItemInit(\''+row.id+'\');"></a>';
				}},		
		    ]],
		    onBeforeLoad:function(data){
		    	//添加搜索条件
		        <%-- if($(".searchBox").length == 0){
			        var searchTool = '<div class="searchBox" style="padding:3px 0 0 15px;height:40px;line-height:30px;">';
			        /* searchTool+='机场:&nbsp;<input id="airportId" style="width:370px;height:30px;"></td>&nbsp;&nbsp;&nbsp;'; */
			        searchTool+='广告商:&nbsp;<input id="advertiserId" style="width:200px;height:30px;"></td>&nbsp;&nbsp;&nbsp;';
			        searchTool+='<td style="">开始上刊时间:&nbsp;<input id="beginTime" class="easyui-datetimebox" style="width:200px;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;';
                    searchTool+='<td style="">下刊时间:&nbsp;<input id="endTime" style="width:200px;height:30px;">';
			        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
			        searchTool+='</div>';
			        $(".datagrid-toolbar").append(searchTool);
			        //初始化广告商的选择
			        $.post("<%=path%>/advertiser/getAdvertiserJson.do",{},function(data){
			        	data.splice(0,0,{id:"",advertiserName:"全部"});
			        	$("#advertiserId").combobox({
			        		data:data,  
					        editable:false,  
					        valueField:'id',  
					        textField:'advertiserName',
					        onSelect:function(record){  	
					        	$("#advertiserId").val(record.id);
					        }  
			        	}) 	
			        },"json");
			     // 初始化开始时间数据
                    $('#beginTime').datetimebox({
                        showSeconds: false
                    });
                     // 初始化结束时间数据
                    $('#endTime').datetimebox({
                        showSeconds: false
                    });
					//初始活动类型选择combobox
			        $('#search').linkbutton({
			        	iconCls:'icon-search', 
			        });
		        } --%>
		    },
		     onLoadSuccess:function(data){
		    	//初始化编辑按钮
		        $('.editCls').linkbutton({text:'管理节目',plain:true,iconCls:'icon-edit'});
		    } 
		}); 
	});
	
	
	//刷新表格
	function reloadTable(){
		$('#showList').datagrid('load', {
			
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val()
 		});
	}
	//查看已经同步过的厅服务器
	function programTingView(programId){
		var dd = $('<div></div>').dialog({
			title:"节目单同步查看页面",
			height:'500',
			width:'800',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,
		    href:'advert/getProgramViproomListSynchInit.do?programId='+programId+'&airportCode='+airportCode,
			onClose:function(){
				dd.dialog('destroy');
			},
            buttons : [
              {
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                	dd.dialog('destroy');
                }
              }
            ]
		});
	}
	/*--------添加节目单信息页面以及编辑节目单页面----------------*/
	function addItem(type){
		var title=(type != "1" ? "编辑节目单信息" : "添加节目单信息");
		var id="";
		
		
		if(type != "1"){
			var ids = [];
			var checkedItems = $('#showList').datagrid('getChecked');
			$.each(checkedItems,function(index,item){
				ids.push(item.id);
			});
			if(ids.length != 1){
				$.messager.alert('提示',"请选择要编辑的广告",'warning');
				return;
			}
			id = ids[0];
		}
		var dialog = $('<div></div>').dialog({
			title:title,
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/program/addOrEditProgramInit.do?id='+id,
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
                    		url:'<%=path%>/program/addOrEditProgram.do',
                    		data:{
                    			id:id,
                    			programName:$("input[name='programName']").val(),
                    			beginTime:$("input[name='startDate']").val(),
                    			endTime:$("input[name='endDate']").val()
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
	//审核节目单
	function CheckItem(){
		var ids = [];
		var status='';
		var checkedItems = $('#showList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
			status+=item.examStatus;
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要审核的广告",'warning');
			return;
		}
		if(status !=1){
			$.messager.alert('提示',"该条广告资源已经操作过，无权修改",'warning');
			return;
		}
		//不能审核已经选择审核过的广告
		var dialog = $('<div></div>').dialog({
			title:'广告审核页面',
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/program/examinProgramInit.do?id='+checkedItems[0].id,  //初始化广告审核页面的接口
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
                    		url:'<%=path%>/program/examinProgram.do',
                    		data:{
                    			id:checkedItems[0].id,
                    			examStatus:$("input[name='examStatus']").val(),
                    			examRemark:$("input[name='examRemark']").val()
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
	//批量删除节目单信息
	function deleteItem(){
		var ids = [];
		var checkedItems = $('#showList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要删除的节目单",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	 $.messager.progress({
			      	   title:'Please waiting',
			     	   msg:'删除中，请稍后...'
			    });
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/program/deleteProgram.do",
	        		data:{
	        			id:ids.join(",")
	        		},
	        		success:function(data){
	        			if(data.result == "1"){
	        				$.messager.alert('提示',"操作成功",'info', function(r){
	        					reloadTable();
	        			    });
	        			}else{
	        				$.messager.alert('操作失败',data.message,'error');
	        			}
	        			$.messager.progress('close');
	        		},
	        		dataType:'json'
	        	});
	        }  
	    });  
	    return false;  
	}
	 //同步节目单
	 function keep(){
		 var ids = [];
			var checkedItems = $('#showList').datagrid('getChecked');
			$.each(checkedItems,function(index,item){
				ids.push(item.rowKey);
			});
			if(ids.length == 0){
				$.messager.alert('提示',"请选择节目单进行同步",'warning');
				return;
			}
			
			var dialog = $('<div id="synchid"></div>').dialog({
				title:'节目单同步页面',
				height:'80%',
				width:'60%',
				top:'10%',
				minimizable:false,  
			    maximizable:false,  
			    collapsible:false,  
			    shadow: true,  
			    modal: true,  
				href:'<%=path%>/program/synchProgramInit.do?id='+checkedItems[0].id,  //同步节目单初始化接口
				onClose:function(){
					$(this).dialog('destroy');
				},
	            buttons : [
	              {
	                text : '确认同步',
	                iconCls : 'icon-save',
	                handler : function(){
	                	var devices=$("#checkItem").find("input[type='checkbox']:checked").map(function(index,elem) {
            	            return $(elem).val();		                    	            
            	        }).get();
	                	if(devices.length==0){
	                		$.messager.alert('提示',"请选择设备进行同步",'warning');
	        				return;
	                	}

	                	$('#notice_info').show();
	                	console.log("来到了点击提交的页面");
	                	$.messager.progress({
					      	   title:'Please waiting',
					     	   msg:'发布中，这可能需要很长时间，请稍后...'
					    });
	                		$.ajax({
	                    		type:'post',
	                    		async:true,
	                    		url:'<%=path%>/program/synchProgram.do',
	                    		data:{
	                    			programId:checkedItems[0].id,
	                    			airlineId:airlineId_,
	                    			airportId:airportId_,
	                    			viproomId:viproomId_,
	                    			deviceIds:devices.join(',')	
	                    		},
	                    		
	                    		success:function(data){
	                    			if(data.result == "1"){
	                    				$.messager.alert('提示',data.message,'info', function(r){
	                    					dialog.dialog('destroy');
	           	
	                    			    });
	                    				reloadTable();
	                    			}else{
	                    				$.messager.alert('操作失败',data.message,'error');
	                    			}
	                    			
	                    			$.messager.progress('close');
	                    		},
	                    		error:function(){
	                    			$.messager.alert('操作失败',data.message,'error');
	                    		},
	                    		dataType:'json'
	                    	});
	                	
	                	
        			    
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
	 //直接发布节目单
	 function pushProgram(){
		 var ids = [];
			var checkedItems = $('#showList').datagrid('getChecked');
			$.each(checkedItems,function(index,item){
				ids.push(item.rowKey);
			});
			if(ids.length == 0){
				$.messager.alert('提示',"请选择节目单进行发布",'warning');
				return;
			}
			
			var dialog = $('<div id="synchid"></div>').dialog({
				title:'节目单发布页面',
				height:'80%',
				width:'60%',
				top:'10%',
				minimizable:false,  
			    maximizable:false,  
			    collapsible:false,  
			    shadow: true,  
			    modal: true,  
				href:'<%=path%>/program/synchProgramInit.do?id='+checkedItems[0].id,  //同步节目单初始化接口
				onClose:function(){
					$(this).dialog('destroy');
				},
	            buttons : [
	              {
	                text : '确认发布',
	                iconCls : 'icon-save',
	                handler : function(){
	                	var devices=$("#checkItem").find("input[type='checkbox']:checked").map(function(index,elem) {
            	            return $(elem).val();		                    	            
            	        }).get();
	                	if(devices.length==0){
	                		$.messager.alert('提示',"请选择设备进行发布",'warning');
	        				return;
	                	}
	                	console.log("来到了点击提交的页面")
	                	    $.messager.progress({
						      	   title:'Please waiting',
						     	   msg:'发布中，这可能需要很长时间，请稍后...'
						    });
	                		$.ajax({
	                    		type:'post',
	                    		async:true,
	                    		url:'<%=path%>/program/pushProgram.do',
	                    		data:{
	                    			programId:checkedItems[0].id,
// 	                    			airlineId:airlineId_,
// 	                    			airportId:airportId_,
// 	                    			viproomId:viproomId_,
	                    			deviceIds:devices.join(',')	
	                    		},
	                    		success:function(data){
	                    			if(data.result == "1"){
	                    				$.messager.alert('提示',"节目单发布成功，具体详情请查看设备监控",'info', function(r){
	                    					dialog.dialog('destroy');
	           	
	                    			    });
	                    				reloadTable();
	                    			}else{
	                    				$.messager.alert('操作失败',data.message,'error');
	                    			}
	                    			
	                    			$.messager.progress('close');
	                    		},
	                    		error:function(){
	                    			$.messager.alert('操作失败',data.message,'error');
	                    		},
	                    		dataType:'json'
	                    	});
	                	
	                	
        			    
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
	
		//节目单打包下载
	    function zipDownload(){
	    	var ids = [];
			var checkedItems = $('#showList').datagrid('getChecked');
			$.each(checkedItems,function(index,item){
				ids.push(item.id);
			});
			if(ids.length == 0){
				$.messager.alert('提示',"请选择要打包下载的节目单",'warning');
				return;
			}
			location.href="program/programDownload.do?programId="+ids.join(",");
	   }
		
		//节目单资源管理
		function editProgramItemInit(id){
			window.open("program/programResourceManageInit.do?programId="+id); //初始化节目单管理页面的接口
		}
	
	</script>
<table id="showList" style="width:100%;margin:0px;"></table>