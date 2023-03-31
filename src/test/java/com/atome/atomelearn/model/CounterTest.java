package com.atome.atomelearn.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CounterTest {

    @Test
    public void testIncCounter() {
        // Given
        Counter counter = Counter.builder()
                .id(1)
                .counter(0)
                .build();

        // When
        counter.incCounter();

        // Then
        assertEquals(1, counter.getCounter());
    }

    @Test
    public void testDecCounter() {
        // Given
        Counter counter = Counter.builder()
                .id(1)
                .counter(1)
                .build();

        // When
        counter.decCounter();

        // Then
        assertEquals(0, counter.getCounter());
    }
}
