package com.example.demo.dto;

import com.example.demo.enums.FirstCategoryKind;
import lombok.Data;

@Data
public class FirstCategoryDto {
    private int id;
    private String name;
    private FirstCategoryKind kind;
}
