package com.zkkj.backend.common.mail;

import java.io.UnsupportedEncodingException;

public class MailUse implements Runnable{
	private String emailString;
	private String subjectString;
	private String bodyString;
	private int sendTime;
	
	public String getEmailString() {
		return emailString;
	}

	public void setEmailString(String emailString) {
		this.emailString = emailString;
	}

	public String getBodyString() {
		return bodyString;
	}

	public void setBodyString(String bodyString) {
		this.bodyString = bodyString;
	}

	public int getSendTime() {
		return sendTime;
	}

	public void setSendTime(int sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(sendTime);
			MailUtil.sendEmail(emailString, "贵宾厅智能候机系统异常邮件",bodyString);
			System.out.println("邮件发送成功.....发送邮箱为"+emailString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
