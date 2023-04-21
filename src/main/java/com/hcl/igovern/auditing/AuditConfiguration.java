package com.hcl.igovern.auditing;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration 
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfiguration {

	@Bean
	public AuditorAware<String> auditorProvider(HttpServletRequest request, HttpServletResponse response) {
		return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				String userName = (String) request.getAttribute("userName");
            	if(userName!=null && !userName.isEmpty()) {
            		return Optional.of("ITS:"+userName.toUpperCase());
            	}
            	return Optional.of("Unknown");
			}
		};
	}
}
