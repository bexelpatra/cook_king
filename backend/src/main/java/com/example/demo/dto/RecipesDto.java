package com.example.demo.dto;

import com.example.demo.entity.*;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;
import com.example.demo.utilities.Utils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipesDto {
    private int id=0;
    private String title;
    private String stuffs;
    private String description;

    private int firstcategoryInt =-1;
    private int secondcategoryInt =-1;

    private FirstCategoryKind firstCategoryKind;
    private SecondCategoryKind secondCategoryKind;

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
        this.firstCategoryKind = (FirstCategoryKind.of(this.firstcategoryInt));
        this.secondCategoryKind = (SecondCategoryKind.of(this.secondcategoryInt));
        return this;
    }

    public RecipesEntity to(){
        RecipesEntity recipesEntity = Utils.to(RecipesEntity.class,this);
        recipesEntity.setCuisineEntity(null);
        return recipesEntity;
    }

    /**
     * 이미지 정보를 지우고 dto내용으로 업데이트 합니다.
     * (제목, 설명, 재료, 1차 분류, 2차 분류)
     * @param recipesEntity
     */
    public void update(RecipesEntity recipesEntity){
        recipesEntity.setTitle(getTitle());
        recipesEntity.setDescription(getDescription());
        recipesEntity.setStuffs(getStuffs());
        recipesEntity.setFirstCategoryKind(getFirstCategoryKind());
        recipesEntity.setSecondCategoryKind(getSecondCategoryKind());
        recipesEntity.setContentEntities(null);
    }
}
