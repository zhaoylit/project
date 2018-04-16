package com.zkkj.chat.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.Request;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.UserServiceImpl;
import com.zkkj.chat.util.MD5Util;

@WebServlet("/enrooll")
public class EnrollServlet extends HttpServlet{
	
	private IUserService userService = new UserServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		resp.setCharacterEncoding("UTF-8"); 
//		resp.setHeader("Content-type", "text/json;charset=UTF-8");
		PrintWriter out =resp.getWriter();
		String nickName= req.getParameter("userName");
		String account= req.getParameter("email");
		String password= req.getParameter("passWord");
		String headUrl= req.getParameter("headUrl");
		String headUrlString=headUrl.toString();
		String secretKey = req.getParameter("secretKey");
		if(!"".equals(secretKey) && secretKey != null){
			String randomNumberString=(String) req.getSession().getAttribute("randomNumberString");
			if(randomNumberString != null){
				if(!secretKey.equals(randomNumberString)){
					resultMap.put("result", "0");
					resultMap.put("msg", "验证码不正确！");
					out.write(JSONObject.fromObject(resultMap).toString());
					return;
				}
			}
		}
		Map params=new HashMap();
		params.put("nickName", nickName);
		params.put("account", account);
		params.put("password", MD5Util.string2MD5(password));
		params.put("headUrl", headUrlString);
		try {
            //判断账户是否存在
            Map accountParamsMap = new HashMap();
            accountParamsMap.put("account", account);
			List<Map> accountList = userService.getUserList(accountParamsMap);
			if(accountList!= null && accountList.size() > 0){
				resultMap.put("result", "0");
	        	resultMap.put("message", "该账户已存在");
	        	out.write(JSONObject.fromObject(resultMap).toString());
	        	return ;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int a=userService.addUser(params);
			if(a>0){
				resultMap.put("result", "1");
				resultMap.put("msg", "操作成功");
			}else {
				resultMap.put("result", "0");
				resultMap.put("msg", "数据库操作失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("result", "0");
			resultMap.put("msg", "系统异常");
			e.printStackTrace();
		}finally{
			out.write(JSONObject.fromObject(resultMap).toString());
		}
	}
	
}
