package com.zyl.export.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.zyl.export.service.ExportService;
import com.zyl.export.socket.WebSocketUtil;
import com.zyl.export.util.EcacheUtil;
import com.zyl.export.util.ParamsUtil;
import com.zyl.export.util.ReflectUtil;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月26日
 * @version 1.0
 */
@Controller
public class ExportController {
    @Autowired
    private ExportService exportService;
    //缓存失效是按
    private static final int TIME_TO_LIVES_ECONDS = 60*60*12;
    private static final String CACHE_NAME = "user_guid";
    private static final EcacheUtil cacheService = new EcacheUtil(CACHE_NAME,1,TIME_TO_LIVES_ECONDS);
    private static final Logger log = Logger.getLogger(ExportController.class);
    @Value("${my.returnPath}")
    private String returnPath;
    @Value("${my.socketUrl}")
    private String socketUrl;
    @RequestMapping("/")
    public String home(HttpServletRequest request,HttpServletResponse response) {
        request.setAttribute("returnPath", returnPath);
        request.setAttribute("socketUrl", socketUrl);
        return "index";
    }
    @RequestMapping("/explain")
    public String explain(HttpServletRequest request,HttpServletResponse response) {
        return "explain";
    }
    @RequestMapping("/direction")
    public String direction(){
        return "direction";
    }
    @RequestMapping("/export")
    public void export(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
        String guid = "";
        try {
            Map<String,Object> params = ReflectUtil.transToMAP(request.getParameterMap());
            String userId = ParamsUtil.nullDeal(params, "userId", "");
            //获取缓存的客户端连接的id
            guid = String.valueOf(cacheService.get(CACHE_NAME, userId));
            Map<String,String> resultMap = exportService.export(params);
            response.getOutputStream().write(JSONObject.toJSONString(resultMap).getBytes("UTF-8"));
        } catch (Exception e) {
            //输出错误消息到控制台
            WebSocketUtil.sendInfoMessage(guid, "<font color='red'>系统错误： "+e.getMessage()+"</font>");
        }
    }
    @RequestMapping("/download")
    public void download(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> params = ReflectUtil.transToMAP(request.getParameterMap());
        String filePath = ParamsUtil.nullDeal(params, "path", "");
        
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);  
        try {  
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        ///home/tomcat/apache-tomcat-9.0.1/files  
//        String realPath=File.separator+"home"+File.separator+"tomcat"+File.separator+"apache-tomcat-9.0.1"+File.separator+"files";  
        File file = new File(filePath);  
        response.reset();  
        response.setContentType("application/octet-stream");  
        response.setCharacterEncoding("utf-8");  
        response.setContentLength((int) file.length());  
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);  
        byte[] buff = new byte[1024];  
        BufferedInputStream bis = null;  
        OutputStream os = null;  
        try {  
            os = response.getOutputStream();  
            bis = new BufferedInputStream(new FileInputStream(file));  
            int i = 0;  
            while ((i = bis.read(buff)) != -1) {  
                os.write(buff, 0, i);  
                os.flush();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                bis.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
}
