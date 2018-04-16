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
<form id="addOrEditTypeForm" method="post" enctype="multipart/form-data" action="<%=path%>/exception/addOrEditExceptionType.do">
	 <input type="hidden" class="form-control" id="id" name="id" value="${resultInfo.id}">
     <table id="myTableP" style="width:100%;height:100%;margin-top:30px;padding-left:30px;">
     
     
     	<tr class="aaaa">
          	<td style="margin-top:20px;width:130px;"><label>异常代码：</label></td>
          	
			<td style="width:200px;">
				<input name="exceptionCode" class="easyui-textbox"  style="width:180px;height:30px" value="${resultInfo.exceptionCode}"/>
			</td>
			<td style="margin-left:50px;margin-top:10px;width:130px;"><label style="margin-left:50px;margin-top:10px;width:130px;">异常类型：</label></td> 
			<td>
				<input name="exceptionType" class="easyui-textbox" style="width:180px;height:30px" value="${resultInfo.exceptionType}"/>
			</td>
	    </tr>
	    
	     <tr class="aaaa" style="margin-top:30px;">  
	     	<td style="margin-top:20px;width:130px;"><label>异常方向：</label></td>
			<td style="width:200px;">
				<select style= "width:180px; height:27px; text-align:center" id="scope" name="scope" class="easyui-combobox"  value="${resultInfo.scope}">
				    <option <c:if test="${resultInfo.scope=='AN'}">selected='selected'</c:if> value="1">安卓端</option>
				    <option <c:if test="${resultInfo.scope=='SE'}">selected='selected'</c:if> value="2">后台</option>
			    </select>
			</td>	
          	
          	<td style="margin-left:50px;margin-top:10px;width:130px;"><label  style="margin-left:50px;margin-top:10px;width:130px;">异常等级：</label></td>
			<td>
				<select style= "width:180px; height:27px; text-align:center" id="grade" name="grade" class="easyui-combobox"  value="${resultInfo.grade}">
				    <option <c:if test="${resultInfo.grade=='1'}">selected='selected'</c:if> value="1">Blocker(致命,一级)</option>
				    <option <c:if test="${resultInfo.grade=='2'}">selected='selected'</c:if> value="2">Major(严重，二级)</option>
				    <option <c:if test="${resultInfo.grade=='3'}">selected='selected'</c:if> value="3">Normal(一般，三级)</option>
				    <option <c:if test="${resultInfo.grade=='4'}">selected='selected'</c:if> value="4">Minor(轻微，四级)</option>
			    </select>
			</td>
	    </tr>
	    <tr class="aaaa" style="margin-top:30px;">  
	     	<td style="margin-left:50px;margin-top:10px;width:130px;"><label  style="margin-top:10px;width:80px;">异常信息：</label></td>
			<td style="width:200px;">
				<input name="info" data-options="multiline:true" class="easyui-textbox" style="width:515px;height:100px" value="${resultInfo.info}"/>
			</td>	
	    </tr>
	    <tr class="aaaa" style="margin-top:30px;">
	    	<td style="margin-left:50px;margin-top:10px;width:130px;"><label  style="margin-top:10px;width:80px;">异常评价：</label></td>
			<td>
				<input name="remark" data-options="multiline:true" class="easyui-textbox" style="width:515px;height:100px" value="${resultInfo.remark}"/>
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
