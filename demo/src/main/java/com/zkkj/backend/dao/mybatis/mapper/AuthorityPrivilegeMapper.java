package com.zkkj.backend.dao.mybatis.mapper;

import java.util.List;

import com.zkkj.backend.entity.AuthorityPrivilege;
import com.zkkj.backend.entity.AuthorityRole;

public interface AuthorityPrivilegeMapper {
    int deleteByPrimaryKey(Integer privilegeId);

    int insert(AuthorityPrivilege record);

    int insertSelective(AuthorityPrivilege record);

    AuthorityPrivilege selectByPrimaryKey(Integer privilegeId);

    int updateByPrimaryKeySelective(AuthorityPrivilege record);

    int updateByPrimaryKey(AuthorityPrivilege record);
    
    List<AuthorityPrivilege> queryAuthorityPrivilegeList(AuthorityPrivilege record);
    
    List<AuthorityPrivilege> getPrivilegeListByRoleId(Integer roleId);
    
    List<AuthorityPrivilege> getRoleOwnedPrivilegeList(Integer roleId);
}