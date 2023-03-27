package com.atome.atomelearn.controller;

import com.atome.atomelearn.exceptions.CounterException;
import com.atome.atomelearn.model.Counter;
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
    private CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/getCounter")
    public ResponseEntity<Map<String, Object>> getCounter() {
        int cntValue = counterService.getCounter();

        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.OK.value());
        response.put("counter", cntValue);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addCounter")
    public ResponseEntity<Map<String, Object>> addCounter() {
        try {
            counterService.incrementCounter();

            Map<String, Object> response = new HashMap<>();
            response.put("code", HttpStatus.OK.value());
            response.put("status", "success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CounterException c) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", HttpStatus.BAD_REQUEST.value());
            response.put("status", c.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/decCounter")
    public ResponseEntity<Map<String, Object>> decCounter() {

        try {
            counterService.decreaseCounter();

            Map<String, Object> response = new HashMap<>();
            response.put("code", HttpStatus.OK.value());
            response.put("status", "success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CounterException c) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", HttpStatus.BAD_REQUEST.value());
            response.put("status", c.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}