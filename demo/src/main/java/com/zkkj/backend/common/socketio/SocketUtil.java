package com.zkkj.backend.common.socketio;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import net.sf.json.JSONObject;

import org.springframework.web.socket.TextMessage;

import com.corundumstudio.socketio.SocketIOClient;
import com.zkkj.backend.common.exception.AdvertException;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.websocket.WebSocketLauncher;
import com.zkkj.backend.common.websocket.WebsocketEndPoint;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.program.util.Config;

public class SocketUtil {
	
	private static final String CONF_SERVER_ID = (String)CustomizedPropertyConfigurer.getContextProperty("device_id");
	private static final String CONF_SERVER_TYPE = (String)CustomizedPropertyConfigurer.getContextProperty("device_type");
	
	IDeviceService deviceService = new DeviceServiceImpl();
	/**
	 * 给制定设备推送全局websocket消息
	 * @param deviceMap
	 * @param pushType
	 * @param data
	 */
	public static void pushOverAllMessage(Map deviceMap,String pushType,String[] data){
		String deviceIp = "",airlineName = "",airportName = "",viproomName = "";
		if(deviceMap != null){
			deviceIp = ParamsUtil.nullDeal(deviceMap, "deviceIp", "");
			airlineName = ParamsUtil.nullDeal(deviceMap, "airlineName", "");
			airportName = ParamsUtil.nullDeal(deviceMap, "airportName", "");
			viproomName = ParamsUtil.nullDeal(deviceMap, "viproomName", "");
		}
		Map overallMap = new HashMap();
		overallMap.put("type", "overall_info");//全局类型
		String  content = "";
		
		//定义推送的内容
		switch (pushType) {
		case SocketConstant.RECEIVE_PROGRAM:
		{
			//接收到节目单
			//消息内容
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("接收到节目单（"+data[0]+"）<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.AN_FILE_DOWNLOAD_SUCCESS:
		{
			//资源下载成功
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("下载了一张资源（"+data[0]+"）<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.AN_FILE_DOWNLOAD_FAIL:
		{
			//资源下载失败
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("下载失败了一张资源（"+data[0]+"）<br/>");
			contentBuilder.append("失败原因："+data[1]+"<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.RESOURCE_DOWNLOAD_ALL:
		{
			//节目单资源全部下载完成
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("资源全部下载完成<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.RECEIVE_APK:
		{
			//接收到apk
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("接收到apk（"+data[0]+"）<br/>");
			contentBuilder.append("版本号："+data[1]+"<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.APK_DOWNLOAD:
		{
			//apk下载成功
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("apk（"+data[0]+"）下载成功<br/>");
			contentBuilder.append("版本号："+data[1]+"<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.APK_INSTALL:
		{
			//apk安装成功
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("apk（"+data[0]+"）已安装成功<br/>");
			contentBuilder.append("版本号："+data[1]+"<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.REBOOT:
		{
			//设备重启
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("重启完成<br/>");
			content = contentBuilder.toString();
		}
		break;
		case SocketConstant.AD_SYNCH:
		{
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append(!"".equals(airlineName) ? airlineName + "<br/>" : "");
			contentBuilder.append(!"".equals(airportName) ? airportName + "<br/>" : "");
			contentBuilder.append(!"".equals(viproomName) ? viproomName + "<br/>" : "");
			contentBuilder.append(!"".equals(deviceIp) ? "设备（"+deviceIp+"）<br/>" : "");
			contentBuilder.append("收到广告同步的指令<br/>");
			content = contentBuilder.toString();
		}
		break;
		default:
			break;
		}
		//推送的数据
		overallMap.put("content", content);
		String pushData = JSONObject.fromObject(overallMap).toString();
		new WebsocketEndPoint().sendMessageToUsers(new TextMessage(pushData));
	}
	/**
	 * @param type  反馈的事件类型
	 * @param uuid  设备的uuid
	 * @param uuidDeviceMap  设备连接socket的Map 对象
	 * @param object 参数对象
	 * @return
	 * @throws AdvertException 
	 */
	public void FeedBackInfoHandel(Map<String,Map> uuidDeviceMap,String[] data) throws AdvertException{
		String uuid = data[0];
		JSONObject object = JSONObject.fromObject(data[1]);
		String type = object.getString("type");//反馈类型
		switch (type) {
		case SocketConstant.RECEIVE_PROGRAM://接收到节目单信息
			{
				try {
					String synchId = object.getString("synchId");
					//查询上次推送的节目单信息
					Map curProgramSynchDeviceInfoParams = new HashMap();
					curProgramSynchDeviceInfoParams.put("uuid", uuid);
					curProgramSynchDeviceInfoParams.put("synchId", synchId);
					Map deviceProgramSynchInfo = deviceService.getDeviceProgramSynchInfo(curProgramSynchDeviceInfoParams);
					
					if(deviceProgramSynchInfo == null){
						return;
					}
					//修改节目单同步设备信息表的状态为已接收
					Map updateMap = new HashMap();
					updateMap.put("id",deviceProgramSynchInfo.get("psdiId"));//设置更新的主键
					updateMap.put("synchStatus","1");
					deviceService.updateProgramSynchDeviceInfo(updateMap);
					
					
					//websocket全局推送
					Map deviceMap = uuidDeviceMap.get(uuid);
					String programName = ParamsUtil.nullDeal(deviceProgramSynchInfo, "programName", "");
					SocketUtil.pushOverAllMessage(deviceMap, SocketConstant.RECEIVE_PROGRAM, new String[]{programName});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("接收到节目单，保存节目单同步设备信息表失败"+e.getMessage());
					//存取异常信息
					AdvertException advertException=new AdvertException();
					advertException.setExceptionCode("SE_AD_ERROR");
					advertException.setStackTrace(e.getStackTrace());
					throw advertException;
				}
			}
		break;
		case SocketConstant.RESOURCE_ALREADY_EXIST:
			{
				try {
					String synchId = object.getString("synchId");
					String size = object.getString("size");//已经存在的文件数量
					
					//查询当节目单当前设备的资源下载成功的数量
					Map curProgramSynchDeviceInfoParams = new HashMap();
					curProgramSynchDeviceInfoParams.put("uuid", uuid);
					curProgramSynchDeviceInfoParams.put("synchId", synchId);
					Map deviceProgramSynchInfo = deviceService.getDeviceProgramSynchInfo(curProgramSynchDeviceInfoParams);
					if(deviceProgramSynchInfo == null){
						return;
					}
					//已经下载成功的数量
					int success = Integer.parseInt(ParamsUtil.nullDeal(deviceProgramSynchInfo, "success", "0"));
					//下载失败的数量
					int resourceCount = Integer.parseInt(ParamsUtil.nullDeal(deviceProgramSynchInfo, "total", "0"));
					//当前下载状态
					int synchStatus = Integer.parseInt(ParamsUtil.nullDeal(deviceProgramSynchInfo, "synchStatus", "0"));
					if(synchStatus == 3){
						return;
					}
					//修改节目单同步设备信息表成功的数量
					Map updateMap = new HashMap();
					updateMap.put("id",deviceProgramSynchInfo.get("psdiId"));//设置更新的主键
					updateMap.put("success",size);//设置成功的资源数量
					if(resourceCount == Integer.parseInt(size)){
						updateMap.put("error","0");
					}
					updateMap.put("synchStatus",resourceCount == Integer.parseInt(size) ? "3" : "2");//根据成功的资源数量设置资源的同步状态
					deviceService.updateProgramSynchDeviceInfo(updateMap);
					
					if(resourceCount == Integer.parseInt(size)){
						//资源全部下载完成
						//删除之前下载失败的资源
						//通过websocket向设备监控推送下载完成
						Map pushMap = new HashMap();
						pushMap.put("type", "resource_download_all");
						pushMap.put("uuid", uuid);
						String content = JSONObject.fromObject(pushMap).toString();
						new WebsocketEndPoint().sendMessageToUsers(new TextMessage(content));
						
						//websocket全局推送下载完成
						Map deviceMap = uuidDeviceMap.get(uuid);
						pushOverAllMessage(deviceMap, SocketConstant.RESOURCE_DOWNLOAD_ALL, new String[]{});
					}else{
						//通过websocket向设备监控推送下载完成
						Map pushMap = new HashMap();
						pushMap.put("type", "resource_download_success");
						pushMap.put("uuid", uuid);
						pushMap.put("count", size);
						String content = JSONObject.fromObject(pushMap).toString();
						new WebsocketEndPoint().sendMessageToUsers(new TextMessage(content));
					}
				} catch (Exception e) {
					e.printStackTrace();
					//存取异常信息
					AdvertException advertException=new AdvertException();
					advertException.setExceptionCode("SE_AD_ERROR");
					advertException.setStackTrace(e.getStackTrace());
					throw advertException;
				}
			}
		break;
		case SocketConstant.AN_FILE_DOWNLOAD_SUCCESS://资源下载成功
			{
				try {
					String synchId = object.getString("synchId");
					String fileName = object.getString("fileName");//文件名称
					//查询当节目单当前设备的资源下载成功的数量
					Map curProgramSynchDeviceInfoParams = new HashMap();
					curProgramSynchDeviceInfoParams.put("uuid", uuid);
					curProgramSynchDeviceInfoParams.put("synchId", synchId);
					Map deviceProgramSynchInfo = deviceService.getDeviceProgramSynchInfo(curProgramSynchDeviceInfoParams);
					if(deviceProgramSynchInfo == null){
						return;
					}
					//已经下载成功的数量
					int success = Integer.parseInt(ParamsUtil.nullDeal(deviceProgramSynchInfo, "success", "0"));
					int resourceCount = Integer.parseInt(ParamsUtil.nullDeal(deviceProgramSynchInfo, "total", "0"));
					//修改节目单同步设备信息表成功的数量
					Map updateMap = new HashMap();
					updateMap.put("id",deviceProgramSynchInfo.get("psdiId"));//设置更新的主键
					updateMap.put("success",success + 1);
					if(success + 1 == resourceCount){
						updateMap.put("error","0");
					}
					updateMap.put("synchStatus",success + 1 == resourceCount ? "3" : "2");//资源下载状态，正在下载或者下载成功
					deviceService.updateProgramSynchDeviceInfo(updateMap);
					
					if(true){//资源未下载完成
						//通过websocket向前端推送成功的数量
						Map pushMap = new HashMap();
						pushMap.put("type", "resource_download_success");
						pushMap.put("count", success + 1);
						pushMap.put("uuid",uuid);
						String content = JSONObject.fromObject(pushMap).toString();
						new WebsocketEndPoint().sendMessageToUsers(new TextMessage(content));
						
						//websocket全局推送
						Map deviceMap = uuidDeviceMap.get(uuid);
						pushOverAllMessage(deviceMap, SocketConstant.AN_FILE_DOWNLOAD_SUCCESS, new String[]{fileName});
					}
					if(success + 1 == resourceCount){
						//通过websocket向前端推送资源下载完成
						Map pushMap = new HashMap();
						pushMap.put("type", "resource_download_all");
						pushMap.put("uuid", uuid);
						String content = JSONObject.fromObject(pushMap).toString();
						new WebsocketEndPoint().sendMessageToUsers(new TextMessage(content));
						
						//websocket全局推送
						Map deviceMap = uuidDeviceMap.get(uuid);
						pushOverAllMessage(deviceMap, SocketConstant.RESOURCE_DOWNLOAD_ALL, new String[]{});
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
		break;
		case SocketConstant.AN_FILE_DOWNLOAD_FAIL://资源下载失败
			{
				try {
					String synchId = object.getString("synchId");
					String fileName = object.getString("fileName");//文件名称
					String err_reason = object.getString("err_reason");//下载失败的原因
					//查询当节目单当前设备的资源下载成功的数量
					Map curProgramSynchDeviceInfoParams = new HashMap();
					curProgramSynchDeviceInfoParams.put("uuid", uuid);
					curProgramSynchDeviceInfoParams.put("synchId", synchId);
					Map deviceProgramSynchInfo = deviceService.getDeviceProgramSynchInfo(curProgramSynchDeviceInfoParams);
					if(deviceProgramSynchInfo == null){
						return;
					}
					//添加或者更新下载失败的信息
					Map errorMap = new HashMap();
					errorMap.put("pasiId", deviceProgramSynchInfo.get("psdiId"));//同步设备信息表的主键
					errorMap.put("fileName",fileName);//文件名称
					errorMap.put("reason", err_reason);//文件下载失败的原因
					int count  = deviceService.addProgramSynchDeviceDownloadErrorInfo(errorMap);
					
					if(count > 0){
						//更新数量
						int error = Integer.parseInt(ParamsUtil.nullDeal(deviceProgramSynchInfo, "error", "0"));
						//修改节目单同步设备信息表成功的数量
						Map updateMap = new HashMap();
						updateMap.put("id",deviceProgramSynchInfo.get("psdiId"));//设置更新的主键
						updateMap.put("error",error + 1);
						deviceService.updateProgramSynchDeviceInfo(updateMap);
						
						
						//通过websocket向前端推送成功的数量
						Map pushMap = new HashMap();
						pushMap.put("type", "resource_download_error");
						pushMap.put("count", error + 1);
						pushMap.put("uuid",uuid);
						String content = JSONObject.fromObject(pushMap).toString();
						new WebsocketEndPoint().sendMessageToUsers(new TextMessage(content));
					}
					//websocket全局推送
					Map deviceMap = uuidDeviceMap.get(uuid);
					pushOverAllMessage(deviceMap, SocketConstant.AN_FILE_DOWNLOAD_FAIL, new String[]{fileName,err_reason});
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
		break;
		case "APK_VERSION"://版本号反馈
			{
				try {
					//apk同步的id
					String synchId = object.getString("apkSynchId");
					String version = object.getString("version");
					if(synchId == null || "".equals(synchId)){
						return;
					}
					//查询上次推送的节目单信息
					Map curApkSynchDeviceInfoParams = new HashMap();
					curApkSynchDeviceInfoParams.put("uuid", uuid);
					curApkSynchDeviceInfoParams.put("synchId", synchId);
					Map apkSynchDeviceInfo = deviceService.getApkSynchDeviceInfo(curApkSynchDeviceInfoParams);
					
					if(apkSynchDeviceInfo == null){
						return;
					}
					String versionCode = ParamsUtil.nullDeal(apkSynchDeviceInfo, "versionCode", "");
					String filePath = ParamsUtil.nullDeal(apkSynchDeviceInfo, "filePath", "");
					String synchStatus = ParamsUtil.nullDeal(apkSynchDeviceInfo, "synchStatus", "");
					//接收到版本号并且当前状态是未安装状态的话，修改同步状态并且推送全局提醒
					if(version.equals(versionCode) && !"3".equals(synchStatus)){
						//修改apk同步设备信息表的状态为已安装
						Map updateMap = new HashMap();
						updateMap.put("id",apkSynchDeviceInfo.get("asdiId"));//设置更新的主键
						updateMap.put("synchStatus","3");
						deviceService.updateApkSynchDeviceInfo(updateMap);
						
						//websocket全局推送
						Map deviceMap = uuidDeviceMap.get(uuid);
						String fileName = "";
						if(!"".equals(filePath)){
							fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
						}
						SocketUtil.pushOverAllMessage(deviceMap, SocketConstant.APK_INSTALL, new String[]{fileName,versionCode});
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					//存取异常信息
					AdvertException advertException=new AdvertException();
					advertException.setExceptionCode("SE_AD_ERROR");
					advertException.setStackTrace(e.getStackTrace());
					throw advertException;
				}
			}
		break;
		case "RECEIVE_APK"://接收到apk文件
			{
				//apk同步的id
				String synchId = object.getString("apkSynchId");
				try {
					//查询上次推送的节目单信息
					Map curApkSynchDeviceInfoParams = new HashMap();
					curApkSynchDeviceInfoParams.put("uuid", uuid);
					curApkSynchDeviceInfoParams.put("synchId", synchId);
					Map apkSynchDeviceInfo = deviceService.getApkSynchDeviceInfo(curApkSynchDeviceInfoParams);
					
					if(apkSynchDeviceInfo == null){
						return;
					}
					//修改apk同步设备信息表的状态为已接收
					Map updateMap = new HashMap();
					updateMap.put("id",apkSynchDeviceInfo.get("asdiId"));//设置更新的主键
					updateMap.put("synchStatus","1");
					deviceService.updateApkSynchDeviceInfo(updateMap);
					
					//websocket全局推送
					Map deviceMap = uuidDeviceMap.get(uuid);
					String versionCode = ParamsUtil.nullDeal(apkSynchDeviceInfo, "versionCode", "");
					String filePath = ParamsUtil.nullDeal(apkSynchDeviceInfo, "filePath", "");
					String fileName = "";
					if(!"".equals(filePath)){
						fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
					}
					SocketUtil.pushOverAllMessage(deviceMap, SocketConstant.RECEIVE_APK, new String[]{fileName,versionCode});
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					//存取异常信息
					AdvertException advertException=new AdvertException();
					advertException.setExceptionCode("SE_AD_ERROR");
					advertException.setStackTrace(e.getStackTrace());
					throw advertException;
				}
			}
		break;
		case "APK_DOWNLOAD"://apk下载完成
			{
				//apk同步的id
				String synchId = object.getString("apkSynchId");
				try {
					//查询上次推送的节目单信息
					Map curApkSynchDeviceInfoParams = new HashMap();
					curApkSynchDeviceInfoParams.put("uuid", uuid);
					curApkSynchDeviceInfoParams.put("synchId", synchId);
					Map apkSynchDeviceInfo = deviceService.getApkSynchDeviceInfo(curApkSynchDeviceInfoParams);
					
					if(apkSynchDeviceInfo == null){
						return;
					}
					//修改apk同步设备信息表的状态为已下载
					Map updateMap = new HashMap();
					updateMap.put("id",apkSynchDeviceInfo.get("asdiId"));//设置更新的主键
					updateMap.put("synchStatus","2");//已下载
					deviceService.updateApkSynchDeviceInfo(updateMap);
					
					
					//websocket全局推送
					Map deviceMap = uuidDeviceMap.get(uuid);
					String versionCode = ParamsUtil.nullDeal(apkSynchDeviceInfo, "versionCode", "");
					String filePath = ParamsUtil.nullDeal(apkSynchDeviceInfo, "filePath", "");
					String fileName = "";
					if(!"".equals(filePath)){
						fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
					}
					SocketUtil.pushOverAllMessage(deviceMap, SocketConstant.APK_DOWNLOAD, new String[]{fileName,versionCode});
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					//存取异常信息
					AdvertException advertException=new AdvertException();
					advertException.setExceptionCode("SE_AD_ERROR");
					advertException.setStackTrace(e.getStackTrace());
					throw advertException;
				}
				
			}
		break;
		case "REBOOT"://设备重启
			{
				Map deviceMap = uuidDeviceMap.get(uuid);
				SocketUtil.pushOverAllMessage(deviceMap, SocketConstant.REBOOT, new String[]{});
			}
		break;
		case "AD_SYNCH"://收到广告同步的指令
		{
			String synchId = object.getString("synchId");
			Map deviceMap = uuidDeviceMap.get(uuid);
			SocketUtil.pushOverAllMessage(deviceMap, SocketConstant.AD_SYNCH, new String[]{});
			try {
				//更新推送的记录的状态
				//查询更新的id
				Map paramsMap = new HashMap();
				paramsMap.put("uuid", uuid);
				paramsMap.put("synchId", synchId);
				Map infoMap = deviceService.getDevicePushPlaySynchInfoByUuid(paramsMap);
				if(infoMap != null){
					String id = ParamsUtil.nullDeal(infoMap, "id", "");
					Map updateParamsMap = new HashMap();
					updateParamsMap.put("id", id);
					updateParamsMap.put("receiveStatus","1");
					deviceService.updateDevicePushPlaySynchInfo(updateParamsMap);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		break;
		default:
			break;
		}
	}
	
	public void OnConnectionHandelForSocketIO(SocketIOClient client,String[] data){
		String uuid = data[0];
		String deviceIp = data[1];
		String connectionType = data[2];
		String synchId = data[3];
		String apkSynchId = data[4];
		System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端已连接***************************[uuid="+uuid+"]");
		try {
			//根据uuid查询设备信息
			Map deviceParamsMap = new HashMap();
			deviceParamsMap.put("uuid", uuid);
			Map deviceInfoMap = deviceService.getDeviceOne(deviceParamsMap);
			
			//保存当前socketclient对象
			BinaryEventLauncher.clientsMap.put("uuid", client);
			//保存连接的设备信息
			BinaryEventLauncher.uuidDeviceMap.put(uuid, deviceInfoMap);
			
			//自动添加或者更新设备信息
			{
				Map deviceMap = new HashMap();
				deviceMap.put("uuid", uuid);
				deviceMap.put("connectionType", connectionType);
				deviceMap.put("deviceIp", deviceIp);
				Config conf = new Config();
				conf.setConfigServerType(CONF_SERVER_TYPE);//设置服务器配置文件的服务器类型	
				conf.setConfigServerId(CONF_SERVER_ID);//设置服务器配置文件的服务器设备id
				deviceService.addOrEditDeviceInfo(deviceMap, conf);
			}
			//查询最近同步的节目单json
			{
				Map paramsMap = new HashMap();
				paramsMap.put("synchId",synchId);
				paramsMap.put("uuid",uuid);
				Map deviceProgramSynchInfo = deviceService.getDeviceProgramSynchInfo(paramsMap);
				if(deviceProgramSynchInfo != null){
					String programJson = String.valueOf(deviceProgramSynchInfo.get("programJson"));
					client.sendEvent("advert_info", programJson);
				}
			}
			//查询最近推送的apk更新信息
			{
				Map	paramsMap = new HashMap();
				paramsMap.put("synchId", apkSynchId);
				paramsMap.put("uuid",uuid);
				Map apkSynchInfoMap = deviceService.getApkSynchDeviceInfo(paramsMap);
				
				if(apkSynchInfoMap != null){
					//推送apk信息
					String pushJson = String.valueOf(apkSynchInfoMap.get("pushJson"));
					client.sendEvent("apk_update", pushJson);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			//存取异常信息
			AdvertException advertException=new AdvertException();
			advertException.setExceptionCode("SE_AD_ERROR");
			advertException.setStackTrace(e.getStackTrace());
			try {
				throw advertException;
			} catch (AdvertException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void OnConnectionHandelForWebSocket(Session session,String[] data){
		String uuid = data[0];
		String deviceIp = data[1];
		String connectionType = data[2];
		String synchId = data[3];
		String apkSynchId = data[4];
		System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端已连接***************************[uuid="+uuid+"]");
		
		try {
			
			//根据uuid查询设备信息
			Map deviceParamsMap = new HashMap();
			deviceParamsMap.put("uuid", uuid);
			Map deviceInfoMap = deviceService.getDeviceOne(deviceParamsMap);
			
			//保存当前socketclient对象
			WebSocketLauncher.getClientsMap().put(uuid, session);
			//保存连接的设备信息
			WebSocketLauncher.getUuidDeviceMap().put(uuid, deviceInfoMap);
			
			//自动添加或者更新设备信息
			{
				Map deviceMap = new HashMap();
				deviceMap.put("uuid", uuid);
				deviceMap.put("connectionType", connectionType);
				deviceMap.put("deviceIp", deviceIp);
				Config conf = new Config();
				conf.setConfigServerType(CONF_SERVER_TYPE);//设置服务器配置文件的服务器类型
				conf.setConfigServerId(CONF_SERVER_ID);//设置服务器配置文件的服务器设备id
				deviceService.addOrEditDeviceInfo(deviceMap, conf); 
			}
			//查询最近同步的节目单json
			{
				Map paramsMap = new HashMap();
				paramsMap.put("synchId",synchId);
				paramsMap.put("uuid",uuid);
				Map deviceProgramSynchInfo = deviceService.getDeviceProgramSynchInfo(paramsMap);
				if(deviceProgramSynchInfo != null){
					String programJson = String.valueOf(deviceProgramSynchInfo.get("programJson"));
					WebSocketLauncher.sendMessageByUuid(uuid, "advert_info", programJson);
				}
			}
			//查询最近推送的apk更新信息
			{
				Map	paramsMap = new HashMap();
				paramsMap.put("synchId", apkSynchId);
				paramsMap.put("uuid",uuid);
				Map apkSynchInfoMap = deviceService.getApkSynchDeviceInfo(paramsMap);
				
				if(apkSynchInfoMap != null){
					//推送apk信息
					String pushJson = String.valueOf(apkSynchInfoMap.get("pushJson"));
					WebSocketLauncher.sendMessageByUuid(uuid, "apk_update", pushJson);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			//存取异常信息
			AdvertException advertException=new AdvertException();
			advertException.setExceptionCode("SE_AD_ERROR");
			advertException.setStackTrace(e.getStackTrace());
			try {
				throw advertException;
			} catch (AdvertException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void onDisConnectionHandelForSocketIO(SocketIOClient client,String[] data){
		 String deviceIp = data[0];
		 String uuid = data[1];
		 try {
			  System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端已断开连接***************************[uuid="+uuid+"]");
			  //修改设备连接状态为连接
			  Map deviceParamsMap = new HashMap();
			  deviceParamsMap.put("uuid",uuid);
			  deviceParamsMap.put("connectionStatus","2");
			  deviceService.updateDeviceByUuid(deviceParamsMap);
			  Map<String, SocketIOClient> clientMap = BinaryEventLauncher.getClientsMap();
			  if(clientMap != null){
				  clientMap.remove(uuid);
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			//存取异常信息
			AdvertException advertException=new AdvertException();
			advertException.setExceptionCode("SE_AD_ERROR");
			advertException.setStackTrace(e.getStackTrace());
			try {
				throw advertException;
			} catch (AdvertException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
				client.disconnect();
		}
	}
	public void onDisConnectionHandelForWebSocket(Session session,String[] data) throws AdvertException{
		 String deviceIp = data[0];
		 String uuid = data[1];
		 try {
			  System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]客户端已断开连接***************************[uuid="+uuid+"]");
			  //修改设备连接状态为连接
			  Map deviceParamsMap = new HashMap();
			  deviceParamsMap.put("uuid",uuid);
			  deviceParamsMap.put("connectionStatus","2");
			  deviceService.updateDeviceByUuid(deviceParamsMap);
			  
			  //移除连接socket添加上的设备信息
			  Map<String, Session> clientMap = WebSocketLauncher.getClientsMap();
			  if(clientMap != null){
				  clientMap.remove(uuid);
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			//存取异常信息
			AdvertException advertException=new AdvertException();
			advertException.setExceptionCode("SE_AD_ERROR");
			advertException.setStackTrace(e.getStackTrace());
			try {
				throw advertException;
			} catch (AdvertException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
