<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ page import="com.zkkj.chat.util.*,java.util.*,java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	
	String websockt_url =  PropertiesConfig.getProperties("websockt_url");
	String return_path_z =  PropertiesConfig.getProperties("return_path_z");
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set value="${userSession.id }" var="curUserId"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>
<c:if test="${type == 1 }">
与${toUserNickName }聊天中
</c:if>
<c:if test="${type == 2 }">
在-${groupName }-群聊中
</c:if>

</title>
<link href="<%=path%>/chat/plug/qqFace/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="<%=path%>/chat/plug/qqFace/css/reset.css">
<style type="text/css">
.comment { width: 680px; margin: 20px auto; position: relative; background: #fff; padding: 20px 50px 50px; border: 1px solid #DDD; border-radius: 5px; }
.comment h3 { height: 28px; line-height: 28px }
.com_form { width: 100%; position: relative }
.input { width: 99%; height: 60px; border: 1px solid #ccc }
.com_form p { height: 28px; line-height: 28px; position: relative; margin-top: 10px; }
span.emotion { width: 42px; height: 20px; padding-left: 20px; cursor: pointer }
span.emotion:hover { background-position: 2px -28px }
.qqFace { margin-top: 4px; background: #fff; padding: 2px; border: 1px #dfe6f6 solid; }
.qqFace table td { padding: 0px; }
.qqFace table td img { cursor: pointer; border: 1px #fff solid; }
.qqFace table td img:hover { border: 1px #0066cc solid; }
#show { width: 770px; margin: 20px auto; background: #fff; padding: 5px; border: 1px solid #DDD; vertical-align: top; }
.sub_btn { position: absolute; right: 0px; top: 0; display: inline-block; zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  *display: inline;
vertical-align: baseline; margin: 0 2px; outline: none; cursor: pointer; text-align: center; font: 14px/100% Arial, Helvetica, sans-serif; padding: .5em 2em .55em; text-shadow: 0 1px 1px rgba(0,0,0,.6); -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2); -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2); box-shadow: 0 1px 2px rgba(0,0,0,.2); color: #e8f0de; border: solid 1px #538312; background: #64991e; background: -webkit-gradient(linear, left top, left bottom, from(#7db72f), to(#4e7d0e)); background: -moz-linear-gradient(top, #7db72f, #4e7d0e);  filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#7db72f', endColorstr='#4e7d0e');
}
.sub_btn:hover { background: #538018; background: -webkit-gradient(linear, left top, left bottom, from(#6b9d28), to(#436b0c)); background: -moz-linear-gradient(top, #6b9d28, #436b0c);  filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#6b9d28', endColorstr='#436b0c');
}
</style>
<script type="text/javascript" src="<%=path %>/chat/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/chat/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path %>/chat/plug/qqFace/js/jquery.qqFace.js"></script>
<!-- 引入录音文件 -->
<script type="text/javascript" src="<%=path %>/chat/plug/record/js/recordmp3.js"></script>


	<!--    qq表情插件 -->
   <script type="text/javascript">
	$(function(){
		//初始化表情插件
		$('.emotion').qqFace({
			id : 'facebox', 
			assign:'textMsg',//表情赋值对象的id 
			path:'<%=path%>/chat/plug/qqFace/arclist/'	//表情存放的路径
		});
	});
	//表情点击事件
	function qqFaceClickEvent(labFace){
		//将选择的表情添加到输入框中
// 		$("#textMsg").val($("#textMsg").val() + labFace);
		var obj = document.getElementById("textMsg");
		//插入表情到对象光标所在的位置
		addImage($("#textMsg"),replace_em(labFace));
	}
	//将表情字符串替换为表情
	function replace_em(str){
		str = str.replace(/\</g,'&lt;');
		str = str.replace(/\>/g,'&gt;');
		str = str.replace(/\n/g,'<br/>');
		str = str.replace(/\[em_([0-9]*)\]/g,'<img src="<%=path%>/chat/plug/qqFace/arclist/$1.gif" border="0" />');
		return str;
	}
	//在光标出插入元素,textarea
	function addTextAtFocus(t, txt){   
		var val = t.value;
		  if(document.selection){
		      t.focus()
		      document.selection.createRange().text = txt;  
		  } else { 
		   var cp = t.selectionStart;
		   var ubbLength = t.value.length;
		   var s = t.scrollTop;
		  // document.getElementById('aaa').innerHTML += s + '<br />';
		   t.value = t.value.slice(0,t.selectionStart) + txt + t.value.slice(t.selectionStart, ubbLength);
		   this.setCursorPosition(t, cp + txt.length);
		  // document.getElementById('aaa').innerHTML += t.scrollTop + '<br />';
		   firefox=navigator.userAgent.toLowerCase().match(/firefox\/([\d\.]+)/) && setTimeout(function(){
		    if(t.scrollTop != s) t.scrollTop=s;
		   },0)

		};
	}
	//在对象光标出插入，div
	function addImage(obj,text){
		var range, node;
		if(!obj.hasfocus) {
			obj.focus();
		}
		if (window.getSelection && window.getSelection().getRangeAt) {
			range = window.getSelection().getRangeAt(0);
			range.collapse(false);
			node = range.createContextualFragment(text);
			var c = node.lastChild;
			range.insertNode(node);
			if(c){
				range.setEndAfter(c);
				range.setStartAfter(c)
			}
			var j = window.getSelection();
			j.removeAllRanges();
			j.addRange(range);
		} else if (document.selection && document.selection.createRange) {
			document.selection.createRange().pasteHTML(text);
		}
	}
</script>
<script type="text/javascript">
    var socket;
    initSocket();
    function initSocket(){
    	console.log("dasdas");
    	socket = new WebSocket("<%=websockt_url%>?userId=${fromUserId}");
        /*
         *监听三种状态的变化 。js会回调
         */
        socket.onopen = function(message) {
        	//客户端连接成功,向后台发送心跳检测,每隔10秒发送一次
        	
     	   setInterval(function (){
     	        var json = {};
     	     	//定义json 发送消息  
     	        var json = {};
     	        json.message = "";
     	        json.extra = "";
     	        json.messageType = "4";//心跳消息
     	        json.type = "${type}";//心跳消息
     	        json.fromUserId = "${fromUserId}";
     	        json.toUserId = "";
     	        json.groupId = "";
     	        socket.send(JSON.stringify(json));
     	   },3000);
        };
        socket.onclose = function(message) {
        	socket.close();
        };
        socket.onmessage = function(message) {
            showMessage(message.data);
        };
        socket.onerror = function(message) {
        	socket.close();
        };
    }
    function showMessage(message) {
    	var json = eval('(' + message + ')');
		var text = '';
    	if(json.chatType == '${type}'){
    		text = getMsgContent(json);
            $("#showChatMessage").append(text);
            document.getElementById('showChatMessage').scrollTop = 100000;
            //初始化消息撤回事件
            initMsgRecall();
    	}
    }
    //根据消息json得到显示的html消息内容
    function getMsgContent(json){
    	var text = "";
    	//只显示自己所在群的消息
    	if('${type}' == '2' && json.groupId == "${groupId}"){//群聊
			if(json.isSystem){
				if(json.messageType == "5"){
					//删除页面上的消息
					$(".msgContent[msgId="+json.msgId+"]").remove();
					//消息类型为，撤回的系统消息
					text +='<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">'+json.nickName+'：'+json.message+'</div>';
				}else{
					//其他系统消息
					text +='<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">'+json.nickName+'：'+json.message+'</div>';
				}
			}else{
				text + "";
				text += '<div class="msgContent" initHover="0" fromUserId="'+json.fromUserId+'" msgId="'+json.msgId+'" msgDate="'+json.msgDate+'" style="width:95%;min-height:40px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;margin:0 0 10px 0;">';
        	  	text += ' 	<img src="<%= return_path_z%>'+json.headUrl+'" style="width:30px;height:30px;border-radius:30px;float:left;"/>';
        	  	text += ' 	<div style="width:200px;height:15px; border:0px solid blue; margin:5px 0 0 32px;font-size: 11px;color:gray;">'+json.nickName +' &nbsp; <span style="color:#1296db;">'+ json.dateShow+'</span></div>';
        	  	text += ' 	<div style="width:90%;min-height:15px; border:0px solid blue; margin:3px 0 0 32px;font-size: 11px;">';
        	  	if(json.messageType == "1"){
        	  		//文本消息
        	  		text += json.message;
        	  	}else if(json.messageType == "3"){
        	  		text += '<img src="<%=path %>/chat/image/file.png" style="cursor:pointer;" title="这是一个附件"/>';
        	  		text += json.message + '&nbsp;<a href="<%=path%>/resource?method=download&path='+json.extra+'&name='+json.message+'" target="_blank">下载</a>';
        	  	}
        	    text += '   </div>';
        	    text += '</div>';
			}
		}else if('${type}' == '1' && (json.fromUserId == '${fromUserId}' || json.fromUserId == '${toUserId}')){//单聊
			if(json.isSystem){
				if(json.messageType == "5"){
					//删除页面上的消息
					$(".msgContent[msgId="+json.msgId+"]").remove();
					//消息类型为，撤回的系统消息
					text +='<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">'+json.nickName+'：'+json.message+'</div>';
				}else{
					//其他系统消息
					text +='<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">'+json.nickName+'：'+json.message+'</div>';
				}
			}else{
				if(json.fromUserId == '${fromUserId}'){
					//自己发送的消息 展示在右边
					text += '<div class="msgContent" initHover="0" fromUserId="'+json.fromUserId+'" msgId="'+json.msgId+'" msgDate="'+json.msgDate+'" style="width:95%;min-height:40px;border:0px solid blue;text-align:right;overflow-y:auto;overflow-x:hidden;margin:0 0 10px 0;">';
					text += '	<img src="<%= return_path_z%>'+json.headUrl+'" style="width:30px;height:30px;border-radius:30px;float:right;"/>';
			   	  	text += ' 	<div style="width:200px;height:15px; border:0px solid blue; margin:5px 3px 0 0;font-size: 11px;color:gray;float:right;text-align:right;"><span style="color:#1296db;">'+ json.dateShow+'</span>&nbsp;'+json.nickName+'</div>';
			   	  	text += ' 	<div style="width:94%;min-height:15px; border:0px solid blue; margin:3px 3px 0 0;font-size: 11px;float:right;text-align:right;">';
			   	 	if(json.messageType == "1"){
	        	  		//文本消息
			   	 		text += json.message;
	        	  	}else if(json.messageType == "3"){
	        	  		text += '&nbsp;<a href="<%=path%>/resource?method=download&path='+json.extra+'&name='+json.message+'" target="_blank">下载</a> &nbsp;' + json.message;
	        	  		text += '<img src="<%=path %>/chat/image/file.png" style="cursor:pointer;" title="这是一个附件"/>';
	        	  	}
			   	 	text += '   </div>';
					text += '</div>';
				}else{
					//对方发送的消息 展示在左边
					text += '<div class="msgContent" initHover="0" fromUserId="'+json.fromUserId+'" msgId="'+json.msgId+'" msgDate="'+json.msgDate+'" style="width:95%;min-height:40px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;margin:0 0 10px 0;">';
					text += '	<img src="<%= return_path_z%>'+json.headUrl+'" style="width:30px;height:30px;border-radius:30px;float:left;"/>';
			   	  	text += ' 	<div style="width:200px;height:15px; border:0px solid blue; margin:5px 0 0 32px;font-size: 11px;color:gray;">'+json.nickName+'&nbsp;<span style="color:#1296db;">'+ json.dateShow+'</span></div>';
			   	  	text += ' 	<div style="width:94%;min-height:15px; border:0px solid blue; margin:3px 0 0 32px;font-size: 11px;">';
			   	 	if(json.messageType == "1"){
	        	  		//文本消息
			   	 		text += json.message;
	        	  	}else if(json.messageType == "3"){
	        	  		text += '<img src="<%=path %>/chat/image/file.png" style="cursor:pointer;" title="这是一个附件"/>';
	        	  		text += json.message + '&nbsp;<a href="<%=path%>/resource?method=download&path='+json.extra+'&name=${json.message }" target="_blank">下载</a>';
	        	  	}
			   	 	text += '   </div>';
					text += '</div>';
				}
			}
		}
    	return text;
    }
    //加载更多的消息
    function loadMoreMessage(){
    	//获取最早的时间
    	var msgDate = $(".msgContent:eq(0)").attr("msgDate");
    	//查询更早的20条信息
    	$.ajax({
    		method:"",
    		url:"<%=path%>/group",
    		data:{
    			method:"loadMore",
    			chatType : "${type}",
    			fromUserId : "${fromUserId}",
    			toUserId : "${toUserId}",
    			groupId : "${groupId}",
    		    msgDate : msgDate
     		},
    		success:function (data){
    			if(data.length < 20){
    				//暂无更多消息
    				$("#loadMore").html("没有更多消息了");
    			}
    			//解析json
//     			var json = eval('(' + data + ')');
    			
    			for(var i = data.length - 1;i >= 0 ;i--){
    				var content = getMsgContent(data[i]);
    				//在对象的外面的前面添加
    				$(".msgContent:eq(0)").before(content);
    			}
    			/* $.each(data,function(index,item){
    				
    			}); */
    			//初始化消息撤回事件
    			initMsgRecall();
    		},
    		dataType : "json",
    		error:function(){
    			
    		}
    	});
    }
  	//发送文本消息
    function sendText() {
    	if (!socket || socket.readyState != 1) {
            initSocket();
        }
        /* var input = document.getElementById("textMsg");
        var text = input.value; */
        var text = $("#textMsg").html().trim();
        if(text == null || text == ""){
        	alert("请输入消息内容！");
        	return false;
        }
        
        //定义json 发送消息  
        var json = {};
        json.message = text;
        json.extra = "";
        json.messageType = "1";
        json.type = "${type}";
        json.fromUserId = "${fromUserId}";
        json.toUserId = "${toUserId}";
        json.groupId = "${groupId}";
        socket.send(JSON.stringify(json));
		$("#textMsg").html("");
    }
    //发送附件消息
  	function sendAttchment(fileSize){
  		if (!socket || socket.readyState != 1) {
            initSocket();
        }
  		var filePath = $("#filePath").val();//附件的保存地址
  		var fileName = $("#fileName").val();//附近的原名称
  		if(filePath == null || filePath == ""){
        	alert("请选择附件！");
        	return false;
        }
        socket.send('{"message":"'+fileName+'（'+fileSize+'kb）","extra":"'+filePath+'","messageType":"3","type":"${type}","fromUserId":"${fromUserId}","toUserId":"${toUserId}","groupId":"${groupId}"}');
  	}
    
</script>
</head>
<body>
   <!--    左侧展示区域 -->
   <div style="width: 600px;float:left;" >
   	   <!--    左侧聊天内容展示区 -->
	   <div style="width: 400px; height: 15px;margin:1px 0 5px 10px;font-weight:bold;">
	   		<c:if test="${type == 1 }">
		    	${toUserNickName }
	   		</c:if>
	   		<c:if test="${type == 2 }">
		    	${groupName }
	   		</c:if>
	   </div>
	   <div style="width: 100%; height: 400px; overflow-y: auto; border: 1px solid #ccc; margin:10px 0 0 10px;padding-top:10px;" id="showChatMessage">
		  <!-- 	如果消息数量大于20条 ，显示加载更多 -->
	   	  <c:if test="${messageList.size() >= 20 }">
	   	  <div id="loadMore"  style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">
	   	  	<a href="javascript:void();" onclick="loadMoreMessage();"><img style ="margin-top:-5px;" src="<%=path %>/chat/image/time.png" title="查看更多消息"/>查看更多消息</a>
	   	  </div>
	   	  </c:if>
		  <!--  群聊消息显示 -->
	   	  <c:if test="${type == 2 }">
		   	  <c:forEach items="${messageList }" var="message">
		   	  	  <!-- 显示系统消息，撤回的消息等 -->
	   	  	 	  <c:choose>
	   	  	 	  	 <c:when test="${message.messageType == '4'  or message.messageType == '5'}">
	   	  	 	  	 	<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">${message.nickName }：${message.content }</div>
	   	  	 	  	 </c:when>
	   	  	 	  	 <c:otherwise>
					 	  <!-- 类型为其他消息 -->
						  <!-- 消息展示区域 -->
					   	  <div class="msgContent" initHover="0" fromUserId="${message.fromUserId }" msgId="${message.id }" msgDate="${message.addedTimeShow }" style="width:95%;min-height:40px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;margin:0 0 10px 0;">
								<!--  头像显示 -->
					   	  	 	<img src="<%= return_path_z%>${message.headUrl }" style="width:30px;height:30px;border-radius:30px;float:left;"/>
								<!-- 昵称和消息时间显示 -->
					   	  	 	<div style="width:200px;height:15px; border:0px solid blue; margin:5px 0 0 32px;font-size: 11px;color:gray;">${message.nickName }&nbsp;<span style="color:#1296db;">${message.dateShow}</span></div>
								<!-- 消息内容显示 -->
					   	  	 	<div style="width:95%;min-height:15px; border:0px solid blue; margin:3px 0 0 32px;font-size: 11px;">
									<!-- 	文本消息 -->
					   	  	 		<c:if test="${message.messageType == '1' }">
					   	  	 			<span id="content2${message.id }">
					   	  	 				<c:out value="${message.content }" escapeXml="false"/>
					   	  	 			</span>
					   	  	 		</c:if>
									<!-- 	附件消息 -->
					   	  	 		<c:if test="${message.messageType == '3' }">
					   	  	 			<img src="<%=path %>/chat/image/file.png" style="cursor:pointer;" title="这是一个附件"/>
					   	  	 			${message.content }&nbsp;<a href="<%=path%>/resource?method=download&path=${message.extra }&name=${message.content }" target="_blank">下载</a>
					   	  	 		</c:if>
								    <!-- 显示系统消息，撤回的消息等 -->
					   	  	 		<c:if test="${message.messageType == '4'  or message.messageType == '5'}">
					   	  	 			<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">${message.nickName }:${message.content }</div>
					   	  	 		</c:if>
					   	  	 	</div>
					   	  </div>
	   	  	 	  	 </c:otherwise>
	   	  	 	  </c:choose>
		   	  </c:forEach>
	   	  </c:if>
		 <!-- 	 单聊消息显示 -->
	   	  <c:if test="${type == 1 }">
	   	  	<c:forEach items="${messageList }" var = "message">
	   	  		<c:choose>
	   	  	 	  	 <c:when test="${message.messageType == '4'  or message.messageType == '5'}">
					 	<!-- 	   	 系统消息显示在中间 -->
	   	  	 	  	 	<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">${message.nickName }：${message.content }</div>
	   	  	 	  	 </c:when>
	   	  	 	  	 <c:otherwise>
					 	   <!-- 类型为其他消息 -->
					   	   <!-- 自己发送的消息 显示在右边 -->
				   	  		<c:if test="${curUserId == message.fromUserId}">
								<div class="msgContent" initHover="0"  fromUserId="${message.fromUserId }" msgId="${message.id }" msgDate="${message.addedTimeShow }" style="width:95%;min-height:40px;border:0px solid blue;text-align:right;overflow-y:auto;overflow-x:hidden;margin:0 0 10px 0;">
									<img src="<%= return_path_z%>${message.headUrl }" style="width:30px;height:30px;border-radius:30px;float:right;"/>
						   	  	 	<div style="width:200px;height:15px; border:0px solid blue; margin:5px 3px 0 0;font-size: 11px;color:gray;float:right;text-align:right;"><span style="color:#1296db;">${message.dateShow }</span> &nbsp;${message.nickName }</div>
						   	  	 	<div style="width:94%;min-height:15px; border:0px solid blue; margin:3px 3px 0 0;font-size: 11px;float:right;text-align:right;">
										<!-- 文本消息 -->
						   	  	 		<c:if test="${message.messageType == '1' }">
						   	  	 			<span id="content1${message.id }">
						   	  	 				<c:out value="${message.content }" escapeXml="false"/>
						   	  	 			</span>
						   	  	 		</c:if>
						   	  	 		<!-- 附件消息 -->					   	  	 		
						   	  	 		<c:if test="${message.messageType == '3' }">
						   	  	 			<a href="<%=path%>/resource?method=download&path=${message.extra }&name=${message.content }" target="_blank">下载</a>&nbsp;${message.content }
						   	  	 			<img src="<%=path %>/chat/image/file.png" style="cursor:pointer;" title="这是一个附件"/>
						   	  	 		</c:if>
									    <!-- 系统消息，用户进入提醒，消息撤回等 -->
						   	  	 		<c:if test="${message.messageType == '4'  or message.messageType == '5'}">
						   	  	 			<div style="width:95%;min-height:20px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;text-align:center;font-size:11px;color:#1296db;">${message.nickName }:${message.content }</div>
						   	  	 		</c:if>
						   	  	 	</div>
								</div>
							</c:if>
						    <!-- 别人发送的消息显示在左边  -->
							<c:if test="${curUserId != message.fromUserId}">
								<div class="msgContent" initHover="0"  fromUserId="${message.fromUserId }" msgId="${message.id }" msgDate="${message.addedTimeShow }" style="width:95%;min-height:40px;border:0px solid blue;text-align:left;overflow-y:auto;overflow-x:hidden;margin:0 0 10px 0;">
									<img src="<%= return_path_z%>${message.headUrl }" style="width:30px;height:30px;border-radius:30px;float:left;"/>
						   	  	 	<div style="width:200px;height:15px; border:0px solid blue; margin:5px 0 0 32px;font-size: 11px;color:gray;">${message.nickName } &nbsp;<span style="color:#1296db;">${message.dateShow}</span></div>
						   	  	 	<div style="width:94%;min-height:15px; border:0px solid blue; margin:3px 0 0 32px;font-size: 11px;">
						   	  	 		<c:if test="${message.messageType == '1' }">
						   	  	 			<span id="content1${message.id }">
						   	  	 				<c:out value="${message.content }" escapeXml="false"/>
						   	  	 			</span>
						   	  	 		</c:if>
						   	  	 		<c:if test="${message.messageType == '3' }">
						   	  	 			<img src="<%=path %>/chat/image/file.png" style="cursor:pointer;" title="这是一个附件"/>
						   	  	 			${message.content }&nbsp;<a href="<%=path%>/resource?method=download&path=${message.extra }&name=${message.content }" target="_blank">下载</a>
						   	  	 		</c:if>
						   	  	 	</div>
								</div>
							</c:if>
	   	  	 	  	 </c:otherwise>
	   	  	 	  </c:choose>
	   	  	</c:forEach>
	   	  </c:if>
	   </div>
	   <!--  聊天功能展示区 -->
	   <div id="chatFunArea" style="width:100%;height:18px;border:0px solid blue;margin:3px 0 3px 0;margin:10px 0 0 10px;">
			<!-- 选择表情  -->
	   		<img class="emotion" src="<%=path %>/chat/image/biaoqing.png" style="cursor:pointer;border:0px solid gray;padding:2px;" title="发送表情"/>
			<!-- 选择图片 -->
	   		<img class="image" src="<%=path %>/chat/image/image.png" style="cursor:pointer;border:0px solid gray;padding:2px;" title="图片消息"/> 
	   		<div style="width:200px;height:30px;border:1px solid gray;background-color:white;position:absolute;margin:0 0 0 25px;display:none;">
	   			<div style="width:100%;min-height:10px;">
	   				<input id="file1"  name="file1" type="file" value="" onchange="file1Upload()" style="height:30px;"/>
	   			</div>
	   		</div>
			<!-- 选择视频 -->
	   		<img class="video" src="<%=path %>/chat/image/video.png" style="cursor:pointer;border:0px solid gray;padding:2px;" title="发送视频"/> 
	   		<div style="width:200px;height:30px;border:1px solid gray;background-color:white;position:absolute;margin:0 0 0 50px;display:none;">
	   			<div style="width:100%;min-height:10px;">
	   				<input id="file3"  name="file3" type="file" value="" onchange="file3Upload()" style="height:30px;"/>
	   			</div>
	   		</div>
			<!-- 语音消息 -->
			<img id="voice" flag="0" class="voice" src="<%=path %>/chat/image/voice.png" style="cursor:pointer;border:0px solid gray;padding:2px;" title="长按我进行录音"/> 
			<div style="width:100px;height:30px;font-size:12px;line-height:30px;border:1px solid #ccc;background-color:white;position:absolute;margin:0 0 0 75px;display:none;">
				<img src="<%=path %>/chat/image/luyin.gif" style="width:30px;"/>
				<span></span>
	   		</div>
			<!-- 选择附件 -->
	   		<img class="attchment" src="<%=path %>/chat/image/attchment.png" style="cursor:pointer;border:0px solid gray;padding:2px;" title="发送附件"/> 
	   		<div style="width:200px;height:30px;border:1px solid gray;background-color:white;position:absolute;margin:0 0 0 75px;display:none;">
	   			<div style="width:100%;min-height:10px;">
	   				<input id="file2"  name="file2" type="file" value="" onchange="file2Upload()" style="height:30px;"/>
	   				<input type="hidden" id="filePath" value=""/>
	   				<input type="hidden" id="fileName" value=""/>
	   			</div>
	   		</div>
			<!-- 附件信息展示区域 -->
	   		<div id="attchmentInfoShow" style="line-height:30px;overflow:hidden;text-overflow:ellipsis;padding-left:5px;font-weight:bold;width:200px;height:30px;border:1px solid #ccc;background-color:white;position:absolute;margin:0 0 0 75px;display:none;">
	   		</div>
	   </div>
	   <!--  功能输入区域 -->
	   <div id="chatInputArea"  style="width:100%;min-height:18px;border:0px solid blue;margin:5px 0 3px 0;margin:10px 0 0 10px;">
	   		<div style="width:100%;min-height:30px;">
	   			 <div  id="textMsg" style ="width:100%;height:100px; border : 1px solid #ccc;overflow:auto;border-radius:5px;" contenteditable="true">
	   			 </div>
	   		</div>
	   </div>
	   <!--  文本消息发送按钮 -->
	   <div id="buttonArea" style="width:100%;height:18px;border:0px solid blue;text-align:right;margin:3px 0 3px 0;margin:10px 0 0 10px;">
	   		<input type="button" value="发送" id="sendBn" name="sendBn" onclick="sendText()" style="width:100px;height:30px;background-color:#64a033; border: 0px solid #6495ED; color: white;">
	   </div>
   </div>
   <!--    右侧群成员展示区域，聊天类型为群组时才显示-->
   <c:if test="${type == '2' }">
   <div style="width:150px;float:left;border:0px solid blue;margin:25px 0 0 20px; font-size:11px;">
   	 <div style="width:100%;height:20px;font-weight:bold;">
   	 	聊天成员(${groupUserList.size() })
   	 </div>
   	 <c:forEach items="${groupUserList }" var ="user">
   	 		<div style="width:150px;height:30px; display:block;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">
   	 			<img src="<%= return_path_z%>${user.headUrl }" style="width:25px;height:25px;float:left;border-radius: 30px;"/>
   	 			<div style="width:120px;height:25px;float:left; padding-left:3px;font-size:10px;">
   	 				<div style="width:100%;height:10px;font-size:10px;">
   	 					<c:if test="${user.onlineStatus == 1 }">
   	 						<img src="<%=path %>/chat/image/onlineGreen.png" title="在线"/>
   	 					</c:if>
   	 					<c:if test="${user.onlineStatus == null or user.onlineStatus == '' or user.onlineStatus == 0 }">
   	 						<img src="<%=path %>/chat/image/offline.png
   	 						" title="离线"/>
   	 					</c:if>
   	 				</div>
   	 				<div style="width:100%;height:10px;margin-top:5px;">
   	 					${user.nickName }(${user.account })
   	 				</div>
   	 			</div>
   	 		</div>
   	 </c:forEach>
   </div>
   </c:if>
   <script type="text/javascript">
   var mp3Blob;
   var recorder = null;
   var timer = null;
   $(function(){
	   //页面加载完，让聊天窗口的滚动条 滚动到底部
	   setTimeout(function(){
// 		   $('#showChatMessage').scrollTop( $('#showChatMessage')[0].scrollHeight );
		   $('#showChatMessage').animate({scrollTop: $('#showChatMessage')[0].scrollHeight + 'px'}, 1000);
	   },200);
	   /* $("#textMsg").focus(function(){
		   $(".image,.video,.attchment").next().slideUp(300);
		   $("#sendBn").attr("onclick","sendText()");
		   
	   }); */
	   //初始化 消息撤回事件
	   initMsgRecall();
	   //初始化录音插件
	   recorder = new MP3Recorder({
           debug:true,
           funOk: function () {
        	   
           },
           funCancel: function (msg) {
               recorder = null;
           }
       });
	   
   })
   //如果按下回车键 发送消息
   $("body").keydown(function(event) {
        if (event.keyCode == "13") {//keyCode=13是回车键并且是文本消息的时候触发
        	sendText();
       		//避免换行
            event.preventDefault();
        }
    });

   $("#voice").click(function (){
	   if(recorder == null){
		   alert("浏览器不支持！");
		   return;
	   }
		 var flag = $(this).attr("flag");
		 if(flag == "0"){
// 			 $("#voice").next().html("音频录制中，再次点击完成").slideDown(300);
			 //开始录音
			 if(funStart()){
			     $("#voice").next().show();
				 $("#voice").next().find("img").show();
			     var time = 1;
			     timer = setInterval(function(){
			    	 $("#voice").next().find("span").html(time+++"秒");
			     },1000);
			     $(this).attr("flag","1");
			 }
			 
		 }else if(flag == "1"){
// 			 $("#voice").next().html("等待上传并发送。。。").slideDown(300);
			 funStop();
			 $(this).attr("flag","0");
			 clearInterval(timer);
			 $("#voice").next().find("img").hide();
			 $("#voice").next().find("span").html("上传并发送中。。");
		 }
	   });
   //录音开始
   function funStart() {
	   if(recorder != null){
		   try{
	       	recorder.start();
	       	return true;
		   }catch(e){
			   alert(e);
			   return false;
		   }
	   }else{
		   alert("录音插件初始化失败");
		   return false;
	   }
	}
   //录音结束
   function funStop() {
       recorder.stop();
       recorder.getMp3Blob(function (blob) {
           mp3Blob = blob;
           var reader = new FileReader(); 
           reader.readAsDataURL(mp3Blob); 
           reader.onload = function(e){ 
        	   funUpload(this.result);
           } 
//            var url = URL.createObjectURL(mp3Blob);
//            funUpload();
       });
   }
   //录音文件上传
   function funUpload(mp3Base64) {
       var fd = new FormData();
       fd.append('file', mp3Base64);
       fd.append("name","张三");
       $.ajax({
           async: false,
           url : "<%=path%>/base64Upload",  
           type : 'post',  
           data :fd,  
           processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
           contentType : false,  //必须false才会自动加上正确的Content-Type
           dataType:"text",
           success : function(result) {  
        	   //直接发送语音消息
               var text = '<audio src="'+result+'"  controls=""></audio>' ;
               var json = {};
               json.message = text;
               json.extra = "";
               json.messageType = "1";
               json.type = "${type}";
               json.fromUserId = "${fromUserId}";
               json.toUserId = "${toUserId}";
               json.groupId = "${groupId}";
               socket.send(JSON.stringify(json));
               $("#voice").next().find("span").html("发送成功");
               $("#voice").next().slideDown(300)
               setTimeout(function (){
            	   $("#voice").next().slideUp(300);
                   $("#voice").next().find("span").html("");
               },2000);
           },  
           error : function(result) {  
             
           }  
       }); 
   }
   //当点击图片时触发 文件选择器
   $(".image,.video,.attchment").click(function(){
	   $(this).next().find("input[type='file']").click();
   });
  
  //消息撤回
  function initMsgRecall(){
	  $(".msgContent[initHover='0']").hover(function(){
		   //消息发送的时间
		   var msgDate = $(this).attr("msgDate");
		   //消息id 
		   var msgId = $(this).attr("msgId");
		   //消息发送者
		   var fromUserId = $(this).attr("fromUserId");
		   //取到当前时间
		   var now = new Date();
		   var t = (new Date()).format("yyyy-MM-dd hh:mm:ss")
		   //当前时间与消息发送时间的间隔
		   var diff = GetDateDiff(msgDate,t, "minute");
		   //消息发送时间 小于三分钟,并且是自己发送的消息可以撤销
		   if(fromUserId == "${fromUserId}" && diff <= 3){
		   	  $(this).find("div:last").append('<span class="recall" style="margin-left:10px;"><a href="javascript:void();" onclick="recallMsg('+msgId+')">撤回</a></span>');
		   }
	   },function(){
		   //移除撤回按钮
		   $(this).find(".recall").remove();
	   });
	  //标记为已经初始化了hover事件
	  $(".msgContent").attr("initHover","1");
   }
   //消息撤回
   function recallMsg(msgId){
	   var json = {};
        json.message = "撤回了一条消息";
        json.extra = "";
        json.messageType = "5";//1.文本消息，3.附件消息，4.心跳消息，5.消息撤回
        json.type = "${type}";//聊天类型，1.单聊，2.群聊
        json.fromUserId = "${fromUserId}";
        json.toUserId = "${toUserId}";
        json.groupId = "${groupId}";
        json.msgId = msgId;
        socket.send(JSON.stringify(json));
        
   }
   //图片文件上传
   function file1Upload(){
	$("#sendBn").val("文件上传中...");
	$("#sendBn").attr("disabled",true);
   	$.ajaxFileUpload({
           url: '<%=path%>/resource?method=upload&fileType=image', 
           type: 'get',
           async: 'false',
           data : {
           },
           secureuri: false, //一般设置为false
           fileElementId: 'file1', // 上传文件的id、name属性名
           dataType: 'JSON', //返回值类;型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
           success: function(data, status){  
           	var obj = $(data).removeAttr("style").prop("outerHTML");
           	data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
           	var json = JSON.parse(data);
           	if(json.result == "0"){
           		alert(json.message);
           		$("#sendBn").val("发送");
            	$("#sendBn").attr("disabled",false);
           		return false;
           	}
           	//显示上传的图片
           	//设置向后台提交的隐藏域的值
           	/* $("#file1Hidden").val(json.fileUrl);
           	$("#file1Hidden").next().val(json.fileOldName);
           	$("#file1HiddenDiv").html(json.fileOldName); */
           	//显示上传的图片到指定区域
//          $("#textMsg").append();
           	//在光标出添加
           	addImage($("#textMsg"),'<img src="'+json.url+'" style="width:100px;">');
           	$(".image").next().slideUp(300);
           	$("#file1").val("");
           	/* $("#imageShow").html('<img src="'+json.url+'" style="width:100px;height:100px;">').show();
           	//保存图片路径到文本域中
           	$("#imageUrl").val(json.savePath); */
       		$("#sendBn").val("发送");
        	$("#sendBn").attr("disabled",false);
           },	
           error: function(data, status, e){ 
           		alert("网络错误，请刷新后重新尝试！");
           		$("#sendBn").val("发送");
            	$("#sendBn").attr("disabled",false);
           }
       }); 
   }
   //附件上传
   function file2Upload(){
    //显示等待信息
    $("#attchmentInfoShow").html('附件上传中，请稍后。。。');
    $("#attchmentInfoShow").slideDown(500);
   	$.ajaxFileUpload({
           url: '<%=path%>/resource?method=upload&fileType=attchment', 
           type: 'get',
           async: 'false',
           data : {
           },
           secureuri: false, //一般设置为false
           fileElementId: 'file2', // 上传文件的id、name属性名
           dataType: 'JSON', //返回值类;型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
           success: function(data, status){  
           	var obj = $(data).removeAttr("style").prop("outerHTML");
           	data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
           	var json = JSON.parse(data);
           	if(json.result == "0"){
           		alert(json.message);
           		$("#sendBn").val("发送");
            	$("#sendBn").attr("disabled",false);
           		return false;
           	}
           	//显示上传的图片
           	//设置向后台提交的隐藏域的值
           	/* $("#file1Hidden").val(json.fileUrl);
           	$("#file1Hidden").next().val(json.fileOldName);
           	$("#file1HiddenDiv").html(json.fileOldName); */
           	//保存附件的路径
           	$("#filePath").val(json.savePath);
           	//保存文件的原名称
           	$("#fileName").val(json.fileOldName);
           	
           	//发送消息
           	sendAttchment(json.fileSize);
           	$("#attchmentInfoShow").html('附件发送成功');
           	
           	setTimeout(function(){
                $("#attchmentInfoShow").slideUp(500);
           	},2000);
           },	
           error: function(data, status, e){ 
           		alert("网络错误，请刷新后重新尝试！");
           		$("#sendBn").val("发送");
            	$("#sendBn").attr("disabled",false);
           }
       }); 
   }
   //视频上传
   function file3Upload(){
	$("#sendBn").val("文件上传中...");
	$("#sendBn").attr("disabled",true);
   	$.ajaxFileUpload({
           url: '<%=path%>/resource?method=upload&fileType=video', 
           type: 'get',
           async: 'false',
           data : {
           },
           secureuri: false, //一般设置为false
           fileElementId: 'file3', // 上传文件的id、name属性名
           dataType: 'JSON', //返回值类;型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
           success: function(data, status){  
           	var obj = $(data).removeAttr("style").prop("outerHTML");
           	data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
           	var json = JSON.parse(data);
           	if(json.result == "0"){
           		alert(json.message);
           		$("#sendBn").val("发送");
            	$("#sendBn").attr("disabled",false);
           		return false;
           	}
           	//显示上传的图片
           	//设置向后台提交的隐藏域的值
           	/* $("#file1Hidden").val(json.fileUrl);
           	$("#file1Hidden").next().val(json.fileOldName);
           	$("#file1HiddenDiv").html(json.fileOldName); */
           	//保存附件的路径
//            	$("#videoPath").val(json.savePath);
           	//保存文件的原名称
//            	$("#videoName").val(json.fileOldName);
           	//在光标出添加
           	
           	
           	addImage($("#textMsg"),'<video src="'+json.url+'" controls="controls" style="width:150px;"></video>');
           	$(".video").next().slideUp(300);
           	$("#file3").val("");
           	
           	//视频显示
//          $("#videoShow").html('<video src="'+json.url+'" controls="controls" style="width:150px;height:150px;"></video>').show();
           	
           	//恢复按钮点击状态
           	$("#sendBn").val("发送");
        	$("#sendBn").attr("disabled",false);
           },	
           error: function(data, status, e){ 
           		alert("网络错误，请刷新后重新尝试！");
           		$("#sendBn").val("发送");
            	$("#sendBn").attr("disabled",false);
           }
       }); 
   }
   
   //日期个格式化
   Date.prototype.format = function (fmt) { //author: meizz   
	    "use strict";  
	    var o = {  
	        "M+": this.getMonth() + 1, //月份   
	        "d+": this.getDate(), //日   
	        "h+": this.getHours(), //小时   
	        "m+": this.getMinutes(), //分   
	        "s+": this.getSeconds(), //秒   
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度   
	        "S": this.getMilliseconds() //毫秒   
	    };  
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
	    for (var k in o)  
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
	    return fmt;  
	};  
   /* 
   * 获得时间差,时间格式为 年-月-日 小时:分钟:秒 或者 年/月/日 小时：分钟：秒 
   * 其中，年月日为全格式，例如 ： 2010-10-12 01:00:00 
   * 返回精度为：秒，分，小时，天
   */
   //获取连个时间的间隔
   function GetDateDiff(startTime, endTime, diffType) {
       //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式 
       startTime = startTime.replace(/\-/g, "/");
       endTime = endTime.replace(/\-/g, "/");
       //将计算间隔类性字符转换为小写
       diffType = diffType.toLowerCase();
       var sTime = new Date(startTime);      //开始时间
       var eTime = new Date(endTime);  //结束时间
       //作为除数的数字
       var divNum = 1;
       switch (diffType) {
           case "second":
               divNum = 1000;
               break;
           case "minute":
               divNum = 1000 * 60;
               break;
           case "hour":
               divNum = 1000 * 3600;
               break;
           case "day":
               divNum = 1000 * 3600 * 24;
               break;
           default:
               break;
       }
       return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
   }
   </script>

</body>
</html>