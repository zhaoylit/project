package com.zj.api.common.socketio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zj.api.common.utils.CustomizedPropertyConfigurer;
import com.zj.api.common.utils.ParamsUtil;
@Service("binaryEventLauncher")
public class BinaryEventLauncher{
    static SocketIOServer server;
    static Map<String, SocketIOClient> clientsMap = new HashMap<String, SocketIOClient>();
    static Map<String, SocketIOClient> ipMap = new HashMap<String, SocketIOClient>();
	public void startServer() throws InterruptedException{
		Configuration config = new Configuration();
		String socketIp = (String)CustomizedPropertyConfigurer.getContextProperty("socket_ip");
		int socketPort = (int)CustomizedPropertyConfigurer.getContextProperty("socket_port");
        config.setHostname(socketIp);
//        config.setHostname("192.168.200.252"); 
        config.setPort(socketPort);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
        //添加广告通知事件
        server.addEventListener("advert_info", String.class, new DataListener<String>(){
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws ClassNotFoundException {
            	JSONArray json = JSONArray.fromObject(data);
        		for(Object object : json){
        			String advertId = String.valueOf(object);
        			if(!"".equals(advertId)){
        				try {
        					
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        		}
            }
        });
        //添加航班提醒事件(找人提醒，航班公告)
        server.addEventListener("notice_info", String.class, new DataListener<String>() {
            @Override	
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
            	System.out.println("-----receive data is："+data);
              //  client.sendEvent("notice_info", data);
            }
        });
        //apk远程更新
        server.addEventListener("apk_update", byte[].class, new DataListener<byte[]>() {
        	@Override	
        	public void onData(SocketIOClient client, byte[] data, AckRequest ackRequest) {
        		
        	}
        });
        //添加连接事件
        server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				String sa = client.getRemoteAddress().toString();
				String deviceIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
				System.out.println(sa+"-------------------------"+"客户端已连接");
				Map params = client.getHandshakeData().getUrlParams();
				//获取设备id
				String deviceId = ParamsUtil.nullDeal(params, "deviceId", "");
				clientsMap.put(deviceId,client);
				//推送消息
				try {
					//自动查询节目单
//					autoPushProgramMessage(deviceIp,deviceId);
//					autoPushApkUpdateMessage(deviceIp,deviceId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        //添加断开连接事件
        server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println(client.getRemoteAddress()+"-------------------------"+"客户端已断开连接");
			}
		});
      	  server.start();
        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
	}
	public void stopServer(){
		if(server != null){
			server.stop();
			server = null;
		}
	}
	/**
	 *  给所有的客户端推送消息
	 * @param eventType 推送的事件类型
	 * @param message  推送的内容
	 */
	public void sendMessageToAllClient(String eventType,String message){
		Collection<SocketIOClient> clients = server.getAllClients();
		for(SocketIOClient client: clients){
			//获取获取握手数据里面的url参数
			/*Map params = client.getHandshakeData().getUrlParams();
			//获取设备id
			String deviceId_ = ParamsUtil.nullDeal(params, "", "");
			//远程连接地址
			SocketAddress remoteAddress = client.getRemoteAddress();*/
			client.sendEvent(eventType,message);
		}
	}
	/**
	 * 给具体的客户端推送消息
	 * @param deviceId 设备类型
	 * @param eventType推送事件类型
	 * @param message 推送的消息内容
	 */
	public void sendMessageToOneClient(String deviceIp,String connectionId,String eventType,String message){
		try {
			if(connectionId != null && !"".equals(connectionId)){
				SocketIOClient client = (SocketIOClient)clientsMap.get(connectionId);
				if(client != null){
					client.sendEvent(eventType,message);
				}
			}else if(deviceIp != null && !"".equals(deviceIp)){
				SocketIOClient client = (SocketIOClient)ipMap.get(deviceIp);
				if(client != null){
					client.sendEvent(eventType,message);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SocketIOServer getServer() {
		return server;
	}
}
