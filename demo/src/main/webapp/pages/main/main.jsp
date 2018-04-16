<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="">
    .row{
        width:100%;
        height:100%;
        background:url("<%=path %>/images/index/background.jpg") no-repeat;
        background-size:100% 100%;
        width:100%;
    }
</style>
<div class="row">

<div class="descripation" style="position:absolute;">
     <img src="<%=path %>/images/index/icon.png" style="width:15%;">
     <h1>请在导航栏选择所需的功能</h1>
     <p>Please select the desired feature in the navigation bar</p>
</div>

</div>