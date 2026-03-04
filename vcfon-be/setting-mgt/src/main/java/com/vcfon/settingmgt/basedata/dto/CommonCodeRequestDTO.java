package com.vcfon.settingmgt.basedata.dto;

import java.util.List;

import com.vcfon.settingmgt.localeinput.dto.LocaleInputCodeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonCodeRequestDTO{
	private Integer commonCodeNo;
	private String featureCodeNo;
	protected String codeTypeNo;
	protected String useStatusNo;
	private List<LocaleInputCodeDTO> localeInputCodes;
}
