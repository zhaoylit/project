package com.zkkj.backend.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;

import com.zkkj.backend.common.bean.JsonInput;


/**
 *@Title:流处理工具类
 *@Description:
 *@Author:Todd
 *@Since:2016年3月20日
 *@Version:1.1.0
 */
public class StreamUtil {
	/**
	 * 从输入流获取数据
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inputStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inputStream.close();
		return outputStream.toByteArray();
	}

	public static byte[] readInputStream(HttpServletRequest request) throws Exception {
		InputStream inputStream = request.getInputStream();
		if (inputStream == null) {
			return null;
		}
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inputStream.close();
		return outputStream.toByteArray();
	}
	
	/**
	 * 从request中获得json对象
	 * @param request
	 * @return
	 */
	public static JsonInput getJsonInput(HttpServletRequest request){
		Object obj=request.getAttribute("jsoninput");
		if(obj!=null){
			System.out.println("params：" + ((JsonInput)obj).getParams());
			return (JsonInput)obj;
		}
		return null;
	}
	
	public static byte[] ObjectToByte(java.lang.Object obj) {  
    byte[] bytes = null;  
    try {  
        // object to bytearray  
        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
        ObjectOutputStream oo = new ObjectOutputStream(bo);  
        oo.writeObject(obj);  
  
        bytes = bo.toByteArray();  
  
        bo.close();  
        oo.close();  
    } catch (Exception e) {  
        System.out.println("translation" + e.getMessage());  
        e.printStackTrace();  
    }  
    return bytes;  
}  


}