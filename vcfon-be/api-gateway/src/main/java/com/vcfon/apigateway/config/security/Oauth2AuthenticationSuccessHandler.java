package com.vcfon.apigateway.config.security;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.vcfon.grpc.usermgt.SysUserProtoRequest;
import com.vcfon.grpc.usermgt.SysUserProtoServiceGrpc.SysUserProtoServiceBlockingStub;
import com.vcfon.common.enums.UserRole;
import com.vcfon.common.utils.CookieUtils;
import com.vcfon.common.utils.SystemUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class Oauth2AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler{

	private final SysUserProtoServiceBlockingStub stub;
	private final PasswordEncoder passwordEncoder;
	private final ReactiveOAuth2AuthorizedClientService oAuth2AuthorizedClientService;
	
	@Value("${url.front-end}")
	private String feURL;
	
	public Oauth2AuthenticationSuccessHandler(SysUserProtoServiceBlockingStub stub
		   ,PasswordEncoder passwordEncoder
		   ,ReactiveOAuth2AuthorizedClientService oAuth2AuthorizedClientService
			) {
		super();
		this.stub = stub;
		this.passwordEncoder = passwordEncoder;
		this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
	}


	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
		try {
			ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
			
			String principalName = authentication.getName();
			OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
			String clientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			
			return oAuth2AuthorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName)
					.flatMap(authorizedClient->{
						OAuth2AccessToken oAuth2AccessToken = authorizedClient.getAccessToken();
						OAuth2RefreshToken oAuth2RefreshToken = authorizedClient.getRefreshToken();
						
						String refreshToken = oAuth2RefreshToken.getTokenValue();
						String accessToken = oAuth2AccessToken.getTokenValue();
						log.info("oauth2 accessToken = "+accessToken);
						log.info("oauth2 refreshToken = "+refreshToken);
						OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
						
						// set token to frontend
						CookieUtils.setReactiveCookie(response, CookieUtils.ACCESS_TOKEN_COOKIE_NAME, accessToken, 1800000);
						CookieUtils.setReactiveCookie(response, CookieUtils.REFRESH_TOKEN_COOKIE_NAME, refreshToken, 3600000);
						
				
						// Call grpc to create new user
						String defaultPassword = SystemUtils.defaultUserPassword();
						SysUserProtoRequest sysUserProtoRequest = SysUserProtoRequest.newBuilder()
								.setEmail(oAuth2User.getAttribute("email"))
								.setFirstName(oAuth2User.getAttribute("name"))
								.setAvatarUrl(oAuth2User.getAttribute("picture"))
								.setRawPassword(defaultPassword)
								.setHashedPassword(passwordEncoder.encode(defaultPassword))
								.setActive(true)
								.setDeletable(false)
								.setLockedTimes(0)
								.setPhoneNo("078424053")
								.setSub(oAuth2User.getAttribute("sub"))
								.setUserName(oAuth2User.getAttribute("email"))
								.setRoleNo(UserRole.EMP.getRoleNo())
								.build();
						stub.createSysUserProto(sysUserProtoRequest);
						
						// response
						response.setStatusCode(HttpStatus.FOUND);
						response.getHeaders().setLocation(URI.create(feURL));
						return response.setComplete();
					});
			
		} catch (Exception e) {
			e.printStackTrace();
			return Mono.empty();
		}
	}

}
