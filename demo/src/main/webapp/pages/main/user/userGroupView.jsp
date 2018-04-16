<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style tyle="text/css">
tr{height:25px;}
td{text-align: center;}
</style>
<table style="width:100px;">
	<tr>
		
		<td><b>用户组</b></td>
	</tr>
	 <c:forEach items="${list }" var="bean"  varStatus="idx">
	 <tr>
		<td>${bean.usergroupsname }</td>
		
	 </tr>
	 </c:forEach>
</table>