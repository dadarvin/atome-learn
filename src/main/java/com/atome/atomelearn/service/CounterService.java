package com.atome.atomelearn.service;

import com.atome.atomelearn.exceptions.CounterException;
import com.atome.atomelearn.model.Counter;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private Counter counter = new Counter();

    public int getCounter() {
        return counter.getCounter();
    }

    public void incrementCounter() {
        if (counter.counter < Integer.MAX_VALUE){
            counter.incCounter();
        } else {
            throw new CounterException("Counter value over the integer limit");
        }
    }

    public void decreaseCounter() {
        if (counter.counter > 0) {
            counter.decCounter();
        } else {
            throw new CounterException("Counter value can't smaller than zero");
        }
    }
}