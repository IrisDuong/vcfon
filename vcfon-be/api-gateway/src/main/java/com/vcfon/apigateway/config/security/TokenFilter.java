package com.vcfon.apigateway.config.security;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.vcfon.common.utils.CookieUtils;
import com.vcfon.common.utils.SystemUtils;

import reactor.core.publisher.Mono;

@Component
public class TokenFilter implements WebFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		if(request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION) && request.getHeaders().getFirst(HttpHeaders.ACCEPT).equals(MediaType.APPLICATION_JSON_VALUE)) {
			return chain.filter(exchange);
		}else {
			HttpCookie cookie = request.getCookies().getFirst(CookieUtils.ACCESS_TOKEN_COOKIE_NAME);
			if(!SystemUtils.isEmptyData(cookie)) {
				String accessToken = cookie.getValue();
				ServerHttpRequest muatatedRequest = request.mutate()
						.header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessToken))
						.build();
				return chain.filter(exchange.mutate().request(muatatedRequest).build());
			}
		}
        return chain.filter(exchange);
	}

}
