package com.web.modules.test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.web.util.ZipUtil;


@Service("testService")
public class TestServiceImpl  implements ITestService{

	@Override
	public String getZipFilePath(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		//待压缩的文件列表
    	List<Map> files = new ArrayList<Map>();
    	try {
    		//第一个文件远程地址
        	String file1Url = "http://bpic.588ku.com/element_origin_min_pic/16/06/24/03576c3b731eec6.jpg";
        	URL url1 = new URL(file1Url);
    		HttpURLConnection connection1=(HttpURLConnection)url1.openConnection();
    		Map map1 = new HashMap();
    		map1.put("inputStream", connection1.getInputStream());
    		String file1Name = file1Url.substring(file1Url.lastIndexOf("/") + 1);
    		map1.put("fileName", file1Name);
    		files.add(map1);
    		
    		//第二个文件的远程地址
        	String file2Url = "http://bpic.588ku.com/element_origin_min_pic/16/09/20/1557e0e6f31cd58.jpg";
        	URL url2 = new URL(file2Url);
    		HttpURLConnection connection2=(HttpURLConnection)url2.openConnection();
    		Map map2 = new HashMap();
    		map2.put("inputStream", connection2.getInputStream());
    		String file2Name = file2Url.substring(file2Url.lastIndexOf("/") + 1);
    		map2.put("fileName", file2Name);
    		files.add(map2);
    		
    		//第三个文件的远程地址
        	String file3Url = "http://bpic.588ku.com/element_origin_min_pic/02/13/31/01576a7c5a4262b.jpg";
        	URL url3 = new URL(file3Url);
    		HttpURLConnection connection3=(HttpURLConnection)url3.openConnection();
    		Map map3 = new HashMap();
    		map3.put("inputStream", connection3.getInputStream());
    		String file3Name = file3Url.substring(file3Url.lastIndexOf("/") + 1);
    		map3.put("fileName", file3Name);
    		files.add(map3);
    		
    		
    		//创建一个临时压缩文件,我们会把文件流全部注入到这个文件, 这里的文件你可以自定义是.rar还是.zip
    		String picZipPath = "D://pic.zip";
    		File file = new File(picZipPath);
    		//文件不存在创建文件
    		if (!file.exists()){
               file.createNewFile();   
            }
            response.reset();
            //创建文件输出流
            FileOutputStream fous = new FileOutputStream (file); 
            //打包的方法我们会用到ZipOutputStream这样一个输出流,所以这里我们把输出流转换一下
            ZipOutputStream zipOut = new ZipOutputStream(fous);
            ZipUtil.zipInputStreamFile(files, zipOut);
            zipOut.close();
            fous.close();
            
            return picZipPath;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("------"+e.getMessage());
		}
    	return "";
	}

}
