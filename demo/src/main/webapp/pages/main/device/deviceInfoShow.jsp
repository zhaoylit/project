<%@ page language="java"  import="java.util.*,com.zkkj.backend.common.util.*"  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	//获取配置文件的websocket连接地址
	String websocketUrl =  (String)CustomizedPropertyConfigurer.getContextProperty("websockt_url");
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="-1" var="programSynchId"></c:set>
<!-- <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/bootstrap-3.3.4.css"> -->
<link href="<%=path %>/css/index/bootstrap.css" media="all" rel="stylesheet" type="text/css" />
<link href="<%=path %>/pages/plugins/popup/css/bootstrap-popover-x.css" media="all" rel="stylesheet" type="text/css" />
<!-- <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script> -->
<!-- <script src="http://www.jq22.com/jquery/bootstrap-3.3.4.js"></script> -->
<script src="<%=path %>/javascript/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="<%=path %>/javascript/bootstrap.min.js" type="text/javascript"></script>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<script src="<%=path %>/pages/plugins/popup/js/bootstrap-popover-x.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/pages/plugins/My97DatePicker/WdatePicker.js"></script>
<style>
<style>
html { overflow-x:hidden; }
input{border:1px solid black;}
</style>
<!-- 设备信息展示区域 -->
<div style="width:550px;border:1px solid bule;margin-left:100px;">
	<c:if test="${data.size() == 0 }">
		<div style="margin-top:20px;color:red;">
			该厅暂无设备信息
		</div>
	</c:if>
	<c:forEach items="${data }" var="device" varStatus="index">
		<c:if test="${programSynchId != device.programSynchId}">
			<c:set value="${device.programSynchId }" var="programSynchId"></c:set>
			<div style="width:550px;height:25px;background-color:#548399;margin-left:10px;margin-top:10px;float:left;padding:5px 0 5px 2px;">
				<a style="color:white;" title="点击查看节目单详情" onclick="changeLocation(this);" href="javascript:void();"   data-toggle="popover-x" data-target="#curProgramInfo${device.uuid}" data-placement="bottom">节目单信息</a>
				<c:if test="${programSynchId != 0 && device.synchInfo.programStatus != '3'}">
					&nbsp;&nbsp;<a href="javascript:void();" style="color:white;" data-toggle="popover-x" data-target="#synchPlay${device.programSynchId}" data-placement="bottom">播放同步</a>
					&nbsp;&nbsp;<a href="<%=path %>/program/programDownload.do?synchId=${programSynchId}"  title="节目单打包下载"  target="_blank" style="color:white;">下载</a>
				</c:if>
				<!-- 节目单信息的提示框-->
				<div id="curProgramInfo${device.uuid}" class="popover popover-default" style="width:300px;height:250px;">
				    <div class="arrow"></div>
				    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>节目单信息</h3>
				    <div class="popover-content" id="play_synch_show">
				    	<c:if test="${programSynchId == 0 }">
				    		<font color="red">暂未推送节目单</font>
				    	</c:if>
				    	<c:if test="${programSynchId > 0 }">
					    	推送类型：
					      	<c:if test="${device.synchInfo.pushType == 1 }">
					      	核心服务器同步后推送
					      	</c:if>
					      	<c:if test="${device.synchInfo.pushType == 2 }">
					      	厅服务器直接推送
					      	</c:if>
					      	<c:if test="${device.synchInfo.pushType == 3 }">
					      	核心服务器直接推送
					      	</c:if><br/>
					      	 节目单名称：${device.synchInfo.programName }<br/>
					      	<!-- json数据：${device.synchInfo.programJson}<br/> -->
					      	版本号：${device.synchInfo.version }<br/>
					      	播放开始时间：${device.synchInfo.beginTime }<br/>
					      	播放结束时间：${device.synchInfo.endTime }<br/>
					      	资源总数：${device.synchInfo.total }<br/>
					      	节目单json：<a href="<%=path %>/program/downloadProgramJson.do?synchId=${device.synchInfo.synchId }" target="_blank">下载</a><br/>
				    	</c:if>
				    </div>
				</div>
				<!-- 节目单播放画面同步-->
				<div id="synchPlay${device.programSynchId}" class="popover popover-default" style="width:300px;height:150px;">
				    <div class="arrow"></div>
				    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>设置画面定时同步的时间</h3>
				    <div class="popover-content">
				    	上次推送：${device.synchInfo.synchPlayDate }
				    	<c:if test="${device.synchInfo.synchPlayDate != null and device.synchInfo.synchPlayDate != ''}">
				    	<a href="javascript:void();" onclick="viewDeviceSynchPushInfo(${device.programSynchId});">详情</a>
				    	</c:if>
				    	<br><br>
				    	同步时间：<input type="text" class="Wdate" style="width:160px;" name="synchTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="" />
				    	<input type="button" style="margin-top:10px;" onclick="pushSynchPlayTime('${device.programSynchId }');" value="推送指令给播放该节目单的所有设备"/>
				    </div>
				</div>
			</div>
		</c:if>
		<div style="width:150px;height:150px;border:1px solid gray;float:left;margin-left:10px;margin-top:5px;">
			<div style="width:20px;height:20px;padding:1px 0 0 5px;margin-top:5px;position:absolute;background:url(<%=path%>/images/device/circle.png);background-size:20px 20px;">
				<span>${index.index + 1}</span>
			</div>
			<div style="width:22px;height:32px;position:absolute;line-height:32px;margin-left:23px;font-weight:bold;font-size:15px;">
				<span>${device.deviceIp}</span>
			</div>
			<!-- 基本信息设置 -->
			<div style="width:22px;height:22px;position:absolute;line-height:32px;margin:125px 0 0 0px;font-weight:bold;font-size:15px;">
				<img src="<%=path %>/images/device/setting.png" onclick="setDeviceInfo('${device.id}');" style="cursor:pointer;width:20px;height:20px;" title="点击修改设备信息"/>
			</div>
			<!-- 删除设备 -->
			<div style="width:22px;height:22px;position:absolute;line-height:32px;margin:125px 0 0 22px;font-weight:bold;font-size:15px;">
				<img src="<%=path %>/images/device/delete.png" onclick="deleteDeviceInfo('${device.id}');" style="cursor:pointer;width:20px;height:20px;" title="点击删除设备"/>
			</div>
			<div style="width:22px;height:22px;position:absolute;line-height:32px;margin:125px 0 0 125px;font-weight:bold;font-size:15px;">
				<img src="<%=path %>/images/device/APK.png" onclick="changeLocation(this);" style="cursor:pointer;width:20px;height:20px;" title="点击查看最新apk推送信息" data-toggle="popover-x" data-target="#apkVersionInfo${device.uuid}" data-placement="bottom"/>
				<!-- apk版本信息-->
				<div id="apkVersionInfo${device.uuid}" class="popover popover-default" style="width:300px;height:150px;">
				    <div class="arrow"></div>
				    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>当前apk推送记录</h3>
				    <div class="popover-content">
				    	<c:if test="${device.apkPushStatus == 0}">
				    		<font color="red">暂无推送apk更新记录</font>
				    	</c:if>
				    	<c:if test="${device.apkPushStatus == 1}">
					      	推送类型：
					      	<c:if test="${device.apkSynchInfo.pushType == 1 }">
					      	核心服务器同步后推送
					      	</c:if>
					      	<c:if test="${device.apkSynchInfo.pushType == 2 }">
					      	厅服务器直接推送
					      	</c:if>
					      	<c:if test="${device.apkSynchInfo.pushType == 3 }">
					      	核心服务器直接推送
					      	</c:if><br/>
					      	推送状态：<span id="apk_push_status">
					      		<c:if test="${device.apkSynchInfo.synchStatus == 0}">待接收</c:if>
					      		<c:if test="${device.apkSynchInfo.synchStatus == 1}">已接收</c:if>
					      		<c:if test="${device.apkSynchInfo.synchStatus == 2}">已下载</c:if>
					      		<c:if test="${device.apkSynchInfo.synchStatus == 3}">已安装</c:if>
					      	</span><br/>
					      	版本号：${device.apkSynchInfo.versionCode }<br/>
					      	文件大小：<fmt:formatNumber type="number" value="${device.apkSynchInfo.fileSize/1024 }" pattern="0.00" maxFractionDigits="2"/>kb<br/>
					      	推送json：<a href="<%=path %>/apk/downloadApkJson.do?synchId=${device.apkSynchInfo.synchId }" target="_blank">下载</a>
				    	</c:if>
				    </div>
				</div>
			</div>
			<!-- 设备重启 -->
			<div style="width:22px;height:22px;position:absolute;line-height:32px;margin:125px 0 0 100px;font-weight:bold;font-size:15px;">
				<img src="<%=path %>/images/device/reboot.png" onclick="deviceReboot('${device.uuid}');" style="cursor:pointer;width:20px;height:20px;" title="点击进行重启设备"/>
			</div>
			<!-- 查询广告日志 -->
			<div style="width:22px;height:22px;position:absolute;line-height:32px;margin:125px 0 0 75px;font-weight:bold;font-size:15px;">
				<img src="<%=path %>/images/device/log.png" onclick="viewDevicePlayLog('${device.uuid}');" style="cursor:pointer;width:20px;height:20px;" title="点击查询设备的播放日志信息"/>
			</div>
			<div style="width:150px;height:120px;margin-top:30px;">
				<c:choose>
					<c:when test="${device.deviceType == 1}">
						<img title="厅服务器" src="<%=path %>/images/device/device-server.png"/>
					</c:when>
					<c:otherwise>
						<img title="工作站" src="<%=path %>/images/device/device-workstation.png"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${device.connectionStatus == 1}">
						<img title="网络连接正常" src="<%=path %>/images/device/wifi-success.png"/>
					</c:when>
					<c:otherwise>
						<img title="网络连接异常" src="<%=path %>/images/device/wifi-error.png"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${device.playStatus == '1'}">
						<img title="广告播放正常" src="<%=path %>/images/device/play-success.png"/>
					</c:when>
					<c:otherwise>
						<img title="广告播放异常" src="<%=path %>/images/device/play-error.png"/>
					</c:otherwise>
				</c:choose>
				<br/>
				<!-- 判断节目单是否同步成功 -->
				<c:if test="${device.pushStatus == 1}">
					<!-- 节目单未到播放时间，显示节目单资源的下载情况-->
					<c:if test="${device.synchInfo.programStatus == 1 }">
						&nbsp;<font style="color:#b3b334;">节目单待播放</font><br/>
						&nbsp;<a style="color:green;" href="javascript:void();" onclick="changeLocation(this);"  data-toggle="popover-x" data-target="#resourceSynchInfo${device.uuid}" data-placement="bottom">资源同步信息</a>
						<br/>
						<!-- 资源同步信息提示框-->
						<div id="resourceSynchInfo${device.uuid}" class="popover popover-default" style="width:300px;height:150px;">
						    <div class="arrow"></div>
						    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>资源同步信息</h3>
						    <div class="popover-content" id="play_synch_show">
						      	<b>推送状态</b>：<span id="resource_synch_status">
						      		<c:if test="${device.synchInfo.synchStatus == 0}">等待接收资源</c:if>
						      		<c:if test="${device.synchInfo.synchStatus == 1}">资源已接收</c:if>
						      		<c:if test="${device.synchInfo.synchStatus == 2}">资源下载中</c:if>
						      		<c:if test="${device.synchInfo.synchStatus == 3}">资源下载完成</c:if>
						      	</span><br/>
						      	<b>资源总数</b>：${device.synchInfo.total }<br/>
						      	<b>下载成功</b>：<span id="resource_download_success">${device.synchInfo.success }</span><br/>
						      	<b>下载失败</b>：<span id="resource_download_error">${device.synchInfo.error }</span>
						      	<a id="resource_error_view" href="javascript:void();" <c:if test="${device.synchInfo.error == 0 }">style="display:none;"</c:if> onclick="viewDownloadErrorInfo(${device.synchInfo.psdiId });">查看</a>
						      	<br/>
						    </div>
						</div>
					</c:if>
					<!-- 节目单正在播放，显示正在播放的画面 -->
					<c:if test="${device.synchInfo.programStatus == 2 }">
						<!-- 如果节目单播放正常的话，显示播放的画面 -->
						<c:if test="${device.playStatus == '1'}">
							&nbsp;<font style="color:green;">节目单正常播放</font><br/>
							&nbsp;<a style="color:green;" href="javascript:void();"   onclick="changeLocation(this);"  data-toggle="popover-x" data-target="#playScreen${device.uuid}" data-placement="bottom">播放画面</a>
							<!-- 当前播放画面的同步框 -->
							<div id="playScreen${device.uuid}" class="popover popover-default" style="width:230px;height:300px;">
							    <div class="arrow"></div>
							    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>当前播放画面</h3>
							    <div class="popover-content" id="play_synch_show">
							      	 暂无播放画面
							    </div>
							</div>
							<br/>
							&nbsp;<a style="color:green;" href="javascript:void();" onclick="changeLocation(this);"  data-toggle="popover-x" data-target="#resourceSynchInfo${device.uuid}" data-placement="bottom">资源同步信息</a>
							<br/>
							<!-- 资源同步信息提示框-->
							<div id="resourceSynchInfo${device.uuid}" class="popover popover-default" style="width:300px;height:150px;">
							    <div class="arrow"></div>
							    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>资源同步信息</h3>
							    <div class="popover-content" id="play_synch_show">
							      	<b>推送状态</b>：<span id="resource_synch_status">
							      		<c:if test="${device.synchInfo.synchStatus == 0}">等待接收资源</c:if>
							      		<c:if test="${device.synchInfo.synchStatus == 1}">资源已接收</c:if>
							      		<c:if test="${device.synchInfo.synchStatus == 2}">资源下载中</c:if>
							      		<c:if test="${device.synchInfo.synchStatus == 3}">资源下载完成</c:if>
							      	</span><br/>
							      	<b>资源总数</b>：${device.synchInfo.total }<br/>
							      	<b>下载成功</b>：<span id="resource_download_success">${device.synchInfo.success }</span><br/>
							      	<b>下载失败</b>：<span id="resource_download_error">${device.synchInfo.error }</span>
							      	<a id="resource_error_view" href="javascript:void();" <c:if test="${device.synchInfo.error == 0 }">style="display:none;"</c:if> onclick="viewDownloadErrorInfo(${device.synchInfo.psdiId });">查看</a>
							      	<br/>
							    </div>
							</div>
						</c:if>
						<c:if test="${device.playStatus == '2'}">
							&nbsp;<font color="red">节目单未正常播放</font><br/>
							&nbsp;<a style="color:green;" href="javascript:void();"  onclick="changeLocation(this);"  data-toggle="popover-x" data-target="#resourceSynchInfo${device.uuid}" data-placement="bottom">资源同步信息</a>
							<br/>
							<!-- 资源同步信息提示框-->
							<div id="resourceSynchInfo${device.uuid}" class="popover popover-default" style="width:300px;height:150px;">
							    <div class="arrow"></div>
							    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>资源同步信息</h3>
							    <div class="popover-content" id="play_synch_show">
							      	<b>推送状态</b>：<span id="resource_synch_status">
							      		<c:if test="${device.synchInfo.synchStatus == 0}">等待接收资源</c:if>
							      		<c:if test="${device.synchInfo.synchStatus == 1}">资源已接收</c:if>
							      		<c:if test="${device.synchInfo.synchStatus == 2}">资源下载中</c:if>
							      		<c:if test="${device.synchInfo.synchStatus == 3}">资源下载完成</c:if>
							      	</span><br/>
							      	<b>资源总数</b>：${device.synchInfo.total }<br/>
							      	<b>下载成功</b>：<span id="resource_download_success">${device.synchInfo.success }</span><br/>
							      	<b>下载失败</b>：<span id="resource_download_error">${device.synchInfo.error }</span>
							      	<a id="resource_error_view" href="javascript:void();" <c:if test="${device.synchInfo.error == 0 }">style="display:none;"</c:if> onclick="viewDownloadErrorInfo(${device.synchInfo.psdiId });">查看</a>
							      	<br/>
							    </div>
							</div>
							&nbsp;<a style="color:red;" href="javascript:void();" onclick="viewErrorInfo('${device.uuid }');">异常信息</a>
						</c:if>
					</c:if>
					<!-- 节目单播放结束，显示播放的次数等信息 -->
					<c:if test="${device.synchInfo.programStatus == 3 }">
						&nbsp;<font style="font-weight:bold;">节目单已下刊</font><br/>
						&nbsp;<a style="color:green;" href="javascript:void();" onclick="changeLocation(this);"  data-toggle="popover-x" data-target="#resourceSynchInfo${device.uuid}" data-placement="bottom">资源同步信息</a>
						<br/>
						<!-- 资源同步信息提示框-->
						<div id="resourceSynchInfo${device.uuid}" class="popover popover-default" style="width:300px;height:150px;">
						    <div class="arrow"></div>
						    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>资源同步信息</h3>
						    <div class="popover-content" id="play_synch_show">
						      	<b>推送状态</b>：<span id="resource_synch_status">
						      		<c:if test="${device.synchInfo.synchStatus == 0}">等待接收资源</c:if>
						      		<c:if test="${device.synchInfo.synchStatus == 1}">资源已接收</c:if>
						      		<c:if test="${device.synchInfo.synchStatus == 2}">资源下载中</c:if>
						      		<c:if test="${device.synchInfo.synchStatus == 3}">资源下载完成</c:if>
						      	</span><br/>
						      	<b>资源总数</b>：${device.synchInfo.total }<br/>
						      	<b>下载成功</b>：<span id="resource_download_success">${device.synchInfo.success }</span><br/>
						      	<b>下载失败</b>：<span id="resource_download_error">${device.synchInfo.error }</span>
						      	<a id="resource_error_view" href="javascript:void();" <c:if test="${device.synchInfo.error == 0 }">style="display:none;"</c:if> onclick="viewDownloadErrorInfo(${device.synchInfo.psdiId });">查看</a>
						      	<br/>
						    </div>
						</div>
					</c:if>
				</c:if>
				<c:if test="${device.pushStatus == 0}">
					&nbsp;<font style="color:red;font-weight:bold;">未推送节目单</font>
				</c:if>
			</div>
		</div>
	</c:forEach>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" style="z-index:99999;margin-top:50px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="padding:5px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="javascript:$('#modelContent').next().hide();">&times;</button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div id="modelContent" class="modal-body" style="height:300px;overflow-y:scroll">
            </div>
            <div class="modal-footer" style="display:none;">
	            <button type="button" class="btn btn-primary">提交更改</button>
	        </div> 
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script type="text/javascript">
$(function(){
	var ws = new WebSocket("<%=websocketUrl%>");
	ws.onopen = function () {  
	};  
	ws.onmessage = function (event) { 
		var json = eval("(" + event.data + ")");//解析json字符串
		var type = json.type;
		var uuid = json.uuid;
		if(type == "play_synch"){
			var path = json.path;
			var imageStr = '<img src="'+path+'" style="height:250px;width:200px;"/>';
			//画面同步
			$("#playScreen"+uuid).find("#play_synch_show").html(imageStr);
		}
		if(type == "resource_download_success"){
			var count = json.count;//下载成功的资源数量	
			//修改资源下载成功的数量
			$("#resourceSynchInfo"+uuid).find("#resource_synch_status").html("资源下载中");	
			$("#resourceSynchInfo"+uuid).find("#resource_download_success").html(count);
		}
		if(type == "resource_download_error"){
			var count = json.count;//下载失败的数量
			//修改资源下载成功的数量
			$("#resourceSynchInfo"+uuid).find("#resource_synch_status").html("资源下载中");
			$("#resourceSynchInfo"+uuid).find("#resource_error_view").show();
			$("#resourceSynchInfo"+uuid).find("#resource_download_error").html(count);
		}
		if(type == "resource_download_all"){
			var count = json.count;//下载失败的数量
			//修改资源下载成功的数量
			$("#resourceSynchInfo"+uuid).find("#resource_synch_status").html("资源下载完成");
			$("#resourceSynchInfo"+uuid).find("#resource_download_error").html("0");
			$("#resourceSynchInfo"+uuid).find("#resource_error_view").hide();
		}
	};  
	ws.onclose = function (event) {  
	    setConnected(false);  
	    log(event);  
	};  
})

//查看设备资源下载失败的异常信息
function viewDownloadErrorInfo(id){
	$.ajax({
		method:"post",
		url:"<%=path%>/device/getProgramSynchDeviceDownloadErrorInfo.do",
		data:{
			pasiId:id
		},
		success:function(data){
			$("#myModalLabel").html("失败的资源信息");
			$("#modelContent").html(data);
			$('#myModal').modal();
			$(".modal-backdrop").hide();
		}
		,dataType:"html"
	});
}
//查看设备播放异常的信息
function viewErrorInfo(uuid){
	//打开loading提示框
	$("#loading",window.parent.document).show();
	$.ajax({
		method:"post",
		url:"<%=path%>/device/getDeviceExceptionInfo.do",
		data:{
			deviceId:uuid
		},
		success:function(data){
			$("#myModalLabel").html("设备异常信息");
			$("#modelContent").html(data);
			$('#myModal').modal();
			$(".modal-backdrop").hide();
			
			//关闭页面loading提示框
			$("#loading",window.parent.document).slideUp(1000);
		}
		,dataType:"html"
	});
}
//查看推送的设备同步画面指令的详情
function viewDeviceSynchPushInfo(synchId){
	$.ajax({
		method:"post",
		url:"<%=path%>/device/getDevicePushPlaySynchInfo.do",
		data:{
			synchId:synchId
		},
		success:function(data){
			$("#myModalLabel").html("推送画面同步指令设备接收详情");
			$("#modelContent").html(data);
			$('#myModal').modal();
			$(".modal-backdrop").hide();
		}
		,dataType:"html"
	});
}
//查询设备的播放日志
function viewDevicePlayLog(uuid){
	//打开loading提示框
	$("#loading",window.parent.document).show();
	$.ajax({
		method:"post",
		url:"<%=path%>/device/getDevicePlayLogList.do",
		data:{
			uuid:uuid
		},
		success:function(data){
			$("#myModalLabel").html("播放日志");
			$("#modelContent").html(data);
			$('#myModal').modal();
			$(".modal-backdrop").hide();
			
			
			//关闭页面loading提示框
			$("#loading",window.parent.document).slideUp(1000);
		}
		,dataType:"html"
	});
}
//修改设备信息
function setDeviceInfo(id){
	//打开loading提示框
	$("#loading",window.parent.document).show();
	$.ajax({
		method:"post",
		url:"<%=path%>/device/addOrEditDeviceInit.do",
		data:{
			id:id
		},
		success:function(data){
			$("#myModalLabel").html("设备信息修改");
			$("#modelContent").html(data);
			$('#myModal').modal();
			$(".modal-backdrop").hide();
			//显示模态框提交按钮
			$("#modelContent").next().find("button").attr("onclick","saveChange();");
			$("#modelContent").next().show();
			
			//关闭页面loading提示框
			$("#loading",window.parent.document).slideUp(1000);
			
		}
		,dataType:"html"
	});
	<%-- $("#modelContent").html('<iframe src="<%=path%>/device/addOrEditDeviceInit.do" style="width:100%;height:100%;"></iframe>');
	$('#myModal').modal();
	$(".modal-backdrop").hide();
	//显示模态框提交按钮
	$("#modelContent").next().find("button").attr("onclick","saveChange();");
	$("#modelContent").next().show(); --%>
}
function saveChange(){
	$.ajax({
		method:"post",
		url:"<%=path%>/device/addOrEditDevice.do",
		data:{
			id:$("#id").val(),
			deviceNo:$("#deviceNo").val(),
			deviceType:$("#deviceType").val(),
			deviceName:$("#deviceName").val(),
			airlineId:$("#airlineId").combobox("getValue"),
			airportId:$("#airportId").combobox("getValue"),
			viproomId:$("#viproomId").combobox("getValue"),
		},
		success:function(data){
			if(data.result == "1"){
				alert("修改成功");
				//关闭模态框
				$('#myModal').modal('hide');
				$("#modelContent").next().hide();
			}else{
				alert("修改失败，异常信息为："+data.message);	
			}
		}
		,dataType:"json"
	});
}
//设备重启
function deviceReboot(uuid){
	if(confirm("确定？")){
		//打开loading提示框
		$("#loading",window.parent.document).show();
		$.ajax({
			method:"post",
			url:"<%=path%>/device/reboot.do",
			data:{
				deviceIds:uuid,
				viproomId:parent.viproomId_
			},
			success:function(data){
				if(data.result == "1"){
					alert(data.message);
				}else{
					alert(data.message);
				}
				//关闭页面loading提示框
				$("#loading",window.parent.document).slideUp(1000);
			}
			,dataType:"json"
		});
	}
}
function changeLocation(dd){
	var height = $(window).height();
	var obj = $(dd).next();
	/* setTimeout(function(){
		var X1 = obj.position().top;
		var Y1 = obj.position().left;
		var curHeight = obj.css("height");
		curHeight = curHeight.replace("px","");
		if(X1 + curHeight > height){
			$(document).scrollTop((X1 + curHeight) - height); 
// 			$(document).animate({scrollTop:((X1 + curHeight) - height)+"px"},800);
		}
	},300); */
}
function viewDetailInfo(dd){
	if($(dd).children().eq(0).css("display") == "block"){
		$(dd).children().eq(0).hide();
		$(dd).children().eq(1).show();
	}else{
		$(dd).children().eq(1).hide();
		$(dd).children().eq(0).show();
	}
}
function updateDevice(id,uuid){
	$.ajax({
		method:"post",
		url:"<%=path%>/device/addOrEditDevice.do",
		data:{
			id:id,
			deviceNo:$("#deviceForm"+uuid).find("input[name='deviceNo']").val(),
			
		},
		success:function(data){
			if(data.result == "1"){
				alert("修改成功！");
			}else{
				alert("修改失败！");
			}
		}
		,dataType:"json"
	});
}
function pushSynchPlayTime(programSynchId){
	if(confirm("确定？")){
		//打开loading提示框
		$("#loading",window.parent.document).show();
		$.ajax({
			method:"post",
			url:"<%=path%>/device/pushSynchPlayTime.do",
			data:{
				synchId:programSynchId,
				synchTime:$("#synchPlay"+programSynchId).find("input[name='synchTime']").val()
			},
			success:function(data){
				if(data.result == "1"){
					alert(data.message);
				}else{
					alert(data.message);
				}
				//关闭页面loading提示框
				$("#loading",window.parent.document).slideUp(1000);
			}
			,dataType:"json"
		});
	}
}
function deleteDeviceInfo(id){
	if(confirm("确定？")){
		$.ajax({
			method:"post",
			url:"<%=path%>/device/deleteDevice.do",
			data:{
				id:id,
			},
			success:function(data){
				if(data.result == "1"){
					alert("删除成功，请刷新后查看");
				}else{
					alert(data.message);
				}
			}
			,dataType:"json"
		});
	}
}
</script>  
</script>