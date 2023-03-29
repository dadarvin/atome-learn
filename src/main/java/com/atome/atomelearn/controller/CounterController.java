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

    @GetMapping("/get")
    public ResponseEntity<CounterResponse> getCounter() {
        int cntValue = counterService.getCounter();
        CounterResponse response = new CounterResponse(HttpStatus.OK.value(), cntValue);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CounterResponse> addCounter() {
        try {
            counterService.incrementCounter();
            CounterResponse response = new CounterResponse(HttpStatus.OK.value(), CounterResponse.OPERATION_SUCCESS);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CounterException c) {
            CounterResponse response = new CounterResponse(HttpStatus.BAD_REQUEST.value(), c.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/dec")
    public ResponseEntity<CounterResponse> decCounter() {
        CounterResponse response;
        HttpStatus httpStatus;
        try {
            counterService.decreaseCounter();
            response = new CounterResponse(HttpStatus.OK.value(), CounterResponse.OPERATION_SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (CounterException c) {
            response = new CounterResponse(HttpStatus.BAD_REQUEST.value(), c.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;

        }

        return new ResponseEntity<>(response, httpStatus);
    }
}