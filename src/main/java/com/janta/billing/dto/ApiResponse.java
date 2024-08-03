package com.janta.billing.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponse<T> {
	String message;
	T data;
}
