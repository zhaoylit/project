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
<img src="<%=path %>/images/index/background.jpg" style="width:100%;height:680px;font-family:"微软雅黑" ">
<div class="descripation" style="position:absolute;top:30%;left:25%;">
     
     <h1>攻城狮正在维修页面，请您耐心等候。。。</h1>
</div>

</div>