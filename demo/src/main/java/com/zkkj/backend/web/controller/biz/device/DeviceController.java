package com.zkkj.backend.web.controller.biz.device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.exception.DeviceException;
import com.zkkj.backend.common.socketio.BinaryEventLauncher;
import com.zkkj.backend.common.socketio.SocketConstant;
import com.zkkj.backend.common.socketio.SocketUtil;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.websocket.WebSocketLauncher;
import com.zkkj.backend.entity.biz.ZkkjUser;
import com.zkkj.backend.web.controller.BaseController;
import com.zkkj.exceptionInfo.service.IExceptionRecordService;
import com.zkkj.exceptionInfo.service.impl.ExceptionRecordServiceImpl;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.IProgramService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.program.service.impl.ProgramServiceImpl;
import com.zkkj.program.util.Page;

/**
 * @author liuliyu
 *
 */

@Controller
@RequestMapping(value="device")
public class DeviceController extends BaseController{
	
//	BinaryEventLauncher socketService;
	WebSocketLauncher socketService;
	IDeviceService deviceService = new DeviceServiceImpl();
	IExceptionRecordService exceptionService = new ExceptionRecordServiceImpl();
	
	
	
	@RequestMapping(value="deviceListInit")
	public ModelAndView deviceListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.addObject("object","");
		model.setViewName("device/deviceList");
		return model;
	}
	
	/**
	 * 查询设备列表
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="deviceList")
	@ResponseBody
	public void deviceList(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "pageNo", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "pageSize", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = deviceService.getDeviceList(params, page);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", deviceService.getDeviceListCount(params));
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message",e.getMessage());
			System.out.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
			
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 添加或者编辑设备信息初始化
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="addOrEditDeviceInit")
	public ModelAndView addOrEditDeviceInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");//设备id
		if(!"".equals(id)){
			//修改设备信息你
			Map deviceMap =  deviceService.getDeviceOne(params);
			
			model.addObject("data", deviceMap);
		}
		model.setViewName("device/deviceEdit");
		return model;
	}
	/**
	 * 更新设备信息
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="addOrEditDevice")
	@ResponseBody
	public void addOrEditDevice(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String id = ParamsUtil.nullDeal(params, "id", "");//设备id
			int count = 0;
			if(!"".equals(id)){
				//修改设备信息你
				count = deviceService.updateDeviceInfo(params);
			}
			resultMap.put("result", count > 0 ? 1 : 0);
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	/**
	 * 删除设备
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="deleteDevice")
	@ResponseBody
	public void deleteDevice(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String id = ParamsUtil.nullDeal(params, "id", "");//设备id
			int count = 0;
			if(!"".equals(id)){
				Map deviceParamsMap = new HashMap();
				deviceParamsMap.put("deviceId", id);
				count = deviceService.deleteDevice(deviceParamsMap);
			}
			resultMap.put("result", count > 0 ? 1 : 0);
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 查询设备列表JSON
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="getDeviceJson")
	@ResponseBody
	public void getDeviceJson(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		List<Map> list = new ArrayList<Map>();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			List<Map> deviceList = deviceService.getDeviceList(params);
			list = deviceList != null && deviceList.size() > 0 ? deviceList : Collections.EMPTY_LIST;
		} catch (Exception e) {
			// TODO: handle exception
			list = Collections.EMPTY_LIST;
			System.out.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(list).getBytes("UTF-8"));
		}
	}
	/**
	 * 设备选择页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("deviceSelectInit")
	public ModelAndView deviceSelectInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String airlineId = ParamsUtil.nullDeal(params, "airlineId", "");
		String airportId = ParamsUtil.nullDeal(params, "airportId", "");
		String viproomId = ParamsUtil.nullDeal(params, "viproomId", "");
		String isOther = ParamsUtil.nullDeal(params, "isOther", "");
		if(!"".equals(viproomId)){
			//查询单条节目单的数据
            model.addObject("data",deviceService.getDeviceList(params));
		}else if(!"".equals(isOther)){
			model.addObject("data",deviceService.getUnViproomDevice(params));
		}
		model.setViewName("device/deviceSelect");
		return model;
	}
	/**
	 * 设备信息展示页面,设备监控
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("deviceInfoShow")
	public ModelAndView deviceInfoShow(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String viproomId = ParamsUtil.nullDeal(params, "viproomId", "");
		if(!"".equals(viproomId)){
			//查询单条节目单的数据
            model.addObject("data",deviceService.getDeviceDetailInfoList(params));
		}
		model.setViewName("device/deviceInfoShow");
		return model;
	}
	
	/**
	 * 查询设备资源下载失败的信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="getProgramSynchDeviceDownloadErrorInfo")
	@ResponseBody
	public ModelAndView getProgramSynchDeviceDownloadErrorInfo(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
		params.put("realPath", realPath);
		List<Map> resultList = deviceService.getProgramSynchDeviceDownloadErrorInfo(params);
		model.addObject("data",resultList);
		model.setViewName("device/deviceDowloadErrorInfo");
		return model;
	}
	
	
	@RequestMapping(value="getDeviceExceptionInfo")
	@ResponseBody
	public ModelAndView getDeviceExceptionInfo(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map> resultList = exceptionService.getExceptionRecordList(params);
		model.addObject("data",resultList);
		model.setViewName("device/deviceExceptionInfo");
		return model;
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="getDevicePushPlaySynchInfo")
	@ResponseBody
	public ModelAndView getDevicePushPlaySynchInfo(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map> resultList = deviceService.getDevicePushPlaySynchInfoListBySynchId(params);
		model.addObject("data",resultList);
		model.setViewName("device/devicePushPlaySynchInfo");
		return model;
	}
	/**
	 * 查询设备播放日志
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="getDevicePlayLogList")
	@ResponseBody
	public ModelAndView getDevicePlayLogList(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
		params.put("realPath", realPath);
		List<Map> resultList = deviceService.getDevicePlayLogList(params);
		model.addObject("data",resultList);
		model.setViewName("device/devicePlayLogInfo");
		return model;
	}
	/**
	 * 查询设备列表树形json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="getViproomTreeJson")
	@ResponseBody
	public void getViproomTreeJson(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		List<Map> resultList = new ArrayList<Map>();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			//根据用户过滤设备目录树
			//Map userMap=(Map) request.getSession().getAttribute("user");
			ZkkjUser user= (ZkkjUser) request.getSession().getAttribute("user");
			params.put("airlineId", user.getOrg());
			List<Map> list = deviceService.getVipRoomDataJson(params);
			resultList = list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
		} catch (Exception e) {
			// TODO: handle exception
			resultList = Collections.EMPTY_LIST;
			System.out.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultList).getBytes("UTF-8"));
		}
	}
	/**
	 * 添加或者编辑属性菜单节点初始化
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="addOrEditDeviceTreeInfoInit")
	@ResponseBody
	public ModelAndView addOrEditDeviceTreeInfo(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String type = ParamsUtil.nullDeal(params, "type", "");//tree的node节点的类型
		String id = ParamsUtil.nullDeal(params, "id", "");//主键id
		String operator = ParamsUtil.nullDeal(params, "operator", "");//操作类型，添加或者编辑
		model.addObject("params", params);
		
		if("edit".equals(operator)){
			if("1".equals(type)){
				params.put("airlineId",id);
				//查询航空公司的节点信息
				List<Map> airlineList = deviceService.getAirlineList(params);
				if(airlineList != null && airlineList.size() > 0){
					Map airlineMap = airlineList.get(0);
					model.addObject("data", airlineMap);
				}
			}else if("2".equals(type)){
				params.put("airportId",id);
				//查询机场的节点信息
				List<Map> airportList = deviceService.getAirportList(params);
				if(airportList != null && airportList.size() > 0){
					Map airportMap = airportList.get(0);
					model.addObject("data", airportMap);
				}
			}else if("3".equals(type)){
				params.put("viproomId",id);
				//查询vip厅的节点信息
				List<Map> viproomList = deviceService.getViproomList(params);
				if(viproomList != null && viproomList.size() > 0){
					Map viproomMap = viproomList.get(0);
					model.addObject("data", viproomMap);
				}
			}
		}
		model.setViewName("device/addOrEditDeviceTree");
		return model;
	}
	
	/**
	 * 添加或者编辑设备树
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="addOrEditDeviceTreeInfo")
	@ResponseBody
	public void addOrEditDeviceTreeInfo(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String type = ParamsUtil.nullDeal(params, "type", "");
			String operator = ParamsUtil.nullDeal(params, "operator", "");//操作类型，添加或者编辑
			int count = 0;
			
			if("add".equals(operator)){
				//添加操作
			   if("0".equals(type)){
				   //添加航空公司信息
				   Map airlineParamsMap = new HashMap();
				   airlineParamsMap.put("airlineCode", ParamsUtil.nullDeal(params,"airlineCode", ""));
				   airlineParamsMap.put("airlineName", ParamsUtil.nullDeal(params,"airlineName", ""));
				   count = deviceService.addAirline(airlineParamsMap);
			   }
			   else if("1".equals(type)){
					//添加机场信息
					Map airportParamsMap = new HashMap();
					airportParamsMap.put("alId", ParamsUtil.nullDeal(params,"airlineId", ""));
					airportParamsMap.put("airportCode", ParamsUtil.nullDeal(params,"airportCode", ""));
					airportParamsMap.put("airportName", ParamsUtil.nullDeal(params,"airportName", ""));
					count = deviceService.addAirport(airportParamsMap);
				}else if("2".equals(type)){
					//添加vip厅信息
					Map viproomParamsMap = new HashMap();
					viproomParamsMap.put("apId", ParamsUtil.nullDeal(params, "airportId", ""));
					viproomParamsMap.put("viproomName", ParamsUtil.nullDeal(params, "viproomName", ""));
					count = deviceService.addViproom(viproomParamsMap);
				}else if("3".equals(type)){
					//添加设备
					count = deviceService.addDevice(params);
				}
				
			}else if("edit".equals(operator)){
				//编辑操作
				if("1".equals(type)){
					//编辑航空公司信息
					Map airlineParamsMap = new HashMap();
					airlineParamsMap.put("airlineId", ParamsUtil.nullDeal(params,"airlineId", ""));
					airlineParamsMap.put("airlineCode", ParamsUtil.nullDeal(params,"airlineCode", ""));
					airlineParamsMap.put("airlineName", ParamsUtil.nullDeal(params,"airlineName", ""));
					count = deviceService.editAirline(airlineParamsMap);
				}else if("2".equals(type)){
					//编辑航空公司信息
					Map airportParamsMap = new HashMap();
					airportParamsMap.put("airportId", ParamsUtil.nullDeal(params, "airportId", ""));
					airportParamsMap.put("airportCode", ParamsUtil.nullDeal(params, "airportCode", ""));
					airportParamsMap.put("airportName", ParamsUtil.nullDeal(params, "airportName", ""));
					count = deviceService.editAirport(airportParamsMap);
				}else if("3".equals(type)){
					//编辑vip厅信息
					Map viproomParamsMap = new HashMap();
					viproomParamsMap.put("viproomId", ParamsUtil.nullDeal(params, "viproomId", ""));
					viproomParamsMap.put("viproomName", ParamsUtil.nullDeal(params, "viproomName", ""));
					count = deviceService.editViproom(viproomParamsMap);
				}
			}
			resultMap.put("result", count > 0 ? 1 : 0);
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	@RequestMapping(value="deleteDeviceTreeInfo")
	@ResponseBody
	public void deleteDeviceTreeInfo(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String id = ParamsUtil.nullDeal(params, "id", "");
			String type = ParamsUtil.nullDeal(params, "type", "");
			int count = 0;
			
			if("1".equals(type)){
				//删除航空公司和下面的子节点的信息
				//删除航空公司信息
				Map airlineParamsMap = new HashMap();
				airlineParamsMap.put("airlineId", id);

				//删除设备信息
				deviceService.deleteDevice(airlineParamsMap);
				
				//查询航空公司下面的机场信息
				List<Map> airportList = deviceService.getAirportList(airlineParamsMap);
				if(airportList != null && airportList.size() > 0){
					for(Map airportMap : airportList){
						String airportId  = ParamsUtil.nullDeal(airportMap, "id", "");
						//删除机场下面的vip厅信息
						Map viproomParamsMap = new HashMap();
						viproomParamsMap.put("airportId", airportId);
						deviceService.deleteViproom(viproomParamsMap);
					}
				}
				//删除机场信息
				deviceService.deleteAirport(airlineParamsMap);
				//删除航空公司信息
				deviceService.deleteAirline(airlineParamsMap);
				
			}else if("2".equals(type)){
				//删除机场和下面的子节点信息
				
				Map airportParamsMap = new HashMap();
				airportParamsMap.put("airportId", id);
				
				//删除设备信息
				deviceService.deleteDevice(airportParamsMap);
				//删除vip厅信息
				deviceService.deleteViproom(airportParamsMap);
				//删除机场信息
				deviceService.deleteAirport(airportParamsMap);
				
			}else if("3".equals(type)){
				//删除vip厅和下面的子节点信息
				Map viproomParamsMap = new HashMap();
				viproomParamsMap.put("viproomId", id);
				
				//删除设备信息
				deviceService.deleteDevice(viproomParamsMap);
				//删除vip厅信息
				deviceService.deleteViproom(viproomParamsMap);
			}
			resultMap.put("result", 1);
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	@RequestMapping(value="deviceManageInit")
	public ModelAndView deviceManageInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("device/deviceManage");
		return model;
	}
	
	@RequestMapping(value="sendMessage")
	public void sendMessageToDevice(HttpServletRequest request ,HttpServletResponse response){
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		SocketUtil.pushOverAllMessage(null, SocketConstant.RECEIVE_PROGRAM, new String[]{"节目单名称"});
	}
	
	/**
	 * 航空公司查
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getAirline")
	@ResponseBody
	public void getAirline(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		
		List<Map> airList = null;
		try {
			airList = deviceService.getAirlineList(params);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		} finally{
			AjaxJsonWrite(airList, response);
		}
	}
	
	
	/**
	 * 机场查询
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="getAirport")
	@ResponseBody
	public void getAirport(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Map> airList = null;
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			airList = deviceService.getAirportList(params);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		} finally{
			AjaxJsonWrite(airList, response);
		}
	}
	
	
	/**
	 * vip厅查询
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="getViproom")
	@ResponseBody
	public void getViproom(ModelAndView model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map> viproomList = null;
		try {
			viproomList = deviceService.getViproomList(params);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		} finally{
			AjaxJsonWrite(viproomList, response);
		}
	}
	//根据航空公司编号查询机场
	@SuppressWarnings("rawtypes")
	@RequestMapping("getAirportByAirlineCode")
	@ResponseBody
	public void getAirportByAirlineCode(HttpServletRequest request,HttpServletResponse response)throws Exception{
		List<Map> AirportList = null;
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			String airlineCode = ParamsUtil.nullDeal(map, "airlineCode", "");
			Map airlineListMap =new HashMap();
			airlineListMap.put("airlineCode", airlineCode);
			List<Map> AirlineList=deviceService.getAirlineList(airlineListMap);
			
			Map airportlistMap=new HashMap();
			airportlistMap.put("airlineId",AirlineList.get(0).get("id") );
			
			AirportList= deviceService.getAirportList(airportlistMap);
			System.out.println(AirportList);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(AirportList).getBytes());
		}
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getViproomByAirportCode")
	@ResponseBody
	public void getViproomByAirportCode(HttpServletRequest request,HttpServletResponse response)throws Exception{
		List<Map> byVipIdList = null;
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			//String id = ParamsUtil.nullDeal(map, "id", "");
			String airportCode = ParamsUtil.nullDeal(map, "airportCode", "");
			Map vipMap = new HashMap();
			vipMap.put("airportId", airportCode);
			/*List<Map> vipList=deviceService.getAirportList(vipMap);
			Map vipListMap=new HashMap();
			vipListMap.put("airportId", vipList.get(0).get("id"));*/
			byVipIdList =deviceService.getViproomList(vipMap);
			System.out.println(byVipIdList);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(byVipIdList).getBytes("UTF-8"));
		}
	}
	/**
	 * 设备重启
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="reboot")
	@ResponseBody
	public void reboot(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String deviceIds = ParamsUtil.nullDeal(params, "deviceIds", "");//选中的设备id
			
			String deviceIdArray[] = deviceIds.split(",");
			for(String uuid : deviceIdArray){
				//给设备推送设备重启
				socketService.sendMessageByUuid(uuid,"device_reboot","device_reboot");
				//给设备推送设备重启
				socketService.sendMessageByUuid(uuid+"-power","device_reboot","device_reboot");
			}
			
			try{
				//远程服务器推送
				Map rebootMap = deviceService.reboot(params);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			resultMap.put("result", "1");
			resultMap.put("message", "重启指令已发送成功，具体详情请查看设备监控及页面提醒！");
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 推送节目单播放画面同步的时间
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="pushSynchPlayTime")
	@ResponseBody
	public void pushSynchPlayTime(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String synchId = ParamsUtil.nullDeal(params, "synchId", "");//节目单同步的id
			String synchTime = ParamsUtil.nullDeal(params, "synchTime", "");//定时同步的时间
			String deviceIds = "";
			//根据同步节目单的id查询节目单推送的设备信息
			List<Map> programSynchDeviceInfoList = deviceService.getProgramSynchDeviceInfoList(params);
			if(programSynchDeviceInfoList != null && programSynchDeviceInfoList.size() > 0){
				//获取推送的设备id
				for(Map synchInfoMap : programSynchDeviceInfoList){
					deviceIds += ParamsUtil.nullDeal(synchInfoMap, "uuid", "")+",";
				}
				deviceIds = deviceIds.substring(0,deviceIds.lastIndexOf(","));
				if("".equals(deviceIds)){
					resultMap.put("result", "0");
					resultMap.put("message", "未查询到节目单推送的设备信息");
					return;
				}
				//本地服务器socket推送
				String deviceIdArray[] = deviceIds.split(",");
				for(String uuid : deviceIdArray){
					//给设备推送节目单
					socketService.sendMessageByUuid(uuid,"ad_synch",synchTime);
					//查询之前推送的记录是否存在
					Map selectMap = new HashMap();
					selectMap.put("uuid", uuid);
					Map pushInfoMap = deviceService.getDevicePushPlaySynchInfoByUuid(selectMap);
					if(pushInfoMap == null){
						//没有推送记录，保存推送记录
						Map insertMap = new HashMap();
						insertMap.put("uuid", uuid);
						insertMap.put("synchId", synchId);
						insertMap.put("synchTime", synchTime);
						deviceService.addDevicePushPlaySynch(insertMap);
					}else{
						//有推送记录，更新为最新的
						String id = ParamsUtil.nullDeal(pushInfoMap, "id", "");//推送记录的主键
						Map updateMap = new HashMap();
						updateMap.put("id", id);
						updateMap.put("synchTime", synchTime);
						updateMap.put("receiveStatus","0");
						deviceService.updateDevicePushPlaySynchInfo(updateMap);
					}
				}
			}
			
			try{
				//远程服务器推送
				Map pushResultMap = deviceService.pushSynchPlayTime(params);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			resultMap.put("result", "1");
			resultMap.put("message", "同步指令已发送成功，具体详情请查看设备监控及页面提醒！");
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
}
