package com.zkkj.backend.service.base.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.AuthorityPrivilegeMapper;
import com.zkkj.backend.entity.AuthorityPrivilege;

@Service("authPrivilegeService")
public class AuthPrivilegeServiceImpl implements AuthPrivilegeService{
	
	@Autowired
	AuthorityPrivilegeMapper authorityPrivilegeMapper;
	@Autowired
	private AuthRoleService authRoleService;

	@Override
	public List<AuthorityPrivilege> getAuthorityPrivilegeList(AuthorityPrivilege privilege) {
		
		return authorityPrivilegeMapper.queryAuthorityPrivilegeList(privilege);

	}
	
	@Override
	public List<AuthorityPrivilege> getPrivilegeListByRoleId(Integer roleId) {
		
		return authorityPrivilegeMapper.getPrivilegeListByRoleId(roleId);

	}
	
	@Override
	public AuthorityPrivilege getAuthorityPrivilegeByKey(Integer privilegeId) {
		
		return authorityPrivilegeMapper.selectByPrimaryKey(privilegeId);
	}

	@Override
	public int addAuthorityPrivilege(AuthorityPrivilege privilege) {
		
		return authorityPrivilegeMapper.insert(privilege);
	}

	@Override
	public int editAuthorityPrivilege(AuthorityPrivilege privilege) {
		return authorityPrivilegeMapper.updateByPrimaryKeySelective(privilege);
	}

	@Override
	public int removeAuthorityPrivilege(Integer privilegeId) {
		return authorityPrivilegeMapper.deleteByPrimaryKey(privilegeId);
	}

	@Override
	public List<AuthorityPrivilege> getRoleOwnedPrivilegeList(Integer roleId) {
		return authorityPrivilegeMapper.getRoleOwnedPrivilegeList(roleId);
	}

	
}
