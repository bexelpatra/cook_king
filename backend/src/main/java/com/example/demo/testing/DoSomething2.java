package com.example.demo.testing;

import org.springframework.stereotype.Component;

@Component
public class DoSomething2 implements DoSomething {
    @Override
    public int doWork(String type, int number) {
        return number*2;
    }
}
