package com.fastshoppers.common;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage<T> {
	private String message;
	private int status;
	private String statusCode;
	private T data;

	public static ResponseMessage<?> ok() {
		return ResponseMessage.builder()
			.status(HttpStatus.OK.value())
			.statusCode(StatusCode.OK.getCode())
			.build();
	}

	public static <T> ResponseMessage<T> ok(T data) {
		return ResponseMessage.<T>builder()
			.status(HttpStatus.OK.value())
			.statusCode(StatusCode.OK.getCode())
			.data(data)
			.build();
	}
}
