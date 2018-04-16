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
//加载商品类目树
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
     <table id="myTable" cellpadding="0" cellspacing="0">
     	<tr>
          	<td class="td_class_1"><label>所属商家：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
			    <span>${data.trueName }</span>
				<input type="hidden" id="userId" name="userId" required="true" value="${data.userId}"/>
				<a id="businessSelect" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',onClick:function(){
						//弹出层显示商家列表
						var shopDialog = $('<div></div>').dialog({
						title:'商家选择',
						height:'400',\
						width:'800',
						top:'10%',
						left:'10%',
						minimizable:false,  
					    maximizable:false,  
					    collapsible:false,  
					    shadow: true,  
					    modal: true,  
						href:'${ctx}/shop/selectBusinessListInit.do',
						onClose:function(){	
							$(this).dialog('destroy');
						},
				        buttons : [
				          {
				            text : '选择',
				            iconCls : 'icon-save',
				            handler : function() {
				            	//选择选中的用户
				            	var checkedItems = $('#businessList').datagrid('getSelected');
				            	if(checkedItems != null){
									var selectId = checkedItems.id;
									var trueName = checkedItems.trueName;
									$('#userId').val(selectId);
									$('#userId').prev().html(trueName);
									shopDialog.dialog('destroy');
								}else{
									$.messager.alert('提示','请选择商家','warning');
									return;
								}
				            }
				          },
				          {
				            text : '关闭',
				            iconCls : 'icon-cancel',
				            handler : function() {
				            	shopDialog.dialog('destroy');
				            }
				          }
				        ]
					});
				}">选择商家</a>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>店铺名称：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" name="shopName" required="true" value="${data.shopName}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>手机号码：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" id="mobile" name="mobile" required="true" value="${data.mobile}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>电话：</label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" id="phone" name="phone" value="${data.phone}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>地址：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" id="address" name="address" required="true" value="${data.address}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>状态：<span style="color:red;">*</span></label></td>
			<td class="td_class_2">
				<input data-options="valueField:'dictValue',textField:'dictName',url:'${ctx }/dict/getDictByKey.do?dictKey=STATUS'" class="easyui-combobox" style="width:250px;height:25px;" id="status" name="status" required="true" value="${data.status}" style="height:30px;"/>
			</td>
	    </tr>
     	<tr>
          	<td class="td_class_1"><label>备注：</label></td>
			<td class="td_class_2">
				<input class="easyui-textbox" style="width:250px;height:25px;" id="remark" name="remark" value="${data.remark}"/>
			</td>
	    </tr>
	</table>
</form>
