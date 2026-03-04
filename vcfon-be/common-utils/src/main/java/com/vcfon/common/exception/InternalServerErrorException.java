package com.vcfon.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BusinessException{

	public InternalServerErrorException(String message) {
		super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Override
	protected int getHttpCode() {
		return this.code;
	}

}
