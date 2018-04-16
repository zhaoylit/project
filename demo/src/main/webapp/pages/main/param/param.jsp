<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/css/page/main.css">
<form id="paramForm" class="Form" method="post" enctype="multipart/form-data">
    <div class="airport" >
        <label >机场:</label><br/>
        <input class="easyui-validatebox" type="text" name="airportId"  id="airportId" style="height:40px;width:100%;" />
    </div>
    <div class="count">
        <label >停留时间:</label><br/>
       <input class="easyui-validatebox" type="text" name="count" id="count" style="width:100%;height:36px;">
    </div>
    <div class="btn">
        <a id="per_sub">确认设置</a>
    </div>
</form>
<script type="text/javascript">
  
		$('#count').numberspinner({
		    min:1,
		    max: 60,
		    value:15,
		    editable:true
		});
		
		 $("#airportId").combobox({
	            url:'',
	            valueField:'airportCode',
	            textField:'airportName',
                multiple:true,
	        })

    $("#per_sub").click(function(){
    	var airportId = $("#airportId").combobox("getValue");
    	if(airportId == null || airportId == ""){
    		$.messager.alert('提示',"请选择要设置的机场",'error');
    	}
    	$.messager.confirm("确认信息","确认设置此机场的参数吗", function (r) {
	    		if(r){
	    			$.ajax({
	            		type:'post',
	            		url:'',
	            		data:$("#paramForm").serialize(),
	            		success:function(data){
	            			if(data.result == "1"){
	            				$.messager.alert('提示',"设置成功",'info');
	            			}else{
	            				$.messager.alert('操作失败',"设置失败",'error');
	            			}
	            		},
	            		dataType:'json'
	            	});	
	    		}
	    })	
    });
</script>