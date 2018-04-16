var startRowKey="",preRowKey = "",nextRowKey = "",pSize = "10",prePage = 1,curPage = 1,queryParam = null;
var array = new Array();
var tableList;
function initPage(obj){
	tableList = obj;
	pSize = "10";
	var selectHtml = '&nbsp;<select id="pageSelect" style="width:70px;float:left;"></select>';
	var linkButtons = '&nbsp;<a href="javascript:void();" onclick="toHomePage()">首页 </a>';
	    linkButtons +='&nbsp;<a href="javascript:void();" onclick="toLastPage()">上一页 </a>';
	    linkButtons +='&nbsp;<a href="javascript:void();" onclick="toNextPage()">下一页 </a>';
	var curPageInfo = '&nbsp;<span id="curPageInfo">当前第'+curPage+'页</span>';
	$(".datagrid-pager").html(selectHtml+linkButtons+curPageInfo);
	$(".datagrid-pager").css({"padding-top":"5px"});
	$("#pageSelect").combobox({
		data:[
		      {pageSize:2,pageValue:2},
		      {pageSize:5,pageValue:5},
		      {pageSize:10,pageValue:10},
		      {pageSize:50,pageValue:50},
		      {pageSize:100,pageValue:100}
		     ],  
        editable:false,
        valueField:'pageSize', 
        textField:'pageValue',  
        onSelect:function(data){
        	changePageSize(data.pageSize);
        },
        onLoadSuccess:function(){
        	//设置默认值
        	$('#pageSelect').combobox('select',10);
        }
	});
}
function changePageSize(pageSize){
	startRowKey = "";
	preRowKey = "";
	nextRowKey = "";
	curPage = 1;
	pSize = pageSize;
	changeQueryParam();
	tableList.datagrid('load',queryParam);
	array.splice(0,array.length);
	$("#curPageInfo").html("当前第"+curPage+"页");
}
function toHomePage(){
	startRowKey = "";
	preRowKey = "";
	nextRowKey = "";
	curPage = 1;
	$("#curPageInfo").html("当前第"+curPage+"页");

	changeQueryParam();
	tableList.datagrid('load',queryParam);
	$("#curPageInfo").html("当前第"+curPage+"页");
}
function toLastPage(){
	startRowKey = getLastRowKey(array,preRowKey);
	if(startRowKey  != preRowKey){
		curPage--;
	}
	changeQueryParam();
	tableList.datagrid('load',queryParam);
	$("#curPageInfo").html("当前第"+curPage+"页");
}
function toNextPage(){
	startRowKey = nextRowKey;
//	alert(startRowKey+"---" + preRowKey + "----" + nextRowKey);
	if(startRowKey != "" && startRowKey != preRowKey){
		curPage++;
	}
	if(!checkExist(array,preRowKey)){
		array[array.length] = preRowKey;
	}
	if(!checkExist(array,nextRowKey)){
		array[array.length] = nextRowKey;
	}
	changeQueryParam();
	tableList.datagrid('load',queryParam);
	$("#curPageInfo").html("当前第"+curPage+"页");
}
function checkExist(array,str){
	if(str == ""){
		return true;
	}
	if(array.length == 0){
		return false;
	}
	for(var i = 0; i < array.length;i++){
		if(array[i] != str){
			continue;
		}else{
			return true;
		}
	}
	return false;
}
function getLastRowKey(array,preRowKey){
	if(preRowKey == "" || array.length == 0){
		return "";
	}else{
		var cur_index = 0;
		for(var i = 0;i < array.length;i++){
			if(array[i] == preRowKey){
				cur_index = i;
			}
		}
		return array[cur_index - 1 > 0 ? cur_index - 1 : 0]
	}
}
function changeQueryParam(){
	var jsonStr = "{";
	var inputObj = $(".datagrid-toolbar").find(".searchBox").find(".textbox-f");
//	alert(inputObj.prop("outerHTML"));
	inputObj.each(function(index){
		jsonStr+= ',"'+$(this).attr("id") + '"' +":"+ '"' + $(this).textbox('getValue') + '"';
	});
	jsonStr +=',"pageSize":' + '"'+pSize+'"';
	jsonStr +=',"startRowKey":' + '"' + startRowKey + '"';
	jsonStr +="}";
	jsonStr = jsonStr.replace(jsonStr.substr(1,1),"");
	queryParam = $.parseJSON(jsonStr);
}

