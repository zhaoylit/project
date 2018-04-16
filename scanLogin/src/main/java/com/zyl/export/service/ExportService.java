package com.zyl.export.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zyl.export.mapper.UserMapper;
import com.zyl.export.socket.WebSocketUtil;
import com.zyl.export.util.EcacheUtil;
import com.zyl.export.util.ExportUtil;
import com.zyl.export.util.FindDates;
import com.zyl.export.util.ParamsUtil;


/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月30日
 * @version 1.0
 */
@Service
public class ExportService {
    @Value("${my.fileSavePath}")
    private String fileSavePath;

    //失效时间
    private static final int TIME_TO_LIVES_ECONDS = 60*60*12;
    //缓存名称
    private static final String CACHE_NAME = "user_guid";
    private static final EcacheUtil cacheService = new EcacheUtil(CACHE_NAME,1,TIME_TO_LIVES_ECONDS);
    private static final Logger log = Logger.getLogger(ExportService.class);

    public Map<String,String> export(Map<String, Object> params) {
        Map<String,String> resultMap = new HashMap<>();
        String exportType = ParamsUtil.nullDeal(params, "exportType", "1");//1.数据导出，2.统计导出
        String userName = ParamsUtil.nullDeal(params, "userName", "");
        String userId = ParamsUtil.nullDeal(params, "userId", "");
        String beginTime = ParamsUtil.nullDeal(params, "beginTime", "");
        String endTime = ParamsUtil.nullDeal(params, "endTime", "");
        String title = ParamsUtil.nullDeal(params, "title", "");
        String column = ParamsUtil.nullDeal(params, "column", "");
        String sql = ParamsUtil.nullDeal(params, "sql", "");
        String resultType = ParamsUtil.nullDeal(params, "resultType", "");//1.excel，2.json字符串
        String guid = ParamsUtil.nullDeal(params, "guid", "");
        String sortField = ParamsUtil.nullDeal(params, "sortField", "");
        String limit = ParamsUtil.nullDeal(params, "limit", "10");
        

        String titleArray[] = title != null && !"".equals(title) ? title.split(",") : new String[]{};
        String columnArray[] = column != null && !column.equals("") ? column.split(",") : new String[]{};
        
        //查询用户id
//        Map<String,String> userMap = userMapper.getUserInfo(userName);
//        String userId = ParamsUtil.nullDeal(userMap, "userId", "");
        //缓存用户id
        cacheService.put(CACHE_NAME, userId, guid);
        try {
            String result = "";
            if("1".equals(exportType)){
              //导出excel文件，返回路径
                List<String> dateList = FindDates.findDates(beginTime, endTime);
                for(String str : dateList){
                    String path = ExportUtil.listExport(userId,userName, str, str, sql, fileSavePath,titleArray,columnArray);
                    if("".equals(result))
                        result = path;
                    else
                        result+="," + path;
                }
            }else{
                if("1".equals(resultType)){
                    //导出excel文件，返回路径
                    result = ExportUtil.countExport(userId, userName, beginTime, endTime, sql, fileSavePath,sortField,Integer.parseInt(limit));
                }else{
                    result = ExportUtil.countJson(userId, beginTime, endTime, sql, sortField,Integer.parseInt(limit));
                }
            }
            if(result == null  || "".equals(result)){
                return null;
            }
            String downloadName = "1".equals(exportType) ? userName + "导出信息("+beginTime+").xlsx" :  userName + "导出信息.xlsx";
            String[] pathArray = result.split(",");
            if(pathArray.length > 1 && "1".equals(exportType)){
                downloadName = userName + "导出信息.zip";
                //打包处理
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(fileSavePath + downloadName));
                byte[] buffer = new byte[1024];
                for(String path : pathArray){
                    File file = new File(path);
                    if(file.exists()){
                        FileInputStream fis = new FileInputStream(file);
                        out.putNextEntry(new ZipEntry(file.getName()));
                        int len;
                        //读入需要下载的文件的内容，打包到zip文件
                        while((len = fis.read(buffer))>0) {
                              out.write(buffer,0,len);
                        }
                        out.closeEntry();
                        fis.close();
                    }
                }
                out.close();
                WebSocketUtil.sendInfoMessage(guid, "打包zip完成：");
            }
            resultMap.put("userName", userName);
            resultMap.put("result", "取得结果：<br/>" + result+"<br/>");
            resultMap.put("downloadUrl", "<font color='red'>"+downloadName + "</font>&nbsp;&nbsp;<a href='/download.do?path="+(fileSavePath + downloadName)+"'>下载</a><br/>");
            return resultMap;
        } catch (Exception e) {
            //输出错误消息到控制台
            log.info("-----------------------------错误信息");
            WebSocketUtil.sendInfoMessage(guid, "<font color='red'>系统错误： "+e+"</font>");
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultMap;
    }
    
}
