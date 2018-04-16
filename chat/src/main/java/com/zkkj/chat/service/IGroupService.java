package com.zkkj.chat.service;

import java.util.List;
import java.util.Map;

public interface IGroupService {
	//查询所哟的群组列表
	List<Map> getGroupList(Map params) throws Exception;
	//查询群组里的成员列表
	List<Map> getGroupUserList(Map params) throws Exception;
	//根据群组id查询单条群组信息
	Map getGroupById(Map params) throws Exception;
	//创建群组
	int addGroup(Map params) throws Exception;
	//删除群组
	int deleteGroup(Map params) throws Exception;
	//添加群组成员
	int addGroupUser(Map params) throws Exception;
	//删除群组成员
	int deleteGroupUser(Map params) throws Exception;
	//检测群组某个成员是否存在
	int checkGroupUserIsExist(Map params) throws Exception;
	//通过用户ID查询该用户拥有的群主
	List<Map> getGroupbyUserId(Map params) throws Exception;
	//根据群组id查询素有的用户列表
	List<Map> getAllUserByGroupId(Map params) throws Exception;
	//删除群组的所有用户
	int deleteAllGroupUserByGroupId(Map params) throws Exception;
	
}
