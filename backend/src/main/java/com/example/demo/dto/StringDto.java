package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StringDto {
    private String s;
    private String s1;
    private String s2;

    private InnerDto innerDto;
    private List<Integer> ints =new ArrayList<>();

}
