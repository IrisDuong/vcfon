package com.vcfon.settingmgt.basedata.dto;

import java.util.List;

import com.vcfon.common.enums.BaseCodeTypeEnums;
import com.vcfon.common.enums.BaseUseStatusEnums;
import com.vcfon.settingmgt.localeinput.dto.LocaleInputCodeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonCodeResponseDTO {
	private Integer commonCodeNo;
	private String featureCodeNo;
	private BaseCodeTypeEnums codeType;
	private BaseUseStatusEnums useStatus;
	private List<LocaleInputCodeDTO> localeInputCodes;
	
}
