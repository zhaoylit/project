package com.zkkj.backend.web.controller.base.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.entity.AuthorityRole;
import com.zkkj.backend.service.base.authority.AuthRoleService;
import com.zkkj.backend.web.controller.BaseController;

@Controller
@RequestMapping("role")
public class AuthRoleController  extends BaseController{
	@Autowired
	private AuthRoleService authRoleService;

	public AuthRoleService getAuthRoleService() {
		return authRoleService;
	}
	public void setAuthRoleService(AuthRoleService authRoleService) {
		this.authRoleService = authRoleService;
	}
	
	@RequestMapping(value = "/getRolePage")
	public String getAuthorityRolePage(HttpServletRequest request){
	
		return "roleList";
	}
	
	/**
	 * 获取角色列表
	 * @param role
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/getRoleList")
	@ResponseBody
	public void getAuthorityRoleList(AuthorityRole role,HttpServletResponse response)
			throws Exception {	
		List<AuthorityRole> roleList = authRoleService.getAuthorityRoleList(role);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("total", roleList.size());
		data.put("rows", roleList);
		response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
		
	}
	
	@RequestMapping(value = "/getOwnedRoleList")
	@ResponseBody
	public void getUserOwnedRoleList(Integer operatorId,HttpServletResponse response)
			throws Exception {
	
		List<AuthorityRole> userList = authRoleService.getUserOwnedRoleList(operatorId);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("total", userList.size());
		data.put("rows", userList);
		response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
		
	}
	
	@RequestMapping(value = "/addAuthorityRole")
	public void addAuthorityRole(AuthorityRole role,HttpServletResponse response)
			throws Exception {
	   try{
			int result = authRoleService.addAuthorityRole(role);
			if(result>0){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","true");
				res.put("message","创建角色成功！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
			else{
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","创建角色失败！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
		
		}catch(Exception e){
			Map<String,String> res = new HashMap<String,String>();
			res.put("success","false");
			res.put("message","创建角色失败！");
			response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			
		}
	}
		
	@RequestMapping(value = "/editAuthorityRole")
	public void editAuthorityRole(AuthorityRole role,HttpServletResponse response)
			throws Exception {
	   try{
			int result = authRoleService.editAuthorityRole(role);
			if(result>0){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","true");
				res.put("message","编辑角色成功！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
			else{
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","编辑角色失败！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
	   }catch(Exception e){
			Map<String,String> res = new HashMap<String,String>();
			res.put("success","false");
			res.put("message","编辑角色失败！");
			response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
	   }
	}
	@RequestMapping(value = "/removeAuthorityRole")
	public void removeAuthorityRole(Integer roleId,HttpServletResponse response)
			throws Exception {
		   try{
				int result = authRoleService.removeAuthorityRole(roleId);
				if(result>0){
					Map<String,String> res = new HashMap<String,String>();
					res.put("success","true");
					res.put("message","删除角色成功！");
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
				else{
					Map<String,String> res = new HashMap<String,String>();
					res.put("success","false");
					res.put("message","删除角色失败！");
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
		   }catch(Exception e){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","删除角色失败！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
		   }
		
	}
	
	@RequestMapping(value = "/getRolePrivilegePage")
	public ModelAndView getRolePrivilegePage(HttpServletRequest request){
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("roleId", request.getParameter("roleId"));
		mav.setViewName("AuthRolePrivilege");
		return mav;
	}

	@RequestMapping(value = "/addRolePrivilege")
	public void addRolePrivilege(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> data = new HashMap<String,Object>();
		
			Map<String, Object> inputMap = ReflectUtil.transToMAP(request.getParameterMap());
			Integer roleId = Integer.valueOf((String) inputMap.get("roleId"));
			Integer privilegeId = Integer.valueOf((String) inputMap.get("privilegeId"));

			boolean res = authRoleService.addRolePrivilege(roleId, privilegeId);	
			
			if(res){
				data.put("success", "true");
				data.put("message", "赋予角色成功！");
			}
			else{
				data.put("success", "false");
				data.put("message", "赋予角色失败！");
			}		
		response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
	}	

	@RequestMapping(value = "/getRoleOwnedPrivilegePage")
	public ModelAndView getRoleOwnedPrivilegePage(HttpServletRequest request){
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("roleId", request.getParameter("roleId"));
		mav.setViewName("AuthRoleOwnedPrivilege");
		return mav;
	}
	
	@RequestMapping(value = "/removeOwnedPrivilege")
	public void removeOwnedPrivilege(Integer roleId,Integer privilegeId,HttpServletResponse response)
			throws Exception {
		   try{
				boolean result = authRoleService.removeOwnedPrivilege(roleId,privilegeId);
				if(result){
					Map<String,String> res = new HashMap<String,String>();
					res.put("success","true");
					res.put("message","删除权限成功！");
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
				else{
					Map<String,String> res = new HashMap<String,String>();
					res.put("success","false");
					res.put("message","删除权限失败！");
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
		   }catch(Exception e){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","删除权限失败！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
		   }
		
	}
	
	
	@RequestMapping(value = "/getRoleOwnedMenuPage")
	public ModelAndView getRoleOwnedMenuPage(HttpServletRequest request){
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("roleId", request.getParameter("roleId"));
		mav.setViewName("AuthRoleOwnedMenu");
		return mav;
	}
}
