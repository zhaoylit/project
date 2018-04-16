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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

@WebServlet("/exitUserSession")
public class ExitUserSessionServlet extends HttpServlet{
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
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out =resp.getWriter();
		Map resultMap = new HashMap();
		try {
			HttpSession session = req.getSession();
			session.removeAttribute("userSession");
			resultMap.put("msg", "1");
		} catch (Exception e) {
			resultMap.put("msg", "2");
		}finally{
			out.write(JSONObject.fromObject(resultMap).toString());
		}
	}
}
