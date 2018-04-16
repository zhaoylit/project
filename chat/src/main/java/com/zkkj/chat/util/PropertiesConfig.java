package com.zkkj.chat.util;

import java.util.Map;

public class PropertiesConfig {
	private static Map<String,String> propertiesMap;
	public static String getProperties(String key){
		return propertiesMap.get(key);
	}
	public static Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}
	public static void setPropertiesMap(Map<String, String> propertiesMap) {
		PropertiesConfig.propertiesMap = propertiesMap;
	}
}
