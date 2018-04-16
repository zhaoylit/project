package com.zkkj.backend.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultHandle {
	public static Map getList(List<Map<String, String>> list) throws Exception{
		Map resultMap = new HashMap<>();
		if(list == null || list.size() == 0){
			resultMap.put("rows", Collections.EMPTY_MAP);
			return resultMap;
		}
		Map<String, String> map = new HashMap<String, String>();
		map = list.get(list.size() - 1);
		String preRowKey =  ParamsUtil.nullDeal(map, "preRowKey", "");	
		String nextRowKey =  ParamsUtil.nullDeal(map, "nexRowKey", "");
		if(!preRowKey.equals(nextRowKey)){
			list.remove(list.size() - 1);
		}
		resultMap.put("preRowKey", preRowKey);
		resultMap.put("nextRowKey", nextRowKey);
		resultMap.put("rows", list);
		return resultMap;
	}	
}
