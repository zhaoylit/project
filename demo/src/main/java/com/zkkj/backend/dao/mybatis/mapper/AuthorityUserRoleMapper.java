package com.zkkj.backend.dao.mybatis.mapper;

import java.util.List;

import com.zkkj.backend.entity.AuthorityUserRole;

public interface AuthorityUserRoleMapper {
	
	List<AuthorityUserRole> queryAuthorityUserRoleList(Integer operatorId);
	
    int insert(AuthorityUserRole record);

    int insertSelective(AuthorityUserRole record);
}