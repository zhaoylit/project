package com.web.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.NewBeanInstanceStrategy;

public class ParamUtil {
	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map toMap(HttpServletRequest request) {
	    // 返回值Map
	    Map returnMap = new HashMap();
		try {
			request.setCharacterEncoding("UTF-8");
			// 参数Map
		    Map properties = request.getParameterMap();
		    Iterator entries = properties.entrySet().iterator();
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return returnMap;
	}
	public static String nullDeal(Map params,String key,String defaultValue){
		if(params == null || !StringUtil.isNorBlank(params.get(key)+"")){
			return defaultValue;
		}
		return String.valueOf(params.get(key));
	}
}
