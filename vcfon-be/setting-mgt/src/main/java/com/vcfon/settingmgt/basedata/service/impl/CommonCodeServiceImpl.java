package com.vcfon.settingmgt.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vcfon.common.enums.BaseCodeTypeEnums;
import com.vcfon.common.enums.BaseUseStatusEnums;
import com.vcfon.common.exception.BadRequestException;
import com.vcfon.settingmgt.basedata.dto.CommonCodeRequestDTO;
import com.vcfon.settingmgt.basedata.dto.CommonCodeResponseDTO;
import com.vcfon.settingmgt.basedata.entity.CommonCode;
import com.vcfon.settingmgt.basedata.repo.CommonCodeRepo;
import com.vcfon.settingmgt.basedata.service.CommonCodeService;
import com.vcfon.settingmgt.localeinput.dto.LocaleInputCodeDTO;
import com.vcfon.settingmgt.localeinput.entity.LocaleInputCode;
import com.vcfon.settingmgt.localeinput.service.LocaleInputCodeService;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonCodeServiceImpl implements CommonCodeService{
	private final CommonCodeRepo commonCodeRepo;
	private final LocaleInputCodeService localeInputCodeService;

	@Override
	@Transactional
	public boolean createCommonCode(CommonCodeRequestDTO param) throws Exception {
		try {
			
			if(commonCodeRepo.existsById(param.getCommonCodeNo())) {
				log.info("DUPLICATE COMMON CODE NO  = {}", param.getCommonCodeNo());
				throw new BadRequestException("Duplicate common code");
			}
			
			List<LocaleInputCode> localeInputCodes = param.getLocaleInputCodes().stream()
					.map(localeInputCodeService::buildEntityFromDto)
					.toList();
			localeInputCodeService.saveListLocaleInputCode(new ArrayList<>(localeInputCodes));
			
			CommonCode commonCodeToSave = new  CommonCode();
			commonCodeToSave.setCodeTypeNo(param.getCodeTypeNo());
			commonCodeToSave.setUseStatusNo(param.getUseStatusNo());
			commonCodeToSave.setFeatureCodeNo(param.getFeatureCodeNo());
			commonCodeToSave.setLocaleCodeNo(localeInputCodes.get(0).getId().getLocaleCodeNo());
			commonCodeRepo.save(commonCodeToSave);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Create common code failed");
		}
	}

	@Override
	public List<CommonCodeResponseDTO> searchCommonCodes(CommonCodeRequestDTO param) {
		Specification<CommonCode> specSearch = (root,query,cb)->{
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("featureCodeNo"),param.getFeatureCodeNo()));
//			predicates.add(cb.or(cb.isNull(root.get("commonCodeNo")), cb.like(cb.lower(root.get("commonCodeNo")), JpaUtils.likeParamsFormater(param.getCommonCodeNo(), false))));
			predicates.add(cb.or(cb.isNull(root.get("codeTypeNo")), cb.equal(root.get("codeTypeNo"), param.getCodeTypeNo())));
			predicates.add(cb.or(cb.isNull(root.get("useStatusNo")), cb.equal(root.get("useStatusNo"), param.getUseStatusNo())));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		var dataResult = commonCodeRepo.findAll(specSearch);
		
		List<Integer> localeCodeParams = dataResult.stream()
				.map(CommonCode::getLocaleCodeNo).toList();
		List<LocaleInputCode> listLocaleInputCodes = localeInputCodeService.findByListLocaleCode(localeCodeParams);
		
		return dataResult.stream()
				.map(item->  {
					List<LocaleInputCodeDTO> listLocaleInputCodesDTO = listLocaleInputCodes.stream()
							.filter(localeInputCode-> localeInputCode.getId().getLocaleCodeNo() == item.getLocaleCodeNo())
							.map(localeInputCodeService::buildDTOFromEntity).toList();
					return CommonCodeResponseDTO.builder()
					.commonCodeNo(item.getCommonCodeNo())
					.featureCodeNo(item.getFeatureCodeNo())
					.codeType(BaseCodeTypeEnums.buildFromCodeTypeNo(item.getCodeTypeNo()))
					.useStatus(BaseUseStatusEnums.buildFromStatusNo(item.getUseStatusNo()))
					.localeInputCodes(listLocaleInputCodesDTO)
					.build();
				}
		).toList();
	}

}
