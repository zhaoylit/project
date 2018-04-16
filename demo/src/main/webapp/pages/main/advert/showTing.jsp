<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			$('#showTingTable').datagrid({
		    url:'advert/getDeviceInfoJson.do?airportCode=${result.airportCode}&deviceType=1',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"viproomId",
		    pagination:false, 
		    singleSelect:false,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    sortName:'viproomId',
		    sortOrder:'desc',
		    queryParams: {
		    },
		    toolbar: [],
			columns:[[
				{field:'ck',width:'5%',checkbox:"true"},    
                {field:'viproom',title:'同步的厅服务器',width:'46%',align:'center'},
                {field:'connectionStatus',title:'厅服务器状态',width:'46%',align:'center',formatter:function(value,row,index){
					if(row.connectionStatus=='1'){
						return "已连接"
					}else{
						return "未连接"
					}
				},styler:function(value,row,index){
					if(row.connectionStatus=='1'){
						return 'background-color:#00FF00;color:#000000;'	
					}else{
						return 'background-color:red;color:#fff;'
					}
				}},	
		    ]],
		    onBeforeLoad:function(data){
		    	//添加搜索条件
		        /* if($(".searchBoxTing").length == 0){
		        	 var searchTool = '<div class="searchBoxTing" style="padding:3px 0 0 15px;height:40px;line-height:30px;">';
		        	searchTool+='厅服务器状态:&nbsp;<select id="tingstateId33" class="easyui-combobox" name="tingstate" style="width:200px;"><option value="">未连接</option><option value="1">已连接</option></select>';
			        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;display:inline-block">搜索</a> ';
			        searchTool+='</div>';
			        $("#synchid").find(".datagrid-toolbar").append(searchTool);
			        
					//初始活动类型选择combobox
			        $('#search').linkbutton({
			        	iconCls:'icon-search', 
			        }); 
			        
		        }*/
		    },
		    onLoadSuccess:function(data){
		    	if(data.result=='0'){
    	    		$.messager.alert('提示',data.message,'error');
    	    		return;
    	    	}
		    	
		    	//初始化表格提示插件
		        $('#showTingTable').datagrid('doCellTip', {
		            onlyShowInterrupt : true,
		            position : 'bottom',
		            maxWidth : '200px',
		            specialShowFields : [{
		                field : 'viproom',
		                showField : 'viproom'
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
		toHomePage();
	}
	</script>
<table id="showTingTable" style="width:100%;margin:0px;"></table>
