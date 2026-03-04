package com.vcfon.settingmgt.localeinput.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocaleInputCodePK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "lang_code")
	private String langCode;

	@Column(name = "locale_code_no")
	private int localeCodeNo;

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof LocaleInputCodePK)) return false;
		LocaleInputCodePK primaryKey = (LocaleInputCodePK) obj;
		return Objects.equals(langCode,primaryKey.langCode)
				&& Objects.equals(localeCodeNo,primaryKey.localeCodeNo);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(langCode, localeCodeNo);
	}
}
