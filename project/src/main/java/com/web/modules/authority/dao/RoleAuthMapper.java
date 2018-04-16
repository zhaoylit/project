package com.web.modules.authority.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RoleAuthMapper {
	List<Map<String,String>> getFunctionsByRoleId(Map params);
    //根据角色查询菜单
    List<Map> selectMenuByRole(@Param("roleId")Integer roleId) throws Exception;
    //查询所有的功能
	List<Map> getAllFunctions(Integer roleId) throws Exception;
	//查询角色列表
	List<Map> getRoleList(Map params) throws Exception;
	//查询角色列表数量
	int  getRoleListCount(Map params) throws Exception;
	//添加角色
	int addRole(Map params) throws Exception;
	//编辑角色
	int updateRoleById(Map params) throws Exception;
	//删除角色
	int deleteRoleById(String roleId) throws Exception;
	//根据角色删除权限
	int deleteAuthByRoleId(Integer roleId) throws Exception;
	//更新角色权限
	int addRoleAuth(Map params) throws Exception;
	//根据角色id查询不在选择的权限列表
	String getRoleAuthNotInSelect(Map params) throws Exception;
	//根据角色id删除不在选择的权限中的之前添加的权限
	int deleteRoleAuthNotInSelect(String ids) throws Exception;
}
