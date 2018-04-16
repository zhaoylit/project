<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
#myTable tr{height:40px;}
#myTable td{text-align:left;}
     .textbox-prompt{
           padding:0px !important;    
     }


</style>
<form id="addOrEditForm" method="post" enctype="multipart/form-data" action="<%=path %>/program/addOrEditProgram.do">
	 <input type="hidden" class="form-control" id="pId" name="pId" value="${id}">
     <table id="myTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
     	<tr>  
          	<td style="width:100px;"><label>审核状态：<span style="color:red;">*</span></label></td>
			<td>
				<input id="examStatus" name="examStatus" value="${resultInfo.examStatus}" style="width:66%;"/>
			</td>
	    </tr>
     	<tr>
          	<td style="width:100px;"><label>审核备注：</label><span style="color:red;"></span></td>
			<td>
				<input name="examRemark" class="easyui-textbox"  style="width:66%;height:100px;" value="${resultInfo.remark}"/>
			</td>
	    </tr>
	</table>
</form>
<script type="text/javascript">
//初始化广告商类型
$("#examStatus").combobox({
	data:[{
			"examStatus":1,
			"examStatusName":"待审核",
		},{
			"examStatus":2,
			"examStatusName":"审核通过",
		},
		{
			"examStatus":3,
			"examStatusName":"审核不通过",
		}
		],   
    editable:false,
    valueField:'examStatus', 
    textField:'examStatusName',  
    onSelect:function(data){
    	
    },
    onLoadSuccess:function(){
    	//设置默认值
    	$('#examStatus').combobox('select',"${resultInfo.examStatus}");
    }
});
function validateForm(){
	if($("input[name='examStatus']").val() == ""){
		$.messager.alert('提示',"请选择审核状态",'error');
		return false;
	}
	return true;
}
</script>