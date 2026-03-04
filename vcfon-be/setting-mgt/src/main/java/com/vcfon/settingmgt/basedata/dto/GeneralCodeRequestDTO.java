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
	private Integer generalCodeNo;
	private Integer commonCodeNo;
	private String featureCodeNo;
	private String codeTypeNo;
	private String useStatusNo;
	private boolean isTree;
	private Integer treeHierLevel;
	private List<LocaleInputCodeDTO> localeInputCodes;
	private List<GeneralCodeRequestDTO> children;
	private Integer parentGeneralCodeNo;
	
	public GeneralCodeRequestDTO(Integer commonCodeNo,Integer generalCodeNo, String featureCodeNo,boolean isTree) {
		super();
		this.commonCodeNo = commonCodeNo;
		this.generalCodeNo = generalCodeNo;
		this.featureCodeNo = featureCodeNo;
		this.isTree = isTree;
	}
}
