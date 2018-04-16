package com.zkkj.chat.service.impl;

import java.util.List;
import java.util.Map;

import com.zkkj.chat.dao.base.BaseDaoImpl;
import com.zkkj.chat.service.IGroupService;

public class GroupServiceImpl extends BaseDaoImpl implements IGroupService {

	@Override
	public List<Map> getGroupList(Map params) throws Exception {
		// TODO Auto-generated method stub
		return selectList("groupInfo.getGroupList",params);
	}

	@Override
	public List<Map> getGroupUserList(Map params) throws Exception {
		// TODO Auto-generated method stub
		return selectList("groupInfo.getGroupUserList", params);
	}

	@Override
	public int addGroup(Map params) throws Exception {
		// TODO Auto-generated method stub
		return insert("groupInfo.addGroup", params);
	}

	@Override
	public int deleteGroup(Map params) throws Exception {
		// TODO Auto-generated method stub
		return deleteByParam("groupInfo.deleteGroup", params);
	}

	@Override
	public int addGroupUser(Map params) throws Exception {
		// TODO Auto-generated method stub
		return insert("groupInfo.addGroupUser", params);
	}

	@Override
	public int deleteGroupUser(Map params) throws Exception {
		// TODO Auto-generated method stub
		return deleteByParam("groupInfo.deleteGroupUser", params);
	}

	@Override
	public int checkGroupUserIsExist(Map params) throws Exception {
		// TODO Auto-generated method stub
		return getCount("groupInfo.checkGroupUserExist", params);
	}

	@Override
	public Map getGroupById(Map params) throws Exception {
		// TODO Auto-generated method stub
		return (Map) selectOneByParam("groupInfo.getGroupById", params);
	}

	@Override
	public List<Map> getGroupbyUserId(Map params) throws Exception {
		// TODO Auto-generated method stub
		return selectList("groupInfo.getGroupByUserId",params);
	}

	@Override
	public List<Map> getAllUserByGroupId(Map params) throws Exception {
		// TODO Auto-generated method stub
		return selectList("groupInfo.getAllUserByGroupId", params);
	}

	@Override
	public int deleteAllGroupUserByGroupId(Map params) throws Exception {
		// TODO Auto-generated method stub
		return deleteByParam("groupInfo.deleteAllGroupUserByGroupId", params);
	}

}
