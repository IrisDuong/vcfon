package com.vcfon.settingmgt.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vcfon.common.enums.BaseCodeTypeEnums;
import com.vcfon.common.enums.BaseUseStatusEnums;
import com.vcfon.common.exception.NotFoundException;
import com.vcfon.common.utils.NumberUtils;
import com.vcfon.settingmgt.basedata.dto.GeneralCodeRequestDTO;
import com.vcfon.settingmgt.basedata.dto.GeneralCodeResponseDTO;
import com.vcfon.settingmgt.basedata.entity.CommonCode;
import com.vcfon.settingmgt.basedata.entity.GeneralCode;
import com.vcfon.settingmgt.basedata.entity.GeneralCodeID;
import com.vcfon.settingmgt.basedata.repo.CommonCodeRepo;
import com.vcfon.settingmgt.basedata.repo.GeneralCodeRepo;
import com.vcfon.settingmgt.basedata.service.GeneralCodeService;
import com.vcfon.settingmgt.localeinput.dto.LocaleInputCodeDTO;
import com.vcfon.settingmgt.localeinput.entity.LocaleInputCode;
import com.vcfon.settingmgt.localeinput.entity.LocaleInputCodePK;
import com.vcfon.settingmgt.localeinput.service.LocaleInputCodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralCodeServiceImpl implements GeneralCodeService{
	private final GeneralCodeRepo generalCodeRepo;
	private final LocaleInputCodeService localeInputCodeService;
	private final CommonCodeRepo commonCodeRepo;
	/**
	 * Create single general code
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public boolean createGeneralCode(GeneralCodeRequestDTO param) throws Exception {
		
		try {
			// find the owner common code
			CommonCode ownerCommonCode = commonCodeRepo.findById(param.getCommonCodeNo())
					.orElseThrow(()-> new NotFoundException("Common code not found"));

			boolean isTree = false;
			Integer treeLevel = null;
			if(BaseCodeTypeEnums.TREE.getcodeTypeNo().equals(ownerCommonCode.getCodeTypeNo())) {
				isTree = true;
			    treeLevel = 1;
			}
			
			GeneralCode rootGeneralCode = this.buildGeneralCode(param,ownerCommonCode,null,isTree,treeLevel);
			param.getChildren().forEach(item-> this.buildGeneralCode(item,ownerCommonCode,rootGeneralCode,false,2));
			return true;
		} catch (Exception e) {
			log.error("[GeneralCodeServiceImpl] - create SINGLE  general code failed");
			throw new Exception("Create general code failed");
		}
	}

	/**
	 * Build single general code from request param
	 */
	private GeneralCode buildGeneralCode(GeneralCodeRequestDTO param, CommonCode ownerCommonCode, GeneralCode parentGeneralCode,boolean isTree,int treeLevel) {
		
		//  Create locale input code for multi-language
		if(param.getGeneralCodeNo() > 0) {
			// delete all locale before create new all to void complicate
			List<LocaleInputCodePK> existedLocaleInputCodeIds = param.getLocaleInputCodes().stream()
					.map(item-> new LocaleInputCodePK(item.getLangCode(), item.getLocaleCodeNo())).toList();
			localeInputCodeService.deleteByIds(existedLocaleInputCodeIds);
		}
		List<LocaleInputCode> newLocaleInputCodes = param.getLocaleInputCodes().stream().map(localeInputCodeService::buildEntityFromDto).toList();
		localeInputCodeService.saveListLocaleInputCode(new ArrayList<>(newLocaleInputCodes));


		// find max general code and create new
		Integer maxGeneralCodeNo = generalCodeRepo.findMaxGeneralcode(param.getCommonCodeNo(),param.getFeatureCodeNo(), BaseUseStatusEnums.USE.getUseStatusNo());
		GeneralCode generalCode = new GeneralCode();
		generalCode.setId(GeneralCodeID.builder()
				.commonCodeNo(ownerCommonCode.getCommonCodeNo())
				.generalCodeNo(NumberUtils.getDefaultIfNull(maxGeneralCodeNo)+1)
				.build()
		);
		generalCode.setFeatureCodeNo(param.getFeatureCodeNo());
		generalCode.setCodeTypeNo(param.getCodeTypeNo());
		generalCode.setUseStatusNo(param.getUseStatusNo());
		generalCode.setLocaleCodeNo(newLocaleInputCodes.get(0).getId().getLocaleCodeNo());
		generalCode.setOwnerCommonCode(ownerCommonCode);
		generalCode.setTree(isTree);
		generalCode.setParent(parentGeneralCode);
		generalCode.setTreeLevel(treeLevel);
		
		return generalCodeRepo.save(generalCode);
	}

	/**
	 * Get list general code from common code & group code
	 */
	@Override
	@Transactional
	public List<GeneralCodeResponseDTO> getListGeneralByCommonCode(GeneralCodeRequestDTO param) {
		try {

			List<GeneralCodeResponseDTO> dataResult = generalCodeRepo.getListGeneralByCommonCode(param.getCommonCodeNo(), param.getFeatureCodeNo(), BaseUseStatusEnums.USE.getUseStatusNo());
			
			List<Integer> localeCodeParams = dataResult.stream()
					.map(GeneralCodeResponseDTO::getLocaleCodeNo).toList();
			List<LocaleInputCode> listLocaleInputCodes = localeInputCodeService.findByListLocaleCode(localeCodeParams);
			dataResult.stream().forEach(generalCode->{
				List<LocaleInputCodeDTO> listLocaleInputCodesDTO = listLocaleInputCodes.stream()
						.filter(item-> item.getId().getLocaleCodeNo() == generalCode.getLocaleCodeNo())
						.map(localeInputCodeService::buildDTOFromEntity)
						.toList();
				generalCode.setLocaleInputCodes(listLocaleInputCodesDTO);
			});
			return dataResult;
		} catch (Exception e) {
			return null;
		}
	}
}
