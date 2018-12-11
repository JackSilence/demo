package com.ibm.demo.aop;

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
		try {
			if ( !Source.MASTER.equals( DynamicDataSourceHolder.get() ) ) {
				Signature signature = point.getSignature();

				String id = String.format( "%s.%s", signature.getDeclaringTypeName(), signature.getName() );

				String sql = sqlSessionFactory.getConfiguration().getMappedStatement( id ).getBoundSql( point.getArgs() ).getSql();

				DynamicDataSourceHolder.set( sql.toUpperCase().startsWith( "SELECT" ) ? Source.READ : Source.WRITE );

				log.info( "SQL: {}, Source: {}", sql, DynamicDataSourceHolder.get() );
			}

		} catch ( Exception e ) {
			log.error( "", e );

		}
	}

	@After( "mapper())" )
	public void after( JoinPoint point ) {
		Source source = DynamicDataSourceHolder.get();

		DynamicDataSourceHolder.remove();

		log.info( "Restore DataSource: {} -> {}, Method: {}", source, DynamicDataSourceHolder.get(), point.getSignature() );
	}
}