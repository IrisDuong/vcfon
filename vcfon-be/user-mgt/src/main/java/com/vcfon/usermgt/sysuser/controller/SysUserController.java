package com.vcfon.usermgt.sysuser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vcfon.common.dto.response.ApiResponse;
import com.vcfon.common.utils.ApiUtils;
import com.vcfon.usermgt.sysuser.dto.SysUserDtoRequest;
import com.vcfon.usermgt.sysuser.service.SysUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sys-user")
@RequiredArgsConstructor
public class SysUserController {

	private final SysUserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Void>> createNewUser(@RequestBody SysUserDtoRequest request){
		userService.createNewUser(request);
		return ApiUtils.buildApiResponse(null, HttpStatus.CREATED, "Create new user successfully");
	}
}
