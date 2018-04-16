<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.zkkj.backend.common.util.*"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
    $(function() {
        /*  加载页面时初始化数据表格 */
        $('#logList').datagrid({
            title:'广告播放日志查询页面',
            url:'<%=path%>/playLog/playLogList.do',
            toolbar:'#tb',
            fit:true,
            method:'post',
            loadMsg:'数据加载中,请稍等...',
            fitColumns:true,
            idField:"id",
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
            toolbar: [],
            columns:[[
                /* {field:'airport',title:'机场/频次',width:'6%',align:'center',formatter:function(value,row,index){
					return '<a class="airportView"  id="'+row.rowKey+'" adId="'+row.adId+'" href="javascript:void();">查看</>';
				}}, */
				//{field:'rowKey',title:'rowKey',width:'12%',align:'center'},
				{field:'airportName',title:'机场',width:'12%',align:'center'},
                {field:'viproomName',title:'VIP厅',width:'8%',align:'center'},
                {field:'deviceNo',title:'设备编号',width:'12%',align:'center'},
                {field:'deviceIp',title:'设备IP',width:'11%',align:'center'},
                {field:'advertiserName',title:'客户名称',width:'11%',align:'center'},
                /* {field:'',title:'播放频次',width:'10%',align:'center'}, */
                {field:'resourceType',title:'广告播放形式',width:'8%',align:'center',formatter:function(value,row,index){
					if(row.resourceType=='1'){
						return '图片广告';
					}else if(row.resourceType=='2'){
						return '视频广告';
					}
				}},
                {field:'beginTime',title:'开始时间',width:'15%',align:'center'},
                {field:'endTime',title:'结束时间',width:'15%',align:'center'},
                {field:'operator1',title:'广告预览',width:'8%',height:'200px',align:'center',formatter:function(value,row,index){
					//return '<a class="preview" href="javascript:MediaLook(\''+row.adtype+'\',\''+row.rowKey+'\',\''+row.auditStatus+'\');"></a>';
                	if(row.resourceType=='1'){
                		return '<img style="width:60px;height:80px" src="<%=realPath%>'+row.filePath+'">';
					}else if(row.resourceType=='2'){
						return '<video style="width:100px;height:80px" src="<%=realPath%>'+row.filePath+'">';
					}
                	
				}},
            ]],
            onBeforeLoad:function(data){
            	$('.preview').css({ "height": "100px", "width": "100px" });
            	//添加搜索条件
		        if($(".searchBox").length == 0){
			        var searchTool = '<div class="searchBox" style="padding:3px 0 0 15px;height:40px;line-height:30px;">';
			        searchTool+='航空公司:&nbsp;<input id="airlineCode" style="width:10%;height:30px;">&nbsp;&nbsp;';
			        searchTool+='机场名称:&nbsp;<input id="airportCode" style="width:10%;height:30px;">&nbsp;&nbsp;';
		            searchTool+=' VIP&nbsp;&nbsp;厅:&nbsp;<input id="viproom" name="viproom" style="width:10%;height:30px;" />&nbsp;&nbsp;'; 
			        searchTool+='广告商:&nbsp;<input id="advertiserName" style="width:10%;height:30px;"/>&nbsp;&nbsp;&nbsp;';
			        searchTool+='设备号:&nbsp;<input id="deviceNo" style="width:10%;height:30px;">&nbsp;&nbsp;&nbsp;';
			        searchTool+='设备IP:&nbsp;<input id="deviceIp" style="width:10%;height:30px;">&nbsp;&nbsp;&nbsp;';
			        /* searchTool+='开始日期:&nbsp;<input id="dateStart" style="width:15%;height:30px;">&nbsp;&nbsp;';
                    searchTool+='结束日期:&nbsp;<input id="dateEnd" style="width:15%;height:30px;">&nbsp;&nbsp;&nbsp;'; */
			        searchTool+='<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
			        searchTool+='</div>';
			        $(".datagrid-toolbar").append(searchTool);
			        $("#deviceNo").textbox({});
			        $("#deviceIp").textbox({});
			        $("#advertiserName").textbox({});
			      		//初始化地区选择combobox
			      		$('#airportCode').combobox({});
			       		$('#viproom').combobox({});
				        $.post("<%=path%>/device/getAirline.do",{},function(data){ 
			        		var aaa=$("#airportCode");
			        		data.splice(0,0,{airlineCode:"",airlineName:"全部"});   
			        		$("#airlineCode").combobox({
			        		     data:data,
			        			 editable:true,
			        			 valueField:'airlineCode',
			        			 textField:'airlineName',
			        		    	onSelect:function(record){ 
				        		    	if(record.airlineCode==''){
				        		    		$("#airportCode").combobox('setValue','');
				        		    		$("#viproom").combobox('setValue','');
				        		    	}else{
				        		    		initaiport(record.airlineCode,"");
			        		    		}
			        		    } 
			        		});
			        },"json"); 
					//初始活动类型选择combobox
			        $('#search').linkbutton({
			        	iconCls:'icon-search', 
			        });
		        }
		    },
            onLoadSuccess:function(data){
                
            }
        });
        //initPage($('#logList'));
    });
    
    function reloadTable(){
		$('#logList').datagrid('load',{
			exceptionState:$('#advertiserName').val(),
			airlineCode:$('#airlineCode').combobox('getValue'),
			airportCode:$('#airportCode').combobox('getValue'),
			viproomId:$('#viproom').combobox('getValue'),
			deviceId:$('#deviceNo').val(),
			deviceIp:$('#deviceIp').val(),
		});
	}
    
    function initaiport(airlineCode,value){
		$("#airportCode").combobox({
			url:'<%=path%>/device/getAirportByAirlineCode.do?airlineCode='+airlineCode,	
			valueField:'airportCode',
			textField:'airportName',
	        onSelect:function(record2){
		    	if(record2.airportCode==''){
		    		$("#viproom").combobox('setValue','');
		    	}else{
	    			$("#viproom").combobox({
	    			     url:'<%=path%>/device/getViproomByAirportCode.do?airportCode='+record2.id,
	    				 valueField:'id',
	    				 textField:'viproomName',
	    				 onLoadSuccess:function(){
	    			        	//设置默认值
	    			        },
	    			});
		   		} 
	         }
		});
	}

    // 所有调取的方法都要在这里以下写
    function initviproomId(airportCode,value){
		$("#viproomId_td").html("");
		$("#viproomId_td").append('<input  id="viproomId" name="viproomId" value="'+value+'"/><input id="viproom" name="viproom" type="hidden"  value="${resultInfo.viproom}"/>');
		$("#viproom").combobox({
		     url:'<%=path%>/device/getViproomByAirportCode.do?airportCode='+airportCode,
		     editable:false,
			 multiple:false,
			 valueField:'viproomId',
			 textField:'viproom',
			 onLoadSuccess:function(){
		        	//设置默认值
		        	$('#viproom').combobox('select',"${resultInfo.viproomId}");
		        },
		});
	}
</script>
<table id="logList" style="width:100%;margin:20px;"></table>
</body>
</html>