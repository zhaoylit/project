package com.zkkj.backend.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zkkj.backend.common.constant.ResourceType;

/**
 * 文件上传工具类
 * @author 93908
 *
 */
@Controller
@RequestMapping("fileInput")
public class PhotoManange {

/*	@Autowired
	JdbcTemplate baseDao;*/
	
	@RequestMapping("/photoManage")
	@ResponseBody
	public String photoManage(HttpServletRequest request,HttpServletResponse response){
		Map params=ReflectUtil.transToMAP(request.getParameterMap());
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		PrintWriter out = null;
		if("1".equals(String.valueOf(params.get("opType")))){
			try {
				out = response.getWriter();
				String rType = String.valueOf(params.get("rType"));
				List<Map<String, Object>> resultList;			
				resultList = null;//baseDao.queryForList("SELECT ri.resourceId,ri.resourcePath,ri.name,ri.originalName,ri.defaultMiddle,ri.resourceType,ri.resourceSeq FROM sys_resource_info ri WHERE ri.resourceType = '"+params.get("rType")+"' and businessType='"+params.get("rSType")+"' and businessId="+params.get("keyPrimary")+" order by ri.resourceSeq asc");
				resultMap.put("photoInfo", resultList);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultMap.put("status", "false");
				resultMap.put("message", "系统错误");
			}finally{
				out.print(JSONObject.fromObject(resultMap));
			}
		}else if("2".equals(String.valueOf(params.get("opType")))){
			try {
				out = response.getWriter();
				//上传照片
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
		                        	String suffix = fileOldName.substring(fileOldName.lastIndexOf("."));
									String resourceType,businessType = "";
									businessType = StringUtils.isNotBlank(params.get("rSType")+"") ? String.valueOf(params.get("rSType")) : "";
									resourceType = StringUtils.isNotBlank(params.get("rType")+"") ? String.valueOf(params.get("rType")) : "";
									
									if("601".equals(String.valueOf(params.get("rSType")))){
										resourceType = "3";
									}
									Random random = new Random();
									String fileUploadName = random.nextInt(10000)+ System.currentTimeMillis()+suffix;
									String fileUploadPath = UrlConvertor.getPathByResourceType(businessType)+fileUploadName;
									String filePath = UrlConvertor.wuLiPath()+fileUploadPath;
									OSSUtil.uploadImgInputStream(fileUploadPath, file.getInputStream());
									String context = OSSUtil.getOSSFileContextPath(ResourceType.IMAGE.name);
									filePath = context+"/"+fileUploadPath;									
								//	baseDao.execute("insert into sys_resource_info(resourceType,businessType,businessId,originalName,name,fileSize,resourcePath,resourceSeq,status,addedTime) values ('"+resourceType+"','"+params.get("rSType")+"',"+params.get("keyPrimary")+",'"+fileOldName+"','"+fileUploadName+"',"+file.getSize()+",'"+filePath+"',IFNULL((SELECT max(ri.resourceSeq)+1 FROM sys_resource_info ri where ri.resourceType = '"+params.get("rType")+"' AND ri.businessType = '"+params.get("rSType")+"' AND ri.businessId = "+params.get("keyPrimary")+"),1),'1',now())");									
									if("promotion".equals(String.valueOf(params.get("rSType")))){
										// baseDao.execute("update promotion_home_banner set imgUrl = '"+filePath+"'  where id = "+params.get("keyPrimary")+"");									
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									System.out.println(e.getMessage());
								}
		                    }  
		                }  
		                //记录上传该文件后的时间  
		                int finaltime = (int) System.currentTimeMillis();  
		                System.out.println(finaltime - pre);  
		            }  
		        }
		        resultMap.put("id", StringUtils.isNotBlank(params.get("sessionId")+"") ? String.valueOf(params.get("sessionId")) : "");
		       	 resultMap.put("value", "1 Details");
		       	out.print(JSONObject.fromObject(resultMap)); 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if("3".equals(String.valueOf(params.get("opType")))){
		 try {
			out = response.getWriter();
	       	 int resourceId = StringUtils.isNotBlank(params.get("resourceId")+"") ? Integer.parseInt(String.valueOf(params.get("resourceId"))) : 0;
	       //	 baseDao.execute("delete from sys_resource_info where resourceId = "+resourceId+"");
	       	 resultMap.put("id", resourceId);
	       	 resultMap.put("value", "1 Details");
	       	out.print(JSONObject.fromObject(resultMap)); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }else if("4".equals(String.valueOf(params.get("opType")))){
  		 try {
 			 out = response.getWriter();
 	       	 int resourceId = StringUtils.isNotBlank(params.get("resourceId")+"") ? Integer.parseInt(String.valueOf(params.get("resourceId"))) : 0;
 	       	 Map<String, Object> map = null ;// baseDao.queryForMap("select operatorId,businessId,resourceType,businessType from sys_resource_info where resourceId = "+resourceId+"");
 	       	 if(map == null || StringUtils.isBlank("businessId") || StringUtils.isBlank("businessType")){
 	       		 resultMap.put("status", "false");
 	       	 }else{
 	       		 String resourceType = String.valueOf(map.get("resourceType"));
 	       		 
 	       		 if(!"101".equals(resourceType)){
 	 	 	       //	 baseDao.execute("update sys_resource_info set defaultMiddle = null where businessId = "+map.get("businessId")+" and businessType = '"+map.get("businessType")+"'");
 	 	 	      // 	 baseDao.execute("update sys_resource_info set defaultMiddle = '1' where resourceId = "+resourceId+"");
 	       		 }else{
 	 	       		 String operatorId = String.valueOf(map.get("operatorId"));
	 	 	      // 	 baseDao.execute("update sys_resource_info set resourceSeq = '1' where resourceId = "+resourceId+"");
	 	 	      // 	 baseDao.execute("update user_info set resourceId = '"+resourceId+"' where operatorId = '"+operatorId+"'");
	 	 	       	 List<Map<String, Object>> list = null; // baseDao.queryForList("select * from sys_resource_info where resourceType = '"+resourceType+"' and operatorId = '"+operatorId+"' and resourceId <> "+resourceId+" order by resourceSeq asc");
	 	 	       	 int idx = 2;
	 	 	       	 for(int i = 0;i < list.size();i++){
	 	 	       		 String rId = String.valueOf(list.get(i).get("resourceId"));
	 	 	       		// baseDao.execute("update sys_resource_info set resourceSeq = '"+idx+++"' where resourceId = "+rId+"");
	 	 	       	 }
 	       		 }
 		 	     resultMap.put("status", "true");
 	       	 }
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 	       	 resultMap.put("status", "false");
 		}
	     out.print(JSONObject.fromObject(resultMap)); 
        }
		return null;
	}
}
