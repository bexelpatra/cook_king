package com.example.demo.dto;

import com.example.demo.enums.SecondCategoryKind;
import lombok.Data;

@Data
public class SecondCategoryDto {
    private int id;
    private String name;
    private SecondCategoryKind kind;
}
