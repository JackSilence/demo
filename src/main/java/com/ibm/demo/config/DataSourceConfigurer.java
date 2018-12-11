package com.ibm.demo.config;

import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.google.common.collect.ImmutableMap;
import com.ibm.demo.common.Source;
import com.ibm.demo.ds.DynamicDataSource;

@Configuration
public class DataSourceConfigurer implements TransactionManagementConfigurer, InitializingBean {
	@Bean
	@Primary
	@ConfigurationProperties( prefix = "spring.datasource.h2.master" )
	public DataSource master() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties( prefix = "spring.datasource.h2.slave" )
	public DataSource slave() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public DynamicDataSource dynamicDataSource() {
		DynamicDataSource source = new DynamicDataSource();

		source.setTargetDataSources( ImmutableMap.of( Source.WRITE, master(), Source.READ, slave(), Source.MASTER, master() ) );

		source.setDefaultTargetDataSource( master() );

		return source;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

		sessionFactory.setDataSource( dynamicDataSource() );

		return sessionFactory.getObject();
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager( dynamicDataSource() );
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Stream.of( master(), slave() ).forEach( i -> {
			Resource resource = new ClassPathResource( "db/schema.sql" );

			DatabasePopulatorUtils.execute( new ResourceDatabasePopulator( resource ), i );

		} );
	}
}