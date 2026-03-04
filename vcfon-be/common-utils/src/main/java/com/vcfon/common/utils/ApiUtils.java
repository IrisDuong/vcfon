package com.vcfon.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vcfon.common.dto.response.ApiResponse;

public class ApiUtils<T> {

	public static <T> ResponseEntity<ApiResponse<T>> buildApiResponse(T data, HttpStatus httpStatus, String message){
		return new ResponseEntity<ApiResponse<T>>(new ApiResponse<T>(data, httpStatus.value(), message, DateUtils.getNowAsString(true)), httpStatus);
	}
	
	public static <T> ResponseEntity<ApiResponse<T>> unauthorizedResponse(){
		return new ResponseEntity<ApiResponse<T>>(new ApiResponse<T>(null, HttpStatus.UNAUTHORIZED.value(), "User is unauthorized", DateUtils.getNowAsString(true)), HttpStatus.UNAUTHORIZED);
	}
	
	public static <T> ResponseEntity<ApiResponse<T>> forbiddenResponse(){
		return new ResponseEntity<ApiResponse<T>>(new ApiResponse<T>(null, HttpStatus.FORBIDDEN.value(), "User is unauthorized", DateUtils.getNowAsString(true)), HttpStatus.FORBIDDEN);
	}
}
