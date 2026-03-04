package com.vcfon.common.utils;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;

public class CookieUtils {

	public static final String ACCESS_TOKEN_COOKIE_NAME = "ATC";
	public static final String REFRESH_TOKEN_COOKIE_NAME = "RTC";
	
	public static void setReactiveCookie(ServerHttpResponse response, String name, String value, long maxAge) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
				.path("/")
				.secure(false)
				.httpOnly(true)
				.maxAge(Duration.ofMillis(maxAge))
				.sameSite("Lax")
				.build();
		response.addCookie(cookie);
	}
}
