package com.zkkj.backend.service.base.authority;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.AuthorityMenu;

public interface AuthMenuService {

	public List<AuthorityMenu> getAuthorityMenuViewList(Integer parentId);

	public AuthorityMenu getAuthorityMenuByKey(Integer menuId);

	public int addAuthorityMenu(AuthorityMenu menu);

	public int editAuthorityMenu(AuthorityMenu menu);

	public int removeAuthorityMenu(Integer menuId);
	
	public AuthorityMenu queryAuthorityByName(String name);
	
	public int addRoleMenu(Map<String,Object> params);
	
	public int removeRoleMenu(Map<String,Object> params);	

}
