package com.vcfon.common.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BaseUseStatusEnums{
	USE("Y","Use"),
	UN_USE("N","Unuse");
	
	private final String useStatusNo;
	private final String useStatusValue;
	private BaseUseStatusEnums(String useStatusNo, String useStatusValue) {
		this.useStatusNo = useStatusNo;
		this.useStatusValue = useStatusValue;
	}
	public String getUseStatusNo() {
		return useStatusNo;
	}
	public String getUseStatusValue() {
		return useStatusValue;
	}
	
//	public static String getByStatusNo(String no) {
//		Optional<BaseUseStatusEnums> baseUseYn = Stream.of(BaseUseStatusEnums.values()).filter(item-> item.getUseStatusNo().equals(no))
//		.findFirst();
//		
//		return baseUseYn.isPresent() ? baseUseYn.get().getUseStatusValue() : null;
//	}
	
	public static BaseUseStatusEnums buildFromStatusNo(String useStatusNo) {
		Optional<BaseUseStatusEnums> result = Arrays.stream(BaseUseStatusEnums.values())
				.filter(status-> status.useStatusNo.equals(useStatusNo))
				.findFirst();
		return result.isPresent() ? result.get() : null;
	}
}
