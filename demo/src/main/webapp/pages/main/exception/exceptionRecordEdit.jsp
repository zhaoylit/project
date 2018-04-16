<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<style>
	 .aaaa{
	 	display:block; /*将tr设置为块体元素*/	
   		margin:20px 0;  /*设置tr间距为2px*/
	 }
</style>
<form id="addOrEditForm" method="post" enctype="multipart/form-data" >
	 <input type="hidden" class="form-control" id="id" name="rowKey" value="${resultInfo.id}">
     <table id="myTableP" style="width:100%;height:100%;margin-top:30px;padding-left:30px;">
     
     
     	<tr class="aaaa">
          	<td style="margin-top:20px;width:130px;"><label>异常代码：</label></td>
          	
			<td style="width:200px;">
				<input   readonly="readonly" name="exceptionCode" class="easyui-textbox"  style="width:300px;height:30px" value="${resultInfo.exceptionCode}"/>
			</td>
			<td style="margin-left:50px;margin-top:10px;width:130px;"><label style="margin-left:50px;margin-top:10px;width:130px;">异常类型：</label></td> 
			<td>
				<input  readonly="readonly" name="exceptionType" class="easyui-textbox" style="width:300px;height:30px" value="${resultInfo.exceptionType}"/>
			</td>
	    </tr>
	    
	     <tr class="aaaa" >  
	     	<td style="margin-top:20px;width:130px;"><label>异常方向：</label></td>
			<td style="width:200px;">
				<input  readonly="readonly" name="scope" class="easyui-textbox" style="width:300px;height:30px" 
					<c:if test="${resultInfo.scope=='AN'}"> value="安卓"</c:if> 
					<c:if test="${resultInfo.scope=='SE'}"> value="后台"</c:if>/>
			</td>
          	
          	<td style="margin-left:50px;margin-top:10px;width:130px;"><label  style="margin-left:50px;margin-top:10px;width:130px;">异常等级：</label></td>
			<td>
				<input  readonly="readonly" name="grade" class="easyui-textbox" style="width:300px;height:30px;" 
				<c:if test="${resultInfo.grade=='1'}"> value="Blocker(致命)" </c:if> 
				<c:if test="${resultInfo.grade=='2'}"> value="Major(严重)" </c:if>
				<c:if test="${resultInfo.grade=='3'}"> value="Normal(一般)" </c:if> 
				<c:if test="${resultInfo.grade=='4'}"> value="Minor(轻微)" </c:if>
				 />
			</td>
	    </tr>
	    
	    
	    <tr class="aaaa" style="margin:50px 0;">  
          	<td style="margin-top:20px;width:130px;"><label>异常详细信息：</label></td>
			<td style="width:200px;">
				<input  readonly="readonly" name="message" class="easyui-textbox" data-options="multiline:true" style="width:734px;height:250px;" value="${resultInfo.message}"/>
			</td>
	    </tr>
	   
	    <tr class="aaaa">  
	    <td style="margin-top:20px;width:130px;"><label>异常区域：</label></td>
			<td>
				<input  readonly="readonly" name="module" class="easyui-textbox" style="width:734px;height:50px;" value="${resultInfo.module}"/>
			</td>
	    </tr>
	</table>
</form>
<script type="text/javascript">
function validateForm(){
	if($("input[name='advertiserName']").val() == ""){
		$.messager.alert('提示',"广告商名称不能为空",'error');
		return false;
	}
	if($("input[name='advertiserTypeId']").val() == ""){
		$.messager.alert('提示',"请选择广告商的类型",'error');
		return false;
	}
	return true;
}
</script>
