package com.web.modules.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/test")
public class TestController {
	@Autowired
	private ITestService testService;
	@RequestMapping("download")
	public void testZipDownload(HttpServletRequest request,HttpServletResponse response){
		try {
			//获取文件打包后的下载路径,业务自行处理
			String zipFilePath = testService.getZipFilePath(request, response);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/form-data");
			//设置前端下载的文件名称
			String fileName = new String("pic".getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName+".zip");
			response.setBufferSize(1024);
			// 文件路径
			File file = new File(zipFilePath);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
