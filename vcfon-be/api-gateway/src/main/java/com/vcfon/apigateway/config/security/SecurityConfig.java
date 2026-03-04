package com.vcfon.apigateway.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	private final Oauth2AuthenticationSuccessHandler authenSuccessHandler;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Value("${url.auth-server}")
	private String authServerURL;
	
	public SecurityConfig(
			Oauth2AuthenticationSuccessHandler authenSuccessHandler
			,CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
		super();
		this.authenSuccessHandler = authenSuccessHandler;
		this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.cors(Customizer.withDefaults())
			.authorizeExchange(authExchange->authExchange
					.pathMatchers("/login/**","/oauth2/**","/static/**").permitAll()
					.pathMatchers(HttpMethod.OPTIONS).permitAll()
					.anyExchange().authenticated()
			)
			.oauth2Login(oauth2Login->oauth2Login.authenticationSuccessHandler(authenSuccessHandler))
			.exceptionHandling(ex->ex
					.authenticationEntryPoint(customAuthenticationEntryPoint)
			)
			.oauth2ResourceServer(resourcerServer->resourcerServer.jwt(Customizer.withDefaults()))
			.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
		return http.build();
	}

	@Bean
	public ReactiveJwtDecoder jwtDecoder() {
		return NimbusReactiveJwtDecoder.withJwkSetUri(authServerURL.concat("/oauth2/jwks")).build();
	}
}
