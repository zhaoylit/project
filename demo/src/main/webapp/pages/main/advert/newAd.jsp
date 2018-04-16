<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
  .gridly {
    position: relative;
    width:100%;
    height:100%;
    border:solid 1px red;
  }
      .gridly1 {
	    position: relative;
	    width:100%;
	    height:100%;
	    border:solid 1px #000;
	    margin:0px !important;
	  }
  .brick.small {
    height: 150px;
    width:100px;
    overflow:hidden
    cursor:move;
  }
  img{cursor:move;}
  .img_1{
  	height:100%;
  	width:100%;
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
  
  .adbo{
      display:block;
      width:400px;
      
      border:solid 1px blue;
      
  }
</style>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/index/jquery.gridly.css">
<script type="text/javascript" src="<%=path %>/javascript/js/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path %>/javascript/jquery.gridly.js" charset="utf-8"></script>

	<input type="button" value="添加" id="add"/>
	<input type="button" value="获取排序后的位置" onclick="getDrag()"/>
	<div class="gridly">
	       
	       <!-- ----外层循环广告商-----可拖动----- -->
	       
	      <div class="adbo">
	           <!----------每个广告商的播放轮数---可拖动-----  -->
	              <div class="gridly1">
	                     <!-- 每轮的显示 -->
	                     <div class="gridly2">
	                          <h3>第一轮的播放图片</h3>
	                          <div style="width:100px;height:200px;border:solid 1px red;">
	                          
	                          </div>
	                     </div>
	                     <div class="gridly2">
	                          <h3>第二轮的播放图片</h3>
	                          <div style="width:100px;height:200px;border:solid 1px red;">
	                          
	                          </div>
	                     </div>
	                     <div class="gridly2">
	                          <h3>第三轮的播放图片</h3>
	                          <div style="width:100px;height:200px;border:solid 1px red;">
	                          
	                          </div>
	                     </div>
	              </div> 
	      </div>
	       
	       
	       <div class="adbo">
	              <div class="gridly1">
	                     <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
	              </div> 
	      </div>
	      <div class="adbo">
	              <div class="gridly1">
	                     <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
	              </div> 
	      </div>
	      <div class="adbo">
	              <div class="gridly1">
	                     <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
	              </div> 
	      </div>
	      <div class="adbo">
	              <div class="gridly1">
	                     <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
						 <div class="brick small" id="1" index="1">
						  	 <div class="show_index">1</div>
						  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/>
						 </div>
	              </div> 
	      </div>
	      
	
	
	
	
	
	
	
	
	 
<!-- 		  <div class="brick small" id="1" index="1"> -->
<!-- 		  	 <div class="show_index">1</div> -->
<!-- 		  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/> -->
<!-- 		  </div> -->
		  
<!-- 		  <div class="brick small" id="1" index="1"> -->
<!-- 		  	 <div class="show_index">1</div> -->
<!-- 		  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/> -->
<!-- 		  </div> -->
		  
<!-- 		  <div class="brick small" id="1" index="1"> -->
<!-- 		  	 <div class="show_index">1</div> -->
<!-- 		  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/> -->
<!-- 		  </div> -->
		  
<!-- 		  <div class="brick small" id="1" index="1"> -->
<!-- 		  	 <div class="show_index">1</div> -->
<!-- 		  	 <img class="img_1" src="http://img.zcool.cn/sucaiori/6A749558-7ADD-3B81-E6DC-302C7F22BEE3.jpg@700w_0e_1l.jpg"/> -->
<!-- 		  </div> -->
	</div>
<script>
   window.onload=function(){
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
		 
		 /*----------第二个的拖动-------  */
		 $('.gridly1').gridly({
			    base: 60, // px 
			    gutter: 10, // px
			    columns: 6,
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
		 /*----------第二个的拖动-------  */
		 
   }
initzGridly()
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
	 
	 /*----------第二个的拖动-------  */
	 $('.gridly1').gridly({
		    base: 60, // px 
		    gutter: 10, // px
		    columns: 6,
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


