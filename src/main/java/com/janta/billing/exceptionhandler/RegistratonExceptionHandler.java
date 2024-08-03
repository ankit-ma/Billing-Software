package com.janta.billing.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.janta.billing.dto.ApiResponse;
import com.janta.billing.dto.ErrorDto;
import com.janta.billing.exception.SystemException;

@RestControllerAdvice
public class RegistratonExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {

		List<ErrorDto> errors = new ArrayList<>();
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			ErrorDto errorDTO = ErrorDto.builder()
					.errorMessage(error.getDefaultMessage())
					.field(error.getField())
					.build();

			errors.add(errorDTO);
		});
		return ApiResponse.builder().message("FAILED").data(errors).build();
	}
	
	@ExceptionHandler(SystemException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<?> handleSystemException(SystemException exception) {
		
			ErrorDto errorDTO = ErrorDto.builder()
					.errorMessage(exception.getMessage())
					.build();

	return ApiResponse.builder().message("FAILED").data(errorDTO).build();

	}
	
}
