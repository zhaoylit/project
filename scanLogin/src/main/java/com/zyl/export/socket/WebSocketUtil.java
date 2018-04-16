package com.zyl.export.socket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@ServerEndpoint(value="/socket")
@Component
public class WebSocketUtil {
    
    //保存所有的连接的客户端对象
    private static ConcurrentMap<String, Session> clientsMap = new ConcurrentHashMap<String, Session>();
    //客户端的唯一idguid 和uuid的绑定
    private static ConcurrentMap<String,String> uuidMap = new ConcurrentHashMap<String,String>();
    //每个客户端guid 最新心心跳时间
    private static ConcurrentMap<String,Long> heartMap = new ConcurrentHashMap<String,Long>();
    private static final Logger log = Logger.getLogger(WebSocketUtil.class);
    
    static{
    	//定时检测客户端的连接状态
        checkHeart();
    }
    
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    @OnOpen
    public void onOpen(Session session) throws Exception {
        this.session = session;
        Map<String,List<String>> params = session.getRequestParameterMap();
        String guid = "";
        Object guidObject = params.get("guid");
        if (guidObject != null) {
            guid = ((List<String>)guidObject).get(0);
            clientsMap.put(guid, session);
		}
        if(uuidMap.get(guid) == null){
            String uuid = UUID.randomUUID().toString();
            uuidMap.put(guid,uuid);
        }
    }
    /*
	*用户断开链接后的回调，注意这个方法必须是客户端调用了断开链接方法后才会回调
	*/
    @OnClose
    public void onClose(Session session) {
    	 Map<String,List<String>> params = session.getRequestParameterMap();
         String guid = "";
         Object guidObject = params.get("guid");
         if (guidObject != null) {
             guid = ((List<String>)guidObject).get(0);
 		 }
         //删除客户端session
         clientsMap.remove(guid);
         //删除扫码状态
         String uuid = uuidMap.get(guid);
         //移除客户端id和uuid的绑定
         uuidMap.remove(guid);
         //移除心跳
         heartMap.remove(guid);
         try {
			session.close();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
    }
    @OnMessage
    public void onMessage(String message,Session session){
    	JSONObject json = JSONObject.parseObject(message);
    	String guid = json.getString("guid");
        String messageType = json.getString("messageType");//消息类型,1.心跳消息
        if("beat".equals(messageType)){
            //更新心跳时间
            heartMap.put(guid, new Date().getTime());
        }
    }
    /**
     * @param guid
     * @param message
     * 推送提示的消息
     */
    public static void sendInfoMessage(String guid,String message){
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("type", "info");
        jsonMap.put("message", message);
        jsonMap.put("guid", guid);
        Session session = clientsMap.get(guid);
        try {
            //推送消息
            if(session != null){
                session.getBasicRemote().sendText(JSONObject.toJSONString(jsonMap));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("---------------------发送消息失败");
        }
        
    }

    /**
     * @param guid
     * @param message
     * 推送结果的消息
     */
    public static void sendMessage(String guid,String message){
        Session session = clientsMap.get(guid);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("---------------------发送消息失败");
        }
    }
    
    /**
     * 线程检测客户端的连接状态
     */
    public static void checkHeart(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {  
            public void run() {  
//              log.info("------------------------------心跳检测开始");
                //监测客户端心跳
                if(heartMap != null){
                    try {
                        for (Map.Entry<String, Long> entry : heartMap.entrySet()) {  
                            long timer = entry.getValue();
                            long curTimer = new Date().getTime();
                            //相差大于30秒 结束定时器
                            if((curTimer - timer)/1000 > 30){
                                String uuid = uuidMap.get(entry.getKey());
                                //移除客户端session 
                                clientsMap.remove(entry.getKey());
                                //移除客户端id和uuid的绑定
                                uuidMap.remove(entry.getKey());
                                //移除心跳
                                heartMap.remove(entry.getKey());
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        log.info("--------------------"+e.getMessage());
                    }
                } 
            }  
           },1, 10, TimeUnit.SECONDS);  
    }
}
