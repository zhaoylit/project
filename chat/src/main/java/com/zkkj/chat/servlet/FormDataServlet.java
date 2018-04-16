package com.zkkj.chat.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.zkkj.chat.util.PropertiesConfig;

/**
 * Servlet implementation class FormDataServlet
 */
@WebServlet("/base64Upload")
@MultipartConfig
public class FormDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/text; charset=utf-8");
        // 设置字符编码为UTF-8, 这样支持汉字显示  
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String file = new String (request.getParameter("file").getBytes("ISO8859-1"),"UTF-8"); //二进制流上传需要转码 
        file = file.replace("data:audio/mp3;base64,", "");
        //文件上传根目录
        String base_upload_path = PropertiesConfig.getProperties("base_upload_path");
        //文件访问路径
        String  return_path_z = PropertiesConfig.getProperties("return_path_z");
        //文件保存路径
        String audio_path = PropertiesConfig.getProperties("audio_path");
        //重新定义文件名称
        Random random = new Random();
        //文件的上传名称
        String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+".mp3";
        //文件保存路径
        String savePath = audio_path + fileUploadName;
        //文件完整路径
        String filePath = base_upload_path + savePath;
        base64ToIo(file,filePath);
        //	        返回文件地址  
        out.write(return_path_z+ savePath);
	}
	
	public void base64ToIo(String strBase64,String path) throws IOException {
		File saveDir = new File(path);
        if (!saveDir.getParentFile().exists())
            saveDir.getParentFile().mkdirs();
        
        String string = strBase64;
        String fileName = path; //生成的新文件
        try {
            // 解码，然后将字节转换为文件
        	boolean flag = Base64.isBase64(strBase64);
            byte[] bytes = Base64.decodeBase64(string);   //将字符串转换为byte数组
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            for (int i = 0; i < buffer.length; ++i) {  
                if (buffer[i] < 0) {  
                    // 调整异常数据  
                	buffer[i] += 256;  
                }  
            } 
            FileOutputStream out = new FileOutputStream(fileName);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread); //文件写操作
            }
            out.flush();
            out.close();
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
