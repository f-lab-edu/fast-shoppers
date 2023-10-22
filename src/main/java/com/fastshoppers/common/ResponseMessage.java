package com.fastshoppers.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
    private String message;
    private int status;
    private String statusCode;
    private Object data;
}
