package com.fastshoppers.common;

public enum StatusCode {

    OK("OK"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND"),
    MEMBER_UNAUTHORIZED("MEMBER_UNAUTHORIZED"),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL"),
    INVALID_PASSWORD("INVALID_PASSWORD");


    private final String code;

    StatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
