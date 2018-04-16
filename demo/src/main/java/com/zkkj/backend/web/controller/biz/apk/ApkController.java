package com.zkkj.backend.web.controller.biz.apk;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.exception.FileException;
import com.zkkj.backend.common.socketio.BinaryEventLauncher;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.websocket.WebSocketLauncher;
import com.zkkj.program.service.IApkService;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.ApkServiceImpl;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.program.util.Config;

@Controller
@RequestMapping(value="apk")
public class ApkController {
//	BinaryEventLauncher socketService;
	WebSocketLauncher socketService;
	IApkService apkService = new ApkServiceImpl();
	IDeviceService deviceService = new DeviceServiceImpl();
	/**
	 * apk同步页面初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("synchApkInit")
	public ModelAndView synchApkInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		model.setViewName("apk/synchApk");
		return model;
	}
	
	/**
	 * apk同步并发布
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("synchApk")
	@ResponseBody
	public void synchApk(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String viproomId = ParamsUtil.nullDeal(params, "viproomId", "");
		String resourceId = ParamsUtil.nullDeal(params, "resourceId", "");
		String versionCode = ParamsUtil.nullDeal(params, "versionCode", "");
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");
		
		try {
			if("".equals(viproomId)){
				resultMap.put("result", "0");
				resultMap.put("message", "请选择同步的厅服务器");
				return;
			}
			if("".equals(resourceId)){
				resultMap.put("result", "0");
				resultMap.put("message", "请上传apk");
				return;
			}
			if("".equals(versionCode)){
				resultMap.put("result", "0");
				resultMap.put("message", "apk版本号不能为空");
				return;
			}
			if("".equals(deviceIds)){
				resultMap.put("result", "0");
				resultMap.put("message", "请选择同步的设备");
				return;
			}
			
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			String downloadPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z1");
			String viproom_username = (String)CustomizedPropertyConfigurer.getContextProperty("viproom_username");
			String viproom_password = (String)CustomizedPropertyConfigurer.getContextProperty("viproom_password");
			String basePath = (String)CustomizedPropertyConfigurer.getContextProperty("base_upload_path");
			String apkUploadPath = (String)CustomizedPropertyConfigurer.getContextProperty("apk_upload_path");
			String packageName = (String)CustomizedPropertyConfigurer.getContextProperty("packageName");
			Config conf = new Config();
			conf.setFlag(1);//设置下载路径，默认资源访问路径
			conf.setRealPath(realPath);
			conf.setDownloadRealPath(downloadPath);
			conf.setPackageName(packageName);
			conf.setSychPath(basePath + apkUploadPath);
			conf.setAccount(viproom_username);
			conf.setPassword(viproom_password);
			conf.setPort(22);
			resultMap = apkService.synchApk(params, conf);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 直接从核心服务器推送
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("pushApk")
	@ResponseBody
	public void pushApk(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String resourceId = ParamsUtil.nullDeal(params, "resourceId", "");//资源id
		String versionCode = ParamsUtil.nullDeal(params, "versionCode", "");//版本号
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");//选中的设备id
		
		try {
			if("".equals(resourceId)){
				resultMap.put("result", "0");
				resultMap.put("message", "请上传apk");
				return;
			}
			if("".equals(versionCode)){
				resultMap.put("result", "0");
				resultMap.put("message", "apk版本号不能为空");
				return;
			}
			if("".equals(deviceIds)){
				resultMap.put("result", "0");
				resultMap.put("message", "请选择同步的设备");
				return;
			}
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			String downloadPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z1");
			String deviceType = (String)CustomizedPropertyConfigurer.getContextProperty("device_type");
			String deviceId = (String)CustomizedPropertyConfigurer.getContextProperty("device_id");
			String packageName = (String)CustomizedPropertyConfigurer.getContextProperty("packageName");
			Config conf = new Config();
			conf.setRealPath(realPath);
			conf.setDownloadRealPath(downloadPath);
			conf.setConfigServerType(deviceType);
			conf.setConfigServerId(deviceId);
			conf.setPackageName(packageName);
			resultMap = apkService.pushApk(params, conf);
			
			String apkJson = String.valueOf(resultMap.get("apkJson"));
			
			String deviceIdArray[] = deviceIds.split(",");
			for(String uuid : deviceIdArray){
				//给设备推送节目单
				socketService.sendMessageByUuid(uuid,"apk_update",apkJson);
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	/**
	 * 下载apk推送json文件
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws FileException 
	 */
	@RequestMapping("downloadApkJson")
	public void downloadApkJson(HttpServletRequest request,HttpServletResponse response) throws FileException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			String synchId = ParamsUtil.nullDeal(params, "synchId", "");
			Map synchParamsMap = new HashMap();
			synchParamsMap.put("synchId", synchId);
			Map synchMap = deviceService.getApkSynchDeviceInfo(synchParamsMap);
			String pushJson = "";
			if(synchMap != null){
				pushJson = String.valueOf(synchMap.get("pushJson"));
			}
			
			pushJson = GsonUtil.formatJson(pushJson);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/form-data");
			String fileName = new String("apkJson".getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;fileName=apkJson.txt");
			response.setBufferSize(1024);
			
			/*File file = new File(zipFilePath);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();*/
			OutputStream os = response.getOutputStream();
			os.write(pushJson.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			//发生异常记录带异常表
			FileException fileException=new FileException();
			fileException.setExceptionCode("SE_FILE_ERROR");
			fileException.setStackTrace(e.getStackTrace());
			throw fileException;
		}
	}
}
