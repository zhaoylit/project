package com.zkkj.backend.service.base.authority;

import java.util.List;

import com.zkkj.backend.entity.AuthorityRole;

public interface AuthRoleService {

	public List<AuthorityRole> getAuthorityRoleList(AuthorityRole role);
	
	public List<AuthorityRole> getUserOwnedRoleList(Integer operatorId);

	public AuthorityRole getAuthorityRoleByKey(Integer roleId);

	public int addAuthorityRole(AuthorityRole role);

	public int editAuthorityRole(AuthorityRole role);

	public int removeAuthorityRole(Integer roleId);
	
	public List<Integer> getMenuListByRoleId(Integer roleId);
	
	public boolean addRolePrivilege(Integer roleId,Integer privilegeId);
	
	public boolean removeOwnedPrivilege(Integer roleId,Integer privilegeId);
	
	
}
