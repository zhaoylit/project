<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
/* #myTable{font-size:12px;border:1px solid blue;} */
#myTable{margin-left:20px;}
#myTable tr{height:35px;display:block;margin-top:10px;}
#myTable tr td{padding:5px;}
.td_class_1{width:150px;border:1px solid #aaa;border-right:0px;background-color:#F4F4F4;}
.td_class_2{width:250px;border:1px solid #aaa;}
</style>
<script type="text/javascript">
$(function(){
	
})
</script>
<form id="addOrEditForm" method="post" enctype="multipart/form-data" action="<%=path %>/roleAuth/addOrEditRole.do">
	 <input type="hidden" class="form-control" name="roleId" value="${resultInfo.id}">
     <table id="myTable" cellpadding="0" cellspacing="0">
     	<tr>
          	<td class="td_class_1"><label>角色名称：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="roleName" required="true" value="${resultInfo.roleName}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>角色状态：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="roleStatus" required="true" value="${resultInfo.roleStatus}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>备注：</label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" data-options="multiline:true" style="width:250px;height:100px;" name="roleDes" value="${resultInfo.roleDes}" style="height:30px;"/>
			</td>
	    </tr>
	</table>
</form>
