package com.vcfon.settingmgt.basedata.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.vcfon.settingmgt.auditing.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDataCode extends BaseEntity{

	@Column(name = "code_type_no")
	protected String codeTypeNo;
	
	@Column(name = "use_status_no")
	protected String useStatusNo;

	@Column(name = "feature_code_no")
	protected String featureCodeNo;

	@Column(name = "locale_code_no")
	protected Integer localeCodeNo;
}
