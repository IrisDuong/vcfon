package com.vcfon.settingmgt.basedata.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vcfon.common.dto.response.ApiResponse;
import com.vcfon.common.enums.BaseCodeTypeEnums;
import com.vcfon.common.exception.BadRequestException;
import com.vcfon.common.exception.NotFoundException;
import com.vcfon.common.utils.ApiUtils;
import com.vcfon.common.utils.SystemUtils;
import com.vcfon.settingmgt.basedata.dto.CommonCodeRequestDTO;
import com.vcfon.settingmgt.basedata.dto.CommonCodeResponseDTO;
import com.vcfon.settingmgt.basedata.service.CommonCodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/base-data/common-code")
@RequiredArgsConstructor
@Slf4j
public class CommonCodeController {
//	private final Logger log = LoggerFactory.getLogger(CommonCodeController.class);
	
	private final CommonCodeService commonCodeService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Boolean>> createCommonCode(@RequestBody CommonCodeRequestDTO reqParams) throws Exception{
		if(SystemUtils.isEmptyData(reqParams))
			throw new BadRequestException("Params is invalid");
		
		var result = commonCodeService.createCommonCode(reqParams);
		ResponseEntity<ApiResponse<Boolean>> dataResponse =  ApiUtils.buildApiResponse(result, HttpStatus.CREATED, "Create common code successfully");
		log.info("[COMMON-CODE-CONTROLLER] - createCommonCode :: {} !",dataResponse.getBody().message());
		return dataResponse;
	}
	
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<List<CommonCodeResponseDTO>>> searchCommonCode(@RequestBody CommonCodeRequestDTO reqParams){
		List<CommonCodeResponseDTO> result = commonCodeService.searchCommonCodes(reqParams);
		if(SystemUtils.isEmptyData(result))
			throw new NotFoundException("No data");
		
		ResponseEntity<ApiResponse<List<CommonCodeResponseDTO>>> dataResponse = ApiUtils.buildApiResponse(result, HttpStatus.OK, "Get list general code successfully !");
		log.info("[COMMON-CODE-CONTROLLER] - createCommonCode :: {} !",dataResponse.getBody().message());
		return dataResponse;
	}
	
	@GetMapping("/detail/{commonCodeNo}")
	public ResponseEntity<ApiResponse<CommonCodeResponseDTO>> searchCommonCode(@PathVariable Integer commonCodeNo){
		CommonCodeResponseDTO result = CommonCodeResponseDTO.builder()
				.commonCodeNo(commonCodeNo)
				.featureCodeNo("IN")
				.codeType(BaseCodeTypeEnums.MULTI)
				.build();
		if(SystemUtils.isEmptyData(result))
			throw new NotFoundException("No data");
		
		ResponseEntity<ApiResponse<CommonCodeResponseDTO>> dataResponse = ApiUtils.buildApiResponse(result, HttpStatus.OK, "Get list general code successfully !");
		log.info("[COMMON-CODE-CONTROLLER] - createCommonCode :: {} !",dataResponse.getBody().message());
		return dataResponse;
	}
}
