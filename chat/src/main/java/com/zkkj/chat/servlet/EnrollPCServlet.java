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

import org.junit.runner.Request;

import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.UserServiceImpl;
import com.zkkj.chat.util.MD5Util;

@WebServlet("/EnrollPC")
public class EnrollPCServlet extends HttpServlet{
	private IUserService userService = new UserServiceImpl();
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
		
		resp.setContentType("application/text; charset=utf-8");
        // 设置字符编码为UTF-8, 这样支持汉字显示  
		resp.setCharacterEncoding("UTF-8");  
		PrintWriter out = resp.getWriter();
		String email=req.getParameter("email");
		String userName=req.getParameter("userName");
		String passWord=req.getParameter("passWord");
		String headUrl=req.getParameter("headUrl");
		String emailCode =req.getParameter("emailCode");
		String randomNumberStringPC = "";
		if(req.getSession().getAttribute("randomNumberStringPC") != null){
			randomNumberStringPC = (String) req.getSession().getAttribute("randomNumberStringPC");
		}
		Map params=new HashMap();
		params.put("nickName", userName);
		params.put("account", email);
		params.put("password", MD5Util.string2MD5(passWord));
		params.put("headUrl", headUrl);
		try {
			if (randomNumberStringPC.equals(emailCode)) {
					int a=userService.addUser(params);
					out.write("1");
				}
			else{
				out.write("3");
			}
		}
		 catch (Exception e) {
				out.write("0");
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
		
	}
}



