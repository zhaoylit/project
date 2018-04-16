package com.web.modules.authority.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.modules.authority.dao.RoleAuthMapper;
import com.web.modules.authority.service.IAuthorityService;
import com.web.util.TreeMenuUtil;

@Service("authorityService")
public class AuthorityServiceImpl implements IAuthorityService {
	@Autowired
	private RoleAuthMapper roleAuthMapper;
	
	@Override
	public String getMenuByRole(Integer roleId) throws Exception {
		// TODO Auto-generated method stub
		
		List<Map> menuList = roleAuthMapper.selectMenuByRole(roleId);
		
		String html = new TreeMenuUtil(menuList).buildMenuTree();
		return html;
	}

	@Override
	public List<Map<String, String>> getFunctionsByRoleId(Map params)
			throws Exception {
		// TODO Auto-generated method stub
		return roleAuthMapper.getFunctionsByRoleId(params);
	}

}
