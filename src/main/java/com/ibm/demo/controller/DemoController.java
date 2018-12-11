package com.ibm.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.demo.common.Source;
import com.ibm.demo.mapper.CityMapper;
import com.ibm.demo.model.City;

@RestController
public class DemoController {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private CityMapper cityMapper;

	@GetMapping( Source.MASTER )
	@Transactional
	public void master() {
		log.info( "Result: " + cityMapper.updateCityById( 1 ) );

		throw new RuntimeException( "Rollback!" );
	}

	@GetMapping( Source.SLAVE )
	public City slave() {
		return cityMapper.selectCityById( 1 );
	}
}