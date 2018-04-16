package com.web.modules.authority.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.modules.authority.service.IAuthorityService;
import com.web.modules.authority.service.IFunctionService;
import com.web.util.JsonUtil;
import com.web.util.ParamsUtil;
import com.web.util.ReflectUtil;

/**
 * 功能管理
 * @author ZYL_PC
 *
 */
@RequestMapping("fun")
@Controller
public class FunctionController {
	private static final Logger log = Logger.getLogger(FunctionController.class);
	
	@Autowired
	private IAuthorityService authorityService;
	@Autowired
	private IFunctionService functionService;
	
	/**
	 * 查询所有的功能树
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getFunTree")
	public ModelAndView getFunTree(ModelAndView model,HttpServletRequest request,HttpServletResponse response){	
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			//查询所有的功能
			String funTreeHtml = functionService.getAllFunTreeHtml();
			model.addObject("funTreeHtml",funTreeHtml);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getFunTree********************************"+e.getMessage());
		}
		model.setViewName("/auth/fun/funTree");
		return model;
	} 	
	/**
	 * 查询所有的功能表格初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getFunTableInit")
	public ModelAndView getFunTableInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		model.addObject("param",params);
		model.setViewName("/auth/fun/funTable");
		return model;
	} 	
	/**
	 * 查询所有的功能表格json数据
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getFunTable")
	public String getFunTable(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String funTableJson = "";
		try {
			//查询所有的功能
			funTableJson = functionService.getAllFunTableJson();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getFunTable********************************"+e.getMessage());
		}finally{
			return funTableJson;
		}
	} 	
	/**
	 * 添加或者编辑功能页面初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("addOrEditFunInit")
	public ModelAndView addOrEditFunInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map nodeMap = functionService.selectById(Integer.parseInt(ParamsUtil.nullDeal(params, "id", "0")));
		model.addObject("resultInfo",nodeMap);
		model.addObject("param",params);
		model.setViewName("/auth/fun/funEdit");
		return model;
	} 	
	/**
	 * 添加或者修改功能
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("addOrEditFun")
	public String addOrEditFun(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String funTableJson = "";
		try {
			//查询所有的功能
			funTableJson = functionService.addOrEditFun(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("addOrEditFun********************************"+e.getMessage());
		}finally{
			return funTableJson;
		}
	} 	
	
	/**
	 * 查询功能树combobox
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getFunTreeJson")
	public String getFunTreeJson(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String funTreeJson = "";
		try {
			//查询所有的功能
			funTreeJson = functionService.getAllFunTreeJson();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getFunTreeJson********************************"+e.getMessage());
		}finally{
			return funTreeJson;
		}
	} 	
	/**
	 * 查询当前节点和下一子节点
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getCurAndNextOneNode")
	public String getCurAndNextOneNode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			json = functionService.getCurAndNextOneNode(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getCurAndNextOneNode********************************"+e.getMessage());
			json = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return json;
		}
	} 
	/**
	 * 删除功能树的节点和下面的所有节点
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("deleteAllChildrenNodebyId")
	public String deleteAllChildrenNodebyId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			json = functionService.deleteAllChildrenNodebyId(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("deleteAllChildrenNodebyId********************************"+e.getMessage());
			json = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return json;
		}
	} 
	
}
