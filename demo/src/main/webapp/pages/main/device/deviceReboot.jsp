<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<style type="text/css">
	

/*   .Form {
	width: 70%;
	margin: 40px auto;
	padding: 2% 5%;
	display: block;
}


.Form>p {
	font-size: 0.8rem;
	margin-bottom: 2%;
}

.Form>div {
	padding-top: 1%;
	padding-bottom: 1%;
}

.Form>div>label {
	
	font-size: 1.1rem;
}

.Form>div>input {
	width: 100%;
	height: 42px;
}

.btn {
	display: block !important;
	margin-top: 20px;
	height:30px;
	line-height:30px;
	text-align:right;
}

.Form input {
	border: solid 1px #c2cad8;
}
.btn a {
     margin-left:40%;
	background-color: #0864aa;
	color: #ffffff;
	border-radius: 5px;
	height:30px !important;
	text-align:center;
	font-size:0.8rem;
}
.btn a:hover{cursor:pointer;}

.file>input{padding-left: 0px !important;height:30px !important;}
#machineForm #per_sub {
	width: 10%;
	display:block;
	text-align:center;
}
 */

</style>
<div>
	<div class="leftdiv" style="height:100%;width:30%;float:left;margin-top:3%;margin-left:5%">
			 <div class="airport" style="margin-left: 3.5%;margin-top:15px">
		        <label >机场:</label><br/>
		        <input class="easyui-validatebox" type="text" name="airportId"  id="airportId" style="height:30px;width:100%;" />
		    </div>
		    <div class="vip" style="margin-left: 3.5%;display:none;margin-top:15px">
		        <label >vip厅:</label><br/>
		        <input class="easyui-validatebox" type="text" name="viproomId" id="viproomId" style="height:30px;width:100%" />
		    </div>
		    <div class="devIp" style="margin-left: 3.5%;margin-top:15px">
		        <label >设备IP:</label><br/>
		        <input class="easyui-validatebox" type="text" name="devIp"  id="devIp" style="height:30px;width:100%;" />
		    </div>
		    <div class="uuid" style="margin-left: 3.5%;margin-top:15px">
		        <label >UUID:</label><br/>
		        <input class="easyui-validatebox" type="text" name="uuid"  id="uuid" style="height:30px;width:100%;" />
		    </div>
		    <div class="connectionStatus" style="margin-left: 3.5%;margin-top:15px">
		        <label >连接状态:</label><br/>
		        <input class="easyui-validatebox" type="text" name="connectionStatus"  id="connectionStatus" style="height:30px;width:100%;" />
		    </div>
		    <div class="devType" style="margin-left: 3.5%;margin-top:15px">
		        <label >设备类型:</label><br/>
		        <input class="easyui-validatebox" type="text" name="devType"  id="devType" style="height:30px;width:100%;" />
		    </div>
		    <!-- <div class="whether" style="margin-left: 3.5%;">
		        <label >是否延时重启:</label><br/>
		        <input class="easyui-validatebox"  name="whether"  id="whether" style="height:30px;width:100%;border-radius:6px;"/>
		    </div> -->
		    <div class="time" style="margin-left: 3.5%;display:none;margin-top:15px">
		        <label >选择重启时间:</label><br/>
		        <input class="easyui-validatebox"  name="time"  id="time" style="height:40px;width:100%;border-radius:6px;"/>
		    </div>
		    <div>
		        <a  id="per_sub" style="margin-top:40px;margin-left:10%;background-color:#DDDDDD">重启</a>
		        <a  id="query" style="margin-top:40px;margin-left:25%;background-color:#DDDDDD">查询</a>
    		</div>
	</div>
	<div style="height:100%;width:60%;float:right;margin-top:2.3%">
			<label>请选择要重启的设备: <input type="checkbox" id="checkAll" style="margin-top:3%;">全选</label>
		    <div id="checkItem"></div>

			<div class="device" style="display:none;font-size:12px;width:31%;float:left;margin-left:3px;margin-top:10px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
				<input type="checkbox">
				<label onclick="checkDevice(this);"></label>
			</div>
	</div>
</div>

   
    
<script type="text/javascript">
    var json = JSON.parse('${airPortJson}');
    $(document).ready(function(){
    	
//        给页面的下拉框做一个初始化的工作
        $("#softType").combobox({});
        $("#vip").combobox({});
        $("#device").combobox({});
        $('#devIp').textbox({}); 
        $('#uuid').textbox({}); 
        $('#devType').combobox({  
    	    data:[{devType:"",devTypeName:"全部"},{devType:"1",devTypeName:"厅服务器"},{devType:"2",devTypeName:"工作站"},{devType:"3",devTypeName:"桥接"},{devType:"4",devTypeName:"PAD"},{devType:"5",devTypeName:"重启设备"},],  
	        editable:true,  
	        valueField:'devType',
	        textField:'devTypeName',  
	        onSelect:function(data){  
	        	$("#devType").val(data.devType);
	        }   
	    }); 
        $('#connectionStatus').combobox({  
    	    data:[{connectionStatus:"",connectionText:"请选择"},{connectionStatus:"1",connectionText:"已连接"},{connectionStatus:"2",connectionText:"未连接"}],  
	        editable:true,  
	        valueField:'connectionStatus',  
	        textField:'connectionText',  
	        onSelect:function(data){  
	        	$("#connectionStatus").val(data.connectionStatus);
	        }   
	    });  
        $('#versionCode').numberspinner({
        	min:0,
            editable:true
        });
        
        $('#whether').combobox({    
            valueField:'id',    
            textField:'text',
            data: [{
            	id: '1',
    			text: '是'
    		},{
    			id: '2',
    			text: '否'
    		}],
    		onSelect:function(data){
    			$(".time").slideDown(200);
    			$("#time").datetimebox({
    				showSeconds: false,
    			})
    		}
        });  
        
        
        $('#per_sub').linkbutton({    
            iconCls: 'icon-restart_device',
            plain:false,
            text:'确认重启',
            onClick:function(){
            	
            }
        }); 
        
        $('#query').linkbutton({    
            iconCls: 'icon-custom_refresh',
            plain:false,
            text:'查询设备',
            onClick:function(){
	            	if($("#airportId").combobox("getValue")==""){
	            		$.messager.alert('提示',"请选择重启设备的机场",'error');
	            		return;
	            	}
	            	if($("#viproomId").combobox("getValue") == ""){
            			$.messager.alert('提示',"请选择重启设备的VIP厅",'error');
                		return;
	            	}
            		$("#checkItem").html("");
    		    	//调接口去拼一个页面的复选框
    		    	var checkItem='';
    		    	$.ajax({
    		    		type:'post',
    	        		data:{
    	        			airportCode:$("#airportId").combobox("getValue"),
    	        			viproomId:$("#viproomId").combobox("getValue"),
    	        			devIp:$("#devIp").val(),
    	        			uuid:$("#uuid").val(),
    	        			connectionStatus:$("#connectionStatus").combobox("getValue"),
    	        			devType:$("#devType").combobox("getValue"),
    	        		},
    		    		url:'<%=path%>/system/getDeviceInfoJson.do',
    		    		success:function(data2){
    		    			$.each(data2,function(index,item){
    		    				//if(item.deviceId != "" && item.deviceId != null){
    		    					var temp='';
    			    				var obj = $(".device").clone();
    			    				obj.removeAttr("class");
    			    				obj.find("input[type='checkbox']").attr({"name":"deviceId","value":item.deviceId1});
    			    				if(item.connectionStatus=='1'){
    			    					temp+='<span style="color:green;">已连接</span>';
    			    				}
    			    				else{
    			    					temp+='<span style="color:red;">未连接</span>';
    			    				}
    			    				obj.find("label").attr("title",item.devIp);
    			    				obj.find("label").html(item.devTypeName+item.devIp+'('+temp+')');
    			    				$("#checkItem").append(obj.show());
    		    				//}
    		    			});
    		    		},
    		    		dataType:'json'	
    		    	})
    		    	$("#checkItem").append(checkItem);
            }
        }); 
        
//      初始化机场数据
        $("#airportId").combobox({
            data:json,
            valueField:'airportCode',
            textField:'airportName',
            onSelect:function(data){
            	$(".vip").slideDown(200);
            	$(".device").slideUp(200);
                $("#viproomId").combobox({
                     url:'<%=path%>/device/getViproomByAirportCode.do?airportCode='+data.airportCode,
                    valueField:'viproomId',
                    textField:'viproom',
                    onSelect:function(data1){
                    	
                    }
                });
            }
        });
    });
    
/*     function reloadTable(){
		toHomePage();
	} */
    
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
    
    $("#per_sub").click(function(){
    	var versionCode=$("#versionCode").val();
    	var airportId = $("#airportId").combobox("getValue");
    	var Apkurl = $("#fileHidden").val();
    	var airportId = $("#airportId").combobox("getValue");
    	console.log(111);
    	if(airportId == null || airportId == ""){
    		$.messager.alert('提示',"请选择重启设备的机场",'error');
    		return;
    	}
    	if(airportId != null || airportId != ""){
    		var viproomId = $("#viproomId").combobox("getValue");
    		if(viproomId == null || viproomId == ""){
    			$.messager.alert('提示',"请选择重启设备的VIP",'error');
        		return;
    		}
    	}
    	if($("#checkItem").find("input[type='checkbox']:checked").length<1){
    		$.messager.alert('提示',"请勾选设备",'error');
    		return;
    	}
    	$.messager.confirm("确认信息","确定远程重启选中的设备吗", function (r) {
	    		if(r){
	    			$.messager.progress({
      	    	   		 title:'Please waiting',
      	    	   		 msg:'正在进行设备远程重启，请稍后...'
     				 });
	    			$.ajax({
	            		type:'post',
	            		url:'<%=path%>/device/equipmentReboot.do',
	            		data:{
                			deviceId1:$("#checkItem").find("input[type='checkbox']:checked").map(function(index,elem) {
                	            return $(elem).val();		                    	            
                	        }).get().join(',')
                		},
	            		success:function(data){
	            			if(data.result == "1"){
	            				$.messager.alert('提示',data.message,'info');
	            			}else if(data.result == "0"){
	            				$.messager.alert('操作失败',data.message,'error'); 
	            			}else if(data.result=="2"){
	            				$.messager.alert('操作失败',data.message+"---",'error');
	            			}else{
	            				$.messager.alert('操作失败',"错误！",'error');
	            			}
	    	    			$.messager.progress('close');
	            		},
	            		dataType:'json'
	            	});	
	    		}
	    })	
    });
</script>