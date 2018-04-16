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
<form id="addOrEditForm" method="post" >
	 <input type="hidden" class="form-control" id="id" name="rowKey" value="${resultInfo.rowKey}">
     <table id="acticityTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	<tr>
          	<td style="width:100px;"><label>设备编号：</label></td>
			<td>
				${resultInfo.deviceId}
			</td>
          	<td style="width:100px;"><label>设备类型：</label></td>
			<td>
				${resultInfo.devTypeName }
			</td>
	    </tr>
	    <tr>
          	<td style="width:100px;"><label>设备名称：</label></td>
			<td>
				${resultInfo.devName}
			</td>
          	<td style="width:100px;"><label>设备IP：</label></td>
			<td>
				${resultInfo.devIp}
			</td>
	    </tr>
	    <tr>
			<td style="width:100px;"><label>航空公司代码：</label></td>
			<td>
			    ${resultInfo.airlineCode}
			</td>
			<td style="width:100px;"><label>航空公司名称：</label></td>
			<td>
			   ${resultInfo.airlineName}
			</td>
	    </tr>
	     <tr>
			<td style="width:100px;"><label>机场代码：</label></td>
			<td>
			   ${resultInfo.airportCode}
			</td>
			<td style="width:100px;"><label>机场名称：</label></td>
			<td>
			    ${resultInfo.airportName}
			</td>
	    </tr>
	     <tr>
			<td style="width:100px;"><label>VIP厅ID：</label></td> 
			<td> <!-- easyui-numberbox easyui-numberbox-->
				${resultInfo.viproomId}&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td style="width:100px;"><label>VIP厅：</label></td> 
			<td> <!-- easyui-numberbox easyui-numberbox-->
				${resultInfo.viproom}
			</td>
	    </tr>
	     <tr>
	     <td style="width:100px;"><label>城市：</label></td>
			<td>
				${resultInfo.city}
			</td>
          	<td style="width:100px;"><label>县区：</label></td>
			<td> <!-- easyui-numberbox easyui-numberbox-->
				${resultInfo.county}
			</td>
	    </tr>
	    <tr>
	    <td style="width:100px;"><label>航站楼：</label></td>
			<td>
				${resultInfo.terminal}
			</td>
	      <td style="width:100px;"><label>状态：</label></td>
			<td>
				${resultInfo.state}
			</td>
	    </tr>
	     <tr>
	    <td style="width:100px;"><label>操作者：</label></td>
			<td>
				${resultInfo.operator}
			</td>
	      <td style="width:100px;"><label>操作时间：</label></td>
			<td>
				${resultInfo.createTime}
			</td>
	    </tr>
     	<tr>
          	<td style="width:100px;"><label>备注：</label></td>
			<td colspan="3">
				${resultInfo.remark}
			</td>
	    </tr>
	</table>
</form>
<script type="text/javascript">

</script>
