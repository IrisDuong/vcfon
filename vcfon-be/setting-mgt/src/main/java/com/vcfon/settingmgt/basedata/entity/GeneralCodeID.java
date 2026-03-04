package com.vcfon.settingmgt.basedata.entity;

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
public class GeneralCodeID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "general_code_no")
	private Integer generalCodeNo;
	
	@Column(name = "common_code_no")
	private Integer commonCodeNo;
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof GeneralCodeID)) return false;
		GeneralCodeID primaryKey = (GeneralCodeID) obj;
		return Objects.equals(commonCodeNo,primaryKey.commonCodeNo)
				&& Objects.equals(generalCodeNo,primaryKey.generalCodeNo);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(commonCodeNo, generalCodeNo);
	}
}
