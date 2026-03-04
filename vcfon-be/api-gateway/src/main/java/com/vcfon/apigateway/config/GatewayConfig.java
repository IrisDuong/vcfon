package com.vcfon.apigateway.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

	@Value("${url.front-end}")
	private String feURL;
	
	@Bean
	public WebClient.Builder webBuilder(){
		return WebClient.builder();
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(Boolean.TRUE);
        corsConfig.setAllowedOrigins(Arrays.asList(feURL));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setExposedHeaders(Arrays.asList(
            "Authorization", "Content-Type", "Content-Disposition",
            "X-Requested-With", "X-User-Authenticated", "X-User-Id"
        ));
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("AUTH-SERVER", r->r
						.path("/auth-server/**")
						.filters(f->f.stripPrefix(1))
						.uri("lb://auth-server"))
				.route("SETTING-MGT", r->r
						.path("/setting-mgt/**")
						.filters(f->f.stripPrefix(1))
						.uri("lb://setting-mgt")
				)
				.route("USER-MGT", r->r
						.path("/user-mgt/**")
						.filters(f->f.stripPrefix(1))
						.uri("lb://user-mgt")
				)
				.build();
				
	}
}
