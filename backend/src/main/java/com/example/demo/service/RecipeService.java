package com.example.demo.service;

import com.example.demo.dto.MultiFileDto;
import com.example.demo.dto.RecipesDto;
import com.example.demo.entity.ContentEntity;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;

import java.util.List;

public interface RecipeService {
    List<RecipesEntity> getRecipeByFirstCategory(FirstCategoryKind firstCategoryKind,int page);
    List<RecipesEntity> getRecipeByCategories(FirstCategoryKind[] firstCategoryKind, SecondCategoryKind[] secondCategoryKind, int page);

    RecipesEntity save(RecipesDto recipesDto);
    RecipesEntity saveRecipeAndImage(RecipesDto recipesDto, MultiFileDto multiFileDto, UsersEntity usersEntity) throws Exception;

    RecipesDto getRecipeById(int recipeId);

    boolean deleteContent(ContentEntity contentEntity);
    boolean deleteContent(List<ContentEntity> contentEntity);
}
