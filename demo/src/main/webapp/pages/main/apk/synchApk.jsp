<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<div style="width:1000px;height:600px;margin-top:10px;padding-left:10px;">
    <div style="width:300px;float:left;">
   		<ul id="viproomTree"></ul>
    </div>
    <div style="width:700px;float:right;text-align:left;display:none;" id="basicInfo">
    	<form id="machineForm" class="Form" method="post" enctype="multipart/form-data">
    		<input type="hidden" id="viproomId" value=""/>
    	   <table>
    	   		<tr>
	    	   		<td>推送类型：</td>
	    	   		<td>
	    	   			 <select id="softType" name="softType" class="easyui-combobox" name="softType" style="width:150px;">
				            <option value="APK">APK更新</option>
				        </select><br>
	    	   		</td>
    	   		</tr>
    	   		<tr>
	    	   		<td>apk文件：</td>
	    	   		<td>
	    	   			<input type="file" id="file" name="file" onchange="fileUpload();"/>
	    	   			<input id="resourceId" name="resourceId" type="hidden" value=""/>
	    	   		</td>
    	   		</tr>
    	   		<tr>
	    	   		<td>版本号：</td>
	    	   		<td>
    					<input type="text" id="versionCode" name="versionCode" value=""/>
	    	   		</td>
    	   		</tr>
    	   </table>
    	</form>
    </div>
    <div id="deviceInfoShow" style="margin-top:10px;width:700px;float:right;text-align:left;max-height:400px;">
    </div>
    <div style="width:700px;float:right;text-align:left;margin-top:10px;display:none;" id="buttonShow">
    	<a id="pushButton" href="javascript:void();"></a>  
    	<a id="pushButton1" href="javascript:void();"></a>  
    </div>
</div>
<script type="text/javascript">
	$("#softType").combobox({});
	/* $("#file").filebox({
		buttonText: '选择文件',
		multiple:true,
		onChange:function(){
			fileUpload();
		}
	}); */
	$("#versionCode").textbox({});
	$('#pushButton').linkbutton({    
	    text:'同步并推送',
	    onClick:function(){
	    	var resourceId = $("#resourceId").val();
	    	var versionCode = $("#versionCode").val();
	    	var viproomId = $("#viproomId").val();
	    	var deviceIds = "";
	    	var deviceIdsArray = new Array();
	    	$("input[type=checkbox]:checked").each(function(){
	    		deviceIdsArray.push($(this).val());
	    	});
	    	deviceIds = deviceIdsArray.join(",")
	    	$.ajax({
				method:"post",
				url:"<%=path%>/apk/synchApk.do",
				async:false,
				data:{
					viproomId:viproomId,
					resourceId:resourceId,
					versionCode:versionCode,
					deviceIds:deviceIds
				},
        		beforeSend:function(){
        			$.messager.progress({
					      	   title:'Please waiting',
					     	   msg:'同步并推送中，这可能需要一段时间，请稍后...'
					    });
        		},
				success:function(data){
					if(data.result == "1"){
        				$.messager.alert('提示',"推送成功，具体下载详情请查看设备监控管理",'info', function(r){
        			    });
        			}else{
        				$.messager.alert('操作失败',data.message,'error');
        			}
					$.messager.progress('close');
				}
				,dataType:"json"
				
			});
	    	
	    }
	});  
	$('#pushButton1').linkbutton({    
	    text:'服务器直接推送',
	    onClick:function(){
	    	var resourceId = $("#resourceId").val();
	    	var versionCode = $("#versionCode").val();
	    	var viproomId = $("#viproomId").val();
	    	var deviceIds = "";
	    	var deviceIdsArray = new Array();
	    	$("input[type=checkbox]:checked").each(function(){
	    		deviceIdsArray.push($(this).val());
	    	});
	    	deviceIds = deviceIdsArray.join(",")
	    	$.ajax({
				method:"post",
				url:"<%=path%>/apk/pushApk.do",
				async:false,
				data:{
					viproomId:viproomId,
					resourceId:resourceId,
					versionCode:versionCode,
					deviceIds:deviceIds
				},
        		beforeSend:function(){
        			$.messager.progress({
					      	   title:'Please waiting',
					     	   msg:'推送中，这可能需要一段时间，请稍后...'
					});
        		},
				success:function(data){
					if(data.result == "1"){
        				$.messager.alert('提示',"推送成功，具体下载详情请查看设备监控管理",'info', function(r){
        			    });
        			}else{
        				$.messager.alert('操作失败',data.message,'error');
        			}
					$.messager.progress('close');
				}
				,dataType:"json"
				
			});
	    	
	    }
	});  
	$('#viproomTree').tree({
		method:"post",
	    url:'<%=path%>/device/getViproomTreeJson.do',
	    animate:true,
	    lines:true,
	});
	
	$('#viproomTree').tree({
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
				$("#viproomId").val(viproomId);
				//根据vip厅的id查询下面的设备
				$.ajax({
					method:"post",
					url:"<%=path%>/device/deviceSelectInit.do?isOther="+isOther,
					data:{
						airlineId:airlineId,
						airportId:airportId,
						viproomId:viproomId
					},
					success:function(data){
						$("#deviceInfoShow").html(data);
						$("#basicInfo").show();
						$("#buttonShow").show();
					}
					,dataType:"html"
					
				});
			}
		}
	});

	//文件1上传
	function fileUpload(){
    	 $.messager.progress({
	   		 title:'Please waiting',
	   		 msg:'文件上传中...'
   		 });
		$.ajaxFileUpload({
	        url: '<%=path%>/resource/addResource.do', 
	        type: 'post',
	        async: 'false',
	        data : {
	        },
	        secureuri: false, //一般设置为false
	        fileElementId: 'file', // 上传文件的id、name属性名
	        dataType: 'JSON', //返回值类;型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
	        success: function(data, status){  
	        	var obj = $(data).removeAttr("style").prop("outerHTML");
	        	data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
	        	var json = JSON.parse(data);
	        	//设置向后台提交的隐藏域的值
	        	$("#resourceId").val(json.data[0].id);
	        	$.messager.progress('close');
	        },
	        error: function(data, status, e){ 
	        	$.messager.progress('close');
	            alert(e);
	        }
	    }); 
	}
</script>