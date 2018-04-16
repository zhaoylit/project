<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
/* #myTable{font-size:12px;border:1px solid blue;} */
#myTable{margin-left:20px;}
#myTable tr{height:35px;display:block;margin-top:10px;}
#myTable tr td{padding:5px;}
.td_class_1{width:150px;border:1px solid #aaa;border-right:0px;background-color:#F4F4F4;}
.td_class_2{width:250px;border:1px solid #aaa;}
</style>

<script type="text/javascript">
$(function(){
	
})
function fileUpload(){
	 $.messager.progress({
   		 title:'Please waiting',
   		 msg:'文件上传中...'
	 });
	$.ajaxFileUpload({
        url: '<%=path%>/resource/fileUpload.do', 
        type: 'post',
        async: 'false',
        data : {
        	resourceType:$("#resourceType").combobox("getValue")
        },
        secureuri: false, //一般设置为false
        fileElementId: 'file1', // 上传文件的id、name属性名
        dataType: 'JSON', //返回值类;型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
        success: function(data, status){  
        	var obj = $(data).removeAttr("style").prop("outerHTML");
        	data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
        	var json = "";
        	try{
        		json = JSON.parse(data)
        	}catch(e){
        		$.messager.progress('close');
        		$.messager.alert('提示','${loginUser.userName }账户<br/>没有[文件上传]<br/>的操作权限!','warning')
        		console.log("异常信息：**********************"+e);
        		return false;
        	}
        	if(json.result == "0"){
        		$.messager.alert('提示',json.message,'error');
        		$.messager.progress('close');
        		return false;
        	}
        	//设置向后台提交的隐藏域的值
        	$("#iconPathHidden").val(json.fileUrl);
        	$("#iconPathHidden").next().val(json.fileOldName);
        	$("#file1OldNameShowDiv").html(json.fileOldName);
        	$("#file1OldNameShowDiv").attr("title",json.fileOldName);
        	$.messager.progress('close');
        },
        error: function(data, status, e){ 
        	$.messager.progress('close');
            alert(e);
        }
    }); 
}
</script>
<form id="addOrEditForm" method="post" enctype="multipart/form-data" action="">
	 <input type="hidden" class="form-control" id="id" name="id" value="${resultInfo.id}">
	 <input type="hidden" class="form-control" id="pid" name="pid" value="0">
     <table id="myTable" cellpadding="0" cellspacing="0">
     	<tr>
          	<td class="td_class_1"><label>icon名称：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="iconName" prompt="中文标记名称" required="true" value="${resultInfo.iconName}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>css选择器名称：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="className" prompt="例如：icon-add" required="true" value="${resultInfo.className}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>	
          	<td class="td_class_1"><label>文件名称：</label></td>
			<td class="td_class_2">
				<input class="easyui-textbox easyui-validatebox" style="width:250px;height:25px;" name="fileName" prompt="不为空使用该文件名" value="${resultInfo.fileName}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>资源类型：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input data-options="valueField:'dictValue',textField:'dictName',url:'${ctx }/dict/getDictByKey.do?dictKey=RESOURCE_TYPE'" class="easyui-combobox" style="width:250px;height:25px;" id="resourceType" name="resourceType" required="true" value="${resultInfo.resourceType}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>图标文件：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input id="file1"  name="file1" type="file" value="${resultInfo.iconPath}" onchange="fileUpload()" style="width:240px;height:30px;"/>
				<div id="file1OldNameShowDiv" title="${resultInfo.fileOldName}" style="color:blue;width:240px;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">
					${resultInfo.fileOldName}
				</div>
				<input id="iconPathHidden" name="filePath" type="hidden" value="${resultInfo.filePath}"/>
				<input name="fileOldName" type="hidden" value="${resultInfo.fileOldName}"/>
			</td>
	    </tr>
	</table>
</form>
