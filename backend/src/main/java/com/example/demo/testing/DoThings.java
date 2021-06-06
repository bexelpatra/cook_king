package com.example.demo.testing;

import com.example.demo.testing.DoSomething1;
import com.example.demo.testing.DoSomething2;
import org.springframework.stereotype.Component;

@Component
public class DoThings {

    public <T extends DoSomething> DoSomething job(String type){
        if (type.equals("a")) return new DoSomething1();
        else return new DoSomething2();
    }
}
