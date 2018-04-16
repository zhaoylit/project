package com.web.modules.authority.service;

import java.util.List;
import java.util.Map;

public interface IAuthorityService {

	//根据角色id查询所有的功能
	List<Map<String, String>> getFunctionsByRoleId(Map params) throws Exception; 
	//根据角色id查询菜单
	String getMenuByRole(Integer roleId) throws Exception;

}
