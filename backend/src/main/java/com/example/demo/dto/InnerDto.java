package com.example.demo.dto;

import lombok.Data;

@Data
public class InnerDto {
    private String s1;

    public InnerDto setS1(String s1) {
        this.s1 = s1;
        return this;
    }

    @Override
    public String toString() {
        return "TestDto3{" +
                "s1='" + s1 + '\'' +
                '}';
    }
}
