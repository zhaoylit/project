package com.web.modules.user.dao;

import java.util.List;
import java.util.Map;

import com.web.modules.user.bean.User;

public interface UserMapper {
	List<Map> selectByParams(Map params);
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}