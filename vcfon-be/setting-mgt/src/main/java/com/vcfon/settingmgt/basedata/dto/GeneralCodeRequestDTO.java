package com.vcfon.settingmgt.basedata.dto;

import java.util.List;

import com.vcfon.common.dto.request.BaseRequest;
import com.vcfon.settingmgt.localeinput.dto.LocaleInputCodeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralCodeRequestDTO extends BaseRequest {
	private int generalCodeNo;
	private int commonCodeNo;
	private String featureCodeNo;
	private String codeTypeNo;
	private String useStatusNo;
	private boolean isTree;
	private int treeHierLevel;
	private List<LocaleInputCodeDTO> localeInputCodes;
	private List<GeneralCodeRequestDTO> children;
	private int parentGeneralCodeNo;
	
	public GeneralCodeRequestDTO(int commonCodeNo,int generalCodeNo, String featureCodeNo,boolean isTree) {
		super();
		this.commonCodeNo = commonCodeNo;
		this.generalCodeNo = generalCodeNo;
		this.featureCodeNo = featureCodeNo;
		this.isTree = isTree;
	}
}
