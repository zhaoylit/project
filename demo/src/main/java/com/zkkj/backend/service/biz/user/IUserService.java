package com.zkkj.backend.service.biz.user;

import java.util.List;
import java.util.Map;


public interface IUserService {
	//查询用户列表
	List<Map<String, String>> getUserList(Map params) throws Exception;
	//查询用户列表数量
	int getUserListCount(Map params) throws Exception;
	//添加或者更新用户信息
	Map addOrEditUser(Map params) throws Exception;
	//删除用户
	Map deleteUser(Map params) throws Exception;
	//账户冻结
	Map updateUserFrozen(Map params) throws Exception;
	//账户解冻
	Map updateAccountThaw(Map params) throws Exception;
	//查询单条用户信息
	Map getUserById(String rowKey) throws Exception;
	//修改密码
	Map updatapwd(Map params) throws Exception;
	//用户登录
	List<Map<String, String>> loginOne(Map params) throws Exception;
	//根据用户名查询单条用户信息
	List<Map<String, String>> UserFrozen(Map params) throws Exception;
	//冻结单条用户
	Map UserFrozen(String params) throws Exception;

	
}
