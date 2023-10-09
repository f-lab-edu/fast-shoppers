package com.fastshoppers.common;

import lombok.Data;

@Data
public class ResponseMessage {
    private String message;
    private int status;
    private Object data;

    public ResponseMessage(String message, int status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }

    public ResponseMessage(String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
