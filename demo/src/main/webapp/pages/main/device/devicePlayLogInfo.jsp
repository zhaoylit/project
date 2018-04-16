<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<table style="width:500px;">
   <tr>
   		<td style="width:50px;">资源</td>
   		<td style="width:100px;">播放开始时间</td>
   		<td style="width:100px;">播放结束时间</td>
   		<td style="width:50px;">播放时长</td>
   </tr>
   <c:forEach items="${data }" var="item">
   <tr>
   		<td><img src="${item.filePath }" style="width:50px;height:80px;"/></td>
   		<td>${item.beginTime }</td>
   		<td>${item.endTime }</td>
   		<td>${item.diff }</td>
   </tr>
   </c:forEach>
</table>