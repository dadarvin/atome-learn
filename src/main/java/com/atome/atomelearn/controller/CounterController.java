package com.atome.atomelearn.controller;

import com.atome.atomelearn.exceptions.CounterException;
import com.atome.atomelearn.model.CounterResponse;
import com.atome.atomelearn.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/counter")
public class CounterController {
    @Autowired
    private CounterService counterService;

    @GetMapping("/get/{id}")
    public ResponseEntity<CounterResponse> getCounter(
            @PathVariable(value = "id") int id
    ) {
        int cntValue = counterService.getCounter(id);
        CounterResponse response = new CounterResponse(CounterResponse.ApiResponseCode.SUCCESS ,cntValue);

        return responseReturn(response, HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<CounterResponse> addCounter(
            @PathVariable(value = "id") int id
    ) {
        CounterResponse response;
        HttpStatus httpStatus;
        try {
            counterService.incrementCounter(id);
            response = new CounterResponse(CounterResponse.ApiResponseCode.SUCCESS, CounterResponse.OPERATION_SUCCESS);
            httpStatus = HttpStatus.OK;
        } catch (CounterException c) {
            response = new CounterResponse(CounterResponse.ApiResponseCode.BAD_REQUEST, c.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return responseReturn(response, httpStatus);
    }

    @PostMapping("/dec/{id}")
    public ResponseEntity<CounterResponse> decCounter(
            @PathVariable(value = "id") int id
    ) {
        CounterResponse response;
        HttpStatus httpStatus;
        try {
            counterService.decreaseCounter(id);
            response = new CounterResponse(CounterResponse.ApiResponseCode.SUCCESS, CounterResponse.OPERATION_SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (CounterException c) {
            response = new CounterResponse(CounterResponse.ApiResponseCode.BAD_REQUEST, c.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;

        }

        return responseReturn(response, httpStatus);
    }

    private ResponseEntity<CounterResponse> responseReturn(CounterResponse counterResponse, HttpStatus httpStatus) {
        return new ResponseEntity<>(counterResponse, httpStatus);
    }
}