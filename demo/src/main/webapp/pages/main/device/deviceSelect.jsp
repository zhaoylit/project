<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div style="width:600px;">
         <input type="checkbox" id="checkAll"  style="margin-bottom:10px;"/><label>请选择要发布的设备: 全选</label>
        
	        <c:if test="${data.size() == 0 }">
				<div style="margin-top:20px;color:red;">
					改厅暂无设备信息
				</div>
			</c:if>
			<div id="checkItem">
			<c:forEach items="${data}" var="device" varStatus="index">
		        <div class="device device_set">
					<input onclick="checkDevice(this);" type="checkbox" name="deviceId" value="${device.uuid}" class="check_status">
					<label > ${device.deviceIp}</label>
					<span>${device.deviceName}</span>
					<span style="color:red">${device.connectionStatusName}</span>
				</div>
			</c:forEach>
		
		</div>
</div>