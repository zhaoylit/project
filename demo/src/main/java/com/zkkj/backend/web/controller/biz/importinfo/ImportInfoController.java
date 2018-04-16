package com.zkkj.backend.web.controller.biz.importinfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.zkkj.backend.common.exception.DataBaseException;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.util.ResultHandle;
import com.zkkj.backend.service.biz.info.ImportInfoService;
import com.zkkj.backend.web.controller.BaseController;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.program.util.Page;
/**
 * 导入信息控制层
 * @author 
 */
@Controller
@RequestMapping("info")
public class ImportInfoController extends BaseController {
	private IDeviceService deviceService=new DeviceServiceImpl();
	
	private static Logger log = LoggerFactory.getLogger(ImportInfoController.class);
	@Autowired
	private ImportInfoService flightInfoService;
	
	/**
	 * 查询航空公司列表json
	 * @throws DataBaseException 
	 */
	@RequestMapping("AirlineList")
	@ResponseBody
	public void userList(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = deviceService.getAirlineList(null);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", "0");
		} catch (Exception e) {
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			System.out.println(e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();	
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	
	/**
	 * 查询机场列表json
	 * @throws DataBaseException 
	 */
	@RequestMapping("AirportList")
	@ResponseBody
	public void AirportList(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = deviceService.getAirportList(null);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", "0");
		} catch (Exception e) {
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			System.out.println(e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();	
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	
	/**
	 * 查询vip列表json
	 * @throws DataBaseException 
	 */
	@RequestMapping("ViproomList")
	@ResponseBody
	public void ViproomList(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = deviceService.getViproomList(null);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", "0");
		} catch (Exception e) {
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			System.out.println(e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();	
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	
	@SuppressWarnings("finally")
	@RequestMapping(value="uploadflight",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> flightImport(@RequestParam(value="airportFile")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Random random = new Random();
			String airportFile = file.getOriginalFilename();
			String suffix = airportFile.substring(airportFile.lastIndexOf("."));
			String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
		/*	String path = request.getSession().getServletContext().getRealPath("/");
			String savePath = "/home/zkkj/upload/"+fileUploadName;
			String pathStr = path + savePath;*/
			 
			String path = (String)CustomizedPropertyConfigurer.getContextProperty("base_upload_path");//图片访问地址
			String savePath = fileUploadName;
			String pathStr=path+","+fileUploadName;

			boolean flag = flightInfoService.upLoadflight(airportFile, pathStr, file);
			if (flag) {
				String Msg = "批量导入Excel成功";
				System.err.println(Msg);
				request.getSession().setAttribute("msg", Msg);
				resultMap.put("result", "1");
				resultMap.put("message", Msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String Msg = "批量导入Excel失败";
			request.getSession().setAttribute("msg", Msg);
			resultMap.put("result", "0");
			resultMap.put("message", Msg);
		} finally {
			return resultMap;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value="uploadairline",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> airlineImport(@RequestParam(value="airportFile")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Random random = new Random();
			String airportFile = file.getOriginalFilename();
			String suffix = airportFile.substring(airportFile.lastIndexOf("."));
			String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
			/*String path = request.getSession().getServletContext().getRealPath("/");
			String savePath = "/home/zkkj/upload/"+fileUploadName;
			String pathStr = path + savePath;*/
			String path = (String)CustomizedPropertyConfigurer.getContextProperty("base_upload_path");//图片访问地址
			String savePath = fileUploadName;
			String pathStr=path+","+fileUploadName;

			boolean flag = flightInfoService.upLoadAirlinePlan(airportFile, pathStr, file);
			if (flag) {
				String Msg = "批量导入Excel成功";
				System.err.println(Msg);
				request.getSession().setAttribute("msg", Msg);
				resultMap.put("result", "1");
				resultMap.put("message", Msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String Msg = "批量导入Excel失败";
			request.getSession().setAttribute("msg", Msg);
			resultMap.put("result", "0");
			resultMap.put("message", Msg);
		} finally {
			return resultMap;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value="uploadairport",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> airportImport(@RequestParam(value="airportFile")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Random random = new Random();
			String airportFile = file.getOriginalFilename();
			String suffix = airportFile.substring(airportFile.lastIndexOf("."));
			String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
			/*String path = request.getSession().getServletContext().getRealPath("/");
			String savePath = "/home/zkkj/upload/"+fileUploadName;
			String pathStr = path + savePath;*/
			String path = (String)CustomizedPropertyConfigurer.getContextProperty("base_upload_path");//图片访问地址
			String savePath = fileUploadName;
			String pathStr=path+","+fileUploadName;
			boolean flag = flightInfoService.upLoadAirport(airportFile, pathStr, file);
			if (flag) {
				String Msg = "导入Excel成功";
				System.err.println(Msg);
				request.getSession().setAttribute("msg", Msg);
				resultMap.put("result", "1");
				resultMap.put("message", Msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String Msg = "导入Excel失败";
			request.getSession().setAttribute("msg", Msg);
			resultMap.put("result", "0");
			resultMap.put("message", Msg);
		} finally {
			return resultMap;
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="getFlightInfo",method=RequestMethod.POST)
	@ResponseBody
	public void getFlightInfoList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
	   List<Map> flightList = null;
		try {
			flightList=deviceService.getFlightList(resultMap);
			/* flightList = flightInfoService.getFlightInfoList(map);
			 resultMap = ResultHandle.getList(flightList);*/
//			 resultMap.put("result", "1");
//			 resultMap.put("rows", flightList);
//			 resultMap.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(flightList).getBytes());
		}	    
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="getAirportInfo",method=RequestMethod.POST)
	@ResponseBody
	public void getAirportInfoList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
	   List<Map<String, String>> airportList = null;
		try {
		/*	airportList = flightInfoService.getAirportInfoList(map);
			 resultMap = ResultHandle.getList(airportList);*/
//			 resultMap.put("result", "1");
//			 resultMap.put("rows", flightList);
//			 resultMap.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes());
		}	    
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="getAirlineInfo",method=RequestMethod.POST)
	@ResponseBody
	public void getAirlineInfoList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
	   List<Map<String, String>> airlineList = null;
		try {
			/* airlineList = flightInfoService.getAirlineInfoList(map);
			 resultMap = ResultHandle.getList(airlineList);*/
//			 resultMap.put("result", "1");
//			 resultMap.put("rows", flightList);
//			 resultMap.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes());
		}	    
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="getGateInfo",method=RequestMethod.POST)
	@ResponseBody
	public void getGateInfoList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map<String, String>> gateList = null;
		try {
			/*gateList = flightInfoService.getGateInfoList(map);
			resultMap = ResultHandle.getList(gateList);*/
//			resultMap.put("result", "1");
//			resultMap.put("rows", gateList);
//			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes());
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="getVipInfo",method=RequestMethod.POST)
	@ResponseBody
	public void getVipInfoList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map<String, String>> vipList = null;
		try {
		/*	vipList = flightInfoService.getVipInfoList(map);
			resultMap = ResultHandle.getList(vipList);*/
//			resultMap.put("result", "1");
//			resultMap.put("rows", vipList);
//			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="getViproomInfo",method=RequestMethod.POST)
	@ResponseBody
	public void getViproomInfoList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map map = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map<String, String>> vipList = null;
		try {
			/*vipList = flightInfoService.getViproomInfoList(map);
			resultMap = ResultHandle.getList(vipList);*/
//			resultMap.put("result", "1");
//			resultMap.put("rows", vipList);
//			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes());
		}
	}
	
	/**
	 * 导入vip旅客信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="uploadVip",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> vipImport(@RequestParam(value="airportFile")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Random random = new Random();
			String airportFile = file.getOriginalFilename();
			String suffix = airportFile.substring(airportFile.lastIndexOf("."));
			String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
			String path = request.getSession().getServletContext().getRealPath("/");
			String savePath = "/home/zkkj/upload/"+fileUploadName;
			String pathStr = path + savePath;
		/*	boolean flag = flightInfoService.upLoadVipTraveller(airportFile, pathStr, file);
			if (flag) {
				String Msg = "批量导入Excel成功";
				System.err.println(Msg);
				request.getSession().setAttribute("msg", Msg);
				resultMap.put("result", "1");
				resultMap.put("message", Msg);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			String Msg = "批量导入Excel失败";
			request.getSession().setAttribute("msg", Msg);
			resultMap.put("result", "0");
			resultMap.put("message", Msg);
		
		} finally {
			return resultMap;
		}
	  
	}
	
	
	/**
	 * 导入vip厅信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="uploadViproom",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> viproomImport(@RequestParam(value="airportFile")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Random random = new Random();
			String airportFile = file.getOriginalFilename();
			String suffix = airportFile.substring(airportFile.lastIndexOf("."));
			String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
			/*String path = request.getSession().getServletContext().getRealPath("/");
			String savePath = "/home/zkkj/upload/"+fileUploadName;
			String pathStr = path + savePath;*/
			String path = (String)CustomizedPropertyConfigurer.getContextProperty("base_upload_path");//图片访问地址
			String savePath = fileUploadName;
			String pathStr=path+","+fileUploadName;
			boolean flag = flightInfoService.upLoadViproomPlan(airportFile, pathStr, file);
			if (flag) {
				String Msg = "批量导入Excel成功";
				System.err.println(Msg);
				request.getSession().setAttribute("msg", Msg);
				resultMap.put("result", "1");
				resultMap.put("message", Msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String Msg = "批量导入Excel失败";
			request.getSession().setAttribute("msg", Msg);
			resultMap.put("result", "0");
			resultMap.put("message", Msg);
		
		} finally {
			return resultMap;
		}
	  
	}
	/**
	 * 导入登机口信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="uploadgate",method=RequestMethod.POST)
	@ResponseBody
	public void gateImport(@RequestParam(value="airportFile")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Random random = new Random();
			String airportFile = file.getOriginalFilename();
			String suffix = airportFile.substring(airportFile.lastIndexOf("."));
			String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis() + suffix;
			String path = request.getSession().getServletContext().getRealPath("/");
			String savePath = "/home/zkkj/upload/"+fileUploadName;
			String pathStr = path + savePath;
			/*boolean flag = flightInfoService.upLoadBoardingGate(airportFile, pathStr, file);
			if (flag) {
				String Msg = "批量导入Excel成功";
				System.err.println(Msg);
				request.getSession().setAttribute("msg", Msg);
				resultMap.put("result", "1");
				resultMap.put("message", Msg);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			String Msg = "批量导入Excel失败";
			request.getSession().setAttribute("msg", Msg);
			resultMap.put("result", "0");
			resultMap.put("message", Msg);
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes());
		}
	}
	
}
