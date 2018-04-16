<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/link.jsp"%>
<%@include file="/script.jsp"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<style type="text/css">
</style>
<script type="text/javascript">
function addTab(title, url,iconCls,isGroup){
	if ($('#menu_tabs').tabs('exists', title)){
		$('#menu_tabs').tabs('select', title);
	} else {
		if(isGroup == 1){
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			$('#menu_tabs').tabs('add',{
				title:title,
				content:content,
				closable:true,
		        iconCls:iconCls

			});
		}
	}
}
$(function(){
	$('.easyui-tree').tree({
		onClick: function(node){
			var id = node.attributes.id;
			var isGroup = node.attributes.isGroup;
			var url = node.attributes.url;
			addTab(node.text,url,node.iconCls,isGroup);
		}
	});
	bindTabEvent();    
    bindTabMenuEvent(); 
})
//绑定tab的双击事件、右键事件    
function bindTabEvent(){    
    $(".tabs-inner").live('dblclick',function(){    
        var subtitle = $(this).children("span").text();    
        if($(this).next().is('.tabs-close')){    
            $('#menu_tabs').tabs('close',subtitle);    
        }    
    });    
    $(".tabs-inner").live('contextmenu',function(e){    
        $('#mm').menu('show', {    
            left: e.pageX,    
            top: e.pageY    
     });    
     var subtitle =$(this).children("span").text();    
     $('#mm').data("currtab",subtitle);    
     return false;    
    });    
 }    
//绑定tab右键菜单事件    
function bindTabMenuEvent() {    
    //刷新
    $('#mm-tabrefresh').click(function() {    
        var tab = $('#menu_tabs').tabs('getSelected');// 获取选择的面板
        $('#menu_tabs').tabs('update', {
        	tab: tab,
        	options: {
        		
        	}
        });
 
    });    
    //关闭当前    
    $('#mm-tabclose').click(function() {    
        var currtab_title = $('#mm').data("currtab");
        $('#menu_tabs').tabs('close', currtab_title);
        /* if ($(this).next().is('.tabs-close')) {    
                
        }  */   
    });    
    //全部关闭    
    $('#mm-tabcloseall').click(function() {    
        $('.tabs-inner span').each(function(i, n) {    
            if ($(this).parent().next().is('.tabs-close')) {    
                var t = $(n).text();    
                $('#menu_tabs').tabs('close', t);    
            }    
        });    
    });    
    //关闭除当前之外的TAB    
    $('#mm-tabcloseother').click(function() {    
        var currtab_title = $('#mm').data("currtab");    
        $('.tabs-inner span').each(function(i, n) {    
            if ($(this).parent().next().is('.tabs-close')) {    
                var t = $(n).text();    
                if (t != currtab_title)    
                    $('#menu_tabs').tabs('close', t);    
            }    
        });    
    });    
    //关闭当前右侧的TAB    
    $('#mm-tabcloseright').click(function() {    
        var nextall = $('.tabs-selected').nextAll();    
        if (nextall.length == 0) {    
            alert('已经是最后一个了');    
            return false;    
        }    
        nextall.each(function(i, n) {    
            if ($('a.tabs-close', $(n)).length > 0) {    
                var t = $('a:eq(0) span', $(n)).text();    
                $('#menu_tabs').tabs('close', t);    
            }    
        });    
    });    
    //关闭当前左侧的TAB    
    $('#mm-tabcloseleft').click(function() {    	
        var prevall = $('.tabs-selected').prevAll();  
        if (prevall.length == 1) {    
            alert('已经是第一个了');    
            return false;    
        }    
        prevall.each(function(i, n) {    
            if ($('a.tabs-close', $(n)).length > 0) {    
                var t = $('a:eq(0) span', $(n)).text();    
                $('#menu_tabs').tabs('close', t);    
            }    
        });    
    });    
}  
function quit(){
	$.messager.confirm("确认信息","确定退出登录吗？", function (r) {  
        if (r){  
        	window.location.href="${ctx}/quit.do";
        }  
    });  
}
</script>
</head>
<body class="easyui-layout">
	<!-- 顶部banner -->
    <div id="header" data-options="region:'north'," style="height:80px;">
	    <div style="width:400px;float:right;text-align:right;padding:45px 20px 10px 0;">
    		<span style="font-size:15px;"><span style="color:red;">${loginUser.userName }</span>，你好！</span>
    		<img title="修改密码" src="${ctx }/easyui/themes/icons/changePwd.png" style="cursor:pointer;"/>
    		<img title="退出登录" onclick="quit();" src="${ctx }/easyui/themes/icons/quit.png" style="cursor:pointer;margin-left:10px;"/>
    		<img title="帮助" src="${ctx }/easyui/themes/icons/help1.png" style="cursor:pointer;margin-left:10px;"/>
		</div>
    </div>
	<!-- 左侧导航菜单 -->
    <div data-options="region:'west',split:true,title:'导航菜单',iconCls:'icon-naviga'" style="width:200px;">
        <div class="easyui-accordion" style="width:100%;border:0px;">
			${menuHtml }
		</div>
    </div>
	<!--主工作区 -->
    <div id="mainPanle" data-options="region:'center'" title="" style="overflow:hidden;">
	     <div id="menu_tabs" class="easyui-tabs"  fit="true" border="false" ></div>
	</div>
	<!--底部版权标识-->
    <div data-options="region:'south'" style="height:50px;text-align:center;padding:20px 0 0 0 ;">
    	版权所有：北京easyui科技有限公司
    </div>
    <div id="mm" class="easyui-menu" style="width:150px;">
        <div id="mm-tabrefresh" data-options="iconCls:'icon-refresh'">刷新</div> 
        <div class="menu-sep"></div>        
        <div id="mm-tabclose" data-options="iconCls:'icon-close'">关闭</div>    
        <div id="mm-tabcloseall">关闭全部</div>    
        <div id="mm-tabcloseother">关闭其他</div>    
        <div class="menu-sep"></div>    
        <div id="mm-tabcloseright">关闭右侧标签</div>    
        <div id="mm-tabcloseleft">关闭左侧标签</div>    
    </div>  
</body>
</html>