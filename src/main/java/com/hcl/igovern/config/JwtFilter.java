package com.hcl.igovern.config;

import java.io.IOException;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtFilter extends GenericFilterBean {
	
	private static final Logger loggers = LoggerFactory.getLogger(JwtFilter.class);

	@Value("${login.app.jwt.SecretKey}")
	private String jwtSecret;
	 
	@Value("${login.app.jwt.CookieName}")
	private String jwtCookie;
	
	public static final String AUDITOR_NAME="userName";
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        try {
        	String jwt = parseJwt(request);
     	   	if (jwt != null && validateJwtToken(jwt)) {
                String username = getUserNameFromJwtToken(jwt);
                request.setAttribute(AUDITOR_NAME, username);
     	    } else {
     		   throw new ServletException("An exception occurred");
     	   }
        } catch(Exception e) {
     	   throw new ServletException("An exception occurred");
        }
        filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {
		return getJwtFromCookies(request);
	}
	
	private boolean validateJwtToken(String authToken)throws ServletException {
		try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
        	loggers.error("Invalid JWT token: {}", e.getMessage());
            throw new ServletException("throw MalformedJwtException exception");
        } catch (ExpiredJwtException e) {
        	loggers.error("JWT token is expired: {}", e.getMessage());
            throw new ServletException("throw ExpiredJwtException exception");
        } catch (UnsupportedJwtException e) {
        	loggers.error("JWT token is unsupported: {}", e.getMessage());
            throw new ServletException("throw UnsupportedJwtException exception");
        } catch (IllegalArgumentException e) {
        	loggers.error("JWT claims string is empty: {}", e.getMessage());
            throw new ServletException("throw IllegalArgumentException exception");
        } 
	}
	
	private String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
    }
	
	private String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "user-token");
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
    
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("200E635266556A586E3272357987652F413F4428472B4B6250645367566H8908");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
