package com.example.demo.dto;

import com.example.demo.entity.RecipesEntity;
import lombok.Data;

@Data
public class WrapperRecipe {
    private RecipesEntity recipesEntity;
    private int counts;
}
