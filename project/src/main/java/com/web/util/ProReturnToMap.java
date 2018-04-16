package com.web.util;

import java.util.HashMap;
import java.util.List;

public class ProReturnToMap {
	public static HashMap<String, Object> listToMap(List<HashMap<String, Object>> list){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if(list == null || list.size() == 0){
			return resultMap;
		}
		if(list.size() == 1){
			resultMap = list.get(0);
			return resultMap;
		}
		if(list.size() == 2){
			resultMap = ((List<HashMap<String, Object>>)list.get(1)).get(0);
			resultMap.put("resultInfo", list.get(0));
			return resultMap;
		}
		return resultMap;
	}
}
