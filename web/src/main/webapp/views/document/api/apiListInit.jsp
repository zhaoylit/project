<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/link.jsp"%>
<%@include file="/script.jsp"%>	
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body{
	padding:0px;
	margin:0px;
}
</style>
<script type="text/javascript">
$(function(){
	$('#tt').tree({    
	    url:'<%=path%>/api/getApiCategoryTreeJson.do'   
	});  
}); 
	
</script>
</head>
<body>
<div id="roleAuth" class="easyui-layout" style="width:100%;height:99.9999%;">   
    <div data-options="region:'west',title:'接口分类',split:true,iconCls:'icon-roleList'" style="width:20%;">
    	<ul id="tt"></ul>  
    </div>   
    <div class="easyui-layout" data-options="region:'center',title:'接口详细说明',iconCls:'icon-menu'" style="width:60%;">
    	<div data-options="region:'north'" style="height:40px;line-height:35px;padding-left:10px;">
	    </div>   
	    <div data-options="region:'center'" style="height:100%;">
	    	<ul id="funTree"></ul> 
	    </div>   
    </div>   
</div>  
</body>
</html>