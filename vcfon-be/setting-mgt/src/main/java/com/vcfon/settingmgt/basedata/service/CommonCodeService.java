package com.vcfon.settingmgt.basedata.service;

import java.util.List;

import com.vcfon.settingmgt.basedata.dto.CommonCodeRequestDTO;
import com.vcfon.settingmgt.basedata.dto.CommonCodeResponseDTO;
import com.vcfon.settingmgt.basedata.entity.CommonCode;

public interface CommonCodeService {

	boolean createCommonCode(CommonCodeRequestDTO param) throws Exception;
	List<CommonCodeResponseDTO> searchCommonCodes(CommonCodeRequestDTO param);
	CommonCodeResponseDTO getCommonCodeDetails(Integer commonCode);
}
