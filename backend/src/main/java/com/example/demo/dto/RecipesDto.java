package com.example.demo.dto;

import com.example.demo.entity.*;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.rmi.CORBA.Util;
import java.util.List;

@Data
public class RecipesDto {
    public final Class convert = RecipesEntity.class;

    private int id;
    private String title;
    private String stuffs;

    private FirstCategoryDto firstCategoryDto;
    private SecondCategoryDto secondCategoryDto;
    private CuisineDto cuisineDto;
    @JsonIgnore
    private List<ContentDto> contentDtos;

    // transient

    public RecipesDto setFirstCategoryDto(FirstCategoryDto firstCategoryDto) {
        this.firstCategoryDto = firstCategoryDto;
        return this;
    }

    public RecipesDto setSecondCategoryDto(SecondCategoryDto secondCategoryDto) {
        this.secondCategoryDto = secondCategoryDto;
        return this;
    }

    public RecipesDto setCuisineDto(CuisineDto cuisineDto) {
        this.cuisineDto = cuisineDto;
        return this;
    }

    public RecipesDto setContentDtos(List<ContentDto> contentDtos) {
        this.contentDtos = contentDtos;
        return this;
    }
}
