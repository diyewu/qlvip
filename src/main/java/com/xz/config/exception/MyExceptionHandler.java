package com.xz.config.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理配置
 */
@Configuration
public class MyExceptionHandler implements HandlerExceptionResolver {
	
	private final static Log logger = LogFactory.getLog(MyExceptionHandler.class);

	/**
	 * 重写解析异常的处理
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);  
        response.setStatus(601); // 统一做跳转到登录页的处理
        // 根据不同错误转向不同页面  
        if(ex instanceof NumberFormatException) { 
        	logger.error("监听到了NumberFormat异常");
        	ex.printStackTrace();
            return new ModelAndView("/login", model);  
        }else if(ex instanceof NullPointerException) {  
        	logger.error("监听到了空指针异常");
        	ex.printStackTrace();
            return new ModelAndView("/login", model);  
        } else {  
        	logger.error("监听到了 other");
        	ex.printStackTrace();
            return new ModelAndView("/login", model);  
        }
	}
	
	
	

}
