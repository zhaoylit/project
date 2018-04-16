<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<style type="text/css">
#userTable tr {
	height: 40px;
}

input {
	width: 230px;
}
</style>
<form id="updatapwd" method="post" enctype="multipart/form-data"
	action="<%=path%>/user/updatepwd.do">
	<input type="hidden" class="form-control" id="id" name="id"
		value="${resultInfo}">
	<table id="userTable"
		style="width: 100%; height: 50%; margin-top: 40px; padding-left: 30px;">
		<tr>
			<td style="width: 100px;"><label>请输入新密码：<span
					style="color: red;">*</span></label></td>
			<td><input id="pwd" name="password" class="easyui-validatebox"
				data-options="required:true"
				validType="length[6,12]" invalidMessage="请输入6~12个字符" /></td>
		</tr>
		<tr>
			<td style="width: 100px;"><label>再次输入密码：<span
					style="color: red;">*</span></label></td>
			<td><input id="rpwd" name="passwordtwo"
				class="easyui-validatebox" required="required"
				validType="equals['#pwd']" /></td>
		</tr>
	</table>
</form>
<script type="text/javascript">
//extend the 'equals' rule    
$.extend($.fn.validatebox.defaults.rules, {    
    equals: {    
        validator: function(value,param){    
            return value == $(param[0]).val();    
        },    
        message: '俩次输入的密码请相同.'   
    }    
});  
</script>
