package com.fastshoppers.common;

public enum StatusCode {

    OK("OK"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    USER_UNAUTHORIZED("USER_UNAUTHORIZED");

    private final String code;

    StatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
