<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="<%=path %>/bootstrap/css/bootstrap.css" media="all" rel="stylesheet" type="text/css" />
<link href="<%=path %>/bootstrap/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/jquery-1.9.1.min.js" charset="utf-8"></script>
<script src="<%=path %>/bootstrap/fileinput.js" type="text/javascript"></script>
 <script src="<%=path %>/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=path %>/bootstrap/zh.js" type="text/javascript"></script>
<style type="text/css">
       .img_pic{
          width:220px;
          height:200px;
       }
</style>
 <div class="container kv-main" style="width:100%;">
        <div class="page-header"  style="width:100%;padding:10px 0 0 0px;margin:0px;">
            <form enctype="multipart/form-data">
                <div class="form-group">
                   <input id="fileUpload" type="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="2" >
                </div>
            </form>
            
            <br>
     </div>
</div>
      <script type="text/javascript">
          $(document).ready(function(){
        	  console.log("1")
              /*--------初始化页面插件----------*/
            $("#fileUpload").fileinput('destroy');
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
            			  str1+="<video controls class='img_pic'><source src='"+jsonStr[i].imageUrl+"' type='video/mp4' /><video>,";
            		  }else{
            			  str1+="<img class='img_pic' src='"+jsonStr[i].imageUrl+"'>,";
            		  }
                	  //str1+="'"+jsonStr[i].filePath+"'";
                      //str1+=",";
                      str2+="{";
                      str2+="caption: '"+jsonStr[i].fileOldName+"',";
                      str2+="size: '"+jsonStr[i].fileSize+"',";
                      str2+="width: '120px', ";
                      str2+="url: '<%=path%>/goodsResource/deleteByPrimaryKey.do?ids="+jsonStr[i].id+"',";
                      str2+="key: "+jsonStr[i].id+", ";
                      str2+="extra: {id: "+jsonStr[i].id+"}";
                      str2+="},";
                  }
            	  str1=str1.substring(0,str1.length-1);
            	  var regex = /,/;
            	  if(str1!=''){
            		  jsonArray=str1.trim().split(regex);
            	  }
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
                   $("#fileUpload").fileinput({
                       language: 'zh',
                       uploadUrl: "<%=path%>/goodsResource/insert.do?serviceId=${params.id}", //文件上传方法
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
                    $('#fileUpload').on('fileuploaded', function(event, file, previewId, index, reader) {
                    	window.location.reload();
                    });

           		    //文件删除之前的确认
                    $('#input-id').on('filepredelete', function(event, key) {  
                           confirm("您确认要删除嘛")  
                    }); 
           		    // 文件的成功删除
                    $('#input-id').on('filedeleted', function(event, key) {  
                         alert("删除文件了")
                    }); 
         function initSort(){
      	   var el = document.getElementById('imageSort');
      		var sortable = Sortable.create(el, {
      			tag : "img",
      			group: "words",
      			animation: 150,/*滑动速度*/
      			disabled: false,/* 为true时，拖拽不可用*/
      			chosenClass: "sortable-chosen",/*选中时的样式*/
      			dragClass: "sortable-drag", /*拖拽是的样式*/
      			dataIdAttr: 'data-id', /*数据id*/
      			store: {
      				get: function (sortable) {
      					var order = localStorage.getItem(sortable.options.group);
      					return order ? order.split('|') : [];
      				},
      				set: function (sortable) {
      					var order = sortable.toArray();
      					var ids =  order.join(',');
      					$("#orderIds").val(ids);
      				}
      			},
      			onAdd: function (evt){ console.log('onAdd.foo:', [evt.item, evt.from]); },
      			onUpdate: function (evt){ 
      				//更新后触发
      				var itemEl = evt.item;
      			},
      			onRemove: function (evt){ console.log('onRemove.foo:', [evt.item, evt.from]); },
      			onStart:function(evt){ console.log('onStart.foo:', [evt.item, evt.from]);},
      			onSort:function(evt){ console.log('onStart.foo:', [evt.item, evt.from]);},
      			onEnd: function(evt){ 
      				//拖拽完毕
      				var oldIndex = evt.oldIndex;  // element's old index within parent
      				var newIndex = evt.newIndex;
      			}
      		});
         }
    </script>