<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style type="text/css">
#userTable tr{height:40px;}
input{width:200px;}
</style>
<form id="addOrEditForm" method="post"  enctype="multipart/form-data" action="<%=path %>/user/addOrEditUserGroups.do">
	 <input type="hidden" class="form-control" id="id" name="rowKey" value="${resultInfo.rowKey}">
     <table id="userTable" style="width:100%;height:100%;margin-top:15px;padding-left:20px;">
		<tr>
          	<td style="width:100px;"><label>用户组名称：<span style="color:red;">*</span></label></td>
			<td>
				<input id="usergroupsname" name="usergroupsname" class="easyui-textbox" required="true" value="${resultInfo.usergroupsname}"/>
			</td>
	    </tr>
	</table>
</form>
<script type="text/javascript">
$(function(){
	//初始化嵌入点
	$("#insetPoint").combobox({
		url:'${ctx}/user/getDictJson.do?dictName=INSET_POINT',  
        editable:false,
        valueField:'dictKey', 
        textField:'dictValue',  
        onSelect:function(district){
        	
        },
        onLoadSuccess:function(){
        	if(data.result=='0'){
	    		$.messager.alert('提示',data.message,'error');
	    		return;
	    	}
        	//设置默认值
        	$('#insetPoint').combobox('select',"${resultInfo.insetPoint}");
        }
	});   
})

</script>
