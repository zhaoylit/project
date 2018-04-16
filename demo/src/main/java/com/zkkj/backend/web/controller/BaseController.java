package com.zkkj.backend.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.support.RequestContext;
import com.zkkj.backend.common.util.GsonUtil;


public class BaseController {
	
	public static final String REQUEST_HEAD = "text/html; charset=UTF-8";
	
	public static final String APPLICATION_JSON = "application/json";
    
	public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	/**
	 * 为AJAX往前端输出的方法
	 * 
	 * @param o
	 *            想要输出的对象
	 * @return 是否成功
	 */
	protected boolean AjaxJsonWrite(Object o, HttpServletResponse response) {
		return AjaxJsonWrite(o, false, response);
	}

	/**
	 * json数据, 前台才能对象.属性
	 */
	protected boolean AjaxJsonWrite(Object o, boolean println, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType(REQUEST_HEAD);
			out = response.getWriter();
			String jsonStr = String.valueOf(o instanceof String || o instanceof Number || o instanceof Boolean ? o
					: o instanceof List<?> || o instanceof Set<?> || o instanceof Object[] ? JSONArray.fromObject(o)
							: JSONObject.fromObject(o));
			if (println)
				System.out.println("zhe json string is " + jsonStr);
			out.write(jsonStr);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("JSON\u683c\u5f0f\u5316\u5bf9\u8c61\u65f6\u9519\u8bef" + o.getClass().getName());
			return false;
		} finally {
			out.flush();
			out.close();
		}
	}
	
	protected boolean AjaxJsonWriteGson(Object o, boolean println, HttpServletResponse response) {
		PrintWriter out = null;
		try {
//			response.setHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
//			response.setContentType(CONTENT_TYPE_TEXT_JSON);
			response.setContentType(REQUEST_HEAD);
			out = response.getWriter();
			String jsonStr = GsonUtil.getGson().toJson(o);
			if (println)
				System.out.println("zhe json string is " + jsonStr);
			out.write(jsonStr);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("JSON\u683c\u5f0f\u5316\u5bf9\u8c61\u65f6\u9519\u8bef" + o.getClass().getName());
			return false;
		} finally {
			out.flush();
			out.close();
		}
	}
	
	protected void warningResponse(boolean success,String message,HttpServletResponse response){
		try {
		  Map<String,String> res = new HashMap<String,String>();
			res.put("success",Boolean.toString(success));
			res.put("message",message);		
			response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getText(String key, HttpServletRequest request) {

		return getText(key, null, request);
	}

	public String getText(String key, Object[] params,
			HttpServletRequest request) {
		try {
			RequestContext requestContext = new RequestContext(request);
			if (params != null)
				return new MessageFormat(requestContext.getMessage(key))
						.format(params);
			else
				return requestContext.getMessage(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解决get方法传递中文乱码
	 * @param target
	 * @return
	 */
	 public String convert(String target) {  
	        try {  
	        	 if(target==null)
	        		 return null;
	            return new String(target.trim().getBytes("ISO-8859-1"), "UTF-8");  
	        } catch (UnsupportedEncodingException e) {  
	            return target;  
	        }  
   }  
}
