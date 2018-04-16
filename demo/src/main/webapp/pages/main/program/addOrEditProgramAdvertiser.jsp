<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=path%>/css/index/amazeui.css">
<link rel="stylesheet" href="<%=path%>/css/index/jedate.css">
<link href="<%=path%>/css/index/animate.min.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=path%>/javascript/js/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/javascript/amazeui.min.js"></script>
<script src="<%=path%>/javascript/swiper.animate1.0.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/javascript/jquery.jedate.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/javascript/jquery-ui.min.js"></script>
<!-- <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> -->

<style type="text/css">
* {
	padding: 0px;
	margin: 0px;
}

body {
	font-family: "微软雅黑";
	background:#CCEEFF !important;
	height: 100%;
	min-width: 800px;
}

p {
	margin: 0;
}

em {
	font-style: normal;
}

input {
	padding: 0;
	margin: 0;
}

ul {
	padding: 0;
	list-style: none;
	margin: 0;
}

span {
	font-size: 0.9rem;
	display: block;
}

a {
	color: #B6B6B6
}

img {
	width: 100%;
}

/*--------格子的一些样式--------*/
.gridly {
	position: relative;
	margin: 0px auto;
}

.gridly1 {
	position: relative;
	margin: 0px auto;
}

.brick.small {
	position: relative;
	height: 200px;
	width: 200px;
	float: left;
	overflow: hidden;
	cursor: move;
	border: solid 1px #EEEEEE;
	/*background-color:#f2f2f2;*/
	margin: 10px 0;
}
/*-------模态弹窗的一些样式--------*/
.ad_pop {
	width: 800px !important;
	height: 800px !important;
}
/*-------图片每轮的控制------*/
.ringPic {
	width: 100%;
	border: solid 1px #EEEEEE;
	height:300px;
	padding-bootom:10px;
}

.halfsmallRingPic {
	display: block;
	width: 100px;
	height: 100px;
	border: solid 1px #AAAAAA;
	position: relative;
}


/*------删除图标的样式---*/
.deleteFile, .emtyHalf {
	position: absolute;
	width: 30px;
	top: 0px;
	right: 0px;
}

.dragImg {
	width: 100px;
	height: 100px;
	margin-right:10px;
	border:solid 1px #eee;
}
.oneRing {
	width: 100%;
	height: 138px;
}
.first{
   width:48%;
   float:left;
   margin-top:0px !important;
}
.ringOther {
	margin-top:20px !important;
}
  #advertRingPic{text-align:center;border-top:solid 1px #ddd}
  .resourVideo{
     dispaly:inline-block !important;
     float:left;
     padding-right:10px;
  }
  .deletepath1:hover, .emtyHalf:hover{
     cursor:pointer;
  }
  .advert_allPic{
      height:200px;
      overflow-y:scroll;
  }
  .add_adertisers{
      border:solid 1px #eee;
      background-color:#fff;
      min-height:1000px;
      padding:30px 0;
  }
</style>
<script type="text/javascript"> 
     document.oncontextmenu=function(e){return false;} 
</script> 
<div class="am-g doc-am-g am-container add_adertisers">
	<div class="am-u-lg-12">
		<form class="am-form">
			<fieldset>
				<legend>广告添加</legend>
				<div class="am-form-group" style="width:30%;float:left;margin-right:15%;">
					<label for="doc-select-1">广告商名称</label> <input type="hidden"
						name="advertiserId" id="advertiserId" value="${data.advertiserId}" />
					<select id="advertiserName" onchange="checkResource()">
					</select> <span class="am-form-caret"></span>
				</div>

				<div class="am-form-group" style="width:30%;float:left;">
					<label for="doc-select-3">频次</label> <select id="frequence">
						<option value="">-----请选择-----</option>
						<c:if test="${empty data.frequency}">
							<option value="1">1</option>
							<option value="2">2</option>
						</c:if>
						<c:if test="${data.frequency == '1'}">
							<option value="1" selected="selected">1</option>
							<option value="2">2</option>
						</c:if>
						<c:if test="${data.frequency == '2'}">
							<option value="1">1</option>
							<option value="2" selected="selected">2</option>
						</c:if>
					</select> <span class="am-form-caret"></span>
				</div>

				<div class="am-form-group" style="clear:both;">
					<a class="am-btn am-btn-primary half_chose">请选择上传的半屏文件</a>(<span style="display:inline-block;">请拖拽下面图片到这里哦</span>)
					<div class="ringPic ">
						<div class="half_drag_area">
							<div class="brick halfsmallRingPic" id="half_id" index="1"
								onclick="test()" ondrop="drop(event)"
								ondragover="allowDrop(event)">	
							</div>
						</div>
						<!-------查询出来的这个广告商的所有图片-->
						<div class="advert_allPic">
						     <h4 style="text-align:center;">广告商资源展示</h4>
							<div class="advert_allPicShow half_picshow"></div>
						</div>
					</div>
				</div>
				<p>
					<a class="am-btn am-btn-success" id="advertSub">提交</a>
				</p>
			</fieldset>
		</form>
	
	     <!--------------- 显示每轮信息的页面----------->
		<div id="advertRingPic">
			<h1>添加完广告商之后才允许添加上刊的全屏文件哦</h1>
		</div>
	</div>
</div>


<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">上传提示
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
             操作成功
    </div>
  </div>
</div>

<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">删除提示</div>
    <div class="am-modal-bd">
                      确定删除这条资源吗？
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
<script type="text/javascript"> 
	//这段代码是干嘛用的
	$('.gridly').sortable().bind('sortupdate', function(event, ui) {
		var order = $(this).sortable("toArray");

	});
	
	     
	//当出现2个下拉框时间选择框以后时间选择
	function changeTime(event){
		if(event.target.value != 5 && event.target.value != 10 && event.target.value != 7.5) {
			alert("请重新输入");
			return;
		}
		var otherValue = 15 - event.target.value;
		if(event.target.className == 'time_show1_1') {
			$(".time_show2_2").val(otherValue);
		} else if(event.target.className == 'time_show2_2') {
			$(".time_show1_1").val(otherValue);
		}
	}
	function allowDrop(ev) {
		//alert("这是啥")
		ev.preventDefault();
	}
	function drag(ev) {
		
		//alert("开始拖动了")
		ev.dataTransfer.setData("Text", ev.target.id);
	}
	function drop(ev) {
		ev.stopPropagation();
		ev.preventDefault();
		var data = ev.dataTransfer.getData("Text");
		var src = ev.target.id;
		//先判断是不是根节点，根节点的ev.target.localName是不是div，直到找到div为止
		if (ev.target.localName != 'div') {
			alert("不允许重复添加");
			return;
		}
		if ($("#" + src + "").find(".dragImg").size() >= 1) {
			alert("不允许重复添加");
			return;
		}
		ev.target.appendChild(document.getElementById(data));
		var p = document.createElement("img");
		p.src = "<%=path%>/images/delete.png";
		p.setAttribute("class", "deleteFile");
		ev.currentTarget.appendChild(p);
		$(".deleteFile").on("click", function() {
			 $(this).parent().empty();
			getResource();
		})
     }
	
	 var temp_option='';
	 var page_advertiseId="${data.advertiserId}";//节目单id
 	 var advertiserType = "${advertiserType}";//广告商分类
 	 var frequency="${data. frequency}";//频次
 	 var pId="${pId}"; //节目单id
 	 var operatorType="${operatorType}"
 	 var paId="";
 	 var resourceId_half_url='${data.path2[0].url}'; //半屏文件的id
 	 var resourceId_half_Type='${data.path2[0].resourceType}'; // 半屏文件的类型
 	 var resourceId_half_Id='${data.path2[0].id}';  //半屏文件的资源id
 	 var advertiserFlag=1;   //上传广告商的名称防止重复点击提交的flag，为1允许提交，为0不允许提交。
 	// alert(operatorType);
 	 if(operatorType=="edit"){
 		paId="${paId}";
 		var half_pic='';
 		 // 在这里把半屏文件给显示出来
 		if(resourceId_half_Type=="video"){
 			half_pic+='<video controls style="height:90px;width:95px;" class="dragImg" resourceId="'+resourceId_half_Id+'"><source src="'+resourceId_half_url+'"></video>'
        }else{
        	half_pic+='<img src="'+resourceId_half_url+'" class="dragImg" resourceId="'+resourceId_half_Id + '"id="half'+resourceId_half_Id+'" draggable="true" ondragstart="drag(event)"/>';
        }
 		half_pic+='<img src="<%=path%>/images/delete.png" class="emtyHalf"/>';
 		half_pic+='</div>';
     	$("#half_id").append(half_pic);
     	// 点击半屏文件的删除，清空已存在的半屏文件
     	$(".emtyHalf").click(function(){
     		$('#my-confirm').modal({
		        relatedTarget: this,
		        onConfirm: function(options) {
		        	$("#half_id").empty();
		     		getResource();
		        },
		        // closeOnConfirm: false,
		        onCancel: function() {
		          
		        }
		      });
     	})
 		allhtml(paId);   //在这里去局部刷新全屏文件的上传方法了
 	 }else{
 		paId='';
 	 }
    $(document).ready(function(){
    	 //在页面加载的时候去拿到这个广告商的所有资源  去请求接口拿值
      	    $.ajax({
              	 	type:"post",
		    		url:"<%=path%>/advertiser/advertiserList.do",//后台添加的接口方法
		    		async:true,
		    		dataType:'json',
		    		data:{
		    			  pageSize:10000,
		    			 advertiserType:advertiserType
		    		},
		    		success: function(msg) {
		    			console.log(msg);
		    			temp_option+='<option value="">----请选择广告商----</option>';
		    			$.each(msg.rows,function(key,item){
		    				if(operatorType=="edit"){
		    					if(item.id==page_advertiseId){
			    					temp_option+='<option  value="'+item.id+'"'+'selected="selected">'+item.advertiserName+'</option>';
			    				}else{
			    					temp_option+='<option disabled value="'+item.id+'">'+item.advertiserName+'</option>';
			    				}	 
		    				}else{
		    					if(item.id==page_advertiseId){
			    					temp_option+='<option  value="'+item.id+'"'+'selected="selected">'+item.advertiserName+'</option>';
			    				}else{
			    					temp_option+='<option  value="'+item.id+'">'+item.advertiserName+'</option>';
			    				}
		    				}	
		    			})
			            $("#advertiserName").empty().append(temp_option);
		    			getResource();
			        },  
			        error: function() {  
			              alert("页面数据请求失败")  
			        }  
              	 })
      	 
    });
    //点击页面的提交按钮，去走一个提交广告商资源的接口
      $("#advertSub").click(function(){
    	   //1.取到页面的广告商名称
    	   var page_advertiseId=$("#advertiserName option:selected").val();
    	   //2.取到页面的频次
    	   var frequency=$("#frequence option:selected").val();
    	  
    	   //3.取到上传的半屏文件
    	   var resourceId_half=$("#half_id").find(".dragImg").attr("resourceId");
    	   if(resourceId_half=="undefined"){
    		   var resourceId_half='';
    	   }
    	   if(page_advertiseId==''){
    		    alert("请您先选择广告商");
    		    return false;
    	   }
    	   if(frequency==''){
	   		    alert("请您选择频次");
	   		    return false;
	   	   }
    	   // alert(advertiserFlag);
    	   if(advertiserFlag==1){
    		   $.ajax({
            	 	type:"post",
	   	    		url:"<%=path%>/program/addOrEditProgramAdvertiser.do",//后台添加的接口方法
	   	    		async:false,
	   	    		dataType:'json',
	   	    		data:{
	   	    			pId:pId,
	   	    			aId:page_advertiseId,
	   	    			paId:paId,
	   	    			frequency:frequency,
	   	    			advertiserType:advertiserType,
	   	    			path2:resourceId_half
	   	    		},
	   	    		success: function(msg) {
	   	    			if(msg. result==1){
	   	    				$('#your-modal').modal();
	   	    				console.log("页面在上传完广告商的时候后台给到的数据："+msg.data);
	   	    				var backPaid=msg.data.paId;//后台回显过来的节目单广告商id
	   	    				console.log(backPaid);
	   	    				//allhtml(backPaid);
	   	    				setTimeout(function time(){
	   	    					self.location = document.referrer;
	   	    		        },2000);
	   	    			 
	   	    			}else{
	   	    				alert("失败")
	   	    			} 
	   	    			advertiserFlag=0;
	   	    			//alert(advertiserFlag);
	   		        },  
	   		        error: function() {  
	   		        	$('.am-alert').alert("页面数据请求失败")
	   		               
	   		        }  
            	 })
    	   }else{
    		    alert("已经添加广告商，请勿重复点击提交")
    	   }
    	  
      })
      //以上是回调页面点击提交的上传接口完毕
      
      //去取某个广告商的所有资源
       function getResource(){
	       var page_advertiseId=$("#advertiserName").val();
	       console.log(page_advertiseId);
	       if(page_advertiseId==''){
	    	   $(".half_picshow").empty().html('<h3 style="color:#87CEFA;text-align:center;margin-top:20px;">选中广告商之后才显示资源哦</h3>');
		       $(".all_picshow").empty().html('<h3 style="color:#87CEFA;text-align:center;margin-top:20px;">选中广告商之后才显示资源哦</h3>');
		       return;
	       }
	        var tempResource='';
        	var tempResource_half='';
        	var tempResource_all='';
        	 $.ajax({
	    		type:"post",
	    		url:"<%=path%>/resource/getResource.do",//后台添加的接口方法
				async : true,
				dataType : 'json',
				data : {
					resourceServiceType : "1",
					resourceServiceId : page_advertiseId
				},
				success : function(result){
					console.log("后台取到的资源数量"+result.data);
					if(result.data.length==0){
						console.log("资源假如没有");
						tempResource_half+='<h4 style="color:red;margin-top:20px;text-align:center;">该广告商下面没有资源</h4>';
						tempResource_all+='<h4 style="color:red;margin-top:20px;text-align:center;">该广告商下面没有资源</h4>';
					}else{
						var data1=$.parseJSON(result.data);
						$.each(data1,function(key1,value1){
							console.log(value1.suffix);  
							var suffix=value1.suffix; //判断取出来的资源类型
							if(suffix==".mp4"){
								tempResource_half+='<video controls class="dragImg resourVideo" draggable="true" ondragstart="drag(event)" resourceId="'+value1.id+'" id="half'+value1.id+'" title="'+value1.fileOldName+'"><source src="'+value1.filePath+'" type="video/mp4"/></video>';
								tempResource_all+='<video controls class="dragImg resourVideo" draggable="true" ondragstart="drag(event)" resourceId="'+value1.id+'" id="all'+value1.id+'" title="'+value1.fileOldName+'"><source src="'+value1.filePath+'" type="video/mp4"/></video>';
							}else{
								tempResource_half+='<img class="dragImg" draggable="true" ondragstart="drag(event)" resourceId="'+value1.id+'" src="'+value1.filePath+'" id="half'+value1.id+'" title="'+value1.fileOldName+'"/>';
								tempResource_all+='<img class="dragImg" draggable="true" ondragstart="drag(event)" resourceId="'+value1.id+'" src="'+value1.filePath+'" id="all'+value1.id+'" title="'+value1.fileOldName+'"/>';
							}
							
						})
					}
					var len = $(".advert_allPicShow").length;
					  $(".advert_allPicShow").empty();
						if(len==1){
							$(".half_picshow").append(tempResource_half);
						}else if(len==2){
							$(".half_picshow").append(tempResource_half);
							$(".all_picshow").append(tempResource_all);
						}
					
				},
			error : function() {
				alert("页面数据请求失败")
			}
		});
	}
        //当提交成功之后去显示每轮的信息
             function allhtml(paId){  
			    document.getElementById("advertRingPic").innerHTML ="";  
			       $.ajax({  
			           type: 'post', //可选get 
			           async :false,
			           url: "<%=path%>/program/getProgramAdvertiserRingView.do", //后台接收数据的地方 
			           data: {
			           	 id:paId
			           }, //传给后台的数据，多个参数用&连接
			           dataType: 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等  
			           success: function(msg) {  
			               //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！  
			               document.getElementById("advertRingPic").innerHTML = msg;  
			               //console.log(msg);
			               //轮排序的接口
			               $('.gridly').sortable().bind('sortupdate', function(event, ui) {
			            	   var orderRing = $(this).sortable("toArray");
	                             //去排序广告商的每一轮的方法
				   				 $.ajax({
				   		           	type:"post",
			   			    		url:"<%=path%>/program/sortProgramAdvertiserRing.do",//后台添加的接口方法
		   							async :true,
		   							dataType : 'json',
		   							data : {
		   								ids : orderRing.join(",")
		   							},
		   							success : function(msg) {
		   								if(msg.result==1){
		   									alert("排序成功！");
		   									
		   								}
		   							},
		   							error : function() {
		   								alert('排序失败');
		   							}
					   		   })   
				   			});
			                 //在每一轮中去拖拽每个图片
			                 $('.small_pic_drag').sortable().bind('sortupdate', function(event, ui){
			            	   var smallorderRing = $(this).sortable("toArray");
				            	 //去排序广告商每轮里面的每张图片的位置
					   				 $.ajax({
					   		           	type:"post",
				   			    		url:"<%=path%>/program/sortProgramAdvertiserRingResorce.do",//后台添加的接口方法
			   							async :true,
			   							dataType : 'json',
			   							data : {
			   								ids : smallorderRing.join(",")
			   							},
			   							success : function(msg) {
			   								if(msg.result>0){
			   									//alert("排序成功！");
			   								}
			   							},
			   							error : function() {
			   								alert('排序失败');
			   							}
						   		   })   
				   			 });
			               //轮删除的接口
			               $(".delete").click(function(event) {
			            	   var id = $(this).parent().parent().attr("id");
			            	   $('#my-confirm').modal({
			       		        onConfirm: function(options) {
			       		       //找到广告商的id 
									$.ajax({
										type : "post",
										url : "<%=path%>/program/deleteProgramAdvertiserRing.do",//后台添加的接口方法
										async :false,
										dataType : 'json',
										data : {
											id : id
										},
										success : function(msg) {
											if(msg.result==1){
												alert("删除成功！");
											}
											window.location.reload();
										},
										error : function() {
											alert('对不起失败了');
										}
									});
			       		        },
			       		        // closeOnConfirm: false,
			       		        onCancel: function() {
			       		          
			       		        }
			       		      });
								
							})
			               // 初始化成功之后的事--------弹框的时间插件
							$("#dateend").jeDate({
								 minDate: $.nowDate(0),
								format: "YYYY-MM-DD",
								isinitVal: false,
								isClear: true,
								isTime: true, //isClear:false,
								clearRestore: true,
							});
							$("#dateBegin").jeDate({
								format: "YYYY-MM-DD",
								isinitVal: false,
								isClear: true,
								isTime: true, //isClear:false,
								clearRestore: true,
							});
							//当弹框的选择的张数改变的时候触发的操作
							$("#picCount").on('change',function(){
								getResource();
								var r = document.getElementById("picCount").value;
								if(r == 1) {
									$('.gridly1').empty();
									$('.time_show').empty();
									$(".time_show").css("display","block");
									for(var i = 0; i < r; i++) {
										var temp = i + 1;
										var id = temp + "_" + temp;
										var $addPIcArea = $('<div class="brick allsmallRingPic" id="' + id + '" ondrop="drop(event)" ondragover="allowDrop(event)" onclick="test()"></div>');
										$('.gridly1').append($addPIcArea);
										var $time ='<input type="text" value="" style="width:100px;float:left; margin-right:10px; margin-top:5px;"/>';
										$(".time_show").append($time);
									}
									
								} else if(r == 2) {
									$('.gridly1').empty();
									$('.time_show').empty();
				                    $(".time_show").css("display","block");
									for(var i = 0; i < r; i++) {
										var temp = i + 1;
										var id = temp + "_" + temp;
										var $addPIcArea = $('<div class="brick allsmallRingPic" id="' + id + '" ondrop="drop(event)" ondragover="allowDrop(event)" onclick="test()"></div>');
										$('.gridly1').append($addPIcArea);
										var $time ='<input type="text" value="" style="width:100px;float:left; margin-right:10px;margin-top:5px;"/>';
										
										/* if(i==0){
											$time = $('<select class="time_show' + id + '" onchange="changeTime(event)"><option value="5" selected>5秒</option><option value="7.5">7.5秒</option><option value="10">10秒</option></select>');
										}else{
											$time = $('<select class="time_show' + id + '" onchange="changeTime(event)"><option value="5">5秒</option><option value="7.5">7.5秒</option><option value="10" selected>10秒</option></select>');
										} */
										$(".time_show").append($time);
									}
								} else if(r == 3) {
									$('.gridly1').empty();
									$('.time_show').empty();
									$(".time_show").css("display","block");
									for(var i = 0; i < r; i++) {
										var temp = i + 1;
										var id = temp + "_" + temp;
										var $addPIcArea = $('<div class="brick allsmallRingPic"  id="' + id + '" onclick="test()" ondrop="drop(event)" ondragover="allowDrop(event)"></div>');
										$('.gridly1').append($addPIcArea);
										//添加 播放时长
										var $time ='<input type="text" value="" style="width:100px;float:left; margin-right:10px;margin-top:5px;"/>';
										$(".time_show").append($time);
									}
									
								}	
							})
							
							//这个方法是为了实现当页面选择张数的时候不同的表现方式
							$('.gridly1').sortable().bind('sortupdate',function(event, ui){
							       var order = $(this).sortable("toArray");
				           });
							 var parId='';      //这是轮的id
							//判断点击的是添加轮还是编辑轮
							    $(".set").click(function(){
							    	var operatePath1=$(this).attr("operatePath1");
							    	if(operatePath1==2){
							    		//这个为编辑操作
							    		parId=$(this).parent().parent().attr("id"); //这个代表的是节目单广告商轮的id
							    		$(".popParId").val(parId);
							    		$.ajax({
											type : "post",
											url : "<%=path%>/program/getProgramAdvertiserRing.do",//后台添加的接口方法
											async :false,
											dataType : 'json',
											data : {
												id :parId
											},
											success : function(msg) {
												if(msg.result==1){
													//alert("你正在编辑吧");
													//1.首先返回给我了轮的id
													var backParId=msg.data.id; //后台给我返回来的轮的id
													var beginTime=msg.data.beginTime;
													var endTime=msg.data.endTime;
											        var resourcecount=msg.data.list.length;
											        var backRingPic='';
											        var time1 = "";
											        //以下进行数据回显
											        $("#picCount").val(resourcecount);
											        $("#dateBegin").val(beginTime);
											        $("#dateend").val(endTime);
											        //以下去回显图片
											        $.each(msg.data.list,function(key2,value2){
											        	backRingPic+='<div class="brick allsmallRingPic" id="'+(key2+1)+'_'+(key2+1)+'" ondrop="drop(event)" ondragover="allowDrop(event)">';
											           // 这里判断传过来的是图片还是视频
											           console.log(value2.resourceType);
											           if(value2.resourceType=="video"){
											        	   backRingPic+='<video controls style="height:90px;width:100px;" class="dragImg" resourceId="'+value2.resourceId+'" parId="'+value2.id+'"><source src="'+value2.url+'"></video>'
											           }else{
											        	   backRingPic+='<img src="'+value2.url+'" class="dragImg" resourceId="'+value2.resourceId + '" parId="'+value2.id+'" id="all'+value2.id+'" draggable="true" ondragstart="drag(event)"/>';
											           }
											        	backRingPic+='<img src="<%=path%>/images/delete.png" class="deletepath1"/>';
											        	backRingPic+='</div>';
											        	
											        	time1 +='<input type="text" value="'+value2.duration+'" style="width:100px;float:left; margin-right:10px;margin-top:5px;"/>';
											        	
											        })
											        
											        $(".gridly1").empty().append(backRingPic);
											        //显示播放时长
											        $('.time_show').empty().css("display","block");
											        $(".time_show").append(time1);
											        
											       /*  if(resourcecount > 0){
											        	$('.time_show').empty().css("display","block");
														var $time='';
														var $time1='';
												      	
														var duration = msg.data.list[0].duration;
												      	if(duration=='5'){
															$time = $('<select class="time_show1_1" onchange="changeTime(event)"><option value="5" selected>5秒</option><option value="7.5" >7.5秒</option><option value="10">10秒</option></select>');
															$time1 = $('<select class="time_show2_2" onchange="changeTime(event)"><option value="5">5秒</option><option value="7.5" >7.5秒</option><option value="10" selected>10秒</option></select>');
														}else if(duration=='7.5'){
															$time = $('<select class="time_show1_1" onchange="changeTime(event)"><option value="5">5秒</option><option value="7.5" selected>7.5秒</option><option value="10">10秒</option></select>');
															$time1 = $('<select class="time_show2_2" onchange="changeTime(event)"><option value="5">5秒</option><option value="7.5" selected>7.5秒</option><option value="10" >10秒</option></select>');
														}else{
															$time = $('<select class="time_show1_1" onchange="changeTime(event)"><option value="5" selected>5秒</option><option value="7.5" >7.5秒</option><option value="10" selected>10秒</option></select>');
															$time1 = $('<select class="time_show2_2" onchange="changeTime(event)"><option value="5" selected>5秒</option><option value="7.5" >7.5秒</option><option value="10" >10秒</option></select>');
														} 
													    
													    
													    $(".time_show").append($time);
														$(".time_show").append($time1);
											        	
											        }else{
											        	$('.time_show').empty().css("display","none");
											        } */
											         //以下是走的图片资源删除接口
											           $(".deletepath1").click(function(){
											        	 //去取到这个图片资源的id
									        	        	var parId=$(this).parent().find(".dragImg").attr("parId");
											        	   $('#my-confirm').modal({
										        	        onConfirm: function(options) {
										        	        	
													        	   //alert(resourceId);
													        	   $.ajax({
													        		   type : "post",
																		url : "<%=path%>/program/deleteProgramAdvertiserRingResource.do",//后台添加的接口方法
																		async :false,
																		dataType : 'json',
																		data : {
																			id :parId
																		},
																		success : function(msg) {
																			if(msg.result==1){
																				alert("删除成功");
																				window.location.reload();
																			}
																			
																		},
																		error : function() {
																			alert('对不起失败了');
																		}
													        	   })   
										        	        },
										        	        // closeOnConfirm: false,
											        	        onCancel: function() {
											        	          
											        	        }
										        	      }); 
											           })
											         
												}
											},
											error : function() {
												alert('对不起失败了');
											}
										}); 
							    	}else{
							    		// 这个逻辑是添加操作
							    		//alert("这是添加操作吧")
							    		// 添加操作完毕
							    	}
							    });
							   // 关闭弹窗事件开始
						    	   $('#doc-modal-1').on('closed.modal.amui', function(){
									      $(".popParId").val('');
									      $("#dateBegin").val('');
									      $("#dateend").val('')
									      $("#picCount").val('');
									      $(".gridly1").empty();
									      $(".time_show").empty();
									});
						       // 关闭弹窗事件结束
							  //以下走的是添加轮的方法
							      var Allring_flag=1  //1是允许点击提交轮 2是不允许
					              $(".ring_add").click(function(){
					            	  var parId= $(".popParId").val();
										var jsonArray  = new Array();
					              	   var beginTime=$("#dateBegin").val();
					              	   var endTime=$("#dateend").val();
					              	   var rcount=$("#picCount").val();
					              	   var addRingTemp='';
					              	   var resourceId='';
					              	   var duration;
					              	   var josnObject = {};
					              	   var jsonStr='';
					              	   //alert(resourceId)
					              	   if(rcount==''){
					              		   alert("请选择这一轮的上传数量");
					              		   return;
					              	   }
					              	   //取资源拼接参数
					              	   if(rcount==1){
					              	   	   resourceId=$(".gridly1 .dragImg").attr("resourceId");
					              	   	   duration = $(".time_show").find("input:eq(0)").val();
					              	   	   
					              	   	   if(typeof(resourceId)=="undefined"){
						              		   alert("请选择这一轮上传的资源");
						              		   return;
						              	   }
					              	   	   if(duration == ""){
					              	   		   alert("请设置播放时长");
						              		   return;
					              	   	   }
						              	   	josnObject.resourceId = resourceId;
							              	 josnObject.duration = duration;
							              	jsonArray.push(josnObject);
							              	jsonStr=JSON.stringify(jsonArray)
						              
					              	   	    
					              	   }else if(rcount==2){
					              		 for(var i=0;i<rcount;i++){
					              			var josnObject = {};
					              			resourceId=$(".gridly1").find(".allsmallRingPic").eq(i).find(".dragImg").attr("resourceId");
					              			duration = $(".time_show").find("input:eq("+i+")").val();
					              			console.log(resourceId)
					              			if(typeof(resourceId)=="undefined"){
							              		   alert("请选择这一轮上传的资源");
							              		   return;
							              	   }
						              	   	   if(duration == ""){
						              	   		   alert("请设置播放时长");
							              		   return;
						              	   	   }
					              			josnObject.resourceId = resourceId;
							              	josnObject.duration = duration;
							              	jsonArray.push(josnObject);
					              	   	  }
					              		  jsonStr=JSON.stringify(jsonArray)
					              	   	  //alert(jsonStr);
					              	   }else if(rcount==3){
					              	   	    for(var i=0;i<rcount;i++){
						              	   	   var josnObject = {};
						              	   	   resourceId=$(".gridly1").find(".allsmallRingPic").eq(i).find(".dragImg").attr("resourceId");
						              	   	   duration = $(".time_show").find("input:eq("+i+")").val();
						              		   josnObject.resourceId = resourceId;
						              		   if(typeof(resourceId)=="undefined"){
							              		   alert("请选择这一轮上传的资源");
							              		   return;
							              	   }
						              	   	   if(duration == ""){
						              	   		   alert("请设置播放时长");
							              		   return;
						              	   	   }
								               josnObject.duration = duration;
								               jsonArray.push(josnObject);
						              	   	    	
						              	   }
					              	   	jsonStr=JSON.stringify(jsonArray)
					              	   	   //alert(jsonStr);
					              	}
					              	 if(Allring_flag==1){
					              		//以下是调轮上传的接口方法
	              	                     $.ajax({
											type : "post",
											url : "<%=path%>/program/addOrEditProgramAdvertisrRing.do",//后台添加的接口方法
											async : false,
											dataType : 'json',
											data : {
												paId : paId,
												parId : parId,
												beginTime : beginTime,
												endTime : endTime,
												resource : jsonStr
											},
											success : function(msg) {
												if (msg.result == 1) {
													alert("上传成功！");
													window.location.reload();
												}
											},
											error : function() {
												alert('对不起失败了');
											}
										 });
										//调轮的上传接口完成
	              	                   Allring_flag=2;
					            	  }else{
					            		  alert("正在上传资源，请勿重复点击提交！")
					            	  }
					              	   
										
										
									})

						//以上为添加轮的方法结束

					},
					error : function() {
						// alert('对不起失败了');  
					}
				})
	}
	//下拉框改变的时候就去请求资源
	//去删除广告商的每一轮的方法

	function checkResource() {
		$(".allsmallRingPic").empty();
		$(".halfsmallRingPic").empty();
		getResource();
	}
	$(".half_chose").click(function() {
		getResource();
	})
</script>