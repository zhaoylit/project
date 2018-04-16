<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<body>
<link id="easyuiTheme" rel="stylesheet"	href="<%=path%>/pages/themes/default/easyui.css" type="text/css"></link>
<script type="text/javascript" src="<%=path%>/pages/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/pages/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<style type="text/css">
#acticityTable tr{height:30px;}
input{width:150px;}
</style>
<form id="addOrEditForm" method="post" action="<%=path %>/device/addOrEditDevice.do" style="margin-top:0px;">
	 <input type="hidden" class="form-control" id="id" name="id" value="${data.id}">
     <table id="acticityTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	<tr>
          	<td><label style="width:100px;">设备id：</label></td>
			<td>
				<input id="deviceId" class="easyui-textbox" readonly="readonly"  value="${data.id}"/>
			</td>
	    </tr>
     	<tr>
          	<td><label style="width:100px;">设备编号：<span style="color:red;">*</span></label></td>
			<td>
				<input id="deviceNo" name="deviceNo"  class="easyui-textbox"  value="${data.deviceNo}"/>
			</td>
          	<td style="width:100px;"><label>设备类型：<span style="color:red;">*</span></label></td>
			<td>
			    <select name="deviceType" id="deviceType">
			    	<option value="1">厅服务器</option>
			    	<option value="2">工作站</option>
			    </select>
			</td>
	    </tr>
	    <tr>
          	<td style="width:100px;"><label>设备名称：<span style="color:red;">*</span></label></td>
			<td>
				<input id="deviceName" name="deviceName" class="easyui-textbox" value="${data.deviceName}"/>
			</td>
          	<td style="width:100px;"><label>设备IP：<span style="color:red;">*</span></label></td>
			<td>
				<input id="deviceIp" name="deviceIp" class="easyui-textbox"  value="${data.deviceIp}"/>
			</td>
	    </tr>
	    <tr>
			<td style="width:100px;"><label>航空公司：</label></td>
			<td id="airlineId_td">
			   <select name="airlineId" id="airlineId" value="${data.airlineId}">
			   </select>
			</td>
			<td style="width:100px;"><label>机场名称：</label></td>
			<td id="airportId_td">
			    <input class="easyui-combobox" name="airportId" id="airportId" value="${data.airportId}"/>
			</td>
	    </tr>
	     <tr>
			<td style="width:100px;"><label>VIP厅：</label></td> 
			<td id="viproomId_td">
				<input name="viproomId" id="viproomId" class="easyui-combobox"  value="${data.viproomId}"/>
			</td>
	    </tr>
	     <%-- <tr>
	     <td style="width:100px;"><label>城市：<span style="color:red;">*</span></label></td>
			<td>
				<input name="city" class="easyui-textbox"  value="${data.city}"/>
			</td>
          	<td style="width:100px;"><label>县区：</label></td>
			<td>
				<input name="county" class="easyui-textbox" value="${data.county}"/>
			</td>
	    </tr>
	    <tr>
	    <td style="width:100px;"><label>航站楼：</label></td>
			<td>
				<input name="terminal" class="easyui-textbox" value="${data.terminal}"/>
			</td>
	      <td style="width:100px;"><label>状态：<span style="color:red;">*</span></label></td>
			<td>
				<input name="state" id="state" class="easyui-combobox"   value="${data.state}"/>
			</td>
	    </tr>
     	<tr>
          	<td style="width:100px;"><label>备注：</label></td>
			<td colspan="3">
				<input id="remark" name="remark" class="easyui-textbox" data-options="multiline:true" style="width:460px;height:100px" value="${data.remark}"/>
			</td>
	    </tr> --%>
	</table>
</form>
<script type="text/javascript">
$(function(){
	$("#deviceId").textbox({});
	$("#deviceNo").textbox({});
	$("#remark").textbox({});
	$("#deviceName").textbox({});
	$("#deviceIp").textbox({});
	$("#deviceType").combobox({});
	
})
var airportId_ = "${data.airportId}";
var viproomId_   = "${data.viproomId}";
$(function(){
	initAirline();
	if('${data.airlineId}' != ""){
		initaiport('${data.airlineId}');
	}else{
		$("#airportId").combobox({});
	}
	if('${data.airportId}' != ""){
		initviproomId('${data.airportId}');
	}else{
		$("#viproomId").combobox({});
	}
})	
function initAirline(){
	$("#airlineId_td").html("");
	$("#airlineId_td").append('<input id="airlineId" name="airlineId" value="${data.airlineId}"/>');
	$("#airlineId").combobox({
	     url:'<%=path%>/device/getAirline.do',
		 editable:false,
		 valueField:'id',
		 textField:'airlineName',
		 onLoadSuccess:function(){
	       	//设置默认值
	       	$('#airlineId').combobox('select',"${data.airlineId}");
	     },
	     onSelect:function(record){ 
	    	 clearAirportCode();
	    	 clearviproomId();
	    	 airportId_ = "";
	    	 viproomId_ = "";
	    	 initaiport(record.id,record.airlineName);
	     } 
	});
}
function initaiport(airlineId){
$("#airportId_td").html("");
$("#airportId_td").append('<input id="airportId" name="airportId" value="'+airportId_+'"/>');
$("#airportId_td").find("#airportId").combobox({
    url:'<%=path%>/device/getAirport.do?airlineId='+airlineId,
    editable:false,
	 multiple:false,
	 valueField:'id',
	 textField:'airportName',
	 onLoadSuccess:function(){
     	//设置默认值
//      	$('#airportId').combobox('select',"${data.airportId}");
     },
     onSelect:function(record){
   	   viproomId_ = "";
   	   $("#airportId_td").find("#airName").val(record.airportName);
  	   initviproomId(record.id,record.airportName);
     }	
});
}
function initviproomId(airportId){
$("#viproomId_td").html("");
$("#viproomId_td").append('<input id="viproomId" name="viproomId" value="'+viproomId_+'"/>');
$("#viproomId_td").find("#viproomId").combobox({
    url:'<%=path%>/device/getViproom.do?airportId='+airportId,
    editable:false,
	 multiple:false,
	 valueField:'id',
	 textField:'viproomName',
	 onLoadSuccess:function(){
       	//设置默认值
       	$('#viproomId').combobox('select',"${data.viproomId}");
       },
       onSelect:function(record){
    	   $("#viproomId_td").find("#viproom").val(record.viproom);
		}
});
}
function clearviproomId(){
	$("#viproomId_td").html("");
	$("#viproomId_td").append('<input id="viproomId" name="viproomId" value=""/>');
	$("#viproomId_td").find("#viproomId").combobox({
		
	});
}
function clearAirportCode(){
	$("#airportId_td").html("");
	$("#airportId_td").append('<input id="airportId" name="airportId" value=""/>');
	$("#airportId_td").find("#airportId").combobox({
		
	});
}

<%-- $('#state').combobox({
	url:'<%=path%>/device/state.do',
	editable:true,
	valueField:'state',
	textField:'stateName'
}); --%>
/* function validateDeviceForm(){
	if($("input[name='deviceId']").val() == ""){
		$.messager.alert('提示',"设备编号不能为空",'error');
        return false;
	}
	 var reg = "^[A-Za-z0-9]+$";
	 	if(!$("input[name='deviceId']").val().match(reg)){
	 		$.messager.alert('提示',"设备编号格式不正确，请重新输入",'error');
	 		return false
	 	} 
	if($("input[name='devName']").val() == ""){
			$.messager.alert('提示',"设备名称不能为空",'error');
	        return false;
	}
	if($("input[name='devType']").val() == ""){
		$.messager.alert('提示',"设备类型不能为空",'error');
		return false;
	}
	if($("input[name='devIp']").val() ==""){
		$.messager.alert('提示',"设备IP不能为空",'error');
		return false
	} 
     var reg1 = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
 	if(!$("input[name='devIp']").val().match(reg1)){
 		$.messager.alert('提示',"您的IP格式不正确，请重新输入",'error');
 		return false
 	}  
	if($("input[name='city']").val() == ""){
		$.messager.alert('提示',"请填写城市",'error');
		return false;
	}
	if($("input[name='state']").val() == ""){
		$.messager.alert('提示',"状态不能为空",'error');
		return false;
	}
	if($("input[name='airlineId']").val() == ""){
		$.messager.alert('提示',"航空公司不能为空",'error');
		return false;
	}
	if($("input[name='airportId']").val() == ""){
		$.messager.alert('提示',"机场不能为空",'error');
		return false;
	}
	 if($("#viproomId").combobox('getValue') == ""){
		$.messager.alert('提示',"VIP厅不能为空",'error');
		return false;
	} 
	return true;
}
 */
 </script>
</body>
