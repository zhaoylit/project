package com.zkkj.backend.web.interceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.service.base.authentication.DefaultSubjectImpl;
import com.zkkj.backend.service.base.authentication.Subject;
import com.zkkj.backend.service.base.authority.AuthorityService;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AuthorityService authorityService;
	private String failureUrl;
	private String defaultUrl;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception {
		
		System.out.println("--------AuthorityInterceptor preHandle----------:"+request.getRequestURL());

		
		String sessionId = request.getSession().getId();
		String account = (String)request.getSession().getAttribute("username");
		if(IConstant.ADMIN_ACCOUNT.equalsIgnoreCase(account))
			return true;
		String requestUri = request.getRequestURI().toString().trim();
		DefaultSubjectImpl subject = new DefaultSubjectImpl();
		Map<String,Object> param = new HashMap<String,Object>();
		subject.setSubjectId(sessionId);
		param.put(Subject.AUTH_SESSION,request.getSession());
		param.put("auth_uri", requestUri);
		subject.setParam(param);
		
		boolean result = authorityService.authorize(subject);
		if(!result){
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ 
				if(request.getRequestURL().indexOf("Init")>-1){
					response.getOutputStream().write(("<div style='margin-top:20%;margin-left:45%;'><h2><p>"+IConstant.AUTH_NOT_ALLOW+"</p></h2></div>").getBytes("UTF-8")); 
					return false;
				}
				Map<String,Object> res = new HashMap<String,Object>();
				res.put("result","0");
				res.put("total","0");
				res.put("rows",Collections.emptyList());
				res.put("message",IConstant.AUTH_NOT_ALLOW);
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8")); 
            }else
            	  response.sendRedirect(request.getContextPath()+failureUrl);
		}  
		return result;

		}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		/*String authResult = (String)request.getAttribute(Subject.AUTH_RESULT_ALLOW);
		if("N".equals(authResult)){
			Map<String,String> result = new HashMap<String,String>();
			result.put("result", "0");
			result.put("message", IConstant.AUTH_NOT_ALLOW);
			response.getOutputStream().write(GsonUtil.getGson().toJson(result).getBytes("UTF-8"));
		}
		System.out.println("--------AuthorityInterceptor preHandle----------:"+request.getRequestURL());
*/
	}
	public String getFailureUrl() {
		return failureUrl;
	}
	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}	
	
}
