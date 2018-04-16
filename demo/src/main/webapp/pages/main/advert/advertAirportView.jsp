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
<table style="width:300px;">
	<tr>
		<td><b>机场代码</b></td>
		<td><b>机场名称</b></td>
		<td><b>频次</b></td>
	</tr>
	 <c:forEach items="${list }" var="bean"  varStatus="idx">
	 <tr>
		<td>${bean.airport }</td>
		<td>${bean.airportName }</td>
		<td>${bean.frequency }</td>
	 </tr>
	 </c:forEach>
</table>