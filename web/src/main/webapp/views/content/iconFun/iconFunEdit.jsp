<%@ page language="java" contentType="text/html; charset=UTF-8"  import="com.zj.web.common.utils.*"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
/* #myTable{font-size:12px;border:1px solid blue;} */
#myTable{margin-left:20px;}
#myTable tr{min-height:35px;display:block;margin-top:10px;}
#myTable tr td{padding:5px;}
.td_class_1{width:150px;border:1px solid #aaa;border-right:0px;background-color:#F4F4F4;}
.td_class_2{width:250px;border:1px solid #aaa;}
</style>

<script type="text/javascript">
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
        	resourceType:"2" //轮播图
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
        	if(!$.isEmptyObject(json)){
        		//设置后台提交的值
            	$("#imageUrl").textbox("setValue",json.filePath);
            	//设置显示的值
            	$("#imageShow").find("img").attr("src", "<%=return_path%>"+json.filePath );
            	
            	/* $("#iconPathHidden").next().val(json.fileOldName);
            	$("#file1OldNameShowDiv").html(json.fileOldName);
            	$("#file1OldNameShowDiv").attr("title",json.fileOldName); */
        	}
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
	 <input type="hidden" class="form-control" id="id" name="id" value="${data.id}">
	 <input type="hidden" class="form-control" id="pid" name="pid" value="0">
     <table id="myTable" cellpadding="0" cellspacing="0">
     	<tr>
          	<td class="td_class_1"><label>名称：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" id="title" name="title" required="true" value="${data.title}" style="height:30px;"/>
			</td>
	    </tr>
        <tr>
          	<td class="td_class_1"><label>图片：<span style="color:red;">*</span></label></td>
			<td class="td_class_2" style="height:100px;">
			    <div id="imageShow" style="cursor:pointer;" title="点击我更换图片" onclick="javascript:$('#file1').click();">
			    	 <img src="${data.imageUrl }" style="height:100px;width:100%"/>
			    </div>
			    <div style="display:none;">
			        <input type="file" id="file1" name="file1" onchange="fileUpload();"/>
					<input class="easyui-textbox" id="imageUrl" name="imageUrl" required="true" value="${data.imageUrl}" style="height:30px;width:250px;"/>
			    </div>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>跳转地址：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="clickUrl" prompt="图片点击跳转地址" required="true" value="${data.clickUrl}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>顺序：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-numberbox" style="width:250px;height:25px;" id="order" name="order" required="true" value="${data.order}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>状态：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input data-options="valueField:'dictValue',textField:'dictName',url:'${ctx }/dict/getDictByKey.do?dictKey=STATUS'" class="easyui-combobox" style="width:250px;height:25px;" id="status" name="status" required="true" value="${data.status}" style="height:30px;"/>
			</td>
	    </tr>
	</table>
</form>
