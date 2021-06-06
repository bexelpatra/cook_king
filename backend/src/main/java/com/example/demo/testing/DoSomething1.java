package com.example.demo.testing;

import org.springframework.stereotype.Component;

@Component
public class DoSomething1 implements DoSomething {
    @Override
    public int doWork(String type, int number) {
        return number*number;
    }
}
