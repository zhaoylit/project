package com.zkkj.backend.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.AuthorityMenu;

public interface AuthorityMenuMapper {
	
    int deleteByPrimaryKey(Integer menuId);

    int insert(AuthorityMenu record);

    int insertSelective(AuthorityMenu record);

    AuthorityMenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(AuthorityMenu record);

    int updateByPrimaryKey(AuthorityMenu record);
    
    List<AuthorityMenu> queryAuthorityMenuList(Integer parentId);
    
    AuthorityMenu queryAuthorityByName(String name);
    
    int insertRoleMenu(Map<String,Object> params);
    
    int deleteRoleMenu(Map<String,Object> params);
    
}