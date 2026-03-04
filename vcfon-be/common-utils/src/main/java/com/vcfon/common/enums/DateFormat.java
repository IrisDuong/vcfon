package com.vcfon.common.enums;

import java.time.format.DateTimeFormatter;

public enum DateFormat {
    ISO_DATE("yyyy-MM-dd"),
    ISO_DATE_TIME("yyyy-MM-dd'T'HH:mm:ss"),
    VN_DATE("dd/MM/yyyy"),
    VN_DATE_TIME("dd/MM/yyyy HH:mm:ss");
	
	private final String pattern;

	DateFormat(String pattern) {
		this.pattern = pattern;
	}
	public String getPattern() {
		return pattern;
	}
	public DateTimeFormatter getFormatter() {
		return DateTimeFormatter.ofPattern(pattern);
	}
}
