package net.msonic.tutorial;



import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;



@SpringBootApplication

public class SampleResourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SampleResourceApplication.class, args);
	}
	

	 @Configuration
	 @EnableResourceServer // [2]
	 protected static class ResourceServer extends ResourceServerConfigurerAdapter {
		 
		 /*@Autowired
			private DataSource dataSource;

		 @Override 
		    public void configure(ResourceServerSecurityConfigurer resources) throws Exception { 
		        TokenStore tokenStore = new JdbcTokenStore(dataSource); 
		        resources.resourceId("hascode") 
		                .tokenStore(tokenStore); 
		    }*/
		 
		 //http://localhost:9000/hascode/oauth/token

		 	
			private TokenExtractor tokenExtractor = new BearerTokenExtractor();
			
			@Override
			public void configure(HttpSecurity http) throws Exception {
				http.addFilterAfter(new OncePerRequestFilter() {
					@Override
					protected void doFilterInternal(HttpServletRequest request,
							HttpServletResponse response, FilterChain filterChain)
							throws ServletException, IOException {
						// We don't want to allow access to a resource with no token so clear
						// the security context in case it is actually an OAuth2Authentication
						if (tokenExtractor.extract(request) == null) {
							SecurityContextHolder.clearContext();
						}
						filterChain.doFilter(request, response);
					}
				}, AbstractPreAuthenticatedProcessingFilter.class);
				http.csrf().disable();
				http.authorizeRequests().anyRequest().authenticated();
			}
			
			
			@Bean
			public AccessTokenConverter accessTokenConverter() {
				return new DefaultAccessTokenConverter();
			}
			
			  @Override
			    public void configure(ResourceServerSecurityConfigurer resources) {
			        RemoteTokenServices tokenService = new RemoteTokenServices();
			        tokenService.setClientId("foo");
			        tokenService.setClientSecret("foosecret");
			        tokenService.setCheckTokenEndpointUrl("http://localhost:9000/hascode/oauth/check_token");
			        tokenService.setAccessTokenConverter(accessTokenConverter());
			        resources
			                .resourceId("hascode")
			                .tokenServices(tokenService);
			    }
			
		
	
	 }
}
		 
		
		
//		 
//		 @Override 
//		    public void configure(HttpSecurity http) throws Exception { 
//			 
//			 /*http.addFilterAfter(new OncePerRequestFilter() {
//					@Override
//					protected void doFilterInternal(HttpServletRequest request,
//							HttpServletResponse response, FilterChain filterChain)
//							throws ServletException, IOException {
//						// We don't want to allow access to a resource with no token so clear
//						// the security context in case it is actually an OAuth2Authentication
//						if (tokenExtractor.extract(request) == null) {
//							SecurityContextHolder.clearContext();
//						}
//						filterChain.doFilter(request, response);
//					}
//				}, AbstractPreAuthenticatedProcessingFilter.class);*/
//				//http.csrf().disable();
//				//http.authorizeRequests().anyRequest().authenticated();
//				
//		        http 
//		            // For some reason we cant just "permitAll" OPTIONS requests which are needed for CORS support. Spring Security 
//		            // will respond with an HTTP 401 nonetheless. 
//		            // So we just put all other requests types under OAuth control and exclude OPTIONS. 
//		            .authorizeRequests() 
//		                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')") 
//		                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')") 
//		                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')") 
//		                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')") 
//		                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')");
//		        /*.and() 
//		            // Add headers required for CORS requests. 
//		            .headers().addHeaderWriter((request, response) -> { 
//		                response.addHeader("Access-Control-Allow-Origin", "*"); 
//		                if (request.getMethod().equals("OPTIONS")) { 
//		                    response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method")); 
//		                    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")); 
//		                } 
//		            }); */
//		    } 
//		 
//	 }
//}


