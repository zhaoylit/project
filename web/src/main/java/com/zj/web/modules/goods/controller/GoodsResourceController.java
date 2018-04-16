package com.zj.web.modules.goods.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.CustomizedPropertyConfigurer;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.ReflectUtil;
import com.zj.web.modules.goods.service.IGoodsResourceService;

@Controller
@RequestMapping(value="goodsResource")
public class GoodsResourceController {
	private static final Logger log = LoggerFactory.getLogger(GoodsResourceController.class);
	@Autowired
	private IGoodsResourceService goodsResourceService;
	@RequestMapping(value="selectListInit")
	public ModelAndView selectListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("goods/goodsList");
		return model;
	}
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="selectList")
	public void selectList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			Integer pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			Integer pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setNumPerPage(pageSize);
			page.setPageNum(pageNo);
			List<Map> selectList = goodsResourceService.selectList(params, page);
			resultMap.put("rows", selectList);
			resultMap.put("total", goodsResourceService.selectCount(params));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("msg",e.getMessage());
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes(Charset.forName("UTF-8")));
		}
	}
	@RequestMapping("selectListJson")
	public ModelAndView selectListJson(ModelAndView model,HttpServletRequest  request,HttpServletResponse response ) {
		 Map params = ReflectUtil.transToMAP(request.getParameterMap());
		 String id = ParamsUtil.nullDeal(params, "id", "");
		 if(!"".equals(id)){
			 //查询商品的资源
			 try {
				List<Map> resourceList = goodsResourceService.selectList(params);
				 model.addObject("data",JSONArray.fromObject(resourceList).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("getGoodsResource-------------------------------" + e.getMessage()); 
			}
		 }
		 model.addObject("params",params);
		 model.setViewName("goods/resourceManage");
		return model;
	}
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("insert")
	@ResponseBody
	public Map FileUpload(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map resultMap = new HashMap();
		String  serviceId  = request.getParameter("serviceId");
		int order = 1;
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
	                    Long fileSize = file.getSize();
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
								String resourceType = request.getParameter("resourceType");
								String resourcePath = (String) CustomizedPropertyConfigurer.getContextProperty("goods_resource") + serviceId + "/";
								//数据保存根目录
								String basePath = (String)CustomizedPropertyConfigurer.getContextProperty("data_path");
								
								String savePath = basePath + resourcePath + fileUploadName;
								
								File saveDir = new File(savePath);
				                if (!saveDir.getParentFile().exists())
				                    saveDir.getParentFile().mkdirs();
				                file.transferTo(saveDir);
				                //保存信息到数据库
				                Map insertMap = new HashMap();
				                insertMap.put("goodsId", serviceId);
				                insertMap.put("imageUrl", resourcePath + fileUploadName);
				                insertMap.put("order", order ++);
				                insertMap.put("fileSize", fileSize);
				                insertMap.put("fileOldName", fileOldName);
				                goodsResourceService.insert(insertMap);
							} catch (Exception e) {
								// TODO Auto-generated catch block
							 e.printStackTrace();
							}
	                    }  
	                }  
	            }  
	            resultMap.put("result","1");
	           }	        }catch (Exception e) {
	        	e.printStackTrace();
				resultMap.put("result","0");
				resultMap.put("message", e.getMessage());
			}finally{
				return resultMap;
			}
		
	}
	@RequestMapping("deleteByPrimaryKey")
	public void deleteByPrimaryKey(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int rowCount = goodsResourceService.deleteByPrimaryKey(params);
			if(rowCount == 0){
				resultMap.put("result", "0");
				resultMap.put("message","操作失败");
				return;
			}
			resultMap.put("result", "1");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("--------------------------------" + e.getMessage());
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}	}
	
	@RequestMapping("resourceManageInit")
	public ModelAndView resourceManageInit(ModelAndView model ,HttpServletRequest request,HttpServletResponse response){
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			model.addObject("params", params);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("------------------------------" + e.getMessage());
		}
		model.setViewName("goods/resourceManageInit");
		return model;
	}
}
