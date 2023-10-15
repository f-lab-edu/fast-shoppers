package com.fastshoppers.common;

import lombok.Data;

@Data
public class ResponseMessage {
    private String message;
    private int status;
    private String statusCode;
    private Object data;

    public ResponseMessage(String message, int status) {
        this.message = message;
        this.status = status;
        this.statusCode = null;
        this.data = null;
    }

    public ResponseMessage(String message, int status, String statusCode) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = null;
    }

    public ResponseMessage(String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.statusCode = null;
        this.data = data;
    }

    public ResponseMessage(String message, int status, String statusCode, Object data) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
    }
}
