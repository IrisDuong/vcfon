package com.vcfon.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationException {

	public static ResponseEntity<BusinessException> doResponse(BusinessException ex){
		return new ResponseEntity<BusinessException>(ex, HttpStatus.valueOf(ex.getHttpCode()));
	}
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BusinessException> handleBadRequest(BadRequestException ex){
		return doResponse(ex);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<BusinessException> handleNotFound(NotFoundException ex){
		return doResponse(ex);
	}

	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<BusinessException> handleNotFound(InternalServerErrorException ex){
		return doResponse(ex);
	}
}
