package com.web.modules.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user")
public class UserController {
	/**
	 * 查询用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userList")
	public ModelAndView userList(ModelAndView m,HttpServletRequest request,HttpServletResponse response){
		m.setViewName("/user/userList");
		return m;
	}
}
