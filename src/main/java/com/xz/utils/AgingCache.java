package com.xz.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;


public class AgingCache implements InitializingBean{ 
    private static HashMap cacheMap = new HashMap(); 
 
//    //单实例构造方法 
//    public AgingCache() { 
//        super(); 
//    } 
 
    //得到缓存。同步静态方法 
    private synchronized static Cache getCache(String key) { 
        return (Cache) cacheMap.get(key); 
    } 
 
    //判断是否存在一个缓存 
    private synchronized static boolean hasCache(String key) { 
        return cacheMap.containsKey(key); 
    } 
 
    //清除所有缓存 
    public synchronized static void clearAll() { 
        cacheMap.clear(); 
    } 
 
    //清除指定的缓存 
    public synchronized static void remove(String key) { 
        cacheMap.remove(key); 
    } 
 
    //载入缓存 
    public synchronized static void putCache(String key, Cache obj) { 
        cacheMap.put(key, obj); 
    } 
 
    //获取缓存信息 
    public static Cache getCacheInfo(String key) { 
        if (hasCache(key)) { 
            Cache cache = getCache(key); 
            if (cacheExpired(cache)) { //调用判断是否终止方法 
            	return cache; 
            }else{
            	cacheMap.remove(key);
            	return null; 
            } 
        }else 
        	return null; 
    } 
 
    //载入缓存信息 
    public static void putCacheInfo(String key, Object obj,int mins) { 
        Cache cache = new Cache();
        cache.setKey(key); 
        cache.setTimeOut(mins); //设置多久后更新缓存 
        cache.setValue(obj); 
        cache.setCurrentTime(System.currentTimeMillis());
        cacheMap.put(key, cache); 
    } 
    
    //更新cache时间
    public static void updateCacheTimeOut(String key) {
        Cache cache = getCache(key); 
        cache.setCurrentTime(System.currentTimeMillis());
    } 
    
 
    //判断缓存是否终止 
    /**
     * 
     * @param cache
     * @return false:过期或者不存在，true：存在且正常
     */
    public static boolean cacheExpired(Cache cache) { 
        if (null == cache) { //传入的缓存不存在 
            return false; 
        } 
        long nowDt = System.currentTimeMillis(); //系统当前的毫秒数 
        long cacheDt = cache.getCurrentTime(); //缓存内的过期分钟数
        int mins = cache.getTimeOut();
        if ((cacheDt+mins*60*1000) < nowDt) { //过期时间大于当前时间时，则为TRUE 
            return false; 
        } else { //大于过期时间 即过期 
            return true; 
        } 
    } 
 
    //获取缓存中的大小 
    public static int getCacheSize() { 
        return cacheMap.size(); 
    } 
 
    //获取缓存对象中的所有键值名称 
    public static ArrayList getCacheAllkey() { 
        ArrayList a = new ArrayList(); 
        try { 
            Iterator i = cacheMap.entrySet().iterator(); 
            while (i.hasNext()) { 
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
                a.add((String) entry.getKey()); 
            } 
        } catch (Exception ex) {} finally { 
            return a; 
        } 
    } 
    public static class Cache { 
        private String key;//缓存ID 
        private Object value;//缓存数据 
        private int timeOut;//缓存保存分钟数 
        private long currentTime;//缓存保存时间 
        public Cache() { 
                super(); 
        } 

        public Cache(String key, Object value, int timeOut , long currentTime) { 
                this.key = key; 
                this.value = value; 
                this.timeOut = timeOut; 
                this.currentTime = currentTime; 
        }

    	public String getKey() {
    		return key;
    	}

    	public void setKey(String key) {
    		this.key = key;
    	}

    	public Object getValue() {
    		return value;
    	}

    	public void setValue(Object value) {
    		this.value = value;
    	}

    	public int getTimeOut() {
    		return timeOut;
    	}

    	public void setTimeOut(int timeOut) {
    		this.timeOut = timeOut;
    	}

    	public long getCurrentTime() {
    		return currentTime;
    	}

    	public void setCurrentTime(long currentTime) {
    		this.currentTime = currentTime;
    	} 

    }
	@Override
	public void afterPropertiesSet() throws Exception {
		Date d = new Date();
		int hours = d.getHours();
		int mins = d.getMinutes();
		// 计算延迟时间
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND,0);
		long delay = (cal.getTime().getTime()-System.currentTimeMillis())/1000;//当前时间与23:50的时间差，单位：秒
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				 try { 
			            Iterator i = cacheMap.entrySet().iterator(); 
			            while (i.hasNext()) { 
			                java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
			                getCacheInfo((String) entry.getKey()); 
			            } 
			        } catch (Exception ex) {} finally { 
			        } 
			}
		}, delay, 24 * 60 * 60, TimeUnit.SECONDS);
	} 
}