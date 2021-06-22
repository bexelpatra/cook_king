package com.example.demo.controller;

import com.example.demo.dto.RecipesDto;
import com.example.demo.dto.UsersDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.service.EmailService;
import com.example.demo.service.LogIn;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 로그인, 메일인증, 회원가입
 */
@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final RecipeService recipeService;

    private final LogIn logIn;

    // 메일 중복 확인
    @GetMapping(value = "/mail-duplication")
    public ResponseEntity checkDuplicate(@RequestParam("email")String email){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        if(!Utils.isValidEmail(email)) {
            result.put("desc","메일 형식이 잘못되었습니다.");
            result.put("result",0);
            return new ResponseEntity(result,httpStatus);
        }

        if(userService.findUsersEntityByEmail(email).isPresent()){
            result.put("desc","이미 등록된 메일입니다.");
            result.put("result",0);
        }else {
            result.put("desc","사용가능한 이메일입니다.");
            result.put("result",1);
        }
        return new ResponseEntity(result,httpStatus);
    }

    /**
     * 로그인 하기
     * @param token 사용자 토큰
     * @return 사용자 정보
     */
    @GetMapping(value = {"/user"})
    public ResponseEntity signIn(@RequestParam(value = "t") Optional<String> token,
                                 @RequestParam(value = "e") Optional<String> email,
                                 @RequestParam(value = "p") Optional<String> password,
                                 @RequestParam(value = "type", defaultValue = "-1") int type
                                 ){
        Map<String, Object> result = new HashMap<>();
        HttpStatus status = null;
        if(type ==0 && token.get().equals("undefined")){
            result.put("desc","토큰이 없습니다.");
            return new ResponseEntity(result,HttpStatus.ACCEPTED);
        }
        Optional<UsersDto> usersDto = logIn.logIn(type,token.orElse(""),email.orElse(""),password.orElse(""));
        if(usersDto.isPresent()){
            result.put("user",usersDto);
            result.put("desc","성공적으로 사용자 정보를 조회했습니다.");
            status = HttpStatus.OK;
        }else {
            result.put("desc","사용자 정보를 조회하지 못했습니다.");
            status = HttpStatus.ACCEPTED;
        }
        // 결과 출력
        return new ResponseEntity(result,status);
    }

    // 사용자의 즐겨찾기 및 자기가 쓴 글등 모두 가져온다.
    @GetMapping(value = "/user/{token}")
    public ResponseEntity myInfo(@PathVariable("token")String token){
        long l1 = System.currentTimeMillis();
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));

        UsersDto usersDto = userService.getUserInfo(usersEntity);

        result.put("user",usersDto);
        result.put("desc","성공적으로 조회했습니다.");
        httpStatus = HttpStatus.OK;
        long res = System.currentTimeMillis() - l1;
        System.out.println(res);
        result.put("time",res);
        return new ResponseEntity(result,httpStatus);
    }
    // 메일 인증번호 발송하기
    @PostMapping(value = {"/mail-certification"})
    public ResponseEntity mailCertification(@RequestParam(value = "email")String receiver){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        String response = emailService.sendCertMail(receiver);
        
        // 결과 출력
        StringBuilder desc = new StringBuilder();
        if(response == null) {
            desc.append(String.format("[%s]로 인증번호를 발송했습니다.",receiver));
            httpStatus = HttpStatus.OK;
        }else {
            desc.append("전송 샐패 ");
            desc.append("["+response+"]");
            httpStatus = HttpStatus.ACCEPTED;
        }
        result.put("desc",desc.toString());
        return new ResponseEntity(result,httpStatus);
    }

    // 메일 인증하기
    @GetMapping(value = {"/mail-certification"})
    public ResponseEntity checkCertification(@RequestParam(value = "email")String receiver,
                                             @RequestParam(value = "number")String number){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        if(userService.findUsersEntityByEmail(receiver).isPresent()){
            result.put("desc","이미 등록된 메일입니다.");
            result.put("result",0);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        }
        boolean b =  emailService.certificate(receiver,number);
        if(b){
            httpStatus = HttpStatus.OK;
            result.put("desc","이메일 인증 성공");
        }else{
            httpStatus = HttpStatus.ACCEPTED;
            result.put("desc","이메일 인증 실패");
        }

        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/users")
    public ResponseEntity signUp(@RequestBody UsersDto usersDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Optional<UsersEntity> usersEntity = null;
        try {
            usersEntity = userService.signUp(usersDto);
        }catch (Exception e){
            result.put("desc","회원 가입 실패 [이미 등록되었습니다]");
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity(result,httpStatus);
        }
        if(usersEntity.isPresent()){
            result.put("desc","회원가입되었습니다.");
            result.put("user", UsersDto.fix(Utils.to(UsersDto.class,usersEntity.get())));
            httpStatus = HttpStatus.OK;
        }else{
            result.put("desc","회원 가입 실패 [입력값이 잘못되었습니다]");
            httpStatus = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/favorite-recipes")
    public ResponseEntity addFavoriteRecipe(@RequestParam("token")String token,@RequestParam("recipeId")int recipeId){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));
        RecipesEntity recipesEntity = recipeService.getRecipeEntityById(recipeId).orElseThrow(() -> new RuntimeException("no recipe found"));

        int favoriteCount = usersEntity.getUsersFavoriteRecipes().size();
        List<Integer> favorite = usersEntity.getUsersFavoriteRecipes().stream().map(RecipesEntity::getId).collect(Collectors.toList());
        if(favorite.contains(recipeId)){
            favorite.removeIf(integer -> integer.equals(recipeId));
        }else {
            favorite.add(recipeId);
        }
        int x = userService.addFavoriteRecipe(usersEntity.getId(),recipesEntity.getId());
//        favoriteCount -= usersEntity.getUsersFavoriteRecipes().size();


        result.put("favorite",favorite);
        if(x == 1){
            result.put("desc","즐겨찾기 추가 완료");
            httpStatus = HttpStatus.OK;
        }else if(x == 2){
            result.put("desc","즐겨찾기 삭제 완료.");
            httpStatus = HttpStatus.OK;
        }else {
            result.put("desc","실패 : 알수없는 오류");
            httpStatus = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity(result,httpStatus);
    }

    @GetMapping(value = "/user/favorite-recipe")
    public ResponseEntity myFavoriteRecipes(@RequestParam("token")String token){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));
        List<RecipesDto> recipesDtoList = usersEntity.getUsersFavoriteRecipes().stream().map(RecipesEntity::toWithContents).collect(Collectors.toList());

        result.put("recipes",recipesDtoList);
        result.put("desc","성공적으로 요리를 조회했습니다.");
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }

    @GetMapping(value = "/user/recipe")
    public ResponseEntity myRecipes(@RequestParam("token")String token){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));
        List<RecipesDto> recipesDtoList = usersEntity.getRecipesEntities().stream().map(RecipesEntity::toWithContents).collect(Collectors.toList());

        result.put("recipes",recipesDtoList);
        result.put("desc","성공적으로 요리를 조회했습니다.");
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
}
