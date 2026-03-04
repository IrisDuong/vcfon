package com.vcfon.common.exception;

import com.vcfon.common.utils.DateUtils;

public abstract class BusinessException extends RuntimeException{

	protected int code;
	protected String message;
	protected String timestamp;
	
	protected BusinessException(String message,int code) {
		super(message);
		this.code = code;
		this.timestamp = DateUtils.getNowAsString(true);
	}
	
	protected abstract int getHttpCode();
}
