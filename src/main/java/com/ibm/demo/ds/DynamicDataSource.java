package com.ibm.demo.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.ibm.demo.common.Source;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Override
	protected Object determineCurrentLookupKey() {
		Source source = DynamicDataSourceHolder.get();

		log.info( "Current DataSource: {}", source );

		return source; // 如果是null會用defaultTargetDataSource
	}
}