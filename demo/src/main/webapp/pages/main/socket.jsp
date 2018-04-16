<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>netty-socketio测试</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<script src="https://cdn.socket.io/socket.io-1.0.6.js"></script>
	<script src="http://code.jquery.com/jquery-1.7.2.js"></script>
	<script>
		$(function(){
			var ws;
			ws = new WebSocket("ws://192.168.3.4:8088/zkkjweb2-0/ws?ip=192.168.3.10&deviceId=1122334455&connectionType=2&synchId=");
			ws.onopen = function () {
// 				var str = '{"method":"feedBack_info","message":{"type":"RECEIVE_PROGRAM","uuid":"1122334455","synchId":"8","size":"2","fileName":"1490427163446.jpg","err_reason":"网络原因","version":"12"}}';
// 				ws.send(str);
				ws.send('{"method":"feedBack_info","message":{"type":"AD_AYNCH"}}');
				/* setInterval(function(){
					ws.send('{"method":"feedBack_info","message":{"type":"REBOOT"}}');
				},5000); */
			};  
			ws.onmessage = function (event) { 
				var json = eval("(" + event.data + ")");//解析json字符串
			};  
			ws.onclose = function (event) {  
				//
			};
		});
		
	</script>
</head>
<body>
	<div id="display" style="height:50px;background-color:grey;">
	</div>
</body>
</html>

