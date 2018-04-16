package com.zj.web.modules.document.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zj.web.common.utils.ReflectUtil;
import com.zj.web.modules.document.service.IApiService;
import com.zj.web.modules.goods.service.IGoodsCategoryService;

@RequestMapping("api")
@Controller
public class ApiController {
	@Autowired
	private IApiService apiService;
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private IGoodsCategoryService goodsCategoryService;
	@RequestMapping(value="selectListInit")
	public ModelAndView selectListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("document/api/apiListInit");
		return model;
	}
	/**
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getApiCategoryTreeJson")
	public String getApiCategoryTreeJson(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String treeJson = "";
		try {
			//查询所有的菜单
			treeJson = apiService.getApiCategoryTreeJson(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getMenuTreeJson********************************"+e.getMessage());
		}finally{
			return treeJson;
		}
	} 
}
