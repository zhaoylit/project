package com.zj.web.modules.system.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.zj.web.common.utils.CustomizedPropertyConfigurer;
import com.zj.web.common.utils.JsonUtil;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.ReflectUtil;
import com.zj.web.modules.system.service.IResourceService;

@RequestMapping("resource")
@Controller
public class ResourceController {
	@Autowired
	private IResourceService resourceService;
	
	private static final Logger log = Logger.getLogger(ResourceController.class);
	
	/**
	 * 图标管理初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("iconListInit")
	public ModelAndView iconListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		model.addObject("param",params);
		model.setViewName("/resource/icon/iconList");
		return model;
	} 	
	
	/**
	 * 图标列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("iconList")
	public void iconList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		try {
			//查询所有的菜单
			List<Map> iconList = resourceService.getIconList(params);
			resultMap.put("count", resourceService.getIconListCount(params));
			resultMap.put("rows", iconList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getMenuTable********************************"+e.getMessage());
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}
	} 
	/**
	 * 添加或者编辑图标初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("addOrEditIconInit")
	public ModelAndView addOrEditIconInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Integer id = Integer.parseInt(ParamsUtil.nullDeal(params, "id", "0"));
		if(id != 0){
			Map nodeMap = resourceService.selectIconById(id);
			model.addObject("resultInfo",nodeMap);
		}
		model.addObject("param",params);
		model.setViewName("/resource/icon/iconEdit");
		return model;
	} 	
	/**
	 * 添加或者修改图标
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("addOrEditIcon")
	public String addOrEditIcon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			//查询所有的功能
			json = resourceService.addOrEditIcon(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("addOrEditFun********************************"+e.getMessage());
			json = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return json;
		}
	} 	
	/**
	 * 删除图标
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("deleteIcon")
	public String deleteIcon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			//查询所有的功能
			json = resourceService.deleteIcon(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("addOrEditFun********************************"+e.getMessage());
			json = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return json;
		}
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("fileUpload")
	@ResponseBody
	public Map FileUpload(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map resultMap = new HashMap();
		String  serviceId  = request.getParameter("serviceId");
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
	                        	if(!".jpg|.jpeg|.gif|.bmp|.png|".contains(suffix)){
	                        		resultMap.put("result","0");
	                				resultMap.put("message", "请上传.jpg|.jpeg|.gif|.bmp|.png|格式的图标文件");
	                				return resultMap;
	                        	}
								String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
//								String path = request.getSession().getServletContext().getRealPath("/");
								
								String resourceType = request.getParameter("resourceType");
//								String resourcePath = resourceService.selectResourcePathByType(resourceType);
								String resourcePath = "";
								switch (resourceType) {
								case "1":
									{
										//icon
										resourcePath = (String) CustomizedPropertyConfigurer.getContextProperty("icon_path");
									}
									break;
								case "2":
									{
										//轮播图
										resourcePath = (String) CustomizedPropertyConfigurer.getContextProperty("lunbotu_path");
									}
									break;
								case "3":
									{
										//商品服务图标
										resourcePath = (String) CustomizedPropertyConfigurer.getContextProperty("goods_services_icon");
									}
									break;
								default:
									break;
								}
								//数据保存根目录
								String basePath = (String)CustomizedPropertyConfigurer.getContextProperty("data_path");
								
								String savePath = basePath + resourcePath + fileUploadName;
								
								File saveDir = new File(savePath);
				                if (!saveDir.getParentFile().exists())
				                    saveDir.getParentFile().mkdirs();
				                file.transferTo(saveDir);
								//返回文件上传后的访问地址	
								resultMap.put("filePath",resourcePath + fileUploadName);
								resultMap.put("fileOldName", fileOldName);
								resultMap.put("fileUploadName", fileUploadName);
								System.out.println("filePath---------------"+savePath);
							} catch (Exception e) {
								// TODO Auto-generated catch block
							 e.printStackTrace();
							}
	                    }  
	                }  
	                //记录上传该文件后的时间  
	                int finaltime = (int) System.currentTimeMillis();  
	                System.out.println("upload time***********************************"+(finaltime - pre));  
	            }  
	           }
	        }catch (Exception e) {
	        	e.printStackTrace();
				resultMap.put("result","0");
				resultMap.put("message", e.getMessage());
			}finally{
				return resultMap;
			}
		
	}
}
