package com.atome.atomelearn.model;

import org.springframework.http.HttpStatus;

public class CounterResponse {
    private int httpStatus;
    private int counter;
    private String status;

    public CounterResponse(int httpStatus, int counter) {
        this.counter = counter;
        this.httpStatus = httpStatus;
    }

    public CounterResponse(int httpStatus, String status) {
        this.httpStatus = httpStatus;
        this.status = status;
    }
}
