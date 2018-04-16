package com.zkkj.chat.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.runner.Request;

import com.sun.mail.iap.Response;
import com.zkkj.chat.util.DESUtils;
import com.zkkj.chat.util.MailUtil;
import com.zkkj.chat.util.PropertiesConfig;

@WebServlet("/EmailEnrollSend")
public class EmailEnrollSendServlet extends HttpServlet{
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
		String method = req.getParameter("method");
		String email =req.getParameter("email");
		String userName =req.getParameter("userName");
		String passWord =req.getParameter("passWord");
		String headUrl =req.getParameter("savePath");
		
		if("getEmailCode".equals(method)){
			resp.setContentType("application/text; charset=utf-8");
            // 设置字符编码为UTF-8, 这样支持汉字显示  
			resp.setCharacterEncoding("UTF-8");  
			PrintWriter out = resp.getWriter();
			if("".equals(email)){
				out.write("0");
				out.flush();
				out.close();
				return;
			}
			try {
				int randomNumber=(int) (Math.random()*900000+100000);
				String randomNumberString=String.valueOf(randomNumber);
				req.getSession().setAttribute("randomNumberString", randomNumberString);
				MailUtil.sendEmail(email, "注册密令邮件确认", "您的6位注册密令为:"+randomNumberString+"。请在邮箱确认页面输入此密令，确认注册成功！此邮件为确认邮件，勿回!");
				out.write("1");
				out.flush();
				out.close();
				return;
			} catch (Exception e) {
				// TODO: handle exception
				out.write("0");
				out.flush();
				out.close();
				return;
			}
			
		}else if("getEmailCodePC".equals(method)){
			resp.setContentType("application/text; charset=utf-8");
            // 设置字符编码为UTF-8, 这样支持汉字显示  
			resp.setCharacterEncoding("UTF-8");  
			PrintWriter out = resp.getWriter();
			if("".equals(email)){
				out.write("0");
				out.flush();
				out.close();
				return;
			}
			try {
				int randomNumber=(int) (Math.random()*900000+100000);
				String randomNumberString=String.valueOf(randomNumber);
				req.getSession().setAttribute("randomNumberString", randomNumberString);
				MailUtil.sendEmail(email, "注册密令邮件确认", "您的6位注册密令为:"+randomNumberString+"。请在邮箱确认页面输入此密令，确认注册成功！此邮件为确认邮件，勿回!");
				out.write("1");
				out.flush();
				out.close();
				return;
			} catch (Exception e) {
				// TODO: handle exception
				out.write("0");
				out.flush();
				out.close();
				return;
			}
		}
		
	}
	
}
