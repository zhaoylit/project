package com.zkkj.chat.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zkkj.chat.util.PropertiesConfig;

/**
 * Servlet implementation class ResourceServlet
 */
@WebServlet("/resource")
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final long MAX_SIZE = 1024 * 1024 * 1024;// 设置上传文件最大为 10M
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResourceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap();
		String method = request.getParameter("method");
		String fileType = request.getParameter("fileType");
		//资源上传
		if("upload".equals(method)){
			response.setContentType("application/json; charset=utf-8");
            // 设置字符编码为UTF-8, 这样支持汉字显示  
            response.setCharacterEncoding("UTF-8");  
            // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload  
            DiskFileItemFactory dfif = new DiskFileItemFactory();  
            dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  

			ServletFileUpload sfu = new ServletFileUpload(dfif);
			// 设置最大上传尺寸  
            sfu.setSizeMax(MAX_SIZE);  
            PrintWriter out = response.getWriter();  
            // 从request得到 所有 上传域的列表  
            List fileList = null;  
            try {  
                fileList = sfu.parseRequest(request);  
            } catch (FileUploadException e) {// 处理文件尺寸过大异常  
                if (e instanceof SizeLimitExceededException) {  
                	resultMap.put("result", "0");
                	resultMap.put("message", "文件尺寸超过规定大小:"+MAX_SIZE+"字节");
                	out.write(JSONObject.fromObject(resultMap).toString());
                	return ;
                }  
                e.printStackTrace();  
            }  
            // 没有文件上传  
            if (fileList == null || fileList.size() == 0) {  
            	resultMap.put("result", "0");
            	resultMap.put("message", "请选择上传文件");
            	out.write(JSONObject.fromObject(resultMap).toString());
            	return ;
            }  
         // 得到所有上传的文件  
            Iterator fileItr = fileList.iterator();  
            // 循环处理所有文件  
            while (fileItr.hasNext()) {  
                FileItem fileItem = null; 
                //文件大小
                long size = 0;  
                //文件原始名称
                String fileOldName = null;
                //文件后缀
                String suffix = null;
                //文件的上传名称
                String fileUploadName = null;
                //文件的保存路径
                String savePath = null;
                //文件的完整保存路径
                String path = null;
                // 得到当前文件  
                fileItem = (FileItem) fileItr.next();  
                // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
                if (fileItem == null || fileItem.isFormField()) {  
                    continue;  
                }  
                //获取文件名称
                fileOldName = fileItem.getName();  
                // 得到文件的大小  
                size = fileItem.getSize();  
                //文件扩展名称
            	 suffix = fileOldName.substring(fileOldName.lastIndexOf("."), fileOldName.length());
                //重新定义文件名称
                Random random = new Random();
                //文件的上传名称
                fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
                //获取文件的上传的根目录
                String base_upload_path = PropertiesConfig.getProperties("base_upload_path");
                //资源的访问地址
                String return_path_z = PropertiesConfig.getProperties("return_path_z");
                switch (fileType) {
				case "image":
				{
					if(
						!".jpg".equals(suffix) && !".JPG".equals(suffix) &&  !".png".equals(suffix) && !".gif".equals(suffix)
					 ){
							resultMap.put("result", "0");
		                    resultMap.put("message","请选择jpg,png,gif格式的图片");
		                	out.write(JSONObject.fromObject(resultMap).toString());
		                	return ; 
					}
					//获取图片资源的上传目录
					String image_path = PropertiesConfig.getProperties("image_path");
					//文件保存到数据库的名称
					savePath = image_path + fileUploadName;
					//文件的完整保存路径
					path = base_upload_path + savePath;
				}
				break;
				case "attchment":
				{
					//附件文件
					String attachment_path = PropertiesConfig.getProperties("attachment_path");
					//文件上传名称
					savePath = attachment_path + fileUploadName;
					//文件的完整保存路径
					path = base_upload_path + savePath;
				}
				break;
				case "video":
				{
					if(
							!".mp4".equals(suffix)
					 ){
							resultMap.put("result", "0");
		                    resultMap.put("message","请选择mp4格式的视频");
		                	out.write(JSONObject.fromObject(resultMap).toString());
		                	return ; 
					}
					//视频消息
					String video_path = PropertiesConfig.getProperties("video_path");
					//上传名称
					savePath = video_path + fileOldName;
					//文件完整的保存路径
					path = base_upload_path + savePath;
				}
				break;
				
				default:
					break;
				}
                try {  
                	File saveDir = new File(path);
	                if (!saveDir.getParentFile().exists())
	                    saveDir.getParentFile().mkdirs();
                    // 保存文件  
                    fileItem.write(new File(path));  
                    response.setStatus(200);  
                    //返回成功的消息
                    resultMap.put("result", "1");
                    resultMap.put("savePath", savePath);
                    resultMap.put("fileOldName", fileOldName);
                    resultMap.put("url",return_path_z + savePath);
                    DecimalFormat df = new DecimalFormat("#.00");
                    resultMap.put("fileSize", df.format(size / 1024));
                	out.write(JSONObject.fromObject(resultMap).toString());
                	return ; 
                } catch (Exception e) {  
                    e.printStackTrace();  
                } 
                out.flush();  
                out.close();  
            }
		}else if("download".equals(method)){
			String path = request.getParameter("path");
			String name = request.getParameter("name");
			if(name.contains("（")){
				name = name.substring(0, name.indexOf("（"));
			}
			String return_path_z =  PropertiesConfig.getProperties("return_path_z1");
			//文件下载
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/form-data");
			String fileName = new String(name.getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			response.setBufferSize(1024);
			// 文件路径
			URL url = new URL(return_path_z + path);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			InputStream inputStream = connection.getInputStream();
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
