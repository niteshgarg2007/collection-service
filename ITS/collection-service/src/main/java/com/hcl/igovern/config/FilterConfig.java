package com.hcl.igovern.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

//@Configuration
public class FilterConfig {

	/*
	 * @Bean public FilterRegistrationBean<Filter> jwtFilter() {
	 * FilterRegistrationBean<Filter> filter= new FilterRegistrationBean<>();
	 * filter.setFilter(new JwtFilter());
	 * 
	 * filter.addUrlPatterns("/its/apis/overpayment/*");
	 * filter.addUrlPatterns("/claimant/service2");
	 * filter.addUrlPatterns("/claimant/add");
	 * filter.addUrlPatterns("/claimant/all");
	 * filter.addUrlPatterns("/api/claimant/all");
	 * filter.addUrlPatterns("/api/employer/update");
	 * filter.addUrlPatterns("/api/employer/find/**");
	 * filter.addUrlPatterns("/api/employer/update");
	 * filter.addUrlPatterns("/api/employer/delete");
	 * filter.addUrlPatterns("/api/employer/addEmp"); return filter; }
	 */
}
