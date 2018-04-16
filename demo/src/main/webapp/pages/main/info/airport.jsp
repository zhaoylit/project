<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<style type="text/css">

        .Form {
	width: 70%;
	margin: 40px auto;
	padding: 2% 5%;
	display: block;
}


.Form>p {
	font-size: 0.8rem;
	margin-bottom: 2%;
}

.Form>div {
	padding-top: 1%;
	padding-bottom: 1%;
}

.Form>div>label {
	/*text-align: right;*/
	font-size: 1.1rem;
}

.Form>div>input {
	width: 100%;
	height: 42px;
}

.btn {
	display: block !important;
	margin-top: 20px;
	height:30px;
	line-height:30px;
	text-align:right;
}

.Form input {
	border: solid 1px #c2cad8;
}
.btn a {
     margin-left:10%;
	background-color: #0864aa;
	color: #ffffff;
	border-radius: 5px;
	height:30px !important;
	text-align:center;
	font-size:0.8rem;
}
.btn a:hover{cursor:pointer;}
 .msg{width:100%;overflow: hidden;display: flex}
.msg_left{
   width:40%;
    float:left; 
    border-right:solid 1px #eee;
}
.msg_left form{;width:90%;}
.msg_right{
    width:60%;
    float:left;
}
.file>input{padding-left: 0px !important;height:30px !important;}
#flight_message #per_sub {
	width: 20%;
	display:inline-block;
}

#flight_message #cancel {
	width: 20%;
	display:inline-block;
}
</style>
<div class="msg">
       <div class="msg_left">
            <form id="flight_message" method="post" class="Form" enctype="multipart/form-data">
		        <p>
		             请导入.xls/xlsx格式的文件
		        </p>
		        <div class="beginTime">
		            <label for="beginTime">开始时间:</label><br/>
		            <input class="easyui-datebox" type="text" name="beginTime" id="beginTime" style="height:38px;width:100%"/>
		        </div>
		        <div class="endTime">
		            <label for="endTime">结束时间:</label><br/>
		            <input class="easyui-datebox" type="text" name="endTime"  id="endTime" style="height:38px;width:100%"/>
		        </div>
		        <div class="airport" style=""><br/>
		            <label for="Airway">航空公司:</label>
		            <input class="easyui-validatebox" type="text" name="Airway"   id="Airway" style="height:38px;width:100%;"/>
		        </div>
		        <div class="file" style=""><br/>
		            <label>计划上传的文件:</label>
		            <input class="easyui-validatebox" type="file" name="airportFile"  id="file" style="width:100%;border-radius: 4px;padding-top: 10px"/>
		        </div>
		        <div class="btn">
		            <a id="per_sub">确定导入</a>
		            <a id="cancel">取消导入</a>
		        </div>
		
		    </form>
       </div>
       <div class="msg_right">
                   <table id="AirwayList" style="width:100%;margin:0px;"></table>
       </div>
</div>
   
    <script type="text/javascript">
             
        //页面表格的初始化
           
           $(function() { 
        	  /*初始化左半边的东西*/
        	  $("#Airway").combobox({
                 url:'<%=path%>/device/getAirline.do',
                 valueField:'airlineCode',
                 textField:'airlineName'
             })
	             $("#beginTime").datebox({
	             });
	             $("#endTime").datebox({
	             });   
	         	/*  加载页面时初始化数据表格 */
	 			$('#AirwayList').datagrid({
	 		    url:'<%=path%>/info/AirportList.do',
	 		    toolbar:'#tb',
	 		    fit:true,
	 		    method:'post',
	 		    loadMsg:'数据加载中,请稍等...',
	 		    fitColumns:true,
	 		    idField:"rowKey",
	 		    pagination:true,
	 		    pageSize:10,
	 		    pageList:[5,10,15,20,100],//每页的个数  
	 		    singleSelect:false,
	 		    checkOnSelect:true,
	 		    selectOnCheck: true,
	 		    sortName:'id',
	 		    sortOrder:'desc',
	 		    queryParams: {
	 		    },
	 		    toolbar: [
	 	      	],
	 			columns:[[
					{field:'id',title:'序号',width:'10%',align:'center'},
					{field:'airportCode',title:'机场代码',width:'40%',align:'center'},
					{field:'airportName',title:'机场名称',width:'40%',align:'center'},
					{field:'alId',title:'航空公司序号',width:'10%',align:'center'},
					
	 		    ]],
	 		    onBeforeLoad:function(data){
	 		    	//添加搜索条件
	 		        if($(".searchBox").length == 0){
	 			        var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:40px;line-height:30px;">';
	                     searchTool+='<td>机场:&nbsp;<input id="airport" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
	                    // searchTool+='<td>开始日期:&nbsp;<input id="dateStart" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
	                    // searchTool+='<td>结束日期:&nbsp;<input id="dateEnd" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
	 			        searchTool+='<td><a id="search" onclick="reloadTable()" style="width:10%;height:32px">查询</a></td>';
	 			        searchTool+='</div>';
	 			        $(".datagrid-toolbar").append(searchTool);
	 			        //初始化地区选择combobox
	 			        	
	 			        	$('#airport').textbox({  
	 					    }); 
	 			        	/* $('#dateStart').datetimebox({  
	 					    }); 
	 			        	$('#dateEnd').datetimebox({  
	 					    }); */ 
	 			        	$('#search').linkbutton({
	 			        		iconCls:'icon-search'
	 			        	});
	 			       
	 		        }
	 		    },
	 		    onLoadSuccess:function(data){  
	 		    	if(data.result=='0'){
	    	    		$.messager.alert('提示',data.message,'error');
	    	    		return;
	    	    	}
	 		    	//初始化表格提示插件
	 		        $('#AirwayList').datagrid('doCellTip', {
	 		            onlyShowInterrupt : true,
	 		            position : 'bottom',
	 		            maxWidth : '200px',
	 		            specialShowFields : [{
	 		                field : 'status',
	 		                showField : 'statusDesc'
	 		            }],
	 		            tipStyler : {
	 		                'backgroundColor' : '#fff000',
	 		                borderColor : '#ff0000',
	 		                boxShadow : '1px 1px 3px #292929'
	 		            }
	 		        });
	 		    }
	 		}); 
	 		      // initPage($('#AirwayList'));
	 	});
	 	//刷新表格
	 	function reloadTable(){
	 		//toHomePage();
	 		 $('#AirwayList').datagrid('reload', {
	     		airportName:$("#airport").val()
	  		}); 
	 	}
        

        <!-----在进行提交表单之前进行判断-->
          $("#per_sub").click(function(){
//              提交之前做的表单验证

               var beginTime=$("#beginTime").parent().find("input[name='beginTime']").val();
               var endTime=$("#endTime").parent().find("input[name='endTime']").val();
               var airport=$("#Airway").parent().find("input[name='Airway']").val();
               var file=$("#file").val()
              if(beginTime==''){
                  $.messager.alert('提示','请输入开始时间！');
                  return false;
              }
              if(endTime==''){
                  $.messager.alert('提示','请输入结束时间！');
                  return false;
              }
              if(Airway==''){
                  $.messager.alert('提示','请选择航空公司！');
                  return false;
              }
              if(file==''){
                  $.messager.alert('提示','请选择要上传的文件！');
                  return false;
              }
              
              var $form = $("#flight_message");
              if($form.form('validate')){
                  $.ajaxFileUpload({
                      type:'post',
                      async: 'false',
                      data:{
                    	  
                      },
                      url:'<%=path%>/info/uploadairport.do',
                      secureuri: false, //一般设置为false
                      fileElementId: 'file', // 上传文件的id、name属性名
                      success: function(data, status){
                    	 var obj = $(data).removeAttr("style").prop("outerHTML");
                      	 data =obj.replace("<PRE>", '').replace("</PRE>", '').replace("<pre>", '').replace("</pre>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
                      	 var json = JSON.parse(data);
                          if(json.result == "1"){
                              $.messager.alert('提示',json.message,'info', function(r){
                                  reloadTable();
                              });
                          }else{
                              $.messager.alert('操作失败',data.message,'error');
                          }
                      },
                      dataType:'JSON'
                  });
              }else{
                  $.messager.alert('提示',"请填写完整的表单数据",'error');
              }
          });
    </script>