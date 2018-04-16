<%@page import="com.zkkj.backend.common.util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serviceId = request.getParameter("serviceId");
String resourceServiceType = request.getParameter("resourceServiceType");
String resourceType = request.getParameter("resourceType");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path %>/pages/plugins/photoManage/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />	
<script src="<%=path %>/pages/plugins/photoManage/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/pages/plugins/photoManage/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=path %>/pages/plugins/photoManage/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script type="text/javascript">
function getSessionId(){
	var c_name = 'JSESSIONID';
	if(document.cookie.length>0){
	  c_start=document.cookie.indexOf(c_name + "=")
	  if(c_start!=-1){ 
	    c_start=c_start + c_name.length+1 
	    c_end=document.cookie.indexOf(";",c_start)
	    if(c_end==-1) c_end=document.cookie.length
	    return unescape(document.cookie.substring(c_start,c_end));
	  }
	}
}
$(document).ready(function() {
	initData();
	$("#file-upload").fileinput({
        language: 'zh',
        uploadUrl: '<%=path%>/fileInput/photoManage.do?opType=2&sessionId="+getSessionId()+"',
        allowedFileExtensions : ['jpg', 'png','gif'],
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-primary",
        maxFileSize: 2048,
        extra:{id:getSessionId()},
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
    });
	//文件上传成功
	$('#file-upload').on('fileuploaded', function(event, file, previewId, index, reader) {
		setTimeout(function(){
    		initData();
    	},1000);
	});
	//导入文件上传完成之后的事件
    $('#file-upload').on('filebatchuploadsuccess', function(event, files, extra) {
    	setTimeout(function(){
    		initData();
    	},1000);
//     	$("#file-upload").fileinput({
//     		showUpload: true
//         });
    });
	//文件删除之后
    $('#file-upload').on('filedeleted', function(event, key) {
//     	initData();
    });
});
function initData(){
	var str1 = "[",str2 = "[";
	$.ajax({
		type:"post",
		async:false,
		url:"<%=path%>/fileInput/photoManage.do",
		data:{keyPrimary:<%=serviceId%>,rSType:'<%=resourceServiceType%>',opType:1,rType:'<%=resourceType%>'},
		dataType:"json",
		success:function(data){
			for(var i = 0;i <  data.photoInfo.length;i++){
				var ss = "";
				if(data.photoInfo[i].resourceType == "101"){
					if(data.photoInfo[i].resourceSeq == "1"){
						ss = "<div style='background-color:white;position:absolute;width:50px;height:20px;color:red;font-weight:bold;padding:0;margin:0;line-height:15px;'>头像</div>";
					}
				}else{
					if(data.photoInfo[i].defaultMiddle == "1"){
						ss = "<div style='background-color:white;position:absolute;width:50px;height:20px;color:red;font-weight:bold;padding:0;margin:0;line-height:15px;'>封面</div>";
					}
				}
				str1+="\"";
				str1+=""+ss+"<img src='"+data.photoInfo[i].resourcePath+"' class='file-preview-image' title='"+data.photoInfo[i].resourceName+"' alt='"+data.photoInfo[i].resourceName+"' style='width:auto;height:160px;'>";
				str1+="\",";
				str2+="{";
	            str2+="    caption: '"+data.photoInfo[i].resourceOriginal+"',";
	            str2+="    width: '120px', ";
	            str2+="    url: '<%=path%>/fileInput/photoManage.do?opType=3&resourceId="+data.photoInfo[i].resourceId+"',";
	            str2+="    key: "+data.photoInfo[i].resourceId+", ";
	            str2+="    extra: {id: "+data.photoInfo[i].resourceId+"}";
	            str2+="},";
			}
			str1 += "]",str2 += "]";
			$("#file-upload").fileinput('refresh',{
				initialPreview:eval(str1)
		        ,initialPreviewConfig: eval(str2)
		        ,layoutTemplates :{
		            footer: '<div class="file-thumbnail-footer">\n' +
		            '    <div class="file-caption-name" style="width:{width}">{caption}</div>\n' +
		            '    {actions}\n' +
		            '</div>', 
		            actions: '<div class="file-actions">\n' +
		            '    <div class="file-footer-buttons">\n' +
		            '        <a href="javascript:void();" class=\'cover_a\' style="font-size:11px;">设为封面</a>&nbsp;{upload}{delete}' +
		            '    </div>\n' +
		            '    <div class="file-upload-indicator" tabindex="-1" title="{indicatorTitle}">{indicator}</div>\n' +
		            '    <div class="clearfix"></div>\n' +
		            '</div>',
		        }
		    });
		}
	});
}
$(function(){
	$(".cover_a").live("click",function(){
		var resourceId = $(this).next().attr("data-key");
		$.ajax({
			type:'post',
			url:'<%=path%>/fileInput/photoManage.do',
			data:{opType:4,resourceId:resourceId},
			dataType:'json',
			success:function(data){
				if(data.status == "true"){
					initData();
				}else{
					alert("系统错误");
				}
			},
			error:function(){
				alert("操作失败");
			}
		});
	});
})
</script>
</head>
<body style="padding:0px;margin:0px;">
<form enctype="multipart/form-data">
	<input id="file-upload" class="file" type="file" multiple data-preview-file-type="any" data-upload-url="<%=path%>/fileInput/photoManage.do?opType=2&keyPrimary=<%=serviceId%>&rSType=<%=resourceServiceType%>&rType=<%=resourceType%>" data-preview-file-icon="">
</form>
</body>
</html>