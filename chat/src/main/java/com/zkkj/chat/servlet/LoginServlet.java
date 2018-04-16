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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap();
		String method = request.getParameter("method");
		//用户登录
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		
		//查询之前的消息记录
		response.setContentType("application/json; charset=utf-8");
        // 设置字符编码为UTF-8, 这样支持汉字显示  
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        if(account == null || "".equals(account)){
        	resultMap.put("result", "0");
        	resultMap.put("message", "账户不能为空");
        	out.write(JSONObject.fromObject(resultMap).toString());
        	return ;
        }
        if(password == null || "".equals(password)){
        	resultMap.put("result", "0");
        	resultMap.put("message", "密码不能为空");
        	out.write(JSONObject.fromObject(resultMap).toString());
        	return ;
        }
        if(code == null || "".equals(code)){
        	resultMap.put("result", "0");
        	resultMap.put("message", "验证码不能为空");
        	out.write(JSONObject.fromObject(resultMap).toString());
        	return ;
        }
        String code_ =  "";
        String randCheckCodeMobile = "";
		if(request.getSession().getAttribute("checkcode") != null){
			randCheckCodeMobile = (String) request.getSession().getAttribute("checkcode");
		}
        if(randCheckCodeMobile != null && !"".equals(randCheckCodeMobile)){
        	code_ = randCheckCodeMobile;
        	
        }
        if(!code_.toLowerCase().equals(code)){
        	resultMap.put("result", "0");
        	resultMap.put("message", "验证码不正确");
        	out.write(JSONObject.fromObject(resultMap).toString());
        	return ;
        }
        
		//根据用户名和密码查询用户信息
		Map params = new HashMap();
		params.put("account", account);
		params.put("password", password);
		try {
			List<Map> userList = userService.getUserList(params);
			if(userList == null || userList.size() == 0){
				resultMap.put("result", "0");
	        	resultMap.put("message", "账号或者密码有误");
	        	out.write(JSONObject.fromObject(resultMap).toString());
	        	return;
			}
			else{
				request.getSession().setAttribute("userSession", userList.get(0));
				resultMap.put("result", "1");
	        	out.write(JSONObject.fromObject(resultMap).toString());
	        	return ;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
