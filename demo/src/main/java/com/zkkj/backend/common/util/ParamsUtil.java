package com.zkkj.backend.common.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
public class ParamsUtil {
	public static String nullDeal(Map params,String key,String defaultValue){
		if(params == null || params.get(key)==null){
			return StringUtils.isNotBlank(defaultValue) ? defaultValue : "";
		}
		return String.valueOf(params.get(key));
	}
}
