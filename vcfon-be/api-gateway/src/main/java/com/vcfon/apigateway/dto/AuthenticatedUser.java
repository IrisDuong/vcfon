package com.vcfon.apigateway.dto;

public record AuthenticatedUser(String sub,String name,String email,String phoneNo,String picture) {

}
