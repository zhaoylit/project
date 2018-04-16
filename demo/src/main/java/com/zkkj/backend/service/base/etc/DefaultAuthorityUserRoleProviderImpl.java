package com.zkkj.backend.service.base.etc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zkkj.backend.entity.AuthorityUserRole;

public class DefaultAuthorityUserRoleProviderImpl implements
		AuthorityCacheProvider<Object, AuthorityUserRole> {

	private ConcurrentHashMap<Object, AuthorityUserRole> mapCache;
	
	private ConcurrentHashMap<Object, List<AuthorityUserRole>> mapListCache;
	
	private ConcurrentHashMap<Object, Map<Object, AuthorityUserRole>> mapMapCache;

	@Override
	public AuthorityUserRole get(Object key) {
		if (mapCache == null)
			mapCache = new ConcurrentHashMap<Object, AuthorityUserRole>();
		return mapCache.get(key);
	}

	@Override
	public void put(Object key, AuthorityUserRole value) {

		if (mapCache == null)
			mapCache = new ConcurrentHashMap<Object, AuthorityUserRole>();
		mapCache.put(key, value);
	}

	@Override
	public void put(Object key, List<AuthorityUserRole> values) {
		if (mapListCache == null)
			mapListCache = new ConcurrentHashMap<Object, List<AuthorityUserRole>>();
		mapListCache.put(key, values);

	}

	@Override
	public void put(Object key, Map<Object, AuthorityUserRole> values) {
		if (mapMapCache == null)
			mapMapCache = new ConcurrentHashMap<Object, Map<Object, AuthorityUserRole>>();
		mapMapCache.put(key, values);

	}

	@Override
	public List<AuthorityUserRole> getForList(Object key) {

		if (mapListCache == null)
			return null;
		return mapListCache.get(key);
	}

	@Override
	public Map<Object, AuthorityUserRole> getForMap(Object key) {

		if (mapMapCache == null)
			return null;
		return mapMapCache.get(key);
	}

}
