package com.zj.api.common.utils;

import java.util.Date;
import java.util.HashMap;

import net.sf.json.util.NewBeanInstanceStrategy;

import com.zj.api.common.constant.Constant;


public class SecurityUtil {
	public static String getToken(String userId){
		HashMap<String, String> params = new HashMap<String, String>();  
		params.put("userId",userId);  
		params.put("timestamp", new Date().getTime() + "");
        HashMap signMap = ParamSignUtils.sign(params,Constant.SIGN_SCRET);  
        String sign = ParamsUtil.nullDeal(signMap, "appSign", "");
		return sign;
	}
	public static void main(String[] args) {
		 System.out.println(getToken("123"));
	}
}
