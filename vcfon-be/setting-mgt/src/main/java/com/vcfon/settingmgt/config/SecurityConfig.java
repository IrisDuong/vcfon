package com.vcfon.settingmgt.config;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import com.vcfon.common.utils.CookieUtils;

import jakarta.servlet.http.Cookie;

@Configuration
public class SecurityConfig {

	
	@Value("${url.auth-server}")
	private String authServerURL;
	
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri(authServerURL.concat("/oauth2/jwks")).build();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth->auth.anyRequest().authenticated())
				.oauth2ResourceServer(resourceServer->resourceServer.jwt(Customizer.withDefaults()))
				.build();
	}
}
