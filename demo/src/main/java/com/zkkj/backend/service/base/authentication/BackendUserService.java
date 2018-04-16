package com.zkkj.backend.service.base.authentication;

import java.util.List;

import com.zkkj.backend.entity.AuthorityUserRole;
import com.zkkj.backend.entity.biz.ZkkjUser;

public interface BackendUserService {

	public List<ZkkjUser> getBackendUserList(ZkkjUser user);

	public ZkkjUser getBackendUserByKey(Integer userId);

	public int addBackendUser(ZkkjUser user);

	public int editBackendUser(ZkkjUser user);

	public int removeBackendUser(Integer userId);
	
	public List<AuthorityUserRole> getAuthorityUserRole(Integer operatorId);
	
	public boolean addUserRole(Integer operatorId,Integer roleId);
	
	public boolean removeOwnedRole(Integer operatorId,Integer roleId);
	
}
