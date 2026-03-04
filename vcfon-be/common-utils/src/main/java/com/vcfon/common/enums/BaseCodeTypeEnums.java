package com.vcfon.common.enums;

import java.util.Optional;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BaseCodeTypeEnums{
	MULTI("M","Multiple"),
	TREE("T","Tree");
	
	private final String codeTypeNo;
	private final String codeTypeValue;
	private BaseCodeTypeEnums(String codeTypeNo, String codeTypeValue) {
		this.codeTypeNo = codeTypeNo;
		this.codeTypeValue = codeTypeValue;
	}
	public String getcodeTypeNo() {
		return codeTypeNo;
	}
	public String getcodeTypeValue() {
		return codeTypeValue;
	}
	
	public static BaseCodeTypeEnums buildFromCodeTypeNo(String codeTypeNo) {
		Optional<BaseCodeTypeEnums> result = Stream.of(BaseCodeTypeEnums.values()).filter(item-> item.getcodeTypeNo().equals(codeTypeNo))
		.findFirst();
		
		return result.isPresent() ? result.get():null;
	}
}
