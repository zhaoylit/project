package com.zkkj.chat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.UserServiceImpl;

import net.sf.json.JSONArray;

@WebServlet("/UserUpdatePWD")
public class UserUpdatePWDServlet extends HttpServlet {
	private IUserService userService=new UserServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setCharacterEncoding("UTF-8"); 
		PrintWriter out =resp.getWriter();
		Map parameter =new HashMap();
		
		String userId=req.getParameter("userId");
		String userPwdString;
		try {
			userPwdString = userService.updateUserPWDOne(userId);
			parameter.put("PWD", userPwdString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArray =new JSONArray().fromObject(parameter);
		System.out.println(jsonArray);
		out.write(jsonArray.toString());
	}
}
