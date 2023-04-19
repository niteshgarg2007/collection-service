package com.hcl.igovern.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI myOpenAPI() {

		Info info = new Info().title("COLLECTION MICRO SERVICE API").version("1.0")
				.description("This API exposes endpoints to deal with overpayments CURD operations.");

		return new OpenAPI().info(info);
	}
}