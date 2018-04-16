package com.zyl.export.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ParamsUtil {
	public static String nullDeal(Map params,String key,String defaultValue){
		if(params == null || params.get(key)==null || "".equals(params.get(key))){
			return StringUtils.isNotEmpty(defaultValue) ? defaultValue : "";
		}
		return String.valueOf(params.get(key));
	}
}
