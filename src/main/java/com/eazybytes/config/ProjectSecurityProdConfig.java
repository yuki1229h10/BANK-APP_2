package com.eazybytes.config;

import java.util.Collections;

import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.util.SessionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)throws Exception {
		CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
		http.securityContext(ContextConfig->ContextConfig.requireExplicitSave(false)).sessionManagement(SessionConfig->SessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)).cors(corsConfig->corsConfig.configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("https://localhost:4200"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		})).csrf(CsrfConfig->CsrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler).ignoringRequestMatchers("/contact","/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())).addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class).requiresChannel(rcc->rcc.anyRequest())
	}

}
