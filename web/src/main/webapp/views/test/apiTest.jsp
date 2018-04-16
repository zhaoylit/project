<%@ page language="java" import="java.util.*,com.zj.web.common.utils.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/link.jsp"%>
<%@include file="/script.jsp"%>	
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	String return_path_api = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_api");
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div style="min-width:300px;height:400px;border:1px solid blue;padding:10px;">
	<div style="width:400px;">
		<table style="width:400px;float:left;">
			<tr>
				<td style="width:100px;">请求地址：</td>
				<td style="width:300px;">
					<input class="easyui-textbox" id="reqUrl" style="width:300px;" value="<%=return_path_api %>index/getCarouselFigure.do"/>
				</td>
			</tr>
			<tr>
				<td style="width:50px;">请求参数：</td>
				<td style="width:300px;">
					<textarea id="params" style="resize:none;font-size:11px;width:300px;height:350px;border: 1px solid #95b8e7;border-radius: 5px;"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div style="width:80px;float:left;text-align:center;padding-top:180px;">
		<input type="button" id="reqClick" style="cursor:pointer;width:50px;height:30px;border: 1px solid #95b8e7;border-radius: 5px;background-color:green;color:white;font-weight:bold;" value="请求>"/>
	</div>
	<div style="width:400px;float:left;">
		<table style="width:100%;">
			<tr>
				<td style="width:100px;">请求结果：</td>
				<td style="width:300px;">
					<textarea id="result" style="resize:none;font-size:11px;width:300px;height:380px;border: 1px solid #95b8e7;border-radius: 5px;"></textarea>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
$(function(){
	var jsonParams = '{"ip":"192.168.0.1","version":"1.0","osType":"android","mobile":"sansungG9200","data":{"app_key":"21242797912","sign":"3C64615D8711DB062425A2F9C03BFD3B","timestamp":"1499505706973"}}';
	$("#params").val(format(jsonParams));
})
$("#reqClick").click(function(){
	var reqUrl = $("#reqUrl").val();
	var params = $("#params").val();
	$.messager.progress({
		title:'Please waiting',
		msg:'请求中，请稍后。。。'
	});
	$.ajax({
		method:"post",
		url:"<%=path%>/test/testApi.do",
		data:{
			reqUrl : reqUrl,
			reqParams:format(params,true)
		},
		success:function(data){
			$("#result").val(format(JSON.stringify(data)));
			$.messager.progress('close');
		},
		error:function(e){
			alert("请求失败，请检查路径是否正确！");
			$.messager.progress('close');
		},
		dataType:"json"
	});
});
</script>
