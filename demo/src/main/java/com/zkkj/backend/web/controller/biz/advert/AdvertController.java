package com.zkkj.backend.web.controller.biz.advert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;

import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.websocket.WebsocketEndPoint;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.IPlayLogService;
import com.zkkj.program.service.IResourceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.program.service.impl.PlayLogServiceImpl;
import com.zkkj.program.service.impl.ResourceServiceImpl;

@Controller
@RequestMapping(value="advert")
public class AdvertController {
	
	IPlayLogService playLogService = new PlayLogServiceImpl();
	IDeviceService deviceService = new DeviceServiceImpl();
	IResourceService resourceService = new ResourceServiceImpl();
	
	@RequestMapping(value="addAvertPlayLog")
	public void addAvertPlayLog(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			
			String adLog = ParamsUtil.nullDeal(params, "adLog", "");
			List<Map> logList = GsonUtil.getGson().fromJson(adLog, List.class);
			
			int count = logList.size();
			if(logList != null && logList.size() > 0){
				for(Map<String,String> mm : logList){
					String uuid = ParamsUtil.nullDeal(mm, "deviceId", "");
					String synchId = ParamsUtil.nullDeal(mm, "synchId", "");//节目单同步资源的id 
					String resourceId = ParamsUtil.nullDeal(mm, "resourceId", "");//资源id
					String advertiserId = ParamsUtil.nullDeal(mm, "advertiserId", "");//资源id
					String fileName = ParamsUtil.nullDeal(mm, "fileName", "");//资源名称
					
					if("".equals(synchId)){
						return;
					}
					playLogService.addPlayLog(mm);//保存日志信息
					//广告播放为公益广告的信息，保存日志后结束程序
					if("".equals(synchId)){
						return;
					}
					
					//查询当前播放资源的路径
					String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
					
					Map resourceParamsMap = new HashMap();
					resourceParamsMap.put("realPath", realPath);
					resourceParamsMap.put("fileName", fileName);
					resourceParamsMap.put("resourceId", resourceId);
					
					List<Map> resourceList = resourceService.getResource(resourceParamsMap);
					if(resourceList != null && resourceList.size() > 0){
						Map resourceMap = resourceList.get(0);
						String path = ParamsUtil.nullDeal(resourceMap, "filePath", "");
						Map deviceOneLogParamsMap = new HashMap();
						deviceOneLogParamsMap.put("uuid",uuid);
						deviceOneLogParamsMap.put("synchId", synchId);
						deviceOneLogParamsMap.put("resourceId",resourceId);
						deviceOneLogParamsMap.put("path", path);
						//更新单条设备的日志信息
						deviceService.addOrEditDevicePlayLogOneByUuid(deviceOneLogParamsMap);
						//当前正在播的页面同步到前端
						if(count == 1){
							//当前正在播放的日志
							try {
								//通过websocket向前端推送当前播放的画面
								Map pushMap = new HashMap();
								pushMap.put("type", "play_synch");
								pushMap.put("uuid", uuid);
								pushMap.put("path", path);
								String content = JSONObject.fromObject(pushMap).toString();
								new WebsocketEndPoint().sendMessageToUsers(new TextMessage(content));
								
								//查询设备信息
								Map deviceInfoParamsMap = new HashMap();
								deviceInfoParamsMap.put("uuid", uuid);
								Map deviceMap = deviceService.getDeviceOne(deviceInfoParamsMap);
								String deviceIp = ParamsUtil.nullDeal(deviceMap,"deviceIp", "");
								System.out.println(DateUtil.getStringDate()+"---["+deviceIp+"]广告播放日志***************************[uuid="+uuid+",synchId="+synchId+"]");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message",e.getMessage());
			System.out.println(e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
}
