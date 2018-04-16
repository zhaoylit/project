package com.zkkj.chat.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkkj.chat.util.DESUtils;
import com.zkkj.chat.util.MailUtil;

@WebServlet("/MobileEmailEnrollSend")
public class MobileEmailEnrollSendServlet extends HttpServlet{
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
		String email =req.getParameter("email");
		String userName =req.getParameter("userName");
		String passWord =req.getParameter("passWord");
		String headUrl =req.getParameter("savePath");
		req.setAttribute("email", email);
		req.setAttribute("userName", userName);
		req.setAttribute("passWord", passWord);
		req.setAttribute("headUrl", headUrl);
		int randomNumber=(int) (Math.random()*900000+100000);
		String randomNumberString=String.valueOf(randomNumber);
		req.setAttribute("randomNumberString", randomNumberString);
		System.out.println("***********************************"+randomNumberString+"***********************************");
		MailUtil.sendEmail(email, "注册密令邮件确认", "您的6位注册密令为:"+randomNumberString+"。请在邮箱确认页面输入此密令，确认注册成功！此邮件为确认邮件，勿回!");
		req.getRequestDispatcher("/chat/mobile/mailLogin.jsp").forward(req, resp);
		
	}
	
}
