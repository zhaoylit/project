package com.zj.api.common.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zj.api.common.constant.Constant;
import com.zj.api.common.utils.CodeEnum;
import com.zj.api.common.utils.JacksonUtil;
import com.zj.api.common.utils.JsonInput;
import com.zj.api.common.utils.ParamSignUtils;
import com.zj.api.common.utils.ParamsUtil;
import com.zj.api.modules.service.IndexService;
public class SystemInterceptor  extends HandlerInterceptorAdapter{
	private final Logger log = Logger.getLogger(SystemInterceptor.class); 
	//登陆地址
	private String loginUrl;
	
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	
	@Autowired
	private IndexService indexService;
	
    /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的 Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */    
    @Override    
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
    	JsonInput input = new JsonInput();
    	String url = "";	
    	String contextPath = request.getContextPath();
    	String requestUri = request.getRequestURI();
		String servletPath = request.getServletPath(); 
    	String userAgent = request.getHeader("User-Agent"); 
		servletPath = servletPath.substring(1);
		//获取请求参数
		String params = request.getParameter("params");
		if(handler instanceof HandlerMethod){
    		HandlerMethod handlerMethod = (HandlerMethod) handler; 
    		url = handlerMethod.getMethod().getName();
    	}
		
		//校验参数格式是否合法
		try {
			input = JacksonUtil.readValue(params, JsonInput.class);
		} catch (Exception e) {
			// TODO: handle exception
			input.setCode(CodeEnum.ILLEGAL_REQUEST.getCode());
			input.setMsg(CodeEnum.ILLEGAL_REQUEST.getMsg());
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes());
			return false;
		}
		
		//校验token和签名
		Map dataMap = input.getData();
		//app_key
		String app_key = ParamsUtil.nullDeal(dataMap, "app_key", "");
		//接口传递的签名
		String sign = ParamsUtil.nullDeal(dataMap, "sign", "");
		//接口传递的时间戳
		Long timestamp = null;
		//校验时间戳格式是否合法
		try{
			timestamp = Long.parseLong(ParamsUtil.nullDeal(dataMap, "timestamp", ""));
		}catch(Exception e){
			input.setCode(CodeEnum.ILLEGAL_REQUEST.getCode());
			input.setMsg(CodeEnum.ILLEGAL_REQUEST.getMsg());
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes());
			return false;
		}
		
		if("".equals(app_key) || "".equals(sign) ||"".equals(timestamp)){
			input.setCode(CodeEnum.ILLEGAL_PARAM.getCode());
			input.setMsg(CodeEnum.ILLEGAL_PARAM.getMsg());
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes());
			return false;
		}
		
		//当前时间戳
		Long cur_timestamp = new Date().getTime();
		log.info("cur_timestamp---------------------------------" + cur_timestamp);
		//判断请求是否超时
		long interval = (cur_timestamp - timestamp)/1000;
		//大于十分钟，请求超时
		if(interval >= 60 || interval <= -60){
			input.setCode(CodeEnum.REQUEST_TIMEOUT.getCode());
			input.setMsg(CodeEnum.REQUEST_TIMEOUT.getMsg());
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes());
			return false;
		}
		
		//将数据添加  app_key对象的 app_secret添加到 参数中进行签名
		//app_key错误
		if("".equals(app_key) || app_key == null){
			input.setCode(CodeEnum.APP_KEY_ERRROR.getCode());
			input.setMsg(CodeEnum.APP_KEY_ERRROR.getMsg());
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes());
			return false;
		}
		
		String app_secret = "";
		try {
			app_secret = stringRedisTemplate.opsForValue().get(app_key);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("===========================================redis服务器异常");
			//查询数据库
			Map selectAppKeyParamsMap = new HashMap();
			selectAppKeyParamsMap.put("app_key", app_key);
			app_secret = indexService.getSecretByKey(selectAppKeyParamsMap);
		}
		if("".equals(app_secret) || app_secret == null){
			input.setCode(CodeEnum.APP_KEY_ERRROR.getCode());
			input.setMsg(CodeEnum.APP_KEY_ERRROR.getMsg());
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes());
			return false;
		}
		//将app_secret 添加到签名中
		dataMap.put("app_secret", app_secret);
		
		//对参数进行签名校验
		List<String> ignoreParamNames=new ArrayList<String>();
		ignoreParamNames.add("sign");
		Map signMap = ParamSignUtils.sign(dataMap,ignoreParamNames, Constant.SIGN_SCRET);
		
		String sign_ = ParamsUtil.nullDeal(signMap, "appSign", "");
		dataMap.remove("app_secret");
		log.info("sign----------------------------------------" + sign_);
		if(!sign.equals(sign_)){
			input.setCode(CodeEnum.ILLEGAL_SIGN.getCode());
			input.setMsg(CodeEnum.ILLEGAL_SIGN.getMsg());
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes());
			return false;
		}
		
		return true;
    }    
    
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {  
    	
    }    
    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     *   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    @Override    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {    
    	
    }

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}    
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
	
}
