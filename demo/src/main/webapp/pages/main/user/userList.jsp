<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style>
.datagrid-toolbar, .datagrid-pager {
	background: none;
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
			$('#userList').datagrid({
// 			title:'用户列表',
		    url:'<%=path%>/user/userList.do',
		    //toolbar:'#tb',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"id",
		    pagination:true,
 		    pageSize:10,
		    pageList:[5,10,15,20,100],//每页的个数  
		    singleSelect:false,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    sortName:'id',
		    sortOrder:'desc',
		    queryParams: {
		    },
		    toolbar: [
	      	    {
	      	    	id:"abc",
	      	    	text:'添加',
	      	    	height:'40',
	      			iconCls: 'icon-custom_adduser',
	      			width:'100',
	      			handler: function(){
	      				editItemInit('');
	      		    }
	      		},'-',{
	      			text:'删除',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-custom_deleteuser',
	      			handler: function(){
	      				deleteItem('');
	      		    }
	      		},'-',{
	      			text:'冻结',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-custom_freeze',
	      			handler: function(){
	      				accountFrozen('');
	      		    }
	      		},'-',{
	      			text:'解冻',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-custom_thaw',
	      			handler: function(){
	      				accountThaw('');
	      		    }
	      		},'-',{
	      			text:'授予设备',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-fuyu',
	      			handler: function(){
	      				awardedDevice('');
	      		    }
	      		},'-',{
	      			text:'刷新',
	      			width:'100',
	      	    	height:'40',
	      			iconCls: 'icon-custom_refresh',
	      			handler: function(){
	      				reloadTable('');
	      		    }
	      		}
	      	],
			columns:[[
				{field:'ck',width:'3%',checkbox:"true"},
				{field:'rowKey',title:'ID',align:'left',hidden:'true'},		
				{field:'account',title:'账户名',width:'7%',align:'center',sortable:true},
				{field:'nickName',title:'昵称',width:'6%',align:'center',sortable:true},
				{field:'realName',title:'真实姓名',width:'6%',align:'center',sortable:true},
				{field:'airlineName',title:'组织',width:'9%',align:'center',sortable:true},
				{field:'airportName',title:'机场',width:'8%',align:'center',sortable:true},
				/* {field:'airpor	t',title:'用户组',width:'5%',align:'center',formatter:function(value,row,index){
					return '<a class="airportView" id="'+row.rowKey+'" href="javascript:void();">查看</>';
				}}, */
				{field:'province',title:'省份',width:'5%',align:'center',formatter:function(value,row,index){
					if(row.province=="BEIJING"){
						return '北京';
					}else if(row.province=="TIANJIN"){
						return '天津';
					}else if(row.province=="SHANGHAI"){
						return '上海';
					}else if(row.province=="CHONGQING"){
						return '重庆';
					}else if(row.province=="HEBEI"){
						return '河北';
					}else if(row.province=="SHANXI1"){
						return '山西';
					}else if(row.province=="LIAONING"){
						return '辽宁';
					}else if(row.province=="JILIN"){
						return '吉林';
					}else if(row.province=="HEILONGJIANG"){
						return '黑龙江';
					}else if(row.province=="JIANGSU"){
						return '江苏';
					}else if(row.province=="ZHEJIANG"){
						return '浙江';
					}else if(row.province=="ANHUI"){
						return '安徽';
					}else if(row.province=="FUJIAN"){
						return '福建';
					}else if(row.province=="JIANGXI"){
						return '江西';
					}else if(row.province=="SHANDONG"){
						return '山东';
					}else if(row.province=="HEBEI"){
						return '河北';
					}else if(row.province=="HENAN"){
						return '河南';
					}else if(row.province=="HUNAN"){
						return '湖南';
					}else if(row.province=="GUANGDONG"){
						return '广东';
					}else if(row.province=="HAINAN"){
						return '海南';
					}else if(row.province=="SICHUAN"){
						return '四川';
					}else if(row.province=="GUIZHOU"){
						return '贵州';
					}else if(row.province=="YUNNAN"){
						return '云南';
					}else if(row.province=="SHANXI2"){
						return '陕西';
					}else if(row.province=="GANSU"){
						return '甘肃';
					}else if(row.province=="QINGHAI"){
						return '青海';
					}else if(row.province=="TAIWAN"){
						return '台湾';
					}else if(row.province=="NEIMENGGU"){
						return '内蒙古';
					}else if(row.province=="GUANGXI"){
						return '广西';
					}else if(row.province=="XIZANG"){
						return '西藏';
					}else if(row.province=="NINGXIA"){
						return '宁夏';
					}else if(row.province=="XINJIANG"){
						return '新疆';
					}else if(row.province=="XIANGGANG"){
						return '香港';
					}else if(row.province=="AOMEN"){
						return '澳门';
					}
				}
				},
				{field:'city',title:'城市',width:'6%',align:'center',sortable:true},
				{field:'street',title:'街道',width:'8%',align:'center',sortable:true},
				{field:'phone',title:'电话',width:'8%',align:'center',sortable:true},
				{field:'email',title:'邮箱',width:'8%',align:'center',sortable:true},
				{field:'weixin',title:'微信',width:'8%',align:'center',sortable:true},
				{field:'userStatus',title:'用户状态',width:'6%',align:'center',formatter:function(value,row,index){
					if(row.userStatus==1){
						return '<span class="waitCheck">正常</span>';
					}else if(row.userStatus==2){
						return '<span>冻结</span>';
					}
				},styler:function(value,row,index){
					if(row.userStatus==2){
						return 'background-color:#0DC8E4;color:#302C3F;'
					}
				}},
				{field:'operator',title:'操作',width:'12%',align:'left',formatter:function(value,row,index){
					return '<a class="editCls" href="javascript:editItemInit(\''+row.id+'\');"></a>&nbsp<a class="editpwd" href="javascript:updatepwd(\''+row.id+'\');"></a>';
				}},		
		    ]],
		    onBeforeLoad:function(data){
		    	
	            	//添加搜索条件
			        if($(".searchBox").length == 0){
				        var searchTool = '<div class="searchBox" style="padding:3px 0 0 15px;height:40px;line-height:30px;">';
				        searchTool+='账户名:&nbsp;<input id="account" style="width:150px;height:25px;"></td>&nbsp;&nbsp;&nbsp;';
				        searchTool+='组织:&nbsp;<input id="airlineName" style="width:150px;height:25px;"></td>&nbsp;&nbsp;&nbsp;';
				        searchTool+='机场:&nbsp;<input id="airportName" style="width:150px;height:25px;"></td>&nbsp;&nbsp;&nbsp;';
				        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
				        searchTool+='</div>';
				        $(".datagrid-toolbar").append(searchTool);
				        $("#account").textbox({});
				        $("#airlineName").textbox({});
				        $("#airportName").textbox({});
						//初始活动类型选择combobox
				        $('#search').linkbutton({
				        	iconCls:'icon-search', 
				        });
			        }
	            	if("${1==1}"){
	            		$("#search").hide();
	            	}
			    },
		    onLoadSuccess:function(data){  
		    	if(data.result=='0'){
    	    		$.messager.alert('提示',data.message,'error');
    	    		return;
    	    	}
		    	
// 		    	alert(preRowKey+"---"+nextRowKey);
		    	//初始化编辑按钮
		        $('.editCls').linkbutton({text:'编辑',plain:true,iconCls:'icon-custom_edit'});
		    	//初始化修改密码按钮
		        $('.editpwd').linkbutton({text:'改密',plain:true,iconCls:'icon-man'});
		      //初始化修改密码按钮
		        $('.editUserDeviceId').linkbutton({text:'赋予设备',plain:true,iconCls:'icon-airline'});
		    	//初始化表格提示插件
		        $('#userList').datagrid('doCellTip', {
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
		        	var userId = $(this).attr("id");
		        	$("#"+userId).tooltip({
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
		        	var userId = $(this).attr("id");
		        	$.ajax({
						type:"post",
						async:false,//jax同步
						url:'<%=path%>/user/getUserGroupsInfo.do',
						data:{userId:userId},
						global:true,//使用全局的ajax
						success:function(html, textStatus){
							$("#"+userId).tooltip("update",html);
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							alert("网络错误，请稍后重试!");
						},
						dataType:"html"
					});
		        },function(){
		        	$("#"+userId).tooltip("hide");
		        });
		    }
		}); 
		/* $("#userList").datagrid({
	        onBeforeLoad:function(prm){
	        	prm.rowKey = $("#lastRowKey").val();
	        }
		}); */
		//initPage($('#userList'));
	});
	//刷新表格
	function reloadTable(){
	  $('#userList').datagrid('load', {
			account:$("#account").val(),
			airportName:$("#airportName").val(),
	  		airlineName:$("#airlineName").val()
    		/* keyWords:$("#search").val(),
    		areaId:$("#areaId").val(),
    		activityType:$("#activityTypeSearch").val(),  */
 		}); 
		//toHomePage();
	}
	
	//编辑条目
	function editItemInit(id){
		var dialog = $('<div></div>').dialog({
			title:id != "" ? "编辑用户" : "添加用户",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/user/addOrEditInit.do?id='+id,
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
                	//$form.form('validate')
                	if(validateUserForm()){
                		$("#airName").val($("#airportCode").combobox("getText"));
                		$.ajax({
                    		type:'post',
                    		url:$form.attr("action"),
                    		data:$form.serialize(),
                    		success:function(data){
                    			if(data.accountrepeat=="1"){
                    				$.messager.alert('操作失败',"该账户已经存在！请重新输入",'error');
                    			}else{
                    				if(data.result == "1"){
                        				$.messager.alert('提示',"操作成功",'info', function(r){
                        					dialog.dialog('destroy');
                        					reloadTable();
                        			    });
                        			}else{
                        				$.messager.alert('操作失败',data.message,'error');
                        			}
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
		var checkedItems = $('#userList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要删除的用户",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/user/deleteUser.do",
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
	//批量账户冻结
	function accountFrozen(){
		var ids = [];
		var checkedItems = $('#userList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要冻结的用户",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定冻结选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/user/updateUserFrozen.do",
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
	//批量用户账户解冻
	function accountThaw(){
		var ids = [];
		var checkedItems = $('#userList').datagrid('getChecked');
		$.each(checkedItems,function(index,item){
			ids.push(item.id);
		});
		if(ids.length == 0){
			$.messager.alert('提示',"请选择要解冻的用户",'warning');
			return;
		}
		$.messager.confirm("确认信息","确定解冻选中的"+ids.length+"条数据吗？", function (r) {  
	        if (r){  
	        	$.ajax({
	        		type:'post',
	        		url:"<%=path%>/user/updateAccountThaw.do",
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
	
	//给用户授予设备
	 function awardedDevice(){
		 var ids = [];
		 
			var checkedItems = $('#userList').datagrid('getChecked');
			$.each(checkedItems,function(index,item){
				ids.push(item.id);
			});
			if(ids.length == 0){
				$.messager.alert('提示',"请选择一个要授予的用户",'warning');
				return;
			}
			if(ids.length > 1){
				$.messager.alert('提示',"只能给一个用户授予",'warning');
				return;
			}
			var dialog = $('<div id="synchid"></div>').dialog({
				title:'用户授予设备页面',
				height:'80%',
				width:'30%',
				top:'10%',
				minimizable:false,  
			    maximizable:false,  
			    collapsible:false,  
			    shadow: true,  
			    modal: true,  
				href:'<%=path%>/user/userDeviceInit.do' ,
				onClose:function(){
					$(this).dialog('destroy');
				},
	            buttons : [
	              {
	                text : '授予',
	                iconCls : 'icon-save',
	                handler : function(){
	                	console.log("来到了点击提交的页面") 
	                	     $.messager.progress({
						      	   title:'Please waiting',
						     	   msg:'正在授予设备，请稍后...'
						    }); 
						    var deviceIds= [];
						    var nodes = $('#viproomTree').tree('getChecked');
         			 		$.each(nodes,function(index,node){
         			 			    var type = node.attributes.type;
         			 			    if(type == "5"){
         			 			    	deviceIds.push(node.id);
         			 			    }
         			 			  deviceArrayString = deviceIds.join(",");
         			 		})
         			 		
         			 		console.log(deviceIds);
	                		$.ajax({
	                    		type:'post',
	                    		async:true,
	                    		url:'<%=path%>/user/addUserDevice.do',
	                    		data:{
 	                    			 	deviceId:deviceArrayString,
	                    				userId:ids	[0]
	                    		},
	                    		success:function(data){
	                    			if(data.result == "1"){
	                    				$.messager.alert('提示',"授予设备成功，请重新登陆该账户获得设备权限！",'info', function(r){
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
	
	
	//修改密码
	function updatepwd(id){
		
		var dialog = $('<div></div>').dialog({
			title:"修改密码",
			height:'40%',
			width:'35%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/user/updatepwdInit.do?id='+id,
			onClose:function(){
				$(this).dialog('destroy');
			},
            buttons : [
              {
                text : '提交',
                iconCls : 'icon-save',
                handler : function() {
                	//提交表单
                	var $form = $("#updatapwd");
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
                		$.messager.alert('提示',"请正确填写表单数据",'error');
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
	
	window.onload=function(){alert(1);
		
			
		
		
	}
	</script>
<table id="userList" style="width: 100%; margin: 0px;"></table>