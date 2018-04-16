package com.zkkj.backend.web.controller.biz.exceptionInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.util.StringUtils;
import com.zkkj.backend.service.biz.device.IDeviceMtnService;
import com.zkkj.backend.service.biz.exceptionInfo.ExceptionInfoService;
import com.zkkj.backend.service.biz.exceptionInfo.ExceptionInfoServiceImpl;
import com.zkkj.exceptionInfo.service.IExceptionRecordService;
import com.zkkj.exceptionInfo.service.IExceptionTypeService;
import com.zkkj.exceptionInfo.service.impl.ExceptionRecordServiceImpl;
import com.zkkj.exceptionInfo.service.impl.ExceptionTypeServiceImpl;
import com.zkkj.exceptionInfo.util.Page;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;

@Controller
@RequestMapping("exception")
public class ExceptionRecordController {
	private ExceptionInfoService exceptionInfoService=new ExceptionInfoServiceImpl();
	
	private IExceptionRecordService service = new ExceptionRecordServiceImpl();
	private IDeviceService deviceService= new DeviceServiceImpl();
	private IExceptionTypeService exceptionTypeService = new ExceptionTypeServiceImpl();
//	private IExceptionLevelService levelService=new ExceptionLevelServiceImpl();
	
	@RequestMapping("exceptionRecordInit")
	public ModelAndView exceptionRecordInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("exception/exceptionRecordList");
		return model;
	}
	/**
	 * 查询设备异常记录json 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("exceptionRecordList")
	@ResponseBody
	public void exceptionRecordList(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = service.getExceptionRecordList(params, page);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", service.getExceptionRecordListCount(params));
		} catch (Exception e) {
			// TODO: handle exception	
			resultMap.put("result", "0");
			resultMap.put("message",e.getMessage());
			System.out.println(e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	
	
	/**
	 * 修改异常状态为已解决
	 */
	@RequestMapping("updateStateSolve")
	@ResponseBody
	public void updateStateSolve(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		int countRecord = 0;
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		Map resultMap = new HashMap();
		try {
			if(!"".equals(ids)){
				List<String> list = new ArrayList<String>();
				String []array = ids.split(",");
				for(String ss : array){
					Map idOneMap=new HashMap();
					idOneMap.put("exceptionState","2");
					idOneMap.put("id", ss);
					service.addOrEditExceptionRecord(idOneMap);
					countRecord++;
				}
				resultMap.put("result",countRecord > 0 ? 1 : 0);
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
	 * 删除异常信息
	 */
	@RequestMapping("deleteexceptionRecord")
	@ResponseBody
	public void deleteexceptionRecord(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		Map resultMap = new HashMap();
		try {
			if(!"".equals(ids)){
				List<String> list = new ArrayList<String>();
				String []array = ids.split(",");
				for(String ss : array){
					list.add(ss);
				}
				int count = service.deleteExceptionRecord(list);
				resultMap.put("result",count > 0 ? 1 : 0);
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
	 * 设备异常回传保存到数据库 接口
	 * @param request
	 * @param response
	 * @throws exceptionRecord
	 * @throws IOException
	 */
	
	@RequestMapping("exceptionRecord")
	@ResponseBody
	public void ExceptionRecord(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
        try{
        	//添加
        	int count = exceptionInfoService.addExceptionInfo(params);
			resultMap.put("result", count > 0 ? 1 : 0);	
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	
	/*@RequestMapping("exceptionRecord")
	@ResponseBody
	public void ExceptionRecord(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params=request.getParameterMap();
		Map resultMap=new HashMap();
		int count=0;
		String uuid = ParamsUtil.nullDeal(params, "uuid", "");
    	Map deviceParameterMap=new HashMap();
    	deviceParameterMap.put("uuid", uuid);
    	//根据传递设备ID查询设备信息
    	Map deviceMap;
		try {
			deviceMap = deviceService.getDeviceOne(deviceParameterMap);
	    	Map exceptionRecordMap=new HashMap();
	    	exceptionRecordMap.put("exceptionCode", params.get("exceptionCode"));
	    	exceptionRecordMap.put("message", params.get("message"));
	    	exceptionRecordMap.put("module", params.get("module"));
	    	exceptionRecordMap.put("airlineCode",deviceMap.get("airlineCode"));
	    	exceptionRecordMap.put("airlineName",deviceMap.get("airlineName"));
	    	exceptionRecordMap.put("airportCode",deviceMap.get("airportCode"));
	    	exceptionRecordMap.put("airportName",deviceMap.get("airportName"));
	    	exceptionRecordMap.put("viproomId",deviceMap.get("viproomId"));
	    	exceptionRecordMap.put("viproom",deviceMap.get("viproomName"));
	    	exceptionRecordMap.put("devIp",deviceMap.get("deviceIp"));
	    	exceptionRecordMap.put("deviceId",deviceMap.get("uuid"));
	    	//添加
			count = service.addOrEditExceptionRecord(exceptionRecordMap);
			resultMap.put("result", count > 0 ? 1 : 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}*/
	
	
	/**
	 * 查看或者修改设备记录初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("editExceptionRecordInit")
	public ModelAndView editExceptionRecordInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		if(!"".equals(id)){
			//查询单条设备记录商的数据
			Map map=service.getExceptionRecordById(params);
			String exceptionCode = ParamsUtil.nullDeal(map, "exceptionCode", "");
			Map exceptionCodeMap =new HashMap();
			exceptionCodeMap.put("exceptionCode", exceptionCode);
			
			Map exceptionTypeMap=exceptionTypeService.getExceptionInfoById(exceptionCodeMap);
			
			map.put("info", exceptionTypeMap.get("info"));
			map.put("exceptionType", exceptionTypeMap.get("exceptionType"));
			map.put("grade", exceptionTypeMap.get("grade"));
			map.put("scope", exceptionTypeMap.get("scope"));
			map.put("remark", exceptionTypeMap.get("remark"));
			
			
            model.addObject("resultInfo",map);
            
            
		}
		model.setViewName("exception/exceptionRecordEdit");
		return model;
	}
	
	
	/**
	 * 添加或编辑设备记录
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("editExceptionRecord")
	@ResponseBody
	public void editExceptionRecord(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
        try{
			int count = service.addOrEditExceptionRecord(params);
			resultMap.put("result", count > 0 ? 1 : 0);
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
	 * 异常类型初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exceptionTypeInit")
	public ModelAndView exceptionTypeInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("exception/exceptionTypeList");
		return model;
	}
	
	
	/**
	 * 查询设备异常类型json 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("exceptionTypeList")
	@ResponseBody
	public void exceptionTypeList(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "pageNo", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "pageSize", "100"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = exceptionTypeService.getExceptionInfoList(params, page);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", service.getExceptionRecordListCount(params));
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message",e.getMessage());
			System.out.println(e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	
	/**
	 * 添加或者修改异常类型初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("addOrEditexceptionTypeInit")
	public ModelAndView addOrEditexceptionTypeInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> params = ReflectUtil.transToMAP(request.getParameterMap());
		String  id = ParamsUtil.nullDeal(params, "id", "");
		if(id != null && !id.equals("")){
			Map editExceptionMap = new HashMap();
			editExceptionMap.put("id", id);
			Map resultMap = exceptionTypeService.getExceptionInfoById(editExceptionMap);
			model.addObject("resultInfo",resultMap);
		}
		model.setViewName("exception/exceptionTypeEdit");
		return model;
	}
	
	/**
	 * 添加或编辑异常类型
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("addOrEditExceptionType")
	@ResponseBody
	public void addOrEditExceptionType(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
        try{
			int count = exceptionTypeService.addOrEditExceptionInfo(params);
			resultMap.put("result", count > 0 ? 1 : 0);
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
	 * 删除异常信息
	 */
	@RequestMapping("deleteExceptionType")
	@ResponseBody
	public void deleteExceptionType(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		Map resultMap = new HashMap();
		try {
			if(!"".equals(ids)){
				List<String> list = new ArrayList<String>();
				String []array = ids.split(",");
				for(String ss : array){
					list.add(ss);
				}
				int count = exceptionTypeService.deleteExceptionInfo(list);
				resultMap.put("result",count > 0 ? 1 : 0);
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
	
	//异常等级设置页面初始化
	@RequestMapping("exceptionLevelInit")
	public ModelAndView exceptionLevelInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		try {
//			List<Map> exceptionRecordList = levelService.getExceptionRecordList(null);
//			model.addObject("result", exceptionRecordList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.setViewName("exception/exceptionLevelEdit");
		return model;
	}
	
	/**
	 *编辑异常等级
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("exceptionLevel")
	@ResponseBody
	public void exceptionLevel(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		//Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String[] ids=request.getParameterValues("id");
		String[] solutions=request.getParameterValues("solution");
		String[] emails=request.getParameterValues("email");
		String[] sendTimes=request.getParameterValues("sendTime");
		int count=0;
		int countError=0;
		Map resultMap = new HashMap();
        try{
        	Map levelMap=new HashMap();
        	for (int i = 0; i < 4; i++) {
				levelMap.put("id", ids[i]);
				levelMap.put("solution", solutions[i]);
				levelMap.put("email", emails[i].equals("") ? "" : emails[i] );
				levelMap.put("sendTime", sendTimes[i].equals("0")  ||  sendTimes[i].equals("") ? "0" : sendTimes[i]);
				if (solutions[i].equals("2")) {
					if (emails[i].equals("")) {
						countError++;
						break;
					}
				}
//				count=levelService.updateExceptionLevel(levelMap);
			}
        	resultMap.put("result",count > 0 ? 1 : 0);
        	
        	if(countError>0){
        		resultMap.put("result","2");
        	}
        	
			//int count = levelService.updateExceptionLevel(params);
			//resultMap.put("result", count > 0 ? 1 : 0);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

}
