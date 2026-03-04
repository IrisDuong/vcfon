package com.vcfon.common.dto.response;

public record ApiResponse<T>(T data, int httpStatusCode, String message, String timestamp) {

}
