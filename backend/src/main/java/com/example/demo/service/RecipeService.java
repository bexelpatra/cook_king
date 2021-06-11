package com.example.demo.service;

import com.example.demo.dto.RecipesDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.enums.FirstCategoryKind;

import java.util.List;

public interface RecipeService {
    List<RecipesEntity> getRecipeByFirstCategory(FirstCategoryKind firstCategoryKind,int page);
}
