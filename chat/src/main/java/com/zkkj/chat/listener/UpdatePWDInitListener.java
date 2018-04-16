package com.zkkj.chat.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.UserServiceImpl;
import com.zkkj.chat.socket.Websocket;
import com.zkkj.chat.util.DateUtil;
import com.zkkj.chat.util.PropertiesConfig;
import com.zkkj.chat.util.TimerModifyPasswordUtil;

public class UpdatePWDInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		int user_PWD_cycle = Integer.parseInt(PropertiesConfig.getProperties("user_PWD_cycle"));
		TimerModifyPasswordUtil.updataPWD(user_PWD_cycle*1000*3600*24);
		System.out.println("********************************定时函数启动，修改周期为"+user_PWD_cycle+"天*********************************************");
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}
		
}
