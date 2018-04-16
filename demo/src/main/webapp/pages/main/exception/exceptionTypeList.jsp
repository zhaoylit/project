<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>


<script>
$(function() { 		 	
 	/*  加载页面时初始化数据表格 */
	$('#tableExceptionList').datagrid({
//	title:'异常记录列表',
    url:'<%=path%>/exception/exceptionTypeList.do',
    fit:true,
    method:'post',
    loadMsg:'数据加载中,请稍等...',
    fitColumns:true,
    idField:"id",
    //pagination:true,
	//pageSize:2,
	//pageList:[2,10,15,20,100],//每页的个数  
    singleSelect:false,
    checkOnSelect:true,
    selectOnCheck: true,
    sortName:'id',
    sortOrder:'desc',
    queryParams: {
    },
    toolbar: [
  	   /*  {
  	    	text:'添加异常类型',
  			iconCls: 'icon-edit',
  	    	height:'40',	
  			width:'130',
  			handler: function(){
  				editTypeItemInit('');
  		    }
  		},'-',{
  			text:'删除异常类型',
  			width:'130',
  	    	height:'40',
  			iconCls: 'icon-remove',
  			handler: function(){
  				deleteExceptionTypeItem('');
  		    }
  		} */
  	],
	columns:[[
		/* {field:'ck',width:'8%',checkbox:"true"},		 */
		{field:'exceptionCode',title:'异常代码',width:'25%',align:'center'},
		//{field:'message',title:'异常信息',width:'10%',align:'center'},
		//{field:'module',title:'异常地区',width:'10%',align:'center'},
		{field:'exceptionType',title:'异常类型',width:'20%',align:'center'},
		//{field:'viproom',title:'vip厅',width:'12%',align:'center'},
		{field:'beginTimeType',title:'异常类型创建时间',width:'20%',align:'center'},
		{field:'scope',title:'异常范围',width:'15%',align:'center',formatter:function(value,row,index){
			if(row.scope=='AN'){
				return '<span>安卓端</span>';
			}else if(row.scope=='SE'){
				return '<span>后台</span>';
			}
		}},
		{field:'grade',title:'异常等级',width:'20%',align:'center',formatter:function(value,row,index){
			if(row.grade==1){
				return '<span>Blocker(致命,一级)</span>';
			}else if(row.grade==2){
				return '<span>Major(严重，二级)</span>';
			}else if(row.grade==3){
				return '<span>Normal(一般，三级)</span>';
			}else if(row.grade==4){
				return '<span>Minor(轻微，四级)</span>';
			}
		},styler:function(value,row,index){
			if(row.grade==1){
				return 'background-color:#FF3333;color:#302C3F;'
			}else if(row.grade==2){
				return 'background-color:#FF7777;color:#302C3F;'
			}else if(row.grade==3){
				return 'background-color:#EBD0D0;color:#302C3F;'
			}else if(row.grade==4){
				return 'background-color:#CCCCCC;color:#302C3F;'
			}
		}},
		/* {field:'operator',title:'操作',width:'20%',align:'center',formatter:function(value,row,index){
			return '<a class="editClsa" href="javascript:editTypeItemInit(\''+row.id+'\');"></a>';
		}},		 */
    ]],
    onBeforeLoad:function(data){
    	//添加搜索条件
        if($(".searchBox").length == 0){
	        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:40px;line-height:30px;">';
	        searchTool+='异常代码:&nbsp;<input id="exceptionCode" style="width:10%;height:30px;"/>&nbsp;&nbsp;';	
	        searchTool+='异常状态:&nbsp;<input id="exceptionState" style="width:10%;height:30px;">&nbsp;&nbsp;';
	        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTableaa()" style="width:80px;height:30px;">搜索</a> ';
	        searchTool+='</div>';
	        $(".datagrid-toolbar").append(searchTool);
        }
    },
	    onLoadSuccess:function(data){ 
	    	if(data.result=='0'){
	    		$.messager.alert('提示',data.message,'error');
	    		return;
	    	}
	    	//初始化编辑按钮
	        $('.editClsa').linkbutton({text:'编辑异常类型',plain:true,iconCls:'icon-edit'});
	    	//初始化表格提示插件
	    }
	});  
	});
	//刷新表格
	function reloadTableaa(){
	//toHomePage();
		$('#tableExceptionList').datagrid('reload');
	}
	
	
	//编辑异常类型条目
	function editTypeItemInit(id){
		var dialogaaab = $('<div></div>').dialog({
			title:id != "" ? "编辑异常类型" : "添加异常类型",
			height:'60%',
			width:'40%',
			top:'20%',
			minimizable:false,  
		    maximizable:false,  
		    collapsible:false,  
		    shadow: true,  
		    modal: true,  
			href:'<%=path%>/exception/addOrEditexceptionTypeInit.do?id='+id,
			onClose:function(){
				$(this).dialog('destroy');
			},
            buttons : [
              {
                text : '提交',
                iconCls : 'icon-save',
                handler : function() {
                	//提交表单
                	var $form = $("#addOrEditTypeForm");
                	
                		$.ajax({
                    		type:'post',
                    		url:'<%=path%>/exception/addOrEditExceptionType.do',
                    		data:$form.serialize(),
                    		success:function(data){
                    			if(data.result == "1"){
                    				$.messager.alert('提示',"操作成功",'info', function(r){
                    					dialogaaab.dialog('destroy');
                    					reloadTableaa();
                    			    });
                    			}else{
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
                	dialogaaab.dialog('destroy');
                }
              }
            ]
		});
	}
	
	
	//批量解决
	function deleteExceptionTypeItem(){
		var ids = [];
		var checkedItems = $('#tableExceptionList').datagrid('getChecked');
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
	        		url:"<%=path%>/exception/deleteExceptionType.do",
	        		data:{
	        			ids:ids.join(",")
	        		},
	        		success:function(data){
	        			if(data.result == "1"){
	        				$.messager.alert('提示',"操作成功",'info', function(r){
	        					reloadTableaa();
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

<table id="tableExceptionList" style="width:100%;margin:0px;"></table>