package com.vcfon.settingmgt.basedata.entity;


import java.util.List;

import org.springframework.data.domain.Persistable;

import com.vcfon.common.utils.SystemUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

@Entity
@Table(name = "SYA_GENERAL_CODE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralCode extends BaseDataCode implements Persistable<GeneralCodeID>{

	@EmbeddedId
	private GeneralCodeID id;
	
	@MapsId("commonCodeNo")
	@ManyToOne
	@JoinColumn(name = "common_code_no")
	private CommonCode ownerCommonCode;
	
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<GeneralCode> children;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({@JoinColumn(name = "parent_general_code_no"),@JoinColumn(name = "parent_common_code_no")})
	@ToString.Exclude
	private GeneralCode parent;
	
	@Column(name = "is_tree")
	private boolean isTree;

	
	@Column(name = "tree_level")
	private Integer treeLevel;
	
	@Transient
	private boolean isNew = true;
	
	public void beforeSave() {
		this.isNew = false;
	}
	
	private void addChild(GeneralCode child) {
		if(this.isTree && !SystemUtils.isEmptyData(this.children)) {
			this.children.add(child);
		}
	}
}
