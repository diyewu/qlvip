package com.xz.config.mybatis;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;

/**
 * Mybatis配置
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfiguration implements TransactionManagementConfigurer{

	private static Log logger = LogFactory.getLog(MybatisConfiguration.class);

//	配置类型别名
	@Value("${mybatis.typeAliasesPackage}")
	private String typeAliasesPackage;
	
//	配置mapper的扫描，找到所有的mapper.xml映射文件
	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;
	
//	加载全局的配置文件
	@Value("${mybatis.configLocation}")
	private String configLocation;
	
	@Autowired
	private DataSource dataSource;
	// DataSource配置
//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DruidDataSource dataSource() {
//		return new com.alibaba.druid.pool.DruidDataSource();
//	}

	// 提供SqlSeesion
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		try {
			SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
			sessionFactoryBean.setDataSource(dataSource);
			
			// 读取配置 
			sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
			
			// 配置mapper.xml文件
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
			sessionFactoryBean.setMapperLocations(resources);

//			加载mybatis全局配置文件
			sessionFactoryBean.setConfigLocation(
					new DefaultResourceLoader().getResource(configLocation));
			
	        //添加插件  （改为使用配置文件加载了）
//	        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper()});
			
			return sessionFactoryBean.getObject();
		} catch (IOException e) {
			logger.warn("mybatis resolver mapper*xml is error");
			return null;
		} catch (Exception e) {
			logger.warn("mybatis sqlSessionFactoryBean create error");
			return null;
		}
	}

	/**
	 * 配置事务
	 */
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
	@Bean
	public PageHelper pageHelper(){
		logger.info("注册MyBatis分页插件PageHelper");
		//分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
		return pageHelper;
	}
	

}
