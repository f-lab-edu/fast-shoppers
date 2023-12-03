package com.fastshoppers.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseMessage {
    private String message;
    private int status;
    private String statusCode;
    private Object data;

    public static ResponseMessage ok() {
        return ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .statusCode(StatusCode.OK.getCode())
                .build();
    }
}
