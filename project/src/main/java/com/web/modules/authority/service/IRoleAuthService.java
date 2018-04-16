package com.web.modules.authority.service;

import java.util.List;
import java.util.Map;

public interface IRoleAuthService {
	//查询角色列表
	List<Map> getRoleList(Map params) throws Exception;
	//查询角色列表数量
	int  getRoleListCount(Map params) throws Exception;
	//根据角色id查询功能树checkbox
	List<Map> getAllAuthByRole(Map params) throws Exception;
	//添加或者编辑角色
	String addOrEditRole(Map params) throws Exception;
	//删除角色
	String deleteRole(Map params) throws Exception;
	//更新用户权限信息
	String updateRoleAuth(Map params) throws Exception;
}
