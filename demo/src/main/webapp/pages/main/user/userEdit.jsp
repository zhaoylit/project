<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style type="text/css">
#userTable tr{height:40px;}
input{width:200px;}
</style>
<form id="addOrEditForm" method="post"  enctype="multipart/form-data" action="<%=path %>/user/addOrEditUser.do">
	 <input type="hidden" class="form-control" id="id" name="id" value="${resultInfo.id}">
     <table id="userTable" style="width:100%;height:100%;margin-top:10px;padding-left:20px;">
	   <c:if test="${resultInfo.id == null or resultInfo.id == ''}">
		<tr>
          	<td style="width:90px;"><label>账户：<span style="color:red;">*</span></label></td>
			<td>
				<input id="accounta" name="account" class="easyui-textbox" placeholder="请输入" value="${resultInfo.account}"/>
			</td>
			<td style="width:90px;"><label>密码：<span style="color:red;">*</span></label></td>
			<td>
				<input id="passWord" name="passWord" class="easyui-textbox"  value="${resultInfo.passWord}"/>
			</td>
	    </tr>
	   </c:if>
     	<tr>
     		 <td style="width:90px;"><label>省份：<span style="color:red;">*</span></label></td>
			<td>
				<select style= "width:200px; height:27px; text-align:center" id="province" name="province" class="easyui-combobox"  value="${resultInfo.province}">
						<option value="1">--请选择--</option>
  					    <option <c:if test="${resultInfo.province=='BEIJING'}">selected='selected'</c:if> value="BEIJING">北京</option>
  					    <option <c:if test="${resultInfo.province=='TIANJIN'}">selected='selected'</c:if> value="TIANJIN">天津</option>
  					    <option <c:if test="${resultInfo.province=='SHANGHAI'}">selected='selected'</c:if> value="SHANGHAI">上海</option>
  					    <option <c:if test="${resultInfo.province=='CHONGQING'}">selected='selected'</c:if> value="CHONGQING">重庆</option>
  					    <option <c:if test="${resultInfo.province=='HEBEI'}">selected='selected'</c:if> value="HEBEI">河北</option>
  					    <option <c:if test="${resultInfo.province=='SHANXI1'}">selected='selected'</c:if> value="SHANXI1">山西</option>
  					    <option <c:if test="${resultInfo.province=='LIAONING'}">selected='selected'</c:if> value="LIAONING">辽宁</option>
  					    <option <c:if test="${resultInfo.province=='JILIN'}">selected='selected'</c:if> value="JILIN">吉林</option>
  					    <option <c:if test="${resultInfo.province=='HEILONGJIANG'}">selected='selected'</c:if> value="HEILONGJIANG">黑龙江</option>
  					    <option <c:if test="${resultInfo.province=='JIANGSU'}">selected='selected'</c:if> value="JIANGSU">江苏</option>
  					    <option <c:if test="${resultInfo.province=='ZHEJIANG'}">selected='selected'</c:if> value="ZHEJIANG">浙江</option>
  					    <option <c:if test="${resultInfo.province=='ANHUI'}">selected='selected'</c:if> value="ANHUI">安徽</option>
  					    <option <c:if test="${resultInfo.province=='FUJIAN'}">selected='selected'</c:if> value="FUJIAN">福建</option>
  					    <option <c:if test="${resultInfo.province=='JIANGXI'}">selected='selected'</c:if> value="JIANGXI">江西</option>
  					    <option <c:if test="${resultInfo.province=='SHANDONG'}">selected='selected'</c:if> value="SHANDONG">山东</option>
  					    <option <c:if test="${resultInfo.province=='HENAN'}">selected='selected'</c:if> value="HENAN">河南</option>
  					    <option <c:if test="${resultInfo.province=='HUBEI'}">selected='selected'</c:if> value=HUBEI"">湖北</option>
  					    <option <c:if test="${resultInfo.province=='HUNAN'}">selected='selected'</c:if> value="HUNAN">湖南</option>
  					    <option <c:if test="${resultInfo.province=='GUANGDONG'}">selected='selected'</c:if> value="GUANGDONG">广东</option>
  					    <option <c:if test="${resultInfo.province=='HAINAN'}">selected='selected'</c:if> value="HAINAN">海南</option>
  					    <option <c:if test="${resultInfo.province=='SICHUAN'}">selected='selected'</c:if> value="SICHUAN">四川</option>
  					    <option <c:if test="${resultInfo.province=='GUIZHOU'}">selected='selected'</c:if> value="GUIZHOU">贵州</option>
  					    <option <c:if test="${resultInfo.province=='YUNNAN'}">selected='selected'</c:if> value="YUNNAN">云南</option>
  					    <option <c:if test="${resultInfo.province=='SHANXI2'}">selected='selected'</c:if> value="SHANXI2">陕西</option>
  					    <option <c:if test="${resultInfo.province=='GANSU'}">selected='selected'</c:if> value="GANSU">甘肃</option>
  					    <option <c:if test="${resultInfo.province=='QINGHAI'}">selected='selected'</c:if> value="QINGHAI">青海</option>
  					    <option <c:if test="${resultInfo.province=='TAIWAN'}">selected='selected'</c:if> value="TAIWAN">台湾</option>
  					    <option <c:if test="${resultInfo.province=='NEIMENGGU'}">selected='selected'</c:if> value="NEIMENGGU">内蒙古</option>
  					    <option <c:if test="${resultInfo.province=='GUANGXI'}">selected='selected'</c:if> value="GUANGXI">广西</option>
  					    <option <c:if test="${resultInfo.province=='XIZANG'}">selected='selected'</c:if> value="XIZANG">西藏</option>
  					    <option <c:if test="${resultInfo.province=='NINGXIA'}">selected='selected'</c:if> value="NINGXIA">宁夏</option>
  					    <option <c:if test="${resultInfo.province=='XINJIANG'}">selected='selected'</c:if> value="XINJIANG">新疆</option>
  					    <option <c:if test="${resultInfo.province=='XIANGGANG'}">selected='selected'</c:if> value="XIANGGANG">香港</option>
  					    <option <c:if test="${resultInfo.province=='AOMEN'}">selected='selected'</c:if> value="AOMEN">澳门</option>
				</select>
			</td> 
			<td style="width:90px;"><label>城市：</label></td>
			<td>
				<input style="height:27px;" name="city" class="easyui-textbox" value="${resultInfo.city}"/>
			</td>
	    </tr>
     	<tr>
     	<td style="width:90px;"><label>街道：</label></td>
			<td>
				<input style="height:27px;" name="street" class="easyui-textbox" value="${resultInfo.street}"/>
			</td>
     	<td style="width:90px;"><label>昵称：<span style="color:red;">*</span></label></td>
			<td>
				<input style="height:27px;" name="nickName" class="easyui-textbox" value="${resultInfo.nickName}"/>
			</td>
	    </tr>
	    <tr>
			<td style="width:100px;"><label>组织名称：<span style="color:red;">*</span></label></td>
			<td>
			   <input class="easyui-combobox" name="org"  id="org" <c:if test="${resultInfo.account=='admin'}">disabled="true"</c:if> value="${resultInfo.org}"/>
			   <input id="lineName" name="airlineName" type="hidden"   value="${resultInfo.airlineName}"/>
			</td>
			<td style="width:100px;"><label>机场名称：</label></td>
			<td id="airportCode_td">
			</td>
	    </tr>
	    <tr>
	    	<td style="width:90px;"><label>真实姓名：</label></td>
			<td>
				<input style="height:27px;" name="realName" class="easyui-textbox" value="${resultInfo.realName}"/>
			</td>
	    	<td style="width:90px;"><label>电话：<span style="color:red;">*</span></label></td>
			<td>
				<input style="height:27px;" name="phone" class="easyui-textbox" value="${resultInfo.phone}"/>
			</td>
	    </tr>
	    <tr>
          	<td style="width:90px;"><label>email：</label></td>
			<td>
				<input style="height:27px;" name="email" class="easyui-textbox" value="${resultInfo.email}"/>
			</td>
          	<td style="width:90px;"><label>微信：</label></td>
			<td>
				<input style="height:27px;" name="weixin" class="easyui-textbox" value="${resultInfo.weixin}"/>
			</td>
	    </tr>
	</table>
</form>
<script type="text/javascript">
$("#province").combobox({editable:false});
/* 组织航空公司 */
 $.post("<%=path%>/device/getAirline.do",{},function(data){ 
	data.splice(data.length,0,{org:"0",airlineName:"其他"}); 
	if("${user.account}"=="admin"){
		data.splice(0,0,{org:"",airlineName:"全部"});   
	}
	/* if($.session.get('username')=='admin'){
	} */
		$("#org").combobox({
		   	 data:data,
			 editable:false,
			 valueField:'id',
			 textField:'airlineName',
			 onLoadSuccess:function(){
				 if(data.result=='0'){
	    	    		$.messager.alert('提示',data.message,'error');
	    	    		return;
	    	    	}
		       	//设置默认值
		       	$('#lineName').combobox('select',"${resultInfo.org}");
		       },
		    onSelect:function(record){ 
		    	$("input[name='airportCode']:hidden").attr("value","");
//			    	$('#airportCode').combobox('setValue', null);
		    	$("#lineName").val(record.airlineName);
		    	if(record.org=='0'){
		    		$("#airportCode").combobox('setValue', '0');
		    		$("#airportCode").attr("disabled",true);
		    	}else{
		    		initaiport(record.id,"");
		    	}
		    } 
		});
},"json");  

 $(function(){
	initaiport('${resultInfo.org}','${resultInfo.airport}');
}) 
function initaiport(org,value){
	$("#airportCode_td").html("");
	$("#airportCode_td").append('<input  id="airportCode" name="airportCode" value="'+value+'"/><input id="airName" name="airportName" type="hidden"  value="${resultInfo.airportName}"/>');
	$("#airportCode").combobox({
	     url:'<%=path%>/device/getAirport.do?airlineId='+org,
	     editable:false,
		 multiple:true,
		 valueField:'id',
		 textField:'airportName',
		 onLoadSuccess:function(){
	        	//设置默认值
	        	$('#airName').combobox('select',"${resultInfo.airport}");
	        },
	});
} 
	$('#accounta').textbox({
		width : 200,
		height : 28,
		iconCls : 'icon-man',
		iconAlign : 'right',
		prompt : '命名规范:zzhd01'
	});
	$('#password').textbox({
		width : 200,
		height : 28,
		iconCls : 'icon-lock',
		iconAlign : 'right',
	});
function validateUserForm(){
	/* var aa=$.session.get('rowKey');  */
	if($("input[name='account']").val()==""){
		$.messager.alert('提示',"账户不能为空，并且长度大于6！用户名的命名规则按照所属组织、机场、功能编号来进行；如zzkj01",'error');
		return false;
	} 
	if($("input[name='passWord']").val()==""){
			$.messager.alert('提示',"密码不能为空！",'error');
			return false;
		} 
	if($("input[name='phone']").val()==""){
			$.messager.alert('提示',"电话号不能为空，并且为11位。",'error');
			return false;
		}
	
	if($("input[name='nickName']").val()==""){
		$.messager.alert('提示',"昵称不能为空！",'error');
		return false;
	}
	if("${user.account}"!="admin"){
		if($("input[name='org']").val()==""){
			$.messager.alert('提示',"组织不能为空！",'error');
			return false;
		}   
	} 
	/*  if($.session.get('username')=='admin'){
		if($("input[name='org']").val()==""){
			$.messager.alert('提示',"组织名称不能为空！",'error');
			return false;
		}
	}   */
	if($("input[name='province']").val()=="1"){
		$.messager.alert('提示',"请勾选正确的省份",'error');
		return false;
	} 
	
	if("${user.account}"!="admin"){
		if($("input[name='org']").val()==""){
			$.messager.alert('提示',"机场不能为空",'error');
			return false;
		}  
	} 
	/* if($.session.get('username')=='admin'){
		if($("input[name='airlineCode']").val()==""){
			$.messager.alert('提示',"机场不能为空",'error');
			return false;
		}  
	} */
	 
	return true;
}
</script>
