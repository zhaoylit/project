package com.zj.api.common.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.zj.api.common.constant.RedisKeyConstant;
import com.zj.api.common.utils.DateUtil;
import com.zj.api.common.utils.ParamsUtil;

public class CommonAspect {
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
	public void doAfter(JoinPoint jp) {  
	   Object[] params = jp.getArgs();
	   String className = jp.getTarget().getClass().getName();
	   String methodName = jp.getSignature().getName();
    }  
  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable { 
        String classType = pjp.getTarget().getClass().getName();  
        Class<?> clazz = Class.forName(classType);    
        String clazzName = clazz.getName(); 
        String methodName = pjp.getSignature().getName(); //获取方法名称
    	//获取方法参数
        Object[] args = pjp.getArgs();
        //获取参数对象
        Map paramsMap = (Map) args[0];
        
        String errorReason = "";
  	    String errorCode = "0";
  	    String result = "";
  	    String resultContent = "";
  	    
        String startTime = DateUtil.getStringDate();
        Object retVal = null;
        try {
            retVal = pjp.proceed();  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorReason = "[" + e.getMessage() + "]";
			errorCode = "1";
		}
        
        String accessType = ParamsUtil.nullDeal(paramsMap, "accessType", "0");
        if(!"1".equals(accessType)){
  	    	return retVal;
  	    }
        //通过非控制层访问的 不进行记录
    	if(retVal instanceof List){
	    	//返回值为list对象
    		resultContent = JSONArray.fromObject((List)retVal).toString();
    		//判断是否有结果集
    		if((List)retVal != null && ((List)retVal).size() > 0){
	    		result = "1";
	    	}
	    	
	    }else if(retVal instanceof Map){
	    	//返回值为map对象
	    	resultContent = JSONObject.fromObject((Map)retVal).toString();
	    	if((Map)retVal != null){
	    		result = "1";
	    	}
	    }else if(retVal instanceof Integer){
	    	resultContent = String.valueOf(retVal);
	    	result = "0";
	    }else{
	    	resultContent = "0";
	    	result = "0";
	    }
        //结束时间
        String endTime = DateUtil.getStringDate();
        final Map dataMap = new HashMap();
        dataMap.put("reqUri", paramsMap.get("reqUri"));
    	dataMap.put("apiName", classType + "." + methodName);
    	dataMap.put("parameter", JSONObject.fromObject(paramsMap).toString());
    	dataMap.put("result", result);
    	dataMap.put("resultContent", resultContent);
    	dataMap.put("errorReason", errorReason);
    	dataMap.put("startTime", startTime);
    	dataMap.put("endTime", endTime);
    	String jsonString = JSONObject.fromObject(dataMap).toString();
    	new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					//将日志内容 存到redis队列
					Long leftPush = redisTemplate.opsForList().leftPush(RedisKeyConstant.API_ACCESS_LOG, dataMap);
					System.out.println(redisTemplate.opsForList().size(RedisKeyConstant.API_ACCESS_LOG));
					for(int i = 0; i < redisTemplate.opsForList().size(RedisKeyConstant.API_ACCESS_LOG);i++){
						Map object = (Map) redisTemplate.opsForList().index(RedisKeyConstant.API_ACCESS_LOG, i);
						System.out.println(object);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
        return retVal;  
    }  
  
    public void doBefore(JoinPoint jp) {  
        System.out.println("*******************调用接口之前: "  
                + jp.getTarget().getClass().getName() + "."  
                + jp.getSignature().getName());  
    }  
  
    public void doThrowing(JoinPoint jp, Throwable ex) {  
        System.out.println("*******************调用接口异常" + jp.getTarget().getClass().getName()  
                + "." + jp.getSignature().getName() + " throw exception");
        System.out.println(ex.getMessage());
    }  
}
