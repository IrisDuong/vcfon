package com.vcfon.settingmgt.localeinput.service;

import java.util.List;

import com.vcfon.common.service.CustomDataConverter;
import com.vcfon.settingmgt.localeinput.dto.LocaleInputCodeDTO;
import com.vcfon.settingmgt.localeinput.entity.LocaleInputCode;
import com.vcfon.settingmgt.localeinput.entity.LocaleInputCodePK;

public interface LocaleInputCodeService extends CustomDataConverter<LocaleInputCode, LocaleInputCodeDTO>{
	List<LocaleInputCode> findByListLocaleCode(List<Integer> localeCodeParams);
	boolean saveListLocaleInputCode(List<LocaleInputCode> entities);
	boolean deleteByIds(List<LocaleInputCodePK> ids);
	Integer findMaxLocaleCode();
	List<LocaleInputCode> findByLocaleCode(Integer localeCode);
}
