package com.hcl.igovern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CollectionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectionServiceApplication.class, args);
	}

}
