<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/css/page/video-js.min.css">
<script type="text/javascript" src="<%=path %>/javascript/js/video.min.js" charset="utf-8"></script>
<style type="text/css">
   .media{display:flex;padding-top:2%;}
   .media-total{width:50%;border-right:solid 1px #eee;padding-left:2%;}
    .media-half{width:50%;padding-left:2%;}
</style>
   <!--    广告位图片类型 -->
   <c:if test="${resultInfo.playType == 'image' }">
        <div class="media">
            <div class="media-total">
               <h4>图片的全屏文件</h4>
   		       <img src="${resultInfo.file1 }" style="width:60%;display: block;margin: 20px auto;">
            </div>
            <div class="media-half">
                <h4>图片的半屏文件</h4>
                <img src="${resultInfo.file2 }" style="width:60%;display: block;margin: 20px auto;">
            </div>
        </div>
        
   </c:if>
   <!--    广告位视频类型 -->
    <c:if test="${resultInfo.playType == 'video' }">
    
        <div class="media">
            <div class="media-total">
               <h4 style="margin-bottom:20px;">视频的全屏文件</h4>
   		       <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="380px;" height="300px"
				      poster="http://video-js.zencoder.com/oceans-clip.png"
				      data-setup="{}">
				    <source src="${resultInfo.file1 }" type='video/mp4' />
				    <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
				    <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
				
				</video>
            </div>
            <div class="media-half">
                <h4 style="margin-bottom:20px;">视频的半屏文件</h4>
                <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="380" height="300px"
				      poster="http://video-js.zencoder.com/oceans-clip.png"
				      data-setup="{}">
				    <source src="${resultInfo.file2 }" type='video/mp4' />
				    <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
				    <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
				
				</video>
            </div>
        </div>
    
    
    
    
    
    
    
        
   </c:if>
   
   
   
  
   
   
   
<script type="text/javascript">
</script>
