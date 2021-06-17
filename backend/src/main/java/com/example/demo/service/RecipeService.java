package com.example.demo.service;

import com.example.demo.dto.MultiFileDto;
import com.example.demo.dto.RecipesDto;
import com.example.demo.entity.ContentEntity;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<RecipesEntity> getRecipeByFirstCategory(FirstCategoryKind firstCategoryKind,int page);
    List<RecipesEntity> getRecipeByFirstCategoryOrderByFavoriteCount(FirstCategoryKind firstCategoryKind,int page);
    List<RecipesEntity> getRecipeByCategories(FirstCategoryKind[] firstCategoryKind, SecondCategoryKind[] secondCategoryKind, int page);
    List<RecipesEntity> getRecipeByCategoriesAndKeyword(FirstCategoryKind[] firstCategoryKind, SecondCategoryKind[] secondCategoryKind,String keyword, int page);
    RecipesEntity save(RecipesDto recipesDto);
    RecipesEntity saveRecipeAndImage(RecipesDto recipesDto, MultiFileDto multiFileDto, UsersEntity usersEntity) throws Exception;
    RecipesEntity deleteAndSaveRecipeAndImage(RecipesEntity recipesEntity,RecipesDto recipesDto, MultiFileDto multiFileDto, UsersEntity usersEntity) throws Exception;
    RecipesDto getRecipeById(int recipeId);
    Optional<RecipesEntity> getRecipeEntityById(int recipeId);


    boolean deleteContent(ContentEntity contentEntity);
    boolean deleteContent(List<ContentEntity> contentEntity);

    String test(String path);
}
