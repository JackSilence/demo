package com.ibm.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement( mode = AdviceMode.ASPECTJ )
public class DemoApplication {
	public static void main( String[] args ) {
		// 參考: https://github.com/helloworlde/SpringBoot-DynamicDataSource
		SpringApplication.run( DemoApplication.class, args );
	}
}