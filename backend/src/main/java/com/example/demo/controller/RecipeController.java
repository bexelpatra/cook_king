package com.example.demo.controller;

import com.example.demo.dto.MultiFileDto;
import com.example.demo.dto.RecipesDto;
import com.example.demo.dto.TestDto2;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    /**
     * 레시피들 검색하기
     * @param fKind first category들
     * @param sKind second category들
     * @param page 마지막 recipe의 id
     * @return
     */
    @GetMapping(value = "/recipes")
    public ResponseEntity getRecipesByCategory(@RequestParam(value = "firstCategory") int[] fKind,
                                               @RequestParam(value = "secondCategory") int[] sKind,
                                               @RequestParam(name = "page",defaultValue = "-1")int page){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;


        result.put("desc","성공적으로 조회했습니다.");
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }

    /**
     * 레시피 내용 조회하기
     * @param recipeId
     * @return
     */
    @GetMapping(value = "/recipe/content")
    public ResponseEntity getRecipeContent(@RequestParam(name = "recipe")int recipeId){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        RecipesDto recipesDto = recipeService.getRecipeById(recipeId);
        result.put("desc","성공적으로 조회했습니다.");
        result.put("recipe",recipesDto);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }

    /**
     * 레시피 신규 등록하기
     * @param token
     * @param recipesDto
     * @param multiFileDto
     * @return
     */
    @PostMapping(value = "/recipe")
    public ResponseEntity postRecipe(@RequestParam("token")String token, RecipesDto recipesDto, MultiFileDto multiFileDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        // 1. token 검증
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseGet(null);
        if(usersEntity==null){
            httpStatus = HttpStatus.ACCEPTED;
            result.put("desc","토큰 오류");
            return new ResponseEntity(result,httpStatus);
        }
        RecipesEntity recipesEntity = null;
        try {
            recipesEntity = recipeService.saveRecipeAndImage(recipesDto,multiFileDto,usersEntity);
            result.put("recipe",recipesEntity.to());
            result.put("desc","성공적으로 저장했습니다.");
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("desc","이미지 저장 실패");
            httpStatus = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity(result,httpStatus);
    }
}
