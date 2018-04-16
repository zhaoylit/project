package com.zkkj.backend.service.base.authority;

import java.util.List;

import com.zkkj.backend.entity.AuthorityPrivilege;

public interface AuthPrivilegeService {

	public List<AuthorityPrivilege> getAuthorityPrivilegeList(AuthorityPrivilege privilege);
	
	public List<AuthorityPrivilege> getRoleOwnedPrivilegeList(Integer roleId);
	
	public List<AuthorityPrivilege> getPrivilegeListByRoleId(Integer roleId);

	public AuthorityPrivilege getAuthorityPrivilegeByKey(Integer privilegeId);

	public int addAuthorityPrivilege(AuthorityPrivilege privilege);

	public int editAuthorityPrivilege(AuthorityPrivilege privilege);

	public int removeAuthorityPrivilege(Integer privilegeId);
	
}
