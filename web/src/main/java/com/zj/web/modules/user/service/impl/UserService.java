package com.zj.web.modules.user.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.formula.functions.Rows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.pagination.Page;
import com.zj.web.modules.user.bean.User;
import com.zj.web.modules.user.dao.UserMapper;
import com.zj.web.modules.user.service.IUserService;

@Service("userService")
public class UserService implements IUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Map> selectList(Map params) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectList(params);
	}

	@Override
	public List<Map> selectList(Map params, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectList(params,new RowBounds(page.getStartItem(), page.getNumPerPage()));
	}

	@Override
	public Map selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int selectCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectCount(params);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Map params) {
		// TODO Auto-generated method stub
		return userMapper.insert(params);
	}

	@Override
	public int updateByPrimaryKeySelective(Map params) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(params);
	}

}
