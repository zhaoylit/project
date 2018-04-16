<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<body>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style type="text/css">
#acticityTable tr{height:30px;}
input{width:150px;}
</style>
<form id="addOrEditForm" method="post" action="<%=path %>/device/addOrEditDeviceTreeInfo.do" style="margin-top:0px;">
	 <input type="hidden" class="form-control" id="operator" name="operator" value="${params.operator}">
	 <input type="hidden" class="form-control" id="type" name="type" value="${params.type}">
     <table id="acticityTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	<c:if test="${params.operator == 'edit'}">
     		<c:if test="${params.type == '1' }">
		    <tr>
	          	<td style="width:100px;"><label>航空公司代码：<span style="color:red;">*</span></label></td>
				<td>
					 <input type="hidden" class="form-control" id="airlineId" name="airlineId" value="${data.id}">
					<input id="airlineCode" name="airlineCode" class="easyui-textbox" value="${data.airlineCode}"/>
				</td>
		    </tr>
		    <tr>
	          	<td style="width:100px;"><label>航空公司名称：<span style="color:red;">*</span></label></td>
				<td>
					<input id="airlineName" name="airlineName" class="easyui-textbox" value="${data.airlineName}"/>
				</td>
		    </tr>
		    </c:if>
		    <c:if test="${params.type == '2' }">
		    <tr>
	          	<td style="width:100px;"><label>机场代码：<span style="color:red;">*</span></label></td>
				<td>
					 <input type="hidden" class="form-control" id="airportId" name="airportId" value="${data.id}">
					<input id="airportCode" name="airportCode" class="easyui-textbox" value="${data.airportCode}"/>
				</td>
		    </tr>
		    <tr>
	          	<td style="width:100px;"><label>机场名称：<span style="color:red;">*</span></label></td>
				<td>
					<input id="airportName" name="airportName" class="easyui-textbox" value="${data.airportName}"/>
				</td>
		    </tr>
		    </c:if>
		    <c:if test="${params.type == '3' }">
		    <tr>
	          	<td style="width:100px;"><label>vip厅名称：<span style="color:red;">*</span></label></td>
				<td>
					<input type="hidden" class="form-control" id="viproomId" name="viproomId" value="${data.id}">
					<input id="viproomName" name="viproomName" class="easyui-textbox" value="${data.viproomName}"/>
				</td>
		    </tr>
		    </c:if>
     	</c:if>
     	<c:if test="${params.operator == 'add'}">
     		<c:if test="${params.type == '0' }">
		    <tr>
	          	<td style="width:100px;"><label>航空公司代码：<span style="color:red;">*</span></label></td>
				<td>
					<input id="airlineCode" name="airlineCode" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    <tr>
	          	<td style="width:100px;"><label>航空公司名称：<span style="color:red;">*</span></label></td>
				<td>
					<input id="airlineName" name="airlineName" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    </c:if>
     		<c:if test="${params.type == '1' }">
		    <tr>
	          	<td style="width:100px;"><label>机场代码：<span style="color:red;">*</span></label></td>
				<td>
					<input id="airportCode" name="airportCode" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    <tr>
	          	<td style="width:100px;"><label>机场名称：<span style="color:red;">*</span></label></td>
				<td>
					<input type="hidden" id="airlineId" name="airlineId" value="${params.id}"/>
					<input id="airportName" name="airportName" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    </c:if>
		    <c:if test="${params.type == '2' }">
		    <tr>
	          	<td style="width:100px;"><label>vip厅名称：<span style="color:red;">*</span></label></td>
				<td>
					<input type="hidden" id="airportId" name="airportId" value="${params.id}"/>
					<input id="viproomName" name="viproomName" class="easyui-textbox" value="${data.viproomName}"/>
				</td>
		    </tr>
		    </c:if>
		    <c:if test="${params.type == '3' }">
		    <tr>
		    	<td style="width:100px;"><label>设备类型：<span style="color:red;">*</span></label></td>
				<td>
					<select id="deviceType" name="deviceType" class="easyui-combobox" value="">
						<option value="1">厅服务器</option>
						<option value="2">工作站</option>
					</select>
				</td>
		    </tr>
		    <tr>
	          	<td style="width:100px;"><label>设备名称：<span style="color:red;">*</span></label></td>
				<td>
					<input type="hidden" id="airlineId" name="airlineId" value="${params.airlineId}"/>
					<input type="hidden" id="airportId" name="airportId" value="${params.airportId}"/>
					<input type="hidden" id="viproomId" name="viproomId" value="${params.id}"/>
					<input id="deviceName" name="deviceName" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    <tr>
		    	<td style="width:100px;"><label>设备编号：<span style="color:red;">*</span></label></td>
				<td>
					<input id="deviceNo" name="deviceNo" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    <tr>
		    	<td style="width:100px;"><label>uuid：<span style="color:red;">*</span></label></td>
				<td>
					<input id="uuid" name="uuid" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    <tr>
	          	<td style="width:100px;"><label>设备ip：<span style="color:red;">*</span></label></td>
				<td>
					<input id="deviceIp" name="deviceIp" class="easyui-textbox" value=""/>
				</td>
		    </tr>
		    </c:if>
     	</c:if>
	</table>
</form>
<script type="text/javascript">
$(function(){
	$("#airlineName").textbox({});
})
function validateForm(){
	return true;
}
 </script>
</body>
