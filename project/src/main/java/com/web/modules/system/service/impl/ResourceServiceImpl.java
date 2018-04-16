package com.web.modules.system.service.impl;

import io.socket.client.Url;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.compilers.Jikes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.css.CSSStyleSheet;

import com.web.common.constant.Constant;
import com.web.modules.system.dao.ResourceMapper;
import com.web.modules.system.service.IResourceService;
import com.web.util.CustomizedPropertyConfigurer;
import com.web.util.FileUtil;
import com.web.util.JsonUtil;
import com.web.util.ParamsUtil;
import com.web.util.cssparser.CssParserUtil;

@Service("resourceService")
public class ResourceServiceImpl implements IResourceService {
	@Autowired
	private ResourceMapper resourceMapper;
	
	private final static Logger log = Logger.getLogger(ResourceServiceImpl.class);

	@Override
	public List<Map> getIconList(Map params) throws Exception {
		// TODO Auto-generated method stub
		String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
		List<Map> list = resourceMapper.getIconList(params);
		for(Map mm : list){
			if(mm != null){
				String fileName = ParamsUtil.nullDeal(mm, "fileName", "");
				String filePath = ParamsUtil.nullDeal(mm, "filePath", "");
				String url = ParamsUtil.nullDeal(mm, "url", "");
				mm.put("fileNamShow", fileName+filePath.substring(filePath.lastIndexOf	(".")));
				mm.put("url", return_path + url);
			}
			
		}
		return list;
	}

	@Override
	public int getIconListCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		int count = resourceMapper.getIconListCount(params);
		return 0;
	}

	@Override
	public Map selectDictById(int id) throws Exception {
		// TODO Auto-generated method stub
		Map iconMap = resourceMapper.selectIconById(id);
		return iconMap;
	}

	@Override
	public String addOrEditDict(Map params) throws Exception {
		// TODO Auto-generated method stub
		Integer id = Integer.parseInt(ParamsUtil.nullDeal(params, "id", "0"));
		String oldCssName = "";//编辑前的样式名称
		String className = ParamsUtil.nullDeal(params, "className", "");//编辑的样式名称
		String filePath = ParamsUtil.nullDeal(params, "filePath", "");
		String fileName = ParamsUtil.nullDeal(params, "fileName", "");
		String resourceType = ParamsUtil.nullDeal(params, "resourceType", "");
		if("".equals(className) || "".equals(filePath) || "".equals(resourceType)){
			return JsonUtil.getResultStatusJson("0","表单数据不完整！");
		}
		Map iconMap = null;
		CSSStyleSheet sheet = null;
		//文件上传的完整路径
        String fileUploadPath = "";
        String suffix = "";
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
//		String path = request.getSession().getServletContext().getRealPath("/");
        //资源访问地址
		String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
		//数据存放位置根目录
		String data_path = (String)CustomizedPropertyConfigurer.getContextProperty("data_path");
		//icon保存地址
		String icon_path = (String)CustomizedPropertyConfigurer.getContextProperty("icon_path");
		
		String cssFilePath = data_path + "css/icon.css";
		String cssImagePath = data_path + icon_path;
		//是否检查样式选择器名称是否存在标记
		boolean flag = true;
		if(id != 0){
			iconMap = resourceMapper.selectIconById(id);
			oldCssName = ParamsUtil.nullDeal(iconMap, "className", "");
			if(className.equals(oldCssName)){
				flag = false;
			}
		}
		if(flag){
			//判断选择器名称是否存在
			InputStream inputStream = new FileInputStream(new File(cssFilePath));
			boolean isExist = CssParserUtil.checkSelectorText(inputStream, "."+className);
			if(isExist){
				return JsonUtil.getResultStatusJson("0", "选择器名称已存在");
			}
		}
		
		if(!"".equals(filePath)){
			String fileName_ = filePath.substring(filePath.lastIndexOf("/")+1);
			suffix = fileName_.substring(fileName_.lastIndexOf("."));
			
			if(!"".equals(fileName)){
				String iconFileName = ParamsUtil.nullDeal(iconMap, "fileName", "");
				if(!fileName.equals(iconFileName)){
					//判断文件名称是否存在
					File file = new File(cssImagePath+fileName+suffix);
					if(file.exists()){
						return JsonUtil.getResultStatusJson("0","文件名重复");
					}
				}
				fileName_ = fileName+suffix;
			}
			//复制文件到指定目录
			if(FileUtil.copyFile(filePath, cssImagePath+fileName_)){
				fileUploadPath = cssImagePath+fileName_;
				
				InputStream inputStream = new FileInputStream(new File(cssFilePath));
				//先删除编辑之前的样式，刷新样式文件，再插入新的样式
				if(!"".equals(oldCssName)){
					sheet = CssParserUtil.deleteRule(inputStream, "."+oldCssName);
					FileOutputStream out = new FileOutputStream(new File(cssFilePath));
					out.write(sheet.getCssRules().toString().getBytes());
					out.close();
					inputStream = new FileInputStream(new File(cssFilePath));
				}
				
				//插入编辑的样式
				sheet = CssParserUtil.insertRule(inputStream,"."+className+"{background:url('"+return_path+"image/icon/"+fileName_+"') no-repeat center center;}");
				if(sheet == null){
					FileUtil.removeFile(fileUploadPath);
					return JsonUtil.getResultStatusJson("0", "css解析失败，请检查选择器名称是否合法");
				}
				params.put("fileName", fileName_.subSequence(0, fileName_.lastIndexOf(".")));
			}else{
				//附件复制失败，设置文件名称为之前保存的名称
				if(iconMap != null){
					String fn = ParamsUtil.nullDeal(iconMap, "fileName", "");
					params.put("fileName",fn);
				}else{
					return JsonUtil.getResultStatusJson("0","找不到上传的文件	");
				}
			}
		}else{
			return JsonUtil.getResultStatusJson("0","找不到上传的文件	");
		}
		
		int rowsCount = 0;
		try {
			if(id == 0){
				rowsCount = resourceMapper.insert(params);
			}else{
				rowsCount = resourceMapper.updateById(params);
			}
			if(rowsCount == 0){
				//删除上传的文件
				FileUtil.removeFile(fileUploadPath);
				return JsonUtil.getResultStatusJson("0", "操作失败");
			}
			if(sheet != null){
				FileOutputStream out = new FileOutputStream(new File(cssFilePath));
				out.write(sheet.getCssRules().toString().getBytes());
				out.close();
			}
			
			//删除之前的文件
			if(iconMap != null){
				String iconFileName = ParamsUtil.nullDeal(iconMap, "fileName", "");
				if(!iconFileName.equals(fileName)){
					FileUtil.removeFile(cssImagePath+iconFileName+suffix);
				}
			}
			
			return JsonUtil.getResultStatusJson("1", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
			//删除上传的文件
			FileUtil.removeFile(fileUploadPath);
			return JsonUtil.getResultStatusJson("0",e.getMessage()+"");
		}
	}

	@Override
	public String deleteIcon(Map params) throws Exception {
		// TODO Auto-generated method stub
		int rowsCount = 0;
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
//		String path = request.getSession().getServletContext().getRealPath("/");
		String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
		String data_path = (String)CustomizedPropertyConfigurer.getContextProperty("data_path");
		
		String cssFilePath = data_path + "css/icon.css";
		String cssImagePath = data_path + "image/icon/";
		String ids = ParamsUtil.nullDeal(params, "ids", "0");
		String array[] = ids.split(",");
		if(array.length > 0){
			for(String str : array){
				Map iconMap = resourceMapper.selectIconById(Integer.parseInt(str));
				if(resourceMapper.deletebyId(str) > 0){
					if(iconMap != null){
						String filePath = ParamsUtil.nullDeal(iconMap, "filePath", "");
						String fileName = ParamsUtil.nullDeal(iconMap, "fileName", "");
						String className = ParamsUtil.nullDeal(iconMap, "className", "");
						String suffix = filePath.substring(filePath.lastIndexOf("."));
						
						if(!"".equals(fileName)){
							//删除文件
							FileUtil.removeFile(cssImagePath + fileName + suffix);
						}
						
						InputStream inputStream = new FileInputStream(new File(cssFilePath));
						CSSStyleSheet sheet = CssParserUtil.deleteRule(inputStream, "."+className);
						
						FileOutputStream out = new FileOutputStream(new File(cssFilePath));
						out.write(sheet.getCssRules().toString().getBytes());
						out.close();
						
					}
					rowsCount++;
				}
			}
		}
		if(rowsCount == 0){
			return JsonUtil.getResultStatusJson("0", "操作失败");
		}
		return JsonUtil.getResultStatusJson("1", "成功删除"+rowsCount+"条数据");
	}

	@Override
	public String selectResourcePathByType(String path) throws Exception {
		// TODO Auto-generated method stub
		return resourceMapper.selectResourcePathByType(path);
	}
}
