<%@ page language="java" contentType="text/html; charset=UTF-8"  import="com.zj.web.common.utils.*"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
 <iframe style = "width: 99%;height:99%; border : 0px solid blue;overflow:hidden;" src="<%=path %>/goodsResource/selectListJson.do?id=${params.id}">
 </iframe>
</body>
</html>