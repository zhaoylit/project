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
			var socket =  io.connect('http://192.168.3.4:9092');
			//监听名为pushpoint的事件，这与服务端推送的那个事件名称必须一致
			socket.on("msg", function(data){
				$('#display').html(data);
			});
		});
		
	</script>
</head>

<body>
	
	<div id="display" style="height:50px;background-color:grey;">
	
	</div>

</body>

</html>