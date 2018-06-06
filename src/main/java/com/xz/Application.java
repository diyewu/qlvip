package com.xz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot标准启动方式，执行main方法即可
 */
@EnableAutoConfiguration
@SpringBootApplication // 必须标明
@ComponentScan // 开启通用注解扫描	
@ServletComponentScan // 扫描使用注解方式的servlet 
@EnableAsync // 支持异步请求
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

