<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
     
</style>
</head>
<body>
<script type="text/javascript">
    $(function() {
        /*  加载页面时初始化数据表格 */
        $('#Per_query').datagrid({
            title:'找人列表查询',
            url:'<%=path%>/notice/getNotePerson.do',
            toolbar:'#tb',
            fit:true,
            method:'post',
            loadMsg:'数据加载中,请稍等...',
            fitColumns:true,
            idField:"rowKey",
            pagination:true,
            //pageSize:10,
            //pageList:[5,10,15,20,100],//每页的个数
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck: true,
            sortName:'rowKey',
            sortOrder:'desc',
            queryParams: {
            },
            toolbar: [],
            columns:[[
				{field:'number',title:'序号',width:'5%',aligen:'center'},
				{field:'rowKey',title:'ID',width:'10%',align:'center',hidden:'true'},
				{field:'flightNo',title:'航班号',width:'15%',align:'center'},
				{field:'name',title:'旅客姓名',width:'15%',align:'center'},
				{field:'msg',title:'提醒内容',width:'15%',align:'center'},
				{field:'operator',title:'发布者',width:'15%',align:'center'},
				{field:'createTime',title:'发布时间',width:'15%',align:'center'},
                {field:'rowKey',title:'id',width:'8%',align:'center',hidden:'true'}
            ]],
            onBeforeLoad:function(data){
                //添加搜索条件
                if($(".searchBox").length == 0){
                    var searchTool = '<div class="searchBox" style="padding:3px 0 0 3px;line-height:3rem;">';
                    searchTool+='<td>航班号:&nbsp;<input id="flightNo" style="width:20%;height:30px;"></td>&nbsp;&nbsp;&nbsp;&nbsp;';
                    searchTool+='<td style="">开始时间:&nbsp;<input id="beginTime" class="easyui-datetimebox" style="width:20%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;';
                    searchTool+='<td style="">结束时间:&nbsp;<input id="endTime" style="width:20%;height:30px;">';
                    searchTool+='<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a></td>'
                    searchTool+='</div>';
                    $(".datagrid-toolbar").append(searchTool);
                    //初始化机场数据
                    $.post("<%=path%>/notice/getFlightInfo.do",{},function(data){
	                    $('#flightNo').combobox({
	                    	 data:data,
			        		 editable:true,
			        		 valueField:'flightNo',
			        		 textField:'flightNo'
	                    });
                     },"json"); 
                    // 初始化开始时间数据
                    $('#beginTime').datetimebox({
                        showSeconds: false
                    });
                     // 初始化结束时间数据
                    $('#endTime').datetimebox({
                        showSeconds: false
                    });
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
            	/* if(data.preRowKey != ""){
			    	preRowKey = data.preRowKey;
			    	nextRowKey = data.nextRowKey;
		    	}else{
		    		preRowKey = nextRowKey;
		    	} */
                //初始化表格提示插件
/*                 $('#flight_query').datagrid('doCellTip', {
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
                }); */
            }
        });
      //  initPage($('#Per_query'));
    });

    // 所有调取的方法都要在这里以下写

    //刷新表格
    function reloadTable(){
    	//toHomePage();
         $('#Per_query').datagrid('load', {
        	flightNo:$("#flightNo").combobox('getValue'),
        	beginTime:$("#beginTime").datetimebox('getValue'),
        	endTime:$("#endTime").datetimebox('getValue'),
        }); 
    }

</script>
<table id="Per_query" style="width:100%;margin:20px;"  pagination="true"></table>
</body>
</html>