package com.zyl.export.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

public class EcacheUtil {
    private static final int TIME_TO_IDLE_SECONDS = 0;
    static CacheManager manager = CacheManager.create();
    public EcacheUtil(String cacheName, int cacheSize, int ttl) {
        if(manager.getCache(cacheName) == null){
            CacheConfiguration config = new CacheConfiguration();
            config.setName(cacheName);
            config.setMaxElementsInMemory(cacheSize);
            config.setCopyOnRead(true);
            config.setCopyOnWrite(true);
            config.setTimeToLiveSeconds(ttl);
            config.setTimeToIdleSeconds(TIME_TO_IDLE_SECONDS);
            Cache store = new Cache(config);
            manager.addCache(store);  
        }
    } 
    public Object get(String cacheName,String key){
        Cache cache = getCache(cacheName);
        Element e = cache.get(key);  
        if ( e!= null ) {  
            return e.getValue();  
        }  
        return null;  
    }
    public void put(String cacheName,String key,Object value) {  
        Cache cache = getCache(cacheName);
        Element element = new Element(key, value);   
        cache.put(element);  
    }  
  
    public boolean remove(String cacheName,String key) {  
        Cache cache = getCache(cacheName);
        return cache.remove(key);  
    }
    public Cache getCache(String cacheName){
        return manager.getCache(cacheName);
    }
    
}
