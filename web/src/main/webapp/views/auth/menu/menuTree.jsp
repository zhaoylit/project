<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/link.jsp"%>
<%@include file="/script.jsp"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
    $('#funTreeTable').treegrid({
        data:'get_data.php',
        idField:'id',
        treeField:'name',
        columns:[[
    		{title:'Task Name',field:'name',width:180},
    		{field:'persons',title:'Persons',width:60,align:'right'},
    		{field:'begin',title:'Begin Date',width:80},
    		{field:'end',title:'End Date',width:80}
        ]]
    });
})
</script>
</head>
<body class="easyui-layout">
	<div region=west border="true" split="true" style="overflow: hidden; height: 80px;width:20%;">
		${funTreeHtml }
	</div>
	<div region="center" border="true" split="true" style="overflow: hidden; height: 40px;width:80%;">
	
	</div>
<table id="funTreeTable" style="width:600px;height:400px"></table>
</body>
</html>