package com.ibm.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main( String[] args ) {
		// 參考: https://github.com/helloworlde/SpringBoot-DynamicDataSource
		SpringApplication.run( DemoApplication.class, args );
	}
}