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
#acticityTable tr{height:40px;}
input{width:200px;}
.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber
  {
      text-overflow: ellipsis;
  }
</style>

<script type="text/javascript"> 
	$(function() { 		 
		initviproomId('${resultInfo.airportCode}','${resultInfo.airportName}');
		 	/*  加载页面时初始化数据表格 */
			$('#tableList').datagrid({
 			//title:'设备列表',
		    url:'<%=path%>/device/deviceList.do',
		    toolbar:'#tb',
		    striped:true,
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"rowKey",
		    pagination:true,
		    rownumbers:true,
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
	      	    	text:'添加',
	      	    	height:'40',
	      			iconCls: 'icon-add',
	      			width:'100',
	      			handler: function(){
	      				editItemInit('');
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
	      			text:'删除',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-remove',
	      			handler: function(){
	      				deleteItem();
	      		    }
	      		},/* '-',{
	      			text:'设备重启',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-restart_device',
	      			handler: function(){
	      				restart();
	      		    }
	      		} */
	      	],
			columns:[[
				{field:'ck',width:'5%',checkbox:"true"},
				{field:'deviceId1',title:'UUID',width:'12%',align:'left',formatter:function(value,row,index){
					return '<input type="text" style="width:100%;" value="'+row.deviceId1+'"/>';
				}},
				{field:'deviceId',title:'设备号',width:'9%',align:'left'},		
				{field:'devTypeName',title:'设备类型',width:'5%',align:'left'},
				{field:'devName',title:'设备名称',width:'5%',align:'left'},
				{field:'devIp',title:'设备IP',width:'10%',align:'left'},
				{field:'viproom',title:'VIP厅',width:'5%',align:'left'},
				{field:'airportName',title:'机场名',width:'8%',align:'left'},
				{field:'airlineName',title:'航空公司',width:'8%',align:'left'},
				{field:'connectionStatus',title:'连接状态',width:'5%',align:'left', formatter : function(value,row,index){
                    if(value=='1'){return '<span>已连接</span>'}  
                    else if(value=='2'){return '<span>未连接</span>'}                        
                  } ,styler:function(value,row,index){
					if(value=='1'){
						return 'background-color:#D2E9FF;color:#000000;'
					}
					if(value=='2'){
						return 'background-color:#FFECEC;color:#000000;'
					}
				}
				},
				{field:'state',title:'状态',width:'4%',align:'left', formatter : function(value,row,index){
                    if(value=='1'){return '<span>正常</span>'}  
                    else if(value=='2'){return '<span>未审核</span>'}                        
                  } ,styler:function(value,row,index){
					if(value=='1'){
						return 'background-color:#CCEED0;color:#000000;'
					}
					if(value=='2'){
						return 'background-color:#FFEC82;color:#000000;'
					}
				}
				},
				//{field:'state',title:'状态',width:'4%',align:'left'},
				{field:'operator',title:'操作者',width:'5%',align:'left'},
				{field:'createTime',title:'操作时间',width:'7%',align:'left'},
				{field:'operators',title:'操作',width:'12%',align:'left',formatter:function(value,row,index){
					return '<a class="editCls" href="javascript:editItemInit(\''+row.rowKey+'\');"></a><a class="details" href="javascript:details(\''+row.rowKey+'\');"></a>';
				}}
		    ]],
		    onBeforeLoad:function(data){
		    	 
		    	//添加搜索条件
		        if($(".searchBox").length == 0){
			        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:108px;line-height:30px;">';
			        searchTool+='机场名称:&nbsp;<input id="airportCode" style="width:15%;height:30px;">&nbsp;&nbsp;';
                    searchTool+='VIP 厅:&nbsp;<input id="viproom" name="viproomId" style="width:15%;height:30px;">&nbsp;&nbsp;';
                    searchTool+='连接状态:&nbsp;<input id="connectionStatus" style="width:15%;height:30px;">&nbsp;&nbsp;';
                    searchTool+='设备类型:&nbsp;<input id="devType" style="width:15%;height:30px;">&nbsp;&nbsp;<br/><br/>';
                    searchTool+='设备名称:&nbsp;<input id="devName" style="width:15%;height:30px;">&nbsp;&nbsp;';
                    searchTool+='设备IP:&nbsp;<input id="devIp" style="width:15%;height:30px;">&nbsp;&nbsp;';
                    searchTool+='设备编号:&nbsp;<input id="deviceId" style="width:15%;height:30px;">&nbsp;&nbsp;';
                    searchTool+='UUID:&nbsp;&nbsp;&nbsp;&nbsp;<input id="deviceId1" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			        searchTool+='<a id="search" onclick="reloadTable()"  style="width:8%;height:32px">查询</a><br/><br/>';
			        searchTool+='</div>';
			        $(".datagrid-toolbar").append(searchTool);
			        //初始化地区选择combobox
			        $('#viproom').combobox({});
			        $.post("<%=path%>/device/getAirport.do",{},function(data){
			        	 data.splice(0,0,{airportCode:"",airportName:"全部"}); 
			        	 $('#airportCode').combobox({
			        		 data:data,
			        		 editable:true,
			        		 valueField:'airportCode',
			        		 textField:'airportName',
			        		 onSelect:function(record){  	
						        	$("#airportCode").val(record.airportCode);
						        	$("input[name='viproomId']:hidden").attr("value","");
									$("#airName").val(record.airportName);
									if(record.airlineCode=='0'){
							    		$("#viproomId").combobox('setValue', '0');
							    		$("#viproomId").attr("disabled",true);
							    	}else{
							    		initviproomId(record.airportCode,"");
							    	}
						        	$('#viproom').combobox({
						        		 url:'<%=path%>/device/getViproomByAirportCode.do?airportCode='+record.airportCode,
										 editable:true,	
										 valueField:'viproomId',
										 textField:'viproom',
										 onSelect:function(record){
											$("#viproom").val(record.viproom);
										}
						        	}); 
						     }  
			        	 }); 
			         },"json");  
			        	$('#deviceId').textbox({}); 
			        	$('#devName').textbox({}); 
			        	$('#devIp').textbox({}); 
			        	$('#deviceId1').textbox({}); 
			        	$('#devType').combobox({  
			        	    data:[{devType:"",devTypeName:"全部"},{devType:"1",devTypeName:"厅服务器"},{devType:"2",devTypeName:"工作站"},{devType:"3",devTypeName:"桥接"},{devType:"4",devTypeName:"PAD"},{devType:"5",devTypeName:"重启设备"},],  
					        editable:true,  
					        valueField:'devType',  
					        textField:'devTypeName',  
					        onSelect:function(data){  
					        	$("#devType").val(data.devType);
					        }   
					    });  
			        	$('#connectionStatus').combobox({  
			        	    data:[{connectionStatus:"",connectionText:"请选择"},{connectionStatus:"1",connectionText:"已连接"},{connectionStatus:"2",connectionText:"未连接"}],  
					        editable:true,  
					        valueField:'connectionStatus',  
					        textField:'connectionText',  
					        onSelect:function(data){  
					        	$("#connectionStatus").val(data.connectionStatus);
					        }   
					    });  
			        	$('#search').linkbutton({
			        		iconCls:'icon-search'
				        });
		        }
		    },
		    onLoadSuccess:function(data){  
		    	if(data.result=='0'){
    	    		$.messager.alert('提示',data.message,'error');
    	    		return;
    	    	}
		    	 $('.editCls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
		    	 $('.details').linkbutton({text:'详情',plain:true,iconCls:'icon-tip'});
		    	if(data.preRowKey != ""){
			    	preRowKey = data.preRowKey;
			    	nextRowKey = data.nextRowKey;
		    	}else{
		    		preRowKey = nextRowKey;
		    	}
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
			initPage($('#tableList'));
	});
	/*  $('#tableList').datagrid('load', {
	airportCode:$("#airportName").combobox('getValue'),
	deviceId:$("#deviceId").val(),
	devType:$("#devType").combobox('getValue') 
	});  */
	//刷新表格
	function reloadTable(){
		toHomePage();
	}
	function initviproomId(airportCode,value){
		$("#viproomId_td").html("");
		$("#viproomId_td").append('<input  id="viproomId" name="viproomId" value="'+value+'"/><input id="viproom" name="viproom" type="hidden"  value="${resultInfo.viproom}"/>');
		$("#viproom").combobox({
		     url:'<%=path%>/device/getViproomByAirportCode.do?airportCode='+airportCode,
		     editable:false,
			 multiple:false,
			 valueField:'viproomId',
			 textField:'viproom',
			 onLoadSuccess:function(){
		        	//设置默认值
		        	$('#viproom').combobox('select',"${resultInfo.viproomId}");
		        },
		});
	}
	//添加维护信息
	function addItem(deviceId){
 		var row = $('#tableList').datagrid('getSelected');
 		if (row){
 			$('#addOrEditMtnForm').form('load',{'deviceId':deviceId});
 			$("input[name='deviceId']").attr('readonly','readonly');
 			$('#mWindow').window('open');
 		} 
	}
	//编辑条目
	function editItemInit(rowKey){
		var dialog = $('<div></div>').dialog({
			title:rowKey != "" ? "编辑设备" : "添加设备",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/device/addOrEditDeviceInit.do?rowKey='+rowKey,
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
                	if(validateDeviceForm()){
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
	//设备详情
	function details(rowKey){
		var dialog = $('<div></div>').dialog({
			title:"设备详情",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/device/detailsInit.do?rowKey='+rowKey,
			onClose:function(){
				$(this).dialog('destroy');
			},
            buttons : [
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
	<%-- //设备重启
	function restart(){
		var ids = [];
		var checkedItems = $('#tableList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.rowKey);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要重启的设备",'warning');
			return;
		}
		
		$.messager.confirm("确认信息","确定操作选中的"+ids.length+"个设备吗？", function (r) {  
	        if (r){
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/device/equipmentRestart.do",
	        		data:{
	        			ids:ids.join(",")
	        		},
	        		success:function(data){
	        			if(data.result == "1"){
	        				$.messager.alert('提示',"设备已重启",'info', function(r){
	        					reloadTable();
	        			    });
	        			}else if(data.result == "2"){
	        				$.messager.alert('提示',"请检查设备状态是否正常",'info', function(r){
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
	 --%>
	
	
	// 所做的一些表单验证
	  function validateDeviceForm(){
		if($("input[name='deviceId']").val() == ""){
			$.messager.alert('提示',"设备编号不能为空",'error');
			return false;
		}
		if($("input[name='devType']").val() == ""){
			$.messager.alert('提示',"设备类型不能为空",'error');
			return false;
		}
		if($("input[name='devIp']").val() ==""){
			$.messager.alert('提示',"设备IP不能为空",'error');
			return false
		} 
		if($("input[name='city']").val() == ""){
			$.messager.alert('提示',"请填写城市",'error');
			return false;
		}
		if($("input[name='state']").val() == ""){
			$.messager.alert('提示',"状态不能为空",'error');
			return false;
		}
		return true;
	}
	
	//批量删除条目
	function deleteItem(){
		var ids = [];
		var checkedItems = $('#tableList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.rowKey);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要删除的设备",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/device/deleteDevice.do",
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
	//批量审核设备
	 function check(){
			var ids = [];
			var checkedItems = $('#tableList').datagrid('getChecked');
			$.each(checkedItems,function(index,item){
				ids.push(item.rowKey);
			});
			if(ids.length == 0){
				$.messager.alert('提示',"请选择要审核的设备",'warning');
				return;
			}
			$.messager.confirm("确认信息","确定审核选中的"+ids.length+"个设备吗？", function (r) {  
		        if (r){  
		        	$.ajax({
		        		type:'post',
		        		url:"<%=path%>/device/deviceExaminePass.do",
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

</div>
