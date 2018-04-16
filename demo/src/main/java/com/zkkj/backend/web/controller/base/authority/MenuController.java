package com.zkkj.backend.web.controller.base.authority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.constant.RoleType;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.entity.AuthorityMenu;
import com.zkkj.backend.service.base.authentication.AuthenticationService;
import com.zkkj.backend.service.base.authentication.Principal;
import com.zkkj.backend.service.base.authentication.Subject;
import com.zkkj.backend.service.base.authority.AuthMenuService;
import com.zkkj.backend.service.base.authority.AuthRoleService;
import com.zkkj.backend.web.controller.BaseController;
import com.zkkj.backend.web.view.base.auth.MenuBean;

@Controller("menuAction")
@RequestMapping(value="/menu") //Manage: 后台拦截
@Scope("prototype")
public class MenuController extends BaseController{
	
	
	@Autowired
	private AuthMenuService authMenuService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private AuthRoleService roleService;
	
	public AuthMenuService getAuthMenuService() {
		return authMenuService;
	}

	public void setAuthMenuService(AuthMenuService authMenuService) {
		this.authMenuService = authMenuService;
	}
	
	@RequestMapping("/index")
	public ModelAndView getMainMenu(String parentId, HttpServletRequest request)
			throws IOException {
		//是否是地服
		boolean isDF = false;
		//认证过的菜单
	
		parentId = "0";
		//获得所有第一级菜单
		List<AuthorityMenu> menuList = authMenuService.getAuthorityMenuViewList(Integer.parseInt(parentId));
		//获得登录主体
		Subject subject = authenticationService.getSubject(request);
		//如果用户已登录
		List<Map<String, String>> resMenuList = new ArrayList<Map<String, String>>(menuList.size());
		ModelAndView mav = new ModelAndView("index");
		try{
		if("admin".equals(request.getSession().getAttribute(Subject.AUTH_USERNAME))){
			for (int i = 0; i < menuList.size(); i++) {
				AuthorityMenu menu = menuList.get(i);
					Map<String,String> resMenu = new HashMap<String,String>(); 
					resMenu.put("visible", IConstant.MENU_VISIBLE);
					resMenu.put("menuId",String.valueOf(menu.getMenuId()));
					resMenu.put("name",String.valueOf(menu.getName()));
					resMenu.put("alias",String.valueOf(menu.getAlias()));
					resMenu.put("parentId",String.valueOf(menu.getParentId()));
					resMenu.put("level",String.valueOf(menu.getLevel()));
					resMenu.put("menuUrl",String.valueOf(menu.getMenuURL()));
					resMenu.put("index",String.valueOf(menu.getIndex()));				    
					resMenuList.add(resMenu);		
			}	
			isDF = true;
		}else if (subject != null) {
			List<Principal> principalList = subject.getPrincipals();
			//获得用户拥有的身份
			if (principalList != null && !principalList.isEmpty()) {
				//登录主体能看到的所有菜单
				List<Integer> menuIdList = new ArrayList<Integer>();
				for (Principal principal : principalList) {
					if (principal.getMenuList() != null)
						menuIdList.addAll(principal.getMenuList());
					//判断是否是机场地服			
						if(RoleType.DF.zhName.equals(principal.getRoleName())||"DF".equalsIgnoreCase(principal.getRoleAlias()))
							isDF = true;
				}
				
				if (!menuIdList.isEmpty()) {
					resMenuList = new ArrayList<Map<String, String>>(
							menuIdList.size());
					//过滤掉没有授权的菜单
					List<Integer>  tempList = menuIdList;
					for (int i = 0; i < menuList.size(); i++) {
						AuthorityMenu menu = menuList.get(i);
						Integer menuId = menu.getMenuId();
						if (tempList.contains(menuId)) {
							
							Map<String,String> resMenu = new HashMap<String,String>(); 
							resMenu.put("visible", IConstant.MENU_VISIBLE);
							resMenu.put("menuId",String.valueOf(menu.getMenuId()));
							resMenu.put("name",String.valueOf(menu.getName()));
							resMenu.put("alias",String.valueOf(menu.getAlias()));
							resMenu.put("parentId",String.valueOf(menu.getParentId()));
							resMenu.put("level",String.valueOf(menu.getLevel()));
							resMenu.put("menuUrl",String.valueOf(menu.getMenuURL()));
							resMenu.put("index",String.valueOf(menu.getIndex()));				    
							resMenuList.add(resMenu);
						}
						
					}
				}
			}
			}
		}catch(Exception e){
		
		}
		mav.addObject("mainMenu", resMenuList);
		mav.addObject("isDF", isDF);	
		mav.addObject("clientIP", getIpAddr(request));
		
		return mav;
	}
	
	public List<String>  transformList(List<Map<String, String>> menuIdList){
		try{
		List<String> muenuIdList = new ArrayList<String>(menuIdList.size());
		for(Map<String, String> item:menuIdList){
			String key = item.get("menuId");
			muenuIdList.add(key);		
		}	
		return muenuIdList;
		}catch(Exception e){
			
		}
		return Collections.emptyList();
	}
	 /**
	  * 获取客户端IP
	  * @param request
	  * @return 
	  */
	 public static String getIpAddr(HttpServletRequest request) {
	        String ip = request.getHeader("x-forwarded-for");
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getRemoteAddr();
	        }
	        return ip;
	    }
	 
	/**
	 * 获得树形菜单
	 * @param parentId父菜单ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTreeMenu1")
	@ResponseBody
	public void getTreeMenu1(Integer parentId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
		List<AuthorityMenu> authMenuList = authMenuService.getAuthorityMenuViewList(parentId);
		List<MenuBean> menuList = new ArrayList<MenuBean>();	
			for (AuthorityMenu menu : authMenuList) {
				MenuBean menuBean = new MenuBean();
				menuBean.setId(menu.getMenuId());
				menuBean.setText(menu.getName());
				//设置子菜单
				setChildrenAuthorityMenu(menuBean);
				Map<String,String> attributes = new HashMap<String,String>(3);
				attributes.put("menuId", menu.getMenuId().toString());
				if(menu.getIndex()!=null)
					attributes.put("index", menu.getIndex().toString());
				if(menu.getParentId()!=null)
					attributes.put("parentId", menu.getParentId().toString());
				if(menu.getVisible()!=null)
					attributes.put("visible", menu.getVisible());
				//设置菜单跳转url
				if( StringUtils.isNotBlank(menu.getMenuURL())){			
					attributes.put("menuUrl", menu.getMenuURL());		
				}
				menuBean.setAttributes(attributes);
				menuList.add(menuBean);
			}			
			menuList = filterMenuForSecurity(menuList,request);		
			response.getOutputStream().write(GsonUtil.getGson().toJson(menuList).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * 获得树形菜单
	 * @param parentId父菜单ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTreeMenu")
	@ResponseBody
	public void getTreeMenu(Integer parentId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			List<AuthorityMenu> authMenuList = authMenuService.getAuthorityMenuViewList(parentId);
		List<MenuBean> menuList = new ArrayList<MenuBean>();	
			for (AuthorityMenu menu : authMenuList) {
				MenuBean menuBean = new MenuBean();
				menuBean.setId(menu.getMenuId());
				menuBean.setText(menu.getName());
				//设置子菜单
				setChildrenAuthorityMenu(menuBean);
				Map<String,String> attributes = new HashMap<String,String>(3);
				attributes.put("menuId", String.valueOf(menu.getMenuId()));
				attributes.put("index", String.valueOf(menu.getIndex()));
				attributes.put("parentId", String.valueOf(menu.getParentId()));
				attributes.put("visible",String.valueOf(menu.getVisible()));
				//设置菜单跳转url		
				attributes.put("menuUrl",menu.getMenuURL());		
				menuBean.setAttributes(attributes);
				menuList.add(menuBean);
			}			
			menuList = filterMenuForSecurity(menuList,request);		
			response.getOutputStream().write(GsonUtil.getGson().toJson(menuList).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获得树形菜单
	 * @param parentId父菜单ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoleTreeMenu")
	@ResponseBody
	public void getRoleTreeMenu(Integer parentId,Integer roleId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
		List<AuthorityMenu> authMenuList = authMenuService.getAuthorityMenuViewList(parentId);
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		List<Integer>  roleMenuList = roleService.getMenuListByRoleId(roleId);
			for (AuthorityMenu menu : authMenuList) {
				MenuBean menuBean = new MenuBean();
				menuBean.setId(menu.getMenuId());
				menuBean.setText(menu.getName());
				//设置子菜单
				setChildrenRoleMenu(menuBean,roleMenuList);
				Map<String,String> attributes = new HashMap<String,String>(3);
				attributes.put("menuId", menu.getMenuId().toString());
				if(menu.getIndex()!=null)
					attributes.put("index", menu.getIndex().toString());
				if(menu.getParentId()!=null)
					attributes.put("parentId", menu.getParentId().toString());
				if(menu.getVisible()!=null)
					attributes.put("visible", menu.getVisible());
				//设置菜单跳转url
				if( StringUtils.isNotBlank(menu.getMenuURL())){			
					attributes.put("menuUrl", menu.getMenuURL());		
				}
				menuBean.setAttributes(attributes);
				
				if(roleMenuList.contains(menuBean.getId()))
					menuBean.setChecked(true);
				else
					menuBean.setChecked(false);
				menuList.add(menuBean);
			}			
			
			response.getOutputStream().write(GsonUtil.getGson().toJson(menuList).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<MenuBean> filterMenuForSecurity(List<MenuBean> menuList, HttpServletRequest request){
		
			Subject subject = authenticationService.getSubject(request);
			if(subject!=null&&"admin".equals(request.getSession().getAttribute(Subject.AUTH_USERNAME)))
				return menuList;
			List<MenuBean> resMenuList = null;
			try{
			List<Principal> principalList = subject.getPrincipals();
			if(principalList!=null && !principalList.isEmpty()){
				List<Integer> menuIdList = new ArrayList<Integer>();
				for(Principal principal:principalList){
					if(principal.getMenuList()!=null)
						menuIdList.addAll(principal.getMenuList());			
				}
				if(!menuIdList.isEmpty()){
					resMenuList = new ArrayList<MenuBean>(menuList.size());
					for(int i=0;i<menuList.size();i++){
						MenuBean menu = menuList.get(i);
					     	Integer menuId = Integer.parseInt((String)menu.getAttributes().get("menuId"));
					     	if(menuIdList.contains(menuId)){
					     		menu.setChecked(true);
					     		resMenuList.add(menu);
					     	}
					}			
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resMenuList;
	}
	
	@RequestMapping("/editMainMenu")
	public void editMainMenu(AuthorityMenu menu, HttpServletResponse response)
			throws IOException {
		Map<String,Object> res = new HashMap<String,Object>();
		 try{	    
				int result = authMenuService.editAuthorityMenu(menu);
				if(result>0){
					MenuBean menuBean = AuthorityMenuToMenuBean(authMenuService.getAuthorityMenuByKey(menu.getMenuId()));
					if(menuBean!=null){
						res.put("menu",menuBean);
					}
					res.put("success","true");
					res.put("message",IConstant.MENU_EDITNODE_MESSAGE_SUCCESS);
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
				else{
					res.put("success","false");
					res.put("message",IConstant.MENU_EDITNODE_MESSAGE_FAILURE);
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
			
			}catch(Exception e){
				res.put("success","false");
				res.put("message",IConstant.MENU_EDITNODE_MESSAGE_FAILURE);
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));			
			}
	}
	
	@RequestMapping("/addMainMenu")
	public void addMainMenu(AuthorityMenu menu,String menuType, HttpServletResponse response)
			throws IOException {
		Map<String,Object> res = new HashMap<String,Object>();
		 try{
			    if(IConstant.MENU_NODE_TYPE_SIBLING.equals(menuType)){
			    	if(menu.getMenuId()== 0 ){
			    		res.put("success","false");
			    		res.put("message",IConstant.MENU_ADDNODE_MESSAGE_FAILURE);
			    		response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			    	}
			    	menu.setMenuId(null);
			    }else if(IConstant.MENU_NODE_TYPE_CHILD.equals(menuType)){
			    	menu.setParentId(menu.getMenuId());
			    	menu.setMenuId(null);
			    }
				int result = authMenuService.addAuthorityMenu(menu);
				if(result>0){
					//查询新增的菜单节点返回json对象给页面
					MenuBean menuBean = AuthorityMenuToMenuBean(authMenuService.queryAuthorityByName(menu.getName()));
					if(menuBean!=null){
						res.put("menu",menuBean);
					}
					res.put("success","true");
					res.put("message",IConstant.MENU_ADDNODE_MESSAGE_SUCCESS);
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
				else{
					res.put("success","false");
					res.put("message",IConstant.MENU_ADDNODE_MESSAGE_FAILURE);
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
			
			}catch(Exception e){
				res.put("success","false");
				res.put("message",IConstant.MENU_ADDNODE_MESSAGE_FAILURE);
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));			
			}
	}
	
	/**
	 * 递归获得子菜单
	 * 
	 * @param parent 当前菜单
	 * @return
	 */
	private List<MenuBean> setChildrenAuthorityMenu(MenuBean parent) {

		if (parent == null)
			return null;
		List<AuthorityMenu> children = authMenuService.getAuthorityMenuViewList(parent.getId());
		if (children != null && !children.isEmpty()) {
			parent.setChildren(AuthorityMenuToMenuBean(children));
			parent.setState(MenuBean.MENU_STATE_CLOSED);
			for (MenuBean menu : parent.getChildren()) {
				setChildrenAuthorityMenu(menu);
			}
			return parent.getChildren();
		} else {
			parent.setState(MenuBean.MENU_STATE_OPEN);
		}
		return null;

	}
	/**
	 * 递归获得子菜单
	 * 
	 * @param parent 当前菜单
	 * @return
	 */
	private List<MenuBean> setChildrenRoleMenu(MenuBean parent,List<Integer>  roleMenuList) {

		if (parent == null)
			return null;
		List<AuthorityMenu> children = authMenuService.getAuthorityMenuViewList(parent.getId());
		if (children != null && !children.isEmpty()) {
			parent.setChildren(AuthorityMenuToMenuBean(children));
			if(roleMenuList.contains(parent.getId()))
				parent.setChecked(true);
			else
				parent.setChecked(false);
			parent.setState(MenuBean.MENU_STATE_CLOSED);
			for (MenuBean menu : parent.getChildren()) {
				setChildrenRoleMenu(menu,roleMenuList);
			}
			return parent.getChildren();
		} else {
			parent.setState(MenuBean.MENU_STATE_OPEN);
		}
		return null;

	}
	/**
	 * AuthorityMenu转换为MenuBean
	 * @param authMenu
	 * @return
	 */
	private MenuBean AuthorityMenuToMenuBean(AuthorityMenu menu) {

		if (menu == null)
			return null;
	
			MenuBean bean = new MenuBean();
			bean.setId(menu.getMenuId());
			bean.setText(menu.getName());
			Map<String,String> attributes = new HashMap<String,String>(5);
			
			attributes.put("menuId", menu.getMenuId().toString());
			if(menu.getIndex()!=null)
				attributes.put("index", menu.getIndex().toString());
			if(menu.getParentId()!=null)
				attributes.put("parentId", menu.getParentId().toString());
			if(menu.getVisible()!=null)
				attributes.put("visible", menu.getVisible());
			if( StringUtils.isNotBlank(menu.getMenuURL())){			
				attributes.put("menuUrl", menu.getMenuURL());		
			}
			bean.setAttributes(attributes);
		return bean;
	}
	
	/**
	 * AuthorityMenu转换为MenuBean
	 * @param authMenu
	 * @return
	 */
	private List<MenuBean> AuthorityMenuToMenuBean(List<AuthorityMenu> authMenu) {

		if (authMenu == null || authMenu.isEmpty())
			return null;
		List<MenuBean> beanList = new ArrayList<MenuBean>(authMenu.size());
		for (AuthorityMenu menu : authMenu) {
			beanList.add(AuthorityMenuToMenuBean(menu));
		}
		return beanList;

	}

	@RequestMapping("/removeMainMenu")
	public void removeMainMenu(Integer menuId, HttpServletResponse response)
			throws IOException {
		 try{
				Map<String,String> res = new HashMap<String,String>();
				int result = authMenuService.removeAuthorityMenu(menuId);
				if(result>0){
					res.put("success","true");
					res.put("message",IConstant.MENU_REMOVENODE_MESSAGE_SUCCESS);
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
				else{	
					res.put("success","false");
					res.put("message",IConstant.MENU_REMOVENODE_MESSAGE_FAILURE);
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
				}
			
			}catch(Exception e){
				Map<String,String> res = new HashMap<String,String>();
				res.put("success","false");
				res.put("message",IConstant.MENU_REMOVENODE_MESSAGE_FAILURE);
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));		
			}
	}
	
	
	@RequestMapping("/saveRoleMenu")
	public void saveRoleMenu(String menuIds,Integer roleId, HttpServletResponse response)
			throws IOException {
		Map<String,Object> res = new HashMap<String,Object>();
		 try{
			    if(StringUtils.isBlank(menuIds)){
			    	res.put("success","false");
					res.put("message",IConstant.MENU_ADDNODE_MESSAGE_FAILURE);
					response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));
			    }
                String[]  menuList = menuIds.split(",");
                List<Integer>  roleMenuList = roleService.getMenuListByRoleId(roleId);
                List<String> tempList = new ArrayList<String>();
                List<Integer> newMenuList = new ArrayList<Integer>();
                Collections.addAll(tempList, menuList);
                for(String menuId:tempList){
                	Integer mid = Integer.parseInt(menuId);
                	newMenuList.add(mid);      	
                }
               //需要删除的菜单
                List<Integer> subList = new ArrayList<Integer>(); 
                subList.addAll(roleMenuList);
                subList.removeAll(newMenuList);
                //需要添加的菜单
                newMenuList.removeAll(roleMenuList);
                if(!subList.isEmpty()){
	                Map<String,Object> removeParams = new HashMap<String,Object>();
	                removeParams.put("roleId", roleId);
	                removeParams.put("list", subList);
				    authMenuService.removeRoleMenu(removeParams);
                }
                if(!newMenuList.isEmpty()){
				 Map<String,Object> saveParams = new HashMap<String,Object>();
				 saveParams.put("roleId", roleId);
				 saveParams.put("list", newMenuList);	 
				 authMenuService.addRoleMenu(saveParams);
                }					
				res.put("success","true");
				res.put("message",IConstant.MENU_ADDNODE_MESSAGE_SUCCESS);
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));				
			}catch(Exception e){
				res.put("success","false");
				res.put("message",IConstant.MENU_ADDNODE_MESSAGE_FAILURE);
				response.getOutputStream().write(GsonUtil.getGson().toJson(res).getBytes("UTF-8"));			
			}
	}

}
