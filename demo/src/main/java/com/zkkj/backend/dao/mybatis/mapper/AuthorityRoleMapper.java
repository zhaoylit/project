package com.zkkj.backend.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.AuthorityRole;

public interface AuthorityRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(AuthorityRole record);

    int insertSelective(AuthorityRole record);

    AuthorityRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(AuthorityRole record);

    int updateByPrimaryKey(AuthorityRole record);
    
    List<AuthorityRole> queryAuthorityRoleList(AuthorityRole record);
    
    public List<Integer> queryMenuListByRoleId(Integer roleId);
    
    List<AuthorityRole> getUserOwnedRoleList(Integer operatorId);
    
    int insertRolePrivilege(Map<String,Object> params);
    
    int removeOwnedPrivilege(Map<String,Object> params);
    
    
}