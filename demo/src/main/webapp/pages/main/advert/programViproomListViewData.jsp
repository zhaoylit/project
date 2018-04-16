<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style tyle="text/css">
tr{height:25px;}
</style>
<table style="width:700px;text-align:center;margin-top:20px;margin-left:20px;" class="VIproomListTable">
	<tr>
		<td style="width:150px;"><b>厅服务器</b></td>
		<td style="width:550px;"><b>已推送的设备</b></td>
	</tr>
	 <c:forEach items="${list}" var="bean"  varStatus="idx">
	 <tr>
		<td>${bean.viproom }</td>
		<td>
			<table style="width:100%;">
				<tr>
					<td>设备名称</td>
					<td>设备ip</td>
					<td>推送时间</td>
				</tr>
				 <c:forEach items="${bean.deviceList }" var="device">
				 <tr>
					<td style="width:150px;">${device.devName}</td>
					<td style="width:150px;">${device.devIp}</td>
					<td style="width:400px;">
						<table style="width:100%;">
							 <c:forEach items="${device.publishTime }" var="time">
							 <tr>
								<td style="width:100%;">${time.publishTime}</td>
							 </tr>
							 </c:forEach>
						</table>
					</td>
				 </tr>
				</c:forEach>
			</table>
		</td>	
	 </tr>
	 </c:forEach>
</table>