package com.hcl.igovern.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MDCFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(MDCFilter.class);
	
	public static final String USERID="userId";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Calling MDCFilter...");
		try {
			MDC.put("IpAddress", request.getRemoteAddr());
			MDC.put("RequestURI", request.getRequestURI());
			// replace the user id form the JWT token / Request in actual service.
			if (request.getAttribute("userName") != null)
				MDC.put(USERID, request.getAttribute("userName").toString()); 
			else
				MDC.put(USERID, "");
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove("IpAddress");
            MDC.remove("RequestURI");
            MDC.remove(USERID);
		}
	}
}
