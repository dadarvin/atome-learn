package com.atome.atomelearn.controller;

import com.atome.atomelearn.dao.CounterRepository;
import com.atome.atomelearn.model.ApiResponseCode;
import com.atome.atomelearn.model.CounterResponse;
import com.atome.atomelearn.service.CounterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CounterControllerTest {
    @Mock
    private CounterRepository counterRepository;

    @Mock
    private CounterService counterService;

    @InjectMocks
    private CounterController counterController;

    @Test
    void testGetCounter_validRequest_success() {
        // given
        int id = 1;
        int counterValue = 10;
        when(counterService.getCounter(id)).thenReturn(counterValue);

        // when
        ResponseEntity<CounterResponse> response = counterController.getCounter(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseCode.SUCCESS.code, response.getBody().code);
        assertEquals(counterValue, response.getBody().counter);
    }

    @Test
    void testAddCounter_validRequest_success() {
        // given
        int id = 1;
        doNothing().when(counterService).incrementCounter(id);

        // when
        ResponseEntity<CounterResponse> response = counterController.addCounter(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseCode.SUCCESS.code, response.getBody().code);
        assertEquals(CounterResponse.OPERATION_SUCCESS, response.getBody().status);

        verify(counterService, times(1)).incrementCounter(id);
    }

//    @Test
//    void testAddCounter_exception_fail() throws CounterException{
//        //given
//        int id = 1;
//        Counter counter = Counter.builder()
//                .id(id)
//                .counter(Integer.MAX_VALUE)
//                .build();
//
//        doNothing().when(counterService).incrementCounter(id);
//
//        // when
//        ResponseEntity<CounterResponse> response = counterController.addCounter(id);
//
//        // then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals(ApiResponseCode.BAD_REQUEST.code, response.getBody().code);
//        assertEquals("Counter value over the integer limit", response.getBody().status);
//        verify(counterService, times(1)).incrementCounter(id);
//    }
//
//    @Test
//    void decCounter() {
//    }
}