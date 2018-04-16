package com.zkkj.backend.web.controller.biz.resource;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.exception.DataBaseException;
import com.zkkj.backend.common.exception.FileException;
import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.util.UrlConvertor;
import com.zkkj.program.service.IResourceService;
import com.zkkj.program.service.impl.ResourceServiceImpl;
import com.zkkj.program.util.Page;

@Controller
@RequestMapping(value="resource")
public class ResourceController {
	private IResourceService service = new ResourceServiceImpl();
	
	/**
	 * 资源管理初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("resourceManageInit")
	public ModelAndView resourceManageInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String resourceServiceType  = ParamsUtil.nullDeal(params, "resourceServiceType", "");
		String resourceServiceId  = ParamsUtil.nullDeal(params, "resourceServiceId", "");
		
		String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");//图片访问地址
		params.put("realPath", realPath);
		List<Map> resultList = service.getResource(params);
		model.addObject("data", resultList != null && resultList.size() > 0 ? GsonUtil.getGson().toJson(resultList) : Collections.EMPTY_LIST);
		model.addObject("resourceServiceType",resourceServiceType);
		model.addObject("resourceServiceId",resourceServiceId); 
		switch (resourceServiceType){
		case "1":
			model.setViewName("resource/advertiserResourceManage");;
		default:
			break;
		}
		return model;
	}
	
	/**
	 * 添加资源
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws FileException 
	 */
	@RequestMapping("addResource")
	@ResponseBody
	public void addResource(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, FileException{
		Map resultMap = new HashMap();
		int count = 0;
		List<Map> list = new ArrayList<Map>();
		String resourceServiceType = request.getParameter("resourceServiceType");
		String resourceServiceId = request.getParameter("resourceServiceId");
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        //判断 request 是否有文件上传,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
	            //取得request中的所有文件名  	
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //记录上传过程起始时的时间，用来计算上传时间  
	                int pre = (int) System.currentTimeMillis();  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());
	                if(file != null){ 
	                    String fileOldName = file.getOriginalFilename();
	                    if(fileOldName.trim() !=""){
	                        try {
								Random random = new Random();
	                        	String suffix = fileOldName.substring(fileOldName.lastIndexOf("."));
	                        	String resourceType = "1";
	                        	if(".mp4".equals(suffix)){
	                        		resourceType = "2";
	                        	}else if(".apk".equals(suffix)){
	                        		resourceType = "3";
	                        	}
								String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
								resultMap.put("fileOldName", fileOldName);
								resultMap.put("fileUploadName", fileUploadName);
								
								//获取文件上传根目录
								String basePath = (String)CustomizedPropertyConfigurer.getContextProperty("base_upload_path");
								
								String uploadPath = "";
								if("3".equals(resourceType)){
									fileUploadName = fileOldName;
									uploadPath = (String)CustomizedPropertyConfigurer.getContextProperty("apk_upload_path");
								}else{
									uploadPath = (String)CustomizedPropertyConfigurer.getContextProperty("advert_upload_path");
								}
								//获取广告上传目录
								//文件上传的完整路径
								String savePath = basePath + uploadPath +fileUploadName;
								System.out.println("文件保存路径" + savePath);
								File saveDir = new File(savePath);
								if (!saveDir.getParentFile().exists())
					                    saveDir.getParentFile().mkdirs();
				                file.transferTo(saveDir);
								//返回文件上传后的访问地址
								resultMap.put("fileUrl",uploadPath +fileUploadName);
								
								//保存文件信息
								Map params = new HashMap();
								params.put("resourceType", resourceType);
								params.put("resourceServiceType", resourceServiceType);
								params.put("resourceServiceId", resourceServiceId);
								params.put("fileOldName", fileOldName);
								params.put("fileName", fileUploadName);
								params.put("filePath", uploadPath +fileUploadName);
								params.put("fileSize", file.getSize());
								params.put("suffix", suffix);
								if(service.addResource(params) == 1){
									list.add(params);
									count ++;
								}
								System.out.println(DateUtil.getStringDate()+"---文件上传成功******************************" + fileOldName);
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println(e.getMessage());
								//发生异常记录到异常表
								FileException fileException=new FileException();
								fileException.setExceptionCode("SE_FILE_ERROR");
								fileException.setStackTrace(e.getStackTrace());
								throw fileException;
							}
	                    }  
	                }  
	                //记录上传该文件后的时间  
	                int finaltime = (int) System.currentTimeMillis();  
	                System.out.println(finaltime - pre);  
	            }  
	           }
	        }catch (Exception e) {
	        	e.printStackTrace();
				resultMap.put("result",0);
				resultMap.put("message", e.getMessage());
				//发生异常记录到异常表
				FileException fileException=new FileException();
				fileException.setExceptionCode("SE_FILE_ERROR");
				fileException.setStackTrace(e.getStackTrace());
				throw fileException;
			}finally{
				resultMap.put("count",count);
				resultMap.put("data", list);
				response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
			}
	}
	
	/**
	 * 删除广告
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("deleteResource")
	@ResponseBody
	public void deleteResource(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
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
				int count = service.deleteResource(list);
				resultMap.put("result",count);
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
	 * 查询资源
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("getResource")
	@ResponseBody
	public void getResource(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			/*int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "pageNo", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "pageSize", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);*/
			String realPath = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");//图片访问地址
			params.put("realPath", realPath);
			List<Map> resultList = service.getResource(params);
			resultMap.put("data", resultList != null && resultList.size() > 0 ? GsonUtil.getGson().toJson(resultList) : Collections.EMPTY_LIST);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("data", Collections.EMPTY_LIST);
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
	 * 根据业务id和业务类型删除资源
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("deleteAllResource")
	@ResponseBody
	public void deleteAllResource(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String resourceType  = ParamsUtil.nullDeal(params, "resourceType", "");
			String resourceServiceId  = ParamsUtil.nullDeal(params, "resourceServiceId", "");
			if("".equals(resourceType)){
				resultMap.put("result", "0");
				resultMap.put("message", "资源类型不能为空");
				return;
			}
			if("".equals(resourceServiceId)){
				resultMap.put("result", "0");
				resultMap.put("message", "业务id不能为空");
				return;
			}
			int count = service.deleteAllResource(params);
			resultMap.put("result", count);
		} catch (Exception e) {
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常，异常信息为："+e.getMessage());
			// TODO: handle exception
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
}
