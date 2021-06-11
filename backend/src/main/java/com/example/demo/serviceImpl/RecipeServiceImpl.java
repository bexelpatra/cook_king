package com.example.demo.serviceImpl;

import com.example.demo.dto.RecipesDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private int maxInt = Integer.MAX_VALUE;

    @Override
    public List<RecipesEntity> getRecipeByFirstCategory(FirstCategoryKind firstCategoryKind, int page) {
        if(firstCategoryKind==null) return new ArrayList<RecipesEntity>();
        if(page <= 0) page = maxInt;

        return recipeRepository.findRecipesEntitiesByIdIsLessThanAndFirstCategoryEntityKindOrderByIdDesc(
                page,
                firstCategoryKind,
                PageRequest.of(0,20, Sort.by(Sort.Direction.DESC,"id")));
    }

    @Override
    public RecipesEntity save(RecipesDto recipesDto) {
        return recipeRepository.save(Utils.to(recipesDto));
    }
}
