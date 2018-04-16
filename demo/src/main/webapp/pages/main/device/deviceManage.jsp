<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<div style="width:1000px;height:600px;margin-top:10px;padding-left:10px;">
	<div style="width:1000px;float:left;padding:2px 0 2px 10px;">
		<a href="javascript:void();" onclick="addAirline()">添加航空公司</a>
	</div>
    <div style="width:300px;float:left;">
   		<ul id="viproomTree"></ul>
    </div>
    <div id="deviceInfoShow" style="width:700px;float:right;text-align:left;">
    	<!-- loading提示框 -->
		<div id="loading" style="width:200px;height:30px;line-height:30px;display:none;padding-left:5px;border:1px solid gray;background-color:white;margin:0px 0 0 110px;position:absolute;">
		<img src="<%=path %>/images/device/loading.gif"/>
		加载中，请稍后。。。
		</div>
    	<iframe id="deviceInfoShowIframe" src="" style="border:0px;width:700px;height:600px;">
    		<span>请在左侧选择厅服务器查看设备信息</span>
    	</iframe>
    </div>
</div>
<script type="text/javascript">
	var curNode;
	$('#viproomTree').tree({
		onBeforeLoad:function(){
			$.messager.progress({
		      	   title:'Please waiting',
		     	   msg:'设备树形菜单加载中，请稍后。。'
		    });
		},
		method:"post",
	    url:'<%=path%>/device/getViproomTreeJson.do',
	    animate:true,
	    lines:true,
		onLoadSuccess:function(){
			$('#viproomTree').find(".tree-node").hover(function(){
				var operator = '<span class="operator" style="margin-left:10px;"  onmouseout="javascript:$(this).parent().parent().removeClass(\'tree-node-hover\')">';
				operator +='<a href="javascript:void();" onclick="editNode();"  onclick="editNode();" onmouseout="javascript:$(this).parent().parent().removeClass(\'tree-node-hover\')">编辑</a>';
				operator +='&nbsp;&nbsp;<a href="javascript:void();" onclick="addNode();" onmouseout="javascript:$(this).parent().parent().removeClass(\'tree-node-hover\')">添加</a>';
				operator +='&nbsp;&nbsp;<a href="javascript:void();" onclick="deleteNode();" onmouseout="javascript:$(this).parent().parent().removeClass(\'tree-node-hover\')">删除</a>';
				operator += '</span>';
				//显示操作菜单
				$(this).append(operator);
			},function(){
				//隐藏操作菜单
				$(this).find(".operator").remove();
			})
			$.messager.progress('close');
		},
		onSelect:function(node){
			
		},
		onClick: function(node){
			curNode = node;
			//获取类型
			var type = node.attributes.type;
			if(type == "3"){
				var airlineId = node.attributes.airlineId;
				var airportId = node.attributes.airportId;
				var viproomId = node.id;
				viproomId_ = viproomId;
				$("#deviceInfoShowIframe").attr("src","<%=path%>/device/deviceInfoShow.do?viproomId="+viproomId);
			}else if(type == "4"){
				$("#deviceInfoShowIframe").attr("src","<%=path%>/device/deviceInfoShow.do?isOther=1&viproomId=0");
			}
		}
	});
	//编辑节点
    function editNode(){
		setTimeout(function(){
			var type = curNode.attributes.type;
	    	var id = curNode.id;
	    	var title="编辑航空公司名称";
	    	if(type == "2"){
	    		title="编辑机场名称";
	    	}else if(type == "3"){
	    		title = "编辑vip厅的名称";
	    	}else if(type == "4"){
	    		$.messager.alert('提示信息',"此分类不支持编辑操作！",'info');
	    		return;
	    	}
	    	var dd = $('<div></div>').dialog({
				title:title,
				height:'400',
				width:'600',
				top:'20%',
				minimizable:false,  
			    maximizable:false,  
			    collapsible:false,  
			    shadow: true,  
			    modal: true,
			    href:'<%=path%>/device/addOrEditDeviceTreeInfoInit.do?operator=edit&id='+id+'&type=' + type,
				onClose:function(){
					dd.dialog('destroy');
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
				        		url:$form.attr("action"),
				        		data:$form.serialize(),
				        		success:function(data){
				        			if(data.result == "1"){
				        				$.messager.alert('提示',"操作成功",'info', function(r){
				        					dd.dialog('destroy');
				        			    });
				        			}else{
				        				$.messager.alert('操作失败',"操作失败",'error');
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
	                	dd.dialog('destroy');
	                }
	              }
	            ]
			});
		},50);
    }
	function addAirline(){
		var dd = $('<div></div>').dialog({
			title:"添加航空公司",
			height:'300',
			width:'350',
			top:'20%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,
		    href:'<%=path%>/device/addOrEditDeviceTreeInfoInit.do?operator=add&type=0',
			onClose:function(){
				dd.dialog('destroy');
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
			        		url:$form.attr("action"),
			        		data:$form.serialize(),
			        		success:function(data){
			        			if(data.result == "1"){
			        				$.messager.alert('提示',"操作成功",'info', function(r){
			        					dd.dialog('destroy');
			        			    });
			        			}else{
			        				$.messager.alert('操作失败',"操作失败",'error');
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
                	dd.dialog('destroy');
                }
              }
            ]
		});
	}
	//添加节点
	function addNode(){
		setTimeout(function(){
			var type = curNode.attributes.type;
	    	var id = curNode.id;
	    	var airlineId = curNode.attributes.airlineId;
	    	var airportId = curNode.attributes.airportId;
	    	var title="添加机场信息";
	    	if(type == "2"){
	    		title="添加vip厅信息";
	    	}else if(type == "3"){
	    		title = "添加设备信息";
	    	}else if(type == "4"){
	    		$.messager.alert('提示信息',"此分类不支持添加操作！",'info');
	    		return;
	    	}
	    	var dd = $('<div></div>').dialog({
				title:title,
				height:'300',
				width:'350',
				top:'20%',
				minimizable:false,  
			    maximizable:false,  
			    collapsible:false,  
			    shadow: true,  
			    modal: true,
			    href:'<%=path%>/device/addOrEditDeviceTreeInfoInit.do?operator=add&id='+id+'&type=' + type + '&airlineId=' + airlineId + '&airportId='+airportId,
				onClose:function(){
					dd.dialog('destroy');
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
				        		url:$form.attr("action"),
				        		data:$form.serialize(),
				        		success:function(data){
				        			if(data.result == "1"){
				        				$.messager.alert('提示',"操作成功",'info', function(r){
				        					dd.dialog('destroy');
				        			    });
				        			}else{
				        				$.messager.alert('操作失败',"操作失败",'error');
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
	                	dd.dialog('destroy');
	                }
	              }
	            ]
			});
		},50);
	}
	function deleteNode(){
		setTimeout(function(){
			var type = curNode.attributes.type;
	    	var id = curNode.id;
	    	if(type == "4"){
	    		$.messager.alert('提示信息',"此分类不支持删除操作！",'info');
	    		return;
	    	}else{
	    		$.messager.confirm("确认信息","确定删除该节点和下面的子节点的所有信息,包括设备信息？", function (r) {
	    			if (r){
	    				$.ajax({
			        		type:'post',
			        		url:"<%=path %>/device/deleteDeviceTreeInfo.do",
			        		data:{
			        			type:type,
			        			id: id
			        		},
			        		success:function(data){
			        			if(data.result == "1"){
			        				$.messager.alert('提示',"操作成功",'info', function(r){
			        					dd.dialog('destroy');
			        			    });
			        			}else{
			        				$.messager.alert('操作失败',"操作失败",'error');
			        			}
			        		},
			        		dataType:'json'
			        	});
	    			}
	    		});
	    	}
		},50);
	}
</script>