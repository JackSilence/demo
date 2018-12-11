package com.ibm.demo.aop;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.demo.common.Source;
import com.ibm.demo.ds.DynamicDataSourceHolder;

@Aspect
@Component
public class DataSourceAspect {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Pointcut( "execution( * com.ibm.demo.mapper.*.*(..))" )
	public void mapper() {
	}

	@Before( "mapper()" )
	public void before( JoinPoint point ) {
		Signature signature = point.getSignature();

		try {
			log.info( point.getClass().getSimpleName() );
			log.info( point.getClass().getName() );

			Configuration config = sqlSessionFactory.getConfiguration();
			String sql = config.getMappedStatement( signature.getName() ).getBoundSql( point.getArgs() ).getSql();

			DynamicDataSourceHolder.set( sql.toUpperCase().startsWith( "SELECT" ) ? Source.SLAVE : Source.MASTER );

		} catch ( Exception e ) {
		}
	}

	@After( "mapper())" )
	public void after( JoinPoint point ) {
		DynamicDataSourceHolder.remove();

		log.info( "Restore DataSource to [{}] in Method [{}]", DynamicDataSourceHolder.get(), point.getSignature() );
	}
}