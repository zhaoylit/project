<%@ page language="java" import="java.util.*,com.zkkj.backend.common.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	//获取配置文件的websocket连接地址
	String websocketUrl =  (String)CustomizedPropertyConfigurer.getContextProperty("websockt_url");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	response.flushBuffer();
%>   
<script>
  var inn = ${inn},out = ${outt};
  var data = [['in',inn],['out',out]];
  //简历socket连接
  $(function(){
	//初始化 饼状图
	initChart(data);
	initPerson(inn,out);
	initInnTable();
	initOutTable();
	var ws = new WebSocket("<%=websocketUrl%>");
	ws.onopen = function () {
		
	};  
	ws.onmessage = function (event) { 
		var json = eval("(" + event.data + ")");//解析json字符串
		var type = json.type;
		if(type == "vip_person_in_out"){
			inn = json.inn;
			out = json.out;
			data = [['in',inn],['out',out]];
			initChart(data);
			initPerson(inn,out);
			initInnTable();
			initOutTable();
		}
	};  
	ws.onclose = function (event) {  
	    setConnected(false);  
	    log(event);  
	};  
	})
	<%-- setInterval(function(){
		//后台请求人数
		$.ajax({
			method:"post",
			url:"<%=path%>",
			data:{},
			success: function (data_){
				inn = data_.inn;
				out = data_.out;
			},
			dataType: "json"
			
		});
	},5000); --%>
	//初始化人数
	function initPerson(inn_,out_){
		$("#showInfo").find("font:eq(0)").html(inn_);
		$("#showInfo").find("font:eq(1)").html(out_);
	}
	//初始化表格
    function initChart(data_){
    	$('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '机场vip厅进出人数统计图'
	        },
	        tooltip: {
	            headerFormat: '{series.name}<br>',
	            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '人数占比',
	            data: data_
	        }]
	    }); 	 	  
    }
	//初始化进站乘客列表
	function initInnTable(){
		$('#innList').datagrid({
			title:'进站乘客列表',
		    url:'<%=path%>/getInnOrOutPersonJson.do?state=0',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    singleSelect:true,
		    selectOnCheck: true,
		    queryParams: {
		    },
		    toolbar: [
		              
	      	],
			columns:[[
				{field:'name',title:'乘客姓名',width:'20%',align:'center'},
				{field:'idcard',title:'身份证号',width:'30%',align:'center'},
				{field:'phone',title:'手机号码',width:'25%',align:'center'},
				{field:'enterTimeShow',title:'进站时间',width:'25%',align:'center'},
				
		    ]],
		    onBeforeLoad:function(data){
		    	
		    },
		    onLoadSuccess:function(data){
		    	
		    }
		}); 
	}
	//初始化出站乘客列表
	function initOutTable(){
		$('#outList').datagrid({
			title:'出站乘客列表',
		    url:'<%=path%>/getInnOrOutPersonJson.do?state=1',
		    fit:true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    singleSelect:true,
		    selectOnCheck: true,
		    queryParams: {
		    },
		    toolbar: [
		              
	      	],
			columns:[[
				{field:'name',title:'乘客姓名',width:'20%',align:'center'},
				{field:'idcard',title:'身份证号',width:'30%',align:'center'},
				{field:'phone',title:'手机号码',width:'25%',align:'center'},
				{field:'exitTimeShow',title:'出站时间',width:'25%',align:'center'},
		    ]],
		    onBeforeLoad:function(data){
		    	
		    },
		    onLoadSuccess:function(data){
		    	
		    }
		}); 
	}
</script>
<div id="container" style="height:400px;width:500px;border:0px solid blue;"></div>
<div id="showInfo" style="width:500px;height:50px;border:0px solid blue;text-align:center;font-weight:bold;font-size:20px;line-height:50px;">
   进站人数：<font style="color:red;"></font>&nbsp;&nbsp;&nbsp;出站人数：<font style="color:red;"></font>
</div>
<div style="height:200px;width:600px;border:0px solid black;margin:-450px 0 0 550px;">
	<table id="innList" style="width:100%;margin:0px;"></table>
</div>

<div style="height:200px;width:600px;border:0px solid black;margin:50px 0 0 550px;">
	<table id="outList" style="width:100%;margin:0px;"></table>
</div>
