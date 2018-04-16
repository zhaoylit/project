package com.zkkj.chat.service;

import java.util.List;
import java.util.Map;

public interface IUserService {
	List<Map> getUserList(Map params) throws Exception;
	Map getUserById(Map params) throws Exception;
	int updateUser(Map params) throws Exception;
	int addUser(Map params) throws Exception;
	void timerUpdatePWD() throws Exception;
	String updateUserPWDOne(String userId) throws Exception;
} 
