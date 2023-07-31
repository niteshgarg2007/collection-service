package com.hcl.igovern.xssfilter;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec.ContentSecurityPolicySpec;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ContentSecurityPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.security.web.server.header.XXssProtectionServerHttpHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityXssConf {

	@Bean(name = "customSecurityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions
					.deny()
				));
			
		http.headers(headers -> headers
				.contentSecurityPolicy("script-src 'self' 'strict-dynamic' 'nonce-rAnd0m123' 'unsafe-inline' ; object-src 'none'; base-uri 'none'; require-trusted-types-for 'script'; frame-ancestors 'none' X-Frame-Options: DENY")	
			);
		
		
        return http.build();
    }
}
