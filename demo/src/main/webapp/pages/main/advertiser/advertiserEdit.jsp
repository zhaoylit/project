<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
#myTable tr{height:40px;}
#myTable td{text-align:left;}

/* input{width:300px;} */
</style>
<form id="addOrEditForm" method="post" enctype="multipart/form-data" >
	 <input type="hidden" class="form-control" id="id" name="rowKey" value="${resultInfo.rowKey}">
     <table id="myTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	<tr>  
          	<td style="width:100px;"><label>广告商名称：<span style="color:red;">*</span></label></td>
			<td>
				<input name="advertiserName" class="easyui-textbox" style="width:66%;" value="${resultInfo.advertiserName}"/>
			</td>
	    </tr>
	    <tr>  
          	<td style="width:100px;"><label>广告商类型：<span style="color:red;">*</span></label></td>
			<td>
				<input id="advertiserTypeId" name="advertiserType" value="${resultInfo.advertiserType}" style="width:66%;"/>
			</td>
	    </tr>
	    
	</table>
</form>
<script type="text/javascript">
		//初始化广告商类型
		$("#advertiserTypeId").combobox({
			data:[{
	 			"advertiserTypeId":"A",
	 			"advertiserTypeName":"A类",
	 		},{
	 			"advertiserTypeId":"B",
	 			"advertiserTypeName":"B类",
	 		}
	 		],   
		    editable:false,
		    valueField:'advertiserTypeId', 
		    textField:'advertiserTypeName',  
		    onSelect:function(data){
		    	
		    },
		    onLoadSuccess:function(){
		    	//设置默认值
		    	$('#advertiserTypeId').combobox('select',"${resultInfo.advertiserType}");
		    }
		});
function validateForm(){
	if($("input[name='advertiserName']").val() == ""){
		$.messager.alert('提示',"广告商名称不能为空",'error');
		return false;
	}
	if($("input[name='advertiserType']").val() == ""){
		$.messager.alert('提示',"请选择广告商的类型",'error');
		return false;
	}
	return true;
}
</script>
