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
</head>
<body>
<script type="text/javascript">
    $(function() {
        /*  加载页面时初始化数据表格 */
        $('#flight_query').datagrid({
            title:'航班提醒页面查询列表',
            url:'<%=path%>/notice/getNoticeFlightByParam.do',
            toolbar:'#tb',
            fit:true,
            method:'post',
            loadMsg:'数据加载中,请稍等...',
            fitColumns:true,
            idField:"rowKey",
            pagination:true,
            pageSize:10,
            pageList:[5,10,15,20,100],//每页的个数
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck: true,
            sortName:'rowKey',
            sortOrder:'desc',
            queryParams: {
            },
            toolbar: [],
            columns:[[
                {field:'rowKey',title:'id',width:'8%',align:'center',hidden:'true'},      
				{field:'flightNo',title:'航班号',width:'8%',align:'center'},
				{field:'fs',title:'航班状态',width:'10%',align:'center',formatter:formatStatus},
				{field:'delayRes',title:'延误原因',width:'13%',align:'center',formatter:formatReason},
				{field:'depCity',title:'出发地',width:'10%',align:'center'},
				{field:'passCity',title:'经停地',width:'10%',align:'center'},
				{field:'arrCity',title:'目的地',width:'10%',align:'center'},
				{field:'gate',title:'登机口',width:'10%',align:'center'},
				{field:'operator',title:'发布者',width:'12%',align:'center'},
				{field:'createTime',title:'发布时间',width:'14%',align:'center'},
            ]],
            onBeforeLoad:function(data){
                //添加搜索条件
                if($(".searchBox").length == 0){
                    var searchTool = '<div class="searchBox" style="padding:3px 0 0 3px;line-height:3rem;">';
                    searchTool+='<td>航班号:&nbsp;<input id="flightNo" style="width:20%;height:30px;"></td>&nbsp;&nbsp;&nbsp;&nbsp;';
                    searchTool+='<td style="">开始时间:&nbsp;<input id="beginTime" class="easyui-datebox" style="width:20%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;';
                    searchTool+='<td style="">结束时间:&nbsp;<input id="endTime"   class="easyui-datebox"  style="width:20%;height:30px;">';
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
/*             	if(data.preRowKey != ""){
			    	preRowKey = data.preRowKey;
			    	nextRowKey = data.nextRowKey;
		    	}else{
		    		preRowKey = nextRowKey;
		    	}
                //初始化表格提示插件
                $('#flight_query').datagrid('doCellTip', {
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
     //   initPage($('#flight_query'));
    });

    // 所有调取的方法都要在这里以下写

    //刷新表格
    function reloadTable(){
    //	toHomePage();
         $('#flight_query').datagrid('load', {
        	flightNo:$("#flightNo").combobox('getValue'),
        	beginTime:$("#beginTime").datetimebox('getValue'),
        	endTime:$("#endTime").datetimebox('getValue')
        }); 
    }
    function formatStatus(val, row) {
		if (val != null) {
			switch(val){
			case '01':
				return "开始登机";
				break;
			case "02":
				return "登机口变更";
				break;
			case "03":
				return "航班延误";
			case "04":
				return "航班取消";
			}
		}
	}
	
	function formatReason(val, row) {
		if (val != null) {
			switch(val){
			case 'C01':
			case 'E01':
				return "航路天气";
				break;
			case "C02":
			case "E02":	
				return "周边天气";
				break;
			case "C03":
			case "E03":
				return "本场天气";
			case "C04":
			case "E04":	
				return "流量控制";
			}
		}
		
		if(val==''){
			return "---";
		}
	}

</script>
    <table id="flight_query"  class="easyui-datagrid"  pagination="true"  style="width:100%;margin:20px;"></table>
	
</body>
</html>




