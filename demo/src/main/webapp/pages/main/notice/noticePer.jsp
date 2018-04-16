<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">
      
</style>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/css/page/main.css">
<div style="">
    <form id="notice" method="post" class="Form" style="margin-top:10px;">
        <div class="">
            <label>航班号:</label><br/>
            <input class="easyui-validatebox" id="flightNo" type="text" name="flightNo"  style="width:100%;height:46px;padding: 0px;" />
        </div>
        <div class="">
            <label>提醒(乘客姓名):</label><br/>
            <input class="" type="text" name="name" id="name" style="height:38px;border-color:#95b8e7;"/>
        </div>
        <div class="">
            <label>提醒内容:</label><br/>
            <input class="" type="text" name="msg" id="msg" style="height:38px;border-color:#95b8e7;" maxlength="20" placeholder="最多可输入20个字符"/>
        </div>
        <div class="btn">
            <a id="per_sub">确定发送</a>
            <a id="cancel" style="margin-left:5%;">取消发送</a>
        </div>

    </form>
 </div>
    <script type="text/javascript">
   
    $(function() {
    $("#flightNo").combobox({
        url:'notice/getFlightPlan.do',
        valueField:'flightNo',
        textField:'flightNo'
    });
    $("#flightNo").combobox({
        onSelect:function(record){
   		  var value=record.flightNo;   		
   		  if(value=="显示更多"){
   			$('#flightNo').combobox('clear');
   			$('#flightNo').combobox('reload','notice/getFlightPlan.do?more=Y'); 
   		  }else if(value=="最近十条"){
   	   			$('#flightNo').combobox('clear');
   	   			$('#flightNo').combobox('reload','notice/getFlightPlan.do?more=N'); 	
   		  } 
   	  }	
    });
    }); 
    //点击取消发送的时候置为空
     $("#cancel").click(function(){
    	 $("input").val("");
     });
    $("#per_sub").click(function(){
      //  点击的时候所做的判断 就是表单验证
          var per_num=$("#flightNo").parent().find("input[name='flightNo']").val();
          var per_not=$("#name").parent().find("input[name='name']").val();
          var per_ctx=$("#msg").parent().find("input[name='msg']").val();
          if(per_num==''){
              $.messager.alert('提示','请输入航班号');
              return false;
          }
          if(per_not==''){
              $.messager.alert('提示','请输入旅客姓名');
              return false;
          }
          if(per_ctx==''){
              $.messager.alert('提示','请输入要提醒的内容');
              return false;
          }

        // ajax的form表单提交
          var $notice=$("#notice");
          $.ajax({
              type:'post',
              dataType:'json',
              url:'notice/pushPersonNotice.do',  //找人的接口
              data:$notice.serialize(),
              success:function(data){
                  if(data.result == "1"){
                      $.messager.alert('提示',data.message,'info', function(r){
                          dialog.dialog('destroy');
                          
                      });
                      $("input").val("");
                  }else{
                      $.messager.alert('操作失败',data.message,'error');
                  }
              },
              dataType:'json'
          })

      });
    </script>