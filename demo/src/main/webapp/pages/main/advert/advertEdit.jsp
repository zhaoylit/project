<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="<%=path %>/css/index/jquery.gridly.css" media="all" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/index/animate.min.css" media="all" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/javascript/js/jquery.min.js" charset="utf-8"></script>
<script src="<%=path %>/javascript/jquery.gridly.js" type="text/javascript"></script>
<script src="<%=path %>/javascript/locales/zh.js" type="text/javascript"></script>
<script src="<%=path %>/javascript/swiper.animate1.0.2.min.js" type="text/javascript"></script>
      <style type="text/css">
        *{padding: 0px;margin: 0px;}
        body{
            font-family: "微软雅黑";
        }
        img{width:100%;}
        .advert_head{
        	width:1300px;
        	margin: 20px auto;
        }
        .gridly {
            position: relative;
            width:1300px;
             margin: 0px auto;
        }
        .brick.small {
            position:relative;
            height:300px;
            width:300px;
            float: left;
            overflow:hidden;
            cursor:move;
            border:solid 1px #EEEEEE;
           margin: 10px 0;
 
        }
        
        img{cursor:move;}
        .show_index{
            width:15px;
            height:20px;
            position:absolute;
            background-color:#000093;
            font-size:12px;
            color:white;
            padding:0 0 0 5px;
            line-height:20px;
            top:0px;
            left:0px;
        }
        /*.ring h5{padding:5px 0 5px 10px;}*/
        .ring{
        	border-top:solid 1px #EEEEEE;
        	border-right:solid 1px #EEEEEE;
        	overflow: hidden;
        	padding: 2px;
        }
                 /*-----假如为一轮的1张图片占满整个屏幕*/
                .oneRing{
                	width:100%;
                	height:138px;
                }
                /*---------假如为2轮或者以上------------*/ 
                .ringOther{
                	width:48%;
                	float: left;
                	height:138px;
                	
                }  
                   .ringOtherPic{
                   	 width:40%;
                   	 float:left;
                   	 height:65px;
                   	 margin-right:5%;
                   	 border:solid 1px #eeeeee;
                   }
        .first{
            /*padding:25px 0 0 0;*/
           margin-top: 24px;
        }
        .pic_area{
            display: block;
        }
        
           /*------控制区域的样式修改------*/
         .control{
             width:100%;
             height:30px;
             position: absolute;
             bottom: 0px;
             left:0px;
             background-color:rgba(0,0,0,.5);
             display: flex;
             display: -webkit-flex;
         }
           .control span{color:#FFFFFF;height:30px;line-height: 30px; display:block;}
        .control span:nth-child(2){
            width:60%;
            text-align: center;
            border-left: solid 1px #FFFFFF;
            border-right:solid 1px #FFFFFF;
        }
        .control span:nth-child(1){
            width:20%;
            text-align: center;
        }
        .control span:nth-child(3){
            width:20%;
            text-align: center;
        }
          .show_operate{
          	position: absolute;
          	top: 2px;
          	right:0px;
          	display: inline-block;
          	width:30%;
          	text-align: center;
          }
             .show_operate img{
             	width:20%;
             }
             .delete{
             	margin-right: 15px;
             }
                .delete:hover, .set:hover, .update:hover{
                	cursor: pointer;
                }
        /*----------半屏文件的样式修改------*/
       .pic_halfarea img{
       	  display: block;
       	  width:80%;
       	  height:240px;
       	  margin: 10px auto;
       	  border:solid 1px #EEEEEE;
       }
          .head_add{
          	width:400px;
          	/*border:solid 1px red;*/
          	float: left;
          }
             .head_add a{
             	text-decoration: none;
             	display: block;
             	width:100px;
             	height:35px;
             	line-height: 35px;
             	background-color: #000093;
             	color:#FFFFFF;
             	text-align: center;
             }
             .head_sort select{
             	width:100px;
             	height:35px;
             	line-height: 35px;
             }
    </style>
    
      <input type="button" value="添加" id="add"/>
<input type="button" value="获取排序后的位置" onclick="getDrag()"/>
        <!----隐藏的节目单Id和广告商ID·-->
      <!--------标题栏---->
      <div class="advert_head">
      	   <div class="head_add">
      	   	   <a href="">
	      	   	   添加
	      	   </a>
      	   </div>
      	   <div class="head_sort">
      	   	   <select>
      	   	   	  <option>A类</option>
      	   	   	  <option>B类</option>
      	   	   </select>
      	   </div>
      </div>
         
            <!-- 以下是广告商的具体展示---- -->
        <input type="hidden" name="programId" id="programId" value="programId"/>
        <!----------这是A类广告的全屏和半屏文件----->
        <div class="A">
        	   <div class="gridly">
        	   	<c:forEach items="${data.result.A}" var="AItems" varStatus="num">
        	   		<div class="brick small" id="${AItems.id}" index="${num.index+1}">
        	   			<input type="hidden" name="advertiserId" id="advertiserId" value="${AItems.advertiserId}"/>
				        <div class="show_index">${num.index+1}</div>
				        <div class="show_operate">
				        	<img src="images/delete.png" class="delete"/>
				        	<a href="javascript:addAdvert()">
				        		<img src="images/set.png" class="set"/>
				        	</a>	
				        </div>
				        <!--全屏文件 -->
				        <div class="A_all" >
				        	<!-- 这个地方的items是否可以换成 data1.path1,测试一下-->
				        	<c:forEach items="${result.A.path1}" var="path1Items" varStatus="pathnum">
				        		<input type="hidden" name="ringId" id="ringId" value="${path1Items.id}"/>
				        		<c:if ${pathnum} &lte 1>
				        			<div class="ring first ringOther">
				        		</c:if> 
				        		<c:if ${pathnum} &gt 1>
				        			<div class="ring ringOther">
				        		</c:if>
				        		       <div class="pic_area">
							        		<c:forEach items="${result.A.path1.list}" var="imageItems" varStatus="imagenum">
							        			<input type="hidden" name="picId" id="picId" value="${imageItems.id}"/>
							        			   <c:if test="${result.A.path1.list.size()==1}">
									        			<img class="oneRing" src="${imageItems.url}"/>
									        		</c:if> 
									        		<c:if test="${result.A.path1.list.size()!=1}">
									        			<img class="ringOtherPic" src="${imageItems.url}"/>
									        		</c:if>
							        		</c:forEach>
					        		    </div>
				        		    </div>
				        	</c:forEach>
			      </div>
			            <div class="A_half" style="display: none;">
			                <div class="ring first">
			                    <h5>半屏文件</h5>
			                    <c:forEach items="${result.A.path2}" var="path2Items" varStatus="path2num">	
				        			<div class="pic_halfarea">
					        			<img class="" src="${path2Items.url}"/>
					        		</div>
				        		</c:forEach>
			                </div>
			            </div>
        	   		</div>
        	   		<div class="control">
	                  	<span class="update">${AItems.advertiserId}更换</span>
	                  	<span>${AItems.advertiserName}</span>
	                  	<span>${num.index}/${}</span>
			        </div>
        	   	</c:forEach>
			    
			    </div>
			</div>
        </div>
        
        <!-------------这是B类广告的全屏和半屏文件-->
        <div class="B">
        	<c:forEach items="${result.B}" var="BItems" varStatus="num">
        	   		<div class="brick small" id="${AItems.advertiserId}" index="${num.index}">
        	   			<input type="hidden" name="advertiserId" id="advertiserId" value="${AItems.advertiserId}"/>
				        <div class="show_index">${num.index}</div>
				        <div class="show_operate">
				        	<img src="img/delete.png" class="delete"/>
				        	<a href="javascript:addAdvert()">
				        		<img src="img/set.png" class="set"/>
				        	</a>	
				        </div>
				        <!--全屏文件 -->
				        <div class="B_all" >
				        	<!-- 这个地方的items是否可以换成 data1.path1,测试一下-->
				        	<c:forEach items="${result.B.path1}" var="path1Items" varStatus="pathnum">
				        		<c:forEach items="${result.B.path1.list}" var="imageItems" varStatus="imagenum">
				        			<div class="pic_area">
					        			<img class="oneRing" src="${imageItems.url}"/>
					        		</div>
				        		</c:forEach>
				        	</c:forEach>
			            </div>
			            <div class="B_half" style="display: none;">
			                <div class="ring first">
			                    <h5>半屏文件</h5>
			                    <c:forEach items="${result.B.path2}" var="path2Items" varStatus="path2num">	
				        			<div class="pic_halfarea">
					        			<img class="" src="${path2Items.url}"/>
					        		</div>
				        		</c:forEach>
			                </div>
			            </div>
        	   		</div>
        	   		<div class="control">
	                  	<span class="update">${num.index}更换</span>
	                  	<span>${AItems.advertiserName}</span>
	                  	<span>${num.index}/${}</span>
			        </div>
        	   	</c:forEach>
        </div>
			
<script type="text/javascript">
    initzGridly();
    function initzGridly(){
        $('.gridly').gridly({
            base: 60, // px
            gutter:5, // px
            columns:20,
            callbacks:{
                reordering: function($elements){
                    //拖动开始回调函数
                    //alert("拖动开始了")
                },
                reordered: function(data){
                	//alert(data);
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
                    //获取到排序后的顺序
                        var order=getDrag();
                        //alert(order);
                        
                    //去调一个后台保存顺序的接口               
                }
            }
        });
    }
    //跳转页面进行
    function addAdvert(){
    	alert("进行页面的跳转");
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
        
        $(".delete").click(function(){
	    	alert("走删除接口");
	    	$(this).parent().parent().remove();
	    	initzGridly();
	    })
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
            //alert(sort.substr(0,sort.length-1));
          return sort.substr(0,sort.length-1);
    }
    //给删除加一个事件
    $(".delete").click(function(){
    	alert("走删除接口");
    	$(this).parent().parent().remove();
    	for(var i = 0;i < $('.gridly').find(".brick").length;i++){
            //brick
            var current = $('.gridly').find(".brick").eq(i);
            //更改元素的左上角的显示索引
            $(current).find(".show_index").html(i + 1);
            //更改元素的index属性
            $(current).find(".show_index").parent().attr("index",i + 1);
        }
    	
    	initzGridly();
    })
    //给设置加一个事件
    $(".set").click(function(){
    	alert("跳转到广告节目添加页面");
    })
    //展示半屏文件的效果
	$(".update").toggle(function(){
		
	    $(this).parent().parent().find(".A_all").css("display","none").removeClass("animated bounceInLeft").addClass("animated bounceOutLeftLeft");
	    $(this).parent().parent().find(".A_half").css("display","block").removeClass("animated bounceOutLeft").addClass("animated bounceInLeft");
	},
	    function(){
	    $(this).parent().parent().find(".A_half").css("display","none").removeClass("animated bounceInLeft").addClass("animated bounceOutLeft");
	    $(this).parent().parent().find(".A_all").css("display","block").removeClass("animated bounceOutLeft").addClass("animated bounceInLeft");
	}
    );
</script>
    