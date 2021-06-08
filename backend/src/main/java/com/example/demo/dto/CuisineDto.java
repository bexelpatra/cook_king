package com.example.demo.dto;

import com.example.demo.entity.CuisineEntity;
import lombok.Data;

@Data
public class CuisineDto {
    public final Class convert = CuisineEntity.class;
    private int id;
    private String name;

}
