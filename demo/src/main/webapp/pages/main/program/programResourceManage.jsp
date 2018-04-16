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
<link href="<%=path%>/css/index/animate.min.css" media="all"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=path%>/javascript/js/jquery.min.js" charset="utf-8"></script>

<script src="<%=path%>/javascript/locales/zh.js" type="text/javascript"></script>
<script src="<%=path%>/javascript/swiper.animate1.0.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/javascript/amazeui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/javascript/jquery-ui.min.js"></script>
<style type="text/css">
* {
	padding: 0px;
	margin: 0px;
}

body {
	font-family: "微软雅黑";
	background:#CCEEFF !important;
}

img {
	width: 100%;
}

.advert_head {
	width: 1300px;
	margin: 20px auto;
	padding: 1% 3%;
	border-bottom: dotted 2px #00BBFF;
}

.gridly {
	position: relative;
	width: 1300px;
	margin: 0px auto;
}

.brick.small {
	position: relative;
	height: 300px;
	width: 300px;
	float: left;
	overflow: hidden;
	cursor: move;
	border: solid 2px #0066FF;
	margin: 10px 30px;
}

img {
	cursor: move;
}

.show_index {
	width: 15px;
	height: 20px;
	position: absolute;
	background-color: #000093;
	font-size: 12px;
	color: white;
	padding: 0 0 0 5px;
	line-height: 20px;
	top: 0px;
	left: 0px;
}

.ring {
	border: solid 1px #D1BBFF;
	overflow: hidden;
	padding: 2px;
}
/*-----假如为一轮的1张图片占满整个屏幕*/
.oneRing {
	width: 100%;
	height: 138px;
}
/*---------假如为2轮或者以上------------*/
.ringOther {
	width: 48%;
	float: left;
	height: 138px;
}

.ringOtherPic {
	width: 45%;
	float: left;
	height: 65px;
	margin-right: 5%;
	border: solid 1px #eeeeee;
}

.first {
	/*padding:25px 0 0 0;*/
	margin-top: 24px;
}

.pic_area {
	display: block;
	position: relative;
}

/*------控制区域的样式修改------*/
.control {
	width: 100%;
	height: 30px;
	position: absolute;
	bottom: 0px;
	left: 0px;
	background-color: rgba(0, 0, 0, .5);
	display: flex;
	display: -webkit-flex;
}

.control span {
	color: #FFFFFF;
	height: 30px;
	line-height: 30px;
	display: block;
}

.control span:nth-child(2) {
	width: 60%;
	text-align: center;
	border-left: solid 1px #FFFFFF;
	border-right: solid 1px #FFFFFF;
}

.control span:nth-child(1) {
	width: 20%;
	text-align: center;
}

.control span:nth-child(3) {
	width: 20%;
	text-align: center;
}

.show_operate {
	position: absolute;
	top: -2px;
	right: 0px;
	display: inline-block;
	width: 30%;
	text-align: center;
	z-index: 100000;
}

.show_operate img {
	width: 20%;
}

.delete {
	margin-right: 15px;
}

.delete:hover, .set:hover, .update:hover, .update_B:hover {
	cursor: pointer;
}
/*----------半屏文件的样式修改------*/
.pic_halfarea img {
	display: block;
	width: 80%;
	height: 240px;
	margin: 10px auto;
	border: solid 1px #EEEEEE;
}

.head_add {
	float: left;
	dispaly: block;
	margin-right: 5%;
}

.head_add a {
	text-decoration: none;
	display: block;
	width: 200px;
	height: 35px;
	line-height: 35px;
	background-color: #9999FF;
	color: #FFFFFF;
	text-align: center;
}

.head_sort {
	width: 180px;
	overflow: hidden;
}

.head_sort select {
	width: 180px;
	height: 35px;
	line-height: 35px;
	border: solid 1px #9999FF;
}

.B {
	display: none;
}

h5 {
	margin: 0px !important;
}

.vedeo_name {
	position: absolute;
	top: 0px;
	left: 0px;
	font-size: 0.5rem;
}
  .all_advertiserpage{
      border:solid 1px #eee;
      background-color:#fff;
      min-height:1000px;
      width: 1300px;
	  margin: 20px auto;
	  overflow:hidden;
  }
</style>
<div class="all_advertiserpage">
		                <!--------标题栏---->
		<div class="advert_head">
			<div class="head_add">
				<a class="set" operate="1">添加广告商</a>
			</div>
			<div class="head_sort">
				<select id="head_sort" onchange="checkType()">
					<option value="A">A类</option>
					<option value="B">B类</option>
				</select>
			</div>
		</div>
                        <!-- 以下是广告商的具体展示---- -->
		<input type="hidden" name="programId" id="programId"
			value="${programId}" />
                        <!----------这是A类广告的全屏和半屏文件----->
		<div class="A">
			<div class="gridly">
				<c:forEach items="${data.result.A}" var="AItems" varStatus="num">
					<div class="brick small" id="${AItems.id}" index="${num.index+1}">
						<input type="hidden" name="advertiserId" id="advertiserId"
							value="${AItems.advertiserId}" />
						<div class="show_index">${num.index+1}</div>
						<div class="show_operate">
							<img src="<%=path%>/images/delete.png" class="delete" /> <img
								src="<%=path%>/images/set.png" class="set" onclick="addAdvert(2)"
								operate="2" />
		
						</div>
						<!--全屏文件 -->
						<div class="A_all">
							<!-- 这个地方的items是否可以换成 data1.path1,测试一下-->
							<c:forEach items="${AItems.path1}" var="path1Items"
								varStatus="pathnum">
								<input type="hidden" name="ringId" id="ringId"
									value="${path1Items.id}" />
								<c:if test="${pathnum.index <=1}">
									<div class="ring first ringOther">
										<div class="pic_area">
											<c:forEach items="${path1Items.list}" var="imageItems"
												varStatus="imagenum">
												<input type="hidden" name="picId" id="picId"
													value="${imageItems.id}" />
												<c:if test="${path1Items.list.size()==1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="oneRing" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="oneRing"
															title="${imageItems.fileOldName}">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
		
													</c:if>
		
												</c:if>
												<c:if test="${path1Items.list.size()!=1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="ringOtherPic" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="ringOtherPic"
															title="${imageItems.fileOldName}">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
		
													</c:if>
		
												</c:if>
											</c:forEach>
										</div>
									</div>
								</c:if>
								<c:if test="${pathnum.index > 1}">
									<div class="ring ringOther">
										<div class="pic_area">
											<c:forEach items="${path1Items.list}" var="imageItems"
												varStatus="imagenum">
												<input type="hidden" name="picId" id="picId"
													value="${imageItems.id}" />
												<c:if test="${path1Items.list.size()==1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="oneRing" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="oneRing"
															title="${imageItems.fileOldName}">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
													</c:if>
												</c:if>
												<c:if test="${path1Items.list.size()!=1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="ringOtherPic" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="ringOtherPic"
															title="${imageItems.fileOldName}">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
		
													</c:if>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<div class="A_half" style="display: none;">
							<c:forEach items="${AItems.path2}" var="path2Items"
								varStatus="pathnum">
								<div class="ring first">
									<h5>半屏文件</h5>
									<div class="pic_halfarea">
										<!--判断取到的资源类型得到不同的表示标签-->
										<c:if test="${path2Items.resourceType=='image'}">
											<img src="${path2Items.url}" />
										</c:if>
										<c:if test="${path2Items.resourceType=='video'}">
											<video controls style="width: 200px; height: 200px;">
												<source src="${path2Items.url}" type='video/mp4' />
											</video>
										</c:if>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="control">
							<span class="update">切换</span> <span>${AItems.advertiserName}</span>
							<span>${AItems.path1.size()}/${AItems.resourceCount}</span>
						</div>
					</div>
				</c:forEach>
			</div>
		
		</div>
		                <!--以上循环A类广告的全屏和半屏文件结束 -->
		
		<!------------------以下是去循环B类广告的全屏和半屏文件--------------->
		<div class="B">
			<div class="gridly">
				<c:forEach items="${data.result.B}" var="BItems" varStatus="num">
					<div class="brick small" id="${BItems.id}" index="${num.index+1}">
						<input type="hidden" name="advertiserId" id="advertiserId"
							value="${BItems.advertiserId}" />
						<div class="show_index">${num.index+1}</div>
						<div class="show_operate">
							<img src="<%=path%>/images/delete.png" class="delete" /> <img
								src="<%=path%>/images/set.png" class="set" onclick="addAdvert(2)"
								operate="2" />
		
						</div>
						<!--全屏文件 -->
						<div class="B_all">
							<!-- 这个地方的items是否可以换成 data1.path1,测试一下-->
							<c:forEach items="${BItems.path1}" var="path1Items"
								varStatus="pathnum">
								<input type="hidden" name="ringId" id="ringId"
									value="${path1Items.id}" />
								<c:if test="${pathnum.index <=1}">
									<div class="ring first ringOther">
										<div class="pic_area">
											<c:forEach items="${path1Items.list}" var="imageItems"
												varStatus="imagenum">
												<input type="hidden" name="picId" id="picId"
													value="${imageItems.id}" />
												<c:if test="${path1Items.list.size()==1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="oneRing" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="oneRing">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
													</c:if>
												</c:if>
												<c:if test="${path1Items.list.size()!=1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="ringOtherPic" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="ringOtherPic">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
													</c:if>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</c:if>
								<c:if test="${pathnum.index > 1}">
									<div class="ring ringOther">
										<div class="pic_area">
											<c:forEach items="${path1Items.list}" var="imageItems"
												varStatus="imagenum">
												<input type="hidden" name="picId" id="picId"
													value="${imageItems.id}" />
												<c:if test="${path1Items.list.size()==1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="oneRing" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="oneRing">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
													</c:if>
												</c:if>
												<c:if test="${path1Items.list.size()!=1}">
													<!--判断取到的资源类型得到不同的表示标签-->
													<c:if test="${imageItems.resourceType=='image'}">
														<img class="ringOtherPic" src="${imageItems.url}" />
													</c:if>
													<c:if test="${imageItems.resourceType=='video'}">
														<video controls class="ringOtherPic">
															<source src="${imageItems.url}" type='video/mp4' />
														</video>
													</c:if>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<div class="B_half" style="display: none;">
							<c:forEach items="${BItems.path2}" var="path2Items"
								varStatus="pathnum">
								<div class="ring first">
									<h5>半屏文件</h5>
									<div class="pic_halfarea">
										<!--判断取到的资源类型得到不同的表示标签-->
										<c:if test="${path2Items.resourceType=='image'}">
											<img src="${path2Items.url}" />
										</c:if>
										<c:if test="${path2Items.resourceType=='video'}">
											<video controls style="width: 200px; height: 200px;">
												<source src="${path2Items.url}" type='video/mp4' />
											</video>
										</c:if>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="control">
							<span class="update_B">切换</span> <span>${BItems.advertiserName}</span>
							<span>${BItems.path1.size()}/${BItems.resourceCount}</span>
						</div>
					</div>
				</c:forEach>
			</div>
		
		</div>

                       <!-----------确认是否删除---->
		<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">广告商提示</div>
				<div class="am-modal-bd">你，确定要删除这条记录吗？</div>
				<div class="am-modal-footer">
					<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
						class="am-modal-btn" data-am-modal-confirm>确定</span>
				</div>
			</div>
		</div>
                        <!--确认是否删除结束---------->

                       <!--以下是删除成功之后的显示的东西  -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">
					删除提示 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">您已经删除成功</div>
			</div>
		</div>
                        <!--删除成功之后的东西显示完毕-->
                        
                        
                        
                        
</div>
<script type="text/javascript">
   $(document).ready(function(){
	   checkType();
	   $('.gridly').sortable().bind('sortupdate',function(event, ui){
		   var order = $(this).sortable("toArray");
		    //alert(order)
		   $.ajax({
           	type:"post",
	    		url:"<%=path%>/program/sortProgramAdvertiser.do",//后台添加的接口方法
					async : false,
					dataType : 'json',
					data : {
						ids : order.join(",")
					},
					success : function(msg) {
						//alert("排序成功！");
					},
					error : function() {
						alert('排序失败');
					}
		 })
     });
	  
   })
          
     console.log("点击测试")
    

	
	//给删除加一个事件
	$(".delete").click(function(event) {
		//找到广告商的id 
		var id = $(this).parent().parent().attr("id");
            $('#my-confirm').modal({
	        onConfirm: function(options) {
	        	$.ajax({
	    			type : "post",
	    			url : "<%=path%>/program/deleteProgramAdvertiser.do",//后台添加的接口方法
	    			async : false,
	    			dataType : 'json',
	    			data : {
	    				id : id
	    			},
	    			success : function(msg) {
	    				if(msg.result==1){
	    					$('#your-modal').modal();
	    				}
	    				
	    				//$(this).parent().parent().remove();
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
	//展示A类的半屏文件的效果
	  var clickFlag=1  //用于更换点击查看的标志     1代表查看半屏文件，2代表查看全屏文件
	  $(".update").click(function(){
		  if(clickFlag==1){
			  $(this).parent().parent().find(".A_all").hide();
			  $(this).parent().parent().find(".A_half").show();
			  clickFlag=2;
		  }else{
			  $(this).parent().parent().find(".A_half").hide();
			  $(this).parent().parent().find(".A_all").show();
			  clickFlag=1;
		  }
	  })
	 //展示B类的半屏文件效果
	   var clickFlagB=1  //这个是B类点击查看的标志  1代表查看半屏文件，2代表查看全屏文件
	 $(".update_B").click(function(){
		 if(clickFlagB==1){
			  $(this).parent().parent().find(".B_all").hide();
			  $(this).parent().parent().find(".B_half").show();
			  clickFlagB=2;
		  }else{
			  $(this).parent().parent().find(".B_half").hide();
			  $(this).parent().parent().find(".B_all").show();
			  clickFlagB=1;
		  }
	 })
	//添加或者编辑广告商的方法
	
	function checkType(){
        	/*-----去改变页面的表现方式把A类和B类广告分开------------*/
            var optionSort=$("#head_sort").val();
        	if(optionSort=="A"){
        		$(".B").hide();
        		$(".A").show();
        	}else{
        		$(".B").show();
        		$(".A").hide();
        	}
      }
			
	$(".set").on("click",function(){
		var operate=$(this).attr("operate");  //这个确定的是添加还是修改的标志位
		var programId=$("#programId").val();  // 取到的节目单id
		var aId='';                           //广告商id,为空就是添加，不为空就是编辑
		var operatorType='';                  // 这个代表的是什么
		var option=$("#head_sort").val();     // 这个代表添加的是A类还是B类
		var id = '';                          //这个代表的是啥
		//1是添加 2是修改
		if (operate == 1) {
			operatorType += 'add';
			aId='';
		} else {
			operatorType +='edit';
			id = $(this).parent().parent().attr("id");
			aId = $(this).parent().parent().find("#advertiserId").val();
			
		}
		window.location.href='<%=path%>/program/addOrEditProgramAdvertiserInit.do?pId='+programId+'&operatorType='+operatorType+'&paId='+id+'&advertiserType='+option+'&aId='+aId;
	})
</script>
