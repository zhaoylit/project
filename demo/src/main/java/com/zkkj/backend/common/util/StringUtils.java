package com.zkkj.backend.common.util;


public class StringUtils {
	public static boolean isNotBlank(String str){
		if(
			str == null 
			|| org.apache.commons.lang.StringUtils.isBlank(str) 
			|| "null".equals(str)
		){
			return false;
		}
		return true;
	}
	public static boolean isBlank(String str){
		if(
			str == null 
			|| org.apache.commons.lang.StringUtils.isBlank(str) 
			|| "null".equals(str)
		){
			return true;
		}
		return false;
	}
}
