package com.capgemini.webapp.dao.impl.config.mybatis;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.capgemini.webapp.dao.impl.config.mybatis.mapper")
public class MyBatisConfig {

	@Autowired
	DataSource dataSource;

	@Bean
	SqlSessionFactoryBean getSqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		//sqlSessionFactoryBean.setTypeAliasesPackage("com.capgemini.webapp.dao.impl.config.mybatis.mapper");
		
		return sqlSessionFactoryBean;
	}
	
}
