package com.zkkj.backend.common.util;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	
	/**
	 * Gson 只转换添加注解的字段
	 */
	public static Gson getExcludeGson(){
		 return GsonFactory.getExcludeGson();
	}
	
	/**
	 * 获取普通Gson对象
	 */
	public static Gson getGson(){
		return GsonFactory.getGson();
	}
	
	/**
	 * @param jsonStr json字符串
	 * @return 使用json库解析json
	 */
	public static JSONObject getJsonObject(String jsonStr){
		 return JSONObject.fromObject(jsonStr);
	}
	
	private static class GsonFactory{
		public static Gson gson = new Gson();
		public static GsonBuilder builder = new GsonBuilder();
		
		private static Gson getGson(){
	        return gson;
	    }
		
		private static Gson getExcludeGson(){
			 builder.excludeFieldsWithoutExposeAnnotation();
			 return builder.create();
		}
	}
	/**
	 * 格式化
	 * @param jsonStr
	 * @return
	 * @author   lizhgb
	 * @Date   2015-10-14 下午1:17:35
	 */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }
    /**
     * 添加space
     * @param sb
     * @param indent
     * @author   lizhgb
     * @Date   2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
	
}
