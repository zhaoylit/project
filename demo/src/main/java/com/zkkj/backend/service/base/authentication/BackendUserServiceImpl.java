package com.zkkj.backend.service.base.authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.AuthorityUserRoleMapper;
import com.zkkj.backend.dao.mybatis.mapper.biz.ZkkjUserMapper;
import com.zkkj.backend.entity.AuthorityUserRole;
import com.zkkj.backend.entity.biz.ZkkjUser;
import com.zkkj.backend.service.base.etc.AuthorityCacheProvider;

@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {

	@Autowired
	ZkkjUserMapper ZkkjUserMapper;
	@Autowired
	AuthorityUserRoleMapper authorityUserRoleMapper;
	@Resource
	AuthorityCacheProvider<Integer, AuthorityUserRole> userRoleCacheProvider;

	@Override
	public List<ZkkjUser> getBackendUserList(ZkkjUser user) {
		try{
				List<ZkkjUser> menuList = ZkkjUserMapper
						.queryBackendUserList(user);
				return menuList;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public ZkkjUser getBackendUserByKey(Integer userId) {

		return ZkkjUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int addBackendUser(ZkkjUser user) {

		return ZkkjUserMapper.insert(user);
	}

	@Override
	public int editBackendUser(ZkkjUser user) {
		return ZkkjUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int removeBackendUser(Integer userId) {
		return ZkkjUserMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public List<AuthorityUserRole> getAuthorityUserRole(Integer operatorId) {

		List<AuthorityUserRole> userRoleList = null;
		if (operatorId == null)
			return null;
		userRoleList = userRoleCacheProvider.getForList(operatorId);
		if (userRoleList == null) {
			userRoleList = authorityUserRoleMapper
					.queryAuthorityUserRoleList(operatorId);
			if (userRoleList != null) {
				userRoleCacheProvider.put(operatorId, userRoleList);
			}
			return userRoleList;
		} else
			return userRoleList;

	}

	@Override
	public boolean addUserRole(Integer operatorId, Integer roleId) {
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("operatorId", operatorId);
			params.put("roleId", roleId);
			int res = ZkkjUserMapper.insertUserRole(params);

			if (res > 0)
				return true;
		} catch (Exception e) {
            e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeOwnedRole(Integer operatorId, Integer roleId) {
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("operatorId", operatorId);
			params.put("roleId", roleId);
			int res = ZkkjUserMapper.removeOwnedRole(params);

			if (res > 0)
				return true;
		} catch (Exception e) {
            e.printStackTrace();
		}
		return false;
	}
	
}
