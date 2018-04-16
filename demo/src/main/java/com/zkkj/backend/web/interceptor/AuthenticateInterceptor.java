package com.zkkj.backend.web.interceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.service.base.authentication.DefaultSubjectImpl;
import com.zkkj.backend.service.base.authentication.AuthenticationService;
import com.zkkj.backend.service.base.authentication.Subject;

public class AuthenticateInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private AuthenticationService authenticationService;
	private String failureUrl;
	private String defaultUrl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception {

		System.out.println("--------AuthenticateInterceptor preHandle----------:"+request.getRequestURL());

		String sessionId = request.getSession().getId();	

		DefaultSubjectImpl subject = new DefaultSubjectImpl();

		Map<String,Object> param = new HashMap<String,Object>();
		subject.setSubjectId(sessionId);
		param.put("auth_session",request.getSession());
		subject.setParam(param);
		authenticationService.authenticate(subject);		
		if(!subject.isAuthenticated()||Subject.AUTH_RESULT_NOLOGIN.equals(subject.getResult())){		
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ 		
				if(request.getRequestURL().indexOf("Init")>-1){
					response.getOutputStream().write(("<div style='margin-top:20%;margin-left:45%;'><h2><p>"+IConstant.SESSION_NOT_LOGIN+"</p></h2></div>").getBytes("UTF-8")); 
					return false;
				}
				Map<String,Object> res = new HashMap<String,Object>();
				res.put("result","0");
				res.put("total","0");
				res.put("rows",Collections.emptyList());
				res.put("message",IConstant.SESSION_NOT_LOGIN);
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8")); 
            }else{
            	 System.out.print("返回登录页...");
                response.sendRedirect(request.getContextPath()+failureUrl);
            }  
            return false;  
        }else  
            return true;     
		
		
	
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

