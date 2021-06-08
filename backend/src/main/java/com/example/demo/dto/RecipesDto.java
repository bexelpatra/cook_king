package com.example.demo.dto;

import com.example.demo.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class RecipesDto {
    public final Class convert = RecipesEntity.class;

    private int id;
    private String title;
    private String stuffs;

    private FirstCategoryEntity firstCategoryEntity;
    private SecondCategoryEntity secondCategoryEntity;
    private CuisineEntity cuisineEntity;
    @JsonIgnore
    private List<ContentEntity> contentEntities;
}
