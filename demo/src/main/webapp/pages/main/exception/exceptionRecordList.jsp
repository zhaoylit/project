<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<script>
			$(function() { 		 	
					 	/*  加载页面时初始化数据表格 */
						$('#tableList').datagrid({
					//	title:'异常记录列表',
					    url:'<%=path%>/exception/exceptionRecordList.do',
					    fit:true,
					    method:'post',
					    loadMsg:'数据加载中,请稍等...',
					    fitColumns:true,
					    idField:"id",
					    pagination:true,
						pageSize:20,
						pageList:[10,15,20,100],//每页的个数  	
					    singleSelect:false,
					    checkOnSelect:true,
					    selectOnCheck: true,
					    sortName:'id',
					    sortOrder:'desc',
					    queryParams: {
					    },
					    toolbar: [
					  	    {
					  	    	text:'异常类型',
					  			iconCls: 'icon-redo',
					  	    	height:'40',	
					  			width:'130',
					  			handler: function(){
					  				exceptionType('');
					  		    }
					  		},'-',{
					  	    	text:'邮件发送',
					  			iconCls: 'icon-back',
					  	    	height:'40',	
					  			width:'130',
					  			handler: function(){
					  				exceptionLevel('');
					  		    }
					  		},'-',{
					  			text:'批量删除',
					  			width:'130',
					  	    	height:'40',
					  			iconCls: 'icon-remove',
					  			handler: function(){
					  				deleteItem('');
					  		    }
					  		},'-',{
					  	    	text:'批量解决',
					  			iconCls: 'icon-back',
					  	    	height:'40',	
					  			width:'130',
					  			handler: function(){
					  				updateStateSolve('');
					  		    }
					  		}
					  	],
						columns:[[
							{field:'ck',width:'5%',checkbox:"true"},		
							{field:'exceptionCode',title:'异常代码',width:'9%',align:'center'},
							//{field:'message',title:'异常信息',width:'10%',align:'center'},
							//{field:'module',title:'异常地区',width:'10%',align:'center'},
							{field:'beginTime',title:'发生时间',width:'10%',align:'center'},
							{field:'deviceId',title:'设备ID',width:'12%',align:'center'},
							{field:'airlineName',title:'航空公司名称',width:'12%',align:'center'},
							{field:'airportName',title:'机场名称',width:'12%',align:'center'},
							{field:'viproom',title:'vip厅',width:'12%',align:'center'},
							{field:'devIp',title:'设备IP',width:'12%',align:'center'},
							{field:'exceptionState',title:'异常状态',width:'10%',align:'center',formatter:function(value,row,index){
								if(row.exceptionState==1){
									return '<span class="waitCheck">待解决</span>';
								}else if(row.exceptionState==2){
									return '<span>已解决</span>';
								}
							},styler:function(value,row,index){
								if(row.exceptionState==1){
									return 'background-color:#FFBBBB;color:#302C3F;'
								}else{
									return 'background-color:#AADDFF;color:#302C3F;'
								}
							}},
							{field:'operator',title:'操作',width:'8%',align:'center',formatter:function(value,row,index){
								return '<a class="editCls" href="javascript:editItemInit(\''+row.id+'\');"></a>';
							}},		
					    ]],
					    onBeforeLoad:function(data){
					    	//添加搜索条件
					        if($(".searchBox").length == 0){
						        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:40px;line-height:30px;">&nbsp;&nbsp;';
						        searchTool+='航空公司:&nbsp;<input id="airlineCode" style="width:10%;height:30px;">&nbsp;&nbsp;';
						        searchTool+='机场名称:&nbsp;<input id="airportCode" style="width:10%;height:30px;">&nbsp;&nbsp;';
					            searchTool+=' VIP&nbsp;&nbsp;厅:&nbsp;<input id="viproom" name="viproom" style="width:10%;height:30px;" '; 
						        searchTool+='设备&nbsp;ID:&nbsp;<input id="deviceId" style="width:10%;height:30px;"/>&nbsp;&nbsp;';	
						        searchTool+='设备&nbsp;IP:&nbsp;<input id="devIp" style="width:10%;height:30px;"/>&nbsp;&nbsp;';	
						        searchTool+='异常代码:&nbsp;<input id="exceptionCode" style="width:10%;height:30px;"/>&nbsp;&nbsp;';	 
						        searchTool+='异常状态:&nbsp;<input id="exceptionState" style="width:10%;height:30px;">&nbsp;&nbsp;';
						        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTableRecord()" style="width:80px;height:30px;">搜索</a> ';
						        searchTool+='</div>';
						        $(".datagrid-toolbar").append(searchTool);
						        $("#exceptionCode").textbox({});
						        $("#deviceId").textbox({});
						        $("#devIp").textbox({});
						        $('#exceptionState').combobox({  
					        	    data:[{exceptionState:"",exceptionStateName:"全部"},{exceptionState:"1",exceptionStateName:"待解决"},{exceptionState:"2",exceptionStateName:"已解决"},],  
							        editable:true,  
							        valueField:'exceptionState',  
							        textField:'exceptionStateName',  
							        onSelect:function(data){  
							        	$("#exceptionState").val(data.exceptionState);
							        }   
							    });  
						       //初始化地区选择combobox
						        $("#airportCode").combobox({});

						        $("#viproom").combobox({});
						        
						        $.post("<%=path%>/device/getAirline.do",{},function(data){ 
						        		var aaa=$("#airportCode");
						        		data.splice(0,0,{airlineCode:"",airlineName:"全部"});   
						        		$("#airlineCode").combobox({
						        		     data:data,
						        			 editable:true,
						        			 valueField:'airlineCode',
						        			 textField:'airlineName',
						        		    	onSelect:function(record){ 
							        		    	if(record.airlineCode==''){
							        		    		$("#airportCode").combobox('setValue','');
							        		    		$("#viproom").combobox('setValue','');
							        		    	}else{
							        		    		initaiport(record.airlineCode,"");
						        		    		}
						        		    } 
						        		});
						        },"json"); 

	        
	      
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
			        $('.editCls').linkbutton({text:'异常详情',plain:true,iconCls:'icon-tip'});
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
			
			function initaiport(airlineCode,value){
				$("#airportCode").combobox({
					url:'<%=path%>/device/getAirportByAirlineCode.do?airlineCode='+airlineCode,	
					valueField:'airportCode',
					textField:'airportName',
			        onSelect:function(record2){
				    	if(record2.airportCode==''){
				    		$("#viproom").combobox('setValue','');
				    	}else{
			    			$("#viproom").combobox({
			    			     url:'<%=path%>/device/getViproomByAirportCode.do?airportCode='+record2.id,
			    				 valueField:'id',
			    				 textField:'viproomName',
			    				 onLoadSuccess:function(){
			    			        	//设置默认值
			    			        },
			    			});
				   		} 
			         }
				});
			}

		function initaviproom(airportCode,value){
			
		}

	///刷新表格
	/* function reloadTable(){
	//toHomePage();
		$('#tableList').datagrid('reload');
	} */
	
	function reloadTableRecord(){
		$('#tableList').datagrid('load',{
			exceptionState:$('#exceptionState').val(),
			airlineCode:$('#airlineCode').combobox('getValue'),
			airportCode:$('#airportCode').combobox('getValue'),
			viproom:$('#viproom').combobox('getValue'),
			deviceId:$('#deviceId').val(),
			deviceIp:$('#deviceIp').val(),
			exceptionCode:$('#exceptionCode').val(),
			exceptionState:$('#exceptionState').val(),
		});
	}
    
	//批量解决
	function updateStateSolve(){
		var ids = [];
		var checkedItems = $('#tableList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要解决的异常",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定修改选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/exception/updateStateSolve.do",
	        		data:{
	        			ids:ids.join(",")
	        		},
	        		success:function(data){
	        			if(data.result == "1"){
	        				$.messager.alert('提示',"操作成功",'info', function(r){
	        					reloadTableRecord();
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


	//批量解决
	function deleteItem(){
		var ids = [];
		var checkedItems = $('#tableList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要删除的异常",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/exception/deleteexceptionRecord.do",
	        		data:{
	        			ids:ids.join(",")
	        		},
	        		success:function(data){
	        			if(data.result == "1"){
	        				$.messager.alert('提示',"操作成功",'info', function(r){
	        					reloadTableRecord();
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
	

	//浏览异常详情
	function editItemInit(id){
		var dialog = $('<div></div>').dialog({
			title:id != "" ? "浏览异常详情" : "添加异常详情",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/exception/editExceptionRecordInit.do?id='+id,
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
	
	
	function exceptionType(id){
		var dialog1 = $('<div></div>').dialog({
			title:"设置异常",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/exception/exceptionTypeInit.do',
			onClose:function(){
				$(this).dialog('destroy');
			},
	        buttons : [
	          {
	            text : '关闭',
	            iconCls : 'icon-cancel',
	            handler : function() {
	            	$('#tableList').datagrid('reload');
	            	dialog1.dialog('destroy');
	            }
	          }
	        ]
		});
	}
	
	function exceptionLevel(id){
		var dialog1 = $('<div></div>').dialog({
			title:"解决方案",
			height:'35%',
			width:'40%',
			top:'30%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/exception/exceptionLevelInit.do',
			onClose:function(){
				$(this).dialog('destroy');
			},
	        buttons : [
			{
			    text : '提交',
			    iconCls : 'icon-save',
			    handler : function() {
			    	var exceptionLevelId="";
			    	var exceptionLevelEmail="";
			    	var exceptionLevelSolution="";
			    	var exceptionLevelSendTime="";
			    	//提交表单
			    	var $form = $("#EditLevelForm");
			    		$.ajax({
			        		type:'post',
			        		url:'<%=path%>/exception/exceptionLevel.do',
			        		data:$form.serialize(),	
			        		/* data:{
			        			resource:jsonStr
			        		}, */
			        		success:function(data){
			        			if(data.result == "1"){
			        				$.messager.alert('提示',"操作成功",'info', function(r){
			        					dialog1.dialog('destroy');
			        					reloadTableRecord();
			        			    });
			        			}else if (data.result=="2"){
			        				$.messager.alert('操作失败',"发送邮件，邮箱不能为空",'error');
			        			}
			        			else {
			        				$.messager.alert('操作失败',data.message,'error');
			        			}
			        		},
			        		dataType:'json'
			        	});
			   	 }
			  },
	          {
	            text : '关闭',
	            iconCls : 'icon-cancel',
	            handler : function() {
	            	$('#tableList').datagrid('reload');
	            	dialog1.dialog('destroy');
	            }
	          }
	        ]
		});
	}


</script>
<table id="tableList" style="width:100%;margin:0px;"></table>