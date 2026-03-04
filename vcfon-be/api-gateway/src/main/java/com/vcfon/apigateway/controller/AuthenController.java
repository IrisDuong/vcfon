package com.vcfon.apigateway.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.util.UriComponentsBuilder;

import com.vcfon.apigateway.dto.AuthenticatedUser;
import com.vcfon.common.dto.response.ApiResponse;
import com.vcfon.common.utils.ApiUtils;
import com.vcfon.common.utils.CookieUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authen")
public class AuthenController {

	@Value("${url.auth-server}")
	private String authServerURL;

	@Value("${url.gateway}")
	private String gatewayURL;

	@Value("${url.front-end}")
	private String feURL;
	
	@GetMapping("/logout/handle")
	public Mono<Void> handleLogout(ServerWebExchange webExchange){
		String logoutRedirectURI = UriComponentsBuilder.fromUriString(authServerURL.concat("/logout"))
				.queryParam("post_logout_redirect_uri", gatewayURL.concat("/authen/logout/success"))
				.build().toUriString();
		ServerHttpResponse response = webExchange.getResponse();
		response.setStatusCode(HttpStatus.FOUND);
		response.getHeaders().setLocation(URI.create(logoutRedirectURI));
		return response.setComplete();
	}


	@GetMapping("/logout/success")
	public Mono<Void> successLogout(ServerWebExchange webExchange){
		ServerHttpResponse response = webExchange.getResponse();
		return webExchange.getSession()
				.flatMap(WebSession::invalidate)
				.contextWrite(ReactiveSecurityContextHolder.clearContext())
				.then(Mono.fromRunnable(()->{
					CookieUtils.setReactiveCookie(response, CookieUtils.ACCESS_TOKEN_COOKIE_NAME, "", 0);
					response.setStatusCode(HttpStatus.FOUND);
					response.getHeaders().setLocation(URI.create(feURL.concat("?isLogged=false")));
				}));
	}
	@GetMapping("/authenticated-user-info")
	public Mono<ResponseEntity<ApiResponse<AuthenticatedUser>>> getAuthenticatedUser(@AuthenticationPrincipal OAuth2User oAuth2User){
		if(!ObjectUtils.isEmpty(oAuth2User)) {

			String sub = oAuth2User.getAttribute("sub");
			String name = oAuth2User.getAttribute("name");
			String email = oAuth2User.getAttribute("email");
			String picture = oAuth2User.getAttribute("picture");
			AuthenticatedUser authenticatedUser = new AuthenticatedUser(sub, name, email, email, picture);
			ResponseEntity<ApiResponse<AuthenticatedUser>> response =  ApiUtils.buildApiResponse(authenticatedUser, HttpStatus.OK, "User has logged");
			return Mono.just(response);
		}else {
			return Mono.just(ApiUtils.buildApiResponse(null, HttpStatus.UNAUTHORIZED, "User is unauthorized"));
		}
		
	}
}
