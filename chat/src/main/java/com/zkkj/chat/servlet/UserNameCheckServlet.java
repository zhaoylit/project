package com.zkkj.chat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.UserServiceImpl;

@WebServlet("/UserNameCheck")
public class UserNameCheckServlet extends HttpServlet{
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
		resp.setCharacterEncoding("UTF-8"); 
		resp.setHeader("Content-type", "text/json;charset=UTF-8");
		PrintWriter out =resp.getWriter();
		String userName=req.getParameter("userName");
		Map params=new HashMap();
		params.put("nickName", userName);
		Map resultMap = new HashMap();
		
		List<Map> userList;
		try {
			userList = userService.getUserList(params);
			if (userList.size()>0) {
				resultMap.put("msg", "1");
			}else {
				resultMap.put("msg", "2");
			}
		} catch (Exception e) {
			resultMap.put("msg", "3");
			e.printStackTrace();
		}finally{
			out.write(JSONObject.fromObject(resultMap).toString());
		}
	}
}
