<%@ page language="java" import="java.util.*,com.zj.web.common.utils.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=return_path %>css/icon.css">
