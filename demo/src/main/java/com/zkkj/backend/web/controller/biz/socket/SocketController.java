package com.zkkj.backend.web.controller.biz.socket;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;

import com.microsoft.schemas.office.office.STInsetMode;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.DESUtils;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.websocket.WebSocketLauncher;
import com.zkkj.backend.common.websocket.WebsocketEndPoint;

@Controller
@RequestMapping(value="socket")
public class SocketController {
//	BinaryEventLauncher socketService;
	WebSocketLauncher socketService;
	/**
	 * 给指定的设备推送节目单,供远程接口调用访问
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("pushProgram")
	public void pushProgram(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String programJson = ParamsUtil.nullDeal(params, "programJson", "");
		String realPath = ParamsUtil.nullDeal(params, "realPath", "");
		String realPath1 = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z1");
		//节目单数据解密
		programJson = DESUtils.decryptDES(programJson, "zkkjad58");
		programJson = programJson.replace(realPath, realPath1);
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");
		if(!"".equals(deviceIds)){
			String devcieIdArray[] = deviceIds.split(",");
			for(String uuid : devcieIdArray){
				socketService.sendMessageByUuid(uuid,"advert_info",programJson);
			}
		}
		
		response.getOutputStream().write("1".getBytes());
	}
	/**
	 * 给指定的设备推送apk更新,供远程接口调用访问
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("pushApk")
	public void pushApk(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String apkJson = ParamsUtil.nullDeal(params, "apkJson", "");
		String realPath = ParamsUtil.nullDeal(params, "realPath", "");
		String realPath1 = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z1");
		apkJson = DESUtils.decryptDES(apkJson, "zkkjad58");
		apkJson = apkJson.replace(realPath, realPath1);
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");
		if(!"".equals(deviceIds)){
			String devcieIdArray[] = deviceIds.split(",");
			for(String uuid : devcieIdArray){
				socketService.sendMessageByUuid(uuid,"apk_update",apkJson);
			}
		}
		
		response.getOutputStream().write("1".getBytes());
	}
	
	/**
	 * 给指定的设备推送设备重启
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("pushReboot")
	public void pushReboot(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");
		if(!"".equals(deviceIds)){
			String devcieIdArray[] = deviceIds.split(",");
			for(String uuid : devcieIdArray){
				//给设备推送设备重启
				socketService.sendMessageByUuid(uuid,"device_reboot","");
				//给设备推送设备重启
				socketService.sendMessageByUuid(uuid+"-power","device_reboot","device_reboot");
			}
		}
		
		response.getOutputStream().write("1".getBytes());
	}
	/**
	 * 给指定的设备推送播放画面同步
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("pushSynchPlayTime")
	public void pushSynchPlayTime(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");
		String synchTime = ParamsUtil.nullDeal(params, "synchTime", "");
		if(!"".equals(deviceIds)){
			String devcieIdArray[] = deviceIds.split(",");
			for(String uuid : devcieIdArray){
				socketService.sendMessageByUuid(uuid,"ad_synch",synchTime);
			}
		}
		
		response.getOutputStream().write("1".getBytes());
	}
	
	@RequestMapping("pushVipInAndOut")
	public void pushVipInAndOut(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		
		
		int inn = Integer.parseInt(ParamsUtil.nullDeal(params,"inn", "0"));
		int out = Integer.parseInt(ParamsUtil.nullDeal(params,"out", "0"));
		Map dataMap = new HashMap();
		dataMap.put("type", "vip_person_in_out");//socket事件类型
		dataMap.put("inn", inn);
		dataMap.put("out",  out);
		String json = JSONObject.fromObject(dataMap).toString();
		//发送消息给前端
		new WebsocketEndPoint().sendMessageToUsers(new TextMessage(json));
		response.getOutputStream().write("1".getBytes());
	}
}
