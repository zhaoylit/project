package com.zkkj.backend.service.base.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.AuthorityRoleMapper;
import com.zkkj.backend.entity.AuthorityRole;

@Service("authRoleService")
public class AuthRoleServiceImpl implements AuthRoleService{
	
	@Autowired
	AuthorityRoleMapper authorityRoleMapper;
	
    
	@Override
	public List<AuthorityRole> getAuthorityRoleList(AuthorityRole role) {
		
		List<AuthorityRole> menuList = authorityRoleMapper.queryAuthorityRoleList(role);
		return menuList;
	}
	
	@Override
	public AuthorityRole getAuthorityRoleByKey(Integer roleId) {
		
		return authorityRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int addAuthorityRole(AuthorityRole role) {
		
		return authorityRoleMapper.insert(role);
	}

	@Override
	public int editAuthorityRole(AuthorityRole role) {
		return authorityRoleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public int removeAuthorityRole(Integer roleId) {
		return authorityRoleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public List<Integer> getMenuListByRoleId(Integer roleId) {
		return authorityRoleMapper.queryMenuListByRoleId(roleId);
	}

	@Override
	public List<AuthorityRole> getUserOwnedRoleList(Integer operatorId) {
	
		return authorityRoleMapper.getUserOwnedRoleList(operatorId);
	}

	@Override
	public boolean addRolePrivilege(Integer roleId, Integer privilegeId) {
		
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("privilegeId", privilegeId);
			int res = authorityRoleMapper.insertRolePrivilege(params);

			if (res > 0)
				return true;
		} catch (Exception e) {
            e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeOwnedPrivilege(Integer roleId, Integer privilegeId) {
		
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("privilegeId", privilegeId);
			int res = authorityRoleMapper.removeOwnedPrivilege(params);

			if (res > 0)
				return true;
		} catch (Exception e) {
            e.printStackTrace();
		}
		return false;
	}	
}
