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
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<style type="text/css">
			.gridly {
				position: relative;
				margin: 0px auto;
			}
			/*----每轮的样式添加-------*/
			.brick.small {
				position: relative;
				height: 250px;
				width: 250px;
				float: left;
				overflow: hidden;
				cursor: move;
				border: solid 1px #87CEFF;
				margin: 10px;
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
				border-top: solid 1px #EEEEEE;
				border-right: solid 1px #EEEEEE;
				overflow: hidden;
				padding: 2px;
			}
			/*-----假如为一轮的1张图片占满整个屏幕*/
			
			.oneRing {
				width: 100%;
				height: 125px;
			}
			/*---------假如为2轮或者以上------------*/
			
			.ringOther {
				width: 48%;
				float: left;
				height: 125px;
			}
			
			.ringOtherPic {
				width: 40%;
				float: left;
				height: 62px;
				margin-right: 5%;
				border: solid 1px #eeeeee;
			}
			
			.first {
				/*padding:25px 0 0 0;*/
				margin-top: 24px;
			}
			
			.pic_area {
				display: block;
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
				width:50%;
				
				border-left: solid 1px #FFFFFF;
				border-right: solid 1px #FFFFFF;
			}
			
			.control span:nth-child(1) {
				width:50%;
				
			}
			.show_operate {
				position: absolute;
				right: 0px;
				display: inline-block;
				width: 30%;
				text-align: center;
				top:-2px;
			}
			
			.show_operate img {
				width: 20%;
			}
			
			.delete {
				margin-right: 15px;
			}
			
			.delete:hover,
			.set:hover,
			.update:hover {
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
				width: 400px;
				/*border:solid 1px red;*/
				float: left;
			}
			
			.head_add a {
				text-decoration: none;
				display: block;
				width: 100px;
				height: 35px;
				line-height: 35px;
				background-color: #000093;
				color: #FFFFFF;
				text-align: center;
			}
			
			.head_sort select {
				width: 100px;
				height: 35px;
				line-height: 35px;
			}
			
			.gridly {
				position: relative;
				margin: 0px auto;
			}
			
			.gridly1 {
				position: relative;
				margin: 0px auto;
				height:90px !important;
			}
			
			.gridly1 .brick{
				position: relative;
				height:90px;
				width: 100px;
				float: left;
				overflow: hidden;
				cursor: move;
				border: solid 1px #CCCCCC;
				margin-right:10px;
				
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
			}
			
			.smallRingPic {
				display: block;
				width: 100px;
				height: 100px;
				border: solid 1px #AAAAAA;
				position: relative;
				margin-left: 10px;
			}
			
			.advert_allPic {
				margin-top: 10px;
				display: block;
				clear: all;
				height: 170px;
				width: 100%;
				border: solid 1px #009CDA;
				overflow-y: scroll;
			}
			/*------删除图标的样式---*/
			
			.deletepath1 {
				position: absolute;
				width: 30px;
				top: 0px;
				right: 0px;
			}
			/*-------时间下拉框的样式修改------*/
			.time_show{
				overflow: hidden;
				clear: left;
			}
			   .time_show select{
			   	  width:100px;
			   	  float: left;
			   	  margin-right: 10px;
			   }
		</style>
	</head>

	<div class="am-g doc-am-g am-container">
			<div class="am-u-lg-12">
				<input type="hidden" value="${data.id}">
				<input type="hidden" value="${data.advertiserId}">
				<form class="am-form">
					<fieldset>

						<!------------以下是添加完广告商信息的时候出现下面这个轮数上传的页面------->
						<div id="all_file" style="margin-top: 10%;">
							<div class="am-form-group am-form-file">
								<button type="button" class="am-btn am-btn-primary set" operatePath1="1" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 700, height:500}">
									    请添加全屏广告
									</button>
								<div class="am-modal am-modal-no-btn " tabindex="-1" id="doc-modal-1">
									<div class="am-modal-dialog ad_pop">
										<div class="am-modal-hd">每轮添加页面
											<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
										</div>

										<!------------弹框的form表单开始------->
										<form class="am-form">
											<div class="am-modal-bd ">
												<!-------先选择张数-->
												<div class="am-g doc-am-g am-container">
													<div class="am-u-lg-12">
														<div class="am-form-group">
															<label for="doc-select-4">张数</label>
															<select id="picCount" >
																<option value="">---请选择张数---</option>
																<option value="1">1</option>
																<option value="2">2</option>
																<option value="3">3</option>
															</select>
															<span class="am-form-caret"></span>
														</div>
														<div class="am-form-group">
															上刊时间<input class="datainp" id="dateBegin" type="text" placeholder="请选择" name="beginTime">

														</div>
														<div class="am-form-group">
															下刊时间 <input class="datainp" id="dateend" type="text" placeholder="请选择" name="endTimes">

														</div>
												     <input type="hidden" class="popParId" value="" />
													 <!--------图片的展示区域开始----->
														<div class="ringPic ">
															<div class="drag_area gridly1">
																  
															</div>
															<div class="time_show" style="display: none;">
                                                                   
															</div>

															<!-------查询出来的这个广告商的所有图片-->
															<div class="advert_allPic">
                                                                <h4 style="text-align:center;">广告商资源展示</h4>      
																<div class="advert_allPicShow all_picshow">
																	

																</div>

															</div>
														</div>
														<!--------图片的展示区域结束----->

														<a class="am-btn am-btn-secondary ring_add"  style="margin-top: 30px;">确定</a>
													</div>
												</div>

											</div>
										</form>
										<!-----------以上为弹框的form表单------->

									</div>
								</div>
							</div>

						</div>
						<!--------------页面轮数上传完毕--------------------->
					</fieldset>
				</form>
				<!-------轮数的显示----->
				<div class="gridly">

					<c:forEach items="${data.path1}" var="Path1Items" varStatus="num">
						<div class="brick small" id="${Path1Items.id}" index="${num.index+1}">
						    <input type="hidden" value="${Path1Items.id}" class="parId"/>
							<div class="show_index">${num.index+1}</div>
							<!------以下为设置区域----->
							<div class="show_operate">
								<img src="<%=path%>/images/delete.png" class="delete" />
							    <img src="<%=path%>/images/set.png" class="set"  operatePath1="2" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 700, height:500}"/>
							</div>
							<div class="small_pic_drag">
								<!--------------在这去循环资源的图片拼写------->
								<c:forEach items="${Path1Items.list}" var="RingItems" varStatus="ringnum">

									<c:if test="${ringnum.index <=1}">
										<div class="ring first ringOther" id="${RingItems.id}">
											<div class="pic_area">
											    <!--判断取到的资源类型得到不同的表示标签-->
											    <c:if test="${RingItems.resourceType=='image'}">
												  <img class="oneRing" src="${RingItems.url}"/>
												</c:if>
												<c:if test="${RingItems.resourceType=='video'}">
												   <video controls class="oneRing"><source src="${RingItems.url}" type='video/mp4' /></video>
												  
												</c:if>
											</div>
										</div>
									</c:if>
									<c:if test="${ringnum.index >1}">
										<div class="ring first" id="${RingItems.id}">
											<div class="pic_area">
												<!--判断取到的资源类型得到不同的表示标签-->
											    <c:if test="${RingItems.resourceType=='image'}">
												  <img class="oneRing" src="${RingItems.url}" />
												</c:if>
												<c:if test="${RingItems.resourceType=='video'}">
												   <video controls class="oneRing"><source src="${RingItems.url}" type='video/mp4' /></video>
												  
												</c:if>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</div>
							<!------控制区域------>
							<div class="control">
							    <c:if test="${Path1Items.beginTime ==null}">
							        <span class="update">节目单开始时间</span>
							    </c:if>
							    <c:if test="${Path1Items.beginTime !=null}">
							       <span class="update">${Path1Items.beginTime}</span>
							    </c:if>
								
								<c:if test="${Path1Items.endTime ==null}">
							        <span class="update">节目单结束时间</span>
							    </c:if>
							    <c:if test="${Path1Items.endTime !=null}">
							       <span>${Path1Items.endTime}</span>
							    </c:if>
							</div>
						</div>

					</c:forEach>
				</div>
				<!-------轮数的显示完毕--->

			</div>
		</div>
		
		