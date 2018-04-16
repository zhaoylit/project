<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
   
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- <link href="http://www.jq22.com/jquery/bootstrap-3.3.4.css" rel="stylesheet"> -->

<link href="<%=path %>/css/index/bootstrap.css" media="all" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/index/fileinput.css" media="all" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/javascript/js/jquery.min.js" charset="utf-8"></script>
<script src="<%=path %>/javascript/fileinput.js" type="text/javascript"></script>
<script src="<%=path %>/javascript/zh.js" type="text/javascript"></script>
<!-- <script src="http://www.jq22.com/jquery/bootstrap-3.3.4.js" type="text/javascript"></script>
 -->
 <script src="<%=path %>/javascript/bootstrap.min.js" type="text/javascript"></script>
<style type="text/css">
       .img_pic{
          width:220px;
          height:200px;
       }
</style>
 <div class="container kv-main">
 
 		<h1>广告资源添加</h1>
        <div class="page-header">
            
            <form enctype="multipart/form-data">
                <div class="form-group">
                   <input id="file-1" type="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="2" >
                </div>
            </form>
            
            <br>
        </div>
</div>
      <script type="text/javascript">
          $(document).ready(function(){
        	  console.log("1")
              /*--------初始化页面插件----------*/
             $("#file-1").fileinput('destroy');
             initData();//有数据的话初始化页面数据
          	});
          var jsonStr = ${data};
         var jsonStr = eval(jsonStr);
          //var arraydata=jsonStr.parseJSON();
          var temp1="";
          var temp2="";
          var str2 = "[";
          var jsonArray  = new Array(); 
              function  getValue(){
            	  var str1 = "";
            	  for(var i=0;i<jsonStr.length;i++){
            		  if(jsonStr[i].suffix==".mp4"){ 
            			  str1+="<video controls class='img_pic'><source src='"+jsonStr[i].filePath+"' type='video/mp4' /><video>,";
            		  }else{
            			  str1+="<img class='img_pic' src='"+jsonStr[i].filePath+"'>,";
            		  }
                	  //str1+="'"+jsonStr[i].filePath+"'";
                      //str1+=",";
                      str2+="{";
                      str2+="caption: '"+jsonStr[i].fileOldName+"',";
                      str2+="size: '"+jsonStr[i].fileSize+"',";
                      str2+="width: '120px', ";
                      str2+="url: '<%=path%>/resource/deleteResource.do?ids="+jsonStr[i].id+"',";
                      str2+="key: "+jsonStr[i].resourceId+", ";
                      str2+="extra: {id: "+jsonStr[i].resourceId+"}";
                      str2+="},";
                  }
            	  str1=str1.substring(0,str1.length-1);
            	  var regex = /,/;
            	  if(str1!=''){
            		  jsonArray=str1.trim().split(regex);
            	  }
            	  console.log(str1);
            	  console.log(jsonArray);
            	  //str1+="]";
          		  str2+="]";
                  //temp1=eval(str1);
                  temp2=eval(str2);  
                }         
              function getFileType(filePath){
            		return filePath.subString(filePath.lastIndexOf("."),filePath.length);
               }

              function initData(path,con){
                   getValue();
                   console.log(jsonArray);
                   $("#file-1").fileinput({
                       language: 'zh',
     
                       uploadUrl: "<%=path%>/resource/addResource.do?resourceServiceType=1&resourceServiceId=${resourceServiceId}", //文件上传方法
                       overwriteInitial: false,
                       allowedFileTypes: ['image', 'video', 'flash'],
                        slugCallback: function(filename) {
                           return filename.replace('(', '_').replace(']', '_');
                       }, 
                       overwriteInitial: false, //不覆盖已存在的图片        
                       initialPreviewAsData:false,  
                       initialPreview:jsonArray,              
                       initialPreviewConfig:temp2
                   });
              } 
              
              //文件上传方法
                    $('#file-1').on('fileuploaded', function(event, file, previewId, index, reader) {
                    	//window.location.reload();
                    });

            //文件删除之前的确认
                      $('#input-id').on('filepredelete', function(event, key) {  
                            confirm("您确认要删除嘛")  
                        }); 
            // 文件的成功删除
                      $('#input-id').on('filedeleted', function(event, key) {  
                                  alert("删除文件了")
                        }); 

    </script>