package com.zkkj.chat.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.crypto.Data;

import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.UserServiceImpl;

public class TimerModifyPasswordUtil {
	private static Timer timer=new Timer(); ;
	
	public static void updataPWD(int time){
		timer.schedule(new MyTask(), time, time);  
	}
	public static void shutDown(){
		timer.cancel();
	}
//	public static void main(String[] args) throws InterruptedException {
//		TimerModifyPasswordUtil.updataPWD(200000);
//		TimerModifyPasswordUtil.shutDown();
//	}
}
class MyTask extends TimerTask {  
	private static IUserService userService=new UserServiceImpl();
    @Override  
    public void run() {  
    	try {
			userService.timerUpdatePWD();
			System.out.println("********************************定时函数修改全部用户密码*********************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
  
}  