package com.vcfon.settingmgt.localeinput.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.vcfon.settingmgt.localeinput.dto.LocaleInputCodeDTO;
import com.vcfon.settingmgt.localeinput.entity.LocaleInputCode;
import com.vcfon.settingmgt.localeinput.entity.LocaleInputCodePK;
import com.vcfon.settingmgt.localeinput.repo.LocaleInputCodeRepo;
import com.vcfon.settingmgt.localeinput.service.LocaleInputCodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocaleInputCodeServiceImpl implements LocaleInputCodeService{
	private final LocaleInputCodeRepo localeInputCodeRepo;

	@Override
	public List<LocaleInputCode> findByListLocaleCode(List<Integer> localeCodeNoParams) {
		return localeInputCodeRepo.findById_LocaleCodeNoIn(localeCodeNoParams);
	}

	@Override
	public boolean saveListLocaleInputCode(List<LocaleInputCode> entities) {
		try {
			localeInputCodeRepo.saveAllAndFlush(entities);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public Integer findMaxLocaleCode() {
		return localeInputCodeRepo.findMaxLocaleCode();
	}


	@Override
	public boolean deleteByIds(List<LocaleInputCodePK> ids) {
		try {
			localeInputCodeRepo.deleteAllByIdInBatch(ids);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public LocaleInputCodeDTO buildDTOFromEntity(LocaleInputCode e) {
		return LocaleInputCodeDTO.builder()
				.langCode(e.getId().getLangCode())
				.localeCodeNo(e.getId().getLocaleCodeNo())
				.codeName(e.getCodeName())
				.build();
	}

	@Override
	public LocaleInputCode buildEntityFromDto(LocaleInputCodeDTO d) {
		Integer maxLocaleCodeNo = localeInputCodeRepo.findMaxLocaleCode();
		LocaleInputCodePK localeInputCodeId = new LocaleInputCodePK();
		localeInputCodeId.setLangCode(d.getLangCode());
		localeInputCodeId.setLocaleCodeNo(maxLocaleCodeNo);
		return LocaleInputCode.builder()
				.id(localeInputCodeId)
				.codeName(d.getCodeName())
				.build();
	}

}
