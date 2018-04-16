package com.zj.api.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommonsUtil {
	/**
	 * 获取返回的数据
	 * @param input
	 * @param object
	 * @return
	 */
	public JsonInput getJsonInput(JsonInput input,Object object){
		if(input.getData() != null){
			input.getData().put("result", object);
		}else{
			Map dataMap = new HashMap();
			dataMap.put("result", object);
			input.setData(dataMap);
		}
		return input;
	}

	public static Map getParams(JsonInput input, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map dataMap = input.getData();
		Map paramsMap = ReflectUtil.transToMAP(request.getParameterMap());
		dataMap.putAll(paramsMap);
		return dataMap;
	}
}
