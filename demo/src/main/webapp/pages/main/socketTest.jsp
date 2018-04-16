    <%@ page language="java" contentType="text/html; charset=UTF-8"  
        pageEncoding="UTF-8"%>  
<!DOCTYPE html>  
<html>  
<head>  
    <title>websocket测试页面</title>  
    <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>  
    <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
    <script type="text/javascript">  
    var ws = new WebSocket("ws://192.168.3.4:8080/zkkjweb/websocket.do");
	    ws.onopen = function () {  
	    	
	    };  
	    ws.onmessage = function (event) {  
	        $('#display').html(event.data);
	    };  
	    ws.onclose = function (event) {  
	        setConnected(false);  
	        log(event);  
	    };  
    </script>  
</head>  
<body>  
	<div id="display" style="width:100px;height:200px;border:1px solid blue;">
	
	</div>
</body>  
</html> 