package com.maywide.core.util;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {
	private static final String cacheName = "uapCache";
	private static final String expireName = "expire";
	private static final String accountName = "account";
	private static final String path = "/ehcache-config.xml";
	private static Cache cache;
	private static Cache expireCache;
	private static Cache accountCache;
	private static EhcacheUtil instance;
	
	private EhcacheUtil() {
		URL url = getClass().getResource(path);
		CacheManager manager = CacheManager.create(url);
		cache = manager.getCache(cacheName);
		expireCache = manager.getCache(expireName);
		accountCache = manager.getCache(accountName);
	}
	
	public static EhcacheUtil getInstance() {
		if (instance == null) {
			synchronized (EhcacheUtil.class) {
				if(instance == null){
					instance = new EhcacheUtil();
				}
			}
		}
		
		return instance;
	}
	
	public Cache getCache() {
		return cache;
	}
	
	private Cache getExipreCache(){
		return expireCache;
	}
	
	private Cache getAccountCache(){
		return accountCache;
	}
	
	public static void put(Object key, Object value) {
		Element element = new Element(key, value);
		getInstance().getCache().put(element);
	}
	
	public static Object get(Object key) {
		Element element = getInstance().getCache().get(key);
		if (element == null) return null;
		return element.getObjectValue();
	}
	
	public static void putExipre(Object key,Object value){
		Element element = new Element(key, value);
		getInstance().getExipreCache().put(element);
	}
	
	public static Object getExipre(Object key){
		Element element = getInstance().getExipreCache().get(key);
		if(element == null) return null;
		return element.getObjectValue();
	}
	
	public static void putAccount(Object key,Object value){
		Element element = new Element(key, value);
		getInstance().getAccountCache().put(element);
	}
	
	public static Object getAccount(Object key){
		Element element = getInstance().getAccountCache().get(key);
		if (element == null) return null;
		return element.getObjectValue();
	}
}
