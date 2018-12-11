package com.ibm.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ibm.demo.model.City;

@Mapper
public interface CityMapper {
	@Select( "SELECT * FROM CITY WHERE STATE = #{state}" )
	City findByState( @Param( "state" ) String state );

	City selectCityById( int city_id );

	int updateCityById( int city_id );
}