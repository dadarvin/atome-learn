package com.atome.atomelearn.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CounterResponse {

    public int code;
    public Integer counter;
    public String status;

    public static final String OPERATION_SUCCESS = "success";

    public CounterResponse(int code, int counter, String status) {
        this.code = code;
        this.counter = counter;
        this.status = status;
    }

    public CounterResponse(int code, int counter) {
        this.code = code;
        this.counter = counter;
    }

    public CounterResponse(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
