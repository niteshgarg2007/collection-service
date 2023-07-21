package com.hcl.igovern.security;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(1)
public class OWASPXssFilter implements Filter {
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
        // no initialization necessary
    }

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new OWASPXssRequestWrapper((HttpServletRequest) request), response);
	}
	
	@Override
    public void destroy() {
        // no destruction necessary
    }

}
