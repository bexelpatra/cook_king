package com.example.demo.controller;

import com.example.demo.dto.MultiFileDto;
import com.example.demo.dto.RecipesDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/recipe")
@RequiredArgsConstructor
@CrossOrigin
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
                                               @RequestParam(value = "keyword") String keyword,
                                               @RequestParam(name = "page",defaultValue = "-1")int page){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        // 1. parameter 검증하기
        FirstCategoryKind[] firstCategoryKinds = FirstCategoryKind.of(fKind);
        SecondCategoryKind[] secondCategoryKinds = SecondCategoryKind.of(sKind);
        if(firstCategoryKinds ==null) {
            result.put("desc","1차 분류가 잘못되었습니다.");
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity(result,httpStatus);
        }
        if(secondCategoryKinds == null){
            result.put("desc","2차 분류가 잘못되었습니다.");
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity(result,httpStatus);
        }

        List<RecipesDto> recipesDtoList = new ArrayList<>();
        recipeService.getRecipeByCategoriesAndKeyword(firstCategoryKinds,secondCategoryKinds,keyword,page).stream().forEach(recipesEntity -> recipesDtoList.add(recipesEntity.toWithContents()));

        result.put("desc","성공적으로 조회했습니다.");
        result.put("recipes",recipesDtoList);
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
            result.put("desc","요리 저장 실패");
            httpStatus = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity(result,httpStatus);
    }
    /**
     * 레시피 업데이트하기
     * @param token
     * @param recipesDto
     * @param multiFileDto
     * @return
     */
    @PatchMapping(value = "/recipe")
    public ResponseEntity patchRecipe(@RequestParam("token")String token, RecipesDto recipesDto, MultiFileDto multiFileDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        // 1. token 검증
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseGet(null);
        if(usersEntity==null){
            httpStatus = HttpStatus.ACCEPTED;
            result.put("desc","토큰 오류");
            return new ResponseEntity(result,httpStatus);
        }
        RecipesEntity recipesEntity = recipeService.getRecipeEntityById(recipesDto.getId()).orElseThrow(() -> new RuntimeException("recipe not found"));
        try {
            recipeService.deleteAndSaveRecipeAndImage(recipesEntity,recipesDto,multiFileDto,usersEntity);
            result.put("recipe",recipesEntity.to());
            result.put("desc","성공적으로 수정했습니다.");
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("desc","이미지 저장 실패");
            httpStatus = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity(result,httpStatus);
    }
    @GetMapping(value = "/pop-recipes")
    public ResponseEntity mainRecipes(@RequestParam("fKind")int fkind, @RequestParam(value = "page",required = false,defaultValue = "-1")int page){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        FirstCategoryKind kind = FirstCategoryKind.of(fkind);
        if(kind ==null) {
            result.put("desc","분류가 잘못되었습니다.");
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity(result,httpStatus);
        }
        // 2. 결과값 받아오기
        List<RecipesEntity> recipesEntities = recipeService.getRecipeByFirstCategoryOrderByFavoriteCount(kind,page);

        // 3. 데이터 변환후 반환
        List<RecipesDto> recipesDtoList = recipesEntities.stream().map(recipesEntity -> recipesEntity.toWithContents()).collect(Collectors.toList());

        result.put("desc",String.format("[%s]요리를 인기순으로 가져왔습니다.",kind.getDesc()));
        result.put("recipes",recipesDtoList);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
}
