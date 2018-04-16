<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
	/* form{
		margin-left:10%;
	} */
    .device_set{
       font-size:20px;
       width:100%;
       margin-top:10px;
       word-break:keep-all;
       white-space:nowrap;
       overflow:hidden;
       text-overflow:ellipsis;
    }
</style>
<form id="synchForm" style="position:relative;">
	<div style="width:100%;height:600px;margin-top:10px;">
	    <div style="width:300px;float:left;">
	   		<ul id="viproomTree"></ul>
	    </div>
	    <div id="deviceInfoShow" style="width:500px;float:right;text-align:left;">
	    	
	    </div>
	</div>
</form>
	
<script type="text/javascript">
function checkDevice(obj){
	var a=$(obj).attr("value");
	console.log(a);
}

    var airlineId_='';
    var airportId_='';
    var viproomId_='';
    var deviceId_='';
	$('#viproomTree').tree({
		method:"post",
	    url:'<%=path%>/user/getDeviceTreeJson.do',
	    animate:true,
	    lines:true,
	    checkbox:true,
	    onBeforeLoad:function(data){
	    	 $.messager.progress({
		      	   title:'Please waiting',
		     	   msg:'正在读取设备，请勿刷新页面...'
		    });
        },
		onLoadSuccess:function(data){
			$.messager.progress('close');
		}
	});
	
	<%-- $('#viproomTree').tree({
		onClick: function(node){
			//获取类型
			var type = node.attributes.type;
			var isOther = "";
			if(type == "4"){
				isOther = "1";
			}
			if(type == "3" || type == "4"){
				 var airlineId = node.attributes.airlineId;
				 var airportId = node.attributes.airportId;
				 var viproomId = node.id;
				
				 airlineId_=airlineId;
				 airportId_=airportId;
				 viproomId_=viproomId;
				//根据vip厅的id查询下面的设备
				 $.ajax({
					method:"post",
					url:"<%=path%>/device/deviceSelectInit.do?isOther="+isOther,
					async:false,
					data:{
						airlineId:airlineId,
						airportId:airportId,
						viproomId:viproomId
					},
					success:function(data){
						$("#deviceInfoShow").html(data);
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
						})
					},
					dataType:"html"
					
				});
			}
		}
	}); --%>
    
	
	
	
      
</script>