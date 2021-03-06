<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
     #adDevice{width:90%;margin:20px auto;padding-top:2%;}
      .adDevice_part label{
      		font-size:20px;
      		display:block;
      		margin-bottom:10px;
      }
</style>
<form id="adDevice" method="post" class="Form" enctype="multipart/form-data">
      <div class="adDevice_part">
         <label>请选择要发布的厅服务器</label><br/>
          <input id="viproom" name="viproom" value="" style="height:38px;width:100%;margin-top:3%;">
          <label>请选择要发布的设备: <input type="checkbox" id="checkAll" style="margin-top:3%;">全选</label>
      </div>
      <div id="checkItem"></div>
</form>
<div class="device" style="display:none;font-size:20px;width:31%;float:left;margin-left:3px;margin-top:10px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
	<input type="checkbox">
	<label onclick="checkDevice(this);"></label>
</div>
<script type="text/javascript">
		var jsonData = $.parseJSON('${viproomJson}');
   	    //初始化厅服务器编号
       $('#viproom').combobox({
		   data:jsonData,  //去取厅服务器的数据接口
		   // multiple:true,
		    valueField:'viproomId',
		    textField:'viproom',
		    onSelect:function(data){
		    	$("#checkItem").html("");
		    	//调接口去拼一个页面的复选框
		    	var checkItem='';
		    	$.ajax({
		    		type:'post',
	        		data:{
	        			viproomId:data.viproomId,
	        			airportCode:data.airportCode,
	        			deviceType:'2',
	        		},
		    		url:'<%=path%>/advert/getDeviceInfoJson.do',
		    		success:function(data1){
		    			$.each(data1,function(index,item){
		    				if(item.deviceId != "" && item.deviceId != null){
		    					var temp='';
			    				var obj = $(".device").clone();
			    				obj.removeAttr("class");
			    				obj.find("input[type='checkbox']").attr({"name":"deviceId","value":item.deviceId});
			    				if(item.connectionStatus=='1'){
			    					temp+='<span style="color:green;">已连接</span>';
			    				}
			    				else{
			    					temp+='<span style="color:red;">未连接</span>';
			    				}
			    				obj.find("label").attr("title",item.devIp);
			    				obj.find("label").html(item.devName+item.devIp+'('+temp+')');
			    				$("#checkItem").append(obj.show());
		    				}
		    			});
		    		},
		    		dataType:'json'	
		    	})
		    	
		    	$("#checkItem").append(checkItem);
                
            }
		});
   //初始化设备
   
// 所做的一些表单验证
  function validateAdvertForm(){
	if($("input[name='viproomId']").val() == ""){
		$.messager.alert('提示',"请选择要发布的vip厅",'error');
		return false;
	}
	if($("input[name='devIp']").val() == ""){
		$.messager.alert('提示',"请选择要发布的设备",'error');
		return false;
	}
	return true;
}
function checkDevice(obj){
	if($(obj).prev().is(":checked")){
		$(obj).prev().attr("checked",false);
	}else{
		$(obj).prev().attr("checked",true);
	}
}
$("#checkAll").click(function(){
	if($(this).is(":checked")){
		$("#checkItem").find("input[type='checkbox']").each(function(index){
			$(this).attr("checked",true);
		});
	}else{
		$("#checkItem").find("input[type='checkbox']").each(function(index){
			$(this).attr("checked",false);
		});
	}
});
</script>
