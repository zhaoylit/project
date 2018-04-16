package com.web.util;

import org.apache.commons.lang3.StringUtils;
public class StringUtil {
	public static boolean isNorBlank(String str){
		if(StringUtils.isBlank(str)){
			return false;
		}
		if("null".equals(str)){
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println(isNorBlank("null"));
	}
}

