package com.vcfon.settingmgt.basedata.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vcfon.common.dto.response.ApiResponse;
import com.vcfon.common.exception.BadRequestException;
import com.vcfon.common.utils.ApiUtils;
import com.vcfon.common.utils.SystemUtils;
import com.vcfon.settingmgt.basedata.dto.GeneralCodeRequestDTO;
import com.vcfon.settingmgt.basedata.dto.GeneralCodeResponseDTO;
import com.vcfon.settingmgt.basedata.service.GeneralCodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/base-data/general-code")
@RequiredArgsConstructor
@Slf4j
public class GeneralCodeController {
	private final GeneralCodeService generalCodeService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Boolean>> createCommonCode(@RequestBody GeneralCodeRequestDTO reqParams) throws Exception{
		if(SystemUtils.isEmptyData(reqParams))
			throw new BadRequestException("Params is invalid");
		
		var result = generalCodeService.createGeneralCode(reqParams);
		ResponseEntity<ApiResponse<Boolean>> dataResponse =  ApiUtils.buildApiResponse(result, HttpStatus.CREATED, "Create list general code successfully");
		log.debug("[GENERAL-CODE-CONTROLLER] - createCommonCode :: {} !",dataResponse.getBody().message());
		return dataResponse;
	}
	
	@PostMapping("/list-by-common-code")
	public ResponseEntity<ApiResponse<List<GeneralCodeResponseDTO>>> getListGeneralByCommonCode(@RequestBody GeneralCodeRequestDTO param){
		if(SystemUtils.isEmptyData(param))
			throw new BadRequestException("Params is invalid");
		
		var result = generalCodeService.getListGeneralByCommonCode(param);
		HttpStatus httpStatus = HttpStatus.OK;
		String resMessage = "Get list general code successfully";
		if(SystemUtils.isEmptyData(result)) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			resMessage = "Get list general code failed";
		}
		ResponseEntity<ApiResponse<List<GeneralCodeResponseDTO>>> dataResponse =  ApiUtils.buildApiResponse(result, httpStatus, resMessage);
		log.debug("[GENERAL-CODE-CONTROLLER] - getListGeneralByCommonCode :: {} !",dataResponse.getBody().message());
		return dataResponse;
	}
	
}
