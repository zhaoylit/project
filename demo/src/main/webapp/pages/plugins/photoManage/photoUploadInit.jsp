<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serviceId = request.getParameter("serviceId");
String resourceServiceType = request.getParameter("resourceServiceType");
System.out.print("------------------"+resourceServiceType);
String resourceType = request.getParameter("resourceType");
%>
<iframe src="<%=path %>/pages/plugins/photoManage/photoUpload.jsp?serviceId=<%=serviceId %>&resourceType=<%=resourceType %>&resourceServiceType=<%=resourceServiceType %>" style="width:99%;height:100%;border:0px;overflow:hidden;"></iframe>
