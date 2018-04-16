package com.zyl.scan.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.zyl.scan.service.ScanService;
import com.zyl.scan.util.EncryptUtils;
import com.zyl.scan.util.ZXingPic;

/**
 * <p>Description: 扫码登录</p>
 * @author zyl
 * @date 2017年12月7日
 * @version 1.0
 */
@Controller
@RequestMapping("sse")
public class SseController {
    @Autowired
    private ScanService testService;
    //请求地址
    @Value("${my.returnPath}")
    private String url;
    //客户端的唯一idguid 和uuid的绑定
    private static ConcurrentMap<String,String> uuidMap = new ConcurrentHashMap<String,String>();
    //每个客户端guid 最新心心跳时间
    private static ConcurrentMap<String,Long> heartMap = new ConcurrentHashMap<String,Long>();
    //二维码uuid绑定的扫码状态
    private static  ConcurrentMap<String, Integer> statusMap = new ConcurrentHashMap<String, Integer>();//1.扫码成功
    //定时器间隔
    private static final int interval = 1000 * 30;
    //客户端唯一唯一id guid绑定的定时器，定时器用于更新uuid重新生成二维码
    private static Map<String,Timer> timerMap = new HashMap<String,Timer>();
    private static final Logger log = Logger.getLogger(SseController.class);
    
    static{
        checkHeart();
    }
    @RequestMapping("/")
    public String home() {
        return "sse";
    }
    
    @RequestMapping("/push/{id}")
    public void push(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
        final String guid = id;
        //更新心跳时间
        heartMap.put(guid, new Date().getTime());
    	response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/event-stream");
        response.setHeader("Expires", "-1"); 
        response.setHeader("Cache-Control", "no-cache"); 
        PrintWriter pw = response.getWriter();
        if(uuidMap.get(guid) == null){
            String uuid = UUID.randomUUID().toString();
            uuidMap.put(guid,uuid);
            statusMap.put(uuid, 0);//未扫码状态
        }
        if(timerMap.get(guid) == null){
            //启动定时器
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String uuid = UUID.randomUUID().toString();
                    statusMap.remove(uuidMap.get(guid));
                    uuidMap.put(guid,uuid);
                    statusMap.put(uuid, 0);//未扫码状态
                }
            },interval,interval);
            timerMap.put(guid, timer);
        }
        
        
        String content = url + "scan/" + uuidMap.get(guid);
        try {
        	//获取logo image
        	File logoFile = null;
        	try {
        		logoFile = ResourceUtils.getFile("classpath:static/img/logo.jpg");
			} catch (Exception e) {
				log.info("-------------------static/img/logo.jpg,文件不存在");
			}
//        	BufferedImage logoImage = ImageIO.read(ScanController.class.getClassLoader().getResourceAsStream("static/img/logo.jpg"));
            String base64 = ZXingPic.getImagePath(content, logoFile,800,800);
            base64 = EncryptUtils.aesEncrypt(base64);
            Map<String,String> jsonMap = new HashMap<String, String>();
            jsonMap.put("base64", base64);
            jsonMap.put("uuid",uuidMap.get(guid));
            String jsonStr = JSONObject.toJSONString(jsonMap);
            pw.write("retry: 10000\n");
            pw.write("data: " + jsonStr  +"\n\n");//输出（始终以 "data: " 开头,\n\n结束）,  
            pw.flush();
        } catch (WriterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    @RequestMapping("/result/{uuid}")
    public void result(@PathVariable("uuid") String uuid,HttpServletRequest request,HttpServletResponse response) throws IOException{  
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/event-stream");
        response.setHeader("Expires", "-1"); 
        response.setHeader("Cache-Control", "no-cache"); 
        PrintWriter pw = response.getWriter();
        Map<String,String> jsonMap = new HashMap<String, String>();
        String result = "0";
        //检测uuid是否登录成功
        if(statusMap.get(uuid) != null && statusMap.get(uuid) == 1){
            //登录成功
            result = "1";
        }
        jsonMap.put("result", result);
        jsonMap.put("uuid", uuid);
        String jsonStr = JSONObject.toJSONString(jsonMap);
        pw.write("data: " + jsonStr  +"\n\n");//输出（始终以 "data: " 开头,\n\n结束）,  
        pw.flush();
    }  
    /**
     * 扫码方法
     * @param uuid
     * @param model
     * @return
     */
    @RequestMapping("/scan/{uuid}")
    @ResponseBody
    public void scan(@PathVariable("uuid") String uuid){
    	/**
    	 * 验证用户，存session
    	 * 
    	 */
        if(statusMap.get(uuid) == null){
        	log.info("-------------------------------二维码超时,uuid:" + uuid);
        }else if(statusMap.get(uuid) == 1){
        	log.info("-------------------------------重复扫码,uuid:" + uuid);
        }else if(statusMap.get(uuid) == 0){
        	log.info("-------------------------------扫码成功,uuid:" + uuid);
            statusMap.put(uuid, 1);
        }
    }
    
    /**
     * 扫码成功跳转
     * @param uuid
     * @param model
     * @return
     */
    @RequestMapping("/success/{uuid}")
    public String success(@PathVariable("uuid") String uuid,Model model){
        model.addAttribute("uuid", uuid);
        return "success";
    }
    /**
     * 页面刷新后 删除之前生成的定时器
     */
    public static void checkHeart(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {  
            public void run() {  
//            	log.info("------------------------------心跳检测开始");
                if(heartMap != null){
                	try {
                		for (Map.Entry<String, Long> entry : heartMap.entrySet()) {  
                            long timer = entry.getValue();
                            long curTimer = new Date().getTime();
                            //相差大于30秒 结束定时器
                            if((curTimer - timer)/1000 > 30){
                            	timerMap.get(entry.getKey()).cancel();
                                timerMap.remove(entry.getKey());
                                String uuid = uuidMap.get(entry.getKey());
                                //移除扫码状态
                                statusMap.remove(uuid);
                                //移除客户端id和uuid的绑定
                                uuidMap.remove(entry.getKey());
                                //移除心跳
                                heartMap.remove(entry.getKey());
                            }
                        }
                        /*log.info("timerMap---------------------" + timerMap);
                        log.info("uuidMap---------------------" + uuidMap);
                        log.info("statusMap---------------------" + statusMap);
                        log.info("heartMap---------------------" + heartMap);*/
					} catch (Exception e) {
						// TODO: handle exception
						log.info("--------------------"+e.getMessage());
					}
                } 
            }  
           },1, 30, TimeUnit.SECONDS);  
    }
}
