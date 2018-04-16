<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
      
</style>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/css/page/main.css">
<script type="text/javascript" src="<%=path %>/javascript/js/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path %>/javascript/js/pdf/build/pdf.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path %>/javascript/js/pdf/build/pdf.worker.js" charset="utf-8"></script>

<div class="guide" style="height:100%;z-index:-10000;">
        <iframe id="showpdf" src="" height="100%" width="100%" style="z-index:-10000"></iframe>
</div>

<script type="text/javascript">
		
var  src="<%=path %>/javascript/js/pdf/web/viewer.html?name=airport.pdf";
$("#showpdf").attr("src",src); 	
</script>
