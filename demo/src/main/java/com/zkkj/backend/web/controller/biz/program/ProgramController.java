package com.zkkj.backend.web.controller.biz.program;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.exception.AdvertException;
import com.zkkj.backend.common.exception.DataBaseException;
import com.zkkj.backend.common.exception.FileException;
import com.zkkj.backend.common.util.CipherUtil;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.FileUtil;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.util.ZipUtil;
import com.zkkj.backend.common.websocket.WebSocketLauncher;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.IProgramService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.program.service.impl.ProgramServiceImpl;
import com.zkkj.program.util.Config;
import com.zkkj.program.util.Page;

@Controller
@RequestMapping("program")
public class ProgramController {
	
//	BinaryEventLauncher socketService;
	WebSocketLauncher socketService;
	
	private IProgramService programService = new ProgramServiceImpl();
	private IDeviceService deviceService = new DeviceServiceImpl();
	@RequestMapping("programListInit")
	public ModelAndView programListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("program/programList");
		return model;
	}
	/**
	 * 查询节目单列表json 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("programList")
	@ResponseBody
	public void programList(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = programService.getProgramList(params, page);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", programService.getProgramListCount(params));
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message",e.getMessage());
			System.out.println(e.getMessage());
			//发生异常记录到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	/**
	 * 添加或者修改节目单信息初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("addOrEditProgramInit")
	public ModelAndView addOrEditInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		if(!"".equals(id)){
			//查询单条节目单的数据
            model.addObject("resultInfo",programService.getProgramById(params));
		}
		model.setViewName("program/programEdit");
		return model;
	}
	/**
	 * 添加删除节目单
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws DataBaseException 
	 */
	@RequestMapping("addOrEditProgram")
	@ResponseBody
	public void addOrEditProgramiser(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
        try{
			int count = programService.addOrEditProgram(params);
			resultMap.put("result", count > 0 ? 1 : 0);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 删除节目单
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("deleteProgram")
	@ResponseBody
	public void deleteProgram(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		Map resultMap = new HashMap();
		try {
			if(!"".equals(id)){
				List<String> list = new ArrayList<String>();
				String []array = id.split(",");
				for(String ss : array){
					list.add(ss);
				}
				int count = programService.deleteProgram(list);
				resultMap.put("result",count > 0 ? 1 : 0);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 审核节目单初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("examinProgramInit")
	public ModelAndView examinProgramInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		model.setViewName("program/examinProgram");
		return model;
	}
	/**
	 * 审核节目单
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("examinProgram")
	@ResponseBody
	public void examinProgram(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String id = ParamsUtil.nullDeal(params, "id", "");
		String examStatus = ParamsUtil.nullDeal(params, "examStatus", "");
		String examRemark = ParamsUtil.nullDeal(params, "examRemark", "");
		
		try {
			if("".equals(id)){
				resultMap.put("result", "0");
				resultMap.put("message", "节目单id不能为空");
				return;
			}
			if("".equals(examStatus)){
				resultMap.put("result", "0");
				resultMap.put("message", "审核状态不能为空");
				return;
			}
			int count = programService.examinProgram(params);
			resultMap.put("result", count);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 节目单资源管理初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("programResourceManageInit")
	public ModelAndView programResourceManageInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String programId = ParamsUtil.nullDeal(params, "programId", "");
		if(!"".equals(programId)){
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			Map programMap = programService.getProgramJsonToUiById(params, conf);
			//查询单条节目单的数据
            model.addObject("data",programMap);
		}
        model.addObject("programId",programId);
		model.setViewName("program/programResourceManage");
		return model;
	}
	
	/**
	 * 节目单同步页面初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("synchProgramInit")
	public ModelAndView synchProgramInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		model.setViewName("program/synchProgram");
		return model;
	}
	
	/**
	 * 节目单同步,并发布
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws AdvertException 
	 */
	@RequestMapping("synchProgram")
	@ResponseBody
	public void synchProgram(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, AdvertException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String programId = ParamsUtil.nullDeal(params, "programId", "");
		String airportId = ParamsUtil.nullDeal(params, "airportId", "");
		String viproomId = ParamsUtil.nullDeal(params, "viproomId", "");
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");
		
		try {
			if("".equals(programId)){
				resultMap.put("result", "0");
				resultMap.put("message", "节目单id不能为空");
				return;
			}
			if("".equals(airportId)){
				resultMap.put("result", "0");
				resultMap.put("message", "请选择同步的机场");
				return;
			}
			if("".equals(viproomId)){
				resultMap.put("result", "0");
				resultMap.put("message", "请选择同步的厅服务器");
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
			String advertPath = (String)CustomizedPropertyConfigurer.getContextProperty("advert_upload_path");
			Config conf = new Config();
			conf.setRealPath(realPath);
			conf.setDownloadRealPath(downloadPath);
			conf.setSychPath(basePath + advertPath);
			conf.setAccount(viproom_username);
			conf.setPassword(viproom_password);
			conf.setPort(22);
			resultMap = programService.synchProgram(params, conf);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录到异常表
			AdvertException advertException=new AdvertException();
			advertException.setExceptionCode("SE_AD_ERROR");
			advertException.setStackTrace(e.getStackTrace());
			throw advertException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 直接从核心服务器或者厅服务器直接发布节目单
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("pushProgram")
	@ResponseBody
	public void pushProgram(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String programId = ParamsUtil.nullDeal(params, "programId", "");//节目单id，必须
		String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");//选中的设备id
		
		try {
			if("".equals(programId)){
				resultMap.put("result", "0");
				resultMap.put("message", "节目单id不能为空");
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
			Config conf = new Config();
			conf.setRealPath(realPath);
			conf.setDownloadRealPath(downloadPath);
			conf.setConfigServerType(deviceType);
			conf.setConfigServerId(deviceId);
			resultMap = programService.pushProgram(params, conf);
			
			String result = String.valueOf(resultMap.get("result"));
			if("1".equals(result)){
				String programJson = String.valueOf(resultMap.get("programJson"));
				
				String deviceIdArray[] = deviceIds.split(",");
				for(String uuid : deviceIdArray){
					//给设备推送节目单
					socketService.sendMessageByUuid(uuid,"advert_info",programJson);
				}
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
	 * 获取节目单json数据 用户前端使用
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("getProgramJsonToUiById")
	@ResponseBody
	public void getProgramJsonToUiById(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String programId = ParamsUtil.nullDeal(params, "programId", "");
		
		try {
			if("".equals(programId)){
				resultMap.put("result", "0");
				resultMap.put("message", "节目单id不能为空");
				return;
			}
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			Map programMap = programService.getProgramJsonToAndroidById(params, conf);
			resultMap.put("result", "1");
			resultMap.put("data", programMap);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 获取节目单的同步信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("getProgramSynchInfoById")
	@ResponseBody
	public void getProgramSynchInfoById(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String programId = ParamsUtil.nullDeal(params, "programId", "");
		
		try {
			if("".equals(programId)){
				resultMap.put("result", "0");
				resultMap.put("message", "节目单id不能为空");
				return;
			}
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			List<Map> list = programService.getProgramSynchInfoById(params, conf);
			resultMap.put("result", "1");
			resultMap.put("data", list);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 节目单广告商的排序
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("sortProgramAdvertiser")
	@ResponseBody
	public void sortProgramAdvertiser(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		
		try {
			if("".equals(ids)){
				resultMap.put("result", "0");
				resultMap.put("message", "排序的id不能为空");
				return;
			}
			int count  = programService.sortProgramAdvertiser(params);
			resultMap.put("result",count);
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
	 * 删除节目单广告商
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("deleteProgramAdvertiser")
	@ResponseBody
	public void deleteProgramAdvertiser(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String id = ParamsUtil.nullDeal(params, "id", "");
		
		try {
			if("".equals(id)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			int count  = programService.deleteProgramAdvertiser(params);
			resultMap.put("result",count);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 查询节目单广告商的信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("getProgramAdvertiserInfo")
	@ResponseBody
	public void getProgramAdvertiserInfo(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String id = ParamsUtil.nullDeal(params, "id", "");
		
		try {
			if("".equals(id)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			Map result  = programService.getProgramAdvertiserInfo(params, conf);
			resultMap.put("result",1);
			resultMap.put("data",result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 添加或者修节目单初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("addOrEditProgramAdvertiserInit")
	public ModelAndView addOrEditProgramAdvertiserInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String operatorType = ParamsUtil.nullDeal(params, "operatorType", "");
		String pId = ParamsUtil.nullDeal(params, "pId", "");
		String aId = ParamsUtil.nullDeal(params, "aId", "");
		String paId = ParamsUtil.nullDeal(params, "paId", "");
		String advertiserType = ParamsUtil.nullDeal(params, "advertiserType", "");
		//查询广告商信息
		model.setViewName("program/addOrEditProgramAdvertiser");
		model.addObject("operatorType",operatorType);
		model.addObject("pId",pId);
		model.addObject("aId",aId);
		model.addObject("paId",paId);
		model.addObject("advertiserType",advertiserType);
		if(!"".equals(paId)){
			Map mm = new HashMap();
			mm.put("id", paId);
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			Map result  = programService.getProgramAdvertiserInfo(mm, conf);
			model.addObject("data",result);
		}
		return model;
	}
	/**
	 * 添加或者编辑节目单广告商信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("addOrEditProgramAdvertiser")
	@ResponseBody
	public void addOrEditProgramAdvertiser(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String pId = ParamsUtil.nullDeal(params, "pId", "");
		String aId = ParamsUtil.nullDeal(params, "aId", "");
		String paId = ParamsUtil.nullDeal(params, "paId", "");
		String frequency = ParamsUtil.nullDeal(params, "frequency", "");
		String path2 = ParamsUtil.nullDeal(params, "path2", "");
		String advertiserType = ParamsUtil.nullDeal(params, "advertiserType", "");
		
		try {
			if("".equals(pId)){
				resultMap.put("result", "0");
				resultMap.put("message", "节目单id不能为空");
				return;
			}
			if("".equals(aId)){
				resultMap.put("result", "0");
				resultMap.put("message", "广告商id不能为空");
				return;
			}
			if("".equals(frequency)){
				resultMap.put("result", "0");
				resultMap.put("message", "播放频次不能为空");
				return;
			}
			if("".equals(advertiserType)){
				resultMap.put("result", "0");
				resultMap.put("message", "广告商分类不能为空");
				return;
			}
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			Map result  = programService.addOrEditProgramAdvertiser(params, conf);
			resultMap.put("result",1);
			resultMap.put("data",result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 拖拽排序节目单广告商的各轮
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("sortProgramAdvertiserRing")
	@ResponseBody
	public void sortProgramAdvertiserRing(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		
		try {
			if("".equals(ids)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			int count  = programService.sortProgramAdvertiserRing(params);
			resultMap.put("result",count);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 删除节目广告商的指定轮的信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("deleteProgramAdvertiserRing")
	@ResponseBody
	public void deleteProgramAdvertiserRing(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String id = ParamsUtil.nullDeal(params, "id", "");
		
		try {
			if("".equals(id)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			int count  = programService.deleteProgramAdvertiserRing(params);
			resultMap.put("result",count);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 查询节目单广告商某轮的信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("getProgramAdvertiserRing")
	@ResponseBody
	public void getProgramAdvertiserRing(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String id = ParamsUtil.nullDeal(params, "id", "");
		
		try {
			if("".equals(id)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			Map result  = programService.getProgramAdvertiserRing(params, conf);
			resultMap.put("result", "1");
			resultMap.put("data", result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 查询节目单广告商某轮的信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("getProgramAdvertiserRingView")
	public ModelAndView getProgramAdvertiserRingView(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		if(!"".equals(id)){
			try {
				String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
				Config conf = new Config();
				conf.setRealPath(realPath);
				Map result  = programService.getProgramAdvertiserInfo(params, conf);
				model.addObject("data", result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
				//发生异常记录带异常表
				DataBaseException dataBaseException=new DataBaseException();
				dataBaseException.setExceptionCode("SE_DB_ERROR");
				dataBaseException.setStackTrace(e.getStackTrace());
				throw dataBaseException;
			}
		}
		model.setViewName("program/programAdvertiserRingView");
		return model;
	}
	/**
	 * 添加或编辑节目单广告商的某轮信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("addOrEditProgramAdvertisrRing")
	@ResponseBody
	public void addOrEditProgramAdvertisrRing(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String paId = ParamsUtil.nullDeal(params, "paId", "");
		String parId = ParamsUtil.nullDeal(params, "parId", "");
		String beginTime = ParamsUtil.nullDeal(params, "beginTime", "");
		String endTime = ParamsUtil.nullDeal(params, "endTime", "");
		String resource = ParamsUtil.nullDeal(params, "resource", "");
		
		/*if("".equals(beginTime)){
			resultMap.put("result", "0");
			resultMap.put("message", "开始时间不能为空");
			return;
		}
		if("".equals(endTime)){
			resultMap.put("result", "0");
			resultMap.put("message", "结束时间不能为空");
			return;
		}*/
		try {
			if("".equals(paId)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
			Config conf = new Config();
			conf.setRealPath(realPath);
			Map result  = programService.addOrEditProgramAdvertisrRing(params, conf);
			resultMap.put("result", "1");
			resultMap.put("data", result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 删除节目单广告商轮资源信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("deleteProgramAdvertiserRingResource")
	@ResponseBody
	public void deleteProgramAdvertiserRingResource(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		String id = ParamsUtil.nullDeal(params, "id", "");
		
		
		try {
			if("".equals(id)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			int count  = programService.deleteProgramAdvertiserRingResource(params);
			resultMap.put("result",count);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 节目单广告商每轮的资源的排序
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("sortProgramAdvertiserRingResorce")
	@ResponseBody
	public void sortProgramAdvertiserRingResorce(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap(); 
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		
		try {
			if("".equals(ids)){
				resultMap.put("result", "0");
				resultMap.put("message", "id不能为空");
				return;
			}
			int count  = programService.sortProgramAdvertiserRingResorce(params);
			resultMap.put("result",count);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常记录带异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	/**
	 * 下载节目单json文件
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws FileException 
	 */
	@RequestMapping("downloadProgramJson")
	public void downloadProgramJson(HttpServletRequest request,HttpServletResponse response) throws FileException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			String synchId = ParamsUtil.nullDeal(params, "synchId", "");
			Map synchParamsMap = new HashMap();
			synchParamsMap.put("synchId", synchId);
			Map synchMap = deviceService.getProgramSynchInfo(synchParamsMap);
			String programJson = "";
			if(synchMap != null){
				programJson = String.valueOf(synchMap.get("programJson"));
			}
			
			programJson = GsonUtil.formatJson(programJson);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/form-data");
			String fileName = new String("program".getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;fileName=programJson.txt");
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
			os.write(programJson.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			//发生异常记录带异常表
			FileException fileException=new FileException();
			fileException.setExceptionCode("SE_FILE_ERROR");
			fileException.setStackTrace(e.getStackTrace());
			throw fileException;
		}
	}
	
	/**
	 * 节目单打包下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("programDownload")
	public void programDownload(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");//图片访问地址
		//zip文件保存路径
		String savePath = "";
		//获取文件上传根目录
		String basePath = (String)CustomizedPropertyConfigurer.getContextProperty("base_upload_path");
		//查询广告的保存路径
		String advertPath = (String)CustomizedPropertyConfigurer.getContextProperty("advert_upload_path");
		//获取广告上传目录
		String advertZipUploadPath = (String)CustomizedPropertyConfigurer.getContextProperty("advert_zip_upload_path");
		savePath = basePath + advertZipUploadPath;
		
		try {
			//所有要打包的文件列表
			List filesTotal = new ArrayList();
			//所有的文件列表
			List<Map> files = new ArrayList<Map>();
			//节目单jsonw文件
			String programJson = "";
			//节目单版本号
			String version = "";
			//节目单开始时间
			String beginTime = "";
			//节目单结束时间
			String endTime = "";
			//资源同步的id
			String synchId = ParamsUtil.nullDeal(params, "synchId", "");
			//查询节目单json文件
			Map synchInfoMap = deviceService.getProgramSynchInfo(params);
			if(synchInfoMap != null){
				//取到节目单json文件
				programJson = ParamsUtil.nullDeal(synchInfoMap, "programJson", "");
				if(!"".equals(programJson)){
					//解析josn字符串为对象
					Map programMap = GsonUtil.getGson().fromJson(programJson, Map.class);
					//循环遍历节目单json对象取到节目单的资源文件
					if(programMap != null){
						Map resultMap = (Map) programMap.get("result");
						List<Map> versionList = (List<Map>) programMap.get("version");//节目单版本号
						List<Map> aList = (List<Map>) resultMap.get("A");//A类广告商
						List<Map> bList = (List<Map>) resultMap.get("B");//B类广告商
						beginTime = String.valueOf(programMap.get("beginTime"));//节目单开始时间
						endTime = String.valueOf(programMap.get("endTime"));//节目单结束时间
						if(aList != null && aList.size() > 0){
							for(Map aMap : aList){
								List<Map> path1List = (List<Map>) aMap.get("path1");//广告商的path1
								List<Map> path2List = (List<Map>) aMap.get("path2");//广告商的path2
								if(path1List != null && path1List.size() > 0){
									for(Map path1Map : path1List){
										//取到path1每轮的资源列表对象
										List<Map> list = (List<Map>) path1Map.get("list");
										if(list != null && list.size() >0){
											//循环每轮的资源列表
											for(Map listMap : list){
												//取到资源的访问地址
												String url = ParamsUtil.nullDeal(listMap, "url", "");
												//更改资源的路径为本地访问的路径
												String fileUrl = url.replace(url.substring(0, url.indexOf(advertPath)), realPath);
												//将资源地址添加到文件列表中
												URL url1 = new URL(fileUrl);
												HttpURLConnection connection1=(HttpURLConnection)url1.openConnection();
												Map map1 = new HashMap();
												map1.put("inputStream", connection1.getInputStream());
												String file1Name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
												map1.put("fileName", file1Name);
												files.add(map1);
											}
										}
									}
								}
								if(path2List != null && path2List.size() > 0){
									for(Map path2Map : path2List){
										//取到path2的资源对象
										String path2Url = ParamsUtil.nullDeal(path2Map, "url", "");
										//更改资源的路径为本地访问的路径
										String fileUrl = path2Url.replace(path2Url.substring(0, path2Url.indexOf(advertPath)), realPath);
										//将资源地址添加到文件列表中
										URL url1 = new URL(fileUrl);
										HttpURLConnection connection1=(HttpURLConnection)url1.openConnection();
										Map map1 = new HashMap();
										map1.put("inputStream", connection1.getInputStream());
										String file1Name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
										map1.put("fileName", file1Name);
										files.add(map1);
									}
								}
							}
						}
						if(bList != null && bList.size() > 0){
							for(Map aMap : bList){
								List<Map> path1List = (List<Map>) aMap.get("path1");//广告商的path1
								List<Map> path2List = (List<Map>) aMap.get("path2");//广告商的path2
								if(path1List != null && path1List.size() > 0){
									for(Map path1Map : path1List){
										//取到path1每轮的资源列表对象
										List<Map> list = (List<Map>) path1Map.get("list");
										if(list != null && list.size() >0){
											//循环每轮的资源列表
											for(Map listMap : list){
												//取到资源的访问地址
												String url = ParamsUtil.nullDeal(listMap, "url", "");
												//更改资源的路径为本地访问的路径
												String fileUrl = url.replace(url.substring(0, url.indexOf(advertPath)), realPath);
												try {
													//将资源地址添加到文件列表中
													URL url1 = new URL(fileUrl);
													HttpURLConnection connection1=(HttpURLConnection)url1.openConnection();
													Map map1 = new HashMap();
													map1.put("inputStream", connection1.getInputStream());
													String file1Name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
													map1.put("fileName", file1Name);
													files.add(map1);
												} catch (Exception e) {
													// TODO: handle exception
													System.out.println(e.getMessage());
												}
											}
										}
									}
								}
								if(path2List != null && path2List.size() > 0){
									for(Map path2Map : path2List){
										//取到path2的资源对象
										String path2Url = ParamsUtil.nullDeal(path2Map, "url", "");
										//更改资源的路径为本地访问的路径
										String fileUrl = path2Url.replace(path2Url.substring(0, path2Url.indexOf(advertPath)), realPath);
										try {
											//将资源地址添加到文件列表中
											URL url1 = new URL(fileUrl);
											HttpURLConnection connection1=(HttpURLConnection)url1.openConnection();
											Map map1 = new HashMap();
											map1.put("inputStream", connection1.getInputStream());
											String file1Name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
											map1.put("fileName", file1Name);
											files.add(map1);
										} catch (Exception e) {
											System.out.println(e.getMessage());
										}
									}
								}
							}
						}
						if(versionList !=null && versionList.size() > 0){
							//取到节目单的版本号
							Map versionMap = versionList.get(0);
							if(versionMap != null){
								//取到节目单版本号
								version = ParamsUtil.nullDeal(versionMap,"version", "");
							}
						}
					}
				}
			}
			
			//节目单json文件的名称
			String programJsonFileName = "ad.json";
			String nowTime = DateUtil.getStringDate();
			if(beginTime.compareTo(nowTime) > 0){
				//节目单待播放
				programJsonFileName = "beforeAd.json";
			}
			//生成json文件
	        String jsonPath = savePath+programJsonFileName;
	        File jsonFile = new File(jsonPath);
	        if (!jsonFile.getParentFile().exists())
	        	jsonFile.getParentFile().mkdirs();
			if (!jsonFile.exists()){   
				jsonFile.createNewFile();   
	        }
	        response.reset();
	        //创建文件输出流
	        FileOutputStream fous1 = new FileOutputStream(jsonFile); 
	        fous1.write(programJson.getBytes());
	        fous1.close();
	        
	        Map map3 = new HashMap();
	        map3.put("inputStream", new FileInputStream(new File(jsonPath)));
			map3.put("fileName",programJsonFileName);
			files.add(map3);
	        
			//生成version版本号
			String versionPath = savePath+"version.txt";
	        File versionFile = new File(versionPath);
			if (!versionFile.exists()){   
				versionFile.createNewFile();   
	        }
	        response.reset();
	        //创建文件输出流
	        FileOutputStream fous3 = new FileOutputStream(versionFile); 
	        fous3.write(String.valueOf(version).getBytes());
	        fous3.close();
	        
			//创建一个临时压缩文件,我们会把文件流全部注入到这个文件, 这里的文件你可以自定义是.rar还是.zip
			//图片的压缩包
			String picZipPath = savePath+"pic.zip";
			File picFile = new File(picZipPath);
			if (!picFile.exists()){
				picFile.createNewFile();   
	        }
	        response.reset();
	        //创建文件输出流
	        FileOutputStream fous = new FileOutputStream(picFile); 
	        //打包的方法我们会用到ZipOutputStream这样一个输出流,所以这里我们把输出流转换一下
	        ZipOutputStream zipOut = new ZipOutputStream(fous);
	        ZipUtil.zipInputStreamFile(files, zipOut);
	        zipOut.close();
	        fous.close();
	        
	        
	        //对图片压缩文件进行签名
	        CipherUtil.signature(picZipPath,savePath + "public.key",savePath + "private.key",savePath + "jiami.txt");
	        
	        filesTotal.add(new File(picZipPath));//添加图片打包文件
	        filesTotal.add(new File(savePath + "public.key"));//添加公钥
	        filesTotal.add(new File(savePath + "jiami.txt"));//添加md5
	        filesTotal.add(new File(savePath + "version.txt"));//版本号
	        
	        //所有的压缩文件
	        String zipPath = savePath + "program" + synchId + ".zip";
	        File zipFile = new File(zipPath);
			if (!zipFile.exists()){   
				zipFile.createNewFile();
	        }
	        response.reset();
	        //创建文件输出流
	        FileOutputStream fous2 = new FileOutputStream(zipFile); 
	        //打包的方法我们会用到ZipOutputStream这样一个输出流,所以这里我们把输出流转换一下
	        ZipOutputStream zipOut2 = new ZipOutputStream(fous2);
	        ZipUtil.zipFile(filesTotal, zipOut2);	
	        zipOut2.close();
	        fous2.close();
	        
	        //删除文件
	        FileUtil.deleteFile(savePath+"ad.json");
	        FileUtil.deleteFile(savePath+"jiami.txt");
	        FileUtil.deleteFile(savePath+"version.txt");
	        FileUtil.deleteFile(savePath+"jiami.txt.encrypt");
	        FileUtil.deleteFile(savePath+"pic.zip");
	        FileUtil.deleteFile(savePath+"private.key");
	        FileUtil.deleteFile(savePath+"public.key");
	        
	        
	        
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/form-data");
			String fileName = new String("program".getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName+".zip");
			response.setBufferSize(1024);
			// 文件路径
			File file = new File(zipPath);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			response.getWriter().print(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			response.getWriter().print(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			response.getWriter().print(e.getMessage());
		}
	}
}