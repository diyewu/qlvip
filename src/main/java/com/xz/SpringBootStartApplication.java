package com.xz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 *
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootStartApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
	
//	public static void main(String[] args) {
//		SpringApplication.run(SpringBootStartApplication.class, args);
//	}

}
