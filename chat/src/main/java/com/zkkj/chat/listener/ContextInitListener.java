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

public class ContextInitListener implements ServletContextListener {

	private IUserService userService = new UserServiceImpl();
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
//		加载classpath下的配置文件
		InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties");
		if(in == null){
			return;
		}
		Properties prop = new Properties();
		Map<String,String> propertiesMap=new HashMap<String, String>();
		try {    
		     prop.load(in);    
		     Iterator<String> it=prop.stringPropertyNames().iterator();
		     while(it.hasNext()){
		    	 String key = it.next();
		    	 String value = prop.getProperty(key);
		    	 propertiesMap.put(key, value);
		     }
		     in.close();
		} catch (IOException e) { 
		      e.printStackTrace();    
		}
	    PropertiesConfig.setPropertiesMap(propertiesMap);
	    
	    //启动定时任务
	    final long timeInterval = 10000;  
        Runnable runnable = new Runnable() {  
            public void run() {  
                while (true) {  
                    // ------- code for task to run  
                    Map<String,Long> heartMap = Websocket.getHeartMap();
                    
                    //查询在线的用户列表
					Map userParamsMap = new HashMap();
					userParamsMap.put("onlineStatus",1);
					List<Map> userList = null;
					try {
						userList = userService.getUserList(userParamsMap);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(userList != null && userList.size() > 0){
						for(Map userMap : userList){
							String userId = String.valueOf(userMap.get("id"));
							//判断如果时间间隔 大于15秒 则关闭socket连接
	                    	Long time = heartMap.get(userId);
	    					if(time == null || (new Date().getTime() - time)/1000 > 15){
	    						//断开socket 连接
	    						try {
	    							//修改在线状态为离线
									Map updateParamsMap = new HashMap();
									updateParamsMap.put("userId", userId);
									updateParamsMap.put("onlineStatus", "0");
									int count = userService.updateUser(updateParamsMap);
							
									if(count > 0){
										System.out.println("==================================" + userMap.get("nickName") + "("+userMap.get("account")+") socket连接超时");
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	    					}
						}
					}
                    // ------- ends here  
                    try {  
                        Thread.sleep(timeInterval);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        };  
        Thread thread = new Thread(runnable);  
        thread.start();  
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
		
}
