package com.zj.web.modules.test.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zj.web.common.utils.HttpUtil;
import com.zj.web.common.utils.JacksonUtil;
import com.zj.web.common.utils.JsonInput;
import com.zj.web.common.utils.ParamSignUtils;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.ReflectUtil;

@Controller
@RequestMapping("test")
public class ApiTestController {
	private final String SIGN_SECRET = "sadg@$$%1231";
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	
	@RequestMapping("apiTestInit")
	public ModelAndView apiTestInit(ModelAndView model,HttpServletRequest resquest,HttpServletResponse response){
		model.setViewName("test/apiTest");
		return model;
	}
	@RequestMapping("testApi")
	@ResponseBody
	public void testApi(HttpServletRequest request,HttpServletResponse response) {
		JsonInput input = null;
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		//取得请求的地址
		String reqUrl = ParamsUtil.nullDeal(params, "reqUrl", "");
		//获取请求的参数
		String reqParams = ParamsUtil.nullDeal(params, "reqParams", "");
		//将请求参数 转换为map
		//Map reqParamsMap = JSONObject.fromObject(reqParams);
		//将参数转化为 jsoninput 对象
		try {
			input = JacksonUtil.readValue(reqParams, JsonInput.class);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("--------------------" + e.getMessage());
		}
		//对参数进行签名
		Map dataMap = input.getData();
		//取到app_key 
		String app_key = ParamsUtil.nullDeal(dataMap, "app_key", "");
		String app_secret = "76cbf4dace6dd030aa52011509bd5848";
		try {
			//根据app_key 从redis中取到app_secret 
			app_secret = stringRedisTemplate.opsForValue().get(app_key);
			//将app_key 添加到秦明中
			dataMap.put("app_secret",app_secret);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("---------------------------" + e.getMessage());
		}
		//将当前时间戳添加单参数中
		dataMap.put("timestamp", String.valueOf(new Date().getTime()));
		
		//对参数进行签名校验
		List<String> ignoreParamNames=new ArrayList<String>();
		ignoreParamNames.add("sign");
		Map signMap = ParamSignUtils.sign(dataMap,ignoreParamNames, SIGN_SECRET);
		//取到签名后的字符串
		String sign_ = ParamsUtil.nullDeal(signMap, "appSign","");
		//将签名添加到 参数中
		dataMap.put("sign", sign_);
		
		
		//去除app_secret
		dataMap.remove("app_secret");
		input.setData(dataMap);
		
		String reqParamsJson = JSONObject.fromObject(input).toString();
		//发送 http请求  并取得结果
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		//定义参数
		NameValuePair avp = new NameValuePair();
		avp.setName("params");
		avp.setValue(reqParamsJson);
		nameValuePairList.add(avp);
		try {
			String httpPost = HttpUtil.httpPost(reqUrl, nameValuePairList, null);
			//返回结果
			response.getOutputStream().write(JSONObject.fromObject(httpPost).toString().getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("----------------------------"+ e.getMessage());
		}
		
	}
}
