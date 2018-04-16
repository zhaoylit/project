package com.zyl.player.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2017年12月28日
 * @version 1.0
 */

@Controller
public class Mp3Controller {
    private static final String BASE_PATH = "/usr/local/data/";
    private static final String FOLD_PATH = "/usr/local/data/music/";
    private static Logger log = Logger.getLogger(Mp3Controller.class);
    @Value("${my.returnPath}")
    private String returnPath = "";
    private static List<Map<String,Object>> pathList = null;
    
    @ResponseBody
    @RequestMapping({"/getMp3List"})
    public void getMp3List(HttpServletRequest request, HttpServletResponse response){
      try{
    	  log.info("=================================");
          pathList = new ArrayList<Map<String,Object>>();
          getFile(FOLD_PATH);
          Map<String,List<Map<String,Object>>> dataMap = new HashMap<String, List<Map<String, Object>>>();
          //分类处理
          for(Map<String,Object> map : pathList){
              String fold = String.valueOf(map.get("fold"));
              final String url = String.valueOf(map.get("url"));
              final String name = String.valueOf(map.get("name"));
              if(dataMap.get(fold) == null ){
                   //添加
                  dataMap.put(fold,new ArrayList<Map<String,Object>>(){{
                      new HashMap<String,Object>(){{
                          put("url", url);
                          put("name", name);
                      }};
                  }});
              }else{
                  //存在更新
                  List<Map<String,Object>> list = dataMap.get(fold);
                  list.add(new HashMap<String,Object>(){{
                      put("url", url);
                      put("name", name);
                  }});
              }
          }
          List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
          for(Map.Entry<String, List<Map<String,Object>>> entry : dataMap.entrySet()){
              Map<String,Object> map = new HashMap<String,Object>();
              map.put("fold", entry.getKey());
              map.put("list", entry.getValue());
              resultList.add(map);
          }
          response.getOutputStream().write(JSONArray.toJSONString(resultList).getBytes("UTF-8"));
      }
      catch (UnsupportedEncodingException e){
          e.printStackTrace();
          log.info("-------------" + e.getMessage());
      }
      catch (IOException e){
          e.printStackTrace();
          log.info("-------------" + e.getMessage());
      }
    }
    
    public static void main(String[] args){
        pathList = new ArrayList<Map<String,Object>>();
        new Mp3Controller().getFile(FOLD_PATH);
        Map<String,List<Map<String,Object>>> dataMap = new HashMap<String, List<Map<String, Object>>>();
        //分类处理
        for(Map<String,Object> map : pathList){
            String fold = String.valueOf(map.get("fold"));
            final String url = String.valueOf(map.get("url"));
            final String name = String.valueOf(map.get("name"));
            if(dataMap.get(fold) == null ){
                 //添加
                dataMap.put(fold,new ArrayList<Map<String,Object>>(){{
                    new HashMap<String,Object>(){{
                        put("url", url);
                        put("name", name);
                    }};
                }});
            }else{
                //存在更新
                List<Map<String,Object>> list = dataMap.get(fold);
                list.add(new HashMap<String,Object>(){{
                    put("url", url);
                    put("name", name);
                }});
            }
        }
        System.out.println(dataMap);
    }
    
    private void getFile(String filePath){
      File file = new File(filePath);
      File[] tempList = file.listFiles();
      if(tempList != null && tempList.length > 0){
    	  for (int i = 0; i < tempList.length; i++)
          {
            if (tempList[i].isFile()) {
                String path1 = tempList[i].getPath().replace("\\", "/").replace(FOLD_PATH, "");
                String path = tempList[i].getPath().replace("\\", "/").replace(BASE_PATH, "");
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("name", path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf(".")));
                map.put("fold", path1.substring(0, path1.indexOf("/")));
                map.put("url", getReturnPath() + path);//文件的访问路径
                pathList.add(map);
            }
            if (tempList[i].isDirectory()) {
              getFile(tempList[i].getPath());
            }
          }
      }
      
    }

	public String getReturnPath() {
		return returnPath;
	}
}
