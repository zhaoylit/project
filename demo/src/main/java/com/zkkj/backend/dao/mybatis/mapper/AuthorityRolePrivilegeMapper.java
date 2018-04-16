package com.zkkj.backend.dao.mybatis.mapper;

import com.zkkj.backend.entity.AuthorityRolePrivilege;

public interface AuthorityRolePrivilegeMapper {
		
    int insert(AuthorityRolePrivilege record);

    int insertSelective(AuthorityRolePrivilege record);
}