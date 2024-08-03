package com.janta.billing.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorDto {
	private String field;
	private String errorMessage;
}
