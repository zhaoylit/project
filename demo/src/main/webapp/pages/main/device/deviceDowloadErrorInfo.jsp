<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forEach items="${data }" var="item">
	<div style="float:left;width:100px;margin-left:10px;">
		<img src="${item.filePath }" style="width:100px;height:150px;"/>
		<div style="font-size:11px;width:100px;" title="${item.fileName}">${item.fileName}</div>
		<div style="font-size:11px;width:100px;" title="文件的大小为：${item.fileSize }kb">
		大小：<fmt:formatNumber type="number" value="${item.fileSize }" pattern="0.00" maxFractionDigits="2"/>kb
		</div>
		<div style="font-size:11px;width:100px;" title="下载失败的原因：${item.reason}">${item.reason}</div>
	</div>
</c:forEach>