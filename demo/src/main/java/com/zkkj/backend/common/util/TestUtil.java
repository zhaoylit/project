package com.zkkj.backend.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestUtil {
	public static void main(String[] args) {
		String orignal_url = "http://vliveachy.tc.qq.com/vwecam.tc.qq.com/1006_9ac06e3a89f7446cb48f26ecb3f5ffff.f20.mp4?ptype&amp%3bvkey=D228A6D49A16B09ED90DAD41DC0ABE3EEB2A6D452B8080C5639AEF83B568EA8A0F42E4E39C6E77E6AFE381E32A2C7D1369B0EAD2CB25CD78&ocid=2440500652&ocid=631707052";
		try {
			URL url = new URL(orignal_url);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			InputStream inputStream = connection.getInputStream();
			
	        String filePath = "d://test1.mp4";
	        File file = new File(filePath);
			if (!file.exists()){   
				file.createNewFile();   
	        }
			FileOutputStream fous = new FileOutputStream(filePath); 
			
			BufferedInputStream bins = new BufferedInputStream(inputStream, 512);
            // 向压缩文件中输出数据   
            int nNumber;
            byte[] buffer = new byte[512];
            while ((nNumber = bins.read(buffer)) != -1) {
            	fous.write(buffer, 0, nNumber);
            }
            // 关闭创建的流对象   
            bins.close();
            inputStream.close();
            
	        fous.close();
			
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
