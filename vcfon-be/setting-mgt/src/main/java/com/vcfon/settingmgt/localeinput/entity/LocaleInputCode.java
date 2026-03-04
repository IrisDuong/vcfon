package com.vcfon.settingmgt.localeinput.entity;


import com.vcfon.settingmgt.auditing.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "SYA_LOCALE_INPUT_CODE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LocaleInputCode extends BaseEntity{

	@EmbeddedId
	private LocaleInputCodePK id;

	@Column(name = "code_name", columnDefinition = "NVARCHAR(255)")
	private String codeName;
	
}
