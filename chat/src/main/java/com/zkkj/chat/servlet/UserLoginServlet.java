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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.UserServiceImpl;
import com.zkkj.chat.util.MD5Util;

@WebServlet("/userlogin")
public class UserLoginServlet extends HttpServlet{
	
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
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out =resp.getWriter();
		String account=req.getParameter("account");
		String password=req.getParameter("password");
		String checkCode=req.getParameter("checkCode");
		HttpSession session=req.getSession();
		String randCheckCode=(String) session.getAttribute("randCheckCode");
		
		Map resultMap = new HashMap();
		
		Map params =new HashMap();
		params.put("account", account);
		try {
			List<Map>userList=userService.getUserList(params);
			
			if (randCheckCode.equals(checkCode)) {
				if (userList.size()>0) {
					String passwordMi=MD5Util.string2MD5(password);
					if (userList.get(0).get("password").equals(passwordMi)) {
						resultMap.put("msg", "success");
						req.getSession().setAttribute("userSession", userList.get(0));
					}else {
						resultMap.put("msg", "wrongPassword");
					}
				}else{
					resultMap.put("msg", "NoAccount");
				}
			}else{
				resultMap.put("msg", "checkCode");
			}
			
			
		} catch (Exception e) {
			resultMap.put("msg", "networkAnomaly");
			e.printStackTrace();
		}
		out.write(JSONObject.fromObject(resultMap).toString());
	}
}
