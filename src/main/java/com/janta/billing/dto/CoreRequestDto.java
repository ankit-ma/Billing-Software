package com.janta.billing.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;



public class CoreRequestDto {

	private String coreName;

	private Map<String, String> fields;

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
}
