package com.zkkj.backend.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.zkkj.backend.service.base.etc.ISessionManager;

public class EhcacheSessionManager implements ISessionManager {

	private String cacheName = "sessionCache";

	@Autowired
	private EhCacheCacheManager cacheManager;

	@Override
	public void put(Object key, Object value) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			cache.put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object get(Object key) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			ValueWrapper value = cache.get(key);
			if(value!=null)
				return value.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object get(Object key, Class<Object> type) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			return cache.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove(Object key) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			cache.evict(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clear() {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			cache.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

}
