package com.example.demo.testing;

public interface MyEnum<X, Y> {
    int getValue();
    String getDesc();
    X of(int val);
}
