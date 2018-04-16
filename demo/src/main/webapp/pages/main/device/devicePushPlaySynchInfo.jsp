<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forEach items="${data }" var="item">
	<div style="float:left;width:500px;margin-left:10px;">
		<span style="width:100px;display:block;float:left;">${item.deviceIp }:</span>
		<span style="width:100px;display:block;float:left;">
			<c:if test="${item.receiveStatus == '0' }">
			<font color="red">指令未接收</font>
			</c:if>
			<c:if test="${item.receiveStatus == '1' }">
			<font color="green">指令已接收</font>
			</c:if>
		</span>
	</div>
</c:forEach>