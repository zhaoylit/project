<%@ page language="java" import="com.zj.web.common.utils.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String returnPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
%>
<script type="text/javascript">
$('#businessList').datagrid({
    url:'<%=path%>/shop/selectBusinessList.do',
    idField:'id',
    treeField:'funName',
    pagination:true,
    pageSize:10,
    pageList:[5,10,15,20,100],//每页的个数
    singleSelect:true,
    selectOnCheck: true,
    lines:true,
	iconCls: 'icon-ok',
	rownumbers: true,
	animate: true,
	fitColumns: true,
    toolbar: [
               {}
	      	],
    columns:[[
		{title:'',field:'imgPath',width:'10%',formatter:function(value,row,index){
			return '<img style="width:50px;height:50px;" src="<%=returnPath%>'+row.photo+'"/>';
		}},
		{title:'真是姓名',field:'trueName',width:'20%'},
		{title:'手机号码',field:'mobile',width:'20%'},
		{title:'邮箱',field:'email',width:'20%'},
    ]],
    onBeforeLoad:function(data){
    	//添加搜索条件
        if($(".searchBox_").length == 0){
	        var searchTool = '<div class="searchBox_" style="padding:3px 0 0 25px;height:30px;line-height:30px;">';
	        searchTool+='<input id="keyWords" style="width:150px;height:30px;">';
	        searchTool+='&nbsp;&nbsp;<a id="search" onclick="reloadTable()" style="width:80px;height:30px;">搜索</a> ';
	        searchTool+='</div>';
	        $('#businessList').find(".datagrid-toolbar").append(searchTool);
        }
    },
    onLoadSuccess:function(data){
    	$('.searchBox_').find("#keyWords").textbox({
			 prompt:'请输入搜索关键字',
		 });
    	$('.searchBox_').find('#search').linkbutton({
        	iconCls:'icon-search', 
         });
    }
});
function reloadTable1(){
	$('#businessList').datagrid('load',{});
}
</script>
<table id="businessList" style="width:100%;height:100%;"></table>