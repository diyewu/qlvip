package com.xz.config.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * ehcache 缓存配置
 */

@Configuration
@EnableCaching // 标注启动缓存
public class CacheConfiguration {

	
	/**
	 * ehcache 主要的管理器
	 * @param bean
	 * @return
	 */
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
		return new EhCacheCacheManager(bean.getObject());
	}
	
	/**
	 * 缓存工厂bean
	 * @return
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
//		指定xml配置文件
		factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		factoryBean.setShared(true);
		
		return factoryBean;
	}
	
	
}
