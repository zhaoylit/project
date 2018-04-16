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

import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.entity.AuthorityPrivilege;
import com.zkkj.backend.service.base.authority.AuthPrivilegeService;
import com.zkkj.backend.web.controller.BaseController;

@Controller
@RequestMapping("priv")
public class AuthPrivilegeController extends BaseController{
	@Autowired
	private AuthPrivilegeService authPrivilegeService;

	public AuthPrivilegeService getAuthPrivilegeService() {
		return authPrivilegeService;
	}
	public void setAuthPrivilegeService(AuthPrivilegeService authPrivilegeService) {
		this.authPrivilegeService = authPrivilegeService;
	}
	
	@RequestMapping(value = "/getPrivilegePage")
	public String getAuthorityPrivilegePage(HttpServletRequest request){
	
		return "privilegeList";
	}
	
	@RequestMapping(value = "/getRolePrivilegePage")
	public String getRolePrivilegePage(HttpServletRequest request){
	
		return "rolePrivilegeList";
	}
	
	/**
	 * 获取权限列表
	 * @param privilege
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/getPrivilegeList")
	@ResponseBody
	public void getAuthorityPrivilegeList(AuthorityPrivilege privilege,HttpServletResponse response)
			throws Exception {
	
		List<AuthorityPrivilege> privilegeList = authPrivilegeService.getAuthorityPrivilegeList(privilege);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("total", privilegeList.size());
		data.put("rows", privilegeList);
		response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
		
	}
	
	@RequestMapping(value = "/addAuthorityPrivilege")
	public void addAuthorityPrivilege(AuthorityPrivilege privilege,HttpServletResponse response)
			throws Exception {
	   try{
		int result = authPrivilegeService.addAuthorityPrivilege(privilege);
		if(result>0){
			Map<String,String> res = new HashMap<String,String>();
			res.put("success","true");
			res.put("message","创建权限成功！");
			response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
		}
		else{
			Map<String,String> res = new HashMap<String,String>();
			res.put("success","false");
			res.put("message","创建权限失败！");
			response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
		}
	
	}catch(Exception e){
		Map<String,String> res = new HashMap<String,String>();
		res.put("success","false");
		res.put("message","创建权限失败！");
		response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
		
	}
	}
		
	@RequestMapping(value = "/editAuthorityPrivilege")
	public void editAuthorityPrivilege(AuthorityPrivilege privilege,HttpServletResponse response)
			throws Exception {
	   try{
			int result = authPrivilegeService.editAuthorityPrivilege(privilege);
			if(result>0){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","true");
				res.put("message","编辑权限成功！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
			else{
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","编辑权限失败！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
	   }catch(Exception e){
			Map<String,String> res = new HashMap<String,String>();
			res.put("success","false");
			res.put("message","编辑权限失败！");
			response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
	   }
	}
	
	@RequestMapping(value = "/removeAuthorityPrivilege")
	public void removeAuthorityPrivilege(Integer privilegeId,HttpServletResponse response)
			throws Exception {
		   try{
				int result = authPrivilegeService.removeAuthorityPrivilege(privilegeId);
				if(result>0){
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
	
	@RequestMapping(value = "/getOwnedPrivilegeList")
	@ResponseBody
	public void getOwnedPrivilegeList(Integer roleId,HttpServletResponse response)
			throws Exception {
	
		List<AuthorityPrivilege> userList = authPrivilegeService.getRoleOwnedPrivilegeList(roleId);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("total", userList.size());
		data.put("rows", userList);
		response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
		
	}
	
	
}
