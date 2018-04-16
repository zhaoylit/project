<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style type="text/css">
	.subtn:hover{
		cursor:pointer;
		
		
	}
	.proTable{
		margin-top:25px;
	}
</style>
<a class="subtn" style="display:block;margin-top:20px;margin-left:10px;width:100px;height:25px;line-height:25px;text-align:center;font-size:0.7rem;border:solid 1px #eee;background-color:#eee;border-radius:20px;">
保存播放顺序
</a><span style="color:red;display:inline-block;position:relative;left:120px;top:-20px;">(修改完顺序在关闭页面之后不可修改哦)</span>
<table style="width:730px;" class="proTable">
	<tr style="">
		<td><b>客户名称</b></td>
		<td><b>播放类型</b></td>
		<td><b>播放时长</b></td>
		<td><b>播放频次</b></td>
		<td><b>广告类型</b></td>
		<td><b>半屏文件名称</b></td>
		<td><b>全屏文件名称</b></td>
		<td><b>排序</b></td>
	</tr>
	 <c:forEach items="${list }" var="bean"  varStatus="idx">
	 <tr>
		<td>${bean.advertiserName}</td>
		
		<td>
		  <c:if test="${bean.adtype == 'image'}">
			   图片
			</c:if>
			<c:if test="${bean.adtype == 'video'}">
			   视频
			</c:if>
		</td>
		<td>${bean.duration }</td>
		<td>${bean.count }</td>
		<td>
		   <c:if test="${bean.type == '1'}">
			    A类
			</c:if>
			<c:if test="${bean.type == '2'}">
			   B类
			</c:if>
		</td>
		
		<td title="${bean.file1OldName}">${bean.file1OldName}</td>
		<td title="${bean.file2OldName}">${bean.file2OldName}</td>
		
		<td>
		<input type="hidden" value="${bean.rowKey}">
			<input <c:if test="${bean.order !=''and bean.order!=null }">disabled="disabled"</c:if> type="number" value="${bean.order}" name="order" class="order" style="border:solid 1px #eee;text-align:center;width:60px;height:22px;font-size:8px;" placeholder="请输入" min="1"/>
		</td>
	 </tr>
	 </c:forEach>
</table>
<script type="text/javascript">
  
      
       function getTableValue(){
    	     
           var rowKey = "";  
           var InputOrder = "";
           $(".order").each(function (index, domEle){
        	   var inputOrder = $(this).val();//输入框的值 
        	  // alert(inputOrder);
        	   var key = $(this).prev().val();
        	   var data = {"rowKey":key,"order":inputOrder}; 
        	   dataJson.push(data);
           });  
       }
		$(".subtn").click(function(){
			
			var dataJson=new Array();
			var sameInput=new Array();
			var sameInpu2t=new Array();
	        var inputDate = "";
	           $(".order").each(function (index, domEle){
	        	   var inputOrder = $(this).val();//输入框的值 
	        	  // alert(inputOrder);
	        	   var key = $(this).prev().val();
	        	   var data = {"rowKey":key,"order":inputOrder}; 
	        	   var inputDate={"order":inputOrder}
	        	   

	        	   dataJson.push(data);
	        	   sameInput.push(inputDate);
	           });
	           /* console.log(sameInput)
	           console.log(sameInput.length)
	            for(var i=0;i<sameInput.length-1;i++){
	        	   for(var j=0;j<sameInput.length-i-1;j++){
	        		   if(sameInput[j]==sameInput[j+1]){
	        			   alert(sameInput[j])
	        		   }
	        	   }
	           }
	           console.log(sameInput.length)  */
			$.ajax({
				dataType:'json',
				type:"post",
				async:true,
				url:'advert/updateProgramOrder.do', //保存节目顺序的接口
				data:{
					"data":JSON.stringify(dataJson)
				},
        		success:function(data){
        			if(parseInt(data.result) == 1){
        				$.messager.alert('提示',"保存成功",'warning');
        				$.messager.alert('提示',"关闭页面之后不可再修改",'warning');
        			}else{
        				$.messager.alert('操作失败',data.message,'error');
        			}
        		},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("网络错误，请稍后重试!");
				}
			});
		})
</script>