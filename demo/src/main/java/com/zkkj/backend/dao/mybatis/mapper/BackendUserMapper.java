package com.zkkj.backend.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.BackendUser;

public interface BackendUserMapper {
	
    int deleteByPrimaryKey(Integer operatorId);

    int insert(BackendUser record);

    int insertSelective(BackendUser record);

    BackendUser selectByPrimaryKey(Integer operatorId);

    int updateByPrimaryKeySelective(BackendUser record);

    int updateByPrimaryKey(BackendUser record);
    
    List<BackendUser> queryBackendUserList(BackendUser record);
    
    int insertUserRole(Map<String,Object> params);
    
}