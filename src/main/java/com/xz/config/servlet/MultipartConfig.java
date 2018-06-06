package com.xz.config.servlet;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传配置，spring默认文件限制大小为1MB，即tomcat的限制
 */
@Configuration
public class MultipartConfig {

	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("10240MB");
		factory.setMaxRequestSize("10240MB");
		return factory.createMultipartConfig();
	}
	
}
