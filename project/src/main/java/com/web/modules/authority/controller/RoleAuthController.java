package com.web.modules.authority.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.ddf.EscherColorRef.SysIndexProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.modules.authority.service.IAuthorityService;
import com.web.modules.authority.service.IRoleAuthService;
import com.web.util.JsonUtil;
import com.web.util.ParamsUtil;
import com.web.util.ReflectUtil;

/**
 * 角色权限管理
 * @author ZYL_PC
 *
 */
@RequestMapping("roleAuth")
@Controller
public class RoleAuthController {
	@Autowired
	private IAuthorityService authorityService;
	@Autowired
	private IRoleAuthService roleAuthService;
	private final static Logger log = Logger.getLogger(RoleAuthController.class);
	
	/**
	 * 查询所有的功能表格初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("roleAuthManageInit")
	public ModelAndView roleAuthManageInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		model.addObject("param",params);
		//查询
		model.setViewName("/auth/roleAuth/roleAuthManage");
		return model;
	} 	
	
	/**
	 * 角色列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("roleList")
	public void roleList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		try {
			//查询所有的菜单
			List<Map> iconList = roleAuthService.getRoleList(params);
			resultMap.put("total", roleAuthService.getRoleListCount(params));
			resultMap.put("rows", iconList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("roleList********************************"+e.getMessage());
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}
	} 
	
	@RequestMapping("getAllAuthByRole")
	@ResponseBody
	public void getAllAuthByRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map> resultList = null;
		try {
			resultList = roleAuthService.getAllAuthByRole(params);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("getAllAuthByRole**********************************"+e.getMessage());
			resultList = Collections.EMPTY_LIST;
		}finally{
			response.getOutputStream().write(JSONArray.fromObject(resultList).toString().getBytes());
		}
	}
	
	/**
	 * 添加或者编辑角色
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("addOrEditRoleInit")
	public ModelAndView addOrEditRoleInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		model.addObject("param",params);
		Integer roleId = Integer.parseInt(ParamsUtil.nullDeal(params, "roleId", "0"));
		if(roleId != 0){
			//根据角色id查询角色信息
			try {
				List<Map> list = roleAuthService.getRoleList(params);
				if(list != null && list.size() > 0){
					model.addObject("resultInfo",list.get(0));
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("addOrEditRoleInit*******************************" + e.getMessage());
			}
		}
		model.setViewName("/auth/roleAuth/roleEdit");
		return model;
	} 	
	/**
	 * 添加或者编辑角色
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("addOrEditRole")
	public String addOrEditRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			//查询所有的功能
			json = roleAuthService.addOrEditRole(params);
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
	 * 删除角色
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("deleteRole")
	public String deleteRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			//查询所有的功能
			json = roleAuthService.deleteRole(params);
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
	 * 更新用户权限信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("updateRoleAuth")
	public String updateRoleAuth(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			//查询所有的功能			
			json = roleAuthService.updateRoleAuth(params);
		} catch (Exception e) {	
			// TODO: handle exception
			e.printStackTrace();
			log.info("addOrEditFun********************************"+e.getMessage());
			json = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return json;
		}
	} 
}
