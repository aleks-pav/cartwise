package lt.cartwise.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final JwtUtils jwtUtils;
	private final CartwiseUserDetailsService userDetailsService;
	
	Logger logger = LoggerFactory.getLogger( JwtRequestFilter.class );

	public JwtRequestFilter(JwtUtils jwtUtils, CartwiseUserDetailsService userDetailsService) {
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
        String jwt = null, email = null;
        
        try {
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            jwt = authHeader.substring(7);
	            email = jwtUtils.extractEmail(jwt);
	        } 
			
	        if (email != null 
	        		&& SecurityContextHolder
	        			.getContext().getAuthentication() == null) {
	        	
	        	UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
	        	
	        	if (jwtUtils.validateToken(jwt, email)) {
	        		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails
	        				, null
	        				, userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	        	}
	        	
	        }
	        
	        filterChain.doFilter(request, response);
        }catch(ExpiredJwtException e) {
        	sendErrorResponse(response, "JWT token expired", HttpServletResponse.SC_UNAUTHORIZED);
        }catch(JwtException e) {
        	sendErrorResponse(response, "Invalid token", HttpServletResponse.SC_UNAUTHORIZED);
        }
	}
	
	
	private void sendErrorResponse(HttpServletResponse response, String errorMessage, int statusCode) throws IOException {
		logger.debug(errorMessage);
		
		Map<String,Object> error = new LinkedHashMap<>();
		
	    error.put("error", errorMessage);
	    error.put("timestamp", LocalDateTime.now().toString());

	    response.setStatus(statusCode);
	    response.setContentType("application/json");

	    ObjectMapper mapper = new ObjectMapper();
	    response.getWriter().write(mapper.writeValueAsString(error));
	}
}
