package com.zkkj.backend.common.socketio;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DefaultExceptionListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.exception.AdvertException;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.DESUtils;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.entity.biz.NoticeFlight;
import com.zkkj.backend.entity.biz.NoticePerson;
import com.zkkj.exceptionInfo.service.IExceptionRecordService;
import com.zkkj.exceptionInfo.service.impl.ExceptionRecordServiceImpl;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
public class BinaryEventLauncher{
	IDeviceService deviceService = new DeviceServiceImpl();
	IExceptionRecordService exceptionService = new ExceptionRecordServiceImpl();
	
    static SocketIOServer server;
    //uuid和当前连接上的SocketIOClient对象
    static Map<String, SocketIOClient> clientsMap = new ConcurrentHashMap<String, SocketIOClient>();
    //uuid和设备的详细信息
    static Map<String, Map> uuidDeviceMap = new HashMap<String, Map>();
    //uuid和设备最近一次的心跳时间
    static Map<String,Long> checkStatusMap = new ConcurrentHashMap<String, Long>();
    //uuid设备重启的次数
    static Map<String, String> deviceRBCount = new HashMap<String, String>();
    

	public static void startServer() throws InterruptedException{
		Configuration config = new Configuration();
		String socketIp = (String)CustomizedPropertyConfigurer.getContextProperty("socket_ip");
		String socketPort = (String)CustomizedPropertyConfigurer.getContextProperty("socket_port");
        config.setHostname(socketIp);
        config.setPort(Integer.parseInt(socketPort));
        config.getSocketConfig().setTcpKeepAlive(true);
        config.getSocketConfig().setReuseAddress(true);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024*5);
        server = new SocketIOServer(config);
        //添加设备重启通知事件
        server.addEventListener("device_reboot", String.class, new DataListener<String>(){
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws ClassNotFoundException {
            	String sa = client.getRemoteAddress().toString();
				String deviceIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
				JSONObject object = JSONObject.fromObject(data);
				String uuid = object.getString("uuid");//获取uuid
				if(data!=null&&!data.equals("")){
					System.out.println(DateUtil.getStringDate()+"---<"+deviceIp+">设备重启返回的信息******************************"+data);
					deviceRBCount.put(uuid,deviceIp);
					client.disconnect();
				}
            	/*JSONArray json = JSONArray.fromObject(data);*/
            }
        });
        //添加广告通知事件
        server.addEventListener("advert_info", String.class, new DataListener<String>(){
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws ClassNotFoundException {
            	String sa = client.getRemoteAddress().toString();
				String deviceIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
            	System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端反馈的信息******************************"+data);
            	/*JSONArray json = JSONArray.fromObject(data);*/
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
        server.addEventListener("apk_update", String.class, new DataListener<String>() {
        	@Override	
        	public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
        		String sa = client.getRemoteAddress().toString();
				String deviceIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
        		System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端反馈的信息******************************"+data);
        	}
        });
        //心跳事件
        server.addEventListener("heart_beat", String.class, new DataListener<String>() {
        	@Override	
        	public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
        		String sa = client.getRemoteAddress().toString();
				String deviceIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
				JSONObject object = JSONObject.fromObject(data);
				String uuid = object.getString("uuid");
				Long time = new Date().getTime();
				
				checkStatusMap.put(uuid,time);
				System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]心跳数据******************************"+object);
        	}
        });
        //反馈时间
        server.addEventListener("feedBack_info",String.class, new DataListener<String>() {
        	@Override	
        	public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws AdvertException {
				String uuid = "";//设备uuid
				Map params = client.getHandshakeData().getUrlParams();
				//获取设备id	
				Object deviceIdObject = params.get("deviceId");
				if(deviceIdObject != null){
					uuid = ((List<String>)deviceIdObject).get(0);
				}
				if(!"".equals(uuid)){
					new SocketUtil().FeedBackInfoHandel(uuidDeviceMap, new String[]{uuid,data});
				}
        	}
        });
        
        //添加连接事件
        server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				String sa = client.getRemoteAddress().toString();
				String deviceIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
				Map params = client.getHandshakeData().getUrlParams();
				//获取设备id	
				Object deviceIdObject = params.get("deviceId");
				//连接类型
				Object connectionTypeObject = params.get("connectionType");
				//节目单同步的id
				Object synchIdObject = params.get("synchId");
				//apkSynchId
				Object apkSynchIdObject = params.get("apkSynchId");
				String uuid = "",connectionType = "",synchId = "",apkSynchId = "";
				if(deviceIdObject != null){
					uuid = ((List<String>)deviceIdObject).get(0);
					clientsMap.put(uuid,client);
				}
				if(connectionTypeObject != null){
					connectionType = ((List<String>)connectionTypeObject).get(0);
				}
				if(synchIdObject != null){
					synchId = ((List<String>)synchIdObject).get(0);
				}
				if(apkSynchIdObject != null){
					apkSynchId = ((List<String>)apkSynchIdObject).get(0);
				}
				if(!"".equals(uuid)){
					new SocketUtil().OnConnectionHandelForSocketIO(client,new String[]{uuid,deviceIp,connectionType,synchId,apkSynchId});
				}
				
			}	
		});
        //添加断开连接事件
        server.addDisconnectListener(new DisconnectListener(){
			@Override	
			public void  onDisconnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				String sa = client.getRemoteAddress().toString();
				String deviceIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
				Map params = client.getHandshakeData().getUrlParams();
				//获取设备id
				Object deviceIdObject = params.get("deviceId");
				String uuid = "";
				if(deviceIdObject != null){
					uuid = ((List<String>)deviceIdObject).get(0);
				}
				if(!"".equals(uuid)){
					new SocketUtil().onDisConnectionHandelForSocketIO(client,new String[]{deviceIp,uuid});
				}
			}
		});
        server.addListeners(new DefaultExceptionListener());
        
      	server.start();
      	
        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
	}
	public static void stopServer(){
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
			client.sendEvent(eventType,message);
		}
	}
	/**
	 * 通过设备的uuid推送消息
	 * @param deviceId 设备类型
	 * @param eventType推送事件类型
	 * @param message 推送的消息内容
	 * @throws AdvertException 
	 */
	public static void sendMessageByUuid(String uuid,String eventType,String message) throws AdvertException{
		try {
			if(uuid != null && !"".equals(uuid)){
				SocketIOClient client = (SocketIOClient)clientsMap.get(uuid);
				if(client != null){
					client.sendEvent(eventType,message);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//存取异常信息
			AdvertException advertException=new AdvertException();
			advertException.setExceptionCode("SE_AD_ERROR");
			advertException.setStackTrace(e.getStackTrace());
			throw advertException;
		}
	}
	/**
	 * 发送公告
	 * @throws Exception
	 */
	public static Map<String,String> sendFlightNotice(String message){
		Map<String,String> result = new HashMap<String,String>(2); 
		try{		
		int count = 0;
		if(clientsMap != null){
			for(String key : clientsMap.keySet()){
				SocketIOClient client = clientsMap.get(key);
				client.sendEvent("notice_info",DESUtils.encryptDES(message, "AC671618"));
				count ++;
			}
		}
		result.put("message","发送成功！已发往 "+count+" 台设备。");	
		
		}catch(Exception e){
			result.put("result",IConstant.DEFAULT_CODE_FAIL);
			result.put("message",IConstant.PUASH_EORROR);		
		}
		return result;
	}
	
	
	public static SocketIOServer getServer() {
		return server;
	}
	public static Map<String, Long> getCheckStatusMap() {
		return checkStatusMap;
	}
	public void cleanCheckStatusMap(String key){
		checkStatusMap.remove(key);
	}
	public void cleanClientsMap(String key){
		clientsMap.remove(key);
	}
	public static Map<String, SocketIOClient> getClientsMap() {
		return clientsMap;
	}
	public static Map<String, String> getDeviceRBCount() {
		return deviceRBCount;
	}
	public static void setDeviceRBCount(Map<String, String> deviceRBCount) {
		BinaryEventLauncher.deviceRBCount = deviceRBCount;
	}
	public static void main(String[] args) {
//		String ss = "/192.168.3.13:54071";
		String message = "{'result':[a:[{a:'1',b:'1'},{a:'1',b:'1'}],b:[{a:'1',b:'1'},{a:'1',b:'1'}]]}";
		/*try {
			new BinaryEventLauncher().sendProgramMessage1("",message);
//			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			System.out.println("1233");
		}*/
		
	}
}
