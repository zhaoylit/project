<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="">
    .row{
        width:100%;
        height:100%;
    }
</style>
<div class="row">
<%-- <img src="<%=path %>/images/index/background.jpg" style="width:100%;height:680px;"> --%>
<div class="descripation" style="height:70%;width:100%; text-align:center;padding-top:10%;" >
     <P><h4>操作失败，您没有该权限！如有疑问请联系管理员。</h4></p>
</div>

</div>