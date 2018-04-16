<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form id="addOrEditForm" method="post" action="<%=path %>/device/addOrEditDevice.do">
	 <input type="hidden" class="form-control" id="id" name="rowKey" value="${resultInfo.rowKey}">
     <table id="acticityTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	<tr>
          	<td style="width:100px;"><label>设备编号：<span style="color:red;">*</span></label></td>
			<td>
				<input name="deviceId"  class="easyui-textbox"  value="${resultInfo.deviceId}"/>
			</td>
          	<td style="width:100px;"><label>设备类型：<span style="color:red;">*</span></label></td>
			<td>
			    <input name="devType" id="devType11" class="easyui-combobox" value="${resultInfo.devType}"/>
				<input name="devTypeName" id="devTypeName" type="hidden" value="${resultInfo.devTypeName }"/>
			</td>
	    </tr>
	</table>
</form>
</body>
</html>