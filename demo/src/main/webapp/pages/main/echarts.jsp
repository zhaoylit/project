<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%
	String path = request.getContextPath()+"/pages";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<html>
<head>
<title>ECharts</title>
</head>
<body>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="height: 600px; width: 1350px"></div>

	<!-- 标签单文件导入 -->
	<script type="text/javascript" src="../plugins/echarts/dist/echarts-all.js"></script>
	
	<script type="text/javascript" src="../jquery-1.8.0.min.js"></script>
	<script>
		var myChart = echarts.init(document.getElementById('main'));

		var option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '可口可乐', '六个核桃', '奔驰', '宝马', '联想' ]
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar', 'stack', 'tiled',]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月',
						'十月', '十一月', '十二月' ]
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : []
		};
		
		//myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
		
		
		$.ajax({
	         type : "post",
	         async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
	         url : "<%=basePath%>log/echartsListJson.do",   
	         //请求发送到TestServlet处
	         data : {},
	         dataType : "json",        //返回数据形式为json
	         success : function(result) {
	             //请求成功时执行该函数内容，result即为服务器返回的json对象
	             if (result) {
	                    myChart.hideLoading();    //隐藏加载动画
	                    myChart.setOption({        //加载数据图表
	                        series: result
	                    });
	             }
	        },
	         error : function(errorMsg) {
	        	 alert(errorMsg.toString);
	             //请求失败时执行该函数
	         alert("图表请求数据失败!");
	         myChart.hideLoading();
	         }
	    })
		
		myChart.setOption(option);
	</script>
</body>
</html>