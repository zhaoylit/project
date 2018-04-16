package com.zyl.scan.controller;

import java.io.File;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.zyl.scan.exception.MyException;
import com.zyl.scan.service.ScanService;
import com.zyl.scan.util.EncryptUtils;
import com.zyl.scan.util.MyConfig;
import com.zyl.scan.util.ZXingPic;

@Controller
@ServerEndpoint(value="/webSocket")
@Component
@RequestMapping("socket")
public class WebSocketController {
    
    @Autowired
    private ScanService testService;
    //保存所有的连接的客户端对象
    private static ConcurrentMap<String, Session> clientsMap = new ConcurrentHashMap<String, Session>();
    //客户端的唯一idguid 和uuid的绑定
    private static ConcurrentMap<String,String> uuidMap = new ConcurrentHashMap<String,String>();
    //每个客户端guid 最新心心跳时间
    private static ConcurrentMap<String,Long> heartMap = new ConcurrentHashMap<String,Long>();
    //uuid的生成时间，用于判断是否超时
    private static ConcurrentMap<String,Long> uuidTimeMap = new ConcurrentHashMap<String,Long>();
    //二维码uuid绑定的扫码状态
    private static  ConcurrentMap<String, Integer> statusMap = new ConcurrentHashMap<String, Integer>();//1.扫码成功
    //二维码超时时间,秒
    private static final int QRCODE_TIME_OUT = 60;
    private static final Logger log = Logger.getLogger(SseController.class);
    
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
            statusMap.put(uuid, 0);//未扫码状态
            //记录uuid的当前时间戳
            uuidTimeMap.put(uuid, new Date().getTime());
        }
        //初始化timer
        String content = MyConfig.getReturnPath() + "socket/scan/" + guid;
        try {
            //获取logo image
            File logoFile = null;
            try {
                logoFile = ResourceUtils.getFile("classpath:static/img/logo.jpg");
            } catch (Exception e) {
                log.info("-------------------static/img/logo.jpg,文件不存在");
            }
//          BufferedImage logoImage = ImageIO.read(ScanController.class.getClassLoader().getResourceAsStream("static/img/logo.jpg"));
            String base64 = ZXingPic.getImagePath(content, logoFile,800,800);
            base64 = EncryptUtils.aesEncrypt(base64);
            Map<String,String> jsonMap = new HashMap<String, String>();
            jsonMap.put("base64", base64);
            jsonMap.put("uuid",uuidMap.get(guid));
            jsonMap.put("messageType","qrcode");//二维码
            String jsonStr = JSONObject.toJSONString(jsonMap);
            session.getBasicRemote().sendText(jsonStr);
        } catch (WriterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
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
         statusMap.remove(uuid);
         //移除客户端id和uuid的绑定
         uuidMap.remove(guid);
         //移除心跳
         heartMap.remove(guid);
         //移除uuid计时
         uuidTimeMap.remove(uuid);
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
    
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("returnPath", MyConfig.getReturnPath());
        model.addAttribute("socketUrl", MyConfig.getSocketUrl());
        return "socket";
    }
    /**
     * 扫码方法
     * @param uuid
     * @param model
     * @return
     * @throws MyException 
     */
    @RequestMapping("/scan/{guid}")
    @ResponseBody
    public void scan(@PathVariable("guid") String guid) throws MyException{
    	String uuid = "";
    	try {
    		uuid = uuidMap.get(guid);
            //判断二维码是否超时
    		Long timer = uuidTimeMap.get(uuid);
            long curTimer = new Date().getTime();
            //二维码60秒失效
            if((curTimer - timer)/1000 > QRCODE_TIME_OUT){
            	sendScanResultMessage(guid,"");
                statusMap.remove(uuid);
                return;
            }else{
            	if(statusMap.get(uuid) == null){//二维码过期
            		sendScanResultMessage(guid,"");
            		return;
            	}else if(statusMap.get(uuid) == 2){//重复扫码
            		sendScanResultMessage(guid,"2");
            		return;
            	}
            }
		} catch (Exception e) {
        	throw new MyException("二维码不存在");
		}
    	
    	/**
         * 验证用户，存session
         * 
         */
    	
    	sendScanResultMessage(guid,"1");//扫码成功
    	statusMap.put(uuid, 2);//设置扫码状态为已扫码
        return;
    }
    
    private static void sendScanResultMessage(String guid,String status){
    	Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("messageType", "scanResult");
        resultMap.put("status",status);
        String jsonStr = JSONObject.toJSONString(resultMap);
        sendMessage(guid, jsonStr);
    }
    
    
    /**
     * 扫码成功跳转
     * @param uuid
     * @param model
     * @return
     */
    @RequestMapping("/success/{guid}")
    public String success(@PathVariable("guid") String guid,Model model){
        model.addAttribute("guid", guid);
        return "success";
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
                                //移除扫码状态
                                statusMap.remove(uuid);
                                //移除客户端id和uuid的绑定
                                uuidMap.remove(entry.getKey());
                                //移除心跳
                                heartMap.remove(entry.getKey());
                                //移除uuid的时间记录
                                uuidTimeMap.remove(uuid);
                            }
                        }
                        /*log.info("clientsMap---------------------" + clientsMap);
                        log.info("uuidMap---------------------" + uuidMap);
                        log.info("statusMap---------------------" + statusMap);
                        log.info("heartMap---------------------" + heartMap);
                        log.info("uuidTimeMap---------------------" + uuidTimeMap);*/
                    } catch (Exception e) {
                        // TODO: handle exception
                        log.info("--------------------"+e.getMessage());
                    }
                } 
            }  
           },1, 10, TimeUnit.SECONDS);  
    }
}
