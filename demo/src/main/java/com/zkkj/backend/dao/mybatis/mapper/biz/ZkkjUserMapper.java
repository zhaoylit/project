package com.zkkj.backend.dao.mybatis.mapper.biz;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.biz.ZkkjUser;

public interface ZkkjUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZkkjUser record);

    int insertSelective(ZkkjUser record);

    ZkkjUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZkkjUser record);

    int updateByPrimaryKey(ZkkjUser record);
    
    List<ZkkjUser> queryBackendUserList(ZkkjUser record);
    
    int insertUserRole(Map<String,Object> params);
    
    int removeOwnedRole(Map<String,Object> params);
    
}