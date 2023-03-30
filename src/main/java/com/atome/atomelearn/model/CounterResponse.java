package com.atome.atomelearn.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CounterResponse {
    public enum ApiResponseCode{
        SUCCESS(HttpStatus.OK.value()),
        BAD_REQUEST(HttpStatus.BAD_REQUEST.value()),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value());

        private int code;
        ApiResponseCode(int code) {
            this.code = code;
        }
    }
    public int code;
    public Integer counter;
    public String status;

    public static final String OPERATION_SUCCESS = "success";

    public CounterResponse(ApiResponseCode apiResponseCode, int counter) {
        this.code = apiResponseCode.code;
        this.counter = counter;
    }

    public CounterResponse(ApiResponseCode apiResponseCode, String status) {
        this.code = apiResponseCode.code;
        this.status = status;
    }
}
