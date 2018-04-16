package com.zkkj.backend.service.base.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.zkkj.backend.entity.AuthorityPrivilege;
import com.zkkj.backend.entity.AuthorityRole;
import com.zkkj.backend.entity.AuthorityUserRole;
import com.zkkj.backend.entity.biz.ZkkjUser;
import com.zkkj.backend.service.base.authority.AuthPrivilegeService;
import com.zkkj.backend.service.base.authority.AuthRoleService;

public class HttpAuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	private BackendUserService userService;
	@Autowired
	AuthPrivilegeService privilegeService;
	@Autowired
	AuthRoleService roleService;
	

	@Override
	public boolean authenticate(Subject subject) {
		
		if (subject == null)
			return false;
		try {
			Map<String, Object> param = subject.getParam();
			if (param == null) {

				return false;
			}
			HttpSession session = (HttpSession) param.get(Subject.AUTH_SESSION);
			if (session == null || session.getAttribute(Subject.AUTH_SUBJECT) == null) {
				subject.setResult(Subject.AUTH_RESULT_NOLOGIN);
				return false;
			}else{
				subject.setAuthenticated(true);
				subject.setResult(Subject.AUTH_RESULT_ALLOW);
				return true;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return false;
		}

	}

	@Override
	public List<ZkkjUser> login(Subject subject) {
		try{
			
			Map<String, Object> param = subject.getParam();
			HttpSession session = (HttpSession) param.get(Subject.AUTH_SESSION);
			String username = (String)param.get(Subject.AUTH_USERNAME);
			String password = (String)param.get(Subject.AUTH_PASSWORD);
		
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			ZkkjUser user = new ZkkjUser();
			user.setAccount(username);
			user.setPassWord(password);
			List<ZkkjUser> userList = userService.getBackendUserList(user);
			if (userList!=null&&!userList.isEmpty()) {
				user = userList.get(0);
				subject.isAuthenticated();
				subject.setResult(Subject.AUTH_RESULT_ALLOW);
				subject.setPrincipals(this.getPrincipals(user.getId()));
				session.setAttribute(Subject.AUTH_USERNAME, username);
				if(!StringUtils.isBlank(userList.get(0).getProvince()))
					session.setAttribute("USER_LOCATION", userList.get(0).getProvince());  //判断是哪里的用户，针对新疆等地区有特别处理
				if (session != null) {
					session.setAttribute(Subject.AUTH_SUBJECT, subject);
				}
				return userList;
			} else {
				subject.setResult(Subject.AUTH_RESULT_NOLOGIN);
				return null;
			}
		}
		}catch(Exception e){
			
			
		}
		return null;
	}

	@Override
	public boolean logout(Subject subject) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Principal> getPrincipals(Integer operatorId){
			
		 List<AuthorityUserRole> userRoles = userService.getAuthorityUserRole(operatorId);
		 List<Principal> principalList = new ArrayList<Principal>();
		 for(AuthorityUserRole userRole:userRoles){
			 DefaultlePrincipalImpl principal = new DefaultlePrincipalImpl();
			 principal.setRoleId(userRole.getRoleId());
			 AuthorityRole  role = roleService.getAuthorityRoleByKey(userRole.getRoleId());
			 principal.setRoleAlias(role.getAlias());
			 principal.setRoleName(role.getName());
			 //角色拥有的权限
			 List<AuthorityPrivilege> tempList = privilegeService.getPrivilegeListByRoleId(userRole.getRoleId());
			 if(!tempList.isEmpty()){								 
				 principal.setPrivilegeList(tempList);			 
			 };	 
			 //角色可看到的菜单
			 List<Integer>  menuList = roleService.getMenuListByRoleId(userRole.getRoleId());
			 if(!menuList.isEmpty()){
				 principal.setMenuList(menuList);				 
			 }
			 if(principal.getMenuList()!=null||principal.getPrivilegeList()!=null)
				 principalList.add(principal);
		 }
        return principalList;
	}
}
