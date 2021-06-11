package com.example.demo.controller;

import com.example.demo.dto.RecipesDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.service.RecipeService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping(value = "/recipe")
    public ResponseEntity getRecipesByCategory(@RequestParam("firstCategory") int fKind,
                                               @RequestParam(name = "page",defaultValue = "-1")int page){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        List<RecipesEntity> recipesEntityList = recipeService.getRecipeByFirstCategory(FirstCategoryKind.byValue(fKind),page);
        List<RecipesDto> recipesDtoList = Utils.to(RecipesDto.class,recipesEntityList);

        result.put("desc","성공적으로 조회했습니다.");
        result.put("recipesList",recipesDtoList);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }

}
