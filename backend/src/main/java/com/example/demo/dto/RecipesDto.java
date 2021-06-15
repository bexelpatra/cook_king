package com.example.demo.dto;

import com.example.demo.entity.*;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecipesDto {
    private int id=0;
    private String title;
    private String stuffs;
    private String description;

    private int firstCategory=-1;
    private int secondCategory=-1;
    private String cuisine = "";
    private String userToken="";

    private FirstCategoryDto firstCategoryDto = new FirstCategoryDto();
    private SecondCategoryDto secondCategoryDto = new SecondCategoryDto();
    private UsersDto usersDto = new UsersDto();

    private List<ContentDto> contentDtos = new ArrayList<>();



    // transient
    private List<String> descriptions = new ArrayList<>();

    public RecipesDto setFirstCategoryDto(FirstCategoryDto firstCategoryDto) {
        this.firstCategoryDto = firstCategoryDto;
        return this;
    }

    public RecipesDto setSecondCategoryDto(SecondCategoryDto secondCategoryDto) {
        this.secondCategoryDto = secondCategoryDto;
        return this;
    }

    public RecipesDto setContentDtos(List<ContentDto> contentDtos) {
        this.contentDtos = contentDtos;
        return this;
    }

    public RecipesDto setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
        return this;
    }

    public RecipesDto setUsersDto(UsersDto usersDto) {
        this.usersDto = usersDto;
        return this;
    }

    public RecipesDto convert(){
        firstCategoryDto.setKind(FirstCategoryKind.byValue(this.firstCategory));
        secondCategoryDto.setKind(SecondCategoryKind.byValue(this.secondCategory));
        return this;
    }

    public RecipesEntity to(){
        RecipesEntity recipesEntity = Utils.to(RecipesEntity.class,this);
        recipesEntity.setFirstCategoryEntity(Utils.to(FirstCategoryEntity.class,this.firstCategoryDto));
        recipesEntity.setSecondCategoryEntity(Utils.to(SecondCategoryEntity.class,this.secondCategoryDto));
        recipesEntity.setCuisineEntity(null);
        return recipesEntity;
    }
}
