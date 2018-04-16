package com.zkkj.backend.web.controller.base.authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.entity.biz.ZkkjUser;
import com.zkkj.backend.service.base.authentication.AuthenticationService;
import com.zkkj.backend.service.base.authentication.DefaultSubjectImpl;
import com.zkkj.backend.service.base.authentication.Subject;
import com.zkkj.backend.service.base.authentication.WebAuthenticationServiceImpl;
import com.zkkj.backend.web.controller.BaseController;

@Controller

public class UserLoginController extends BaseController{
	
	public static String REDIECT_URL ="/menu/index.do";

	@Autowired
	private AuthenticationService authenticationService;

	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@RequestMapping(value = "/loginPage")
	public String getLoginPage(){
		return "login";
	}
	@RequestMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String,Object> data = new HashMap<String,Object>();
			Map<String,Object>  inputMap = ReflectUtil.transToMAP(request.getParameterMap());
			String username = (String)inputMap.get("username");
			String password = (String)inputMap.get("password");
			String verifyCode = (String)inputMap.get("verifyCode");
             if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){     	
                data.put("result", "FAIL");
 				data.put("message", "账号或密码不能为空！");
 				response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
 				return;
             }
             String vcode = (String)request.getSession().getAttribute(IConstant.VALIDATE_CODE);
             if(StringUtils.isBlank(vcode)||!vcode.equalsIgnoreCase(verifyCode)){	  
            	 data.put("result", "FAIL");
  				 data.put("message", "验证码错误，请重新输入！");
  				 response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
  				return;
             }       	 
             List<ZkkjUser> result = ((WebAuthenticationServiceImpl) authenticationService)
					.login(username, password, request);

			if (result!=null &&result.size()>0) {		
				data.put("result", "SUCCESS");
				data.put("message", "登录成功！");
				data.put("url",request.getContextPath()+REDIECT_URL);
				response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
			}else{
            	 data.put("result", "FAIL");
  				 data.put("message", "账号或密码错误！");
  				 response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
			}			
		} catch (Exception e) {

		}
	}
	
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
            Subject subject = new DefaultSubjectImpl();
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("auth_session",request.getSession());
            subject.setParam(param);
			boolean isLogout = ((WebAuthenticationServiceImpl) authenticationService)
					.logout(subject);

			if (isLogout) {
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","true");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));	
			}

		} catch (Exception e) {

		}
	}

}
