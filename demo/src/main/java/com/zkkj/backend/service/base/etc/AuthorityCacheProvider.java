package com.zkkj.backend.service.base.etc;

import java.util.List;
import java.util.Map;

public interface AuthorityCacheProvider<K, V> {

	public V get(K key);

	public List<V> getForList(K key);

	public Map<Object, V> getForMap(K key);

	public void put(K key, V value);

	public void put(K key, List<V> values);

	public void put(K key, Map<Object, V> values);

}
