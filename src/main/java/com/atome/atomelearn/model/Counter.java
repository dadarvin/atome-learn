package com.atome.atomelearn.model;

import org.springframework.context.annotation.Bean;


public class Counter {
    public int counter;

    public Counter(int counter) {
        this.counter = counter;
    }

    public Counter() {
        this.counter = 0;
    }

    public int getCounter(){
        return this.counter;
    }

    public void incCounter() {
        this.counter++;
    }

    public void decCounter() {
        this.counter--;
    }

    public void setCounter(int val) {
        this.counter = val;
        addTwoCounter();
    }

    private void addTwoCounter() {

    }
}

