package com.zyl.export.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *@Title:反射工具类
 *@Description:
 *@Author:Todd
 *@Since:2016年3月20日
 *@Version:1.1.0
 */
public class ReflectUtil {
	/**
	 * 将request中的参数放入map中
	 * @param parameterMap
	 * @return
	 */
	public static Map<String,Object> transToMAP(Map parameterMap){
	      // 返回值Map
	      Map<String,Object> returnMap = new HashMap<String,Object>();
	      Iterator entries = parameterMap.entrySet().iterator();
	      Map.Entry entry;
	      String name = "";
	      String value = "";
	      while (entries.hasNext()) {
	          entry = (Map.Entry) entries.next();
	          name = (String) entry.getKey();
	          Object valueObj = entry.getValue();
	          if(null == valueObj){
	              value = "";
	          }else if(valueObj instanceof String[]){
	              String[] values = (String[])valueObj;
	              for(int i=0;i<values.length;i++){
	                  value = values[i] + ",";
	              }
	              value = value.substring(0, value.length()-1);
	          }else{
	              value = valueObj.toString();
	          }
	          returnMap.put(name, value);
	      }
	      
	      return  returnMap;
	  }

}
