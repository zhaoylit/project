package com.web.modules.authority.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.modules.authority.service.IMenuService;
import com.web.util.JsonUtil;
import com.web.util.ParamsUtil;
import com.web.util.ReflectUtil;

/**
 * 菜单管理控制层
 * @author ZYL_PC
 *
 */
@RequestMapping("menu")
@Controller
public class MenuController {
	@Autowired
	private IMenuService menuService;
	private final static Logger log = Logger.getLogger(MenuController.class);
	
	/**
	 * 查询所有的菜单树
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMenuTree")
	public ModelAndView getMenuTree(ModelAndView model,HttpServletRequest request,HttpServletResponse response){	
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			//查询所有的菜单
			String menuTreeHtml = menuService.getAllMenuTreeHtml();
			model.addObject("menuTreeHtml",menuTreeHtml);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getMenuTree********************************"+e.getMessage());
		}
		model.setViewName("/auth/menu/menuTree");
		return model;
	} 	
	/**
	 * 查询所有的菜单表格初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMenuTableInit")
	public ModelAndView getMenuTableInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		model.addObject("param",params);
		model.setViewName("/auth/menu/menuTable");
		return model;
	} 	
	/**
	 * 查询所有的菜单表格json数据
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getMenuTable")
	public String getMenuTable(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String menuTableJson = "";
		try {
			//查询所有的菜单
			menuTableJson = menuService.getAllMenuTableJson();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getMenuTable********************************"+e.getMessage());
		}finally{
			return menuTableJson;
		}
	} 	
	/**
	 * 添加或者编辑菜单页面初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("addOrEditMenuInit")
	public ModelAndView addOrEditMenuInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map nodeMap = menuService.selectById(Integer.parseInt(ParamsUtil.nullDeal(params, "id", "0")));
		model.addObject("resultInfo",nodeMap);
		model.addObject("param",params);
		model.setViewName("/auth/menu/menuEdit");
		return model;
	} 	
	/**
	 * 添加或者修改菜单
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("addOrEditMenu")
	public String addOrEditMenu(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String menuTableJson = "";
		try {
			//查询所有的菜单
			menuTableJson = menuService.addOrEditMenu(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("addOrEditMenu********************************"+e.getMessage());
			menuTableJson = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return menuTableJson;
		}
	} 	
	
	/**
	 * 查询菜单树combobox
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getMenuTreeJson")
	public String getMenuTreeJson(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String menuTreeJson = "";
		try {
			//查询所有的菜单
			menuTreeJson = menuService.getAllMenuTreeJson();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getMenuTreeJson********************************"+e.getMessage());
		}finally{
			return menuTreeJson;
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
			json = menuService.getCurAndNextOneNode(params);
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
	 * 删除菜单树的节点和下面的所有节点
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
			json = menuService.deleteAllChildrenNodebyId(params);
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
