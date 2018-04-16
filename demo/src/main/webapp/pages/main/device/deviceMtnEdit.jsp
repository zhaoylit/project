<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
#acticityTable tr{height:40px;}
input{width:200px;}
</style>
<form id="addOrEditMtn" method="post" action="<%=path %>/device/addOrEditDeviceMtn.do">
	 <input type="hidden" class="form-control" id="id" name="rowKey" value="${resultInfo.rowKey}">
     <table id="acticityTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	 <tr>
          	<td style="width:100px;"><label>设备ID：<span style="color:red;">*</span></label></td>
         </tr>
         <tr>
			<td>
				<input id="deviceId" name="deviceId" class="easyui-textbox" style="width:580px;height:30px" value="${resultInfo.deviceId}"/>
			</td>
		</tr>
		 <tr>
          	<td style="width:100px;"><label>设备维护者：<span style="color:red;">*</span></label></td>
         </tr>
         <tr>
			<td>
				<input id="operator" name="operator" class="easyui-textbox" style="width:580px;height:30px" value="${resultInfo.operator}"/>
			</td>
		</tr>
		 <tr>
          	<td style="width:100px;"><label>设备状态：<span style="color:red;">*</span></label></td>
         </tr>
         <tr>
			<td>
				<input id="mtnState" name="mtnState" class="easyui-combobox" style="width:580px;height:30px" value="${resultInfo.mtnState}" />
			</td>
		</tr>
		<tr>
          	<td style="width:100px;"><label>问题描述：</label></td>
        </tr>
         <tr>
			 <td colspan="3">
				<input name="describe" class="easyui-textbox" data-options="multiline:true" style="width:580px;height:100px" value="${resultInfo.describe}"/>
			 </td>
		 </tr>
	</table>
</form>
<script type="text/javascript">
$(function(){
	//初始化嵌入点
	$("#insetPoint").combobox({
		url:'${ctx}/device/getDictJson.do?dictName=INSET_POINT',  
        editable:false,
        valueField:'dictKey', 
        textField:'dictValue',  
        onSelect:function(district){
        	
        },
        onLoadSuccess:function(){
        	//设置默认值
        	$('#insetPoint').combobox('select',"${resultInfo.insetPoint}");
        }
	});
})
// 所做的一些表单验证
  function validateDeviceMtnForm(){
	if($("input[name='operator']").val() == ""){
		$.messager.alert('提示',"设备维护者不能为空",'error');
		return false;
	}
	if($("input[name='mtnState']").val() == ""){
		$.messager.alert('提示',"设备状态不能为空",'error');
		return false;
	}
	return true;
}
$('#mtnState').combobox({  
    data:[{devId:"1",devName:"已响应"},{devId:"2",devName:"未响应"},{devId:"3",devName:"已解决"},{devId:"4",devName:"未解决"},],  
    editable:false,  
    valueField:'devName',  
    textField:'devName',  
       
}); 
</script>
