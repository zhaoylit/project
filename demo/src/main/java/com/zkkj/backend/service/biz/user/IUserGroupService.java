package com.zkkj.backend.service.biz.user;

import java.util.List;
import java.util.Map;

public interface IUserGroupService {
	//查询用户组列表
	List<Map<String, String>> getUserGroupsList(Map params) throws Exception;
	//添加或者更新用户组信息
	Map addOrEditUserGroups(Map params) throws Exception;
	//删除用户组
	Map deleteUserGroups(Map params) throws Exception;
	//查询单条用户组信息
	Map getUserGroupsById(String rowKey) throws Exception;
	//用户修改用户组
	Map userAddUserGroups(Map params) throws Exception;
	//根据用户id查关联用户组
	public List<Map<String, String>> getUserGroupListInfo(String userId) throws Exception;
	//删除用户拥有的用户组
	Map deleteUserGroup(Map params) throws Exception;

			
}
