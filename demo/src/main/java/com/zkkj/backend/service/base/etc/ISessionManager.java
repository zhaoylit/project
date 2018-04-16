package com.zkkj.backend.service.base.etc;
public interface ISessionManager {

	public Object get(Object key);

	public Object get(Object key, Class<Object> type);

	public void put(Object key, Object value);

	public void remove(Object key);

	public void clear();

}
