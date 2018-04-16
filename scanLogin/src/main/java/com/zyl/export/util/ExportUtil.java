package com.zyl.export.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyl.export.socket.WebSocketUtil;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月26日
 * @version 1.0
 */
public class ExportUtil {
    private static final String SORT_TIME_FIELD = "sortTime";
    private static final String GROUP_NAME = "name";
    //失效时间
    private static final int TIME_TO_LIVES_ECONDS = 60*60*12;
    //缓存名称
    private static final String CACHE_NAME = "user_guid";
    private static final EcacheUtil cacheService = new EcacheUtil(CACHE_NAME,1,TIME_TO_LIVES_ECONDS);
    private static final Logger log = Logger.getLogger(ExportUtil.class);
    public static void queryNoReturn(String userId,String beginTime,String endTime,String sql) throws UnsupportedEncodingException, ParseException{
        String fetch_all = "0";
        //查询起止时间的所有日期
        List<String> dateList = FindDates.findDates(beginTime, endTime);
        for(String str : dateList){
            StringBuilder sb = new StringBuilder("http://192.168.224.1:8888/open_db");
            sb.append("?user_id=").append(userId);
            sb.append("&date_s=").append(str);
            sb.append("&fetch_all=").append(fetch_all);
            sb.append("&instance=").append(str);
            sb.append("&sql=").append(URLEncoder.encode(sql, "UTF-8" ));
            String result = HttpUtil.sendGet(sb.toString());
            FormatUtil.printJson(result);
        }
    }
    public static List<Map<String,Object>> queryReturn(String userId,String beginTime,String endTime,String sql) throws ParseException, UnsupportedEncodingException{
        List<Map<String,Object>> dataList = new ArrayList<>();
        String guid = String.valueOf(cacheService.get(CACHE_NAME, userId));
        String fetch_all = "1";
        //查询起止时间的所有日期
        List<String> dateList = FindDates.findDates(beginTime, endTime);
        for(String str : dateList){
            WebSocketUtil.sendInfoMessage(guid, "查询["+str+"]数据开始：");
//            WebSocketUtil.sendInfoMessage(guid, message);
            StringBuilder sb = new StringBuilder("http://192.168.224.1:8888/open_db");
            sb.append("?user_id=").append(userId);
            sb.append("&date_s=").append(str);
            sb.append("&fetch_all=").append(fetch_all);
            sb.append("&instance=").append(str);
            sb.append("&sql=").append(URLEncoder.encode(sql, "UTF-8" ));
            String result = HttpUtil.sendGet(sb.toString());
            WebSocketUtil.sendInfoMessage(guid, "取得["+str+"]查询结果：");
            try {
                JSONObject jsonObjet = JSONObject.parseObject(result);
                String errorMsg  = jsonObjet.getString("err_msg");
                if(errorMsg == null || "".equals(errorMsg)){
                    dataList.add(jsonObjet);
                    WebSocketUtil.sendInfoMessage(guid, "解析["+str+"]完成");
                }else{
                    WebSocketUtil.sendInfoMessage(guid, "解析["+str+"]数据出现错误，错误信息为：" + errorMsg);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
                WebSocketUtil.sendInfoMessage(guid, "["+str+"]数据返回结果json转换错误，接口返回结果为： " + result+",");
            }
        }
        return dataList;
        
    }
    public static void queryData(List<Map<String,Object>> dataList,String beginTime,String endTime) throws ParseException{
        int noDoneCount = 0;
        List<String> dateList = FindDates.findDates(beginTime, endTime);
        for(String str : dateList){
            StringBuilder sb = new StringBuilder("http://192.168.224.1:8888/fetch");
            sb.append("?instance=").append(str);
            sb.append("&fetch_count=").append(Long.MAX_VALUE);
            String result = HttpUtil.sendGet(sb.toString());
            try {
                JSONObject jsonObjet = JSONObject.parseObject(result);
                List<List<String>> list = (List<List<String>>) jsonObjet.get("data");
                if(list != null && !list.isEmpty()){
                    String isDone = jsonObjet.getString("is_done");
                    if("1".equals(isDone)){
                        dataList.add((Map<String, Object>) jsonObjet);
                    }else{
                        noDoneCount++;
                    }
                }
            } catch (Exception e) {
                
            }
        }
        if(noDoneCount > 0){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            queryData(dataList,beginTime,endTime);
        }
    }
    private static List<Map<String,String>> handelData(String beginTime,String endTime,List<Map<String,Object>> dataList,List<String> queryColunmList) throws ParseException{
        List<Map<String,String>> allList = new ArrayList<>();
        for(Map<String,Object> map : dataList){
             List<List<String>> ld = (List<List<String>>) map.get("data");
             List<String> lm = (List<String>) map.get("cols");
             if(ld == null || ld.isEmpty()){
                 continue;
             }
             //保存查询列
             if(lm != null && ! lm.isEmpty() && queryColunmList != null && queryColunmList.isEmpty()){
                 queryColunmList.addAll(lm);
             }
             for(int i = 0;i < ld.size();i++){
                 Map<String,String> countMap = new HashMap<>();
                 for(int j = 0;j < lm.size();j++){
                     countMap.put(lm.get(j), ld.get(i).get(j));
                 }
                 String ctime = countMap.get(SORT_TIME_FIELD);
                 if(ctime != null && !"".equals(ctime)){
                   //去除非查询时间的数据
                     Long beginTimeLong = DateUtil.strToDate1(beginTime).getTime();
                     Long endTimeLong = DateUtil.strToDate1(endTime).getTime();
                     Long ctimeLong = DateUtil.strToDate(ctime).getTime();
                     if(beginTimeLong <= ctimeLong && ctimeLong <= endTimeLong)
                         allList.add(countMap);
                 }else{
                     allList.add(countMap);
                 }
             }
        }
        return allList;
    }

    public static Object[] getExcelData(List<Map<String,String>> dataList,String[] titleArray,String[] columnArray){
        Object[] result = new Object[2];//返回结果
        List<Object[]> excelList = new ArrayList<>();
        int index = 1;
        for(Map<String,String> map : dataList){
            Object[] object = new Object[titleArray.length + 2];
            //添加索引列
            object[0] = index++;
            //循环标题列
            for(int i = 0;i < titleArray.length;i++){
                object[i + 1] = map.get(columnArray[i]);
            }
            //添加空白列
            object[titleArray.length + 1] = "";
            excelList.add(object);
        }
        //为标题添加顺序列
        List<String> titleList = new ArrayList<>(Arrays.asList(titleArray)); //导出标题
        titleList.add(0, "顺序");
        titleList.add(titleList.size(), " ");
        String[] newTitleArrayTemp = new String[titleList.size()];
        String[] newTitleArray=titleList.toArray(newTitleArrayTemp);
        result[0] = excelList;
        result[1] = newTitleArray;
        return result;
    }
    public static String createAndSaveExcel(String userId,String exportName,String[] title,List<Map<String,String>> excelList,String fileSavePath) throws Exception{
        //获取缓存的客户端连接的id
        String guid = String.valueOf(cacheService.get(CACHE_NAME, userId));
        XSSFWorkbook workbook = new ExportExcel(exportName,title, excelList).export();
        WebSocketUtil.sendInfoMessage(guid, "创建excel表完成：");
        String fileName = exportName + ".xlsx";
        OutputStream out = new FileOutputStream(new File(fileSavePath + fileName));
        workbook.write(out);
        WebSocketUtil.sendInfoMessage(guid, "生成excel文件完成：");
        return fileSavePath + fileName;
    }
    public static List<Map<String,String>> countExportList(String userId,String beginTime,String endTime,String sql,String sortField,int limit) throws UnsupportedEncodingException, ParseException{
      //获取缓存的客户端连接的id
        String guid = String.valueOf(cacheService.get(CACHE_NAME, userId));
        //sql查询的数据列
        List<String> queryColunmList = new ArrayList<>();
        List<Map<String, Object>> dataList = queryReturn(userId,beginTime,endTime,sql);
        List<Map<String,String>> allList = handelData(beginTime,endTime,dataList,queryColunmList);
        if(allList.size() ==0 || allList.isEmpty()){
            WebSocketUtil.sendInfoMessage(guid, "<font color='red'>没有查询到数据</font>");
            return Collections.emptyList();
        }
        //sql查询列 去掉分组的列名
        for(int i = 0;i < queryColunmList.size();i++){
            if(queryColunmList.get(i).equals(GROUP_NAME)){
                queryColunmList.remove(i);
                i--;
            }
        }
        //按照name升序排列
        allList.sort((Map<String,String> map1, Map<String,String> map2) -> map1.get(GROUP_NAME).compareTo(map2.get(GROUP_NAME)));

        WebSocketUtil.sendInfoMessage(guid, "数据按照时间倒序排列完成");
        //相同累加
        String curName = allList.get(0).get(GROUP_NAME);
        Map<String,Integer> curCountMap = new HashMap<>();
        for(String str : queryColunmList){
            curCountMap.put(str, 0);
        }
        Map<String,String> tempMap = null;
        List<Map<String,String>> mergeList = new ArrayList<>();
        for(Map<String,String> map : allList){
            String name = map.get(GROUP_NAME);
            if(!curName.equals(name)){
                tempMap = new HashMap<>();
                tempMap.put(GROUP_NAME, curName);
                for(Map.Entry<String, Integer> entry : curCountMap.entrySet()){
                    tempMap.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
                mergeList.add(tempMap);
                curName = name;
                curCountMap = new HashMap<>();
                for(String str : queryColunmList){
                    curCountMap.put(str, 0);
                }
                continue;
            }
            for(String str : queryColunmList){
                int count = Integer.parseInt(map.get(str));
                curCountMap.put(str, curCountMap.get(str) + count);
            }
        }
        WebSocketUtil.sendInfoMessage(guid, "数据合并完成");
        //按照数量降序排列
        Collections.sort(mergeList, (Map<String, String> a, Map<String, String> b) -> (Integer.parseInt(String.valueOf(b.get(sortField))) - Integer.parseInt(String.valueOf(a.get(sortField)))));
        //按照指定数量截取
        WebSocketUtil.sendInfoMessage(guid, "数据按照排序字段排序完成");
        return mergeList == null || mergeList.size() ==0 ? Collections.emptyList() : mergeList.size() >= limit ? mergeList.subList(0, limit) : mergeList;
    }
    public static String countJson(String userId,String beginTime,String endTime,String sql,String sortField,int limit) throws UnsupportedEncodingException, ParseException{
        List<Map<String,String>> dataList =  countExportList(userId,beginTime,endTime,sql,sortField,limit);
        String json = JSONArray.toJSONString(dataList);
        return json;
    }
    public static String countExport(String userId,String userName,String beginTime,String endTime,String sql,String fileSavePath,String sortField,int limit) throws Exception{
        //获取缓存的客户端连接的id
        String guid = String.valueOf(cacheService.get(CACHE_NAME, userId));
        List<Map<String,String>> dataList =  countExportList(userId,beginTime,endTime,sql,sortField,limit);
        String titleArray[] = new String[]{GROUP_NAME,"count"};
        String columnArray[] = new String[]{GROUP_NAME,"count"};
        //查询excel数据
        Object[] excelData = getExcelData(dataList,titleArray,columnArray);
        List<Map<String,String>> excelList = (List<Map<String, String>>) excelData[0];
        String[] newTitleArray = (String[]) excelData[1];
        
        if(excelList.size() == 0){
            WebSocketUtil.sendInfoMessage(guid, "<font color='red'>没有查询到数据</font>");
            return "";
        }
        
        //创建excel并返回文件路径
        return createAndSaveExcel(userId,userName + "导出信息",newTitleArray,excelList,fileSavePath);
    }
    public static String listExport(String userId,String userName,String beginTime,String endTime,String sql,String fileSavePath,String[] titleArray,String[] columnArray) throws Exception{
      //获取缓存的客户端连接的id
        String guid = String.valueOf(cacheService.get(CACHE_NAME, userId));
        //处理sql 
        if(!sql.contains(SORT_TIME_FIELD)){
            sql = sql.replace("from", ",datetime(ctime,'unixepoch','localtime') as "+SORT_TIME_FIELD+" from ");
        }
        List<Map<String, Object>> dataList = queryReturn(userId,beginTime,endTime,sql);
        //sql查询的数据列
        List<String> queryColunmList = new ArrayList<>();
        List<Map<String, String>> mergeList = handelData(beginTime,endTime,dataList,queryColunmList);

        if(mergeList.size() == 0){
            WebSocketUtil.sendInfoMessage(guid, "<font color='red'>没有查询到数据</font>");
            return "";
        }
        
        WebSocketUtil.sendInfoMessage(guid, "合并数据完成");
        //list去重
        HashSet h = new  HashSet(mergeList);
        mergeList.clear();
        mergeList.addAll(h);
        WebSocketUtil.sendInfoMessage(guid, "数据去重完成");
        //按照时间排序
        mergeList.sort((Map<String,String> map1, Map<String,String> map2) -> map2.get(SORT_TIME_FIELD).compareTo(map1.get(SORT_TIME_FIELD)));
        WebSocketUtil.sendInfoMessage(guid, "数据按照时间倒序排列完成");
        
        //处理标题
        if(titleArray.length == 0 || columnArray.length == 0 || titleArray.length != columnArray.length){
            //自定义标题不正确，取sql查询的列,去掉sortTime字段
            for(int i = 0;i < queryColunmList.size();i++){
                if(SORT_TIME_FIELD.equals(queryColunmList.get(i))){
                    queryColunmList.remove(i);
                    i--;
                }
            }
            titleArray = new String[queryColunmList.size()];
            columnArray = new String[queryColunmList.size()];
            for(int i = 0;i < queryColunmList.size();i++){
                titleArray[i] = queryColunmList.get(i);
                columnArray[i] = queryColunmList.get(i);
            }
            
            WebSocketUtil.sendInfoMessage(guid, "未设置自定义excel导出标题列，获取sql查询的列为标题列");
        }
        
        if(titleArray.length == 0  || columnArray.length == 0 ){
            //无标题，我查询列，返回空路径
            WebSocketUtil.sendInfoMessage(guid, "没有导出标题列");
            return "";
        }
        Object[] excelData = getExcelData(mergeList,titleArray,columnArray);
        List<Map<String,String>> excelList = (List<Map<String, String>>) excelData[0];
        String[] newTitleArray = (String[]) excelData[1];
        //创建excel并返回文件路径
        return createAndSaveExcel(userId,userName + "导出信息("+beginTime+")",newTitleArray,excelList,fileSavePath);
    }
    public static void main(String[] args) {
        /*String userId = "46019";
        String beginTime = "20170101";
        String endTime = "20170930";
        StringBuilder sb = new StringBuilder("select");
        sb.append(" substr(url,instr(url,'://') + 3, instr(replace(url,'://',''),'/') - instr(url,'://'))  AS name");
        sb.append(" ,count(0) as count");
        sb.append(" from info WHERE info_flag = '02'");
        sb.append(" group by substr(url,instr(url,'://') + 3, instr(replace(url,'://',''),'/') - instr(url,'://'))");
        sb.append(" order by count desc");
        sb.append(";");
        String sql = sb.toString();
        countExport(userId,beginTime,endTime,sql); */
        
        String userId = "43138";
        String userName = "艾利艾1";
        String beginTime = "20180121";
        String endTime = "20180121";
        String fileSavePath = "D://";   
        StringBuilder sb = new StringBuilder("select");
        sb.append(" title,source,info.url,visitCount,replyCount,match_words,siteName");
        sb.append(" ,datetime(ctime,'unixepoch','localtime') as ctime");
        sb.append(" ,datetime(gtime,'unixepoch','localtime') as gtime");
        sb.append(" ,length(content) as len");
        sb.append(" ,case when negative > 0 then '正面' when negative = 0 then '中性' when negative < 0 then '负面' end as orientation");
        sb.append(" ,summary");
        sb.append(" from info left join `subject` on info.url = `subject`.url");
        sb.append(" where `subject`.subject_id = '44aded605e684e6c84642547c6aa6037'");
        sb.append(";");
        String sql = sb.toString();
         
        try {
            
            List<Map<String, Object>> queryReturn = queryReturn(userId,beginTime,beginTime,sql);
            log.info("======================" + queryReturn);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            String titleArray[] = new String[]{};
            String columnArray[] = new String[]{};
//            String titleArray[] = new String[]{"标题","来源","倾向性","要闻连接","浏览数","回复数","关键词","媒体类别","发布时间","抓取时间","字数","摘要"};
//            String s[] = new String[]{"title","source","orientation","url","visitCount","replyCount","match_words","siteName","ctime","gtime","len","summary"};
            String returnPath = listExport(userId,userName,beginTime,endTime,sql,fileSavePath,titleArray,columnArray);
            log.info("获得文件路径为：" + returnPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}
