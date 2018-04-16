<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<style type="text/css">
#userTable{display:block;width:100%;padding-top:2%;padding-bottom:5%;}
#userTable tr {
	height: 50px;
	line-height:50px;
	display:block;
	width:100%;
	margin-left:20%;
	margin-top:3%;
}
#userTable tr td{}
</style>
<form  style="height:90%;width:80%;margin:0px auto;"  id="updatapwd" method="post" enctype="multipart/form-data"
	action="#">
	<table id="userTable">
		<tr>
			<td style="width:200px;text-align:right;"><label style="size:30px;text-align:right;">请输入原密码<span style="color: red;">*</span>：</label></td>
			<td><input style="width:300px;height:30px;" id="yuanpwd" name="yuanpwd"/></td>
		</tr>
		<tr>
			
			<td style="width:200px;text-align:right;"><label>请输入新密码<span style="color: red;">*</span>：</label></td>
			<td><input style="width:300px;height:30px;" id="password" name="password"/></td>
		</tr>
		<tr>
			
			<td style="width:200px;text-align:right;"><label>再次输入新密码<span style="color: red;">*</span>：</label></td>
			<td><input style="width:300px;height:30px;" id="passwordtwo" name="passwordtwo"/></td>
			<td>
			  <div style="padding: 2px 0; margin-top: 2px; text-align: center; color: red;"id="showMsg"></div>
			</td>
		</tr>
		<tr>
			
			<td style="width:250px;text-align:right;">
			<!-- <div region="south" border="false"
				style="text-align: right; padding: 5px 0;">
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				
				&nbsp;&nbsp;&nbsp;
				
			</div> -->
			
			<a style="margin-left:60%;border-radius:10px;background:#0864aa;display:block;width:100px;font-size:1rem;color:#fff;line-height:30px;text-align:center;" class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="login()">确认修改</a>
			</td>
			<td style="width:50%; text-align:center;"><a style="margin-left:20px;border-radius:10px;background:#0864aa;display:inline-block;width:100px;font-size:1rem;color:#fff;line-height:30px;text-align:center;" class="easyui-linkbutton" iconCls="icon-reload"
					href="javascript:void(0)" onclick="cleardata()">重&nbsp;置</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			
		</tr>
	</table>
</form>
<script type="text/javascript">
$('#yuanpwd').textbox({
	//required: true,
	iconAlign : 'right',
	prompt : '请输入原始密码'
});
$('#password').textbox({    
	//required: true,
    iconAlign:'right',
    prompt : '请输入新密码'
});
$('#passwordtwo').textbox({    
    //required: true,    
	iconAlign:'right',
    prompt : '请再次输入新密码'
});  
function login() {
	var jiu= $("input[name='yuanpwd']").val();
	var xin2= $("input[name='passwordtwo']").val();
	var xin1=$("input[name='password']").val();
	if (xin1!=null&xin1!=xin2 ) {
		$("#showMsg").html("请俩次输入的新密码相同！");
		$("input[name='login']").focus();
	} else {
		//ajax异步提交  
		$.ajax({
			data : $("#updatapwd").serializeArray(),
			type : "GET",
			dataType : 'json',
			url : "user/updatePWD.do",	
			error : function(data) {
				$.messager.alert('警告',"网络出错了！！" ,'warning');
			},
			success : function(data) {
				if(data.result=="1"){
					$.messager.alert('警告', '修改成功,返回登陆页面！','warning');
					window.location.href="<%=basePath%>";
				}
				if(data.result=="2"){
					$.messager.alert('警告', '原密码输入错误','warning');
				}
				}
		});
	}
}
function cleardata() {
	$('#updatapwd').form('clear');
}

</script>
