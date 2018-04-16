<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>测试的拖拽功能</title>
<script src="<%=path %>/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js" type="text/javascript"></script> -->
<script src="<%=path %>/plug/gridly/jquery.gridly.js" type="text/javascript"></script>
<link href="<%=path %>/plug/gridly/jquery.gridly.css" rel="stylesheet" type="text/css" />
<style type="text/css">
  .gridly {
    position: relative;
    width: 900px;
  }
  .brick.small {
    position:absolute;
    height: 150px;
    width:100px;
    overflow:hidden
    cursor:move;
  }
  .brick.large {
    position:absolute;
    height: 300px;
    width:300px;
    overflow:hidden
    cursor:move;
  }
  img{cursor:move;}
  .img_1{
  	height:100%;
  }
  .img_2{
    width:50%;
  	height:100%;
  }
  .img_3{
    width:49%;
  	height:50%;
  }
  .show_index{
  	width:15px;
  	height:20px;
  	position:absolute;
    background-color:green;
    font-size:12px;
    color:white;
    padding:0 0 0 5px;
    line-height:20px;
  }
  .show_dele{
  	width:20px;
  	height:20px;
  	position:absolute;
    background-color:green;
    font-size:12px;
    color:white;
    margin:0 0 0 180px;
    padding:0 0 0 5px;
    line-height:20px;
    background:url(<%=path %>/plug/delete.png) no-repeat 1px 1px;
    background-size:20px 20px;
  }
</style>
</head>
<body>
<input type="button" value="添加" id="add"/>
<input type="button" value="获取排序后的位置" onclick="getDrag()"/>
<div class="gridly"> 
  <div class="brick large" id="1" index="1">
  	 <div class="show_index">1</div>
  	 <div class="show_dele" style="cursor:hand;"></div>
  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
  </div>
  <div class="brick large" id="2" index="2">
  	<div class="show_index">2</div>
  	<img class="img_3" style="float:left;" src="http://pic.qiantucdn.com/58pic/16/24/66/68w58PIC3Sg_1024.jpg"/>
  	<img class="img_3" style="float:right;" src="http://img9.3lian.com/c1/vec2013/10/2.jpg"/>
  	<img class="img_3"  src="http://img.sccnn.com/bimg/339/00077.jpg"/>
  </div> 
  <div class="brick large" id="3" index="3">
  	<div class="show_index">3</div>
  	<img class="img_2" style="float:left;" src="http://img.sccnn.com/bimg/328/454.jpg"/>
  	<img class="img_2" style="float:right;" src="http://img.zcool.cn/sucaiori/57A80C54-4E6A-F3C9-B30B-140A4262E452.jpg@700w_0e_1l.jpg"/>
  </div>
  <div class="brick large" id="4" index="4">
  	<div class="show_index">4</div>
  	 <img class="img_1" src="http://pic2.cxtuku.com/00/09/34/b0736957331b.jpg"/>
  </div>
  <div class="brick large" id="5" index="5">
  	<div class="show_index">5</div>
  	<img class="img_2" style="float:left;" src="http://pic.qiantucdn.com/58pic/16/24/66/68w58PIC3Sg_1024.jpg"/>
  	<img class="img_2" style="float:right;" src="http://img.zcool.cn/sucaiori/FB06F38C-EC92-571E-E75D-5070B2E1F5F2.jpg@700w_0e_1l.jpg"/>
  </div>
</div>
<script>
initzGridly();
 function initzGridly(){
	 $('.gridly').gridly({
	    base: 60, // px 
	    gutter: 10, // px
	    columns: 20,
	    callbacks:{ 
	    	reordering: function($elements){
		    	//拖动开始回调函数
		    }, 
		    reordered: function(data){
		    	//拖动完成回调
		    	//查看data元素的内容，alert(JSON.stringify(data));
		    	for(var i = 0;i < data.length;i++){
		    		//获取排序后的当前索引为i的div对象
		    		var current = data[i];
		    		//更改元素的左上角的显示索引
		    		$(current).find(".show_index").html(i + 1);
		    		//更改元素的index属性
		    		$(current).find(".show_index").parent().attr("index",i + 1);
		    	}
		    } 
	    }
	  });
 }
  $("#add").click(function(){
	 //获取第一个元素进行克隆
	 var clone = $(".brick:eq(0)").clone();
	 //获取div元素的总数
	 var len = $(".show_index").length;
	 //设置当前克隆元素左上角显示的索引为下一个
	 clone.find(".show_index").html(len + 1);
	 //设置div的索引为下一个，设置这个索引方便根据这个索引查找元素
	 clone.find(".show_index").parent().attr("index",len + 1);
	 //设置元素的唯一标示，正常这个是后台传过来的，这里为动态设置
	 clone.find(".show_index").parent().attr("id",len + 1);
	 //将克隆的div元素添加到最后面
	 $(".gridly").append(clone);
	 //重新初始化托送排序插件
	 initzGridly();
  });
  //获取排序后的元素id
  function getDrag(){
	  //获取当前所有拖动元素的长度
	  var length = $(".brick").length;
	  //排序后的id以逗号分隔开的字符串
	  var sort = "";
	  //循环按顺序取到排序后的每一个元素
	  for(var i = 0;i < length;i++){
		  //取到当前索引为(i+1)的元素的id
		  var id = $("[index='"+(i+1)+"']").attr("id");
		  sort+=id + ",";
	  }
	  alert("排序后为："+"-----" + sort.substr(0,sort.length-1)); 
  }  
</script>
</body>
</html>