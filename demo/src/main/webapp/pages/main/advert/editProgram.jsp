<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
#myTable tr{height:40px;}
#myTable td{text-align:left;}
</style>
<form id="addOrEditForm" method="post" enctype="multipart/form-data" action="<%=path %>/program/addOrEditProgram.do">
	 <input type="hidden" class="form-control" id="id" name="rowKey" value="${resultInfo.rowKey}">
     <table id="myTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	<tr>  
          	<td style="width:100px;"><label>节目单名称：<span style="color:red;">*</span></label></td>
			<td>
				<input name="programName" class="easyui-textbox" style="width:66%;" value="${resultInfo.programName}"/>
			</td>
	    </tr>
     	<tr>
          	<td style="width:100px;"><label>有效开始时间：<span style="color:red;">*</span></label></td>
			<td style="text-align:left;">
				<input style="width:66%;" name="startDate" class="easyui-datetimebox" value="${resultInfo.startDate}"	/>
			</td>
	    </tr>
     	<tr>
          	<td style="width:100px;"><label>有效结束时间：<span style="color:re	d;">*</span></label></td>
			<td>
				<input style="width:66%;" name="endDate" class="easyui-datetimebox" value="${resultInfo.endDate}"/>
			</td>
	    </tr>
     	<tr>
          	<td style="width:100px;"><label>备注：</label><span style="color:red;">*</span></td>
			<td>
				<input name="remark" class="easyui-textbox" data-options="multiline:true" style="width:66%;height:100px" value="${resultInfo.remark}"/>
			</td>
	    </tr>
	</table>
</form>
<script type="text/javascript">
function validateForm(){
	if($("input[name='programName']").val() == ""){
		$.messager.alert('提示',"节目单名称不能为空",'error');
		return false;
	}
	if($("input[name='startDate']").val() == ""){
		$.messager.alert('提示',"开始时间不能为空",'error');
		return false;
	}
	if($("input[name='endDate']").val() == ""){
		$.messager.alert('提示',"结束时间不能为空",'error');
		return false;
	}
	return true;
}
</script>
