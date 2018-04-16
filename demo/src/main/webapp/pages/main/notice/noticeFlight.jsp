<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<style type="text/css">
  .flight_total {
	width: 100%;
	display:flex;
}
  .flight_left{width:40%;}
  .flight_right{width:60%;}
  
  
  
  /*--------form表单的样式修改-------*/
.Form {
	width: 70%;
	margin: 40px auto;
	padding: 2% 5%;
	display: block;
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
	display:block;
	width:100% !important;
	text-align:right;
}
   .btn a {
	padding:0 2%;
	background-color: #0864aa;
	color: #ffffff;
	border: solid 1px #0864aa;
	border-radius: 5px;
	height:30px;
	line-height:30px;
}
#per_sub, #cancel{
    display:inline-block;
    height:30px;
    line-height:30px;
    margin-left:3%;
}
.Form input {
	border: solid 1px #95b8e7;
}
#notice_flight {
	width: 90%;
	padding: 0px;
	margin: 0px auto
}



.flight_left input {
	display: block;
	width: 100% !important;
	padding: 0px !important;
	border-radius: 8px !important;
	margin-top: 15px;
}

#notice_flight input {
	display: block;
	width: 100% !important;
	padding-left: 0px !important;
	border-radius: 8px !important;
	margin-top: 10px;
}

#notice_flight label {
	display: block;
}

#notice_flight>div {
	margin-bottom: 2%;
}
#per_sub:hover, #cancel:hover {
	cursor: pointer;
}
.reason {
	width: 100%;
	overflow: hidden;
	display: flex;
	display: -webkit-flex;
}

.reason_left {
	width: 48%;
	float: left;
}
     .reason_left .textbox{
        width:100% !important;
     }
.reason_right {
	width:48%;
	float: left;
	margin-left: 2%;
} 
    .reason_right .textbox{
        width:100% !important;
     }

.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber
  {
      text-overflow: ellipsis;
  }
</style>

<div class="flight_total">
    <div class="flight_left">
        <form id="notice_flight" method="post" class="Form">
            <div class="">
                <label>航班号:</label><br/>
                <input class="easyui-validatebox" id="flightNo" type="text" name="flightNo"  style="width:100%;height:42px;padding: 0px" />
            </div>
            <div class="">
                <label>航班状态:</label><br/>
                <select name="fs" id="fs" class="easyui-combobox" style="width:100%;height:42px;">
                    <option value="00">----请选择航班状态-----</option>
                    <option value="01">开始登机 </option>
                    <option value="02">登机口变更 </option>
                    <option value="03">航班延误 </option>
                    <option value="04">航班取消 </option>
                </select>
            </div>
            <div class="reason">
                 <div class="reason_left">
                     <label>请选择延误原因:</label><br/>
                     <select name="delayRes" list="" id="delayRes" class="easyui-combobox" style="width:100%;height:34px;">
                         <option value="C01">航路天气 </option>
                         <option value="C02">周边天气 </option>
                         <option value="C03">本场天气 </option>
                         <option value="C04">流量控制 </option>
                     </select>
                 </div>
                <div class="reason_right">
                    <label>Flight reason:</label><br/>
                    <select name="resa_En" list="" id="resa_En" class="easyui-combobox" style="width:100%;height:34px;">
                        <option value="E01">route weather</option>
                        <option value="E02">surrounding weather</option>
                        <option value="E03"> local weather</option>
                        <option value="E04">air traffic control</option>
                    </select>
                </div>
            </div>
            <div class="">
                <label>出发地:</label>
                <input class="" type="text" name="depCity" id="depCity" style="border-color:#95b8e7;"/>
                <input class="" type="hidden" name="depCode" id="depCode" />
                <input class="" type="hidden" name="takeoffTime" id="takeoffTime" />
            </div>
            <div class="">
                <label>经停地:</label>
                <input class="easyui-validatebox" type="text" name="passCity" id="passCity" style="border-color:#95b8e7;"/>
                 <input class="" type="hidden" name="passCode" id="passCode" />
            </div>
            <div class="">
                <label>目的地:</label>
                <input class="easyui-validatebox" type="text" name="arrCity" id="arrCity" style="border-color:#95b8e7;"/>
                 <input class="" type="hidden" name="arrCode" id="arrCode" />
            </div>
            <div class="">
                <label>登机口:</label>
                <input class="easyui-validatebox" id="gate" type="text" name="gate"  style="width:50%;height:42px;padding: 0px" />
            </div>
            <div class="stayTime" style="display:none;">
                
                <input class="easyui-validatebox" type="text" name="count" id="count" style="width:100%;height:36px;">
            </div>
            <div class="btn">
                <a id="per_sub">确定发送</a>
                <a id="cancel">取消发送</a>
            </div>
            <input type="hidden" name="isFlush" value="0"/>
            
        </form>
    </div>
    
      <!--用来存放所有的城市字段的  -->
      <input type="hidden" id="str">
    <div class="flight_right">

        <table id="right_table"   pagination="true"  style="width:100%;"></table>
    </div>
 </div>
    <script type="text/javascript">
    
 
    
    
    
    $(function() {
    	$('#count').numberspinner({
		    min:1,
		    max: 60,
		    value:15,
		    editable:true
		});
    $(".flight_right").css("height",$(document).height()*0.7);
    
	 $(".reason").hide();
    // ----------关于航班号的显示
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
   		  }else{		
   			 $.ajax({
		            type:'post',
		            dataType:'json',
		            url:'notice/getOneFlightPlan.do',  //航班提醒的接口
		            data:{flightNo:record.flightNo},
		            success:function(data){
		   		            	  
	             		var flightNo=data.flightNo; 		
	            		var fs=data.fs;		
	            		var delayRes=data.delayRes;		
	            		var depCity=data.depCity; 		
	            		var passCity=data.passCity;		
	            		var arrCity=data.arrCity;		
	            		var takeoffTime = data.takeoffTime;            
		      		    
		      			$("#depCity").val(depCity); 
		        		$("#passCity").val(passCity);
		        		$("#arrCity").val(arrCity);
		        		$("#takeoffTime").val(takeoffTime);
		        		//$('#delayRes').combobox('setValue', delayRes);
		        		//$('#resa_En').combobox('setValue', null);
		        		$('#fs').combobox('setValue', fs);
		        		$("#flightNo").combobox('setValue', flightNo);
		        		$("#gate").val('');    		 
		      		  
		            } 
		            
		        })  
   			  
   		  }		 
   	  }
    	
    });
   
   
   /*  $("#gate").combobox({
        url:'notice/getFlightPlan.do',
        valueField:'fightNo',
        textField:'depCity'
    }); */
   //  时间微调器的应用
   


  // 选中航班延误的时候出现原因的效果
    $("#fs").combobox({
        onChange:function (n,o) {
        	console.log(n);
            if(n=='03'){
                $(".reason").show();
                $(".stayTime").hide();
            }else{
                $(".reason").hide();
                $('#delayRes').combobox('setValue', null);
              $('#resa_En').combobox('setValue', null);
                   
            }

        }

    });
  
  //对于航班延误的一些样式的修改
  
     $("#delayRes").combobox({
   	  onSelect:function(record){
   		  var value=record.value;
   		  if(value=="C01"){
   			  $("#resa_En").combobox('select', 'E01');
   		  }
   		  if(value=="C02"){
   			  $("#resa_En").combobox('select', 'E02');
   		  }
   		  if(value=="C03"){
   			  $("#resa_En").combobox('select', 'E03');
   		  }
   		  if(value=="C04"){
   			  $("#resa_En").combobox('select', 'E04');
   		  }
   	  }
      });
     $("#resa_En").combobox({
   	   onSelect:function(record){
   		  var value=record.value;
   		  if(value=="E01"){
   			  $("#delayRes").combobox('select', 'C01');
   		  }
   		  if(value=="E02"){
   			  $("#delayRes").combobox('select', 'C02');
   		  }
   		  if(value=="E03"){
   			  $("#delayRes").combobox('select', 'C03');
   		  }
   		  if(value=="E04"){
   			  $("#delayRes").combobox('select', 'C04');
   		  }
   	  }
     });
     
   //点击取消发送的时候置为空
     $("#cancel").click(function(){
    	 $("#flightNo").combobox('setValue', null);
 		$("#depCity").val(''); 
 		$("#passCity").val('');
 		$("#arrCity").val('');
 		$('#delayRes').combobox('setValue', null);
 		$('#resa_En').combobox('setValue', null);
 		$('#fs').combobox('setValue', null);
 		$("#gate").val('');  
     });
    //  点击确定发送的时候进行的表单验证
    $("#per_sub").click(function(){
        var flightNo=$("#flightNo").parent().find("input[name='flightNo']").val();
        var fs=$("input[name='fs']").val();
        if(flightNo==''){
            $.messager.alert('提示','请输入航班号');
            return false;
        }
        if(fs=='00'||typeof(fs)=='undefined'){
            $.messager.alert('提示','请选择航班状态');
            return false;
        }
        //进行form表单提交
        var $notice_flight=$("#notice_flight");
        $.ajax({
            type:'post',
            dataType:'json',
            url:'notice/pushFlightNotice.do',  //航班提醒的接口
            data:$notice_flight.serialize(),
            success:function(data){
                if(data.result == "1"){
                    $.messager.alert('提示',data.message,'info', function(r){
                        //dialog.dialog('destroy');
                    });
                    
                    $('#right_table').datagrid('reload');
                    $("#flightNo").combobox('setValue', null);
            		$("#depCity").val(''); 
            		$("#passCity").val('');
            		$("#arrCity").val('');
            		$('#delayRes').combobox('setValue', null);
            		$('#resa_En').combobox('setValue', null);
            		$('#fs').combobox('setValue', null);
            		$("#gate").val('');  
            		
                    
                }else{
                    $.messager.alert('提示',data.message,'error');
                }
            } 
            
        })
    })
     // 创建数据表格
    $('#right_table').datagrid({
		   	 width:'100%',
		   	 height:'100%',
   	       url:'<%=path%>/notice/getNoticeFlight.do',
		    toolbar:'#tb',
		    fit:true,
		    singleSelect:true,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    method:'post',
		    loadMsg:'数据加载中,请稍等...',
		    fitColumns:true,
		    idField:"flightNo",
		    pagination:true,
		    //pageSize:10,
		    //pageList:[5,10,15,20,100],//每页的个数  
		    singleSelect:true,
		    checkOnSelect:true,
		    selectOnCheck: true,
		    sortName:'id',
		    sortOrder:'desc',
		    queryParams: {
		    },
		    toolbar: [
				{
			  	text:'刷新',
					iconCls: 'icon-reload',
			  	    height:'40',
					width:'100',
					handler: function(){
						 reloadTable('');
				    }
				}     
	      	],
       columns:[[
           {field:'flightNo',title:'航班号',width:'10%',align:'center'},
           {field:'fs',title:'航班状态',width:'10%',align:'center',formatter:formatStatus},
           {field:'delayRes',title:'延误原因',width:'10%',align:'center',formatter:formatReason},
           {field:'depCity',title:'出发地',width:'8%',align:'center'},
           {field:'passCity',title:'经停地',width:'8%',align:'center'},
           {field:'arrCity',title:'目的地',width:'8%',align:'center'},
           {field:'gate',title:'登机口',width:'8%',align:'center'},
           {field:'status',hidden:true},
           {field:'takeoffTime',hidden:false,title:'起飞时间',width:'10%',align:'center',formatter:function(value,row,index){
        	   var str=row.takeoffTime;
        	   console.log(str);
        	   var temp=str.substr(0,2)+":"+str.substr(2);
        	   return (temp);
           }},
           {field:'createTime',title:'发布时间',width:'10%',align:'center'},
           {field:'sendState',title:'发布状态',width:'10%',align:'center',formatter:formatSendState},
           {field:'operator',title:'操作',width:'10%',align:'center',formatter:function(value,row,index){
           	if(row.status=='0'){
           		$("#right_table").datagrid('selectRecord',row.flightNo);  
           		return '<a class="forbid" href="javascript:forbid();"></a>';
           	}else if(row.status=='1'){
           		return '<a class="change" href="javascript:editstatus(\''+row.flightNo+'\');"></a>';
           	}             
           }},
       ]],
       
        rowStyler: function(index,row){
            if (row.status=='0'){
                return 'background-color:#eeeeee;'; 
            }
        },
        onLoadSuccess:function(data){
        	if(data.result=='0'){
	    		$.messager.alert('提示',data.message,'error');
	    		return;
	    	}
/*         	if(data.preRowKey != ""){
		    	preRowKey = data.preRowKey;
		    	nextRowKey = data.nextRowKey;
	    	}else{
	    		preRowKey = nextRowKey;
	    	} */
	    	//初始化编辑按钮
	        $('.change').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	       $('.forbid').linkbutton({text:'禁止',plain:true,iconCls:'icon-cancel'});
	    	//初始化表格提示插件
/* 	        $('#right_table').datagrid('doCellTip', {
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
	        }); */
        
	    }
    });
  //  initPage($('#right_table'));
    });	
    	//刷新表格
		function reloadTable(){
			
			 $('#right_table').datagrid('reload');
		}
    	//禁止修改的方法
    	function forbid(){
    		$.messager.alert('提示','该状态为无效信息，禁止修改！');
    	}

    	// 修改状态
    	function editstatus(id){
    		var checkedItems = $('#right_table').datagrid('getSelected');
    		
    		var EnCode='';
    		var flightNo=checkedItems.flightNo; 		
    		var fs=checkedItems.fs;		
    		var delayRes=checkedItems.delayRes;		
    		var depCity=checkedItems.depCity; 		
    		var passCity=checkedItems.passCity;		
    		var arrCity=checkedItems.arrCity;		
    		var gate=checkedItems.gate;
    		var time=checkedItems.takeoffTime;
    		
    		
    		//alert(time);
    		if(delayRes=="C01"){
    			EnCode="E01"			
    		 }
    		if(delayRes=="C02"){
    			EnCode="E02"			
    		 }
    		if(delayRes=="C03"){
    			EnCode="E03"			
    		 }
    		if(delayRes=="C04"){
    			EnCode="E04"			
    		 }
    		$("#flightNo").combobox('setValue', flightNo);
    		$("#depCity").val(depCity); 
    		$("#passCity").val(passCity);
    		$("#arrCity").val(arrCity);
    		$('#delayRes').combobox('setValue', delayRes);
    		$('#resa_En').combobox('setValue', EnCode);
    		$('#fs').combobox('setValue', fs);
    		
    		
    		$("#gate").val(gate);  
    		$("#takeoffTime").val(time);
    		
    		
            
    	}
		function formatStatus(val, row) {
			if (val != null) {
				switch(val){
				case '01':
					return "开始登机";
					break;
				case "02":
					return "登机口变更";
					break;
				case "03":
					return "航班延误";
				case "04":
					return "航班取消";
				}
			}
		}
		
		function formatReason(val, row) {
			if (val != null) {
				switch(val){
				case 'C01':
				case 'E01':
					return "航路天气";
					break;
				case "C02":
				case "E02":	
					return "周边天气";
					break;
				case "C03":
				case "E03":
					return "本场天气";
				case "C04":
				case "E04":	
					return "流量控制";
				}
			}
		}
		
		function formatSendState(val,row){
			if (val != null) {
				switch(val){
				case "1":
					return "发布成功";
					break;
				case "0":
					return "发布失败";
					break;
				default:
					return "发布成功";
				}
			}
			
		}
		
		//校验输入的城市字段是否是空     1.先把城市的json数据转化为字符串
		   var temp='';
	      $.ajax({
	          url:'<%=path %>/javascript/js/city.json',
	          async:false,
	          type:'post',
	          dataType:'json',
	          success:function(data){
	              for(var i=0;i<data.length;i++){
	                  temp+=data[i].name+'-';
	              }
	          }
	      })
	      console.log(temp);
	      $("#str").val(temp);
		//2.在每个输入框输入完 失去焦点的时候去判断   下面是判断出发地的
		 var str=$("#str").val();
		$("#depCity").blur(function(){
			var  depstr=$("#depCity").val();
			if(str.indexOf(depstr)==-1){
				$.messager.alert('提示','请输入合法的城市名');
					$("#depCity").val("");
			}
		});
		//下面是判断经停地的
		$("#passCity").blur(function(){
			var  depstr=$("#passCity").val();
			if(str.indexOf(depstr)==-1){
				$.messager.alert('提示','请输入合法的城市名');
					$("#passCity").val("");
			}
		});
		// 下面是判断目的地的
		$("#arrCity").blur(function(){
			var  depstr=$("#arrCity").val();
			if(str.indexOf(depstr)==-1){
				$.messager.alert('提示','请输入合法的城市名');
					$("#arrCity").val("");
			}
		});
		
		//当选中航班延误的时候，停留时间的字段被隐藏
		
    </script>
