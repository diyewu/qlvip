package com.xz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 系统初始化加载时调用的方法，用于初始化字典配置、缓存、环境等
 */

@Component
@Order(value = 1)  // @Order 注解的执行优先级是按value值从小到大顺序
public class MyStartupRunner implements CommandLineRunner {
	
	private static Log logger = LogFactory.getLog(MyStartupRunner.class);
	
	@Override
	public void run(String... args) throws Exception {
		
		logger.info("系统开始启动... ...");
		
		logger.info("系统的临时目录为:  " + System.getProperty("java.io.tmpdir"));
		
		/**
		 * 在这里初始化缓存数据
		 */
		
		System.out.println("恭喜你！系统启动完毕！！！");
	}

}
