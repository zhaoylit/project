package com.zj.web.modules.system.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zj.web.common.utils.ReflectUtil;
import com.zj.web.modules.system.service.IDictService;

@RequestMapping("dict")
@Controller
public class DictController {
	private static final Logger log = Logger.getLogger(DictController.class);
	
	@Autowired
	private IDictService dictService;
	/**
	 * 根据key查询字典
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getDictByKey")
	public void getDictByKey(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map> resultList = null;
		try {
			//查询所有的功能
			resultList = dictService.getDictByKey(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("addOrEditFun********************************"+e.getMessage());
			resultList = Collections.EMPTY_LIST;
		}finally{
			response.getOutputStream().write(JSONArray.fromObject(resultList).toString().getBytes());
		}
	}
}
