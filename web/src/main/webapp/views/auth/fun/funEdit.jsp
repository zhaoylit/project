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
var flag = true;
$(function(){
	$('#parentFunSelect').combotree({    
	    url: '${ctx}/fun/getFunTreeJson.do',    
	    required: true,
	    animate:true,
	    onSelect:function(node){
	    	flag = false;
	    	initNextNode(node.id,node.pid);
	    },
	    onLoadSuccess:function(){
	    	$('#parentFunSelect').combotree("setValue",'${resultInfo.pid}');
	    }
	});  
	function initNextNode(id,pid){
		$.ajax({
    		type:'post',
    		url:'${ctx}/fun/getCurAndNextOneNode.do',
    		data:{
    			id:id,
    			pid:pid,
    			curId:'${resultInfo.id}'
    		},
    		success:function(data){
    			$('#afterFunSelect').combotree({    
    			    data: data,    
    			    required: true,
    			    animate:true,
    			    onSelect:function(node){
    			    	
    			    },
    			    onLoadSuccess:function(){
    			    	if(flag){
	    			    	$('#afterFunSelect').combotree("setValue",'${resultInfo.preNodeId}');
    			    	}
    			    }
    			});  
    		},
    		dataType:'json'
    	});
	}
	if('${resultInfo.id}' != ""){
		initNextNode('${resultInfo.parentId}','${resultInfo.parentPid}');
	}
})
</script>
<form id="addOrEditForm" method="post" enctype="multipart/form-data" action="<%=path %>/fun/addOrEditFun.do">
	 <input type="hidden" class="form-control" id="id" name="id" value="${resultInfo.id}">
	 <input type="hidden" class="form-control" id="pid" name="pid" value="${resultInfo.pid}">
     <table id="myTable" cellpadding="0" cellspacing="0">
     	<tr>
          	<td class="td_class_1"><label>功能名称：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="funName" required="true" value="${resultInfo.funName}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>功能地址：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="funUrl" required="true" value="${resultInfo.funUrl}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>父节点：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input id="parentFunSelect" class="easyui-combobox" style="width:250px;height:25px;" required="true" name="parendId" value="" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>位置：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input id="afterFunSelect" class="easyui-combobox" style="width:220px;height:25px;" required="true" name="afterId" value="" style="height:30px;"/><span style="font-size:11px;">之后</span>
			</td>
	    </tr>
	</table>
</form>
