package com.atome.atomelearn.service;

import com.atome.atomelearn.dao.CounterRepository;
import com.atome.atomelearn.exceptions.CounterException;
import com.atome.atomelearn.model.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CounterService {
    @Autowired
    public CounterRepository counterRepository;

    private Counter generateCounter(int id) {
        Counter countGenerate = Counter.builder()
                .id(id)
                .counter(0)
                .build();
        counterRepository.save(countGenerate);

        return countGenerate;
    }

    private void updateCounter(Counter counter) {
        counterRepository.save(counter);
    }

    public int getCounter(int id) {
        Optional<Counter> counterList = counterRepository.findById(id);

        if (counterList.isPresent()) {
            return counterList.get().getCounter();
        } else {
            Counter tempCounter = generateCounter(id);

            return tempCounter.getCounter();
        }
    }

    public void incrementCounter(int id) {
        Optional <Counter> counterList = counterRepository.findById(id);
        Counter counter;

        if (!counterList.isPresent()) {
            counter = generateCounter(id);
        } else {
            counter = counterList.get();
        }

        if (counter.getCounter() < Integer.MAX_VALUE){
            counter.incCounter();
            updateCounter(counter);
        } else {
            throw new CounterException("Counter value over the integer limit");
        }
    }

    public void decreaseCounter(int id) {
        Optional <Counter> counterList = counterRepository.findById(id);
        Counter counter;

        if (!counterList.isPresent()) {
            counter = generateCounter(id);
        } else {
            counter = counterList.get();
        }

        if (counter.getCounter() > 0) {
            counter.decCounter();
            updateCounter(counter);
        } else {
            throw new CounterException("Counter value can't smaller than zero");
        }
    }
}
