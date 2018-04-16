package com.zkkj.backend.service.base.authentication;

import java.util.List;

import com.zkkj.backend.entity.AuthorityPrivilege;

public class DefaultlePrincipalImpl implements Principal{

	private Integer roleId;
	private String roleName;
	private String roleAlias;
	
	private List<Integer> menuList;
	private  List<AuthorityPrivilege> privilegeList;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public List<AuthorityPrivilege> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<AuthorityPrivilege> privilegeList) {
		this.privilegeList = privilegeList;
	}
	public List<Integer> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Integer> menuList) {
		this.menuList = menuList;
	}
	@Override
	public String getRoleName() {

		return null;
	}
	@Override
	public String getRoleAlias() {

		return null;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setRoleAlias(String roleAlias) {
		this.roleAlias = roleAlias;
	}
	
	
}
