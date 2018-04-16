<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style tyle="text/css">
tr{height:25px;}
td{text-align:left;}
.testTable1{
      width:785px;
      min-height:100px;
      
      overflow-y:auto;
     
}
.proTable{
     table-layout:fixed;
     margin:0px auto;
    
}
.proTable td{
    text-align:center;
    word-break:keep-all;/* 不换行 */
    white-space:nowrap;/* 不换行 */
    overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
    text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
}

.mainPart{
   margin-top:12px;
   display:flex;
   width:50%;
   margin-left:30%;
   margin-top:20px;
}
   .mainPart img{
      width:2%;
      display:block;
      width:10%;
      margin-right:15px;
   }
.protext{
    font-size:1.0rem;
    margin-top:10px;
    dispaly:block;
    width:100%;
}
</style>
<div id="testTable1${programId}" class="testTable1">

</div>

<script type="text/javascript">

	$.ajax({
		type:"post",
		url:'advert/getProgramViproomListSynchData.do',
		data:{
				programId:"${programId}",
				airportCode:"${airportCode}"
			},
		beforeSend:function(){
			$("#testTable1${programId}").html("<div class='mainPart'><img src='<%=path %>/images/5-121204194035-50.gif'/><span class='protext'>数据加载中，请稍后...</span></div>");
		},
		success:function(data, textStatus){
			$("#testTable1${programId}").html(data);	
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("网络错误，请稍后重试!");
		},
		dataType:"html"
	});
</script>