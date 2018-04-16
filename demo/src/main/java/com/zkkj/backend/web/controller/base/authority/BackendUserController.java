package com.zkkj.backend.web.controller.base.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.MD5Util;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.common.util.StringRegexUtil;
import com.zkkj.backend.entity.biz.ZkkjUser;
import com.zkkj.backend.service.base.authentication.BackendUserService;
import com.zkkj.backend.web.controller.BaseController;

@Controller
@RequestMapping("bkuser")
public class BackendUserController extends BaseController{
	@Autowired
	private BackendUserService BackendUserService;

	public BackendUserService getBackendUserService() {
		return BackendUserService;
	}
	public void setBackendUserService(BackendUserService BackendUserService) {
		this.BackendUserService = BackendUserService;
	}
	
	@RequestMapping(value = "/getUserPage")
	public String getZkkjUserPage(HttpServletRequest request){
	
		return "userList";
	}
	
	/**
	 * 获取用户列表
	 * @param user
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/getUserList")
	@ResponseBody
	public void getZkkjUserList(ZkkjUser user,HttpServletResponse response)
			throws Exception {
	
		List<ZkkjUser> userList = BackendUserService.getBackendUserList(user);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("total", userList.size());
		data.put("rows", userList);
		response.getOutputStream().write(GsonUtil.getGson().toJson(data).getBytes("UTF-8"));
		
	}
	
	@RequestMapping(value = "/addBackendUser")
	public void addBackendUser(ZkkjUser user,HttpServletRequest request,HttpServletResponse response)
			throws Exception {
	   try{
		   String username = user.getAccount();
		   String password = user.getPassWord();
		   if(!StringRegexUtil.checkLetterNumberUnderline(username)||(username!=null && !(username.length()>=5 && username.length()<32))){
				warningResponse(false,getText("user.adduser.username.error",new String[]{"godlike"},request),response);
				return;
		   }
		   if((password!=null && !(password.length()>=6 && password.length()<16))){
				warningResponse(false,"密码必须大于等于6位小于16位字符！",response);
				return;
		   }  
		String md5Password=MD5Util.string2MD5(password);
		user.setPassWord(md5Password);
		int result = BackendUserService.addBackendUser(user);
		if(result>0){
			warningResponse(true,"创建用户成功！",response);
		}
		else{
			warningResponse(true,"创建用户失败！",response);
		}
	
	}catch(Exception e){
		warningResponse(true,"创建用户失败！",response);
	}
	}
		
	@RequestMapping(value = "/editBackendUser")
	public void editBackendUser(ZkkjUser user,HttpServletResponse response)
			throws Exception {
	   try{
		   if(StringUtils.isNotBlank(user.getPassWord())){
				String md5Password=MD5Util.string2MD5(user.getPassWord());
				user.setPassWord(md5Password);
		   }else{
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","密码不能为空！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				return;
		   }
			int result = BackendUserService.editBackendUser(user);
			if(result>0){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","true");
				res.put("message","编辑用户成功！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
			else{
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","编辑用户失败！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			}
	   }catch(Exception e){
			Map<String,String> res = new HashMap<String,String>();
			res.put("success","false");
			res.put("message","编辑用户失败！");
			response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
	   }
	}
	@RequestMapping(value = "/removeBackendUser")
	public void removeBackendUser(Integer operatorId,HttpServletResponse response)
			throws Exception {
		   try{
				int result = BackendUserService.removeBackendUser(operatorId);
				if(result>0){
					Map<String,String> res = new HashMap<String,String>();
					res.put("success","true");
					res.put("message","删除用户成功！");
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
				else{
					Map<String,String> res = new HashMap<String,String>();
					res.put("success","false");
					res.put("message","删除用户失败！");
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
		   }catch(Exception e){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message","删除用户失败！");
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
		   }
		
	}
	

	@RequestMapping(value = "/getUserRolePage")
	public ModelAndView getUserRolePage(HttpServletRequest request){
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("operatorId", request.getParameter("operatorId"));
		mav.setViewName("AuthUserRole");
		return mav;
	}
	
	@RequestMapping(value = "/addUserRole")
	public void addUserRole(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> data = new HashMap<String,Object>();
		
			Map<String, Object> inputMap = ReflectUtil.transToMAP(request.getParameterMap());
			Integer operatorId = Integer.valueOf((String) inputMap.get("operatorId"));
			Integer roleId = Integer.valueOf((String) inputMap.get("roleId"));

			boolean res = BackendUserService.addUserRole(operatorId, roleId);	
			
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
	
	@RequestMapping(value = "/getUserOwnedRolePage")
	public ModelAndView getUserOwnedRolePage(HttpServletRequest request){
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("operatorId", request.getParameter("id"));
		mav.setViewName("AuthUserOwnedRole");
		return mav;
	}
	
	@RequestMapping(value = "/removeOwnedRole")
	public void removeAuthorityRole(Integer operatorId,Integer roleId,HttpServletResponse response)
			throws Exception {
		   try{
				boolean result = BackendUserService.removeOwnedRole(operatorId,roleId);
				if(result){
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
	
}
