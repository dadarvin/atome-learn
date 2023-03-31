package com.atome.atomelearn.model;

import org.springframework.http.HttpStatus;

public enum ApiResponseCode {
    SUCCESS(HttpStatus.OK.value()),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public int code;

    ApiResponseCode(int code) {
        this.code = code;
    }
}
