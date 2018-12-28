package com.ibm.demo.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.ibm.demo.model.City;

/**
 * @author YuHsuanLin 要嘛使用完整的class名稱, 不然就要是正確的bean name
 */
@RestController
public class PNDDemoController {
	@Autowired
	private ParameterNameDiscoverer discoverer;

	@GetMapping( "pnd" )
	public Map<String, List<String>> get() {
		return ImmutableMap.of( "java8", java8(), "spring", spring() );
	}

	private List<String> spring() {
		return stream().flatMap( i -> Stream.of( discoverer.getParameterNames( i ) ) ).collect( Collectors.toList() );
	}

	private List<String> java8() {
		return stream().flatMap( i -> Stream.of( i.getParameters() ) ).map( Parameter::getName ).collect( Collectors.toList() );
	}

	private Stream<Method> stream() {
		return Stream.of( City.class.getDeclaredMethods() ).filter( i -> {
			return i.getName().equals( "setName" );
		} );
	}
}