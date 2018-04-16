<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath() ;
	String patha = request.getContextPath() + "/javascript";
    String ctx = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String email=(String)request.getAttribute("email");
	String userName=(String)request.getAttribute("userName");
	String passWord=(String)request.getAttribute("passWord");
	String headUrl=(String)request.getAttribute("headUrl");
	String randomNumberString=(String)request.getAttribute("randomNumberString");
%>
<title>邮箱确认</title>
<link rel="stylesheet" href="<%=path %>/chat/plugin/bootstrap/bootstrap.min.css">
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/chat/plugin/bootstrap/jquery.cookie.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=path %>/chat/plugin/bootstrap/bootstrap.min.js"></script>
<style type=""text/css>
		#topDiv{
			margin-top:15%;
		}
</style>
	
<script type="text/javascript">
	function enrooll(){
		debugger;
		var secretKey=$("#secretKey").val();
// 		alert(secretKey);
		if(secretKey== '<%=randomNumberString%>'){
				$.ajax({
					data : {
							email:'<%=email%>',
							userName:'<%=userName%>',
							passWord:'<%=passWord%>',
							headUrl:'<%=headUrl%>'
						},
						type : "POST",
						dataType : 'json',
						url : "<%=path%>/enrooll",
						success : function(data) {
							if(data.result=="1"){
								window.location.href="<%=path%>/chat/loading.jsp"
							}else{
								alert("注册失败，请检查网络！")
							}
						}
				});
			}else{
				alert("密令错误，请重新输入！")
			}
	}
</script>	
<body style="background-color:#808080">
	
          <!-- 注册窗口 -->
        <div id="topDiv" class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-title">
                    <h3 class="text-center">邮箱确认</h2>
                </div>
                <div class="modal-body" >
                    <div class="form-group" action="">
                    		<div class="form-group">
                                <label for="">密令已经发送至<%=email %>,填写至下方输入框，确认完成注册！</label>
                                <input id="secretKey"  name="secretKey" class="form-control" type="email" placeholder="例如:123456">
                            </div>
                            
                            <div class="text-right">
<!--                                 <button class="btn btn-primary" type="submit">提交</button> -->
                                <button class="btn btn-primary"  onclick="enrooll()">完成注册</button>
<!--                                 <button class="btn btn-danger" data-dismiss="modal">取消</button> -->
                            </div>
                            <a href="<%=path %>/chat/login.jsp" data-toggle="modal" data-dismiss="modal" >已有账号？点我登录</a>
                    </div>
                </div>
            </div>
    </div>
    
    </body>