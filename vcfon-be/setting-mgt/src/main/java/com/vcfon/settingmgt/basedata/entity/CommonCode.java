package com.vcfon.settingmgt.basedata.entity;


import java.util.Set;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

@Entity
@Table(name = "SYA_COMMON_CODE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonCode extends BaseDataCode implements Persistable<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "common_code_no")
	private Integer commonCodeNo;
	
	
	@OneToMany(mappedBy = "ownerCommonCode", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<GeneralCode> generalCodes;


	@Transient
	private boolean isNew = true; // flag
	
	@Override
	public Integer getId() {
		return commonCodeNo;
	}


	@Override
	public boolean isNew() {
		return isNew;
	}
	
	@PrePersist
	public void beforeSave() {
		this.isNew = false;
	}
}
