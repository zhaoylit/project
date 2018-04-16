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
</style>
<script type="text/javascript"> 
	$(function() { 		 	
		 	/*  加载页面时初始化数据表格 */
			$('#tableList').datagrid({
// 			title:'设备列表',
		    url:'<%=path%>/device/deviceMtnList.do',
		    toolbar:'#tb',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"rowKey",
		    pagination:true,
		   // pageSize:10,
		   // pageList:[5,10,15,20,100],//每页的个数  
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
	      				editDeviceMtnInit('');
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
				{field:'deviceId',title:'设备Id',width:'18%',align:'center'},
				{field:'describe',title:'问题描述',width:'18%',align:'center'},		
				{field:'mtnState',title:'设备状态',width:'18%',align:'center'},
				{field:'operator',title:'维护操作者',width:'18%',align:'center'},
				{field:'mtnDate',title:'维护日期',width:'18%',align:'center'},
				{field:'operators',title:'操作',width:'9%',align:'center',formatter:function(value,row,index){
					return '<a class="editCls" href="javascript:editDeviceMtnInit(\''+row.rowKey+'\');"></a>';
				}}
		    ]],
		    onBeforeLoad:function(data){
		    	//添加搜索条件
		        if($(".searchBox").length == 0){
			        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:40px;line-height:30px;">';
                 // searchTool+='<td>设备编号:&nbsp;<input id="deviceNo" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
                    searchTool+='<td>开始日期:&nbsp;<input id="dateStart" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
                    searchTool+='<td>结束日期:&nbsp;<input id="dateEnd" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
			        searchTool+='<td><a id="search" onclick="reloadTable()" style="width:8%;height:32px">查询</a></td>';
			        searchTool+='</div>';
			        $(".datagrid-toolbar").append(searchTool);
			        //初始化地区选择combobox
			        <%-- $.post("<%=path%>/device/gerAreaJson.do",{},function(data){ --%>
			        	/* $('#deviceNo').textbox({});  */
			        	$('#dateStart').datetimebox({}); 
			        	$('#dateEnd').datetimebox({}); 
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
		    	//初始化表格提示插件
	        	if(data.result=='0')
	        		$.messager.alert('提示',data.message, '');
	      
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
	//刷新表格
	function reloadTable(){
		$('#tableList').datagrid('load', {
			beginTime:$("#dateStart").datetimebox('getValue'),
			endTime:$("#dateEnd").datetimebox('getValue')
 		});
	}
	//编辑条目
	function editDeviceMtnInit(rowKey){
		var dialog = $('<div></div>').dialog({
			title:rowKey != "" ? "编辑设备维护记录" : "添加设备维护记录",
			height:'80%',
			width:'60%',
			top:'10%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/device/addOrEditDeviceMtnInit.do?rowKey='+rowKey,
			onClose:function(){
				$(this).dialog('destroy');
			},
            buttons : [
              {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                	//提交表单
                	var $form = $("#addOrEditMtn");
                	if(validateDeviceMtnForm()){
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
		var checked = $('#tableList').datagrid('getChecked');
		$.each(checked,function(index,item){
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
	        		url:"<%=path%>/device/deleteDeviceMtn.do",
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

<script type="text/javascript">
$(function(){
	//初始化嵌入点
	$("#insetPoint").combobox({
		url:'${ctx}/device/getDictJson.do?dictName=INSET_POINT',  
        editable:false,
        valueField:'dictKey', 
        textField:'dictValue',  
        onSelect:function(district){
        	
        },
        onLoadSuccess:function(){
        	//设置默认值
        	$('#insetPoint').combobox('select',"${resultInfo.insetPoint}");
        }
	});
})
</script>
