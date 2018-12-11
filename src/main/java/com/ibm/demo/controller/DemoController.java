package com.ibm.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.ibm.demo.common.Source;
import com.ibm.demo.ds.DynamicDataSourceHolder;
import com.ibm.demo.mapper.CityMapper;
import com.ibm.demo.model.City;

@RestController
public class DemoController {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	private static final int CITY_ID = 1;

	@Autowired
	private CityMapper cityMapper;

	@GetMapping( "write-read-tx" )
	@Transactional
	public City writeAndReadWithTx() {
		// 同個交易無法切換DataSource, 且會使用交易前的DataSource
		// 參考連結: https://www.cnblogs.com/yjmyzz/p/7390331.html
		return writeAndRead();
	}

	@GetMapping( "write-read" )
	public City writeAndReadWithoutTx() {
		return writeAndRead();
	}

	@GetMapping( "read" )
	public City read() {
		return select();
	}

	@GetMapping( "read-all" )
	public Map<Source, City> readAll() {
		City city = select();

		DynamicDataSourceHolder.set( Source.MASTER ); // 強制用master

		return ImmutableMap.of( Source.READ, city, Source.MASTER, select() );
	}

	@GetMapping( "write" )
	@Transactional
	public void write() {
		update();

		throw new RuntimeException();
	}

	private City writeAndRead() {
		log.info( "Update Rows: {}", update() );

		return select();
	}

	private int update() {
		return cityMapper.updateCityById( CITY_ID );
	}

	private City select() {
		return cityMapper.selectCityById( CITY_ID );
	}
}