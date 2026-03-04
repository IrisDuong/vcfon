package com.vcfon.common.utils;
import org.springframework.util.ObjectUtils;

public class NumberUtils {

	public static Long getDefaultIfNull(Long number) {
		return !ObjectUtils.isEmpty(number) ? number:0l;
	}
	
	public static Integer getDefaultIfNull(Integer number) {
		return !ObjectUtils.isEmpty(number) ? number:0;
	}

	public static Double getDefaultIfNull(Double number) {
		return !ObjectUtils.isEmpty(number) ? number:0.0d;
	}

	public static Float getDefaultIfNull(Float number) {
		return !ObjectUtils.isEmpty(number) ? number:0.0f;
	}
}
