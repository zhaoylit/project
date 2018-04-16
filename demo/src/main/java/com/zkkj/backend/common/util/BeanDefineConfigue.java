package com.zkkj.backend.common.util;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.zkkj.backend.common.exception.ServerException;
import com.zkkj.backend.common.socketio.BinaryEventLauncher;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;

/**
 * spring加载完毕后执行
 * @author ZYL_PC
 *
 */
@Component("BeanDefineConfigue")
public class BeanDefineConfigue  implements ApplicationListener<ContextRefreshedEvent>{
	IDeviceService deviceService = new DeviceServiceImpl();
	//核心服务器ip
	final String core_server_ip = (String)CustomizedPropertyConfigurer.getContextProperty("core_server_ip");
	//设备类型
	final String device_type = (String)CustomizedPropertyConfigurer.getContextProperty("device_type");
	//socket端口号
	final String port = (String)CustomizedPropertyConfigurer.getContextProperty("socket_port");
	//设备id
	final String device_id = (String)CustomizedPropertyConfigurer.getContextProperty("device_id");
	//资源访问路径
	final String return_path_z = (String) CustomizedPropertyConfigurer.getContextProperty("return_path_z");
	//当前服务器的ip
	private String serverIp = "";
	//当前服务器设备id
	private String deviceId = "";
	
	//定时心跳
	private Timer timer = null;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {	
		// TODO Auto-generated method stub
	    //checkDeviceStatus();
		//启动厅服务器连接核心服务器的Websocket
		openWebsocketConnection();
		//启动厅服务器连接核心服务器的SocketIO
//	    openSocketIOConnetion();
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	      public void run() {
			    try {
					//启动SocketIO
//					startSocketServer();
			    	//添加或者修改厅服务器的设备信息
			    	addOrEditDeviceInfo();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("自动添加厅服务器设备信息异常----------------"+e.getMessage());
				}
	      }
	    },3000,Long.MAX_VALUE);// 这里设定将延时每天固定执行
	}
	/**
	 * 启动socket服务
	 */
	private void startSocketServer() {
		// TODO Auto-generated method stub
		//启动socket监听
  		try{
  			if(BinaryEventLauncher.getServer() == null){
  				new Thread(new Runnable() {
  					@Override
  					public void run() {
  						try {
  							BinaryEventLauncher.startServer();
  							System.out.println("-------------------------socketIO启动成功");
  						} catch (InterruptedException e) {
  							e.printStackTrace();
  						}
  					}
  				}).start();
  			}
  		}catch(Exception e){
  			ServerException serverException=new ServerException();
  			serverException.setExceptionCode("SE_SERVER_INTERNAL_ERROR");
  			serverException.setStackTrace(e.getStackTrace());
  			try {
				throw serverException;
			} catch (ServerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  		}
	}
	
	/**
	 * 定时器检测设备状态
	 */
	/*private void checkDeviceStatus() {
		// TODO Auto-generated method stub
		//检测设备 状态
	    Timer timer1 = new Timer();
	    timer1.schedule(new TimerTask(){
	      public void run() {
	  		try{
	  			//获取设备心跳信息
	  			Map<String,Long> checkStatusMap = launacher.getCheckStatusMap();
//	  			System.out.println("心跳间隔时长检测----------------------------");
	  			//打印每个设备的最新心跳时间间隔
	  			for(String key : checkStatusMap.keySet()){
	  				Map uuidIpMap = launacher.getUuidIpMap();
	  				System.out.println(DateUtil.getStringDate()+"---"+key+"["+uuidIpMap.get(key)+"]---------------------"+(new Date().getTime() - checkStatusMap.get(key))/1000+"秒");
	  			}
	  			//遍历每个设备判断心跳间隔是否大于30秒，更改设备连接状态
	  			for(String key : checkStatusMap.keySet()){
					Long time = checkStatusMap.get(key);
					long interval = (new Date().getTime() - time)/1000;
//					System.out.println("interval-----------------------------"+interval);
					if(interval > 30){
						Map params = new	 HashMap();
						params.put("connectionId", key);
						List<Map<String, String>> deviceList = advertService.getDeviceInfoByParams(params);
						if(deviceList != null && deviceList.size() > 0){
							Map<String, String> deviceMap = deviceList.get(0);
							String rowKey = deviceMap.get("rowKey");
							String devIp = deviceMap.get("devIp");
							String devType = deviceMap.get("devType"); 
							Map updateMap1 = new HashMap();
							updateMap1.put("rowKey", rowKey);
							updateMap1.put("connectionStatus","2");
							advertService.updateDeviceInfo(updateMap1);
							
							launacher.cleanCheckStatusMap(key);
							SocketIOClient client = launacher.getClientsMap().get(key);
							if(client != null){
								try{
									if(!launacher.getServer().getClient(client.getSessionId()).isChannelOpen()){
										System.out.println(DateUtil.getStringDate()+"---["+devIp+"]心跳超时，断开连接--------------------------------------[uid:"+key+"]");
										launacher.getServer().getClient(client.getSessionId()).disconnect();
									}
								}catch(Exception e){
									
								}
							}
							launacher.cleanClientsMap(key);
							launacher.cleanUuidIpMap(key);	
							launacher.cleanIpMap(devIp);
							//查询厅服务器下面的设备
							
							if("1".equals(devType)){
								String airlineCode = deviceMap.get("airlineCode");
								String airportCode = deviceMap.get("airportCode");
								String viproomId = deviceMap.get("viproomId");
								//查询设备下面的工作站
								Map params1 = new HashMap();
				  				params1.put("deviceType", "2");
				  				params1.put("airlineCode",airlineCode);
				  				params1.put("airportCode", airportCode);
				  				params1.put("viproomId", viproomId);
								List<Map<String, String>> deviceList1 = advertService.getDeviceInfoByParams(params1);
								for(Map<String, String> mm1 : deviceList1){
									String rowKey1 = mm1.get("rowKey");
									String devIp1 = mm1.get("devIp");
									String connectionId = mm1.get("connectionId");
									Map updateMap2 = new HashMap();
									updateMap2.put("rowKey", rowKey1);
									updateMap2.put("connectionStatus","2");
									advertService.updateDeviceInfo(updateMap2);
								}
							}
						}
					}else{
						Map params = new HashMap();
						params.put("connectionId", key);
						List<Map<String, String>> deviceList = advertService.getDeviceInfoByParams(params);
						if(deviceList != null && deviceList.size() > 0){
							Map<String, String> deviceMap = deviceList.get(0);
							String rowKey = deviceMap.get("rowKey");
							
							Map updateMap1 = new HashMap();
							updateMap1.put("rowKey", rowKey);
							updateMap1.put("connectionStatus","1");
							advertService.updateDeviceInfo(updateMap1);
						}
					}
	  			}
	  		}catch(Exception e){
	  			
	  		}
	      }
	    }, 3000,1000 * 10);// 这里设定将延时每天固定执行
	}*/
	
	/**
	 * 厅服务器连接核心服务器的websocket 
	 */
	private void openWebsocketConnection(){
		
		if("1".equals(device_type)){
			try {
				serverIp = return_path_z.substring(return_path_z.indexOf("//") + 2, return_path_z.lastIndexOf(":"));
				OkHttpClient sClient = new OkHttpClient();
				Request request = new Request.Builder().url("ws://"+core_server_ip+":" + port+"/zkkjweb/ws?deviceIp="+serverIp+"&deviceId="+device_id+"&connectionType=1").build();
				EchoWebSocketListener listener = new EchoWebSocketListener();
				WebSocket socket =  sClient.newWebSocket(request, listener);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
	public static class EchoWebSocketListener extends WebSocketListener{
        private static final String TAG = "EchoWebSocketListener";

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
        	System.out.println("onOpen-------厅服务器连接上核心服务器");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
        	System.out.println("Receiving: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
        	System.out.println ("Receiving: " + bytes.hex());
        }


        @Override

       public void onClosed(WebSocket webSocket, int code, String reason) {
        	System.out.println ("Closed: " + code + " " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            t.printStackTrace();
        }
    }
	/**
	 * 如果服务器类型是厅服务器的话，建立连接核心服务器的socket连接
	 */
	private void openSocketIOConnetion() {
		// TODO Auto-generated method stub
		try{
	  		  if("1".equals(device_type)){
	  			  int port = 9092;
		  		  try{
		  			serverIp = return_path_z.substring(return_path_z.indexOf("//") + 2, return_path_z.lastIndexOf(":"));
		  			deviceId = device_id;
		  		  }catch(Exception e){
		  		  }
		  		IO.Options options = new IO.Options();
		          options.forceNew = true;
		          options.reconnection = true;
		          final Socket socket = IO.socket("http://"+core_server_ip+":" + port+"?deviceId="+device_id+"&connectionType=1", options);
		          socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){
		              @Override
		              public void call(Object... args) {
		                  System.out.println("connect");
		                  //向核心服务器发送心跳数据
		                  if(timer == null){
		                	  timer = new Timer();
		                	  timer.schedule(new TimerTask(){
				        	      public void run() {
				        	  		try{
				    	                Map<String, Object> mm = new HashMap<String, Object>();
				    	                mm.put("uuid", device_id);
				    	                mm.put("time", new Date().getTime());
				    	                socket.emit("heart_beat",JSONObject.fromObject(mm).toString());
				        	  		}catch(Exception e){
				        	  			ServerException serverException=new ServerException();
				        	  			serverException.setExceptionCode("SE_SERVER_INTERNAL_ERROR");
				        	  			serverException.setStackTrace(e.getStackTrace());
				        	  			try {
											throw serverException;
										} catch (ServerException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
				        	  		}
				        	      }
				        	  }, 1000 * 15,1000 * 30);//这里设定将延时每天固定执行
		                  }
		              }
		          }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
		              @Override
		              public void call(Object... args) {
		            	  System.out.println("connect timeout");
		            	  if(timer != null){
			            	  timer.cancel();
			            	  timer = null;
		            	  }
		              }
		          }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
		              @Override
		              public void call(Object... args) {
		                  System.out.println("connect error");
		                  if(timer != null){
			            	  timer.cancel();
			            	  timer = null;
		            	  }
		              }
		          }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
		              @Override
		              public void call(Object... args) {
		                  System.out.println("disconnect");
		                  if(timer != null){
			            	  timer.cancel();
			            	  timer = null;
		            	  }
		              }
		          });
		          socket.open();
	  		  }
	  	  }catch(Exception e){
	  		  
	  	  }
	}
	public void addOrEditDeviceInfo() throws Exception{
		if("1".equals(device_type)){
			Map paramMap = new HashMap();
			paramMap.put("uuid", device_id);
			paramMap.put("deviceType", device_type);
			Map deviceMap = deviceService.getDeviceOne(paramMap);
			if(deviceMap == null){
				//添加厅服务器信息
				Map viproomInsertMap = new HashMap();
				String viproomId = device_id;
				String viproomName = "";
				String airportId = "";
				String airportCode = "";
				String airlineId = "";
				String airlineCode = "";
				//查询厅服务器信息
				Map params1 = new HashMap();
				params1.put("viproomId", device_id);
				List<Map> viproomList = deviceService.getViproomList(params1);
				if(viproomList !=null && viproomList.size() > 0){
					Map map1 = viproomList.get(0);
					airportId = ParamsUtil.nullDeal(map1, "apId", "");
					Map param2 = new HashMap();
					param2.put("airportId", airportId);
					List<Map> airportList = deviceService.getAirportList(param2);
					if(airportList != null && airportList.size() > 0){
						Map map2 = airportList.get(0);
						airportCode = ParamsUtil.nullDeal(map2, "airportCode", "");
						airlineId = ParamsUtil.nullDeal(map2, "alId", "");
						Map param3 = new HashMap();
						param3.put("airlineId", airlineId);
						List<Map> airlineList = deviceService.getAirlineList(param3);
						if(airlineList != null && airlineList.size() > 0){
							Map map3 = airlineList.get(0);
							airlineCode = ParamsUtil.nullDeal(map3, "airlineCode", "");
						}
					}
					viproomInsertMap.put("viproomId", device_id);
					viproomInsertMap.put("airportId", airportId);
					viproomInsertMap.put("airlineId", airlineId);
					viproomInsertMap.put("deviceNo", airlineCode + airportCode + viproomName);
					viproomInsertMap.put("deviceName", "厅服务器");
					viproomInsertMap.put("deviceType", "1");
					viproomInsertMap.put("uuid", viproomId);
					viproomInsertMap.put("connectionStatus", "1");
					deviceService.addDevice(viproomInsertMap);
				}
			}
		}
		
	}
	public static void main(String[] args) {
		try{
			IO.Options options = new IO.Options();
	        options.forceNew = true;
	        options.reconnection = true;
	        final Socket socket = IO.socket("http://192.168.3.4:9092?deviceId=1122334455&connectionType=2".toString(), options);
	       
	        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
	            @Override
	            public void call(Object... args) {
	                System.out.println("connect");
//	                socket.close();
	                //检测设备 状态
	        	    /*Timer timer = new Timer();
	        	    timer.scheduleAtFixedRate(new TimerTask(){
	        	      public void run() {
	        	  		try{
	    	                Map<String, Object> mm = new HashMap<String, Object>();
	    	                mm.put("uuid", "DFHKHZXST1VIP01");
	    	                mm.put("time", new Date().getTime());
	    	                socket.emit("heart_beat",JSONObject.fromObject(mm).toString());
	        	  		}catch(Exception e){
	        	  			
	        	  		}
	        	      }
	        	    }, 1000,1000 * 30);// 这里设定将延时每天固定执行
*/
//	                socket.emit("advert_info", "广告图片下载完成-----广告名:http://192.168.9.2:30000/zkkjweb/upload/advert/1482910418913.jpg---设备id:ffffffff-bdaa-eb27-13fe-47590033c587");
//	                socket.emit("System_operation", "");
	                Map dataMap = new HashMap();
	                dataMap.put("type", "AD_AYNCH");
//	                dataMap.put("synchId", "25");
//	                dataMap.put("fileName", "1490665100408.jpg");
//	                dataMap.put("err_reason", "网络原因");
	                socket.emit("feedBack_info", JSONObject.fromObject(dataMap).toString());
	            }
	        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener(){
	            @Override 
	            public void call(Object... args) {
	                System.out.println("connect timeout");
	            }
	        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener(){
	            @Override	
	            public void call(Object... args) {
	                System.out.println("connect error");
	            }
	        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener(){
	            @Override
	            public void call(Object... args) {
	                System.out.println("disconnect");
	            }
	        }).on("advert_info1", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String data = (String)args[0];
                    System.out.println("节目单数据********************************"+data.toString());
                    Map<String, List<Map<String, List<Map<String,String>>>>> programMap = GsonUtil.getGson().fromJson(data, Map.class);
            		List<Map<String, List<Map<String,String>>>> list = programMap.get("result");
            		if(list != null && list.size() > 0){
            			List<Map<String,String>> aList = list.get(0).get("A");
            			List<Map<String,String>> bList = list.get(1).get("B");	
            			if(aList != null && aList.size() > 0){
            				for(int i = 0;i < aList.size();i++){
            					String path1 = aList.get(i).get("path1");
            					String path2 = aList.get(i).get("path2");
            					
            					MultiTheradDownLoad load = new MultiTheradDownLoad(path1 ,2);    
            			        load.downloadPart();
            			        MultiTheradDownLoad load1 = new MultiTheradDownLoad(path2 ,2);    
            			        load1.downloadPart();
            				}
            			}
            			if(bList != null && bList.size() > 0){	
            				for(int i = 0;i < bList.size();i++){
            					String path1 = aList.get(i).get("path1");
            					String path2 = aList.get(i).get("path2");
            					MultiTheradDownLoad load = new MultiTheradDownLoad(path1 ,2);    
            			        load.downloadPart();
            			        MultiTheradDownLoad load1 = new MultiTheradDownLoad(path2 ,2);    
            			        load1.downloadPart();	
            				}
            			}
            			
            		}
                }
            }).on("notice_info", new Emitter.Listener(){
                @Override
                public void call(Object... args){
                    String data = (String)args[0];
                    System.out.println("公告********************************"+data.toString());
                }
            }).on("apk_update", new Emitter.Listener(){
                @Override
                public void call(Object... args){
                    String data = (String)args[0];
                    System.out.println("apk远程更新********************************"+data.toString());
                }
            }).on("System_operation", new Emitter.Listener(){
                @Override
                public void call(Object... args){
                    String data = (String)args[0];
                    System.out.println("系统操作********************************"+data.toString());
                }
            });
	        
	        
	        socket.open();
		}catch(Exception e){
			
		}
	}
}
