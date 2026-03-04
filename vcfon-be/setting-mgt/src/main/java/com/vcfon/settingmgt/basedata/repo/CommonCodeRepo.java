package com.vcfon.settingmgt.basedata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vcfon.settingmgt.basedata.entity.CommonCode;

public interface CommonCodeRepo extends JpaRepository<CommonCode, Integer>, JpaSpecificationExecutor<CommonCode>{
//	List<CommonCodeResponseDTO> searchCommonCode(CommonCodeRequestDTO param);
}
