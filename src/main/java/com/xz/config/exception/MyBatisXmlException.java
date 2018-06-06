package com.xz.config.exception;

import java.io.IOException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.NestedIOException;

public class MyBatisXmlException extends org.mybatis.spring.SqlSessionFactoryBean {
	public SqlSessionFactory logException() throws IOException{
	    try {  
	        return super.buildSqlSessionFactory();  
	    } catch (NestedIOException e) {  
	        e.printStackTrace(); // XML 有错误时打印异常。  
	        throw new NestedIOException("Failed to parse mapping resource: '" + "____" + "'", e);  
	    } finally {  
	        ErrorContext.instance().reset();  
	    }  
	}
}
