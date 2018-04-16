package com.zkkj.backend.service.base.authentication;

import java.util.List;

import com.zkkj.backend.entity.AuthorityPrivilege;

public interface Principal {
	
	public String getRoleName();
	
	public String getRoleAlias();
	
	public List<AuthorityPrivilege> getPrivilegeList();
	
	public List<Integer> getMenuList();

}
