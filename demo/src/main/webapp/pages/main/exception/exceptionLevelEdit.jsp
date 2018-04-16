<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

    <style type="text/css">
     #mytable123{
         border-collapse :collapse ;
     }
     .levelTh,.levelTd{
         width:210px;
         height:51px;
         border :1px dashed  #AAAAAA;
         font-size:12px;
         text-align :center;
     }
     
     .levelSolutin{
		border:1px solid #95B8E7;
		border-radius:4px;
     }
    </style>
    
<form id="EditLevelForm" method="post" enctype="multipart/form-data" >
     <table id="mytable123" ">
     	<thead>
		     	<tr>
			      <th class="levelTh">异常等级</th>
			      <th class="levelTh">异常解决</th>
			      <th class="levelTh">email</th>
			      <th class="levelTh">邮件延迟时间(小时)</th>
			    </tr>
		</thead>
		<tbody>
		<c:forEach items="${result}"  var="exceptionRecordList">
				    <tr class="aaaa" style="margin:50px 0;">
						<td class="levelTd">
							<input readonly="readonly"  name="id"   style="width:40px;height:30px;" value="${exceptionRecordList.id}"/>
								<c:if test="${exceptionRecordList.id=='1'}">Blocker(致命，一级)</c:if>
								<c:if test="${exceptionRecordList.id=='2'}">Major(严重，二级)</c:if>
								<c:if test="${exceptionRecordList.id=='3'}">Normal(一般，三级)</c:if>
								<c:if test="${exceptionRecordList.id=='4'}">Minor(轻微，四级)</c:if>
						</td>
						<td class="levelTd">
							<select style= "width:130px; height:27px; text-align:center" class="solution" name="solution" class="easyui-combobox"  value="${exceptionRecordList.solution}">
								<option <c:if test="${exceptionRecordList.solution=='1'}">selected='selected' "</c:if> value="1">无提醒</option>
  					    		<option <c:if test="${exceptionRecordList.solution=='2'}">selected='selected' "</c:if> value="2">邮件发送</option>
							</select>
							<%-- <input id="solution"  name="solution"  style="width:40px;height:30px;" value="${exceptionRecordList.solution}"/> --%>
						</td>
						<td class="levelTd">
							<input class="levelSolutin"  
							name="email"   style="width:160px;height:26px;<c:if test="${exceptionRecordList.solution=='1'}">display:none;</c:if>"  value="${exceptionRecordList.email}"/>
						</td>
						<td class="levelTd">
							<input class="levelSolutin" 
							name="sendTime"   style="width:80px;height:26px;<c:if test="${exceptionRecordList.solution=='1'}">display:none;</c:if>"  value="${exceptionRecordList.sendTime}"/>
						</td>
				    </tr>
	    </c:forEach>
	     </tbody>
	</table>
</form>
<script type="text/javascript">
	$(".solution").each(function(){
		var obj=$(this);
		$(this).combobox({
			onChange:function(newValue, oldValue){
				if(newValue=="2"){
					obj.parent().next().find("input").show();
					obj.parent().parent().find("td:last").find("input").show();
				}
				if(newValue=="1"){
					obj.parent().next().find("input").hide();
					obj.parent().parent().find("td:last").find("input").hide();
				}
			}
		});	
	});

</script>
