package com.ibm.demo.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Override
	protected Object determineCurrentLookupKey() {
		String source = DynamicDataSourceHolder.get();

		log.info( "Current DataSource is [{}]", source );

		return source;
	}
}