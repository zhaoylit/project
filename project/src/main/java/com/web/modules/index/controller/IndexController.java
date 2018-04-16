package com.web.modules.index.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.constant.Constant;
import com.web.modules.authority.service.IAuthorityService;
import com.web.modules.index.service.IIndexService;
import com.web.util.ParamsUtil;
import com.web.util.ReflectUtil;

@Controller
public class IndexController {
	
	@Autowired
	private IAuthorityService authorityService;
	@Autowired
	private IIndexService indexService;
	
	private Logger log = Logger.getLogger(IndexController.class);
	/**
	 * 跳转到登陆页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView m,HttpServletRequest  request,HttpServletResponse response){
		m.setViewName("/index/login");	
		return m;
	}
	/**
	 * 跳转到登录页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView m,HttpServletRequest request,HttpServletResponse response){
		m.setViewName("/index/login");
		return m;
	}
	/**
	 * 跳转到首页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main(ModelAndView m,HttpServletRequest request,HttpServletResponse response){
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			Integer roleId = null;
			Map userMap = (Map<String, String>) request.getSession().getAttribute(Constant.SESSION_USER);
			String account = String.valueOf(userMap.get("userName"));
			if(Constant.ADMIN_ACCOUNT.equals(account)){
				roleId = 0;
			}else{
				roleId = Integer.parseInt(ParamsUtil.nullDeal(userMap, "roleId", "0"));
			}
			//查询用户权限
			String menuHtml = authorityService.getMenuByRole(roleId);
			m.addObject("menuHtml",menuHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.setViewName("/index/main");
		return m;
	}
	/**
	 * 登录验证
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/checkLogin")
	@ResponseBody
	public void checkLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			resultMap = indexService.checkLogin(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
			resultMap.put("resultCode", "0");
			resultMap.put("resultMsg", e.getMessage());
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}
	}
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/quit")
	public ModelAndView quit(ModelAndView m,HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute(Constant.SESSION_USER);
		m.setViewName("/index/login");
		return m;
	}
}
