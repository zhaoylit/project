package com.zkkj.backend.common.websocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.exception.AdvertException;
import com.zkkj.backend.common.exception.NetworkException;
import com.zkkj.backend.common.exception.ServerException;
import com.zkkj.backend.common.socketio.SocketUtil;
import com.zkkj.backend.common.util.DESUtils;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.exceptionInfo.service.IExceptionRecordService;
import com.zkkj.exceptionInfo.service.impl.ExceptionRecordServiceImpl;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;

@ServerEndpoint(value = "/ws", configurator = SpringConfigurator.class)
public class WebSocketLauncher{
	private static final Logger log = LoggerFactory
			.getLogger(WebSocketLauncher.class);

	/*private static ClassPathXmlApplicationContext context = null;
	{
		context = new ClassPathXmlApplicationContext(
				new String[] { "spring-mvc.xml" });
		context.start();
	}*/

	static IDeviceService deviceService = new DeviceServiceImpl();
	IExceptionRecordService exceptionService = new ExceptionRecordServiceImpl();
	
	static Map<String, Session> clientsMap = new ConcurrentHashMap<String, Session>();
	// 连接上socket查询设备的详细信息
	static Map<String, Map> uuidDeviceMap = new HashMap<String, Map>();

	static Map<String, Long> checkStatusMap = new ConcurrentHashMap<String, Long>();

	static Map<String, String> deviceRBCount = new HashMap<String, String>();

	private Session session;
	
	// 循环Ping客户端，如果ping不通则关闭连接别更新设备连接状态
	private static ScheduledExecutorService executorService = Executors
			.newScheduledThreadPool(2);

	private static class PingTask implements Runnable {
		@Override
		public void run() {
		
			for (String deviceId : clientsMap.keySet()) {
				try {
					Session client = clientsMap.get(deviceId);
				//	log.error("------PingTask 开始ping客户端   1  ------："+deviceId);
					AtomicLong beforeTick = (AtomicLong)client.getUserProperties().get("beforeTick");
					AtomicLong afterTick = (AtomicLong)client.getUserProperties().get("afterTick");
					RemoteEndpoint.Basic sync = client.getBasicRemote();
					if(beforeTick!=null&&afterTick!=null && afterTick.get()<=beforeTick.get()){
						log.error("------Ping客户端不通，即将关闭socket------"+String.valueOf(client.getUserProperties().get("deviceIp")));
						Map<String, String> deviceParamsMap = new HashMap<String, String>(2);
						String uuid = deviceId;
						deviceParamsMap.put("uuid", uuid);
						deviceParamsMap.put("connectionStatus", "2");
						try {
							deviceService.updateDeviceByUuid(deviceParamsMap);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			
						clientsMap.remove(uuid);
						uuidDeviceMap.remove(uuid);
						client.close();		
						
					}else{		
					//	log.error("------Ping Ping Ping------："+deviceId);
						long tick = new Date().getTime();
						client.getUserProperties().put("beforeTick", new AtomicLong(tick));
						client.getUserProperties().put("afterTick", new AtomicLong(tick));
	/*					Map<String,String> resBody = new HashMap<String,String>(2);
						Map<String,String> res = new HashMap<String,String>(2);				
						resBody.put("data", "ping");
						res.put("method", "PING");
						res.put("tick",String.valueOf(tick));
						res.put("message", JSONObject.fromObject(resBody).toString());
						sync.sendText(JSONObject.fromObject(res).toString());*/					
						sync.sendPing(ByteBuffer.wrap(String.valueOf(tick).getBytes("UTF-8")));				
					}
				} catch (Exception e) {			
						log.error("------Ping客户端不通，发生异常------"+e.getMessage());		
				}
			}
		}

	}

	static {
		long time = new Date().getTime();
		executorService.scheduleAtFixedRate(new PingTask(), 15, 15,TimeUnit.SECONDS);
	}

	@OnOpen
	public void handleOpen(Session session) throws IOException, NetworkException,Exception {
		
		this.session = session;
		long now = new Date().getTime();
		session.getUserProperties().put("beforeTick", new AtomicLong(now));
		
		Map params = session.getRequestParameterMap();
		
		session.getRequestURI().getHost();
		
		Object ipObject = params.get("ip");
		// 获取设备id
		Object deviceIdObject = params.get("deviceId");
		// 连接类型
		Object connectionTypeObject = params.get("connectionType");
		// 节目单同步的id
		Object synchIdObject = params.get("synchId");
		//apkSynchId
		Object apkSynchIdObject = params.get("apkSynchId");
		
		String deviceIp="",uuid = "", connectionType = "", synchId = "",apkSynchId = "";
		
		if (deviceIdObject != null) {
			uuid = ((List<String>)deviceIdObject).get(0);
			clientsMap.put(uuid, session);
			session.getUserProperties().put("uuid", uuid);	
		}
		if(ipObject!=null){
			deviceIp = ((List<String>)ipObject).get(0);	
			session.getUserProperties().put("deviceIp", deviceIp);
		}
		if(params!=null)
			System.out.println(" 客户端已握手:-----"+deviceIp+" "+JSONObject.fromObject(params).toString());
		if (connectionTypeObject != null) {
			connectionType = ((List<String>)connectionTypeObject).get(0);
		}
		if (synchIdObject != null) {
			synchId = ((List<String>)synchIdObject).get(0);
		}
		if(apkSynchIdObject != null){
			apkSynchId = ((List<String>)apkSynchIdObject).get(0);
		}
		new SocketUtil().OnConnectionHandelForWebSocket(session, new String[]{uuid,deviceIp,connectionType,synchId,apkSynchId});

	}

	@OnMessage
	public void handleMessage(Session session, String message)
			throws ServerException {
		System.out.println("I got a message :" + message);
		try {
			JSONObject object = JSONObject.fromObject(message);
			String method = (String) object.get("method");
			String data = String.valueOf(object.get("message"));
			switch (method) {
			case "System_operation":
				onClientReboot(session, data);
            break;
			case "advert_info":
				onAdvertInfo(session,data);
				break;
			case "notice_info":
				onNoticeInfo(session,data);
				break;
			case "apk_update":
				onApkUpdate(session,data);
				break;
			case "feedBack_info":
				onFeedBackInfo(session,data);
				break;
				
			}
		} catch (JSONException ee) {
			ServerException exception = new ServerException("Json格式解析错误");
			exception.setExceptionCode("SE_SERVER_INTERNAL_ERROR");
			throw exception;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ServerException exception = new ServerException(e.getMessage());
			exception.setExceptionCode("SE_SERVER_INTERNAL_ERROR");
			throw exception;
		}

	}
    /**
     * System_operation
     * @param client
     * @param data
     */
	public void onClientReboot(Session client, String data) {
		String deviceIp = (String)session.getUserProperties().get("deviceIp");// 获取设备ip
		JSONObject object = JSONObject.fromObject(data);
		String uuid = object.getString("uuid");// 获取uuid
		if (data != null && !data.equals("")) {
			System.out.println(DateUtil.getStringDate() + "---<" + deviceIp
					+ ">设备重启返回的信息******************************" + data);
			deviceRBCount.put(uuid, deviceIp);
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
/**
 * advert_info
 * @param client
 * @param data
 * @param ackRequest
 * @throws ClassNotFoundException
 */
	public void onAdvertInfo(Session client, String data) throws JSONException{
		
    	String deviceIp = (String)client.getUserProperties().get("deviceIp");//获取设备ip
    	JSONObject object = JSONObject.fromObject(data);
    	System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端反馈的信息******************************"+data);
		
    }
	/**
	 * notice_info
	 * @param client
	 * @param data
	 * @throws JSONException
	 */
	public void onNoticeInfo(Session client, String data) throws JSONException{
		
    	String deviceIp = (String)client.getUserProperties().get("deviceIp");//获取设备ip
    	JSONObject object = JSONObject.fromObject(data);
    	System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端反馈的信息******************************"+data);
		
    }
	
	/**
	 * apk_update
	 * @param client
	 * @param data
	 * @throws JSONException
	 */
	public void onApkUpdate(Session client, String data) throws JSONException{
		
    	String deviceIp = (String)client.getUserProperties().get("deviceIp");//获取设备ip
    	JSONObject object = JSONObject.fromObject(data);
    	System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端反馈的信息******************************"+data);
		
    }
	
	/**
	 * feedBack_info
	 * @param client
	 * @param data
	 * @throws JSONException
	 * @throws AdvertException 
	 */
	public void onFeedBackInfo(Session client, String data) throws JSONException, AdvertException{
		

		String deviceIp = (String)client.getUserProperties().get("deviceIp");//获取设备ip
		String uuid = "";//设备uuid
		Map params = client.getRequestParameterMap();
		//获取设备id	
		Object deviceIdObject = params.get("deviceId");
		if(deviceIdObject != null){
			uuid = ((List<String>)deviceIdObject).get(0);
		}
		new SocketUtil().FeedBackInfoHandel(uuidDeviceMap, new String[]{uuid,data});
	}

	@OnMessage
	public void handlePongMessage(Session session,PongMessage pongMessage) {
		ByteBuffer resp = pongMessage.getApplicationData();
		session.getUserProperties().put("afterTick", new AtomicLong(Long.parseLong(new String(resp.array()))+1));	
	}

	@OnError
	public void handleError(Session session, Throwable t)
			throws NetworkException, Exception {
		try {
			log.error(session.getUserProperties().get("deviceIp")!=null?(String)session.getUserProperties().get("deviceIp"):""
		        +"------WebSocket发生错误------"+t.getMessage());
			Map<String, String> deviceParamsMap = new HashMap<String, String>(2);
			if(t instanceof IOException){
				String uuid = (String)session.getUserProperties().get("uuid");
				deviceParamsMap.put("uuid", uuid);
				deviceParamsMap.put("connectionStatus", "2");
				WebSocketLauncher.deviceService.updateDeviceByUuid(deviceParamsMap);			
				Session client = clientsMap.get(uuid);
				clientsMap.remove(uuid);
				uuidDeviceMap.remove(uuid);
				client.close();
			}
		}catch(IOException e1){
			log.error("------客户端已关闭------"+e1.getMessage());
		}catch (Exception ee){
			log.error("------handleError------"+ee.getMessage());
		}
	}

	@OnClose
	public void handleClose(Session session) throws IOException, AdvertException {
		// TODO Auto-generated method stub
		Map params = session.getRequestParameterMap();
		// 获取设备id
		Object deviceIdObject = params.get("deviceId");
		//获取设备ip
		Object ipObject = params.get("ip");
		String uuid = "",deviceIp = "";
		if (deviceIdObject != null) {
			uuid = ((List<String>) deviceIdObject).get(0);
		}
		if (ipObject != null) {
			deviceIp = ((List<String>) ipObject).get(0);
		}
		new SocketUtil().onDisConnectionHandelForWebSocket(session, new String[]{deviceIp,uuid});
	}

	/**
	 * 给所有的客户端推送消息
	 * 
	 * @param eventType
	 *            推送的事件类型
	 * @param message
	 *            推送的内容
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void sendMessageToAllClient(String eventType, String message) {
		Collection<Session> clients = clientsMap.values();
		Map<String, String> result = new HashMap<String, String>(2);
		result.put("method", eventType);
		result.put("message", message);
		for (Session client : clients) {
			try {
				client.getBasicRemote().sendText(
						GsonUtil.getGson().toJson(result));
			} catch (IOException e) {
				try {
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.error("------推送消息失败------");
			}
		}
	}

	/**
	 * 通过设备的uuid推送消息
	 * 
	 * @param deviceId
	 *            设备类型
	 * @param eventType推送事件类型
	 * @param message
	 *            推送的消息内容
	 * @throws IOException
	 * @throws NetworkException
	 */
	public static void sendMessageByUuid(String uuid, String eventType,
			String message) throws IOException, NetworkException {

		if (StringUtils.isBlank(uuid)) {
			return;
		}
		Session client = WebSocketLauncher.clientsMap.get(uuid);
		try {
			if (client != null && client.isOpen()) {
				Map<String, String> result = new HashMap<String, String>(2);
				result.put("method", eventType);
				result.put("message", message);
				client.getBasicRemote().sendText(
						GsonUtil.getGson().toJson(result));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			client.close();
			NetworkException network = new NetworkException();
			network.setExceptionCode("SE_CONNECT_ERROR");
			log.error("------推送消息失败------");
			throw network;
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
				sendMessageByUuid(key,"notice_info",DESUtils.encryptDES(message, "AC671618"));
				count ++;
			}
		}
		result.put("result","1");
		result.put("message","发送成功！已发往 "+count+" 台设备。");	
		
		}catch(Exception e){
			result.put("result",IConstant.DEFAULT_CODE_FAIL);
			result.put("message",IConstant.PUASH_EORROR);		
		}
		return result;
	}

	public static Map<String, Long> getCheckStatusMap() {
		return checkStatusMap;
	}

	public void cleanCheckStatusMap(String key) {
		checkStatusMap.remove(key);
	}

	public void cleanClientsMap(String key) {
		clientsMap.remove(key);
	}

	public static Map<String, Session> getClientsMap() {
		return clientsMap;
	}

	public static Map<String, String> getDeviceRBCount() {
		return deviceRBCount;
	}

	public static void setDeviceRBCount(Map<String, String> deviceRBCount) {
		WebSocketLauncher.deviceRBCount = deviceRBCount;
	}

	public static Map<String, Map> getUuidDeviceMap() {
		return uuidDeviceMap;
	}	
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		
		Map<String,String> resBody = new HashMap<String,String>(2);
		Map<String,String> res = new HashMap<String,String>(2);				
		resBody.put("data", "ping");
		res.put("method", "ping");
		res.put("tick",String.valueOf(new Date().getTime()));
		res.put("message", JSONObject.fromObject(resBody).toString());
	
	   ByteBuffer data = ByteBuffer.wrap(JSONObject.fromObject(res).toString().getBytes("UTF-8"));
		
	
		JSONObject object = JSONObject.fromObject(new String(data.array()));
		String method = (String) object.get("method");
		String data1 = String.valueOf(object.get("tick"));
		
		System.out.println(data1);
		
		
	}
}
