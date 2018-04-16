package com.zj.web.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.zj.web.modules.user.bean.User;

public interface UserMapper {
	List<Map> selectList(Map params) throws Exception;
	
	List<Map> selectList(Map params,RowBounds rowBounds) throws Exception;
	
	Map selectByPrimaryKey(Integer id);
	
	int selectCount(Map params) throws Exception;
	
    int deleteByPrimaryKey(Integer id);

    int insert(Map params);

    int updateByPrimaryKeySelective(Map params);
}