package com.atome.atomelearn.service;

import com.atome.atomelearn.dao.CounterRepository;
import com.atome.atomelearn.exceptions.CounterException;
import com.atome.atomelearn.model.Counter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CounterServiceTest {

    @Mock
    private CounterRepository counterRepository;

    @InjectMocks
    private CounterService counterService;

    @Test
    void testGetCounter_validRequest_success() {
        int id = 1;
        Counter counter = Counter.builder()
                .id(id)
                .counter(5)
                .build();

        when(counterRepository.findById(id)).thenReturn(Optional.of(counter));

        assertEquals(5, counterService.getCounter(id));
    }

    @Test
    void testGetCounter_notExist_fail() {
        int id = 2;

        when(counterRepository.findById(id)).thenReturn(Optional.empty());

        assertEquals(0, counterService.getCounter(id));
        verify(counterRepository, times(1)).save(any(Counter.class));
    }

    // naming: methodName_valid/no_expected-result
    @Test
    void testIncrementCounter_validRequest_success() {
        int id = 3;
        Counter counter = Counter.builder()
                .id(id)
                .counter(5)
                .build();

        when(counterRepository.findById(id)).thenReturn(Optional.of(counter));

        counterService.incrementCounter(id);

        assertEquals(6, counter.getCounter());
        verify(counterRepository, times(1)).save(counter);
    }

    @Test
    void testIncrementCounter_notExist_fail() {
        int id = 4;

        when(counterRepository.findById(id)).thenReturn(Optional.empty());

        counterService.incrementCounter(id);

        verify(counterRepository, times(2)).save(any(Counter.class));
    }

    @Test
    void testIncrementCounter_whenOverLimit_fail() {
        int id = 5;
        Counter counter = Counter.builder()
                .id(id)
                .counter(Integer.MAX_VALUE)
                .build();

        when(counterRepository.findById(id)).thenReturn(Optional.of(counter));

        assertThrows(CounterException.class, () -> counterService.incrementCounter(id));
    }

    @Test
    void testDecrementCounter_validRequest_success() {
        int id = 6;
        Counter counter = Counter.builder()
                .id(id)
                .counter(5)
                .build();

        when(counterRepository.findById(id)).thenReturn(Optional.of(counter));

        counterService.decreaseCounter(id);

        assertEquals(4, counter.getCounter());
        verify(counterRepository, times(1)).save(counter);
    }

    @Test
    void testDecrementCounter_whenNotExist_fail() {
        int id = 7;

        when(counterRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CounterException.class, () -> counterService.decreaseCounter(id));
    }

    @Test
    void testDecrementCounter_whenUnderZero() {
        int id = 8;
        Counter counter = Counter.builder()
                .id(id)
                .counter(0)
                .build();

        when(counterRepository.findById(id)).thenReturn(Optional.of(counter));

        assertThrows(CounterException.class, () -> counterService.decreaseCounter(id));
    }
}
