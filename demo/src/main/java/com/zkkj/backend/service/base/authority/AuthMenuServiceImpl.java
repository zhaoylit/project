package com.zkkj.backend.service.base.authority;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.AuthorityMenuMapper;
import com.zkkj.backend.entity.AuthorityMenu;

@Service("authMenuService")
public class AuthMenuServiceImpl implements AuthMenuService{
	
	@Autowired
	AuthorityMenuMapper authorityMenuMapper;

	@Override
	public List<AuthorityMenu> getAuthorityMenuViewList(Integer parentId) {
		
		List<AuthorityMenu> menuList = authorityMenuMapper.queryAuthorityMenuList(parentId);
		return menuList;
	}
	
	@Override
	public AuthorityMenu getAuthorityMenuByKey(Integer menuId) {
		
		return authorityMenuMapper.selectByPrimaryKey(menuId);
	}

	@Override
	public int addAuthorityMenu(AuthorityMenu menu) {
		return authorityMenuMapper.insertSelective(menu);
	}

	@Override
	public int editAuthorityMenu(AuthorityMenu menu) {
		return authorityMenuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public int removeAuthorityMenu(Integer menuId) {
		return authorityMenuMapper.deleteByPrimaryKey(menuId);
	}

	@Override
	public AuthorityMenu queryAuthorityByName(String name) {
		return authorityMenuMapper.queryAuthorityByName(name);
	}

	@Override
	public int addRoleMenu(Map<String,Object> params) {
		return authorityMenuMapper.insertRoleMenu(params);
	}

	@Override
	public int removeRoleMenu(Map<String,Object> params) {
		return authorityMenuMapper.deleteRoleMenu(params);
	}
	
}
